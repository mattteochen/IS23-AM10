package it.polimi.is23am10.server.items.card.exceptions;

/**
 * Custom exception for null matched block count Integer.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public class NullMatchedBlockCountException extends Exception {
  public NullMatchedBlockCountException(String msg) {
    super(msg);
  }
}
