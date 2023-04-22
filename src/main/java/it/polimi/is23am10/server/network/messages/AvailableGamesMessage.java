package it.polimi.is23am10.server.network.messages;


import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import it.polimi.is23am10.server.model.player.Player;
import it.polimi.is23am10.server.network.virtualview.VirtualView;


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
  protected final Gson gson = new GsonBuilder()
    .create();
  
  /**
   * Not mandatory. Not null if direct message.
   */
  private List<VirtualView> availableGames;

  /**
   * Not mandatory. Not null if direct message.
   */
  private Player receiver;

  /**
   * Public constructor for AvailableGamesMessage in broadcast.
   *
   * @param availableGames games to send.
   */
  public AvailableGamesMessage(List<VirtualView> availableGames, Player receiver) {
    msgType = MessageType.AVAILABLE_GAMES;
    this.availableGames = availableGames;
    this.message = gson.toJson(availableGames);
    this.receiver = receiver;
  }

  /**
   * Available games getter.
   * 
   * @return available games list.
   */
  public List<VirtualView> getAvailableGames() {
    return availableGames;
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
