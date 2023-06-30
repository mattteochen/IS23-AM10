package it.polimi.is23am10.utils;

import it.polimi.is23am10.server.network.messages.ErrorMessage.ErrorSeverity;

/**
 * Helper class to identify messages to be sent to client and to logger. Some messages might be sent
 * to client only, hiding complexity where not needed, others might be sent to server only for same
 * reasons. Please check each string's javadoc.
 *
 * <p>The error convention used is the same as {@link ErrorSeverity}
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public final class ErrorTypeString {

  /** Private constructor. */
  private ErrorTypeString() {}

  /** Message error in initializing the game. */
  public static final String ERROR_INITIALIZING_NEW_GAME =
      "Failed to initialize new game. Please try again.";

  /** Message error in adding player to the game. */
  public static final String ERROR_ADDING_PLAYERS =
      "Failed to add player. Please consider re-joining.";

  /** Message error in adding player to the game. */
  public static final String ERROR_JOINING = "Failed to add player. Please consider re-joining.";

  /** Message error on failure while pushing messages in player queues. */
  public static final String ERROR_MESSAGE_DELIVERY = "Thread interruption or Remote call failure";

  /** Message error in executing a move chosen by the client. */
  public static final String ERROR_INVALID_MOVE = "Failed to execute the moves. Please try again.";

  /** Message error to be shown client-side when trying to join a game already full. */
  public static final String ERROR_GAME_FULL =
      "This game is already full. Please join another game or create one.";

  /**
   * Message error to be shown client-side to hide server-side controller failures that the user
   * can't help fixing (player connectors, gamehandlers, networking...).
   */
  public static final String ERROR_SERVER_SIDE = "Generic server-side failure.";

  /** Message warning sent to all players to inform them of a new player joining the game. */
  public static final String WARNING_PLAYER_JOIN = "Player %s joined the game!";

  /** Message warning logged when new player is joining the game. */
  public static final String WARNING_PLAYER_JOIN_SERVER = "Player %s joined the game %s";

  /** Message warning sent to all players to inform them of a new player joining the game. */
  public static final String WARNING_PLAYER_REJOIN = "Player %s rejoined the game! Welcome back.";

  /** Message warning sent to all players to inform them of a player leaving the game. */
  public static final String WARNING_PLAYER_DISCONNECT =
      "Player %s disconnected from the game. Their turn will be skipped.";

  /** Message error in adding player connector. */
  public static final String ERROR_ADDING_CONNECTOR = "Failed to add player connector";

  /** Message error in retrieving socket connector. */
  public static final String ERROR_SOCKET_CONNECTOR = "Failed to get socket connector";

  /** Message error in adding game handler. */
  public static final String ERROR_ADDING_HANDLER = "Failed to add game handler";

  /** Message error on failure while pushing game updates in player queues. */
  public static final String ERROR_UPDATING_GAME = "Failed to deliver game snapshot to user.";

  /** Message error in adding a new RMI player connector. */
  public static final String ERROR_RMI_EXPOSURE = "Failed to rebind RMI player connector";

  /** Message error in snoozing game time handler. */
  public static final String ERROR_SNOOZING_TIMER = "Failed snooze player timer";

  /** Message error in game model fault. */
  public static final String ERROR_GAME_STATE = "Severe game state failure";

  /** Message error in sending message to player name not existing. */
  public static final String RECEIVER_NOT_FOUND = "Player not found, message couldn't be delivered";
}
