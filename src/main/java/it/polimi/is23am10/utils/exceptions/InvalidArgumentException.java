package it.polimi.is23am10.utils.exceptions;

/**
 * Custom exception for invalid cli argument parsed.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public class InvalidArgumentException extends Exception {
  public InvalidArgumentException(String arg) {
    super("The provided argument is not valid" + arg);
  }
}
