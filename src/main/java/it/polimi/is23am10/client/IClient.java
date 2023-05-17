package it.polimi.is23am10.client;

import java.rmi.Remote;
import java.rmi.RemoteException;

import it.polimi.is23am10.server.network.messages.AbstractMessage;

/**
 * The <code>IClient</code> interface represents the remote interface for clients in a Java RMI application.
 * It defines methods that the server can call on the client.
 */
public interface IClient extends Remote {
  /**
   * Displays a server message on the client.
   *
   * @param msg The server message to be displayed on the client.
   * @throws RemoteException If a communication-related exception occurs during the remote method invocation.
   */
  void showServerMessage(AbstractMessage msg) throws RemoteException;
}
