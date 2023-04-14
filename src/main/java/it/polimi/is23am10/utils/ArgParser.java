package it.polimi.is23am10.utils;

import it.polimi.is23am10.server.config.ServerConfig;
import it.polimi.is23am10.server.config.exceptions.InvalidMaxConnectionsNumberException;
import it.polimi.is23am10.server.config.exceptions.InvalidPortNumberException;
import it.polimi.is23am10.utils.exceptions.InvalidArgumentException;
import it.polimi.is23am10.utils.exceptions.MissingParameterException;

/**
 * Parser for argument from CLI.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public class ArgParser {

  /**
   * Private constructor of argument parser.
   */
  private ArgParser() {
  }

  /**
   * The socket port cli command.
   */
  public static final String SOCKET_PORT_CLI_COMMAND = "--socket-port";

  /**
   * The rmi port cli command.
   */
  public static final String RMI_PORT_CLI_COMMAND = "--rmi-port";

  /**
   * The max connections cli command.
   */
  public static final String MAX_CONNECTIONS_CLI_COMMAND = "--max-connections";

  /**
   * The keep alive cli command.
   */
  public static final String KEEP_ALIVE_CLI_COMMAND = "--keep-alive";

  /**
   * Argument parser method that checks commands.
   *
   * @param args arguments passed through cli.
   * @throws InvalidArgumentException             for invalid command.
   * @throws MissingParameterException            for missing param.
   * @throws InvalidPortNumberException           if port not in bounds.
   * @throws NumberFormatException                if int not found.
   * @throws InvalidMaxConnectionsNumberException
   * 
   */
  public static void parse(String[] args)
      throws InvalidArgumentException, MissingParameterException, NumberFormatException,
      InvalidPortNumberException, InvalidMaxConnectionsNumberException {
    if (args != null) {
      for (int i = 0; i < args.length; i++) {
        switch (args[i]) {
          case SOCKET_PORT_CLI_COMMAND:
            if (i + 1 < args.length) {
              ServerConfig.setServerSocketPort(Integer.parseInt(args[i + 1]));
              i++;
            } else {
              throw new MissingParameterException(args[i]);
            }
            break;
          case RMI_PORT_CLI_COMMAND:
            if (i + 1 < args.length) {
              ServerConfig.setServerRmiPort(Integer.parseInt(args[i + 1]));
              i++;
            } else {
              throw new MissingParameterException(args[i]);
            }
            break;
          case MAX_CONNECTIONS_CLI_COMMAND:
            if (i + 1 < args.length) {
              ServerConfig.setMaxConnections(Integer.parseInt(args[i + 1]));
              i++;
            } else {
              throw new MissingParameterException(args[i]);
            }
            break;
          case KEEP_ALIVE_CLI_COMMAND:
            if (i + 1 < args.length) {
              ServerConfig.setKeepAlive(Boolean.parseBoolean(args[i + 1]));
              i++;
            } else {
              throw new MissingParameterException(args[i]);
            }
            break;
          default:
            throw new InvalidArgumentException(args[i]);
        }
      }
    }
  }
}
