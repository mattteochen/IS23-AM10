package it.polimi.is23am10.server.network.virtualview;

import it.polimi.is23am10.server.model.game.Game;
import it.polimi.is23am10.server.model.game.Game.GameStatus;
import it.polimi.is23am10.server.model.items.board.Board;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * A virtual view with the state of the game, downscoped to what is essential for the client to view
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public final class VirtualView implements Serializable {

  /** Unique Game identifier */
  private UUID gameId;

  /** Maximum number of players for this game */
  private Integer maxPlayers;

  /** List of virtual players in the game */
  private List<VirtualPlayer> players;

  /** VirtualPlayer currently playing */
  private VirtualPlayer activePlayer;

  /** VirtualPlayer choosen as first */
  private VirtualPlayer firstPlayer;

  /** VirtualPlayer who won the game */
  private VirtualPlayer winnerPlayer;

  /** Instance of board where tiles are */
  private Board gameBoard;

  /** List of the two shared cards index for this game session. */
  private List<Integer> sharedCards;

  /** Boolean flag signaling game is over */
  private GameStatus status;

  /**
   * Getter for game id
   *
   * @return game id
   */
  public UUID getGameId() {
    return gameId;
  }

  /**
   * Getter for player list ({@link VirtualPlayer})
   *
   * @return player list
   */
  public List<VirtualPlayer> getPlayers() {
    return players;
  }

  /**
   * Getter for max players
   *
   * @return max players
   */
  public Integer getMaxPlayers() {
    return maxPlayers;
  }

  /**
   * Getter for active player
   *
   * @return active player
   */
  public VirtualPlayer getActivePlayer() {
    return activePlayer;
  }

  /**
   * Getter for first player
   *
   * @return first player
   */
  public VirtualPlayer getFirstPlayer() {
    return firstPlayer;
  }

  /**
   * Getter for winner player
   *
   * @return winner player
   */
  public VirtualPlayer getWinnerPlayer() {
    return winnerPlayer;
  }

  /**
   * Getter for game board
   *
   * @return game board
   */
  public Board getGameBoard() {
    return gameBoard;
  }

  /**
   * Getter for shared cards indexes
   *
   * @return Pairs of shared cards indexes and descriptions.
   */
  public List<Integer> getSharedCards() {
    return sharedCards;
  }

  /**
   * Getter for status.
   *
   * @return game status.
   */
  public GameStatus getStatus() {
    return status;
  }

  /**
   * Simple helper function to get the number of disconnected players to discount when looking for
   * available games.
   *
   * @return disconnected player num.
   */
  public synchronized Integer getDisconnectedPlayersNum() {
    return (int) players.stream().filter(p -> !p.getIsConnected()).count();
  }

  /**
   * Public constructor. Builds VirtualView out of {@link Game}
   *
   * @param g instance of {@link Game} to "virtualize"
   */
  public VirtualView(Game g) {
    this.gameId = g.getGameId();
    this.activePlayer = g.getActivePlayer() == null ? null : new VirtualPlayer(g.getActivePlayer());
    this.firstPlayer = g.getFirstPlayer() == null ? null : new VirtualPlayer(g.getFirstPlayer());
    this.gameBoard = new Board(g.getGameBoard());
    this.status = g.getStatus();
    this.maxPlayers = g.getMaxPlayer();
    this.players =
        g.getPlayers().stream().map(p -> new VirtualPlayer(p)).collect(Collectors.toList());

    this.sharedCards =
        g.getSharedCard().stream().map(c -> c.getPattern().getIndex()).collect(Collectors.toList());

    this.winnerPlayer = g.getWinnerPlayer() == null ? null : new VirtualPlayer(g.getWinnerPlayer());
  }

  /** {@inheritDoc} */
  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof VirtualView)) {
      return false;
    }

    VirtualView view = (VirtualView) obj;
    return (gameId.equals(view.getGameId()));
  }

  /** {@inheritDoc} */
  @Override
  public int hashCode() {
    return gameId.hashCode();
  }
}
