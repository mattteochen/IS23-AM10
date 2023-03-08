package it.polimi.is23am10.items.library.exceptions;

import it.polimi.is23am10.items.library.Library;
import it.polimi.is23am10.sharedexceptions.ColIndexOutOfBoundsException;

/**
 * Custom exception to handle library grid out of bounds column values.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public class LibraryGridColIndexOutOfBoundsException extends ColIndexOutOfBoundsException {
	public LibraryGridColIndexOutOfBoundsException(Integer col) {
		super("Library", col, Library.BOOK_SHELF_COLS);
	}
}
