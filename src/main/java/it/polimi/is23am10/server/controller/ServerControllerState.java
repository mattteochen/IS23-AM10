package it.polimi.is23am10.server.controller;

import it.polimi.is23am10.server.controller.exceptions.NullGameHandlerInstance;
import it.polimi.is23am10.server.model.factory.exceptions.DuplicatePlayerNameException;
import it.polimi.is23am10.server.model.player.Player;
import it.polimi.is23am10.server.network.gamehandler.GameHandler;
import it.polimi.is23am10.server.network.gamehandler.exceptions.NullPlayerConnector;
import it.polimi.is23am10.server.network.playerconnector.AbstractPlayerConnector;
import it.polimi.is23am10.server.network.playerconnector.PlayerConnectorSocket;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The server controller state class definition. This should be a singleton-like instance owning the
 * server current state.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
@SuppressWarnings({"checkstyle:abbreviationaswordinnamecheck"})
public final class ServerControllerState {

  /** The logger, an instance of {@link Logger}. */
  private static final Logger logger = LogManager.getLogger(ServerControllerState.class);

  /** Active {@link GameHandler} instances. */
  private static Set<GameHandler> gamePool = Collections.synchronizedSet(new HashSet<>());

  /** Active players connected with their {@link AbstractPlayerConnector} instances. */
  private static Set<AbstractPlayerConnector> playersPool =
      Collections.synchronizedSet(new HashSet<>());

  /** References to rmi connectors proxies. The link is made through the unique playerIds. */
  private static Map<UUID, AbstractPlayerConnector> rmiConnectorsProxies =
      Collections.synchronizedMap(new HashMap<>());

  /** Private constructor. */
  private ServerControllerState() {}

  /**
   * Add a new game handler to the pool.
   *
   * @param handler The game handler instance to add.
   * @throws NullGameHandlerInstance If the game handler is null. On null game handler.
   */
  public static final void addGameHandler(GameHandler handler) throws NullGameHandlerInstance {
    if (handler == null) {
      throw new NullGameHandlerInstance();
    }
    // synch performed by the collection
    gamePool.add(handler);
    logger.info("Added new game handler with id {}", handler.getGame().getGameId());
  }

  /**
   * Remove a game handler from the pool. This performs all the clients disconnections.
   *
   * @param id The game id to remove.
   */
  public static final void removeGameHandlerById(UUID id) {
    if (id == null) {
      return;
    }

    Optional<GameHandler> target;

    synchronized (gamePool) {
      target = gamePool.stream().filter(game -> game.getGame().getGameId().equals(id)).findFirst();
    }
    if (target.isPresent()) {
      GameHandler targetHandler = target.get();
      synchronized (targetHandler) {
        targetHandler.getPlayerConnectors().stream()
            // point of optimization, can be parallelized
            .forEach(connector -> removePlayerByGame(connector.getGameId(), connector.getPlayer()));
      }
      gamePool.remove(targetHandler);
      logger.info("Removed game handler with id {}", id);
    }
  }

  /**
   * Add player link to the pool.
   *
   * @param playerConnector The connector object to be linked with a player.
   * @throws NullPlayerConnector On null Player connector.
   * @throws DuplicatePlayerNameException On duplicate player name.
   */
  public static final void addPlayerConnector(AbstractPlayerConnector playerConnector)
      throws NullPlayerConnector, DuplicatePlayerNameException {
    if (playerConnector == null) {
      throw new NullPlayerConnector();
    }
    Optional<AbstractPlayerConnector> found;
    synchronized (playersPool) {
      found =
          playersPool.stream()
              .filter(
                  p ->
                      p.getPlayer()
                          .getPlayerName()
                          .equals(playerConnector.getPlayer().getPlayerName()))
              .findFirst();
    }
    if (found.isPresent()) {
      throw new DuplicatePlayerNameException("Player name already in use");
    }
    // synch is performed by the collection
    playersPool.add(playerConnector);
    logger.info("Added new player connector");
  }

  /**
   * Remove a player connector from the pool. This closes the socket connection.
   *
   * @param gameId The game id reference.
   * @param player The player to remove.
   */
  public static final void removePlayerByGame(UUID gameId, Player player) {
    if (gameId == null || player == null) {
      return;
    }

    Optional<AbstractPlayerConnector> target;

    synchronized (playersPool) {
      target =
          playersPool.stream()
              .filter(
                  connector ->
                      connector.getGameId().equals(gameId) && connector.getPlayer().equals(player))
              .findFirst();
    }
    if (target.isPresent()) {
      AbstractPlayerConnector targetConnector = target.get();
      if (targetConnector.getClass() == PlayerConnectorSocket.class) {
        try {
          PlayerConnectorSocket ps = (PlayerConnectorSocket) targetConnector;
          synchronized (ps.getConnector()) {
            ps.getConnector().close();
          }
        } catch (IOException e) {
          logger.error("Failed to close socket connection", e);
        }
      }
      playersPool.remove(targetConnector);
      logger.info("Removed player {} connector from game {}", player.getPlayerName(), gameId);
    }
  }

  /**
   * Finds a game handler in the game pool by its game id.
   *
   * @param gameId the UUID to search for
   * @return the GameHandler, if found
   * @throws NullGameHandlerInstance If the game handler is null. If game handler with that id is
   *     not found.
   */
  public static GameHandler getGameHandlerByUUID(UUID gameId) throws NullGameHandlerInstance {
    Optional<GameHandler> target;

    synchronized (gamePool) {
      target = gamePool.stream().filter(gh -> gh.getGame().getGameId().equals(gameId)).findFirst();
    }
    if (target.isPresent()) {
      return target.get();
    } else {
      throw new NullGameHandlerInstance();
    }
  }

  /**
   * Player pool getter.
   *
   * @return The actively connected players.
   */
  public static synchronized Set<AbstractPlayerConnector> getPlayersPool() {
    return playersPool;
  }

  /**
   * Game pool getter.
   *
   * @return The actively started games instances.
   */
  public static synchronized Set<GameHandler> getGamePools() {
    return gamePool;
  }

  /**
   * Add a new rmi connector reference.
   *
   * @param id The player id.
   * @param pc The proxy connector.
   */
  public static void addRmiProxyConnector(UUID id, AbstractPlayerConnector pc) {
    rmiConnectorsProxies.put(id, pc);
  }

  /**
   * Retrieve the rmi connector proxy associated to a player id.
   *
   * @return The rmi connector proxy.
   */
  public static AbstractPlayerConnector getRmiProxyConnector(UUID id) {
    return rmiConnectorsProxies.get(id);
  }
}
