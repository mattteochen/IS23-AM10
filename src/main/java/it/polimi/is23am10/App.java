package it.polimi.is23am10;

import it.polimi.is23am10.client.Client;
import it.polimi.is23am10.client.RMIClient;
import it.polimi.is23am10.client.SocketClient;
import it.polimi.is23am10.client.userinterface.CommandLineInterface;
import it.polimi.is23am10.client.userinterface.GraphicUserInterface;
import it.polimi.is23am10.client.userinterface.UserInterface;
import it.polimi.is23am10.server.Server;
import it.polimi.is23am10.server.config.ServerConfigContext;
import it.polimi.is23am10.server.config.exceptions.InvalidMaxConnectionsNumberException;
import it.polimi.is23am10.server.config.exceptions.InvalidPortNumberException;
import it.polimi.is23am10.server.controller.ServerControllerAction;
import it.polimi.is23am10.server.network.playerconnector.AbstractPlayerConnector;
import it.polimi.is23am10.server.network.playerconnector.PlayerConnectorRmi;
import it.polimi.is23am10.server.network.playerconnector.PlayerConnectorSocket;
import it.polimi.is23am10.server.network.playerconnector.exceptions.NullBlockingQueueException;
import it.polimi.is23am10.server.network.playerconnector.exceptions.NullSocketConnectorException;
import it.polimi.is23am10.utils.ArgParser;
import it.polimi.is23am10.utils.exceptions.InvalidArgumentException;
import it.polimi.is23am10.utils.exceptions.MissingParameterException;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The entrypoint for the app. Parses args and launches
 * either client or server with desired options.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public class App {

  /**
   * Logger instance.
   *
   */
  protected final static Logger logger = LogManager.getLogger(App.class);

  public static void main(String[] args) {
    try {
      ArgParser.parse(args);
      ServerConfigContext ctx = new ServerConfigContext();

      if (ctx.getIsServer()) {
        // SERVER MODE
        Server server = new Server(new ServerSocket(ctx.getServerSocketPort()),
            Executors.newFixedThreadPool(ctx.getMaxConnections()), new ServerControllerAction(),
            LocateRegistry.createRegistry(ctx.getServerRmiPort()));

        server.start(ctx);
      } else {
        // CLIENT MODE
        UserInterface userInterface = ctx.getShowGUI() ? new GraphicUserInterface() : new CommandLineInterface();
        Client client;
        if (ctx.getUseRMI()) {
          Registry registry = LocateRegistry.getRegistry(ctx.getServerRmiPort());
          // TODO: Lookup for server controller action. Evaluate possible passing it over
          PlayerConnectorRmi playerConnector = new PlayerConnectorRmi(new LinkedBlockingQueue<>());
          client = new RMIClient(playerConnector, userInterface);
        } else {
          Socket socket = new Socket(ctx.getServerAddress(), ctx.getServerSocketPort());
          PlayerConnectorSocket playerConnector = new PlayerConnectorSocket(socket, new LinkedBlockingQueue<>());
          client = new SocketClient(playerConnector, userInterface);
        }
        // TODO: run client
      }
    } catch (NumberFormatException | InvalidArgumentException | MissingParameterException | InvalidPortNumberException
        | InvalidMaxConnectionsNumberException e) {
      logger.error("Cannot parse CLI arguments.", e);
    } catch (IOException e) {
      logger.error("Cannot launch server.", e);
    } catch (NullBlockingQueueException | NullSocketConnectorException e) {
      logger.error("Cannot launch client.", e);
    }
  }
}
