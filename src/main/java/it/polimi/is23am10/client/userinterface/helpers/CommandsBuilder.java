package it.polimi.is23am10.client.userinterface.helpers;

/**
 * Game mode string constants.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public final class CommandsBuilder {

  /** The command string for creating a game. */
  public static final String CREATE_GAME = "c";

  /** The command string for joining a game. */
  public static final String JOIN_GAME = "j";

  /** The command string for moving a tile. */
  public static final String MOVE_TILE = "move";

  /** The command string for sending a message */
  public static final String SEND_MESSAGE = "chat";

  /**
   * Builds a create game command with the specified player name.
   *
   * @param pn The player name.
   * @return The create game command string.
   */
  public static final String buildCreateGameCmd(String pn) {
    return CREATE_GAME + " " + pn;
  }

  /**
   * Builds a join game command with the specified game ID.
   *
   * @param id The game ID.
   * @return The join game command string.
   */
  public static final String buildJoinGameCmd(String id) {
    return JOIN_GAME + " " + id;
  }

  /**
   * Builds a move tile command with the specified move.
   *
   * @param move The move to be performed.
   * @return The move tile command string.
   */
  public static final String moveTileCmd(String move) {
    return MOVE_TILE + " " + move;
  }

  /**
   * Builds a chat message and sends it. The syntax for the chat messages is the following:
   * Broadcast: "textContent" To player: "> playerName textContent"
   *
   * @param msg The msg to be sent.
   * @return The send message command string.
   */
  public static final String sendChatMessageCmd(String msg) {
    if (msg.startsWith(">")) {
      String receiverName = msg.stripLeading().split(" ")[1];
      int indexToTrim = msg.stripLeading().indexOf(" ", msg.stripLeading().indexOf(" ") + 1);
      String msgTextTrimmed = msg.stripLeading().trim().substring(indexToTrim);
      return SEND_MESSAGE + " " + receiverName + " \"" + msgTextTrimmed + "\" ";
    }
    return SEND_MESSAGE + " \"" + msg + "\" ";
  }
}
