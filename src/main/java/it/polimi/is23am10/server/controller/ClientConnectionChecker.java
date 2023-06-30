package it.polimi.is23am10.server.controller;

import it.polimi.is23am10.server.controller.exceptions.NullGameHandlerInstance;
import it.polimi.is23am10.server.model.items.bookshelf.exceptions.BookshelfGridColIndexOutOfBoundsException;
import it.polimi.is23am10.server.model.items.bookshelf.exceptions.BookshelfGridRowIndexOutOfBoundsException;
import it.polimi.is23am10.server.model.items.card.exceptions.NegativeMatchedBlockCountException;
import it.polimi.is23am10.server.model.items.card.exceptions.NullMatchedBlockCountException;
import it.polimi.is23am10.server.model.items.card.exceptions.NullScoreBlockListException;
import it.polimi.is23am10.server.model.player.exceptions.NullPlayerBookshelfException;
import it.polimi.is23am10.server.network.gamehandler.CurrentPlayerHandler;
import it.polimi.is23am10.server.network.gamehandler.GameHandler;
import it.polimi.is23am10.server.network.gamehandler.exceptions.GameSnapshotUpdateException;
import it.polimi.is23am10.server.network.messages.ErrorMessage;
import it.polimi.is23am10.server.network.messages.ErrorMessage.ErrorSeverity;
import it.polimi.is23am10.server.network.playerconnector.AbstractPlayerConnector;
import it.polimi.is23am10.utils.ErrorTypeString;
import it.polimi.is23am10.utils.exceptions.NullIndexValueException;
import java.rmi.RemoteException;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The server client connection checker class definition.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
@SuppressWarnings({"checkstyle:nonemptyatclausedescriptioncheck"})
public final class ClientConnectionChecker implements Runnable {

  /** The logger, an instance of {@link Logger}. */
  protected Logger logger = LogManager.getLogger(ClientConnectionChecker.class);

  /** The connected players. */
  protected Set<AbstractPlayerConnector> pcs;

  /** The acive game handlers. */
  protected Set<GameHandler> ghs;

  /**
   * The max connection snoozer miss time: 15 seconds. This is 3 times the client snooze frequency.
   */
  protected final long MAX_SNOOZE_TIMEOUT_MS = 1000 * 15;

  /** The max game turn inactivity time. */
  protected long MAX_TURN_INACTIVITY_MS;

  /**
   * Constructor.
   *
   * @param maxTurnInactivityTimeMs the max configured game turn inactivity time in ms.
   */
  public ClientConnectionChecker(long maxTurnInactivityTimeMs) {
    MAX_TURN_INACTIVITY_MS = maxTurnInactivityTimeMs;
  }

  /** {@inheritDoc} */
  @Override
  public void run() {
    while (true) {
      try {
        checkAllPlayers();
        checkActivePlayersInactivity();
        Thread.sleep(5000);
      } catch (GameSnapshotUpdateException e) {
        // Logging as fatal here as it's failing to send a game snapshot. Other
        // message delivery failures will be considered errors.
        logger.fatal(
            "{} {} {}",
            ServerDebugPrefixString.START_COMMAND_PREFIX,
            ErrorTypeString.ERROR_UPDATING_GAME,
            e);
        // Not adding the error here since it will not be possible to be sent
        // to player if it already failed delivering a game.
      } catch (InterruptedException e) {
        logger.error(e.getMessage());
      }
    }
  }

  /**
   * Advance the game state if an active player has been disconnected.
   *
   * @param pc the disconnected player connector reference.
   * @throws NullScoreBlockListException If the list of scoreblocks is null.
   * @throws NullPlayerBookshelfException If bookshelf is null.
   * @throws NullIndexValueException If the index provided is null.
   * @throws BookshelfGridRowIndexOutOfBoundsException If the bookshelf row index is out of bounds.
   * @throws BookshelfGridColIndexOutOfBoundsException If the bookshelf column index is out of
   *     bounds.
   * @throws NegativeMatchedBlockCountException If the number of matched blocks to set is negative.
   * @throws NullMatchedBlockCountException If the number of matched blocks to set is null.
   * @throws NullPointerException Generic NPE.
   * @throws InterruptedException Thread interruption exception.
   * @throws NullGameHandlerInstance If it is not possible to retrieve a game handler referece by
   *     id.
   * @throws GameSnapshotUpdateException If not able to push game update.
   */
  protected void advanceGame(AbstractPlayerConnector pc)
      throws BookshelfGridColIndexOutOfBoundsException,
          BookshelfGridRowIndexOutOfBoundsException,
          NullIndexValueException,
          NullPlayerBookshelfException,
          NullScoreBlockListException,
          NullPointerException,
          NullMatchedBlockCountException,
          NegativeMatchedBlockCountException,
          InterruptedException,
          NullGameHandlerInstance,
          GameSnapshotUpdateException {
    if (pc == null) {
      logger.error(
          "Can not advance the game state after the active player disconnection, null player"
              + " connector received");
      return;
    }
    // call next turn if the disconnected player is the active player
    GameHandler handlerRef = ServerControllerState.getGameHandlerByUUID(pc.getGameId());
    if (handlerRef.getGame().getActivePlayer().equals(pc.getPlayer())) {
      handlerRef.getGame().nextTurn();
      handlerRef.pushGameState();
      handlerRef.updateCurrentPlayerHandler();
    }
  }

