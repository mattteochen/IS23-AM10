package it.polimi.is23am10.server.network.playerconnector.exceptions;

/**
 * Custom exception to handle null blocking queue instances.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public class NullBlockingQueueException extends Exception {
  public NullBlockingQueueException() {
    super("Null blocking queue instance");
  }
}
