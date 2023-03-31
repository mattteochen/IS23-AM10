package it.polimi.is23am10.config;

import it.polimi.is23am10.config.exceptions.InvalidMaxConnectionsNumberException;
import it.polimi.is23am10.config.exceptions.InvalidPortNumberException;

/**
 * The server default config class definition.
 * All the default configuration for the server reside here.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public final class ServerConfig {

  /**
   * Private constructor.
   * 
   */
  private ServerConfig() {

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
   * Server port setter.
   * @throws InvalidPortNumberException
   *
   * 
   */
  public static void setServerSocketPort(Integer p) throws InvalidPortNumberException {
    if(p < MIN_PORT_NUMBER || p > MAX_PORT_NUMBER){
      throw new InvalidPortNumberException();
    }
    serverSocketPort = p;
  }

  /**
   * Server port setter.
   * @throws InvalidPortNumberException
   *
   * 
   */
  public static void setServerRmiPort(Integer p) throws InvalidPortNumberException {
    if(p < MIN_PORT_NUMBER || p > MAX_PORT_NUMBER){
      throw new InvalidPortNumberException();
    }
    serverRmiPort = p;
  }

  /**
   * Max connections setter.
   * @throws InvalidMaxConnectionsNumberException
   *
   * 
   */
  public static void setMaxConnections(Integer maxConn) throws InvalidMaxConnectionsNumberException {
    if(maxConn < MIN_CONNECTIONS_NUMBER || maxConn > MAX_CONNECTIONS_NUMBER){
      throw new InvalidMaxConnectionsNumberException();
    }
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
}
