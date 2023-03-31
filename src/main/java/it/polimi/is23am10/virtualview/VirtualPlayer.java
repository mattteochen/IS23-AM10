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
public class VirtualPlayer {

  private UUID playerId;

  private String playerName;

  private Score score;

  private Bookshelf bookshelf;

  /**
   * 1-12 number referencing the private card to show
   * //TODO: add matching reference in PrivatePattern
   */
  private Integer privateCardIndex;

  public UUID getPlayerId() {
    return playerId;
  }

  public Score getScore() {
    return score;
  }

  public Bookshelf getBookshelf() {
    return bookshelf;
  }

  public Integer getPrivateCardIndex() {
    return privateCardIndex;
  }

  public VirtualPlayer(Player p) {
    if (p != null) {
      this.playerId = p.getPlayerID();
      this.playerName = p.getPlayerName();
      this.score = p.getScore();
      this.bookshelf = p.getBookshelf();
      this.privateCardIndex = p.getPrivateCard().getPattern().getIndex();
    }
  }

  public String getPlayerName() {
    return playerName;
  }

  public void obfuscatePrivateCard() {
    privateCardIndex = 0;
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof VirtualPlayer)) {
      return false;
    }

    VirtualPlayer player = (VirtualPlayer) obj;
    return (playerId.equals(player.getPlayerId())
        && playerName.equals(player.getPlayerName()));
  }
}
