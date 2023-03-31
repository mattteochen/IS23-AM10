package it.polimi.is23am10.config;

/**
 * The server config context class definition.
 * 
 */
public class ServerConfigContext {

  /**
   * The server socket port.
   * 
   */
  private int serverSocketPort;

  /**
   * The server rmi port.
   * 
   */
  private int serverRmiPort;

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
  public ServerConfigContext(int serverSocketPort, int serverRmiPort, int maxConnections, boolean keepAlive) {
    this.serverSocketPort = serverSocketPort;
    this.serverRmiPort = serverRmiPort;
    this.maxConnection = maxConnections;
    this.keepAlive = keepAlive;
  }

  /**
   * Server socket port getter.
   *
   * @return The instantiated server socket port.
   * 
   */
  public int getServerSocketPort() {
    return serverSocketPort;
  }

  /**
   * Server rmi port getter.
   *
   * @return The instantiated server rmi port.
   * 
   */
  public int getServerRmiPort() {
    return serverRmiPort;
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
