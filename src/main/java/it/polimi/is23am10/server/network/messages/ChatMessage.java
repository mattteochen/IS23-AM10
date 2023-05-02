package it.polimi.is23am10.server.network.messages;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import it.polimi.is23am10.server.model.player.Player;
import it.polimi.is23am10.utils.ThreadLocalTypeAdapterFactory;

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
   * An utility to be used during deserialization processes.
   * 
   */
  @SuppressWarnings("unused")
  private final String className = this.getClass().getName();
  
    /**
   * Gson object for serialization and deserialization
   */
  protected final transient Gson gson = new GsonBuilder()
    .registerTypeAdapterFactory(new ThreadLocalTypeAdapterFactory())
    .create();
  
  /**
   * Not mandatory. Not null if direct message.
   */
  private Player receiver;

  /**
   * Not mandatory, used for alt constructor.
   */
  private String receiverName;


  /**
   * Player sending the message.
   */
  protected Player sender;

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
   * Public constructor for building a direct message.
   * 
   * @param sender the player sending the message
   * @param chatMessage the actual message
   * @param receiver the player name receiving the message
   */
  public ChatMessage(Player sender, String chatMessage, String receiver) {
    msgType = MessageType.CHAT_MESSAGE;
    this.sender = sender;
    message = chatMessage;
    this.receiverName = receiver;
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

  /**
   * Getter for the receiving player.
   * 
   * @return the player instance
   */
  public String getReceiverName() {
    return receiverName;
  }

  /**
   * Getter for the sending player.
   *
   * @return the sending player
   */
  public Player getSender() {
    return sender;
  }

}
