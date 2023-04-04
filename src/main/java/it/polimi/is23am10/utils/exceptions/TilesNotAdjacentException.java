package it.polimi.is23am10.utils.exceptions;

/**
 * Custom exception for handling not adjacent moved or positioned tiles.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public class TilesNotAdjacentException extends Exception {
  public TilesNotAdjacentException() {
    super("The moved tiles are not adjacent");
  }
}
