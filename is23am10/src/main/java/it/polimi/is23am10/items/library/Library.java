package it.polimi.is23am10.items.library;

import java.util.Map;

import it.polimi.is23am10.items.library.Exceptions.ColsIndexOutOfBoundsException;
import it.polimi.is23am10.items.library.Exceptions.NullTileException;
import it.polimi.is23am10.items.library.Exceptions.RowsIndexOutOfBoundsException;
import it.polimi.is23am10.items.tile.Tile;
import it.polimi.is23am10.items.tile.Tile.TileType;

/**
 * Players' library object.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public class Library {
  /*
   * These are the constants used inside the class library
   */
  private static final int LIB_ROWS = 6;
  private static final int LIB_COLS = 5;
  Map<String, TileType> tileMap = Map.of(
      "C", TileType.CAT,
      "B", TileType.BOOK,
      "G", TileType.GAME,
      "F", TileType.FRAME,
      "T", TileType.TROPHY,
      "P", TileType.PLANT,
      "X", TileType.EMPTY);

  /**
   * A fixed 2d array referencing the physical library instance.
   * 
   */
  private Tile[][] libraryGrid;

  public Library() {
    libraryGrid = new Tile[LIB_ROWS][LIB_COLS];
  }

  /**
   * This constructor takes a 30 char long string containing the content
   * of a library, with each tile associated to a letter, as shown below
   * and builds and returns the matching library object
   * 
   * @param libraryString A string that allows us to fill the libraryGrid with the
   *                      correspondance between each char and the position in the
   *                      grid, there's a map to help us matching the char with
   *                      the {@link TileType}
   */
  public Library(String libraryString) {

    String[] tileChars = libraryString.split("");
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

  /*
   * A method that allows us to get the libraryGrid element
   * 
   */
  public Tile[][] getLibraryGrid() {
    return libraryGrid;
  }

  /*
   * A method that allows us to set a {@link Tile} in a specific position inside
   * the libraryGrid
   * 
   */
  public void setTile(int i, int j, Tile t)
      throws NullTileException, RowsIndexOutOfBoundsException, ColsIndexOutOfBoundsException {
    if (t == null) {
      throw new NullTileException("[Class Library, method setTile]: Null tile");
    }
    if (i < 0 || i >= LIB_ROWS) {
      throw new RowsIndexOutOfBoundsException("[Class Library, method setTile]: Rows index out of bounds");
    }
    if (j < 0 || j >= LIB_COLS) {
      throw new ColsIndexOutOfBoundsException("[Class Library, method setTile]: Cols index out of bounds");
    }

    libraryGrid[i][j] = t;
  }
}
