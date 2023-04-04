package it.polimi.is23am10.utils.exceptions;

/**
 * Custom exception to handle wrong picks inside the game board.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public class WrongGameBoardPicksException extends Exception {
  public WrongGameBoardPicksException(String msg) {
    super(msg);
  }
}
