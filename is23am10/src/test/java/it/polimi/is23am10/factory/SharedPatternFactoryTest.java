package it.polimi.is23am10.factory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import it.polimi.is23am10.factory.exceptions.DuplicatePlayerNameException;
import it.polimi.is23am10.factory.exceptions.NullPlayerNamesException;
import it.polimi.is23am10.game.Game;
import it.polimi.is23am10.game.exceptions.InvalidMaxPlayerException;
import it.polimi.is23am10.game.exceptions.NullMaxPlayerException;
import it.polimi.is23am10.items.board.exceptions.InvalidNumOfPlayersException;
import it.polimi.is23am10.items.board.exceptions.NullNumOfPlayersException;
import it.polimi.is23am10.items.bookshelf.Bookshelf;
import it.polimi.is23am10.items.bookshelf.exceptions.WrongCharBookshelfStringException;
import it.polimi.is23am10.items.bookshelf.exceptions.WrongLengthBookshelfStringException;
import it.polimi.is23am10.items.card.exceptions.AlreadyInitiatedPatternException;
import it.polimi.is23am10.pattern.SharedPattern;
import it.polimi.is23am10.player.exceptions.NullPlayerBookshelfException;
import it.polimi.is23am10.player.exceptions.NullPlayerIdException;
import it.polimi.is23am10.player.exceptions.NullPlayerNameException;
import it.polimi.is23am10.player.exceptions.NullPlayerPrivateCardException;
import it.polimi.is23am10.player.exceptions.NullPlayerScoreBlocksException;
import it.polimi.is23am10.player.exceptions.NullPlayerScoreException;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SharedPatternFactoryTest {

  @BeforeEach
  public void clear_used_pattern_list_to_avoid_using_all_patterns_in_tests() {
    GameFactory.clearUsedPatternsList();
    PlayerFactory.clearUsedPatternsList();
  }

  @Test
  public void TWO_ADJACENTS_RULE_satisfied()
      throws WrongLengthBookshelfStringException, WrongCharBookshelfStringException,
      NullPointerException {
    Bookshelf twoAdjacentMatching = new Bookshelf(
        "CCBGF" +
            "FTBFC" +
            "FCCGF" +
            "CBGFT" +
            "PPCBC" +
            "BCBPC");
    Bookshelf twoAdjacentAllEqualsMatching = new Bookshelf(
        "CCCCC" +
            "CCCCC" +
            "CCCCC" +
            "CCCCC" +
            "CCCCC" +
            "CCCCC");
    Bookshelf twoAdjacentNotMatching = new Bookshelf(
        "CBGFT" +
            "BGFTC" +
            "GFTCB" +
            "FTCBG" +
            "TCBGF" +
            "CBGFT");
    Bookshelf allNull = new Bookshelf(
        "XXXXX" +
            "XXXXX" +
            "XXXXX" +
            "XXXXX" +
            "XXXXX" +
            "XXXXX");
    Bookshelf withHorizontalOverlappingBookshelf = new Bookshelf(
        "XCCCX" +
            "XXXXX" +
            "XCCCX" +
            "XXXXX" +
            "XCCCX" +
            "XXXXX");
    Bookshelf withVerticalOverlappingBookshelf = new Bookshelf(
        "XCXCX" +
            "XCXCX" +
            "XCXCX" +
            "XXCXX" +
            "XXCXX" +
            "XXCXX");

    assertTrue(SharedPatternFactory.checkTwoAdjacents.test(twoAdjacentMatching));
    assertTrue(SharedPatternFactory.checkTwoAdjacents.test(twoAdjacentAllEqualsMatching));
    assertFalse(SharedPatternFactory.checkTwoAdjacents.test(twoAdjacentNotMatching));
    assertFalse(SharedPatternFactory.checkTwoAdjacents.test(withHorizontalOverlappingBookshelf));
    assertFalse(SharedPatternFactory.checkTwoAdjacents.test(withVerticalOverlappingBookshelf));
    assertFalse(SharedPatternFactory.checkTwoAdjacents.test(allNull));
  };

  @Test
  public void FOUR_CORNERS_RULE_satisfied() throws NullPointerException, WrongLengthBookshelfStringException,
      WrongCharBookshelfStringException {
    Bookshelf fourCornersMatching = new Bookshelf(
        "CBBBC" +
            "BBBBB" +
            "BBBBB" +
            "BBBBB" +
            "BBBBB" +
            "CBBBC");
    Bookshelf fourCornersNotMatching = new Bookshelf(
        "CBBBB" +
            "BBBBB" +
            "BBBBB" +
            "BBBBB" +
            "BBBBB" +
            "CBBBC");
    Bookshelf allNull = new Bookshelf(
        "XXXXX" +
            "XXXXX" +
            "XXXXX" +
            "XXXXX" +
            "XXXXX" +
            "XXXXX");

    assertTrue(SharedPatternFactory.checkCornersMatch.test(fourCornersMatching));
    assertFalse(SharedPatternFactory.checkCornersMatch.test(fourCornersNotMatching));
    assertFalse(SharedPatternFactory.checkCornersMatch.test(allNull));
  };

  @Test
  public void FOUR_ADJACENTS_RULE_satisfied() throws NullPointerException, WrongLengthBookshelfStringException,
      WrongCharBookshelfStringException {
    Bookshelf fourAdjacentsMatching = new Bookshelf(
        "CCCCB" +
            "FTTTT" +
            "BFGCG" +
            "FFFFG" +
            "TTBCG" +
            "GGGTG");
    Bookshelf fourAdjacentsNotMatching = new Bookshelf(
        "CBGFT" +
            "BGFTC" +
            "GFTCB" +
            "FTCBG" +
            "TCBGF" +
            "CBGFT");
    Bookshelf allNull = new Bookshelf(
        "XXXXX" +
            "XXXXX" +
            "XXXXX" +
            "XXXXX" +
            "XXXXX" +
            "XXXXX");
    Bookshelf withHorizontalOverlappingBookshelf = new Bookshelf(
        "CCCCC" +
            "XXXXX" +
            "CCCCC" +
            "XXXXX" +
            "CCCCC" +
            "XXXXX");
    Bookshelf withVerticalOverlappingBookshelf = new Bookshelf(
        "XCXCX" +
            "XCCCX" +
            "XCCCX" +
            "XCCCX" +
            "XCCCX" +
            "XXCXX");

    assertTrue(SharedPatternFactory.checkFourAdjacents.test(fourAdjacentsMatching));
    assertFalse(SharedPatternFactory.checkFourAdjacents.test(fourAdjacentsNotMatching));
    assertFalse(SharedPatternFactory.checkFourAdjacents.test(withHorizontalOverlappingBookshelf));
    assertFalse(SharedPatternFactory.checkFourAdjacents.test(withVerticalOverlappingBookshelf));
    assertFalse(SharedPatternFactory.checkFourAdjacents.test(allNull));
  };

  @Test
  public void SQUARES_RULE_satisfied() throws NullPointerException, WrongLengthBookshelfStringException,
      WrongCharBookshelfStringException {
    Bookshelf twoSquaresSameTypeMatching = new Bookshelf(
        "BCCTG" +
            "BCCGG" +
            "TTTGG" +
            "CCCCC" +
            "GGTTC" +
            "GGTBB");
    Bookshelf twoSquaresDifferentTypesMatching = new Bookshelf(
        "BCCTG" +
            "BCCGG" +
            "TTTGG" +
            "CCCCC" +
            "TTTTT" +
            "GGGGG");
    Bookshelf noSquaresMatching = new Bookshelf(
        "CBGFT" +
            "BGFTC" +
            "GFTCB" +
            "FTCBG" +
            "TCBGF" +
            "CBGFT");
    Bookshelf allNull = new Bookshelf(
        "XXXXX" +
            "XXXXX" +
            "XXXXX" +
            "XXXXX" +
            "XXXXX" +
            "XXXXX");

    assertTrue(SharedPatternFactory.checkSquares.test(twoSquaresSameTypeMatching));
    assertFalse(SharedPatternFactory.checkSquares.test(twoSquaresDifferentTypesMatching));
    assertFalse(SharedPatternFactory.checkSquares.test(noSquaresMatching));
    assertFalse(SharedPatternFactory.checkSquares.test(allNull));
  };

  @Test
  public void MAX_THREE_TYPES_COLUMN_RULE_satisfied()
      throws NullPointerException, WrongLengthBookshelfStringException,
      WrongCharBookshelfStringException {
    Bookshelf threeColumnMaxThreeTypesMatching = new Bookshelf(
        "CBTFG" +
            "CBTCB" +
            "CBTGF" +
            "CBTBC" +
            "CBTPP" +
            "PPPTT");
    Bookshelf twoColumnMaxThreeTypesMatching = new Bookshelf(
        "CBTFG" +
            "BBTCB" +
            "TBTGF" +
            "CBTBC" +
            "BTPPP" +
            "PPTTT");
    Bookshelf noColumnMaxThreeTypesMatching = new Bookshelf(
        "CBGFT" +
            "BGFTC" +
            "GFTCB" +
            "FTCBG" +
            "TCBGF" +
            "CBGFT");
    Bookshelf allNull = new Bookshelf(
        "XXXXX" +
            "XXXXX" +
            "XXXXX" +
            "XXXXX" +
            "XXXXX" +
            "XXXXX");

    assertTrue(SharedPatternFactory.checkMaxThreeTypesInColumn.test(threeColumnMaxThreeTypesMatching));
    assertFalse(SharedPatternFactory.checkMaxThreeTypesInColumn.test(twoColumnMaxThreeTypesMatching));
    assertFalse(SharedPatternFactory.checkMaxThreeTypesInColumn.test(noColumnMaxThreeTypesMatching));
    assertTrue(SharedPatternFactory.checkMaxThreeTypesInColumn.test(allNull));
  };

  @Test
  public void EIGHT_SAME_TYPE_RULE_satisfied() throws NullPointerException, WrongLengthBookshelfStringException,
      WrongCharBookshelfStringException {
    Bookshelf eightSameTypeMatching = new Bookshelf(
        "PPPPP" +
            "PPPGG" +
            "GGGGG" +
            "FFFFF" +
            "BBBBB" +
            "CCCCC");
    Bookshelf sevenSameTypeMatching = new Bookshelf(
        "PPPPP" +
            "PPCGG" +
            "GGGGG" +
            "FFFFF" +
            "BBBBB" +
            "CCCCC");
    Bookshelf noSameTypeMatching = new Bookshelf(
        "CBGFT" +
            "BGFTC" +
            "GFTCB" +
            "FTCBG" +
            "TCBGF" +
            "CBGFT");
    Bookshelf allNull = new Bookshelf(
        "XXXXX" +
            "XXXXX" +
            "XXXXX" +
            "XXXXX" +
            "XXXXX" +
            "XXXXX");

    assertTrue(SharedPatternFactory.checkEightOfSameType.test(eightSameTypeMatching));
    assertFalse(SharedPatternFactory.checkEightOfSameType.test(sevenSameTypeMatching));
    assertFalse(SharedPatternFactory.checkEightOfSameType.test(noSameTypeMatching));
    assertFalse(SharedPatternFactory.checkEightOfSameType.test(allNull));
  };

  @Test
  public void DIAGONALS_RULE_satisfied() throws NullPointerException, WrongLengthBookshelfStringException,
      WrongCharBookshelfStringException {
    Bookshelf diagonalTlbrMatching = new Bookshelf(
        "PXXXX" +
            "XPXXX" +
            "XXPXX" +
            "XXXPX" +
            "XXXXP" +
            "XXXXX");
    Bookshelf diagonalTlbrShiftedMatching = new Bookshelf(
        "XXXXX" +
            "CXXXX" +
            "XCXXX" +
            "XXCXX" +
            "XXXCX" +
            "XXXXC");
    Bookshelf diagonalTrblMatching = new Bookshelf(
        "XXXXP" +
            "XXXPX" +
            "XXPXX" +
            "XPXXX" +
            "PXXXX" +
            "XXXXX");
    Bookshelf diagonalTrblShiftedMatching = new Bookshelf(
        "XXXXX" +
            "XXXXP" +
            "XXXPX" +
            "XXPXX" +
            "XPXXX" +
            "PXXXX");
    Bookshelf noDiagonalsMatching = new Bookshelf(
        "CCBGF" +
            "FTBFC" +
            "FCCGF" +
            "CBGFT" +
            "PPCBC" +
            "BCBPC");
    Bookshelf allNull = new Bookshelf(
        "XXXXX" +
            "XXXXX" +
            "XXXXX" +
            "XXXXX" +
            "XXXXX" +
            "XXXXX");

    assertTrue(SharedPatternFactory.checkDiagonalsSameType.test(diagonalTlbrMatching));
    assertTrue(SharedPatternFactory.checkDiagonalsSameType.test(diagonalTlbrShiftedMatching));
    assertTrue(SharedPatternFactory.checkDiagonalsSameType.test(diagonalTrblMatching));
    assertTrue(SharedPatternFactory.checkDiagonalsSameType.test(diagonalTrblShiftedMatching));
    assertFalse(SharedPatternFactory.checkDiagonalsSameType.test(allNull));
    assertFalse(SharedPatternFactory.checkDiagonalsSameType.test(noDiagonalsMatching));
  };

  @Test
  public void MAX_THREE_TYPES_ROW_RULE_satisfied() throws NullPointerException, WrongLengthBookshelfStringException,
      WrongCharBookshelfStringException {
    Bookshelf fourRowsMaxThreeTypesMatching = new Bookshelf(
        "CCCBB" +
            "PBCTG" +
            "GGGGG" +
            "PTCGF" +
            "FFFFP" +
            "GGGGP");
    Bookshelf threeRowsMaxThreeTypesMatching = new Bookshelf(
        "CCCBB" +
            "PBCTG" +
            "GGGGG" +
            "PTCGF" +
            "FFFFP" +
            "GGTCP");
    Bookshelf twoRowsMaxThreeTypesMatching = new Bookshelf(
        "CCCCC" +
            "BBBBB" +
            "PTCGF" +
            "PTCGF" +
            "PTCGF" +
            "PTCGF");
    Bookshelf noRowMaxThreeTypesMatching = new Bookshelf(
        "PTCGF" +
            "PTCGF" +
            "PTCGF" +
            "PTCGF" +
            "PTCGF" +
            "PTCGF");
    Bookshelf allNull = new Bookshelf(
        "XXXXX" +
            "XXXXX" +
            "XXXXX" +
            "XXXXX" +
            "XXXXX" +
            "XXXXX");

    assertTrue(SharedPatternFactory.checkMaxThreeTypesInRow.test(allNull));
    assertTrue(SharedPatternFactory.checkMaxThreeTypesInRow.test(fourRowsMaxThreeTypesMatching));
    assertFalse(SharedPatternFactory.checkMaxThreeTypesInRow.test(threeRowsMaxThreeTypesMatching));
    assertFalse(SharedPatternFactory.checkMaxThreeTypesInRow.test(twoRowsMaxThreeTypesMatching));
    assertFalse(SharedPatternFactory.checkMaxThreeTypesInRow.test(noRowMaxThreeTypesMatching));
  };

  @Test
  public void TWO_COLUMNS_DIFF_RULE_satisfied() throws NullPointerException, WrongLengthBookshelfStringException,
      WrongCharBookshelfStringException {
    Bookshelf twoColumnAllDiffMatching = new Bookshelf(
        "PXPXT" +
            "CXCXT" +
            "FXFXF" +
            "BXBXB" +
            "GXGXG" +
            "TXTXX");
    Bookshelf oneColumnAllDiffMatching = new Bookshelf(
        "PXXXT" +
            "CXXXT" +
            "FXXXF" +
            "BXXXB" +
            "GXXXG" +
            "TXXXX");
    Bookshelf noColumnAllDiffMatching = new Bookshelf(
        "CCCCC" +
            "CCCCC" +
            "TTTTT" +
            "PPPPP" +
            "GGGGG" +
            "BBBBB");
    Bookshelf allNull = new Bookshelf(
        "XXXXX" +
            "XXXXX" +
            "XXXXX" +
            "XXXXX" +
            "XXXXX" +
            "XXXXX");

    assertTrue(SharedPatternFactory.checkTwoColumnAllDiff.test(twoColumnAllDiffMatching));
    assertFalse(SharedPatternFactory.checkTwoColumnAllDiff.test(oneColumnAllDiffMatching));
    assertFalse(SharedPatternFactory.checkTwoColumnAllDiff.test(noColumnAllDiffMatching));
    assertFalse(SharedPatternFactory.checkTwoColumnAllDiff.test(allNull));
  };

  @Test
  public void TWO_ROWS_DIFF_RULE_satisfied() throws NullPointerException, WrongLengthBookshelfStringException,
      WrongCharBookshelfStringException {
    Bookshelf twoRowsAllDiffMatching = new Bookshelf(
        "PCTFG" +
            "XXXXX" +
            "GPTFC" +
            "CCCCC" +
            "PTCGT" +
            "PTCGT");
    Bookshelf oneRowAllDiffMatching = new Bookshelf(
        "PCTFG" +
            "XXXXX" +
            "GTTFC" +
            "CCCCC" +
            "PTCGT" +
            "PTCGT");
    Bookshelf noRowAllDiffMatching = new Bookshelf(
        "CCCCC" +
            "CCCCC" +
            "TTTTT" +
            "PPPPP" +
            "GGGGG" +
            "BBBBB");
    Bookshelf allNull = new Bookshelf(
        "XXXXX" +
            "XXXXX" +
            "XXXXX" +
            "XXXXX" +
            "XXXXX" +
            "XXXXX");

    assertTrue(SharedPatternFactory.checkTwoRowsAllDiff.test(twoRowsAllDiffMatching));
    assertFalse(SharedPatternFactory.checkTwoRowsAllDiff.test(oneRowAllDiffMatching));
    assertFalse(SharedPatternFactory.checkTwoRowsAllDiff.test(noRowAllDiffMatching));
    assertFalse(SharedPatternFactory.checkTwoRowsAllDiff.test(allNull));
  };

  @Test
  public void X_SHAPE_RULE_satisfied() throws NullPointerException, WrongLengthBookshelfStringException,
      WrongCharBookshelfStringException {
    Bookshelf oneXShapeMatching = new Bookshelf(
        "XXXXX" +
            "XPXPX" +
            "XXPXX" +
            "XPXPX" +
            "XXXXX" +
            "XXXXX");
    Bookshelf noXShapeMatching = new Bookshelf(
        "PPPPP" +
            "CCCCC" +
            "GGGGG" +
            "FFFFF" +
            "TTTTT" +
            "CCCCC");
    Bookshelf nullXShapeMatching = new Bookshelf(
        "PPPPP" +
            "CXCXC" +
            "GGXGG" +
            "TXTXT" +
            "FFFFF" +
            "BBBBB");
    Bookshelf allNull = new Bookshelf(
        "XXXXX" +
            "XXXXX" +
            "XXXXX" +
            "XXXXX" +
            "XXXXX" +
            "XXXXX");

    assertTrue(SharedPatternFactory.checkTilesXShape.test(oneXShapeMatching));
    assertFalse(SharedPatternFactory.checkTilesXShape.test(noXShapeMatching));
    assertFalse(SharedPatternFactory.checkTilesXShape.test(nullXShapeMatching));
    assertFalse(SharedPatternFactory.checkTilesXShape.test(allNull));
  };

  @Test
  public void ORDERED_BOOKSHELF_RULE_satisfied() throws NullPointerException, WrongLengthBookshelfStringException,
      WrongCharBookshelfStringException {
    Bookshelf ascOrderedBookshelfMatching = new Bookshelf(
        "XXXXX" +
            "XXXXG" +
            "XXXBT" +
            "XXCGC" +
            "XTBCG" +
            "BBBBB");
    Bookshelf ascOrderedBookshelfShiftedMatching = new Bookshelf(
        "XXXXG" +
            "XXXBT" +
            "XXCGC" +
            "XTBCG" +
            "BBBBB" +
            "PPPPP");
    Bookshelf descOrderedBookshelfMatching = new Bookshelf(
        "XXXXX" +
            "PXXXX" +
            "CGXXX" +
            "BGCXX" +
            "BGTTX" +
            "PPPPP");
    Bookshelf descOrderedBookshelfShiftedMatching = new Bookshelf(
        "PXXXX" +
            "CGXXX" +
            "CBGXX" +
            "BGTTX" +
            "PPPPP" +
            "BBBBB");
    Bookshelf noOrderedBookshelfMatching = new Bookshelf(
        "CCCCC" +
            "BBBBB" +
            "PTCGF" +
            "PTCGF" +
            "PTCGF" +
            "PTCGF");
    Bookshelf allNull = new Bookshelf(
        "XXXXX" +
            "XXXXX" +
            "XXXXX" +
            "XXXXX" +
            "XXXXX" +
            "XXXXX");

    assertTrue(SharedPatternFactory.checkOrderedBookshelfColumns.test(ascOrderedBookshelfMatching));
    assertTrue(SharedPatternFactory.checkOrderedBookshelfColumns.test(ascOrderedBookshelfShiftedMatching));
    assertTrue(SharedPatternFactory.checkOrderedBookshelfColumns.test(descOrderedBookshelfMatching));
    assertTrue(SharedPatternFactory.checkOrderedBookshelfColumns.test(descOrderedBookshelfShiftedMatching));
    assertFalse(SharedPatternFactory.checkOrderedBookshelfColumns.test(noOrderedBookshelfMatching));
    assertFalse(SharedPatternFactory.checkOrderedBookshelfColumns.test(allNull));
  };

  @Test
  public void TEST_GET_RANDOM_SHAREDCARDS_no_duplicates()
      throws AlreadyInitiatedPatternException, NullMaxPlayerException, InvalidMaxPlayerException,
      NullPlayerNameException, NullPlayerIdException, NullPlayerBookshelfException, NullPlayerScoreException,
      NullPlayerPrivateCardException, NullPlayerScoreBlocksException, DuplicatePlayerNameException,
      NullPlayerNamesException, InvalidNumOfPlayersException, NullNumOfPlayersException {

    Game game = GameFactory.getNewGame("firstPlayer", 4);

    List<SharedPattern<Predicate<Bookshelf>>> allUsedPatterns = game.getSharedCard().stream()
        .map(card -> card.getPattern()).distinct().collect(Collectors.toList());

    assertEquals(2, allUsedPatterns.size());
  }
}
