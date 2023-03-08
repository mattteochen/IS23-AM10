package it.polimi.is23am10.items.pattern;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import it.polimi.is23am10.items.library.Library;

public class SharedPatternFactoryTest {

  @Test
  public void TWO_ADJACENTS_RULE_satisfied() {
    Library twoAdjacentMatching = new Library(
        "CCBGF" +
            "FTBFC" +
            "FCCGF" +
            "CBGFT" +
            "PPCBC" +
            "BCBPC");
    Library twoAdjacentAllEqualsMatching = new Library(
        "CCCCC" +
            "CCCCC" +
            "CCCCC" +
            "CCCCC" +
            "CCCCC" +
            "CCCCC");
    Library twoAdjacentNotMatching = new Library(
        "CBGFT" +
            "BGFTC" +
            "GFTCB" +
            "FTCBG" +
            "TCBGF" +
            "CBGFT");
    Library allNull = new Library(
        "XXXXX" +
            "XXXXX" +
            "XXXXX" +
            "XXXXX" +
            "XXXXX" +
            "XXXXX");

    assertTrue(SharedPatternFactory.checkTwoAdjacents.test(twoAdjacentMatching));
    assertTrue(SharedPatternFactory.checkTwoAdjacents.test(twoAdjacentAllEqualsMatching));
    assertFalse(SharedPatternFactory.checkTwoAdjacents.test(twoAdjacentNotMatching));
    assertFalse(SharedPatternFactory.checkTwoAdjacents.test(allNull));
  };

