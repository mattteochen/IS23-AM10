package it.polimi.is23am10.controller.interfaces;

import it.polimi.is23am10.command.AbstractCommand;
import it.polimi.is23am10.playerconnector.AbstractPlayerConnector;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IServerControllerActionRmi extends Remote {
  /**
   * Execute the client request.
   *
   * @param connector The connector to a player.
   * @param commanD The command to be executed.
   * @throws RemoteException
   *
   */
  void execute(AbstractPlayerConnector connector, AbstractCommand command) throws RemoteException;
}
