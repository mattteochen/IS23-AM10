package it.polimi.is23am10.server;

import it.polimi.is23am10.server.controller.ClientConnectionChecker;
import it.polimi.is23am10.server.controller.ServerControllerAction;
import it.polimi.is23am10.server.controller.ServerControllerRmiBindings;
import it.polimi.is23am10.server.controller.ServerControllerSocket;
import it.polimi.is23am10.server.controller.interfaces.IServerControllerAction;
import it.polimi.is23am10.server.network.playerconnector.PlayerConnectorSocket;
import it.polimi.is23am10.server.network.playerconnector.exceptions.NullBlockingQueueException;
import it.polimi.is23am10.server.network.playerconnector.exceptions.NullSocketConnectorException;
import it.polimi.is23am10.utils.config.AppConfigContext;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The Server entry point class definition.
 * This has only static methods and this class should not be instantiated.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public class Server {

  /**
   * The serverSocket status.
   *
   */
  public enum ServerStatus {
    STARTED,
    STOPPED
  }

  /**
   * Logger instance.
   *
   */
  protected final Logger logger = LogManager.getLogger(Server.class);

  /**
   * Socket serverSocket instance.
   *
   */
  protected ServerSocket serverSocket;

  /**
   * Socket socket clients connected.
   *
   */
  protected static int socketClientsConnected = 0;

  /**
   * Server thread executor service manager.
   *
   */
  protected ExecutorService executorService;

  /**
   * Constructor.
   *
   * @param serverSocket                    The server socket reference of a newly
   *                                        connected client.
   * @param executorService                 The built thread executor service.
   * @param rmiServerControllerActionServer An built instance of the implementing
   *                                        class.
   * @param rmiRegistry                     A built instance of the RMI registry.
   * @throws RemoteException On rebind failure.
   *
   */
  public Server(ServerSocket serverSocket, ExecutorService executorService,
      IServerControllerAction rmiServerControllerActionServer, Registry rmiRegistry) throws RemoteException {
    this.executorService = executorService;
    this.serverSocket = serverSocket;
    ServerControllerRmiBindings.setRmiRegistry(rmiRegistry);
    ServerControllerRmiBindings.rebindServerControllerAction(rmiServerControllerActionServer);
  }

  /**
   * Server entry point.
   * A new {@link ServerSocket} instance is spawned and in a
   * infinity loop listens for clients connections.
   *
   * @param ctx An instance of the server configuration.
   *
   */
  public void start(AppConfigContext ctx) {
    logger.info("Starting Spurious Dragon, try to kill me...");
    // https://www.youtube.com/watch?v=Jo6fKboqfMs&ab_channel=memesammler

    executorService.execute(new ClientConnectionChecker(ctx.getMaxInactivityTime()));

    // start the socket server
    while (!serverSocket.isClosed()) {
      try {
        if (getSocketClientsConnected() < ctx.getMaxConnections()) {
          Socket client = serverSocket.accept();
          client.setKeepAlive(ctx.getKeepAlive());
          setSocketClientConnected(getSocketClientsConnected() + 1);
          executorService.execute(new ServerControllerSocket(
            new PlayerConnectorSocket(client,
            new LinkedBlockingQueue<>()),
            new ServerControllerAction()));
          logger.info("Received new connection " + "(" + getSocketClientsConnected() + "/" + ctx.getMaxConnections() + ")" );
        } else {
          serverSocket.accept();
          logger.error("Socket connection cannot be established as the server has reached its maximum socket client connections capacity.");
        }
      } catch (IOException | NullSocketConnectorException | NullBlockingQueueException e) {
        logger.error("Failed to process connection", e);
      }
    }
  }

  /**
   * Stop the serverSocket.
   *
   * @throws IOException On socket closure failing.
   *
   */
  public void stop() throws IOException {
    if (serverSocket != null && !serverSocket.isClosed()) {
      serverSocket.close();
    }
  }

  /**
   * Check the current serverSocket status.
   *
   * @return The an enum {@link ServerStatus} stating the current status.
   *
   */
  public ServerStatus status() {
    return serverSocket == null || serverSocket.isClosed()
        ? ServerStatus.STOPPED
        : ServerStatus.STARTED;
  }

  /**
   * Get the current number of clients connected to the socket.
   *
   * @return The connect clients number.
   *
   */
  public static int getSocketClientsConnected(){
    return socketClientsConnected;
  }

  /**
   * Set the current number of clients connected to the socket.
   *
   * @param scc The connect clients number.
   *
   */
  public static void setSocketClientConnected(int scc){
    socketClientsConnected = scc;
  }
}
