package it.polimi.is23am10.items.board;

import java.util.List;

import it.polimi.is23am10.items.tile.Tile;

/**
 * Game's board object.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public class Board {

  /**
   * A fixed 2d array referencing the physical grid instance.
   * 
   */
  private Tile[][] boardGrid;

  /**
   * A fixed 2d array referencing a weight for each boardGrid cell.
   * Each value represents the minimum number of players needed to get that square filled.
   * 
   * <ul>
   *  <li> Values >4 are meant for squares out of the board
   *  <li> Values <3 instead will always be filled
   * </ul>
   */
  // TODO: set final and add init values
  private Integer[][] blackMap;

  /**
   * A list containing the available tiles.
   * 
   */
  private List<Tile> tileStack;
}
