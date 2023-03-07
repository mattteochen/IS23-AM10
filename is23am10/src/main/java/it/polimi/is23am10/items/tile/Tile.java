package it.polimi.is23am10.items.tile;

/**
 * The tile object.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public class Tile {

  /**
   * An enumeration about the available tyle types.
   * 
   */
  public enum TileType {
    CAT,
    BOOK,
    GAME,
    FRAME,
    TROPHY,
    PLANT
  }

  /**
   * The instance Tile type.
   * 
   */
  private TileType type;

  public Tile(TileType tt){
    type = tt;
  }

  public TileType getType() {
    return type;
  }

  public void setTile(TileType tt) {
    type = tt;
  }
}
