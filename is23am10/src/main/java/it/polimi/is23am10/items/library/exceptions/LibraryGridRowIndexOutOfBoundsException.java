package it.polimi.is23am10.items.library.exceptions;

import it.polimi.is23am10.items.library.Library;
import it.polimi.is23am10.sharedexceptions.RowIndexOutOfBoundsException;

/**
 * Custom exception to handle library grid out of bounds row values.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public class LibraryGridRowIndexOutOfBoundsException extends RowIndexOutOfBoundsException {
  public LibraryGridRowIndexOutOfBoundsException(Integer row) {
    super("Library", row, Library.LIB_ROWS);
  }
}
