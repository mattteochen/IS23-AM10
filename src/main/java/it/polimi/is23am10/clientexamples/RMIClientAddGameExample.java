package it.polimi.is23am10.clientexamples;

import it.polimi.is23am10.command.AbstractCommand;
import it.polimi.is23am10.command.StartGameCommand;
import it.polimi.is23am10.config.ServerConfigDefault;
import it.polimi.is23am10.controller.interfaces.IServerControllerActionRmi;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This an example class.
 *
 */
public class RMIClientAddGameExample {
  /**
   * Main method.
   *
   * @throws RemoteException
   * @throws NotBoundException
   *
   */
  public static void main(String[] args) throws RemoteException, NotBoundException {
    final Logger logger = LogManager.getLogger(RMIClientAddGameExample.class);
    Registry registry = LocateRegistry.getRegistry(ServerConfigDefault.SERVER_RMI_PORT);
    IServerControllerActionRmi server =
        (IServerControllerActionRmi) registry.lookup("IServerControllerActionRmi");
    AbstractCommand command = new StartGameCommand("Steve", 4);
    logger.info("Sending request to RMI Server {}", command.getOpcode());
    server.execute(null, command);
    logger.info("Operation ended");
  }
}