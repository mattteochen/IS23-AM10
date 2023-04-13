package it.polimi.is23am10.server.model.score;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import it.polimi.is23am10.server.model.items.bookshelf.Bookshelf;
import it.polimi.is23am10.server.model.items.bookshelf.exceptions.BookshelfGridColIndexOutOfBoundsException;
import it.polimi.is23am10.server.model.items.bookshelf.exceptions.BookshelfGridRowIndexOutOfBoundsException;
import it.polimi.is23am10.server.model.items.bookshelf.exceptions.WrongCharBookshelfStringException;
import it.polimi.is23am10.server.model.items.bookshelf.exceptions.WrongLengthBookshelfStringException;
import it.polimi.is23am10.server.model.items.card.PrivateCard;
import it.polimi.is23am10.server.model.items.card.exceptions.AlreadyInitiatedPatternException;
import it.polimi.is23am10.server.model.items.card.exceptions.NegativeMatchedBlockCountException;
import it.polimi.is23am10.server.model.items.card.exceptions.NullMatchedBlockCountException;
import it.polimi.is23am10.server.model.items.card.exceptions.NullScoreBlockListException;
import it.polimi.is23am10.server.model.items.scoreblock.ScoreBlock;
import it.polimi.is23am10.server.model.items.scoreblock.exceptions.NotValidScoreBlockValueException;
import it.polimi.is23am10.server.model.pattern.PrivatePattern;
import it.polimi.is23am10.server.model.player.exceptions.NullPlayerBookshelfException;
import it.polimi.is23am10.server.model.score.Score;
import it.polimi.is23am10.utils.exceptions.NullIndexValueException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@SuppressWarnings({ "checkstyle:methodname", "checkstyle:abbreviationaswordinnamecheck", "checkstyle:linelengthcheck",
    "checkstyle:onetoplevelclasscheck", "checkstyle:variabledeclarationusagedistancecheck",
    "checkstyle:operatorwrapcheck", "checkstyle:multiplevariabledeclarationscheck", "checkstyle:membernamecheck",
    "checkstyle:nonemptyatclausedescriptioncheck", "checkstyle:typenamecheck" })
class ScoreTest {
  @Test
  public void constructor_should_set_zeros() {
    Score s = new Score();
    Integer zero = 0;
    assertEquals(zero, s.getExtraPoint());
    assertEquals(zero, s.getBookshelfPoints());
    assertEquals(zero, s.getScoreBlockPoints());
    assertEquals(zero, s.getPrivatePoints());
  }

  @Test
  public void setExtraPoint_should_set_extraPoint() {
    Score s = new Score();
    Integer one = 1;
    s.setExtraPoint();
    assertEquals(one, s.getExtraPoint());
  }

  @Nested
  class setBookshelfPoints_tests {
    /**
     * This test comes directly from the rulebook.
     * Check Rulebook - page 2 - Final count example.
     *
     * @throws WrongCharBookshelfStringException
     * @throws WrongLengthBookshelfStringException
     * @throws NullPointerException
     * @throws NullIndexValueException
     * @throws BookshelfGridRowIndexOutOfBoundsException
     * @throws BookshelfGridColIndexOutOfBoundsException
     * @throws NullPlayerBookshelfException
     */
    @Test
    public void setBookshelfPoints_should_set_exampleBS()
        throws NullPointerException, WrongLengthBookshelfStringException, WrongCharBookshelfStringException,
        BookshelfGridColIndexOutOfBoundsException, BookshelfGridRowIndexOutOfBoundsException, NullIndexValueException,
        NullPlayerBookshelfException {
      Score s = new Score();
      Bookshelf bs = new Bookshelf(
          "PPPXX" +
              "BPPCX" +
              "FBFBX" +
              "TGTGX" +
              "TTCCC" +
              "TTTCC");

      s.setBookshelfPoints(bs);
      final Integer EIGHTEEN = 18;

      assertEquals(EIGHTEEN, s.getBookshelfPoints());
    }

    /**
     * This test checks that an empty bookshelf
     * returns a zero score.
     *
     * @throws NullPointerException
     * @throws WrongLengthBookshelfStringException
     * @throws WrongCharBookshelfStringException
     * 
     * @throws BookshelfGridColIndexOutOfBoundsException
     * @throws BookshelfGridRowIndexOutOfBoundsException
     * @throws NullIndexValueException
     * @throws NullPlayerBookshelfException
     */
    @Test
    public void setBookshelfPoints_should_set_emptyBS()
        throws NullPointerException, WrongLengthBookshelfStringException, WrongCharBookshelfStringException,
        BookshelfGridColIndexOutOfBoundsException, BookshelfGridRowIndexOutOfBoundsException, NullIndexValueException,
        NullPlayerBookshelfException {
      Score s = new Score();
      Bookshelf bs = new Bookshelf();

      s.setBookshelfPoints(bs);
      final Integer ZERO = 0;

      assertEquals(ZERO, s.getBookshelfPoints());
    }

