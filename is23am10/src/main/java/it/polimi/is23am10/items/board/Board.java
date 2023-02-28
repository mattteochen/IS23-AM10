package it.polimi.is23am10.items.board;

import it.polimi.is23am10.items.tile.Tile;

/**
 * Game's grid object.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 *         Franesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 *         Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 *         Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public class Board {

  /**
   * A fixed 2d array referencing the physical grid instance.
   * 
   */
  private Tile[][] boardGrid;

  /**
   * A fixed 2d array referencing a weight for each boardGrid cell.
   * 
   */
  // TODO: set final and add init values
  private Integer[][] blackMap;
}
