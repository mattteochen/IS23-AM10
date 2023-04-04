package it.polimi.is23am10.items.bookshelf;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import it.polimi.is23am10.items.bookshelf.exceptions.BookshelfGridColIndexOutOfBoundsException;
import it.polimi.is23am10.items.bookshelf.exceptions.BookshelfGridRowIndexOutOfBoundsException;
import it.polimi.is23am10.items.bookshelf.exceptions.NullTileException;
import it.polimi.is23am10.items.bookshelf.exceptions.WrongCharBookshelfStringException;
import it.polimi.is23am10.items.bookshelf.exceptions.WrongLengthBookshelfStringException;
import it.polimi.is23am10.items.tile.Tile;
import it.polimi.is23am10.items.tile.Tile.TileType;
import it.polimi.is23am10.utils.exceptions.NullIndexValueException;
import org.junit.jupiter.api.Test;

/**
 * Tests for Bookshelf class.
 */
@SuppressWarnings({ "checkstyle:methodname", "checkstyle:abbreviationaswordinnamecheck", "checkstyle:linelengthcheck",
    "checkstyle:onetoplevelclasscheck", "checkstyle:variabledeclarationusagedistancecheck",
    "checkstyle:operatorwrapcheck", "checkstyle:multiplevariabledeclarationscheck", "checkstyle:membernamecheck",
    "checkstyle:nonemptyatclausedescriptioncheck" })
public class BookshelfTest {

  /**
   * Testing empty constructor. It should set all tiles to empty.
   *
   * @throws BookshelfGridColIndexOutOfBoundsException
   * @throws BookshelfGridRowIndexOutOfBoundsException
   * @throws NullIndexValueException
   */
  @Test
  public void constructor_should_create_Bookshelf()
      throws BookshelfGridColIndexOutOfBoundsException, BookshelfGridRowIndexOutOfBoundsException,
      NullIndexValueException {

    Bookshelf bs = new Bookshelf();
    assertNotNull(bs);
    for (int i = 0; i < Bookshelf.BOOKSHELF_ROWS; i++) {
      for (int j = 0; j < Bookshelf.BOOKSHELF_COLS; j++) {
        Tile t = bs.getBookshelfGridAt(i, j);
        assertNotNull(t);
        assertTrue(t.isEmpty());
      }
    }
  }

  /**
   * Testing copy constructor.
   *
   * @throws BookshelfGridColIndexOutOfBoundsException
   * @throws BookshelfGridRowIndexOutOfBoundsException
   * @throws NullIndexValueException
   * @throws NullTileException
   */
  @Test
  public void copyConstructor_should_copy_Bookshelf()
      throws BookshelfGridColIndexOutOfBoundsException, BookshelfGridRowIndexOutOfBoundsException,
      NullIndexValueException, NullTileException {

    Bookshelf original = new Bookshelf();
    Bookshelf copied = new Bookshelf(original);
    //assert array on different memory
    assertNotEquals(original.getBookshelfGrid(), copied.getBookshelfGrid());
    for (int i = 0; i < Bookshelf.BOOKSHELF_ROWS; i++) {
      //assert internal arrays on different memory
      assertNotEquals(original.getBookshelfGrid()[i], copied.getBookshelfGrid()[i]);
    }
    copied.setBookshelfGridIndex(0, 0, new Tile(TileType.PLANT));
    //assert no changes reflected on original array
    assertNotEquals(original.getBookshelfGridAt(0, 0), copied.getBookshelfGridAt(0, 0));
  }

  /**
   * Testing the string-based constructor. Providing a string of a different
   * length should throw an error.
   */
  @Test
  public void string_constructor_should_throw_WrongLengthBookshelfStringException() {

    // String-based constructor takes strings of exactly 30 chars.
    final String WRONG_LENGTH_STRING = "CCCC";

    assertThrows(WrongLengthBookshelfStringException.class, () -> new Bookshelf(WRONG_LENGTH_STRING));
  }

  /**
   * Testing the string-based constructor. Providing a string 
   * with an unexpected char should throw an error.
   * Valid chars are the ones defined in {@link Bookshelf#tileMap}
   */
  @Test
  public void string_constructor_should_throw_WrongCharBookshelfStringException() {

    // String should pass length check, being 30 char long, but contains invalid
    // chars.
    final String WRONG_CHAR_STRING = "ABCDEFABCDEFABCDEFABCDEFABCDEF";

    assertThrows(WrongCharBookshelfStringException.class, () -> new Bookshelf(WRONG_CHAR_STRING));
  }

