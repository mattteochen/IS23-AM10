package it.polimi.is23am10.controller.interfaces;

import it.polimi.is23am10.command.AbstractCommand;
import it.polimi.is23am10.playerconnector.PlayerConnector;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IServerControllerActionRmi extends Remote {
  void execute(PlayerConnector connector, AbstractCommand command) throws RemoteException;
}
