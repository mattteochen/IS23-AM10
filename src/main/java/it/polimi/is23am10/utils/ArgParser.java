package it.polimi.is23am10.utils;

import it.polimi.is23am10.config.ServerConfigContext;
import it.polimi.is23am10.config.ServerConfigDefault;

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
   * 
   */
  public static void parse(String[] args) {
    for (int i = 0; i < args.length; i++) {
      switch (args[i]) {
        case "--port":
          if (i + 1 < args.length) {
            ServerConfigDefault.setServerPort(Integer.parseInt(args[i + 1]));
          }
          break;
      }
    }
  }
}
