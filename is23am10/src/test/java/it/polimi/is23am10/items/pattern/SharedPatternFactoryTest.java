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
    Library fourCornersMatching = new Library();
    fourCornersMatching.setTile(0, 0, new Tile(TileType.CAT));
    fourCornersMatching.setTile(5, 0, new Tile(TileType.CAT));
    fourCornersMatching.setTile(0, 4, new Tile(TileType.CAT));
    fourCornersMatching.setTile(5, 4, new Tile(TileType.CAT));

    assertTrue(SharedPatternFactory.checkCornersMatch.test(fourCornersMatching));
  };
}
