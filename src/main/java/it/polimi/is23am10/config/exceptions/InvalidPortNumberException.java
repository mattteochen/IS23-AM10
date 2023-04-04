package it.polimi.is23am10.config.exceptions;

/**
 * Custom exception for invalid port number.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public class InvalidPortNumberException extends Exception {
  public InvalidPortNumberException() {
    super("The provided port number is not valid");
  }
}
