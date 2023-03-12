package it.polimi.is23am10.items.tile;

/**
 * The tile class definition.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public class Tile {

  /**
   * An enumeration about the available tile types.
   * 
   */
  public enum TileType {
    CAT,
    BOOK,
    GAME,
    FRAME,
    TROPHY,
    PLANT,
    EMPTY
  }

  /**
   * The instance Tile type.
   * 
   */
  private TileType type;

  /**
   * Constructor.
   * If the desired type has null value, an empty tile will be set.
   * 
   */
  public Tile(TileType type) {
    this.type = type == null ? TileType.EMPTY : type;
  }

  /**
   * type getter.
   * 
   * @return The type of the current Tile instance.
   * 
   */
  public TileType getType() {
    return type;
  }

  /**
   * Tile type comparator.
   * 
   * @param type The type to assign.
   * @return The comparison result.
   * 
   */
  public boolean typeEqual(Tile type) {
    if (getType() == null || type.getType() == null) {
      return false;
    }
    return (getType() == type.getType());
  }
}