package it.polimi.is23am10.items.library.exceptions;

/**
 * libraryString given to the constructor has wrong length
 * Can be used public methods of {@link Library}.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public class WrongLengthLibraryStringException extends Exception {
  public WrongLengthLibraryStringException(String msg) {
    super(msg);
  }
}