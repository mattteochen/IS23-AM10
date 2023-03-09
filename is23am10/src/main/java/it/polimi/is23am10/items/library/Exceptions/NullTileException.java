package it.polimi.is23am10.items.library.Exceptions;

/**
 * Null Tile exception.
 * Can be used public methods of {@link Library}.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public class NullTileException extends Exception {
  public NullTileException(String msg) {
    super(msg);
  }
}
