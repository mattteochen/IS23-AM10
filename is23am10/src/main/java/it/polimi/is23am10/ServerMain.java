package it.polimi.is23am10;

import java.io.IOException;

/**
 * The server main class definition.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public final class ServerMain  {

  /**
   * The server executor instance.
   *
   */
  private static final Server server = new Server();

  /**
   * The main method.
   *
   */
  public static void main(String[] args) throws IOException {
    server.start();
  }
}
