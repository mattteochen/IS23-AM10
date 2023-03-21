package it.polimi.is23am10;

import it.polimi.is23am10.config.ServerConfigContext;
import it.polimi.is23am10.controller.ServerController;
import it.polimi.is23am10.controller.ServerControllerAction;
import it.polimi.is23am10.playerconnector.PlayerConnector;
import it.polimi.is23am10.playerconnector.exceptions.NullBlockingQueueException;
import it.polimi.is23am10.playerconnector.exceptions.NullSocketConnectorException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
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
   * Server thread executor service manager.
   *
   */
  protected ExecutorService executorService;

  /**
   * Constructor.
   *
   */
  public Server(ServerSocket serverSocket, ExecutorService executorService) {
    this.executorService = executorService;
    this.serverSocket = serverSocket;
  }

  /**
   * Server entry point.
   * A new {@link ServerSocket} instance is spawned and in a
   * infinity loop listens for clients connections.
   *
   */
  public void start(ServerConfigContext ctx) {
    logger.info("Starting Spurious Dragon, try to kill me...");
    // https://www.youtube.com/watch?v=Jo6fKboqfMs&ab_channel=memesammler
    while (!serverSocket.isClosed()) {
      try {
        Socket client = serverSocket.accept();
        client.setKeepAlive(ctx.getKeepAlive());
        logger.info("Received new connection");
        executorService.execute(new ServerController(
            new PlayerConnector(client,
                new LinkedBlockingQueue<>()),
            new ServerControllerAction()));
      } catch (IOException | NullSocketConnectorException | NullBlockingQueueException e) {
        logger.error("Failed to process connection", e);
      }
    }
  }

  /**
   * Stop the serverSocket.
   *
   * @throws IOException
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
}
