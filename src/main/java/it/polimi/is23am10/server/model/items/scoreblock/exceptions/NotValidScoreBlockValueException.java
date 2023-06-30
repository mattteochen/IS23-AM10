package it.polimi.is23am10.server.model.items.scoreblock.exceptions;

/**
 * Custom exception for not valid score block value.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public class NotValidScoreBlockValueException extends Exception {
  public NotValidScoreBlockValueException(String msg) {
    super(msg);
  }
}
