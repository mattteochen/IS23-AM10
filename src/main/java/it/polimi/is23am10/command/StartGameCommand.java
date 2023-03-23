package it.polimi.is23am10.command;

/**
 * The start game command class definition.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public class StartGameCommand extends AbstractCommand {

  /**
   * The player name who has started a game.
   * 
   */
  private String startingPlayerName;

  /**
   * The chosen max player value.
   * 
   */
  private Integer maxPlayers;

  /**
   * An utility to be used during deserialization processes.
   * 
   */
  @SuppressWarnings("unused")
  private final String className = this.getClass().getName();

  /**
   * Constructor.
   *
   * @param startingPlayerName The chosen player name who has started a game request.
   * @param maxPlayers The chosen max player value for the game request.
   * 
   */
  public StartGameCommand(String startingPlayerName, Integer maxPlayers) {
    super(Opcode.START);
    this.startingPlayerName = startingPlayerName;
    this.maxPlayers = maxPlayers;
  }

  /**
   * Starting player name getter.
   *
   * @return The player name.
   * 
   */
  public String getStartingPlayerName() {
    return startingPlayerName;
  }

  /**
   * Max player getter.
   *
   * @return The max player value allowed for the requested game.
   * 
   */
  public Integer getMaxPlayers() {
    return maxPlayers;
  }

  /**
   * {@inheritDoc}
   *
   */
  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof StartGameCommand)) {
      return false;
    }

    StartGameCommand casted = (StartGameCommand) obj;

    return (opcode == casted.getOpcode()
        && startingPlayerName.equals(casted.getStartingPlayerName())
        && maxPlayers == (casted.getMaxPlayers()));
  }

  /**
   * {@inheritDoc}
   *
   */
  @Override
  public int hashCode() {
    return startingPlayerName.hashCode();
  }
}
