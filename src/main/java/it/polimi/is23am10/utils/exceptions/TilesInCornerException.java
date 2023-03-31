package it.polimi.is23am10.utils.exceptions;

/**
 * Custom exception for illegal corner tile move.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public class TilesInCornerException extends Exception {
  public TilesInCornerException() {
    super("Illegal move: selected tile in corner");
  }
}
