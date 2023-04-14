package it.polimi.is23am10.server.model.items.bookshelf.exceptions;

/**
 * One char in the bookshelfString is not among the possibles {@link TileType}
 * Can be used public methods of {@link Bookshelf}.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public class WrongCharBookshelfStringException extends Exception {
  public WrongCharBookshelfStringException(String msg) {
    super(msg);
  }
}