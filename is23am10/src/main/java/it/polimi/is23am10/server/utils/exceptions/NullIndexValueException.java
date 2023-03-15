package it.polimi.is23am10.server.utils.exceptions;

/**
 * Custom exception for null index values.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public class NullIndexValueException extends Exception {
  public NullIndexValueException() {
    super("The provided index value is null");
  }
}
