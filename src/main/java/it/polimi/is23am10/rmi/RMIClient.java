package it.polimi.is23am10.rmi;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import it.polimi.is23am10.command.StartGameCommand;

public class RMIClient {
  public static void main(String[] args) throws AccessException, RemoteException, NotBoundException {
    Registry registry = LocateRegistry.getRegistry();
    MessengerService server = (MessengerService) registry.lookup("MessengerService");
    
    server.setCommand(new StartGameCommand("pippo", 4));
  }
}