  /**
   * Testing the string-based constructor. Green path.
   *
   * @throws NullPointerException
   * @throws WrongLengthBookshelfStringException
   * @throws WrongCharBookshelfStringException
   * @throws BookshelfGridColIndexOutOfBoundsException
   * @throws BookshelfGridRowIndexOutOfBoundsException
   * @throws NullIndexValueException
   */
  @Test
  public void string_constructor_should_create_Bookshelf()
      throws NullPointerException, WrongLengthBookshelfStringException,
      WrongCharBookshelfStringException,
      BookshelfGridColIndexOutOfBoundsException, BookshelfGridRowIndexOutOfBoundsException,
      NullIndexValueException {

    // String is 30 char long and contains only allowed chars.
    final String CORRECT_STRING = "CCCCCCCCCCCCCCCCCCCCCCCCCCCCCC";

    Bookshelf bs = new Bookshelf(CORRECT_STRING);
    assertNotNull(bs);

    for (int i = 0; i < Bookshelf.BOOKSHELF_ROWS; i++) {
      for (int j = 0; j < Bookshelf.BOOKSHELF_COLS; j++) {
        Tile t = bs.getBookshelfGridAt(i, j);
        assertNotNull(t);
        assertSame(TileType.CAT, t.getType());
      }
    }
  }

  /**
   * Testing row boundaries in setBookshelfGridIndex.
   */
  @Test
  public void setBookshelfGridIndex_should_throw_BookshelfGridRowIndexOutOfBoundsException() {

    Bookshelf bs = new Bookshelf();
    final Integer INVALID_ROW = 99;
    final Integer VALID_COL = 1;
    final Tile validTile = new Tile(TileType.BOOK);
    assertThrows(BookshelfGridRowIndexOutOfBoundsException.class,
        () -> bs.setBookshelfGridIndex(INVALID_ROW, VALID_COL, validTile));
  }

  /**
   * Testing column boundaries in setBookshelfGridIndex.
   */
  @Test
  public void setBookshelfGridIndex_should_throw_BookshelfGridColIndexOutOfBoundsException() {

    Bookshelf bs = new Bookshelf();
    final Integer VALID_ROW = 1;
    final Integer INVALID_COL = 99;
    final Tile validTile = new Tile(TileType.BOOK);
    assertThrows(BookshelfGridColIndexOutOfBoundsException.class,
        () -> bs.setBookshelfGridIndex(VALID_ROW, INVALID_COL, validTile));
  }

  /**
   * Testing column boundaries in setBookshelfGridIndex.
   */
  @Test
  public void setBookshelfGridIndex_should_throw_NullTileException() {

    Bookshelf bs = new Bookshelf();
    final Integer VALID_ROW = 1;
    final Integer VALID_COL = 1;
    final Tile invalidTile = null;
    assertThrows(NullTileException.class,
        () -> bs.setBookshelfGridIndex(VALID_ROW, VALID_COL, invalidTile));
  }

  /**
   * Testing row boundaries in getBookShelfGridAt.
   */
  @Test
  public void getBookShelfGridAt_should_throw_BookshelfGridRowIndexOutOfBoundsException() {

    Bookshelf bs = new Bookshelf();
    final Integer INVALID_ROW = 99;
    final Integer VALID_COL = 1;
    assertThrows(BookshelfGridRowIndexOutOfBoundsException.class,
        () -> bs.getBookshelfGridAt(INVALID_ROW, VALID_COL));
  }

  /**
   * Testing column boundaries in getBookShelfGridAt.
   */
  @Test
  public void getBookShelfGridAt_should_throw_BookshelfGridColIndexOutOfBoundsException() {

    Bookshelf bs = new Bookshelf();
    final Integer VALID_ROW = 1;
    final Integer INVALID_COL = 99;
    assertThrows(BookshelfGridColIndexOutOfBoundsException.class,
        () -> bs.getBookshelfGridAt(VALID_ROW, INVALID_COL));
  }

  /**
   * Testing setBookshelfGridIndex + getBookShelfGridAt green path.
   *
   * @throws NullTileException
   * @throws NullIndexValueException
   * @throws BookshelfGridRowIndexOutOfBoundsException
   * @throws BookshelfGridColIndexOutOfBoundsException
   */
  @Test
  public void getBookShelfGridAt_should_get_Tile()
      throws BookshelfGridColIndexOutOfBoundsException,
      BookshelfGridRowIndexOutOfBoundsException, NullIndexValueException, NullTileException {

    Bookshelf bs = new Bookshelf();
    final Integer VALID_ROW = 1;
    final Integer VALID_COL = 1;
    final Tile validTile = new Tile(TileType.BOOK);

    bs.setBookshelfGridIndex(VALID_ROW, VALID_COL, validTile);
    Tile res = bs.getBookshelfGridAt(VALID_ROW, VALID_COL);
    assertNotNull(res);
    assertEquals(validTile, res);
  }
}
