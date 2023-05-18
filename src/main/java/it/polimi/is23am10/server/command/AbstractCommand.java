package it.polimi.is23am10.server.command;

import java.io.Serializable;

/**
 * The abstract command class definition.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public abstract class AbstractCommand implements Serializable {

  /**
   * Opcodes to communicate the action taken by a player.
   * 
   */
  public enum Opcode {
    /**
     * Start a new game command.
     */
    START,
    /**
     * Add a new player to existing game command.
     */
    ADD_PLAYER,
    /**
     * Move tiles from board to user's bookshelf command.
     */
    MOVE_TILES,
    /**
     * Retrieve existing games to join command.
     */
    GET_GAMES,
    /**
     * Send chat message command
     */
    SEND_CHAT_MESSAGE,
    /**
     * Game timer snooze command.
     */
    GAME_TIMER,
    /**
     * Null command.
     */
    NULL,
    /**
     * Logout command.
     */
    LOGOUT
  }

  /**
   * The opcode instance for the current command instance.
   * 
   */
  protected Opcode opcode;

  /**
   * The opcode instance for the current command instance.
   * 
   * @param opcode command op code.
   * 
   */
  protected AbstractCommand(Opcode opcode) {
    this.opcode = opcode != null ? opcode : Opcode.NULL;
  }

  /**
   * Opcode getter.
   *
   * @return The current command Opcode.
   * 
   */
  public Opcode getOpcode() {
    return opcode;
  }
}
