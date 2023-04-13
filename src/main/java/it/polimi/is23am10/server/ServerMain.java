package it.polimi.is23am10.server;

import it.polimi.is23am10.utils.ArgParser;
import it.polimi.is23am10.utils.exceptions.InvalidArgumentException;
import it.polimi.is23am10.utils.exceptions.MissingParameterException;
import it.polimi.is23am10.server.config.ServerConfigContext;
import it.polimi.is23am10.server.config.exceptions.InvalidMaxConnectionsNumberException;
import it.polimi.is23am10.server.config.exceptions.InvalidPortNumberException;
import it.polimi.is23am10.server.controller.ServerControllerAction;

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
   * @throws MissingParameterException
   * @throws InvalidArgumentException
   * @throws InvalidMaxConnectionsNumberException
   * @throws InvalidPortNumberException
   * @throws NumberFormatException
   *
   * @throws IOException
   *
   * @throws IOException
   *
   */
  public static void main(String[] args) throws IOException, InvalidArgumentException, 
      MissingParameterException, NumberFormatException, InvalidPortNumberException, 
      InvalidMaxConnectionsNumberException {
    ArgParser.parse(args);
    ServerConfigContext ctx = new ServerConfigContext();

    Server server = new Server(new ServerSocket(ctx.getServerSocketPort()),
        Executors.newFixedThreadPool(ctx.getMaxConnections()), new ServerControllerAction(),
        LocateRegistry.createRegistry(ctx.getServerRmiPort()));
    server.start(ctx);
  }
}
