package it.polimi.is23am10.server.model.items.board.exceptions;

import it.polimi.is23am10.server.model.items.board.Board;
import it.polimi.is23am10.utils.exceptions.RowIndexOutOfBoundsException;

/**
 * Custom exception to handle board grid out of bounds row values.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public class BoardGridRowIndexOutOfBoundsException extends RowIndexOutOfBoundsException {
  public BoardGridRowIndexOutOfBoundsException(Integer row) {
    super("Board", row, Board.BOARD_GRID_ROWS);
  }
}
