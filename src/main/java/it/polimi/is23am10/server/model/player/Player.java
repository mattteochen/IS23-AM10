package it.polimi.is23am10.server.model.player;

import it.polimi.is23am10.server.model.factory.PlayerFactory;
import it.polimi.is23am10.server.model.items.bookshelf.Bookshelf;
import it.polimi.is23am10.server.model.items.bookshelf.exceptions.BookshelfGridColIndexOutOfBoundsException;
import it.polimi.is23am10.server.model.items.bookshelf.exceptions.BookshelfGridRowIndexOutOfBoundsException;
import it.polimi.is23am10.server.model.items.card.PrivateCard;
import it.polimi.is23am10.server.model.items.card.exceptions.NegativeMatchedBlockCountException;
import it.polimi.is23am10.server.model.items.card.exceptions.NullMatchedBlockCountException;
import it.polimi.is23am10.server.model.items.card.exceptions.NullScoreBlockListException;
import it.polimi.is23am10.server.model.items.scoreblock.ScoreBlock;
import it.polimi.is23am10.server.model.pattern.PrivatePattern;
import it.polimi.is23am10.server.model.player.exceptions.NullPlayerBookshelfException;
import it.polimi.is23am10.server.model.player.exceptions.NullPlayerIdException;
import it.polimi.is23am10.server.model.player.exceptions.NullPlayerNameException;
import it.polimi.is23am10.server.model.player.exceptions.NullPlayerPrivateCardException;
import it.polimi.is23am10.server.model.player.exceptions.NullPlayerScoreBlocksException;
import it.polimi.is23am10.server.model.player.exceptions.NullPlayerScoreException;
import it.polimi.is23am10.server.model.score.Score;
import it.polimi.is23am10.utils.exceptions.NullIndexValueException;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * The Player class definition.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public class Player implements Serializable {

  /**
   * A randomly generated {@link UUID} id.
   * 
   */
  private UUID playerId;

  /**
   * The player name.
   * Chosen by the player itself.
   * 
   */
  private String playerName;

  /**
   * The score storage for the player.
   * It groups all the possible score values that a player can earn during a game.
   * 
   */
  private Score score;

  /**
   * The player's bookshelf playground.
   * 
   */
  private Bookshelf bookshelf;

  /**
   * The player's {@link PrivateCard} with a specific {@link PrivatePattern}.
   * 
   */
  private PrivateCard privateCard;

  /**
   * A list of all {@link ScoreBlock} earned by the player.
   * 
   */
  private List<ScoreBlock> scoreBlocks;

  /**
   * playerId setter.
   *
   * @param playerId The player id.
   * 
   */
  public void setPlayerID(UUID playerId) throws NullPlayerIdException {
    if (playerId == null) {
      throw new NullPlayerIdException("[Class Player, Method setPlayerID]: Null player id");
    }
    this.playerId = playerId;
  }

  /**
   * playerName setter.
   * The {@link PlayerFactory} has the ownership to guarantee the uniqueness of
   * this
   * name in a game instance.
   *
   * @param playerName The player name.
   * 
   */
  public void setPlayerName(String playerName) throws NullPlayerNameException {
    if (playerName == null) {
      throw new NullPlayerNameException("[Class Player, method setPlayerName]: Null player name");
    }
    this.playerName = playerName;
  }

  /**
   * score setter.
   *
   * @param score The score.
   * 
   */
  public void setScore(Score score) throws NullPlayerScoreException {
    if (score == null) {
      throw new NullPlayerScoreException("[Class Player, method setScore]: Null score");
    }
    this.score = score;
  }

  /**
   * bookshelf setter.
   *
   * @param bookshelf The bookshelf.
   * 
   */
  public void setBookshelf(Bookshelf bookshelf) throws NullPlayerBookshelfException {
    if (bookshelf == null) {
      throw new NullPlayerBookshelfException("[Class Player, method setBookshelf]: Null bookshelf");
    }
    this.bookshelf = bookshelf;
  }

  /**
   * privateCard setter.
   *
   * @param privateCard The private card.
   * 
   */
  public void setPrivateCard(PrivateCard privateCard) throws NullPlayerPrivateCardException {
    if (privateCard == null) {
      throw new NullPlayerPrivateCardException("[Class Player, method setPrivateCard]: Null private card");
    }
    this.privateCard = privateCard;
  }

  /**
   * scoreBlocks setter.
   *
   * @param scoreBlocks The score blocks list.
   * 
   */
  public void setScoreBlocks(List<ScoreBlock> scoreBlocks) throws NullPlayerScoreBlocksException {
    if (scoreBlocks == null) {
      throw new NullPlayerScoreBlocksException("[Class Player, method setScoreBlocks]: Null score blocks");
    }
    this.scoreBlocks = scoreBlocks;
  }

  /**
   * Method to add a scoreblock to the player.
   * 
   * @param scoreBlock The scoreblock to add.
   */
  public void addScoreBlock(ScoreBlock scoreBlock) {
    scoreBlocks.add(scoreBlock);
  }

  /**
   * playerId getter.
   *
   * @return The player's id.
   * 
   */
  public UUID getPlayerID() {
    return playerId;
  }

  /**
   * playerName getter.
   *
   * @return The player's name.
   * 
   */
  public String getPlayerName() {
    return playerName;
  }

  /**
   * score getter.
   *
   * @return The player's score.
   * 
   */
  public Score getScore() {
    return score;
  }

  /**
   * bookshelf getter.
   *
   * @return The player's bookshelf.
   * 
   */
  public Bookshelf getBookshelf() {
    return bookshelf;
  }

  /**
   * privateCard getter.
   *
   * @return The player's private card.
   * 
   */
  public PrivateCard getPrivateCard() {
    return privateCard;
  }

  /**
   * scoreBlocks getter.
   *
   * @return The player's score blocks list.
   * 
   */
  public List<ScoreBlock> getScoreBlocks() {
    return scoreBlocks;
  }

  /**
   * Function to be called by {@link Game} at the end of Player's turn.
   * Updates its scores passing their score-giving objects to specific methods.
   * 
   * @throws NullPointerException
   * @throws BookshelfGridColIndexOutOfBoundsException
   * @throws BookshelfGridRowIndexOutOfBoundsException
   * @throws NullIndexValueException
   * @throws NullPlayerBookshelfException
   * @throws NullScoreBlockListException
   * @throws NegativeMatchedBlockCountException
   * @throws NullMatchedBlockCountException
   */
  public void updateScore() throws NullPointerException, BookshelfGridColIndexOutOfBoundsException,
      BookshelfGridRowIndexOutOfBoundsException, NullIndexValueException, NullPlayerBookshelfException,
      NullScoreBlockListException, NullMatchedBlockCountException, NegativeMatchedBlockCountException {
    score.setBookshelfPoints(bookshelf);
    score.setPrivatePoints(bookshelf, privateCard);
    score.setScoreBlockPoints(scoreBlocks);
  }

  /**
   * {@inheritDoc}
   * 
   */
  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Player)) {
      return false;
    }
    Player player = (Player) obj;
    return (playerId.equals(player.getPlayerID())
        && playerName.equals(player.getPlayerName()));
  }

  /**
   * {@inheritDoc}
   * 
   */
  @Override
  public int hashCode() {
    return playerName.hashCode() * playerId.hashCode();
  }
}