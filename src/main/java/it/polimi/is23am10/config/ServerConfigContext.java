package it.polimi.is23am10.config;


/**
 * The server config context class definition.
 * 
 */
public class ServerConfigContext {

  /**
   * The server port.
   * 
   */
  private int serverPort;

  /**
   * The max allowed connection for this server instance.
   * This is a momentary max value.
   * 
   */
  private int maxConnection;

  /**
   * The socket SO_KEEPALIVE flag.
   * 
   */
  private boolean keepAlive;

  /**
   * Constructor.
   *
   * @param serverPort     The server port number.
   * @param maxConnections The maximum allowed connections.
   * @param keepAlive      The socket keep alive flag.
   * 
   */
  public ServerConfigContext(int serverPort, int maxConnections, boolean keepAlive) {
    this.serverPort = serverPort;
    this.maxConnection = maxConnections;
    this.keepAlive = keepAlive;
  }

  /**
   * Server port getter.
   *
   * @return The instantiated server port.
   * 
   */
  public int getServerPort() {
    return serverPort;
  }

  /**
   * Max connections getter.
   *
   * @return The maximum allowed connections.
   * 
   */
  public int getMaxConnections() {
    return maxConnection;
  }

  /**
   * Keep alive getter.
   *
   * @return The keep alive flag.
   * 
   */
  public boolean getKeepAlive() {
    return keepAlive;
  }
}
