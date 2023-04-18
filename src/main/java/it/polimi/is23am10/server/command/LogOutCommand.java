package it.polimi.is23am10.server.command;

import java.util.UUID;

/**
 * The log out command class definition.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public class LogOutCommand extends AbstractCommand {

  /**
   * The player name who has logged out.
   * 
   */
  private String playerName;

  /**
   * Game id to specify in which match this command
   * is executed. Must match with game currently playing.
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
   * @param playerName The player name who has logged out.
   */
  public LogOutCommand(String playerName) {
    super(Opcode.LOGOUT);
    this.playerName = playerName;
  }

  /**
   * Player name getter.
   *
   * @return The player name.
   */
  public String getPlayerName() {
    return playerName;
  }

  /**
   * GameId getter.
   */
  public UUID getGameId() {
    return gameId;
  }

  /**
   * {@inheritDoc}
   *
   */
  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof LogOutCommand)) {
      return false;
    }

    LogOutCommand casted = (LogOutCommand) obj;

    return (opcode == casted.getOpcode() && playerName.equals(casted.getPlayerName()));
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
