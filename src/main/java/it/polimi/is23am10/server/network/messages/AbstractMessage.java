package it.polimi.is23am10.server.network.messages;

import java.io.Serializable;

/**
 * Abstract class representing a generic message exchanged.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public class AbstractMessage implements Serializable {

  /**
   * Message in string. Plaintext or JSON.
   */
  protected String message;

  /**
   * Enum for type of message sent.
   */
  public enum MessageType {
    /**
     * Message containing a VirtualView, representing a game snapshot.
     */
    GAME_SNAPSHOT,
    /**
     * Message containing a chat textual exchange between players.
     */
    CHAT_MESSAGE,
    /**
     * Message containing an error to display to the player.
     */
    ERROR_MESSAGE,
    /**
     * Message containing the available games joinable by player.
     */
    AVAILABLE_GAMES,
    /**
     * ACK message sent from server to client to confirm that the timer has
     * been snoozed. If not received by client implies client termination.
     */
    SNOOZE_ACK
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
}
