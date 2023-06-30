package it.polimi.is23am10.server.model.items.card;

import it.polimi.is23am10.server.model.factory.PrivatePatternFactory;
import it.polimi.is23am10.server.model.items.bookshelf.Bookshelf;
import it.polimi.is23am10.server.model.items.card.exceptions.AlreadyInitiatedPatternException;
import it.polimi.is23am10.server.model.items.card.exceptions.NegativeMatchedBlockCountException;
import it.polimi.is23am10.server.model.items.card.exceptions.NullMatchedBlockCountException;
import it.polimi.is23am10.server.model.pattern.PrivatePattern;
import java.util.List;
import java.util.function.Function;

/**
 * Private card class definition.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public class PrivateCard
    extends AbstractCard<
        Function<Bookshelf, Integer>, PrivatePattern<Function<Bookshelf, Integer>>> {

  /** A counter for the number of matched blocks in the {@link PrivatePattern}. */
  private Integer matchedBlocksCount;

  /**
   * Constructor.
   *
   * @param usedPrivatePatterns is a list of PrivatePattern used to store the already used one.
   * @throws AlreadyInitiatedPatternException If assigning a pattern to a card that already has one.
   */
  public PrivateCard(List<PrivatePattern<Function<Bookshelf, Integer>>> usedPrivatePatterns)
      throws AlreadyInitiatedPatternException {
    matchedBlocksCount = 0;
    setPattern(PrivatePatternFactory.getNotUsedPattern(usedPrivatePatterns));
  }

  /**
   * Empty constructor. To be used when obscuring private card of other players when sending game
   * updates
   */
  public PrivateCard() {}

  /**
   * matchedBlocksCount setter.
   *
   * @param matchedBlocksCount The value about how many matches the played has achieved inside
   *     his/her private card.
   * @throws NullMatchedBlockCountException If the number of matched blocks to set is null.
   * @throws NegativeMatchedBlockCountException If the number of matched blocks to set is negative.
   */
  public void setMatchedBlocksCount(Integer matchedBlocksCount)
      throws NullMatchedBlockCountException, NegativeMatchedBlockCountException {
    if (matchedBlocksCount == null) {
      throw new NullMatchedBlockCountException(
          "[Class " + this.getClass() + ", method setMatchedBlocksCount]: Null matchedBlocksCount");
    }
    if (matchedBlocksCount < 0) {
      throw new NegativeMatchedBlockCountException(
          "[Class "
              + this.getClass()
              + ", method setMatchedBlocksCount]: Negative matchedBlocksCount");
    }
    this.matchedBlocksCount = matchedBlocksCount;
  }

  /**
   * matchedBlocksCount getter.
   *
   * @return The value of matched blocks inside the user private card.
   */
  public Integer getMatchedBlocksCount() {
    return matchedBlocksCount;
  }
}
