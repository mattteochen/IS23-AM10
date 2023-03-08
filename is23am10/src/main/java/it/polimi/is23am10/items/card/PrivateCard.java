package it.polimi.is23am10.items.card;

import it.polimi.is23am10.items.card.exceptions.AlreadyInitiatedPatternException;
import it.polimi.is23am10.items.card.exceptions.NegativeMatchedBlockCountException;
import it.polimi.is23am10.items.card.exceptions.NullMatchedBlockCountException;
import it.polimi.is23am10.pattern.PrivatePattern;

/**
 * Private card class definition.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public class PrivateCard extends AbstractCard<PrivatePattern> {

  /**
   * A counter for the number of matched blocks in the {@link PrivatePattern}.
   * 
   */
  private Integer matchedBlocksCount;

  /**
   * Constructor.
   * 
   * @throws AlreadyInitiatedPatternException
   * 
   */
  public PrivateCard() throws AlreadyInitiatedPatternException {
    matchedBlocksCount = 0;
    // TODO: assign pattern with private pattern factory
    setPattern(new PrivatePattern());
  }

  /**
   * matchedBlocksCount setter.
   * 
   * @param matchedBlocksCount The value about how many matches the played has
   *                           achieved inside his/her private card.
   * @throws NullMatchedBlockCountException
   * @throws NegativeMatchedBlockCountException
   * 
   */
  public void setMatchedBlocksCount(Integer matchedBlocksCount)
      throws NullMatchedBlockCountException, NegativeMatchedBlockCountException {
    if (matchedBlocksCount == null) {
      throw new NullMatchedBlockCountException(
          "[Class " + this.getClass() + ", method setMatchedBlocksCount]: Null matchedBlocksCount");
    }
    if (matchedBlocksCount < 0) {
      throw new NegativeMatchedBlockCountException(
          "[Class " + this.getClass() + ", method setMatchedBlocksCount]: Negative matchedBlocksCount");
    }
    this.matchedBlocksCount = matchedBlocksCount;
  }

  /**
   * matchedBlocksCount getter.
   * 
   * @return The value of matched blocks inside the user private card.
   * 
   */
  public Integer getMatchedBlocksCount() {
    return matchedBlocksCount;
  }
}
