package it.polimi.is23am10.server.network.messages;

/**
 * Class representing a ACK message sent from server to client in response to a snooze command. Used
 * to keep alice client.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public class SnoozeACKMessage extends AbstractMessage {

  /** An utility to be used during deserialization processes. */
  @SuppressWarnings("unused")
  private final String className = this.getClass().getName();

  /** Public constructor. */
  public SnoozeACKMessage() {
    msgType = MessageType.SNOOZE_ACK;
  }
}
