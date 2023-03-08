package it.polimi.is23am10.items.library;

import java.util.Map;

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

  /**
   * A fixed 2d array referencing the physical library instance.
   * 
   */
  private Tile[][] libraryGrid;

  public Library() {
    libraryGrid = new Tile[6][5];
  }

  /**
   * This constructor takes a 30 char long string containing the content
   * of a library, with each tile associated to a letter, as shown below
   * and builds and returns the matching library object
   * 
   * @param libraryString
   */
  public Library(String libraryString) {
    String[] tileChars = libraryString.split("");
    libraryGrid = new Tile[6][5];
    Map<String, TileType> tileMap = Map.of(
        "C", TileType.CAT,
        "B", TileType.BOOK,
        "G", TileType.GAME,
        "F", TileType.FRAME,
        "T", TileType.TROPHY,
        "P", TileType.PLANT,
        "X", TileType.EMPTY);

    for (int i = 0; i < libraryGrid.length; i++) {
      for (int j = 0; j < libraryGrid[0].length; j++) {
        libraryGrid[i][j] = new Tile(tileMap.get(tileChars[5 * i + j]));
      }
    }
  }

  public Tile[][] getLibraryGrid() {
    return libraryGrid;
  }

  public void setTile(int i, int j, Tile t) {
    libraryGrid[i][j] = t;
  }
}
