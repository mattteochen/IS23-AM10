package it.polimi.is23am10.items.card.exceptions;

/**
 * Custom exception for null score blocks list instance.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public class NullScoreBlockListException extends Exception {
  public NullScoreBlockListException(String msg) {
    super(msg);
  }
}
