package it.polimi.is23am10.items.tile;

import it.polimi.is23am10.items.tile.Exceptions.WrongTileTypeException;

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
    PLANT,
    EMPTY
  }

  /**
   * The instance Tile type.
   * 
   */
  private TileType type;

  /**
   * The constructor given the {@link TileType} we want to set.
   * 
   * @throws NullPointerException
   * @throws WrongTileTypeException
   * 
   * @param tt The tile type we want to set for the tile.
   */
  public Tile(TileType tt) throws NullPointerException, WrongTileTypeException {
    if (tt == null) {
      throw new NullPointerException("[Class Tile, method constructor]: Null pointer exception");
    }
    if (tt != TileType.CAT && tt != TileType.BOOK && tt != TileType.GAME && tt != TileType.FRAME
        && tt != TileType.TROPHY && tt != TileType.PLANT && tt != TileType.EMPTY) {
      throw new WrongTileTypeException("[Class Tile, method constructor]: Wrong TileType exception");
    }
    this.type = tt;
  }

  /**
   * The method that returns us the type of the Tile
   * 
   * @return tile type.
   */
  public TileType getType() {
    return type;
  }

  /**
   * The method that allows us to manually change the type of a specific Tile.
   * 
   * @throws NullPointerException
   * @throws WrongTileTypeException
   * 
   * @param tt The tile type we want to set for the tile.
   */
  public void setTile(TileType tt) throws NullPointerException, WrongTileTypeException {
    if (tt == null) {
      throw new NullPointerException("[Class Tile, method setTile]: Null pointer exception");
    }
    if (!isValidTileType(tt)) {
      throw new WrongTileTypeException("[Class Tile, method setTile]: Wrong TileType exception");
    }
    type = tt;
  }


  /**
   * The method that allows us to compare two Tiles.
   * 
   * @param t The tile we want to have a comparison with.
   * 
   * @return True if the Tiles have the same type.
   */
  public boolean equals(Tile t) throws NullPointerException {
    if (t == null) {
      throw new NullPointerException("[Class Tile, method equals]: Null pointer exception");
    }
    return (getType() == t.getType());
  }

  /**
   * Method that checks if the Tile's {@link TileType} is EMPTY or not.
   * 
   * @return True if the tile has {@link TileType#EMPTY}. 
   * 
   */
  public boolean isEmpty() {
    if (this.getType() == TileType.EMPTY) {
      return true;
    }
    return false;
  }

  /**
   * Method checking if the {@link TileType} of a tile is in the enum of possible tile types.
   * 
   * @param tt tile type we want to check.
   * @return True if the tile type is valid.
   */
  public boolean isValidTileType(TileType tt){
    if(tt != TileType.CAT && tt != TileType.BOOK && tt != TileType.GAME && tt != TileType.FRAME
    && tt != TileType.TROPHY && tt != TileType.PLANT && tt != TileType.EMPTY){
      return false;
    }
    return true;
  }
}
