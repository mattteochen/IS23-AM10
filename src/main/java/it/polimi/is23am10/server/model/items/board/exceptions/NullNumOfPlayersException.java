package it.polimi.is23am10.server.model.items.board.exceptions;

/**
 * Custom exception for null number of players value.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public class NullNumOfPlayersException extends Exception {
  public NullNumOfPlayersException() {
    super("Null number of players value");
  }
}
