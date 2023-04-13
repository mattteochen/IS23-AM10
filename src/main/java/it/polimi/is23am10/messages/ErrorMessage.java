package it.polimi.is23am10.messages;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.is23am10.player.Player;


/**
 * A message containing an error message to be sent to the client.
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
   * Public constructor for ErrorMessage.
   *
   * @param errorMessage message to send.
   * @param receiver player who receives the message.
   */
  public ErrorMessage(String errorMessage, Player receiver) {
    msgType = MessageType.ERROR_MESSAGE;
    this.message = errorMessage;
    this.receiver = receiver;
  }

  /**
   * Public constructor for ErrorMessage in broadcast.
   *
   * @param errorMessage message to send.
   */
  public ErrorMessage(String errorMessage) {
    msgType = MessageType.ERROR_MESSAGE;
    this.message = errorMessage;
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
