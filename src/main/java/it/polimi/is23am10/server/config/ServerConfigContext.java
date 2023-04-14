package it.polimi.is23am10.server.config;

/**
 * The server config context class definition.
 * 
 */
public class ServerConfigContext {

  /**
   * The server socket port.
   * 
   */
  private Integer serverSocketPort;


  /**
   * The server rmi port.
   * 
   */
  private Integer serverRmiPort;

  /**
   * The max allowed connection for this server instance.
   * This is a momentary max value.
   * 
   */
  private Integer maxConnection;

  /**
   * The socket SO_KEEPALIVE flag.
   * 
   */
  private boolean keepAlive;

  /**
   * The kind of app to launch. If false launches a client.
   * 
   */
  private boolean isServer;

  /**
   * The interface to show if app is launched in Client mode.
   * If false launches client in CLI mode.
   * If {@link ServerConfigContext#isServer} is true this flag is ignored,
   * as server mode only supports CLI as interface, and for logging purpose only.
   */
  private boolean showGUI;

  /**
   * Constructor.
   *
   * @param serverSocketPort     The server port number.
   * @param maxConnections The maximum allowed connections.
   * @param keepAlive      The socket keep alive flag.
   * 
   */
  public ServerConfigContext(Integer serverSocketPort, Integer serverRmiPort,
      Integer maxConnections, boolean keepAlive, boolean isServer, boolean showGUI) {
    this.serverSocketPort = serverSocketPort;
    this.serverRmiPort = serverRmiPort;
    this.maxConnection = maxConnections;
    this.keepAlive = keepAlive;
    this.isServer = isServer;
    this.showGUI = showGUI;
  }


  /**
   * Constructor with default values.
   *
   * 
   */
  public ServerConfigContext() {
    this.serverSocketPort = ServerConfig.getServerSocketPort();
    this.serverRmiPort = ServerConfig.getServerRmiPort();
    this.maxConnection = ServerConfig.getMaxConnections();
    this.keepAlive = ServerConfig.getKeepAlive();
    this.isServer = ServerConfig.getIsServer();
    this.showGUI = ServerConfig.getShowGUI();
  }

  /**
   * Server socket port getter.
   *
   * @return The instantiated server socket port.
   * 
   */
  public Integer getServerSocketPort() {
    return serverSocketPort;
  }

  /**
   * Server rmi port getter.
   *
   * @return The instantiated server rmi port.
   * 
   */
  public Integer getServerRmiPort() {
    return serverRmiPort;
  }

  /**
   * Max connections getter.
   *
   * @return The maximum allowed connections.
   * 
   */
  public Integer getMaxConnections() {
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

  /**
   * Is server getter.
   *
   * @return The isServer flag.
   * 
   */
  public boolean getIsServer() {
    return isServer;
  }

  /**
   * Show GUI getter.
   *
   * @return The show GUI flag.
   * 
   */
  public boolean getShowGUI() {
    return showGUI;
  }
}
