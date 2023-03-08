package it.polimi.is23am10.items.library;

import it.polimi.is23am10.items.library.exceptions.LibraryGridColIndexOutOfBoundsException;
import it.polimi.is23am10.items.library.exceptions.LibraryGridRowIndexOutOfBoundsException;
import it.polimi.is23am10.items.tile.Tile;
import it.polimi.is23am10.utils.IndexValidator;
import it.polimi.is23am10.utils.exceptions.NullIndexValueException;

/**
 * Players' library class definition.
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
   * Set a {@link Tile} inside the library grid.
   * 
   * @param row  The library grid row's value.
   * @param col  The library grid col's value.
   * @param tile The tile to be set.
   * @throws NullIndexValueException
   * @throws LibraryGridColIndexOutOfBoundsException.
   * @throws LibraryGridRowIndexOutOfBoundsException.
   * 
   */
  public void setLibraryGridIndex(Integer row, Integer col, Tile tile)
      throws LibraryGridColIndexOutOfBoundsException, LibraryGridRowIndexOutOfBoundsException, NullIndexValueException {
    if (!IndexValidator.validRowIndex(row, Library.BOOK_SHELF_ROWS)) {
      throw new LibraryGridRowIndexOutOfBoundsException(row);
    }
    if (!IndexValidator.validColIndex(row, Library.BOOK_SHELF_COLS)) {
      throw new LibraryGridColIndexOutOfBoundsException(col);
    }
    libraryGrid[row][col] = tile;
  }

  /**
   * libraryGrid getter.
   * 
   * @return The library's 6x5 playground grid.
   * 
   */
  public Tile[][] getLibraryGrid() {
    return libraryGrid;
  }

  /**
   * libraryGrid index getter.
   * 
   * @param row The library grid's row value.
   * @param col The library grid's col value.
   * @return The tile at the given indexes.
   * @throws NullIndexValueException
   * @throws LibraryGridColIndexOutOfBoundsException.
   * @throws LibraryGridRowIndexOutOfBoundsException.
   * 
   */
  public Tile getLibraryGridAt(Integer row, Integer col)
      throws LibraryGridColIndexOutOfBoundsException, LibraryGridRowIndexOutOfBoundsException, NullIndexValueException {
    if (!IndexValidator.validRowIndex(row, BOOK_SHELF_ROWS)) {
      throw new LibraryGridRowIndexOutOfBoundsException(row);
    }
    if (!IndexValidator.validColIndex(row, Library.BOOK_SHELF_COLS)) {
      throw new LibraryGridColIndexOutOfBoundsException(col);
    }
    return libraryGrid[row][col];
  }
}