  @Test
  public void FOUR_CORNERS_RULE_satisfied() {
    Library fourCornersMatching = new Library(
        "CBBBC" +
            "BBBBB" +
            "BBBBB" +
            "BBBBB" +
            "BBBBB" +
            "CBBBC");
    Library fourCornersNotMatching = new Library(
        "CBBBB" +
            "BBBBB" +
            "BBBBB" +
            "BBBBB" +
            "BBBBB" +
            "CBBBC");
    Library allNull = new Library(
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
  public void FOUR_ADJACENTS_RULE_satisfied() {
    Library fourAdjacentsMatching = new Library(
        "CCCCB" +
            "FTTTT" +
            "BFGCG" +
            "FFFFG" +
            "TTBCG" +
            "GGGTG");
    Library fourAdjacentsNotMatching = new Library(
        "CBGFT" +
            "BGFTC" +
            "GFTCB" +
            "FTCBG" +
            "TCBGF" +
            "CBGFT");
    Library allNull = new Library(
        "XXXXX" +
            "XXXXX" +
            "XXXXX" +
            "XXXXX" +
            "XXXXX" +
            "XXXXX");

    assertTrue(SharedPatternFactory.checkFourAdjacents.test(fourAdjacentsMatching));
    assertFalse(SharedPatternFactory.checkFourAdjacents.test(fourAdjacentsNotMatching));
    assertFalse(SharedPatternFactory.checkFourAdjacents.test(allNull));
  };

  @Test
  public void SQUARES_RULE_satisfied() {
    Library twoSquaresSameTypeMatching = new Library(
        "BCCTG" +
            "BCCGG" +
            "TTTGG" +
            "CCCCC" +
            "GGTTC" +
            "GGTBB");
    Library twoSquaresDifferentTypesMatching = new Library(
        "BCCTG" +
            "BCCGG" +
            "TTTGG" +
            "CCCCC" +
            "TTTTT" +
            "GGGGG");
    Library noSquaresMatching = new Library(
        "CBGFT" +
            "BGFTC" +
            "GFTCB" +
            "FTCBG" +
            "TCBGF" +
            "CBGFT");
    Library allNull = new Library(
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
  public void MAX_THREE_TYPES_COLUMN_RULE_satisfied() {
    Library threeColumnMaxThreeTypesMatching = new Library(
        "CBTFG" +
            "CBTCB" +
            "CBTGF" +
            "CBTBC" +
            "CBTPP" +
            "PPPTT");
    Library twoColumnMaxThreeTypesMatching = new Library(
        "CBTFG" +
            "BBTCB" +
            "TBTGF" +
            "CBTBC" +
            "BTPPP" +
            "PPTTT");
    Library noColumnMaxThreeTypesMatching = new Library(
        "CBGFT" +
            "BGFTC" +
            "GFTCB" +
            "FTCBG" +
            "TCBGF" +
            "CBGFT");
    Library allNull = new Library(
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
  public void EIGHT_SAME_TYPE_RULE_satisfied() {
    Library eightSameTypeMatching = new Library(
        "PPPPP" +
            "PPPGG" +
            "GGGGG" +
            "FFFFF" +
            "BBBBB" +
            "CCCCC");
    Library sevenSameTypeMatching = new Library(
        "PPPPP" +
            "PPCGG" +
            "GGGGG" +
            "FFFFF" +
            "BBBBB" +
            "CCCCC");
    Library noSameTypeMatching = new Library(
        "CBGFT" +
            "BGFTC" +
            "GFTCB" +
            "FTCBG" +
            "TCBGF" +
            "CBGFT");
    Library allNull = new Library(
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
  public void DIAGONALS_RULE_satisfied() {
    Library diagonalTlbrMatching = new Library(
        "PXXXX" +
            "XPXXX" +
            "XXPXX" +
            "XXXPX" +
            "XXXXP" +
            "XXXXX");
    Library diagonalTlbrShiftedMatching = new Library(
        "XXXXX" +
            "CXXXX" +
            "XCXXX" +
            "XXCXX" +
            "XXXCX" +
            "XXXXC");
    Library diagonalTrblMatching = new Library(
        "XXXXP" +
            "XXXPX" +
            "XXPXX" +
            "XPXXX" +
            "PXXXX" +
            "XXXXX");
    Library diagonalTrblShiftedMatching = new Library(
        "XXXXX" +
            "XXXXP" +
            "XXXPX" +
            "XXPXX" +
            "XPXXX" +
            "PXXXX");
    Library noDiagonalsMatching = new Library(
        "CCBGF" +
            "FTBFC" +
            "FCCGF" +
            "CBGFT" +
            "PPCBC" +
            "BCBPC");
    Library allNull = new Library(
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
  public void MAX_THREE_TYPES_ROW_RULE_satisfied() {
    Library fourRowsMaxThreeTypesMatching = new Library(
        "CCCBB" +
            "PBCTG" +
            "GGGGG" +
            "PTCGF" +
            "FFFFP" +
            "GGGGP");
    Library threeRowsMaxThreeTypesMatching = new Library(
        "CCCBB" +
            "PBCTG" +
            "GGGGG" +
            "PTCGF" +
            "FFFFP" +
            "GGTCP");
    Library twoRowsMaxThreeTypesMatching = new Library(
        "CCCCC" +
            "BBBBB" +
            "PTCGF" +
            "PTCGF" +
            "PTCGF" +
            "PTCGF");
    Library noRowMaxThreeTypesMatching = new Library(
        "PTCGF" +
            "PTCGF" +
            "PTCGF" +
            "PTCGF" +
            "PTCGF" +
            "PTCGF");
    Library allNull = new Library(
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
  public void TWO_COLUMNS_DIFF_RULE_satisfied() {
    Library twoColumnAllDiffMatching = new Library(
        "PXPXT" +
            "CXCXT" +
            "FXFXF" +
            "BXBXB" +
            "GXGXG" +
            "TXTXX");
    Library oneColumnAllDiffMatching = new Library(
        "PXXXT" +
            "CXXXT" +
            "FXXXF" +
            "BXXXB" +
            "GXXXG" +
            "TXXXX");
    Library noColumnAllDiffMatching = new Library(
        "CCCCC" +
            "CCCCC" +
            "TTTTT" +
            "PPPPP" +
            "GGGGG" +
            "BBBBB");
    Library allNull = new Library(
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
  public void X_SHAPE_RULE_satisfied() {
    Library oneXShapeMatching = new Library(
        "XXXXX" +
            "XPXPX" +
            "XXPXX" +
            "XPXPX" +
            "XXXXX" +
            "XXXXX");
    Library noXShapeMatching = new Library(
        "PPPPP" +
            "CCCCC" +
            "GGGGG" +
            "FFFFF" +
            "TTTTT" +
            "CCCCC");
    Library nullXShapeMatching = new Library(
        "PPPPP" +
            "CXCXC" +
            "GGXGG" +
            "TXTXT" +
            "FFFFF" +
            "BBBBB");
    Library allNull = new Library(
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
  public void ORDERED_LIBRARY_RULE_satisfied() {
    Library ascOrderedLibraryMatching = new Library(
        "XXXXX" +
            "XXXXG" +
            "XXXBT" +
            "XXCGC" +
            "XTBCG" +
            "BBBBB");
    Library ascOrderedLibraryShiftedMatching = new Library(
        "XXXXG" +
            "XXXBT" +
            "XXCGC" +
            "XTBCG" +
            "BBBBB" +
            "PPPPP");
    Library descOrderedLibraryMatching = new Library(
        "XXXXX" +
            "PXXXX" +
            "CGXXX" +
            "BGCXX" +
            "BGTTX" +
            "PPPPP");
    Library descOrderedLibraryShiftedMatching = new Library(
        "PXXXX" +
            "CGXXX" +
            "CBGXX" +
            "BGTTX" +
            "PPPPP" +
            "BBBBB");
    Library noOrderedLibraryMatching = new Library(
        "CCCCC" +
            "BBBBB" +
            "PTCGF" +
            "PTCGF" +
            "PTCGF" +
            "PTCGF");
    Library allNull = new Library(
        "XXXXX" +
            "XXXXX" +
            "XXXXX" +
            "XXXXX" +
            "XXXXX" +
            "XXXXX");

    assertTrue(SharedPatternFactory.checkOrderedLibraryColumns.test(ascOrderedLibraryMatching));
    assertTrue(SharedPatternFactory.checkOrderedLibraryColumns.test(ascOrderedLibraryShiftedMatching));
    assertTrue(SharedPatternFactory.checkOrderedLibraryColumns.test(descOrderedLibraryMatching));
    assertTrue(SharedPatternFactory.checkOrderedLibraryColumns.test(descOrderedLibraryShiftedMatching));
    assertFalse(SharedPatternFactory.checkOrderedLibraryColumns.test(noOrderedLibraryMatching));
    assertFalse(SharedPatternFactory.checkOrderedLibraryColumns.test(allNull));
  };

}
