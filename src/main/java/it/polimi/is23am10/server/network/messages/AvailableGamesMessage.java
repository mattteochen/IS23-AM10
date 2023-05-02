package it.polimi.is23am10.server.network.messages;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import it.polimi.is23am10.server.model.player.Player;
import it.polimi.is23am10.server.network.virtualview.VirtualView;
import it.polimi.is23am10.utils.ThreadLocalTypeAdapterFactory;

/**
 * A message containing a list of available games to be sent to the client.
 * Note that games are already converted to network-friendly {@link VirtualView}
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public final class AvailableGamesMessage extends AbstractMessage {

  /**
   * Gson object for serialization and deserialization
   */
  protected final transient Gson gson = new GsonBuilder()
.registerTypeAdapterFactory(new ThreadLocalTypeAdapterFactory())
.create();


  /**
   * Player receiving the list.
   */
  private Player receiver;

  /**
   * Public constructor for AvailableGamesMessage in broadcast.
   *
   * @param availableGames games to send.
   * @param receivers the optional receiver player.
   */
  public AvailableGamesMessage(List<VirtualView> availableGames, Player ...receivers) {
    msgType = MessageType.AVAILABLE_GAMES;
    this.message = gson.toJson(availableGames);
    if (receivers != null && receivers.length > 0) {
      this.receiver = receivers[0];
    }
  }

  /**
   * Boolean to check if message is direct or broadcast.
   * Overridden to false as this message can only be private.
   *
   * @return is the message broadcast?
   */
  public boolean isBroadcast() {
    return false;
  }

  /**
   * Getter for the receiving player.
   *
   * @return the player instance
   */
  public Player getReceiver() {
    return receiver;
  }
}
