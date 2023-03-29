package it.polimi.is23am10.playerconnector;

import it.polimi.is23am10.game.Game;
import it.polimi.is23am10.gamehandler.GameHandler;
import it.polimi.is23am10.playerconnector.exceptions.NullBlockingQueueException;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.BaseStream;
import java.util.stream.Stream;

/**
 * The player connector class definition.
 * This class is responsible to handle clients' sockets connections.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
@SuppressWarnings({ "checkstyle:nonemptyatclausedescriptioncheck" })
public abstract class AbstractPlayerConnector {

  /**
   * The player name inside a game session.
   *
   */
  protected String playerName;

  /**
   * The unique {@link Game} id reference.
   *
   */
  protected UUID gameId;

  /**
   * The connector message queue.
   *
   */
  protected BlockingQueue<Game> msgQueue;

  /**
   * Constructor.
   *
   *
   * @param msgQueue The message queue instance.
   * @throws NullBlockingQueueException
   *
   */
  protected AbstractPlayerConnector(LinkedBlockingQueue<Game> msgQueue)
      throws NullBlockingQueueException {
    if (msgQueue == null) {
      throw new NullBlockingQueueException();
    }
    this.msgQueue = msgQueue;
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
   * @param playerName The player name to associate to the current player
   *                   connector.
   *
   */
  public synchronized void setPlayerName(String playerName) {
    this.playerName = playerName;
  }
}
