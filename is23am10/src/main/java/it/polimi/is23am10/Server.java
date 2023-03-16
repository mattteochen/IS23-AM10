package it.polimi.is23am10;

import it.polimi.is23am10.config.ServerConfig;
import it.polimi.is23am10.controller.ServerController;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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
  protected Logger logger;

  /**
   * Socket serverSocket instance.
   *
   */
  protected ServerSocket serverSocket;

  /**
   * Server thread executor service manager.
   *
   */
  protected ExecutorService threadPool; 

  /**
   * Constructor.
   *
   */
  public Server() {
    logger = LogManager.getLogger(Server.class);
    threadPool = Executors.newFixedThreadPool(
      ServerConfig.MAX_CLIENT_CONNECTION);
  }

  /**
   * Initialized the server socket instance.
   * @throws IOException
   *
   */
  protected void initServerSocket() throws IOException {
    serverSocket = new ServerSocket(ServerConfig.SERVER_PORT);
  }

  /**
   * Server entry point.
   * A new {@link ServerSocket} instance is spawned and in a
   * infinity loop listens for clients connections.
   * @throws IOException
   *
   */
  public void start() throws IOException {
    logger.info("Starting Spurious Dragon, try to kill me...");
    // instantiate the thread pools executor service.
    // https://www.youtube.com/watch?v=Jo6fKboqfMs&ab_channel=memesammler
    initServerSocket();
    while (!serverSocket.isClosed()) {
      try {
        Socket client = serverSocket.accept();
        logger.info("Received new connection");
        threadPool.execute(new ServerController(client));
      } catch (IOException e) {
        logger.error("Failed to accept connection", e);
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
    return serverSocket == null || serverSocket.isClosed() ? ServerStatus.STOPPED :
        ServerStatus.STARTED;
  }
}
