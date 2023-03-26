package it.polimi.is23am10.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

import it.polimi.is23am10.command.AbstractCommand;

public interface MessengerService extends Remote {

  AbstractCommand command = null;

  String sendMessage(String clientMessage) throws RemoteException;

  AbstractCommand getCommand() throws RemoteException;

  void setStartGameCommand(String playerName, Integer numPlayers) throws RemoteException;
}
