package it.polimi.is23am10.items.card;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import it.polimi.is23am10.factory.PrivatePatternFactory;
import it.polimi.is23am10.items.card.exceptions.AlreadyInitiatedPatternException;
import it.polimi.is23am10.items.card.exceptions.NegativeMatchedBlockCountException;
import it.polimi.is23am10.items.card.exceptions.NullMatchedBlockCountException;

public class PrivateCardTest {
  /**
   * Green path test for constructor.
   * @throws AlreadyInitiatedPatternException
   */
  @Test
  public void constructor_should_create_PrivateCard() throws AlreadyInitiatedPatternException {
    final Integer ZERO = 0;
    PrivateCard pc = new PrivateCard();

    assertNotNull(pc);
    assertNotNull(pc.getPattern());
    assertEquals(ZERO, pc.getMatchedBlocksCount());
  }

  /**
   * Testing setPattern throwing exception, when trying
   * to set a new pattern on an already instantiated one.
   * @throws AlreadyInitiatedPatternException
   */
  @Test
  public void setPattern_should_throw_AlreadyInitiatedPatternException() throws AlreadyInitiatedPatternException {
    PrivateCard pc = new PrivateCard();
    assertThrows(AlreadyInitiatedPatternException.class, () -> pc.setPattern(PrivatePatternFactory.getRandomPattern()));
  }

  /**
   * Testing exception when setting a null number of
   * matched blocks.
   * @throws AlreadyInitiatedPatternException
   */
  @Test
  public void setMatchedBlocksCount_should_throw_NullMatchedBlockCountException() throws AlreadyInitiatedPatternException {
    PrivateCard pc = new PrivateCard();
    assertThrows(NullMatchedBlockCountException.class, () -> pc.setMatchedBlocksCount(null));
  }

  /**
   * Testing exception when setting a negative number of
   * matched blocks.
   * @throws AlreadyInitiatedPatternException
   */
  @Test
  public void setMatchedBlocksCount_should_throw_NegativeMatchedBlockCountException() throws AlreadyInitiatedPatternException {
    final Integer NEGATIVE_VAL = -1;
    PrivateCard pc = new PrivateCard();
    assertThrows(NegativeMatchedBlockCountException.class, () -> pc.setMatchedBlocksCount(NEGATIVE_VAL));
  }
}
