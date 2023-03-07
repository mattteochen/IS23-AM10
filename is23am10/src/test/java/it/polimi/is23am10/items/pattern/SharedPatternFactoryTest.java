package it.polimi.is23am10.items.pattern;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import it.polimi.is23am10.items.library.Library;
import it.polimi.is23am10.items.tile.Tile;
import it.polimi.is23am10.items.tile.Tile.TileType;

public class SharedPatternFactoryTest {

  @Test
  public void FOUR_CORNERS_RULE_satisfied() {
    Library fourCornersMatching = new Library("CBBBCBBBBBBBBBBBBBBBBBBBBCBBBC");
    Library fourCornersNotMatching = new Library("CBBBBBBBBBBBBBBBBBBBBBBBBCBBBC");

    assertTrue(SharedPatternFactory.checkCornersMatch.test(fourCornersMatching));
    assertFalse(SharedPatternFactory.checkCornersMatch.test(fourCornersNotMatching));
  };

  @Test
  public void TWO_ADJACENTS_RULE_satisfied() {
    Library twoAdjacentMatching = new Library("CCBGFFTBFCFCCGFCBGFTPPCBCBCBPC");
    Library twoAdjacentAllEqualsMatching = new Library("CCCCCCCCCCCCCCCCCCCCCCCCCCCCCC");
    Library twoAdjacentNotMatching = new Library("CBGFTBGFTCGFTCBFTCBGTCBGFCBGFT");

    assertTrue(SharedPatternFactory.checkTwoAdjacents.test(twoAdjacentMatching));
    assertTrue(SharedPatternFactory.checkTwoAdjacents.test(twoAdjacentAllEqualsMatching));
    assertFalse(SharedPatternFactory.checkTwoAdjacents.test(twoAdjacentNotMatching));
  };
}
