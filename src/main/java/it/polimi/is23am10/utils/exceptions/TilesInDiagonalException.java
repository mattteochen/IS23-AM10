package it.polimi.is23am10.utils.exceptions;

/**
 * Custom exception for illegal moves in diagonal.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public class TilesInDiagonalException extends Exception {
  public TilesInDiagonalException() {
    super("The selected tiles are in diagonal: illegal move");
  }
}
