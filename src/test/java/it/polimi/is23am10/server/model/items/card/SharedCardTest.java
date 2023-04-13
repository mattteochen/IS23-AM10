package it.polimi.is23am10.server.model.items.card;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import org.junit.jupiter.api.Test;

import it.polimi.is23am10.server.model.factory.SharedPatternFactory;
import it.polimi.is23am10.server.model.items.bookshelf.Bookshelf;
import it.polimi.is23am10.server.model.items.card.SharedCard;
import it.polimi.is23am10.server.model.items.card.exceptions.AlreadyInitiatedPatternException;
import it.polimi.is23am10.server.model.items.card.exceptions.NullScoreBlockListException;
import it.polimi.is23am10.server.model.items.scoreblock.ScoreBlock;
import it.polimi.is23am10.server.model.items.scoreblock.exceptions.NotValidScoreBlockValueException;
import it.polimi.is23am10.server.model.pattern.SharedPattern;

@SuppressWarnings({ "checkstyle:methodname", "checkstyle:abbreviationaswordinnamecheck", "checkstyle:linelengthcheck",
    "checkstyle:onetoplevelclasscheck", "checkstyle:variabledeclarationusagedistancecheck",
    "checkstyle:operatorwrapcheck", "checkstyle:multiplevariabledeclarationscheck", "checkstyle:membernamecheck",
    "checkstyle:nonemptyatclausedescriptioncheck" })
class SharedCardTest {
  /**
   * Green path test for constructor.
   *
   * @throws AlreadyInitiatedPatternException
   * @throws NotValidScoreBlockValueException
   */
  @Test
  public void constructor_should_create_SharedCard() throws AlreadyInitiatedPatternException, NotValidScoreBlockValueException {
    final List<SharedPattern<Predicate<Bookshelf>>> emptyList = new ArrayList<SharedPattern<Predicate<Bookshelf>>>();
    SharedCard sc = new SharedCard(emptyList, 2);

    assertNotNull(sc);
    assertNotNull(sc.getPattern());
    assertEquals(List.of(new ScoreBlock(8), new ScoreBlock(4)), sc.getScoreBlocks());
  }

  /**
   * Testing setPattern throwing exception, when trying
   * to set a new pattern on an already instantiated one.
   *
   * @throws AlreadyInitiatedPatternException
   * @throws NotValidScoreBlockValueException
   */
  @Test
  public void setPattern_should_throw_AlreadyInitiatedPatternException() throws AlreadyInitiatedPatternException, NotValidScoreBlockValueException {
    final List<SharedPattern<Predicate<Bookshelf>>> emptyList = new ArrayList<SharedPattern<Predicate<Bookshelf>>>();
    SharedCard sc = new SharedCard(emptyList, 2);
    assertThrows(AlreadyInitiatedPatternException.class,
        () -> sc.setPattern(SharedPatternFactory.getNotUsedPattern(emptyList)));
  }

  /**
   * Testing exception when setting a null scoreBlock array.
   *
   * @throws AlreadyInitiatedPatternException
   * @throws NotValidScoreBlockValueException
   */
  @Test
  public void setScoreBlocks_should_throw_NullScoreBlockListException() throws AlreadyInitiatedPatternException, NotValidScoreBlockValueException {
    final List<SharedPattern<Predicate<Bookshelf>>> emptyList = new ArrayList<SharedPattern<Predicate<Bookshelf>>>();
    SharedCard sc = new SharedCard(emptyList, 2);
    assertThrows(NullScoreBlockListException.class, () -> sc.setScoreBlocks(null));
  }

  /**
   * Testing Green Path for setScoreBlocks.
   *
   * @throws AlreadyInitiatedPatternException
   * @throws NullScoreBlockListException
   * @throws NotValidScoreBlockValueException
   */
  @Test
  public void setScoreBlocks_should_set_scoreblocks()
      throws AlreadyInitiatedPatternException, NullScoreBlockListException, NotValidScoreBlockValueException {

    final List<SharedPattern<Predicate<Bookshelf>>> emptyList = new ArrayList<SharedPattern<Predicate<Bookshelf>>>();
    SharedCard sc = new SharedCard(emptyList, 2);
    final ArrayList<ScoreBlock> arr = new ArrayList<ScoreBlock>();

    sc.setScoreBlocks(arr);
    assertEquals(arr, sc.getScoreBlocks());
  }
}
