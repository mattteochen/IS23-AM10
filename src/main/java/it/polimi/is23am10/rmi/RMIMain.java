package it.polimi.is23am10.rmi;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RMIMain {
  public static void main(String[] args) throws RemoteException {
    MessengerService server = new MessengerServiceImpl();
    MessengerService stub = (MessengerService) UnicastRemoteObject.exportObject((MessengerService) server, 0);
    Registry registry = LocateRegistry.createRegistry(1099);
    registry.rebind("MessengerService", stub);
  }
}
