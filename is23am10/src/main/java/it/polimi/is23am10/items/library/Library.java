package it.polimi.is23am10.items.library;

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
   * A fixed 2d array referencing the physical library instance.
   * 
   */
  private Tile[][] libraryGrid;

  public Tile[][] getLibraryGrid() {
    return libraryGrid;
  }

  public void setTile(int i, int j, Tile t) {
    libraryGrid[i][j] = t;
  }
}
