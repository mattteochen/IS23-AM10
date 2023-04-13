package it.polimi.is23am10.server.model.factory.exceptions;

/**
 * Custom exception to handle null player names when checking for duplicates
 * in Player Factory.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public class NullPlayerNamesException extends Exception {
  public NullPlayerNamesException(String msg) {
    super(msg);
  }
}
