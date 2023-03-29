package it.polimi.is23am10.controller;

import it.polimi.is23am10.controller.exceptions.NullGameHandlerInstance;
import it.polimi.is23am10.gamehandler.GameHandler;
import it.polimi.is23am10.gamehandler.exceptions.NullPlayerConnector;
import it.polimi.is23am10.playerconnector.AbstractPlayerConnector;
import it.polimi.is23am10.playerconnector.PlayerConnector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The server controller state class definition.
 * This should be a singleton-like instance owning
 * the server current state.
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
   * Active players connected with their {@link AbstractPlayerConnector} instances.
   *
   */
  private static List<AbstractPlayerConnector> playersPool
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
   * Remove a game handler from the pool.
   * This performs all the clients disconnections.
   *
   * @param id The game id to remove.
   *
   */
  public static final void removeGameHandlerById(UUID id) {
    if (id == null) {
      return;
    }

    Optional<GameHandler> target;

    synchronized (gamePool) {
      target = gamePool.stream()
          .filter(game -> game.getGame().getGameId().equals(id))
          .findFirst();
    }
    if (target.isPresent()) {
      GameHandler targetHandler = target.get();
      targetHandler.getPlayerConnectors()
          .stream()
          // point of optimization, can be parallelized
          .forEach(connector ->
              removePlayerByGameAndName(connector.getGameId(), connector.getPlayerName()));
      gamePool.remove(targetHandler);
      logger.info("Removed game handler with id {}", id);
    }
  }

  /**
   * Add player link to the pool.
   *
   * @param playerConnector The connector object to be linked with a player.
   * @throws NullPlayerConnector.
   *
   */
  public static final void addPlayerConnector(
      AbstractPlayerConnector playerConnector) throws NullPlayerConnector {
    if (playerConnector == null) {
      throw new NullPlayerConnector();
    }
    playersPool.add(playerConnector);
    logger.info("Added new player connector");
  }

  /**
   * Remove a player connector from the pool.
   * This closes the socket connection.
   *
   * @param gameId     The game id reference.
   * @param playerName The playerName.
   * @throws IOException
   *
   */
  public static final void removePlayerByGameAndName(UUID gameId, String playerName) {
    if (gameId == null || playerName == null) {
      return;
    }

    Optional<AbstractPlayerConnector> target;

    synchronized (playersPool) {
      target = playersPool.stream()
          .filter(connector ->
              connector.getGameId().equals(gameId) && connector.getPlayerName().equals(playerName))
          .findFirst();
    }
    if (target.isPresent()) {
      AbstractPlayerConnector targetConnector = target.get();
      if (targetConnector instanceof PlayerConnector) {
        try {
          ((PlayerConnector) targetConnector).getConnector().close();
        } catch (IOException e) {
          logger.error("Failed to close socket connection", e);
        }
      }
      playersPool.remove(targetConnector);
      logger.info("Removed player connector from game {} with name {}", gameId, playerName);
    }
  }

  /**
   * Finds a game handler in the gamepool by its game id.
   * 
   * @param gameId the UUID to search for
   * @return the GameHandler, if found
   * @throws NullGameHandlerInstance
   */
  public static GameHandler getGameHandlerByUUID(UUID gameId) throws NullGameHandlerInstance{
    Optional<GameHandler> target;

    synchronized (gamePool) {
      target = gamePool.stream()
        .filter(gh -> 
            gh.getGame().getGameId().equals(gameId))
        .findFirst();
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
   *
   */
  public static synchronized List<AbstractPlayerConnector> getPlayersPool() {
    return playersPool;
  }

  /**
   * Game pool getter.
   *
   * @return The actively started games instances.
   *
   */
  public static synchronized List<GameHandler> getGamePools() {
    return gamePool;
  }
}
