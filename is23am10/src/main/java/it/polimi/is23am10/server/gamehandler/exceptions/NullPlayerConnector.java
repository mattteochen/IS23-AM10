package it.polimi.is23am10.server.gamehandler.exceptions;

/**
 * Custom exception to handle null max player connectors.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public class NullPlayerConnector extends Exception {
  public NullPlayerConnector() {
    super("Null player connector");
  }
}
