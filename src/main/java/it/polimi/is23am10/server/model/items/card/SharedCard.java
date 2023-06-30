package it.polimi.is23am10.server.model.items.card;

import it.polimi.is23am10.server.model.factory.SharedPatternFactory;
import it.polimi.is23am10.server.model.items.bookshelf.Bookshelf;
import it.polimi.is23am10.server.model.items.card.exceptions.AlreadyInitiatedPatternException;
import it.polimi.is23am10.server.model.items.card.exceptions.NullScoreBlockListException;
import it.polimi.is23am10.server.model.items.scoreblock.ScoreBlock;
import it.polimi.is23am10.server.model.items.scoreblock.exceptions.NotValidScoreBlockValueException;
import it.polimi.is23am10.server.model.pattern.SharedPattern;
import it.polimi.is23am10.server.model.player.Player;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/**
 * Shared card object.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public class SharedCard
    extends AbstractCard<Predicate<Bookshelf>, SharedPattern<Predicate<Bookshelf>>> {

  /**
   * A list of {@link ScoreBlock} instances. They are used to track the score points that the
   * current shared card instance can assign to the players.
   */
  private List<ScoreBlock> scoreBlocks;

  /**
   * List of players who satisfied the pattern and won the SB. Used to prevent one player from
   * taking two SBs.
   */
  private List<Player> cardWinners;

  /**
   * A map to get the correct list of scoreblocks by number of players playing. Order is reversed as
   * first to pick gets the highest valued SB.
   */
  private Map<Integer, List<Integer>> scoreBlocksMap =
      Map.of(
          2, List.of(8, 4),
          3, List.of(8, 6, 4),
          4, List.of(8, 6, 4, 2));

  /**
   * Constructor.
   *
   * @param usedSharedPatterns is a list of SharedPattern used to store the already used.
   * @param numPlayers the number of players in the game.
   * @throws AlreadyInitiatedPatternException If assigning a pattern to a card that already has one.
   */
  public SharedCard(
      List<SharedPattern<Predicate<Bookshelf>>> usedSharedPatterns, Integer numPlayers)
      throws AlreadyInitiatedPatternException, NotValidScoreBlockValueException {
    cardWinners = new ArrayList<>();
    scoreBlocks = new ArrayList<>();
    for (Integer scoreBlockValue : scoreBlocksMap.get(numPlayers)) {
      scoreBlocks.add(new ScoreBlock(scoreBlockValue));
    }
    setPattern(SharedPatternFactory.getNotUsedPattern(usedSharedPatterns));
  }

  /**
   * ScoreBlocks setter.
   *
   * @param scoreBlockList The score block list.
   * @throws NullScoreBlockListException If the list of scoreblocks is null.
   */
  public void setScoreBlocks(List<ScoreBlock> scoreBlockList) throws NullScoreBlockListException {
    if (scoreBlockList == null) {
      throw new NullScoreBlockListException(
          "[Class " + this.getClass() + ", method setScoreBlock]: Null score block list");
    }
    scoreBlocks = scoreBlockList;
  }

  /**
   * scoreBlocks getter.
   *
   * @return The current score block points assigned to this card instance.
   */
  public List<ScoreBlock> getScoreBlocks() {
    return scoreBlocks;
  }

  /**
   * cardWinners getter.
   *
   * @return The list of player who got a SB from this card.
   */
  public List<Player> getCardWinners() {
    return cardWinners;
  }

  /**
   * Method used to add a player to the winner array. When a player is added it's not allowed to
   * take another scoreblock from this card.
   *
   * @param player the player to add to array
   */
  public void addCardWinner(Player player) {
    cardWinners.add(player);
  }
}
