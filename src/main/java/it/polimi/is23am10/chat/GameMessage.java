package it.polimi.is23am10.chat;

import it.polimi.is23am10.player.Player;
import it.polimi.is23am10.virtualview.VirtualView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * A message containing a serialized JSON of a virtual view instance.
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
  protected final Gson gson = new GsonBuilder()
    .create();
  
  /**
   * Public constructor for virtualView message.
   * Game object is serialized into JSON and set as message.
   * @param sender
   * @param virtualView
   */
  public GameMessage(Player sender, VirtualView virtualView) {
    msgType = MessageType.GAME_SNAPSHOT;
    this.sender = sender; 
    message = gson.toJson(virtualView);
  }

  /**
   * Getter for virtualView instance. It deserializes it from
   * JSON message.
   * @return
   */
  public VirtualView getGame() {
    return gson.fromJson(message, VirtualView.class);
  }
}
