package it.polimi.is23am10;

import it.polimi.is23am10.config.ServerConfigContext;
import it.polimi.is23am10.config.ServerConfigDefault;
import it.polimi.is23am10.controller.ServerControllerAction;
import java.io.IOException;
import java.net.ServerSocket;
import java.rmi.registry.LocateRegistry;
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
   * @throws IOException
   *
   */
  public static void main(String[] args) throws IOException {
    // TODO add args parser for CLI context loading if args is present
    ServerConfigContext ctx = new ServerConfigContext(ServerConfigDefault.SERVER_SOCKET_PORT,
        ServerConfigDefault.SERVER_RMI_PORT,
        ServerConfigDefault.MAX_CLIENT_CONNECTION, ServerConfigDefault.KEEP_ALIVE);

    Server server = new Server(new ServerSocket(ctx.getServerSocketPort()),
        Executors.newFixedThreadPool(ctx.getMaxConnections()), new ServerControllerAction(),
        LocateRegistry.createRegistry(ctx.getServerRmiPort()));
    server.start(ctx);
  }
}
