package it.polimi.is23am10.virtualview;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import it.polimi.is23am10.game.Game;
import it.polimi.is23am10.items.board.Board;
import it.polimi.is23am10.items.bookshelf.exceptions.BookshelfGridColIndexOutOfBoundsException;
import it.polimi.is23am10.items.bookshelf.exceptions.BookshelfGridRowIndexOutOfBoundsException;
import it.polimi.is23am10.utils.exceptions.NullIndexValueException;

/**
 * A virtual view with the state of the game, downscoped
 * to what is essential for the client to view
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public final class VirtualView {
  
  /**
   * Unique Game identifier
   */
  private UUID gameId;

  /**
   * Maximum number of players for this game
   */
  private Integer maxPlayers;

  /**
   * List of virtual players in the game
   */
  private List<VirtualPlayer> players;

  /**
   * VirtualPlayer currently playing
   */
  private VirtualPlayer activePlayer;

  /**
   * VirtualPlayer choosen as first
   */
  private VirtualPlayer firstPlayer;

  /**
   * VirtualPlayer who won the game
   */
  private VirtualPlayer winnerPlayer;

  /**
   * Instance of board where tiles are
   */
  private Board gameBoard;

  /**
   * 1-12 number referencing the shared cards to show
   */
  private List<Integer> sharedCardsIndexes;
  
  /**
   * Boolean flag signaling game is over
   */
  private boolean ended;

  /**
   * Boolean flag signaling game is in its
   * last round.
   */
  private boolean lastRound;
  
  /**
   * Getter for game id
   * @return game id
   */
  public UUID getGameId() {
    return gameId;
  }

  /**
   * Getter for player list ({@link VirtualPlayer})
   * @return player list
   */
  public List<VirtualPlayer> getPlayers() {
    return players;
  }

  /**
   * Getter for max players
   * @return max players
   */
  public Integer getMaxPlayers() {
    return maxPlayers;
  }

  /**
   * Getter for active player
   * @return active player
   */
  public VirtualPlayer getActivePlayer() {
    return activePlayer;
  }

  /**
   * Getter for first player
   * @return first player
   */
  public VirtualPlayer getFirstPlayer() {
    return firstPlayer;
  }

  /**
   * Getter for winner player
   * @return winner player
   */
  public VirtualPlayer getWinnerPlayer() {
    return winnerPlayer;
  }

  /**
   * Getter for game board
   * @return game board
   */
  public Board getGameBoard() {
    return gameBoard;
  }

  /**
   * Getter for shared cards indexes
   * @return shared cards indexes
   */
  public List<Integer> getSharedCardsIndexes() {
    return sharedCardsIndexes;
  }

  /**
   * Getter for ended
   * @return is game ended
   */
  public boolean isEnded() {
    return ended;
  }

  /**
   * Getter for last round
   * @return is game in last round
   */
  public boolean isLastRound() {
    return lastRound;
  }



  /**
   * Public constructor. Builds VirtualView out of {@link Game}
   * @param g instance of {@link Game} to "virtualize"
   */
  public VirtualView(Game g) {
    this.gameId = g.getGameId();
    this.activePlayer = g.getActivePlayer() == null ? null : new VirtualPlayer(g.getActivePlayer());
    this.ended = g.getEnded();
    this.firstPlayer = g.getFirstPlayer() == null ? null : new VirtualPlayer(g.getFirstPlayer());
    this.gameBoard = new Board(g.getGameBoard());
    this.lastRound = g.isLastRound();
    this.maxPlayers = g.getMaxPlayer();
    this.players = g.getPlayers()
      .stream()
      .map(p -> new VirtualPlayer(p))
      .collect(Collectors.toList());
    this.sharedCardsIndexes = g.getSharedCard()
      .stream()
      .map(c -> c.getPattern().getIndex())
      .collect(Collectors.toList());
    this.winnerPlayer = g.getWinnerPlayer() == null ? null : new VirtualPlayer(g.getWinnerPlayer());
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof VirtualView)) {
      return false;
    }

    VirtualView view = (VirtualView) obj;
    return (
      gameId.equals(view.getGameId())
    );
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    return gameId.hashCode();
  }
}