    /**
     * This tests checks that groups bigger than {@link Score#MAX_GROUP_SIZE}
     * only count as {@link Score#MAX_GROUP_SIZE}-big groups.
     *
     * @throws NullPointerException
     * @throws WrongLengthBookshelfStringException
     * @throws WrongCharBookshelfStringException
     * 
     * @throws BookshelfGridColIndexOutOfBoundsException
     * @throws BookshelfGridRowIndexOutOfBoundsException
     * @throws NullIndexValueException
     * @throws NullPlayerBookshelfException
     */
    @Test
    public void setBookshelfPoints_should_set_bigGroupsBS()
        throws NullPointerException, WrongLengthBookshelfStringException, WrongCharBookshelfStringException,
        BookshelfGridColIndexOutOfBoundsException, BookshelfGridRowIndexOutOfBoundsException, NullIndexValueException,
        NullPlayerBookshelfException {
      Score s = new Score();
      Bookshelf bs = new Bookshelf(
          "PPPPP" +
              "PPPPP" +
              "PPPPP" +
              "CCCCC" +
              "CCCCC" +
              "CCCCC");

      s.setBookshelfPoints(bs);
      // Two groups of 6+ should be counted as two groups of 6
      // returning 2x8 = 16 score
      final Integer SIXTEEN = 16;

      assertEquals(SIXTEEN, s.getBookshelfPoints());
    }

    /**
     * This tests checks that groups smaller than {@link Score#MIN_GROUP_SIZE}
     * get ignored.
     *
     * @throws NullPointerException
     * @throws WrongLengthBookshelfStringException
     * @throws WrongCharBookshelfStringException
     * 
     * @throws BookshelfGridColIndexOutOfBoundsException
     * @throws BookshelfGridRowIndexOutOfBoundsException
     * @throws NullIndexValueException
     * @throws NullPlayerBookshelfException
     */
    @Test
    public void setBookshelfPoints_should_set_smallGroupsBS()
        throws NullPointerException, WrongLengthBookshelfStringException, WrongCharBookshelfStringException,
        BookshelfGridColIndexOutOfBoundsException, BookshelfGridRowIndexOutOfBoundsException, NullIndexValueException,
        NullPlayerBookshelfException {
      Score s = new Score();
      Bookshelf bs = new Bookshelf(
          "PCPCP" +
              "CPCPC" +
              "PCPCP" +
              "CPCPC" +
              "PCPCP" +
              "CPCPC");

      s.setBookshelfPoints(bs);
      final Integer ZERO = 0;

      assertEquals(ZERO, s.getBookshelfPoints());
    }
  }

  @Nested
  class setScoreBlockPoints_tests {

    /**
     * Test to check exception throws.
     */
    @Test
    public void setScoreBlockPoints_should_throw_NullScoreBlockListException() {
      Score s = new Score();
      assertThrows(NullScoreBlockListException.class, () -> s.setScoreBlockPoints(null));
    }

    /**
     * Test to check green path. Scoreblocks are present and valid
     *
     * @throws NotValidScoreBlockValueException
     * @throws NullScoreBlockListException
     */
    @Test
    public void setScoreBlockPoints_should_set() throws NotValidScoreBlockValueException, NullScoreBlockListException {
      Score s = new Score();
      List<ScoreBlock> list = List.of(
          new ScoreBlock(2),
          new ScoreBlock(4));

      s.setScoreBlockPoints(list);

      // Sum of the two available scoreblocks 2+4=6
      final Integer SIX = 6;
      assertEquals(SIX, s.getScoreBlockPoints());

    }

    /**
     * Test to check that an empty list of scoreblocks
     * results in a zero-score.
     *
     * @throws NotValidScoreBlockValueException
     * @throws NullScoreBlockListException
     */
    @Test
    public void setScoreBlockPoints_should_set_empty()
        throws NotValidScoreBlockValueException, NullScoreBlockListException {
      Score s = new Score();
      List<ScoreBlock> list = List.of();

      s.setScoreBlockPoints(list);

      final Integer ZERO = 0;
      assertEquals(ZERO, s.getScoreBlockPoints());
    }
  }

  @Nested
  class setPrivatePoints_tests {

    private List<PrivatePattern<Function<Bookshelf, Integer>>> usedPatterns = new ArrayList<>();

    /**
     * Test to check exception throws.
     */
    @Test
    public void setPrivatePoints_should_throw_NullPointerException() {
      Score s = new Score();
      assertThrows(NullPointerException.class, () -> s.setPrivatePoints(null, null));
    }

    /**
     * Test to check green path. Private
     *
     * @throws NotValidScoreBlockValueException
     * @throws NullScoreBlockListException
     * @throws AlreadyInitiatedPatternException
     * @throws NegativeMatchedBlockCountException
     * @throws NullMatchedBlockCountException
     */
    @ParameterizedTest
    @ValueSource(ints = { 0, 1, 2, 3, 4, 5, 6 })
    public void setPrivatePoints_should_set(Integer value)
        throws NotValidScoreBlockValueException, NullScoreBlockListException,
        AlreadyInitiatedPatternException, NullMatchedBlockCountException, NegativeMatchedBlockCountException {

      Score s = new Score();
      PrivateCard pc = new PrivateCard(usedPatterns);
      pc.setMatchedBlocksCount(value);

      assertEquals(Score.privateCardPointsMap.get(value), Score.privateCardPointsMap.get(pc.getMatchedBlocksCount()));

    }
  }
}
