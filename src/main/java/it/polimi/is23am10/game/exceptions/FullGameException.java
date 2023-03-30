package main.java.it.polimi.is23am10.game.exceptions;

/**
 * The game reached its maximum number of players.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public class FullGameException extends Exception {
  public FullGameException(String msg) {
    super(msg);
  }
}
