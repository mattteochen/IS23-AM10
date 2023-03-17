package it.polimi.is23am10;

import it.polimi.is23am10.config.ServerConfig;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.Executors;

/**
 * The server main class definition.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public final class ServerMain {

  /**
   * The main method.
   *
   */
  public static void main(String[] args) throws IOException {
    Server server = new Server(new ServerSocket(ServerConfig.SERVER_PORT),
        Executors.newFixedThreadPool(ServerConfig.MAX_CLIENT_CONNECTION));
    server.start();
  }
}
