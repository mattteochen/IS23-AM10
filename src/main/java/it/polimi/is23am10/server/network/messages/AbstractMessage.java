package it.polimi.is23am10.server.network.messages;

import it.polimi.is23am10.server.model.player.Player;

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
   * Enum for type of message sent.
   */
  public enum MessageType {
    GAME_SNAPSHOT,
    CHAT_MESSAGE
  }

  /**
   * Type of message sent.
   */
  protected MessageType msgType;
  
  /**
   * Getter for message.
   * 
   * @return the string message
   */
  public String getMessage() {
    return message;
  }

  /**
   * Getter for message type.
   * 
   * @return message type
   */
  public MessageType getMessageType() {
    return msgType;
  }

  /**
   * Getter for the sending player.
   * 
   * @return the sending player
   */
  public Player getSender(){
    return sender;
  }
}
