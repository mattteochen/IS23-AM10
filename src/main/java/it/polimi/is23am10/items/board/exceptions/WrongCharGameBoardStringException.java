package it.polimi.is23am10.items.board.exceptions;

/**
 * gameBoardString given to the constructor has wrong char
 * Can be used public methods of {@link Board}.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public class WrongCharGameBoardStringException extends Exception {
  public WrongCharGameBoardStringException(String msg) {
    super(msg);
  }
}
