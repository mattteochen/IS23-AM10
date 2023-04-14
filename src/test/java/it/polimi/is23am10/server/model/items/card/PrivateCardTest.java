package it.polimi.is23am10.server.model.items.card;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import org.junit.jupiter.api.Test;

import it.polimi.is23am10.server.model.factory.PrivatePatternFactory;
import it.polimi.is23am10.server.model.items.bookshelf.Bookshelf;
import it.polimi.is23am10.server.model.items.card.PrivateCard;
import it.polimi.is23am10.server.model.items.card.exceptions.AlreadyInitiatedPatternException;
import it.polimi.is23am10.server.model.items.card.exceptions.NegativeMatchedBlockCountException;
import it.polimi.is23am10.server.model.items.card.exceptions.NullMatchedBlockCountException;
import it.polimi.is23am10.server.model.pattern.PrivatePattern;

@SuppressWarnings({ "checkstyle:methodname", "checkstyle:abbreviationaswordinnamecheck", "checkstyle:linelengthcheck",
    "checkstyle:onetoplevelclasscheck", "checkstyle:variabledeclarationusagedistancecheck",
    "checkstyle:operatorwrapcheck", "checkstyle:multiplevariabledeclarationscheck", "checkstyle:membernamecheck", "checkstyle:nonemptyatclausedescriptioncheck" })
class PrivateCardTest {
  /**
   * Green path test for constructor.
   *
   * @throws AlreadyInitiatedPatternException
   */
  @Test
  public void constructor_should_create_PrivateCard() throws AlreadyInitiatedPatternException {
    final Integer ZERO = 0;
    final List<PrivatePattern<Function<Bookshelf, Integer>>> emptyList = new ArrayList<PrivatePattern<Function<Bookshelf, Integer>>>();
    PrivateCard pc = new PrivateCard(emptyList);

    assertNotNull(pc);
    assertNotNull(pc.getPattern());
    assertEquals(ZERO, pc.getMatchedBlocksCount());
  }

  /**
   * Testing setPattern throwing exception, when trying
   * to set a new pattern on an already instantiated one.
   *
   * @throws AlreadyInitiatedPatternException
   */
  @Test
  public void setPattern_should_throw_AlreadyInitiatedPatternException() throws AlreadyInitiatedPatternException {
    final List<PrivatePattern<Function<Bookshelf, Integer>>> emptyList = new ArrayList<PrivatePattern<Function<Bookshelf, Integer>>>();
    PrivateCard pc = new PrivateCard(emptyList);
    assertThrows(AlreadyInitiatedPatternException.class,
        () -> pc.setPattern(PrivatePatternFactory.getNotUsedPattern(emptyList)));
  }

  /**
   * Testing exception when setting a null number of
   * matched blocks.
   *
   * @throws AlreadyInitiatedPatternException
   */
  @Test
  public void setMatchedBlocksCount_should_throw_NullMatchedBlockCountException()
      throws AlreadyInitiatedPatternException {
    final List<PrivatePattern<Function<Bookshelf, Integer>>> emptyList = new ArrayList<PrivatePattern<Function<Bookshelf, Integer>>>();
    PrivateCard pc = new PrivateCard(emptyList);
    assertThrows(NullMatchedBlockCountException.class, () -> pc.setMatchedBlocksCount(null));
  }

  /**
   * Testing exception when setting a negative number of
   * matched blocks.
   *
   * @throws AlreadyInitiatedPatternException
   */
  @Test
  public void setMatchedBlocksCount_should_throw_NegativeMatchedBlockCountException()
      throws AlreadyInitiatedPatternException {
    final Integer NEGATIVE_VAL = -1;
    final List<PrivatePattern<Function<Bookshelf, Integer>>> emptyList = new ArrayList<PrivatePattern<Function<Bookshelf, Integer>>>();
    PrivateCard pc = new PrivateCard(emptyList);
    assertThrows(NegativeMatchedBlockCountException.class, () -> pc.setMatchedBlocksCount(NEGATIVE_VAL));
  }
}
