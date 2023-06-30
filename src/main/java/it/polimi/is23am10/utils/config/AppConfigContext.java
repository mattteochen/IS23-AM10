package it.polimi.is23am10.utils.config;

/** The server config context class definition. */
public class AppConfigContext {

  /** The server socket port. */
  private Integer serverSocketPort;

  /** The server rmi port. */
  private Integer serverRmiPort;

  /** The max allowed connection for this server instance. This is a momentary max value. */
  private Integer maxConnection;

  /** The socket SO_KEEPALIVE flag. */
  private boolean keepAlive;

  /** The kind of app to launch. If false launches a client. */
  private boolean isServer;

  /**
   * The interface to show if app is launched in Client mode. If false launches client in CLI mode.
   * If {@link AppConfigContext#isServer} is true this flag is ignored, as server mode only supports
   * CLI as interface, and for logging purpose only.
   */
  private boolean showGUI;

  /**
   * The communication method to use if app is launched is Client Mode. If false launches client
   * over Socket connection. If {@link AppConfigContext#isServer} is true this flag is ignored, as
   * server mode both receives RMI and socket connections
   */
  private boolean useRMI;

  /** The network address where the server is running. */
  private String serverAddress;

  /** A simple flag that if enabled shows all verbose messages in console logs. */
  private boolean showDebug;

  /** The maximum inactivity time allowed to a player when playing a game turn. */
  private long maxInactivityTimeMs;

  /**
   * Constructor.
   *
   * @param serverSocketPort The server socket port number.
   * @param serverRmiPort The server rmi port number.
   * @param maxConnections The maximum allowed connections.
   * @param keepAlive The socket keep alive flag.
   * @param isServer Run app in server mode flag.
   * @param showGUI Show GUI client flag.
   * @param useRMI Use RMI over socket flag.
   * @param serverAddress Address where to find server.
   * @param showDebug Show debug infos flag.
   * @param maxInactivityTimeMs Milliseconds to wait for inactive players before disconnecting.
   */
  public AppConfigContext(
      Integer serverSocketPort,
      Integer serverRmiPort,
      Integer maxConnections,
      boolean keepAlive,
      boolean isServer,
      boolean showGUI,
      boolean useRMI,
      String serverAddress,
      boolean showDebug,
      long maxInactivityTimeMs) {
    this.serverSocketPort = serverSocketPort;
    this.serverRmiPort = serverRmiPort;
    this.maxConnection = maxConnections;
    this.keepAlive = keepAlive;
    this.isServer = isServer;
    this.showGUI = showGUI;
    this.useRMI = useRMI;
    this.serverAddress = serverAddress;
    this.showDebug = showDebug;
    this.maxInactivityTimeMs = maxInactivityTimeMs;
  }

  /** Constructor with default values. */
  public AppConfigContext() {
    this.serverSocketPort = AppConfig.getServerSocketPort();
    this.serverRmiPort = AppConfig.getServerRmiPort();
    this.maxConnection = AppConfig.getMaxConnections();
    this.keepAlive = AppConfig.getKeepAlive();
    this.isServer = AppConfig.getIsServer();
    this.showGUI = AppConfig.getShowGUI();
    this.useRMI = AppConfig.getUseRMI();
    this.serverAddress = AppConfig.getServerAddress();
    this.showDebug = AppConfig.getShowDebug();
    this.maxInactivityTimeMs = AppConfig.getMaxInactivityTime();
  }

  /**
   * Server socket port getter.
   *
   * @return The instantiated server socket port.
   */
  public Integer getServerSocketPort() {
    return serverSocketPort;
  }

  /**
   * Server rmi port getter.
   *
   * @return The instantiated server rmi port.
   */
  public Integer getServerRmiPort() {
    return serverRmiPort;
  }

  /**
   * Max connections getter.
   *
   * @return The maximum allowed connections.
   */
  public Integer getMaxConnections() {
    return maxConnection;
  }

  /**
   * Keep alive getter.
   *
   * @return The keep alive flag.
   */
  public boolean getKeepAlive() {
    return keepAlive;
  }

  /**
   * Is server getter.
   *
   * @return The isServer flag.
   */
  public boolean getIsServer() {
    return isServer;
  }

  /**
   * Show GUI getter.
   *
   * @return The show GUI flag.
   */
  public boolean getShowGUI() {
    return showGUI;
  }

  /**
   * Use RMI getter.
   *
   * @return The use RMI flag.
   */
  public boolean getUseRMI() {
    return useRMI;
  }

  /**
   * Server address getter.
   *
   * @return The server address.
   */
  public String getServerAddress() {
    return serverAddress;
  }

  /**
   * Show debug getter.
   *
   * @return The debug flag.
   */
  public boolean getShowDebug() {
    return showDebug;
  }

  /**
   * Max inactivity time getter.
   *
   * @return The max inactivity time value in ms.
   */
  public long getMaxInactivityTime() {
    return maxInactivityTimeMs;
  }
}
