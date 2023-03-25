package it.polimi.is23am10.command;

import java.util.Map;
import java.util.UUID;

import it.polimi.is23am10.utils.Coordinates;

/**
 * The move tiles command class definition.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public class MoveTilesCommand extends AbstractCommand {

  /**
   * Map that associates the position of a picked tile
   * on the {@link Board} to the destination position to move
   * that same tile inside user's {@link Bookshelf}.
   */
  private Map<Coordinates, Coordinates> moves;

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
   * Public constructor.
   * 
   * @param moves The map of moves. See javadoc above.
   */
  public MoveTilesCommand(UUID gameId, Map<Coordinates, Coordinates> moves) {
    super(Opcode.MOVE_TILES);
    this.gameId = gameId;
    this.moves = moves;
  }

  /**
   * GameId getter.
   */
  public UUID getGameId(){
    return gameId;
  }

  /**
   * Moves map getter.
   */
  public Map<Coordinates,Coordinates> getMoves(){
    return moves;
  }

    /**
   * {@inheritDoc}
   *
   */
  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof MoveTilesCommand)) {
      return false;
    }

    MoveTilesCommand casted = (MoveTilesCommand) obj;

    return (opcode == casted.getOpcode()
        && gameId.equals(casted.getGameId())
        && moves.equals(casted.getMoves()));
  }

  /**
   * {@inheritDoc}
   *
   */
  @Override
  public int hashCode() {
    return gameId.hashCode() * moves.hashCode();
  }
  
}
