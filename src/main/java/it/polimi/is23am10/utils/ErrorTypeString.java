package it.polimi.is23am10.utils;

/**
 * Helper class to identify messages to be sent to client.
 * This will be the content of the message sent to client.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public final class ErrorTypeString {

  /**
   * Private constructor.
   *
   */
  private ErrorTypeString() {

  }

  /**
   * Message error in initializing the game.
   *
   */
  public static final String ERROR_INITIALIZING_NEW_GAME = "Failed to initialize new game";

  /**
   * Message error in adding player to the game.
   *
   */
  public static final String ERROR_ADDING_PLAYERS = "Failed to add player";

  /**
   * Message error in executing a command.
   *
   */
  public static final String ERROR_INTERRUPTED = "Failed to execute";

  /**
   * Message error in executing a move chosen by the client.
   *
   */
  public static final String ERROR_INVALID_MOVE = "Failed to execute the move";

  /**
   * Message error in adding player connector.
   */
  public static final String ERROR_ADDING_CONNECTOR = "Failed to add player connector";

  /**
   * Message error in adding game handler.
   *
   */
  public static final String ERROR_ADDING_HANDLER = "Failed to add game handler";

  /**
   * Message error in adding a new RMI server.
   *
   */
  public static final String ERROR_RMI_EXPOSURE = "Failed to start RMI server";
}
