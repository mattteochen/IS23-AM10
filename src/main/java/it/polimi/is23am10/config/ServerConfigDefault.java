package it.polimi.is23am10.config;

/**
 * The server default config class definition.
 * All the default configuration for the server reside here.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public final class ServerConfigDefault {

  /**
   * Private constructor.
   * 
   */
  private ServerConfigDefault() {

  }

  /**
   * The server config server port.
   * 
   */
  private static Integer serverPort = 9001;

  /**
   * The max allowed connection for this server instance.
   * This is a momentary max value.
   * 
   */
  private static Integer maxConnection = 8;

  /**
   * The socket SO_KEEPALIVE flag.
   * 
   */
  private static boolean keepAlive = true;

  /**
   * Server port setter.
   *
   * 
   */
  public static void setServerPort(Integer p) {
    serverPort = p;
  }

  /**
   * Max connections setter.
   *
   * 
   */
  public static void setMaxConnections(Integer maxConn) {
    maxConnection = maxConn;
  }

  /**
   * Keep alive setter.
   *
   * 
   */
  public static void setKeepAlive(boolean k) {
    keepAlive = k;
  }

  /**
   * Server port getter.
   *
   * @return The instantiated server port.
   * 
   */
  public static Integer getServerPort() {
    return serverPort;
  }

  /**
   * Max connections getter.
   *
   * @return The maximum allowed connections.
   * 
   */
  public static Integer getMaxConnections() {
    return maxConnection;
  }

  /**
   * Keep alive getter.
   *
   * @return The keep alive flag.
   * 
   */
  public static boolean getKeepAlive() {
    return keepAlive;
  }
}
