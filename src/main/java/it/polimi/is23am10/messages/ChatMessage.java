package it.polimi.is23am10.messages;

import it.polimi.is23am10.player.Player;

/**
 * A message sent via chat.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public final class ChatMessage extends AbstractMessage {

  /**
   * Not mandatory. Not null if direct message.
   */
  private Player receiver;

  /**
   * Public constructor for building a direct message.
   * 
   * @param sender the player sending the message
   * @param chatMessage the actual message
   * @param receiver the player receiving the message
   */
  public ChatMessage(Player sender, String chatMessage, Player receiver) {
    msgType = MessageType.CHAT_MESSAGE;
    this.sender = sender;
    message = chatMessage;
    this.receiver = receiver;
  }

  /**
   * Public constructor for building a broadcast message.
   * 
   * @param sender the player sending the message
   * @param chatMessage the actual message
   */
  public ChatMessage(Player sender, String chatMessage){
    msgType = MessageType.CHAT_MESSAGE;
    this.sender = sender;
    message = chatMessage;
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
