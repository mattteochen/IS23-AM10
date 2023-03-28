package it.polimi.is23am10.controller.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Optional;

import it.polimi.is23am10.command.AbstractCommand;
import it.polimi.is23am10.playerconnector.PlayerConnector;

public interface IServerControllerActionRMI extends Remote {
	void execute(Optional<PlayerConnector> connector, AbstractCommand command) throws RemoteException;
}
