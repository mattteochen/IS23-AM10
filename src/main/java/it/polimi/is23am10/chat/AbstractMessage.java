package it.polimi.is23am10.chat;

import it.polimi.is23am10.game.exceptions.NullPlayerException;
import it.polimi.is23am10.player.Player;

/**
 * Abstract class representing a generic message exchanged.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public abstract class AbstractMessage {

  /**
   * Message in string. Plaintext or JSON.
   */
  protected String message;

  /**
   * Player sending the message.
   */
  protected Player sender;

  /**
   * Type of message sent.
   */
  public enum MessageType {
    GAME_SNAPSHOT,
    CHAT_MESSAGE
  }

  protected MessageType msgType;
  
  /**
   * Getter for message.
   * @return the string message
   */
  public String getMessage() {
    return message;
  }

  public MessageType getMessageType() {
    return msgType;
  }

  /**
   * Getter for the sending player.
   * @return the sending player
   */
  public Player getSender(){
    return sender;
  }
}
