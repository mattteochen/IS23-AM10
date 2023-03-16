package it.polimi.is23am10.server.controller;

import it.polimi.is23am10.server.command.AbstractCommand.Opcode;

/**
 * The server debug prefixes string interface definition.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public final class ServerDebugPrefixString {
  /**
   * Private constructor.
   *
   */
  private ServerDebugPrefixString() {

  }

  /**
   * {@link Opcode#START} prefix string.
   *
   */
  public static final String START_COMMAND_PREFIX = "START ->";
}
