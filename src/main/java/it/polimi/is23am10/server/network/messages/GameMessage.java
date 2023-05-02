package it.polimi.is23am10.server.network.messages;

import it.polimi.is23am10.server.network.virtualview.VirtualView;
import it.polimi.is23am10.utils.ThreadLocalTypeAdapterFactory;

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
  protected final transient Gson gson = new GsonBuilder()
    .registerTypeAdapterFactory(new ThreadLocalTypeAdapterFactory())
    .create();
  
  /**
   * Public constructor for virtualView message.
   * Game object is serialized into JSON and set as message.
   * 
   * @param virtualView virtual view to serialize
   */
  public GameMessage(VirtualView virtualView) {
    msgType = MessageType.GAME_SNAPSHOT;
    message = gson.toJson(virtualView);
  }

  /**
   * Getter for virtualView instance. It deserializes it from
   * JSON message.
   * 
   * @return deserialized virtual view object
   */
  public VirtualView getGame() {
    return gson.fromJson(message, VirtualView.class);
  }
}
