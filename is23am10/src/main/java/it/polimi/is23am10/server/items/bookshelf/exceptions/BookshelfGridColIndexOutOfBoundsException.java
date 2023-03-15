package it.polimi.is23am10.server.items.bookshelf.exceptions;

import it.polimi.is23am10.server.items.bookshelf.Bookshelf;
import it.polimi.is23am10.server.sharedexceptions.ColIndexOutOfBoundsException;

/**
 * Custom exception to handle bookshelf grid out of bounds column values.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public class BookshelfGridColIndexOutOfBoundsException extends ColIndexOutOfBoundsException {
  public BookshelfGridColIndexOutOfBoundsException(Integer col) {
    super("Bookshelf", col, Bookshelf.BOOKSHELF_COLS);
  }
}
