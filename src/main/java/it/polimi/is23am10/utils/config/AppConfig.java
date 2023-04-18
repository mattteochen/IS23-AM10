package it.polimi.is23am10.utils.config;

import it.polimi.is23am10.utils.config.exceptions.InvalidMaxConnectionsNumberException;
import it.polimi.is23am10.utils.config.exceptions.InvalidPortNumberException;

/**
 * The application config class definition.
 * If not set otherwise all the configuration parameters for the application
 * are set to default values here.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public final class AppConfig {

  /**
   * Private constructor.
   * 
   */
  private AppConfig() {

  }

  /**
   * The lower bound of port numbers.
   * 
   */
  public static final Integer MIN_PORT_NUMBER = 1024;

  /**
   * The upper bound of port numbers.
   * 
   */
  public static final Integer MAX_PORT_NUMBER = 65535;

  /**
   * The lower bound of connection numbers.
   * 
   */
  public static final Integer MIN_CONNECTIONS_NUMBER = 0;

  /**
   * The upper bound of connection numbers.
   * 
   */
  public static final Integer MAX_CONNECTIONS_NUMBER = 10;

  /**
   * The server config server port.
   * 
   */
  private static Integer serverSocketPort = 9001;

  /**
   * The server config rmi port.
   * 
   */
  private static Integer serverRmiPort = 9002;

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
   * The kind of app to launch. If false launches a client.
   * 
   */
  private static boolean isServer = false;

  /**
   * The interface to show if app is launched in Client mode.
   * If false launches client in CLI mode.
   * If {@link AppConfigContext#isServer} is true this flag is ignored,
   * as server mode only supports CLI as interface, and for logging purpose only.
   */
  private static boolean showGUI = false;

    /**
   * The communication method to use if app is launched is Client Mode.
   * If false launches client over Socket connection.
   * If {@link AppConfigContext#isServer} is true this flag is ignored,
   * as server mode both receives RMI and socket connections
   */
  private static boolean useRMI = false;

  /**
   * The network address where the server is running.
   * 
   */
  private static String serverAddress = "localhost";

  /**
   * Server port setter.
   *
   * @param p port number.
   * @throws InvalidPortNumberException invalid port number.
   */
  public static void setServerSocketPort(Integer p) throws InvalidPortNumberException {
    if (p < MIN_PORT_NUMBER || p > MAX_PORT_NUMBER) {
      throw new InvalidPortNumberException();
    }
    serverSocketPort = p;
  }

  /**
   * Server port setter.
   *
   * @param p port number.
   * @throws InvalidPortNumberException invalid port number.
   */
  public static void setServerRmiPort(Integer p) throws InvalidPortNumberException {
    if (p < MIN_PORT_NUMBER || p > MAX_PORT_NUMBER) {
      throw new InvalidPortNumberException();
    }
    serverRmiPort = p;
  }

  /**
   * Max connections setter.
   *
   * @param maxConn number of max connections.
   * @throws InvalidMaxConnectionsNumberException invalid number of maxConn.
   */
  public static void setMaxConnections(Integer maxConn) 
      throws InvalidMaxConnectionsNumberException {
    if (maxConn < MIN_CONNECTIONS_NUMBER || maxConn > MAX_CONNECTIONS_NUMBER){
      throw new InvalidMaxConnectionsNumberException();
    }
    maxConnection = maxConn;
  }

  /**
   * Keep alive setter.
   *
   * @param k keep alive status.
   * 
   */
  public static void setKeepAlive(boolean k) {
    keepAlive = k;
  }
  
  /**
   * Keep alive setter.
   *
   * @param is launch server app
   * 
   */
  public static void setIsServer(boolean is) {
    isServer = is;
  }

  /**
   * Show GUI setter.
   *
   * @param sg show GUI if client
   * 
   */
  public static void setShowGUI(boolean sg) {
    showGUI = sg;
  }

  /**
   * Use RMI setter.
   *
   * @param rmi use RMI or not (socket).
   * 
   */
  public static void setUseRMI(boolean rmi) {
    useRMI = rmi;
  }

  /**
   * Server address setter.
   *
   * @param address Server address.
   * 
   */
  public static void setServerAddress(String address) {
    serverAddress = address;
  }

  /**
   * Server port getter.
   *
   * @return The instantiated server port.
   * 
   */
  public static Integer getServerSocketPort() {
    return serverSocketPort;
  }

  /**
   * Server port getter.
   *
   * @return The instantiated server port.
   * 
   */
  public static Integer getServerRmiPort() {
    return serverRmiPort;
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

  /**
   * Is server getter.
   *
   * @return The isServer flag.
   * 
   */
  public static boolean getIsServer() {
    return isServer;
  }

  /**
   * Show GUI getter.
   *
   * @return The show GUI flag.
   * 
   */
  public static boolean getShowGUI() {
    return showGUI;
  }
  
  /**
   * Use RMI getter.
   *
   * @return The use RMI flag.
   * 
   */
  public static boolean getUseRMI() {
    return useRMI;
  }

  /**
   * Server address getter.
   *
   * @return The server address.
   * 
   */
  public static String getServerAddress() {
    return serverAddress;
  }
}
