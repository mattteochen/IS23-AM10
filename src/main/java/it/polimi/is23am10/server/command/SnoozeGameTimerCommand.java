package it.polimi.is23am10.server.command;

/**
 * The snooze game timer command class definition.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public class SnoozeGameTimerCommand extends AbstractCommand {

  /**
   * The player name who has snoozed the game timer.
   * 
   */
  private String playerName;

  /**
   * An utility to be used during deserialization processes.
   * 
   */
  @SuppressWarnings("unused")
  private final String className = this.getClass().getName();

  /**
   * Constructor.
   *
   * @param playerNamE The player name.
   * 
   */
  public SnoozeGameTimerCommand(String playerName) {
    super(Opcode.GAME_TIMER);
    this.playerName = playerName;
  }

  /**
   * Player name getter.
   *
   * @return The player name.
   * 
   */
  public String getPlayerName() {
    return playerName;
  }

  /**
   * {@inheritDoc}
   *
   */
  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof SnoozeGameTimerCommand)) {
      return false;
    }

    SnoozeGameTimerCommand casted = (SnoozeGameTimerCommand) obj;

    return (opcode == casted.getOpcode()
        && playerName.equals(casted.getPlayerName()));
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
