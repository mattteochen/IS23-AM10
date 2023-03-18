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
   * The server config class definition.
   * 
   */
  public static final int SERVER_PORT = 9001;

  /**
   * The max allowed connection for this server instance.
   * This is a momentary max value.
   * 
   */
  public static final int MAX_CLIENT_CONNECTION = 8;

  /**
   * The socket SO_KEEPALIVE flag.
   * 
   */
  public static final boolean KEEP_ALIVE = true;
}
