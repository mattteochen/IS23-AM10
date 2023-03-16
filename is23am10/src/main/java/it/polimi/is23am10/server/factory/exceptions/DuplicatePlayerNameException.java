package it.polimi.is23am10.server.factory.exceptions;

/**
 * Custom exception to handle duplicate player names inside a game instance.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public class DuplicatePlayerNameException extends Exception {
  public DuplicatePlayerNameException(String msg) {
    super(msg);
  }
}
