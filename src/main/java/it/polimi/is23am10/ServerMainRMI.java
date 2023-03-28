package it.polimi.is23am10;

import it.polimi.is23am10.config.ServerConfigDefault;
import it.polimi.is23am10.controller.ServerControllerActionRMIImpl;
import it.polimi.is23am10.controller.interfaces.IServerControllerActionRMI;

import java.io.IOException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * The server main class definition.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public final class ServerMainRMI {

  /**
   * The main method.
   *
   */
  public static void main(String[] args) throws IOException {
    IServerControllerActionRMI server = new ServerControllerActionRMIImpl();
    IServerControllerActionRMI stub = (IServerControllerActionRMI) UnicastRemoteObject.exportObject(server, 0);
    Registry registry = LocateRegistry.createRegistry(ServerConfigDefault.SERVER_RMI_PORT);
    registry.rebind("IServerControllerActionRMI", stub);
  }
}
