package it.polimi.is23am10.messages;

import it.polimi.is23am10.messages.AbstractMessage.MessageType;
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
public final class ErrorMessage extends AbstractMessage {

  /**
   * Not mandatory. Not null if direct message.
   */
  private Player receiver;

  /**
   * Gson object for serialization and deserialization
   */
  protected final Gson gson = new GsonBuilder()
      .create();

  /**
   * Public constructor for ErrorMessage.
   * Error object is serialized into JSON and set as message.
   * 
   * @param chatMessage message to send.
   * @param receiver player who receives the message.
   */
  public ErrorMessage(String chatMessage, Player receiver) {
    msgType = MessageType.ERROR_MESSAGE;
    this.message = chatMessage;
    this.receiver = receiver;
  }

  /**
   * Public constructor for ErrorMessage in broadcast.
   * Error object is serialized into JSON and set as message.
   * 
   * @param chatMessage message to send.
   */
  public ErrorMessage(String chatMessage) {
    msgType = MessageType.ERROR_MESSAGE;
    this.message = chatMessage;
  }

    /**
   * Boolean to check if message is direct or broadcast.
   * 
   * @return is the message broadcast?
   */
  public boolean isBroadcast() {
    return receiver == null;
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
