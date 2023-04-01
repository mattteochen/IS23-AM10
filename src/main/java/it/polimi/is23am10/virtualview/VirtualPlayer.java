package it.polimi.is23am10.virtualview;

import java.util.UUID;

import it.polimi.is23am10.items.bookshelf.Bookshelf;
import it.polimi.is23am10.player.Player;
import it.polimi.is23am10.score.Score;

/**
 * A virtual view with the state of a player, downscoped
 * to what is essential for the client to view
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public final class VirtualPlayer {

  /**
   * Unique player identifier
   */
  private UUID playerId;

  /**
   * Player's game name
   */
  private String playerName;

  /**
   * Player's score
   */
  private Score score;

  /**
   * Player's bookshelf with its tiles
   */
  private Bookshelf bookshelf;

  /**
   * 1-12 number referencing the private card to show
   */
  private Integer privateCardIndex;
  
  /**
   * Public constructor. Builds VirtualPlayer out of {@link Player}
   * @param p instance of {@link Player} to "virtualize"
   */
  public VirtualPlayer(Player p) {
    if (p != null) {
      this.playerId = p.getPlayerID();
      this.playerName = p.getPlayerName();
      this.score = p.getScore();
      this.bookshelf = p.getBookshelf();
      this.privateCardIndex = p.getPrivateCard().getPattern().getIndex();
    }
  }

  /**
   * Getter for player id
   * @return player id
   */
  public UUID getPlayerId() {
    return playerId;
  }

  /**
   * Getter for score
   * @return score
   */
  public Score getScore() {
    return score;
  }

  /**
   * Getter for bookshelf
   * @return bookshelf
   */
  public Bookshelf getBookshelf() {
    return bookshelf;
  }

  /**
   * Getter for private card index
   * @return private card index
   */
  public Integer getPrivateCardIndex() {
    return privateCardIndex;
  }

  /**
   * Getter for player name
   * @return player name
   */
  public String getPlayerName() {
    return playerName;
  }

  /**
   * Void method used when pushing state
   * to all players, in order to keep secret
   * each player's card to other players
   */
  public void obfuscatePrivateCard() {
    privateCardIndex = 0;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof VirtualPlayer)) {
      return false;
    }

    VirtualPlayer player = (VirtualPlayer) obj;
    return (playerId.equals(player.getPlayerId())
        && playerName.equals(player.getPlayerName()));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    return playerName.hashCode() * playerId.hashCode();
  }
}
