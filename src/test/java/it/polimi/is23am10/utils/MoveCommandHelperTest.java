package it.polimi.is23am10.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import it.polimi.is23am10.server.model.items.bookshelf.Bookshelf;
import it.polimi.is23am10.server.model.items.bookshelf.exceptions.BookshelfGridColIndexOutOfBoundsException;
import it.polimi.is23am10.server.model.items.bookshelf.exceptions.BookshelfGridRowIndexOutOfBoundsException;
import it.polimi.is23am10.server.model.items.bookshelf.exceptions.NullTileException;
import it.polimi.is23am10.server.model.items.tile.Tile;
import it.polimi.is23am10.server.model.items.tile.Tile.TileType;
import it.polimi.is23am10.utils.exceptions.NullIndexValueException;
import it.polimi.is23am10.utils.exceptions.WrongBookShelfPicksException;
import java.util.List;
import org.junit.Test;

/** Test class for move command helper. */
public class MoveCommandHelperTest {

  @Test
  public void moveCommandConverter_should_convert_correctly()
      throws BookshelfGridColIndexOutOfBoundsException,
          BookshelfGridRowIndexOutOfBoundsException,
          NullIndexValueException,
          WrongBookShelfPicksException,
          NullTileException {
    Bookshelf emptyBs = new Bookshelf();
    Bookshelf bs = new Bookshelf();
    bs.setBookshelfGridIndex(5, 1, new Tile(TileType.CAT));
    Integer nMovesEmptyBs = 1;
    Integer nMoves = 2;
    String columnIdxEmptyBs = "A";
    String columnIdx = "B";

    List<Coordinates> coordsEmptyBs =
        List.of(new Coordinates(emptyBs.getBookshelfGrid()[0].length, 0));
    List<Coordinates> coords =
        List.of(
            new Coordinates(emptyBs.getBookshelfGrid()[0].length - 1, 1),
            new Coordinates(emptyBs.getBookshelfGrid()[0].length - 2, 1));

    assertEquals(
        coordsEmptyBs,
        MoveCommandHelper.fromColIdxToCoord(columnIdxEmptyBs, emptyBs, nMovesEmptyBs));
    assertEquals(coords, MoveCommandHelper.fromColIdxToCoord(columnIdx, bs, nMoves));
  }
}
