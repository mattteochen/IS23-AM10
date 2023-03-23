package it.polimi.is23am10.items.card;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;

import it.polimi.is23am10.factory.SharedPatternFactory;
import it.polimi.is23am10.items.bookshelf.Bookshelf;
import it.polimi.is23am10.items.card.exceptions.AlreadyInitiatedPatternException;
import it.polimi.is23am10.items.card.exceptions.NullScoreBlockListException;
import it.polimi.is23am10.items.scoreblock.ScoreBlock;
import it.polimi.is23am10.pattern.SharedPattern;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import org.junit.jupiter.api.Test;

@SuppressWarnings({ "checkstyle:methodname", "checkstyle:abbreviationaswordinnamecheck", "checkstyle:linelengthcheck",
    "checkstyle:onetoplevelclasscheck", "checkstyle:variabledeclarationusagedistancecheck",
    "checkstyle:operatorwrapcheck", "checkstyle:multiplevariabledeclarationscheck", "checkstyle:membernamecheck",
    "checkstyle:nonemptyatclausedescriptioncheck" })
class SharedCardTest {
  /**
   * Green path test for constructor.
   *
   * @throws AlreadyInitiatedPatternException
   */
  @Test
  public void constructor_should_create_SharedCard() throws AlreadyInitiatedPatternException {
    final List<SharedPattern<Predicate<Bookshelf>>> emptyList = new ArrayList<SharedPattern<Predicate<Bookshelf>>>();
    SharedCard sc = new SharedCard(emptyList);

    assertNotNull(sc);
    assertNotNull(sc.getPattern());
    assertEquals(new ArrayList<>(), sc.getScoreBlocks());
  }

  /**
   * Testing setPattern throwing exception, when trying
   * to set a new pattern on an already instantiated one.
   *
   * @throws AlreadyInitiatedPatternException
   */
  @Test
  public void setPattern_should_throw_AlreadyInitiatedPatternException() throws AlreadyInitiatedPatternException {
    final List<SharedPattern<Predicate<Bookshelf>>> emptyList = new ArrayList<SharedPattern<Predicate<Bookshelf>>>();
    SharedCard sc = new SharedCard(emptyList);
    assertThrows(AlreadyInitiatedPatternException.class,
        () -> sc.setPattern(SharedPatternFactory.getNotUsedPattern(emptyList)));
  }

  /**
   * Testing exception when setting a null scoreBlock array.
   *
   * @throws AlreadyInitiatedPatternException
   */
  @Test
  public void setScoreBlocks_should_throw_NullScoreBlockListException() throws AlreadyInitiatedPatternException {
    final List<SharedPattern<Predicate<Bookshelf>>> emptyList = new ArrayList<SharedPattern<Predicate<Bookshelf>>>();
    SharedCard sc = new SharedCard(emptyList);
    assertThrows(NullScoreBlockListException.class, () -> sc.setScoreBlocks(null));
  }

  /**
   * Testing Green Path for setScoreBlocks.
   *
   * @throws AlreadyInitiatedPatternException
   * @throws NullScoreBlockListException
   */
  @Test
  public void setScoreBlocks_should_set_scoreblocks()
      throws AlreadyInitiatedPatternException, NullScoreBlockListException {

    final List<SharedPattern<Predicate<Bookshelf>>> emptyList = new ArrayList<SharedPattern<Predicate<Bookshelf>>>();
    SharedCard sc = new SharedCard(emptyList);
    final ArrayList<ScoreBlock> arr = new ArrayList<ScoreBlock>();

    sc.setScoreBlocks(arr);
    assertEquals(arr, sc.getScoreBlocks());
  }
}
