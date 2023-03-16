package it.polimi.is23am10.config;

/**
 * The server config class definition.
 * All the configuration for the server reside here.
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
   * The server config class definition.
   * 
   */
  public static final Integer SERVER_PORT = 9000;

  /**
   * The max allowed connection for this server instance.
   * 
   */
  public static final Integer MAX_CLIENT_CONNECTION = 8;
}
