package it.polimi.is23am10.items.library.exceptions;

import it.polimi.is23am10.items.library.Library;

/**
 * Custom exception to handle library grid out of bounds row values.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public class LibraryGridRowIndexOutOfBoundsException extends Exception {
	public LibraryGridRowIndexOutOfBoundsException(Integer row) {
		super(row < 0 ? "Library grid column index can not be negative"
				: "Library grid column index can not be greater than " + Library.LIBRARY_COLS);
	}
}
