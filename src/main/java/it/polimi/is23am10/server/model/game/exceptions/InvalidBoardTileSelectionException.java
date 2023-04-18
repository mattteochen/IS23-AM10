package it.polimi.is23am10.server.model.game.exceptions;

/**
 * The TileType given is not inside the enum of possible TileType.
 * Can be used in public methods of {@link Tile}.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public class InvalidBoardTileSelectionException extends Exception {
  public InvalidBoardTileSelectionException(String msg) {
    super(msg);
  }
}
