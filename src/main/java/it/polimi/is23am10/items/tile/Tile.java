package it.polimi.is23am10.items.tile;

import java.io.Serializable;

/**
 * The tile class definition.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public class Tile implements Serializable {

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
   * Copy constructor for Tile.
   * 
   * @param toCopy tile to copy
   */
  public Tile(Tile toCopy) {
    type = toCopy.getType();
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
   * The method that allows us to manually change the type of a specific Tile.
   * 
   * @throws NullPointerException
   * 
   * @param tt The tile type we want to set for the tile.
   */
  public void setTile(TileType tt) throws NullPointerException {
    if (tt == null) {
      throw new NullPointerException("[Class Tile, method setTile]: Null pointer exception");
    }
    type = tt;
  }


  /**
   * {@inheritDoc}}
   * 
   */
  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Tile)) {
      return false;
    }
    Tile tile = (Tile) obj;
    return tile.getType() == type;
  }

  /**
   * {@inheritDoc}}
   * 
   */
  @Override
  public int hashCode() {
    return type.toString().hashCode();
  }

  /**
   * Method that checks if the Tile's {@link TileType} is EMPTY or not.
   * 
   * @return True if the tile has {@link TileType#EMPTY}. 
   * 
   */
  public boolean isEmpty() {
    return (this.getType() == TileType.EMPTY);
  }
}
