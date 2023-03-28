package it.polimi.is23am10.examples;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import it.polimi.is23am10.command.AbstractCommand;
import it.polimi.is23am10.command.StartGameCommand;
import it.polimi.is23am10.config.ServerConfigDefault;
import it.polimi.is23am10.controller.interfaces.IServerControllerActionRMI;

public class RMIClientExample {
  public static void main(String[] args) throws RemoteException, NotBoundException {
    Registry registry = LocateRegistry.getRegistry(ServerConfigDefault.SERVER_RMI_PORT);
    IServerControllerActionRMI server = (IServerControllerActionRMI) registry.lookup("IServerControllerActionRMI");
    AbstractCommand command = new StartGameCommand("Steve", 4);
    System.out.println("Sending....");
    server.execute(null, command);
    System.out.println("Sent the start game command");
  }
}