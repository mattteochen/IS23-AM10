package it.polimi.is23am10.utils.exceptions;

/**
 * Custom exception for the illegal selection of moves
 * in the bookshelf. All the selected tiles must be in the same column.
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
