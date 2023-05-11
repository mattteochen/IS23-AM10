package it.polimi.is23am10.server.command;

import java.util.UUID;

import it.polimi.is23am10.server.network.playerconnector.AbstractPlayerConnector;

/**
 * The player's log out command.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public class LogOutCommand extends AbstractCommand {
  /**
   * The player name to log out.
   * 
   */
  private String playerName;

  /**
   * The game id reference.
   * 
   */
  private UUID gameId;

  /**
   * An utility to be used during deserialization processes.
   * 
   */
  @SuppressWarnings("unused")
  private final String className = this.getClass().getName();

  /**
   * Constructor.
   *
   * @param playerName The player name to log out.
   * @param gameId     The game instance id.
   * 
   */
  public LogOutCommand(String playerName, UUID gameId) {
    super(Opcode.LOG_OUT);
    this.playerName = playerName;
    this.gameId = gameId;
  }

  /**
   * {@inheritDoc}
   *
   */
  @Override
  public boolean equals(Object obj) {
    return (obj instanceof LogOutCommand);
  }

  /**
   * {@inheritDoc}
   *
   */
  @Override
  public int hashCode() {
    return playerName.hashCode();
  }
}
