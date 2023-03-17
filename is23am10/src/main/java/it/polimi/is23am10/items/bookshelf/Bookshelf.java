package it.polimi.is23am10.items.bookshelf;

import it.polimi.is23am10.items.bookshelf.exceptions.BookshelfGridColIndexOutOfBoundsException;
import it.polimi.is23am10.items.bookshelf.exceptions.BookshelfGridRowIndexOutOfBoundsException;
import it.polimi.is23am10.items.bookshelf.exceptions.NullTileException;
import it.polimi.is23am10.items.bookshelf.exceptions.WrongCharBookshelfStringException;
import it.polimi.is23am10.items.bookshelf.exceptions.WrongLengthBookshelfStringException;
import it.polimi.is23am10.items.tile.Tile;
import it.polimi.is23am10.items.tile.Tile.TileType;
import it.polimi.is23am10.items.tile.exceptions.WrongTileTypeException;
import it.polimi.is23am10.utils.IndexValidator;
import it.polimi.is23am10.utils.exceptions.NullIndexValueException;

import java.util.Map;


/**
 * Players' bookshelf class definition.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public class Bookshelf {
  /**
   * These are the constants used inside the class bookshelf
   */

   /**
    * The bookshelf max rows value.
    */
  public static final int BOOKSHELF_ROWS = 6;

  /**
   * The bookshelf max columns value.
   */
  public static final int BOOKSHELF_COLS = 5;

  /**
   * 
   * The support map to reference each {@link TileType} with a char.
   * Used for the constructor of Bookshelf made for tests.
   * 
   */
  Map<String, TileType> tileMap = Map.of(
      "C", TileType.CAT,
      "B", TileType.BOOK,
      "G", TileType.GAME,
      "F", TileType.FRAME,
      "T", TileType.TROPHY,
      "P", TileType.PLANT,
      "X", TileType.EMPTY);

  /**
   * Max bookshelf grid size. 
   */
  private static final int BOOKSHELF_SIZE = BOOKSHELF_COLS * BOOKSHELF_ROWS;


  /**
   * A fixed 2d array referencing the physical bookshelf instance.
   * 
   */
  private Tile[][] bookshelfGrid;

  /**
   * Constructor for the Bookshelf instance.
   */
  public Bookshelf() {
    bookshelfGrid = new Tile[BOOKSHELF_ROWS][BOOKSHELF_COLS];
    for (int i = 0; i < BOOKSHELF_ROWS; i++) {
      for (int j = 0; j < BOOKSHELF_COLS; j++) {
        bookshelfGrid[i][j] = new Tile(TileType.EMPTY);
      }
    }
  }

  /**
   * This constructor takes a 30 char long string containing the content
   * of a bookshelf, with each tile associated to a letter, as shown below
   * and builds and returns the matching bookshelf object.
   * 
   * @param bookshelfString A string that allows us to fill the bookshelfGrid with the
   *                      correspondance between each char and the position in the
   *                      grid, there's a map to help us matching the char with
   *                      the {@link TileType}
   * @throws WrongTileTypeException
   * @throws WrongLengthBookshelfStringException
   * @throws WrongCharBookshelfStringException
   * @throws NullPointerException
   */
  public Bookshelf(String bookshelfString) throws WrongLengthBookshelfStringException, WrongCharBookshelfStringException,
      NullPointerException, WrongTileTypeException {

    if (bookshelfString.length() != BOOKSHELF_SIZE) {
      throw new WrongLengthBookshelfStringException(
          "[Class Bookshelf, method constructor]: Bookshelf string has incorrect length exception");
    }
    String[] tileChars = bookshelfString.split("");
    for (String c : tileChars) {
      if (!tileMap.containsKey(c)) {
        throw new WrongCharBookshelfStringException(
            "[Class Bookshelf, method constructor]: Bookshelf string contains invalid char exception");
      }
    }
    bookshelfGrid = new Tile[BOOKSHELF_ROWS][BOOKSHELF_COLS];

    /*
     * Here we are filling the bookshelf inserting a Tile of the corresponding
     * TileType, using the mapping we implemented before.
     * To access the right char in each cycle we are using an index which maps the
     * bidimensional array indexes into one single index.
     */
    for (int i = 0; i < BOOKSHELF_ROWS; i++) {
      for (int j = 0; j < BOOKSHELF_COLS; j++) {
        bookshelfGrid[i][j] = new Tile(tileMap.get(tileChars[BOOKSHELF_COLS * i + j]));
      }
    }
  }



  /**
   * Set a {@link Tile} inside the bookshelf grid.
   * 
   * @param row  The bookshelf grid row's value.
   * @param col  The bookshelf grid col's value.
   * @param tile The tile to be set.
   * @throws NullIndexValueException
   * @throws BookshelfGridColIndexOutOfBoundsException.
   * @throws BookshelfGridRowIndexOutOfBoundsException.
   * 
   */
  public void setBookshelfGridIndex(Integer row, Integer col, Tile tile)
      throws BookshelfGridColIndexOutOfBoundsException, BookshelfGridRowIndexOutOfBoundsException, NullIndexValueException, NullTileException {
    if (!IndexValidator.validRowIndex(row, Bookshelf.BOOKSHELF_ROWS)) {
      throw new BookshelfGridRowIndexOutOfBoundsException(row);
    }
    if (!IndexValidator.validColIndex(row, Bookshelf.BOOKSHELF_COLS)) {
      throw new BookshelfGridColIndexOutOfBoundsException(col);
    }
    if(tile == null){
      throw new NullTileException("[Class Bookshelf, method SetBookshelfGridIndex]: Null tile exception ");
    }
    bookshelfGrid[row][col] = tile;
  }


 /**
   * bookshelfGrid getter.
   * 
   * @return The bookshelf's 6x5 playground grid.
   *    
*/
  public Tile[][] getBookshelfGrid() {
    return bookshelfGrid;
  }


  /**
   * bookshelfGrid index getter.
   * 
   * @param row The bookshelf grid's row value.
   * @param col The bookshelf grid's col value.
   * @return The tile at the given indexes.
   * @throws NullIndexValueException
   * @throws BookshelfGridColIndexOutOfBoundsException.
   * @throws BookshelfGridRowIndexOutOfBoundsException.
   * 
   */
  public Tile getBookshelfGridAt(Integer row, Integer col)
      throws BookshelfGridColIndexOutOfBoundsException, BookshelfGridRowIndexOutOfBoundsException, NullIndexValueException {
    if (!IndexValidator.validRowIndex(row, Bookshelf.BOOKSHELF_ROWS)) {
      throw new BookshelfGridRowIndexOutOfBoundsException(row);
    }
    if (!IndexValidator.validColIndex(col, Bookshelf.BOOKSHELF_COLS)) {
      throw new BookshelfGridColIndexOutOfBoundsException(col);
    }
    return bookshelfGrid[row][col];}
}