  /**
   * Perform connection check on all connected players.
   *
   * @throws GameSnapshotUpdateException
   */
  protected void checkAllPlayers() throws GameSnapshotUpdateException {
    try {
      pcs = ServerControllerState.getPlayersPool();
      synchronized (pcs) {
        for (AbstractPlayerConnector pc : pcs) {
          if (pc.getPlayer().getIsConnected()
              && expired(pc.getLastSnoozeMs(), System.currentTimeMillis(), MAX_SNOOZE_TIMEOUT_MS)) {
            logger.warn(
                "Detected connection loss for {}, disconnecting the player",
                pc.getPlayer().getPlayerName());
            pc.getPlayer().setIsConnected(false);

            // call next turn if the disconnected player is the active player
            advanceGame(pc);
          }
        }
      }
    } catch (NullGameHandlerInstance
        | BookshelfGridColIndexOutOfBoundsException
        | BookshelfGridRowIndexOutOfBoundsException
        | NullIndexValueException
        | NullPlayerBookshelfException
        | NullScoreBlockListException
        | NullPointerException
        | NullMatchedBlockCountException
        | NegativeMatchedBlockCountException e) {
      logger.error("{} {}", ErrorTypeString.ERROR_GAME_STATE, e);
    } catch (InterruptedException e) {
      logger.error("{} {}", ErrorTypeString.ERROR_MESSAGE_DELIVERY, e);
    }
  }

  /**
   * Perform activity check on all the active players across all games.
   *
   * @throws GameSnapshotUpdateException
   */
  protected void checkActivePlayersInactivity() throws GameSnapshotUpdateException {
    try {
      ghs = ServerControllerState.getGamePools();
      synchronized (ghs) {
        for (GameHandler gh : ghs) {
          CurrentPlayerHandler h = gh.getCurrentPlayerHandler();
          if (h.getPlayer().getIsConnected()
              && expired(
                  h.getStartPlayingTimeMs(), System.currentTimeMillis(), MAX_TURN_INACTIVITY_MS)) {
            AbstractPlayerConnector connectorRef = gh.getPlayerConnectorFromPlayer(h.getPlayer());
            // already notified, perform disconnectoion
            if (h.getNotified()) {
              logger.warn(
                  "Detected turn inactivity for {}, disconnecting the player",
                  h.getPlayer().getPlayerName());
              h.getPlayer().setIsConnected(false);
              if (connectorRef != null) {
                connectorRef.notify(
                    new ErrorMessage(
                        "You have been disconnected due to inactivity",
                        h.getPlayer(),
                        ErrorSeverity.ERROR));
              } else {
                logger.error(
                    "{}: Failed to push warning message, can not find player connector from player",
                    ErrorTypeString.ERROR_GAME_STATE);
              }

              // call next turn if the disconnected player is the active player
              advanceGame(connectorRef);
              // notify disconnection warning
            } else {
              h.setNotified(true);
              h.setStartPlayingTimeMs(System.currentTimeMillis());
              if (connectorRef != null) {
                connectorRef.notify(
                    new ErrorMessage(
                        "You will be disconnected for inactivity in "
                            + String.valueOf(MAX_TURN_INACTIVITY_MS / 1000)
                            + " seconds",
                        h.getPlayer(),
                        ErrorSeverity.INFO));
              } else {
                logger.error(
                    "{}: Failed to push warning message, can not find player connector from player",
                    ErrorTypeString.ERROR_GAME_STATE);
              }
            }
          }
        }
      }
    } catch (NullGameHandlerInstance
        | BookshelfGridColIndexOutOfBoundsException
        | BookshelfGridRowIndexOutOfBoundsException
        | NullIndexValueException
        | NullPlayerBookshelfException
        | NullScoreBlockListException
        | NullPointerException
        | NullMatchedBlockCountException
        | NegativeMatchedBlockCountException e) {
      logger.error("{} {}", ErrorTypeString.ERROR_GAME_STATE, e);
    } catch (InterruptedException | RemoteException e) {
      logger.error("{} {}", ErrorTypeString.ERROR_MESSAGE_DELIVERY, e);
    }
  }

  /**
   * Check if a player activity is expired or not.
   *
   * @param lhs The left hand side of the operation.
   * @param rhs The right hand side of the operation.
   * @param limit The operation limit value.
   * @return The requested check result.
   */
  protected boolean expired(long lhs, long rhs, long limit) {
    return rhs - lhs > limit;
  }
}
