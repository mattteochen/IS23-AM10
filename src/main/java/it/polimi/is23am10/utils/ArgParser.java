package it.polimi.is23am10.utils;

import it.polimi.is23am10.config.ServerConfigContext;
import it.polimi.is23am10.config.ServerConfigDefault;
import it.polimi.is23am10.utils.exceptions.InvalidArgumentException;
import it.polimi.is23am10.utils.exceptions.MissingParameter;

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
   * Argument parser method that checks commands.
   *
   * @param args arguments passed through cli.
   * @throws InvalidArgumentException
   * @throws MissingParameter
   * 
   */
  public static void parse(String[] args) throws InvalidArgumentException, MissingParameter {
    for (int i = 0; i < args.length; i++) {
      switch (args[i]) {
        case "--port":
          if (i + 1 < args.length) {
            ServerConfigDefault.setServerPort(Integer.parseInt(args[i + 1]));
          }
          else
            throw new MissingParameter(args[i]);
          break;
        case "--max-connections":
          if (i + 1 < args.length){
            ServerConfigDefault.setMaxConnections(Integer.parseInt(args[i + 1]));
          }
          else
            throw new MissingParameter(args[i]);
          break;
        case "--keep-alive":
          if (i + 1 < args.length){
            ServerConfigDefault.setKeepAlive(Boolean.parseBoolean(args[i + 1]));
          }
          else
            throw new MissingParameter(args[i]);
          break;
        default:
          throw new InvalidArgumentException();
      }
    }
  }
}
