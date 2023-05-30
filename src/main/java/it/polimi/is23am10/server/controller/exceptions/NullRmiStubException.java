package it.polimi.is23am10.server.controller.exceptions;

/**
 * Custom runtime exception to null RMI stubs.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public class NullRmiStubException extends RuntimeException {
  public NullRmiStubException() {
    super("Null RMI stub instance");
  }
}
