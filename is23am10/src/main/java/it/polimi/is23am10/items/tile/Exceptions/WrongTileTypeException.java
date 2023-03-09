package it.polimi.is23am10.items.tile.Exceptions;

/**
 * The TileType given is not inside the enum of possible TileTypr
 * Can be used public methods of {@link Tile}.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public class WrongTileTypeException extends Exception {
  public WrongTileTypeException(String msg) {
    super(msg);
  }
}
