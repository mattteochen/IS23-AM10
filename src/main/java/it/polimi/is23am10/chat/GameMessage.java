package it.polimi.is23am10.chat;

import it.polimi.is23am10.game.Game;
import it.polimi.is23am10.player.Player;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * A message containing a serialized JSON of a game instance.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public final class GameMessage extends AbstractMessage {

  /**
   * Gson object for serialization and deserialization
   */
  private final Gson gson = new GsonBuilder()
    .create();
  
  /**
   * Public constructor for game message.
   * Game object is serialized into JSON and set as message.
   * @param sender
   * @param game
   */
  public GameMessage(Player sender, Game game) {
    msgType = MessageType.GAME_SNAPSHOT;
    this.sender = sender; 
    message = gson.toJson(game);
  }

  /**
   * Getter for game instance. It deserializes it from
   * JSON message.
   * @return
   */
  public Game getGame() {
    return gson.fromJson(message, Game.class);
  }
}
