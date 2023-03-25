package it.polimi.is23am10.command;

/**
 * The abstract command class definition.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public abstract class AbstractCommand {

  /**
   * Opcodes to communicate the action taken by a player.
   * 
   */
  public enum Opcode {
    START,
    ADD_PLAYER,
    MOVE_TILES,
    NULL
  }

  /**
   * The opcode instance for the current command instance.
   * 
   */
  protected Opcode opcode;

  /**
   * The opcode instance for the current command instance.
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
