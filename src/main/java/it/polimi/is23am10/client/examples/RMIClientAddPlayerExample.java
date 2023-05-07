package it.polimi.is23am10.client.examples;

import it.polimi.is23am10.server.command.AbstractCommand;
import it.polimi.is23am10.server.command.AddPlayerCommand;
import it.polimi.is23am10.server.controller.interfaces.IServerControllerAction;
import it.polimi.is23am10.server.network.playerconnector.PlayerConnectorRmi;
import it.polimi.is23am10.server.network.playerconnector.exceptions.NullBlockingQueueException;
import it.polimi.is23am10.server.network.playerconnector.exceptions.NullSocketConnectorException;
import it.polimi.is23am10.utils.config.AppConfig;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingQueue;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This an example class.
 *
 */
public class RMIClientAddPlayerExample {
  /**
   * Main method.
   *
   * @throws RemoteException On lookup failure.
   * @throws NotBoundException On binding failure.
   * @throws NullBlockingQueueException If providing a null queue when building player connector.
   *
   */
  public static void main(String[] args)
      throws RemoteException, NotBoundException, NullBlockingQueueException {
    final Logger logger = LogManager.getLogger(RMIClientAddPlayerExample.class);
    Registry registry = LocateRegistry.getRegistry(AppConfig.getServerRmiPort());
    IServerControllerAction server = (IServerControllerAction) registry.lookup("IServerControllerAction");
    AbstractCommand command = new AddPlayerCommand("Bob", UUID.fromString(""));
    logger.info("Sending request to RMI Server {}", command.getOpcode());
    server.execute(new PlayerConnectorRmi(new LinkedBlockingQueue<>()), command);
    logger.info("Operation ended");
  }
}