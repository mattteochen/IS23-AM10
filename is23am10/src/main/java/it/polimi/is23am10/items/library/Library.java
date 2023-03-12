package it.polimi.is23am10.items.library;

import java.util.Map;

import it.polimi.is23am10.items.library.exceptions.NullTileException;
import it.polimi.is23am10.items.library.exceptions.WrongCharLibraryStringException;
import it.polimi.is23am10.items.library.exceptions.WrongLengthLibraryStringException;
import it.polimi.is23am10.items.tile.Tile;
import it.polimi.is23am10.items.tile.Exceptions.WrongTileTypeException;
import it.polimi.is23am10.items.tile.Tile.TileType;
import it.polimi.is23am10.items.library.exceptions.LibraryGridColIndexOutOfBoundsException;
import it.polimi.is23am10.items.library.exceptions.LibraryGridRowIndexOutOfBoundsException;
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
   * These are the constants used inside the class library
   */

   /**
    * The library max rows value.
    */
  public static final int LIB_ROWS = 6;

  /**
   * The library max columns value.
   */
  public static final int LIB_COLS = 5;

  /**
   * 
   * The support map to reference each {@link TileType} with a char.
   * Used for the constructor of Library made for tests.
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
   * Max library grid size. 
   */
  private static final int LIB_SIZE = LIB_COLS * LIB_ROWS;


  /**
   * A fixed 2d array referencing the physical library instance.
   * 
   */
  private Tile[][] libraryGrid;

  /**
   * Constructor for the Library instance.
   */
  public Library() {
    libraryGrid = new Tile[LIB_ROWS][LIB_COLS];
  }

  /**
   * This constructor takes a 30 char long string containing the content
   * of a library, with each tile associated to a letter, as shown below
   * and builds and returns the matching library object.
   * 
   * @param libraryString A string that allows us to fill the libraryGrid with the
   *                      correspondance between each char and the position in the
   *                      grid, there's a map to help us matching the char with
   *                      the {@link TileType}
   * @throws WrongTileTypeException
   * @throws WrongLengthLibraryStringException
   * @throws WrongCharLibraryStringException
   * @throws NullPointerException
   */
  public Library(String libraryString) throws WrongLengthLibraryStringException, WrongCharLibraryStringException,
      NullPointerException, WrongTileTypeException {

    if (libraryString.length() != LIB_SIZE) {
      throw new WrongLengthLibraryStringException(
          "[Class Library, method constructor]: Library string has incorrect length exception");
    }
    String[] tileChars = libraryString.split("");
    for (String c : tileChars) {
      if (!tileMap.containsKey(c)) {
        throw new WrongCharLibraryStringException(
            "[Class Library, method constructor]: Library string contains invalid char exception");
      }
    }
    libraryGrid = new Tile[LIB_ROWS][LIB_COLS];

    /*
     * Here we are filling the library inserting a Tile of the corresponding
     * TileType, using the mapping we implemented before.
     * To access the right char in each cycle we are using an index which maps the
     * bidimensional array indexes into one single index.
     */
    for (int i = 0; i < libraryGrid.length; i++) {
      for (int j = 0; j < libraryGrid[0].length; j++) {
        libraryGrid[i][j] = new Tile(tileMap.get(tileChars[LIB_COLS * i + j]));
      }
    }
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
      throws LibraryGridColIndexOutOfBoundsException, LibraryGridRowIndexOutOfBoundsException, NullIndexValueException, NullTileException {
    if (!IndexValidator.validRowIndex(row, Library.LIB_ROWS)) {
      throw new LibraryGridRowIndexOutOfBoundsException(row);
    }
    if (!IndexValidator.validColIndex(row, Library.LIB_COLS)) {
      throw new LibraryGridColIndexOutOfBoundsException(col);
    }
    if(tile == null){
      throw new NullTileException("[Class Library, method SetLibraryGridIndex]: Null tile exception ");
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
    if (!IndexValidator.validRowIndex(row, Library.LIB_ROWS)) {
      throw new LibraryGridRowIndexOutOfBoundsException(row);
    }
    if (!IndexValidator.validColIndex(row, Library.LIB_COLS)) {
      throw new LibraryGridColIndexOutOfBoundsException(col);
    }
    return libraryGrid[row][col];}
}
