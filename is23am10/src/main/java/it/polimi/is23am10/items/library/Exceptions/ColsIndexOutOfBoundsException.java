package it.polimi.is23am10.items.library.Exceptions;

/**
 * Cols index given is out of bounds
 * Can be used public methods of {@link Library}.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public class ColsIndexOutOfBoundsException extends Exception {
  public ColsIndexOutOfBoundsException(String msg) {
    super(msg);
  }
}