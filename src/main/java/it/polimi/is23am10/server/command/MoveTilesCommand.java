package it.polimi.is23am10.server.command;

import it.polimi.is23am10.utils.Coordinates;
import it.polimi.is23am10.utils.MoveTileCommandTypeAdaptor;

import java.util.Map;
import java.util.UUID;
import com.google.gson.annotations.JsonAdapter;
import it.polimi.is23am10.server.model.items.board.Board;
import it.polimi.is23am10.server.model.items.bookshelf.Bookshelf;

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
   * The player who calls the operation.
   */
  private String movingPlayer;

  /**
   * Map that associates the position of a picked tile
   * on the {@link Board} to the destination position to move
   * that same tile inside user's {@link Bookshelf}.
   */
  @JsonAdapter(MoveTileCommandTypeAdaptor.class)
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
   * @param movingPlayer The player requesting the move action.
   * @param gameId The game id reference.
   * @param moves The map of moves. See javadoc above.
   */
  public MoveTilesCommand(String movingPlayer, UUID gameId, Map<Coordinates, Coordinates> moves) {
    super(Opcode.MOVE_TILES);
    this.movingPlayer = movingPlayer;
    this.gameId = gameId;
    this.moves = moves;
  }

  /**
   * Moving player getter.
   */
  public String getMovingPlayer() {
    return movingPlayer;
  }

  /**
   * GameId getter.
   */
  public UUID getGameId() {
    return gameId;
  }

  /**
   * Moves map getter.
   */
  public Map<Coordinates, Coordinates> getMoves() {
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
        && movingPlayer.equals(casted.getMovingPlayer())
        && gameId.equals(casted.getGameId())
        && moves.equals(casted.getMoves()));
  }

  /**
   * {@inheritDoc}
   *
   */
  @Override
  public int hashCode() {
    return movingPlayer.hashCode() * gameId.hashCode() * moves.hashCode();
  }
}
