package it.polimi.is23am10.items.tile;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import it.polimi.is23am10.items.tile.Tile.TileType;

public class TileTest {
  /**
   * Green path for constructor.
   */
  @Test
  public void constructor_should_create_Tile(){
    final Tile emptyTile = new Tile(null);
    final Tile notEmptyTile = new Tile(TileType.BOOK);

    assertNotNull(emptyTile);
    assertNotNull(notEmptyTile);

    assertEquals(TileType.EMPTY, emptyTile.getType());
    assertEquals(TileType.BOOK, notEmptyTile.getType());
  }

  /**
   * Testing NPE on setTile
   */
  @Test
  public void setTile_should_throw_NullPointerException() {
    final Tile dummyTile = new Tile(TileType.BOOK);
    assertThrows(NullPointerException.class, () -> dummyTile.setTile(null));
  }

  /**
   * Testing setTile green path
   */
  @Test
  public void setTile_should_set_tile() {
    final Tile dummyTile = new Tile(TileType.BOOK);
    final TileType newType = TileType.CAT;

    dummyTile.setTile(newType);

    assertEquals(newType, dummyTile.getType());
  }
}
