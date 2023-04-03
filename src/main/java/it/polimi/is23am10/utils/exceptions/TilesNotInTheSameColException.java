package it.polimi.is23am10.utils.exceptions;

/**
 * Custom exception for more than tree moves.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public class TilesNotInTheSameColException extends Exception {
  public TilesNotInTheSameColException() {
    super("Bookshelf move not valid: tiles not in the same column");
  }
}
