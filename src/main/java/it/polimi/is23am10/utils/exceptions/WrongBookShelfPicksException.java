package it.polimi.is23am10.utils.exceptions;

/**
 * Custom exception to handle wrong picks inside the book shelf.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public class WrongBookShelfPicksException extends Exception {
  public WrongBookShelfPicksException(String msg) {
    super(msg);
  }
}
