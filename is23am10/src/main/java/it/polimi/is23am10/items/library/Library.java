package it.polimi.is23am10.items.library;

import it.polimi.is23am10.items.library.exceptions.LibraryGridColIndexOutOfBoundsException;
import it.polimi.is23am10.items.library.exceptions.LibraryGridRowIndexOutOfBoundsException;
import it.polimi.is23am10.items.library.exceptions.NullIndexValueException;
import it.polimi.is23am10.items.tile.Tile;

/**
 * Players' library object.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public class Library {

  /**
   * The library's grid rows number.
   * 
   */
  public static final Integer BOOK_SHELF_ROWS = 6;

  /**
   * The library's grid cols number.
   * 
   */
  public static final Integer BOOK_SHELF_COLS = 5;

  /**
   * A fixed 2d array referencing the physical library instance.
   * 
   */
  private Tile[][] libraryGrid;

  /**
   * Constructor.
   * 
   */
  public Library() {
    libraryGrid = new Tile[BOOK_SHELF_ROWS][BOOK_SHELF_COLS];
  }

  /**
   * Validate the row index for the current library grid.
   * 
   * @param row The value to be evaluated.
   * @return The validation result.
   * @throws NullIndexValueException
   * 
   */
  private boolean validRowIndex(Integer row) throws NullIndexValueException {
    if (row == null) {
      throw new NullIndexValueException();
    }
    return row >= 0 && row < BOOK_SHELF_ROWS;
  }

  /**
   * Validate the column index for the current library grid.
   * 
   * @param col The value to be evaluated.
   * @return The validation result.
   * @throws NullIndexValueException
   * 
   */
  private boolean validColIndex(Integer col) throws NullIndexValueException {
    if (col == null) {
      throw new NullIndexValueException();
    }
    return col >= 0 && col < BOOK_SHELF_COLS;
  }

  /**
   * Set a {@link Tile} inside the library grid.
   * 
   * @param row  The library grid row value.
   * @param col  The library grid col value.
   * @param tile The tile to be set.
   * @throws NullIndexValueException
   * @throws LibraryGridColIndexOutOfBoundsException.
   * @throws LibraryGridRowIndexOutOfBoundsException.
   * 
   */
  public void setLibraryGridIndex(Integer row, Integer col, Tile tile)
      throws LibraryGridColIndexOutOfBoundsException, LibraryGridRowIndexOutOfBoundsException, NullIndexValueException {
    if (!validRowIndex(row)) {
      throw new LibraryGridRowIndexOutOfBoundsException(row);
    }
    if (!validColIndex(col)) {
      throw new LibraryGridColIndexOutOfBoundsException(col);
    }
    libraryGrid[row][col] = tile;
  }

  /**
   * libraryGrid getter.
   * 
   * @return The library's 6x5 playgound grid.
   * 
   */
  public Tile[][] getLibraryGrid() {
    return libraryGrid;
  }
}
