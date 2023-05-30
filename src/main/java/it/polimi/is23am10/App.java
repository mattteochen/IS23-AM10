package it.polimi.is23am10;

import it.polimi.is23am10.client.Client;
import it.polimi.is23am10.client.RMIClient;
import it.polimi.is23am10.client.SocketClient;
import it.polimi.is23am10.client.userinterface.CommandLineInterface;
import it.polimi.is23am10.client.userinterface.GraphicUserInterface;
import it.polimi.is23am10.client.userinterface.UserInterface;
import it.polimi.is23am10.server.Server;
import it.polimi.is23am10.server.controller.ServerControllerAction;
import it.polimi.is23am10.server.controller.interfaces.IServerControllerAction;
import it.polimi.is23am10.server.network.messages.ErrorMessage;
import it.polimi.is23am10.server.network.messages.ErrorMessage.ErrorSeverity;
import it.polimi.is23am10.server.network.playerconnector.PlayerConnectorRmi;
import it.polimi.is23am10.server.network.playerconnector.PlayerConnectorSocket;
import it.polimi.is23am10.server.network.playerconnector.exceptions.NullBlockingQueueException;
import it.polimi.is23am10.server.network.playerconnector.exceptions.NullSocketConnectorException;
import it.polimi.is23am10.utils.ArgParser;
import it.polimi.is23am10.utils.config.AppConfigContext;
import it.polimi.is23am10.utils.config.exceptions.InvalidMaxConnectionsNumberException;
import it.polimi.is23am10.utils.config.exceptions.InvalidPortNumberException;
import it.polimi.is23am10.utils.exceptions.InvalidArgumentException;
import it.polimi.is23am10.utils.exceptions.MissingParameterException;

import java.io.IOException;
import java.net.ConnectException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
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

  /**
   * The main function. Entrypoint for both server and client.
   * 
   * @param args CLI arguments to parse.
   */
  public static void main(String[] args) {
    try {
      ArgParser.parse(args);
      AppConfigContext ctx = new AppConfigContext();
      if (ctx.getIsServer()) {
        // SERVER MODE
        Server server = new Server(new ServerSocket(ctx.getServerSocketPort()),
            Executors.newFixedThreadPool(ctx.getMaxConnections()), new ServerControllerAction(),
            LocateRegistry.createRegistry(ctx.getServerRmiPort()));

        server.start(ctx);
      } else {
        // CLIENT MODE
        UserInterface userInterface = ctx.getShowGUI() ? new GraphicUserInterface() : new CommandLineInterface(ctx.getShowDebug());
        Client client = null;
        if (ctx.getUseRMI()) {
          try {
            Registry registry = LocateRegistry.getRegistry(ctx.getServerRmiPort());
            PlayerConnectorRmi playerConnector = new PlayerConnectorRmi(new LinkedBlockingQueue<>(), client);
            IServerControllerAction serverControllerActionServerRef = (IServerControllerAction) registry
                .lookup(IServerControllerAction.class.getName());
            client = new RMIClient(playerConnector, userInterface, serverControllerActionServerRef, registry);
            playerConnector.setClient(client);
          } catch (NotBoundException e) {
            userInterface.displayError(new ErrorMessage("Server not found. Shutting down client...", ErrorSeverity.CRITICAL));
            return;
          } catch(NullBlockingQueueException e) {
            userInterface.displayError(new ErrorMessage("Client module error. Shutting down", ErrorSeverity.CRITICAL));
            return;
          } catch (IOException e) {
            logger.error("Cannot connect to server. Verify address and retry.");
            return;
          }
        } else {
          try {
            Socket socket = new Socket(ctx.getServerAddress(), ctx.getServerSocketPort());
            PlayerConnectorSocket playerConnector = new PlayerConnectorSocket(socket, new LinkedBlockingQueue<>());
            client = new SocketClient(playerConnector, userInterface);
          } catch (UnknownHostException e) {
            userInterface.displayError(new ErrorMessage("Server not found. Shutting down client...", ErrorSeverity.CRITICAL));
            return;
          } catch (NullBlockingQueueException | NullSocketConnectorException e) {
            userInterface.displayError(new ErrorMessage("Client module error. Shutting down", ErrorSeverity.CRITICAL));
            return;
          }
        }
        client.run();
      }
    } catch (NumberFormatException | InvalidArgumentException | MissingParameterException | InvalidPortNumberException
        | InvalidMaxConnectionsNumberException e) {
      logger.error("Cannot parse CLI arguments.", e);
    } catch (ConnectException e) {
      logger.error("Cannot connect to server. Verify address and retry.");
    } catch (IOException e) {
      logger.error("Cannot launch server.", e);
    }
  }
}
