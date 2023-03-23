package it.polimi.is23am10.items.board.exceptions;

import it.polimi.is23am10.items.board.Board;
import it.polimi.is23am10.sharedexceptions.ColIndexOutOfBoundsException;

/**
 * Custom exception to handle board grid out of bounds column values.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public class BoardGridColIndexOutOfBoundsException extends ColIndexOutOfBoundsException {
  public BoardGridColIndexOutOfBoundsException(Integer col) {
    super("Board", col, Board.BOARD_GRID_COLS);
  }
}
