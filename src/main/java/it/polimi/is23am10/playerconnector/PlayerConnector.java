package it.polimi.is23am10.playerconnector;

import it.polimi.is23am10.game.Game;
import it.polimi.is23am10.playerconnector.exceptions.NullBlockingQueueException;
import it.polimi.is23am10.playerconnector.exceptions.NullSocketConnectorException;
import java.net.Socket;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * The player connector class definition.
 * This class is responsible to handle clients' sockets connections.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
@SuppressWarnings({"checkstyle:nonemptyatclausedescriptioncheck"})
public class PlayerConnector {

  /**
   * The player name inside a game session.
   *
   */
  private String playerName;

  /**
   * The unique {@link Game} id reference.
   *
   */
  private UUID gameId;

  /**
   * The socket connection reference.
   *
   */
  private Socket connector;

  /**
   * The connector message queue.
   *
   */
  private BlockingQueue<Game> msgQueue;

  /**
   * Constructor.
   *
   *
   * @param connector The {@link Socket} instance linked to a player client.
   * @throws NullSocketConnectorException
   * @throws NullBlockingQueueException
   *
   */
  public PlayerConnector(Socket connector, LinkedBlockingQueue<Game> msgQueue)
      throws NullSocketConnectorException, NullBlockingQueueException {
    if (connector == null) {
      throw new NullSocketConnectorException();
    }
    if (msgQueue == null) {
      throw new NullBlockingQueueException();
    }
    this.connector = connector;
    this.msgQueue = msgQueue;
  }

  /**
   * Getter for {@link Socket}, the low level connector.
   *
   * @return The socket connector.
   *
   */
  public synchronized Socket getConnector() {
    return connector;
  }

  /**
   * Getter for the associated game id.
   *
   * @return The game id.
   *
   */
  public synchronized UUID getGameId() {
    return gameId;
  }

  /**
   * Getter for the associated player name.
   *
   * @return The player name as string.
   *
   */
  public synchronized String getPlayerName() {
    return playerName;
  }

  /**
   * Retrieve a message from the queue.
   * This deletes the retrieved message.
   *
   * @return The oldest message if present.
   * @throws InterruptedException
   *
   */
  public Optional<Game> getMessageFromQueue() throws InterruptedException {
    if (msgQueue.isEmpty()) {
      return Optional.empty();
    }
    return Optional.of(msgQueue.take());
  }

  /**
   * Retrieve the message queue size.
   *
   * @return The blocking message queue size.
   * @throws InterruptedException
   *
   */
  public int getMsgQueueSize() {
    return msgQueue.size();
  }

  /**
   * Add a message from the queue.
   * This blocks undefinably until the queue is available.
   * {@link Game} model instances should leverage this
   * queue to send responses to the client (i.e. game updates).
   *
   * @param message The message to be added.
   * @throws InterruptedException
   *
   */
  public void addMessageToQueue(Game message) throws InterruptedException {
    if (message != null) {
      msgQueue.put(message);
    }
  }

  /**
   * Setter for the associated game id.
   *
   * @param gameId The game id to associate to the current player connector.
   *
   */
  public synchronized void setGameId(UUID gameId) {
    this.gameId = gameId;
  }

  /**
   * Setter for the player name.
   *
   * @param playerName The player name to associate to the current player connector.
   *
   */
  public synchronized void setPlayerName(String playerName) {
    this.playerName = playerName;
  }
}
