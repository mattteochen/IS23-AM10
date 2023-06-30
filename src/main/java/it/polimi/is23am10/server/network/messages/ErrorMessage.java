package it.polimi.is23am10.server.network.messages;

import it.polimi.is23am10.server.model.player.Player;

/**
 * A message containing an error message to be sent to the client.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public final class ErrorMessage extends AbstractMessage {

  /** An utility to be used during deserialization processes. */
  @SuppressWarnings("unused")
  private final String className = this.getClass().getName();

  /** Not mandatory. Not null if direct message. */
  private Player receiver;

  /**
   * Three degrees of severity. Rule of thumb:
   *
   * <ul>
   *   <li>Warning = something changed the flow of the game. User must be aware.
   *   <li>Error = something unexpected happened but game can continue.
   *   <li>Critical = something so bad happened that the game must end.
   * </ul>
   */
  public enum ErrorSeverity {
    INFO,
    WARNING,
    ERROR,
    CRITICAL
  }

  private ErrorSeverity errorSeverity;

  /**
   * Public constructor for ErrorMessage.
   *
   * @param errorMessage message to send.
   * @param receiver player who receives the message.
   */
  public ErrorMessage(String errorMessage, Player receiver, ErrorSeverity errorSeverity) {
    msgType = MessageType.ERROR_MESSAGE;
    this.message = errorMessage;
    this.receiver = receiver;
    this.errorSeverity = errorSeverity;
  }

  /**
   * Public constructor for ErrorMessage in broadcast.
   *
   * @param errorMessage message to send.
   */
  public ErrorMessage(String errorMessage, ErrorSeverity errorSeverity) {
    msgType = MessageType.ERROR_MESSAGE;
    this.message = errorMessage;
    this.errorSeverity = errorSeverity;
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
   * Getter for the severity.
   *
   * @return the severity of the error.
   */
  public ErrorSeverity getErrorSeverity() {
    return errorSeverity;
  }
}
