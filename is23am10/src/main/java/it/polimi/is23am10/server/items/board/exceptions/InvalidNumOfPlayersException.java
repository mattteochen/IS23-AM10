package it.polimi.is23am10.server.items.board.exceptions;

import it.polimi.is23am10.server.items.board.Board;

/**
 * Custom exception for wrong number of players.
 * initialization.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public class InvalidNumOfPlayersException extends Exception {
  public InvalidNumOfPlayersException(Integer num) {
    super("The number of players must be a value in: " + Board.allowedNumOfPlayers.toString() + ". Received: " + num);
  }
}
