package it.polimi.is23am10.items.board.exceptions;

/**
 * GameBoard string given in Board constructor has a not empty tile
 * in a unplayable position.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public class NotEmptyTileInWrongPositionException extends Exception{
  public NotEmptyTileInWrongPositionException(String msg) {
    super(msg);
  }
}