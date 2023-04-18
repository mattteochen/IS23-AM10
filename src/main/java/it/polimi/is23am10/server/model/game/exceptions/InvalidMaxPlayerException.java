package it.polimi.is23am10.server.model.game.exceptions;

/**
 * Custom exception to handle invalid max players value.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public class InvalidMaxPlayerException extends Exception {
  public InvalidMaxPlayerException() {
    super("Invalid max players");
  }
}
