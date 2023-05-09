package it.polimi.is23am10.server.network.playerconnector;

import it.polimi.is23am10.server.model.game.Game;
import it.polimi.is23am10.server.model.player.Player;
import it.polimi.is23am10.server.network.messages.AbstractMessage;
import it.polimi.is23am10.server.network.playerconnector.exceptions.NullBlockingQueueException;
import it.polimi.is23am10.server.network.playerconnector.interfaces.IPlayerConnector;

import java.io.Serializable;
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
@SuppressWarnings({ "checkstyle:nonemptyatclausedescriptioncheck" })
public abstract class AbstractPlayerConnector implements Serializable, IPlayerConnector {

  /**
   * The player inside a game session.
   *
   */
  protected Player player;

  /**
   * The unique {@link Game} id reference.
   *
   */
  protected UUID gameId;

  /**
   * The connector message queue.
   *
   */
  protected BlockingQueue<AbstractMessage> msgQueue;

  /**
   * The client last alarm snooze time in ms truggered by the alarm.
   *
   */
  protected long lastSnoozeMs;

  /**
   * Constructor.
   *
   *
   * @param msgQueue The message queue instance.
   * @throws NullBlockingQueueException If providing a null queue when building player connector.
   *
   */
  protected AbstractPlayerConnector(LinkedBlockingQueue<AbstractMessage> msgQueue)
      throws NullBlockingQueueException {
    if (msgQueue == null) {
      throw new NullBlockingQueueException();
    }
    this.msgQueue = msgQueue;
    this.lastSnoozeMs = System.currentTimeMillis();
  }

  /**
   * {@inheritDoc}
   *
   *
   */
  @Override
  public synchronized UUID getGameId() {
    return gameId;
  }

  /**
   * {@inheritDoc}
   *
   *
   */
  @Override
  public synchronized Player getPlayer() {
    return player;
  }

  /**
   * {@inheritDoc}
   *
   *
   */
  @Override
  public AbstractMessage getMessageFromQueue() throws InterruptedException {
    if (msgQueue.isEmpty()) {
      return null;
    }
    //synch is performed by the collection
    return (msgQueue.take());
  }

  /**
   * {@inheritDoc}
   *
   *
   */
  @Override
  public synchronized int getMsgQueueSize() {
    return msgQueue.size();
  }

  /**
   * Get the client last snooze time in ms.
   *
   * @return The ms of the last alarm snooze.
   *
   */
  public long getLastSnoozeMs() {
    return lastSnoozeMs;
  }

  /**
   * Add a message from the queue.
   * This blocks undefinably until the queue is available.
   * {@link Game} model instances should leverage this
   * queue to send responses to the client (i.e. game updates).
   *
   * @param message The message to be added.
   * @throws InterruptedException On queue message insertion failure.
   *
   */
  public void addMessageToQueue(AbstractMessage message) throws InterruptedException {
    if (message != null) {
      //sync is performed by the collection
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
   * Setter for the player reference.
   *
   * @param player The player to associate to the current player connector.
   *
   */
  public synchronized void setPlayer(Player player) {
    this.player = player;
  }

  /**
   * Set the client last snooze time in ms.
   *
   * @param lastSnoozeMs The snooze ms.
   *
   */
  public void setLastSnoozeMs(long lastSnoozeMs) {
    this.lastSnoozeMs = lastSnoozeMs;
  }

  /**
   * {@inheritDoc}
   *
   */
  public boolean equals(Object obj) {
    if (!(obj instanceof AbstractPlayerConnector)) {
      return false;
    }
    AbstractPlayerConnector casted = (AbstractPlayerConnector) obj;
    return casted.getPlayer().equals(player)
        && casted.getGameId().equals(gameId);
  }

  /**
   * {@inheritDoc}
   *
   */
  public int hashCode() {
    return player.hashCode() * gameId.hashCode();
  }
}
