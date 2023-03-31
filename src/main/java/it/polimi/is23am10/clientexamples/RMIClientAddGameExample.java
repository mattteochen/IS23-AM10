package it.polimi.is23am10.clientexamples;

import it.polimi.is23am10.command.AbstractCommand;
import it.polimi.is23am10.command.StartGameCommand;
import it.polimi.is23am10.config.ServerConfig;
import it.polimi.is23am10.controller.interfaces.IServerControllerAction;
import it.polimi.is23am10.playerconnector.PlayerConnectorRmi;
import it.polimi.is23am10.playerconnector.exceptions.NullBlockingQueueException;
import it.polimi.is23am10.playerconnector.exceptions.NullSocketConnectorException;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.concurrent.LinkedBlockingQueue;

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
   * @throws NullBlockingQueueException
   * @throws NullSocketConnectorException
   *
   */
  public static void main(String[] args) throws RemoteException, NotBoundException, NullSocketConnectorException, NullBlockingQueueException {
    final Logger logger = LogManager.getLogger(RMIClientAddGameExample.class);
    Registry registry = LocateRegistry.getRegistry(ServerConfig.getServerRmiPort());
    IServerControllerAction server =
        (IServerControllerAction) registry.lookup("IServerControllerAction");
    AbstractCommand command = new StartGameCommand("Steve", 4);
    logger.info("Sending request to RMI Server {}", command.getOpcode());
    server.execute(new PlayerConnectorRmi(new LinkedBlockingQueue<>()), command);
    logger.info("Operation ended");
  }
}