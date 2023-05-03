package it.polimi.is23am10.server.network.playerconnector.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.UUID;

import it.polimi.is23am10.server.model.player.Player;
import it.polimi.is23am10.server.network.messages.AbstractMessage;

/**
 * The player connector interface definition.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public interface IPlayerConnector extends Remote {

  /**
   * Getter for the associated player.
   *
   * @return The player reference.
   *
   */
  Player getPlayer() throws RemoteException;

  /**
   * Setter for the associated player.
   *
   * @param p The player to be set.
   *
   */
  void setPlayer(Player p) throws RemoteException;

  /**
   * Getter for the associated game id.
   *
   * @return The game id.
   *
   */
  UUID getGameId() throws RemoteException;

  /**
   * Retrieve the message queue size.
   *
   * @return The blocking message queue size.
   * @throws InterruptedException
   *
   */
  int getMsgQueueSize() throws RemoteException;

  /**
   * Retrieve a message from the queue.
   * This deletes the retrieved message.
   *
   * @return The oldest message if present.
   * @throws InterruptedException
   *
   */
  AbstractMessage getMessageFromQueue() throws RemoteException, InterruptedException;
}
