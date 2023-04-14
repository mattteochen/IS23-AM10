package it.polimi.is23am10.server.model.items.bookshelf.exceptions;

import it.polimi.is23am10.server.model.items.bookshelf.Bookshelf;
import it.polimi.is23am10.utils.exceptions.RowIndexOutOfBoundsException;

/**
 * Custom exception to handle bookshelf grid out of bounds row values.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public class BookshelfGridRowIndexOutOfBoundsException extends RowIndexOutOfBoundsException {
  public BookshelfGridRowIndexOutOfBoundsException(Integer row) {
    super("Bookshelf", row, Bookshelf.BOOKSHELF_ROWS);
  }
}
