package it.polimi.is23am10.items.library.exceptions;

/**
 * One char in the libraryString is not among the possibles {@link TileType}
 * Can be used public methods of {@link Library}.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public class WrongCharLibraryStringException extends Exception {
  public WrongCharLibraryStringException(String msg) {
    super(msg);
  }
}