package it.polimi.is23am10.items.card;

import it.polimi.is23am10.items.bookshelf.Bookshelf;
import it.polimi.is23am10.items.card.exceptions.AlreadyInitiatedPatternException;
import it.polimi.is23am10.items.card.exceptions.NullScoreBlockListException;
import it.polimi.is23am10.items.scoreblock.ScoreBlock;
import it.polimi.is23am10.pattern.SharedPattern;
import it.polimi.is23am10.pattern.SharedPatternFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Shared card object.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public class SharedCard extends AbstractCard<SharedPattern<Bookshelf>> {

  /**
   * A list of {@link ScoreBlock} instances.
   * They are used to track the score points that the current shared card instance
   * can assign to the players.
   * 
   */
  private List<ScoreBlock> scoreBlocks;

  /**
   * Constructor.
   * 
   * @throws AlreadyInitiatedPatternException
   * 
   */
  public SharedCard() throws AlreadyInitiatedPatternException {
    scoreBlocks = new ArrayList<>();
    // TODO: assign pattern with private pattern factory
    setPattern(SharedPatternFactory.getRandomPattern());
  }

  /**
   * ScoreBlocks setter.
   * 
   * @param scoreBlockList The score block list.
   * @throws NullScoreBlockListException.
   * 
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
   * 
   */
  public List<ScoreBlock> getScoreBlocks() {
    return scoreBlocks;
  }
}
