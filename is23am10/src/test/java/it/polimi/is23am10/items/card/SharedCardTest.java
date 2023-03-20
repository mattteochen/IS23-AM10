package it.polimi.is23am10.items.card;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import it.polimi.is23am10.factory.SharedPatternFactory;
import it.polimi.is23am10.items.card.exceptions.AlreadyInitiatedPatternException;
import it.polimi.is23am10.items.card.exceptions.NullScoreBlockListException;
import it.polimi.is23am10.items.scoreblock.ScoreBlock;

public class SharedCardTest {
  /**
   * Green path test for constructor.
   * @throws AlreadyInitiatedPatternException
   */
  @Test
  public void constructor_should_create_SharedCard() throws AlreadyInitiatedPatternException {
    SharedCard sc = new SharedCard();

    assertNotNull(sc);
    assertNotNull(sc.getPattern());
    assertEquals(new ArrayList<>(), sc.getScoreBlocks());
  }

  /**
   * Testing setPattern throwing exception, when trying
   * to set a new pattern on an already instantiated one.
   * @throws AlreadyInitiatedPatternException
   */
  @Test
  public void setPattern_should_throw_AlreadyInitiatedPatternException() throws AlreadyInitiatedPatternException {
    SharedCard sc = new SharedCard();
    assertThrows(AlreadyInitiatedPatternException.class, () -> sc.setPattern(SharedPatternFactory.getRandomPattern()));
  }

  /**
   * Testing exception when setting a null scoreBlock array.
   * @throws AlreadyInitiatedPatternException
   */
  @Test
  public void setScoreBlocks_should_throw_NullScoreBlockListException() throws AlreadyInitiatedPatternException {
    SharedCard sc = new SharedCard();
    assertThrows(NullScoreBlockListException.class, () -> sc.setScoreBlocks(null));
  }

  /**
   * Testing Green Path for setScoreBlocks.
   * @throws AlreadyInitiatedPatternException
   * @throws NullScoreBlockListException
   */
  @Test
  public void setScoreBlocks_should_set_scoreblocks() 
      throws AlreadyInitiatedPatternException, NullScoreBlockListException {
    
    SharedCard sc = new SharedCard();
    final ArrayList arr = new ArrayList<ScoreBlock>();

    sc.setScoreBlocks(arr);
    assertEquals(arr, sc.getScoreBlocks());
  }
}
