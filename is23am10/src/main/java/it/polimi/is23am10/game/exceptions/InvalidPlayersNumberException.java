package it.polimi.is23am10.game.exceptions;

public class InvalidPlayersNumberException extends Exception {
  public InvalidPlayersNumberException(){
    super("Number of players not matching game's");
  }
}
