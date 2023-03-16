package it.polimi.is23am10.controller;

import it.polimi.is23am10.controller.exceptions.NullGameHandlerInstance;
import it.polimi.is23am10.gamehandler.GameHandler;
import it.polimi.is23am10.gamehandler.exceptions.NullPlayerConnector;
import it.polimi.is23am10.playerconnector.PlayerConnector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The server controller class definition.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public final class ServerControllerState {

  /**
   * The logger, an instance of {@link Logger}.
   *
   */
  private static final Logger logger = LogManager.getLogger(ServerControllerState.class);

  /**
   * Active {@link GameHandler} instances.
   *
   */
  private static List<GameHandler> gamePool = Collections.synchronizedList(new ArrayList<>());

  /**
   * Active players connected with their {@link PlayerConnector} instances.
   *
   */
  private static List<PlayerConnector> playersPool
      = Collections.synchronizedList(new ArrayList<>());

  /**
   * Private constructor.
   * 
   */
  private ServerControllerState() {
  }

  /**
   * Add a new game handler to the pool.
   * 
   *
   * @param handler The game handler instance to add.
   * @throws NullGameHandlerInstance.
   *
   */
  public static final void addGameHandler(
      GameHandler handler) throws NullGameHandlerInstance {
    if (handler == null) {
      throw new NullGameHandlerInstance();
    }
    gamePool.add(handler);
    logger.info(
        "Added new game handler with id {}", handler.getGame().getGameId());
  }

  /**
   * Remove a new game handler from the pool.
   * This performs all the clients disconnections.
   * 
   *
   * @param id The game id to remove.
   *
   */
  public static final void removeGameHandlerById(UUID id) {
    if (id == null) {
      return;
    }

    Optional<GameHandler> target = Optional.empty();

    synchronized (gamePool) {
      target = gamePool.stream()
          .filter(game -> game.getGame().getGameId().equals(id))
          .findFirst();
    }
    if (target.isPresent()) {
      // TODO client disconnection
      gamePool.remove(target.get());
      logger.info("Removed game handler with id {}", id);
    }
  }

  /**
   * Add player link to the pool.
   * 
   *
   * @param playerConnector The connector object to be linked with a player.
   * @throws NullPlayerConnector.
   *
   */
  public static final void addPlayerConnector(
      PlayerConnector playerConnector) throws NullPlayerConnector {
    if (playerConnector == null) {
      throw new NullPlayerConnector();
    }
    playersPool.add(playerConnector);
    logger.info("Added new player connector");
  }
}
