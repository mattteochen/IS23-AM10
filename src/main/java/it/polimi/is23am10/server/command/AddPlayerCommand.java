package it.polimi.is23am10.server.command;

import java.util.UUID;

/**
 * The add new player to game command class definition.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public class AddPlayerCommand extends AbstractCommand {

  /** The player name to add. */
  private String playerName;

  /** The game id reference. */
  private UUID gameId;

  /** An utility to be used during deserialization processes. */
  @SuppressWarnings("unused")
  private final String className = this.getClass().getName();

  /**
   * Constructor.
   *
   * @param playerName The player name to add.
   * @param gameId The game instance id.
   */
  public AddPlayerCommand(String playerName, UUID gameId) {
    super(Opcode.ADD_PLAYER);
    this.playerName = playerName;
    this.gameId = gameId;
  }

  /**
   * Starting player name getter.
   *
   * @return The player name.
   */
  public String getPlayerName() {
    return playerName;
  }

  /**
   * Game id getter.
   *
   * @return The game id reference.
   */
  public UUID getGameId() {
    return gameId;
  }

  /** {@inheritDoc} */
  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof AddPlayerCommand)) {
      return false;
    }

    AddPlayerCommand casted = (AddPlayerCommand) obj;

    return (opcode == casted.getOpcode()
        && playerName.equals(casted.getPlayerName())
        && gameId.equals(casted.getGameId()));
  }

  /** {@inheritDoc} */
  @Override
  public int hashCode() {
    // we can have same player name for multiple games!
    return playerName.hashCode() * gameId.hashCode();
  }
}
