package it.polimi.is23am10.server.controller.exceptions;

/**
 * Custom exception to handle null game handler instances.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public class NullPlayerConnectorInstance extends Exception {
  public NullPlayerConnectorInstance() {
    super("Null player connector instance");
  }
}
