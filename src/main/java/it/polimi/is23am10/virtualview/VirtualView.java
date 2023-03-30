package it.polimi.is23am10.virtualview;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import it.polimi.is23am10.game.Game;
import it.polimi.is23am10.items.board.Board;

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
  
  private UUID gameId;

  private Integer maxPlayers;

  private List<VirtualPlayer> players;

  private VirtualPlayer activePlayer;

  private VirtualPlayer firstPlayer;

  private VirtualPlayer winnerPlayer;

  private Board gameBoard;

  /**
   * 1-12 number referencing the shared cards to show
   * //TODO: add matching reference in PrivatePattern
   */
  private List<Integer> sharedCardsIndexes;

  private boolean ended;

  private boolean lastRound;

  public VirtualView(Game g) {
    this.gameId = g.getGameId();
    this.activePlayer = new VirtualPlayer(g.getActivePlayer());
    this.ended = g.getEnded();
    this.firstPlayer = new VirtualPlayer(g.getFirstPlayer());
    this.gameBoard = g.getGameBoard();
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

  public List<VirtualPlayer> getPlayers() {
    return players;
  }
}
