package it.polimi.is23am10.factory;

import it.polimi.is23am10.items.bookshelf.Bookshelf;
import it.polimi.is23am10.items.bookshelf.exceptions.BookshelfGridColIndexOutOfBoundsException;
import it.polimi.is23am10.items.bookshelf.exceptions.BookshelfGridRowIndexOutOfBoundsException;
import it.polimi.is23am10.items.tile.Tile.TileType;
import it.polimi.is23am10.pattern.PrivatePattern;
import it.polimi.is23am10.utils.exceptions.NullIndexValueException;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

/**
 * Private pattern factory object.
 * This creates a new {@link PrivatePattern}
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public final class PrivatePatternFactory {

  /**
   * Private constructor.
   * 
   */
  private PrivatePatternFactory() {

  }

  /**
   * The random generator instance.
   * 
   */
  private static final Random random = new Random();

  /**
   * PrivatePattern Rule #1.
   * 
   */
  public static final Function<Bookshelf, Integer> checkPattern1 = bs -> {
    int tilesMatched = 0;

    try {
      if (bs.getBookshelfGridAt(0, 0).getType() == (TileType.PLANT)) {
        tilesMatched++;
      }
      if (bs.getBookshelfGridAt(0, 2).getType() == (TileType.FRAME)) {
        tilesMatched++;
      }
      if (bs.getBookshelfGridAt(1, 4).getType() == (TileType.CAT)) {
        tilesMatched++;
      }
      if (bs.getBookshelfGridAt(2, 3).getType() == (TileType.BOOK)) {
        tilesMatched++;
      }
      if (bs.getBookshelfGridAt(3, 1).getType() == (TileType.GAME)) {
        tilesMatched++;
      }
      if (bs.getBookshelfGridAt(5, 2).getType() == (TileType.TROPHY)) {
        tilesMatched++;
      }
      return tilesMatched;
    } catch (BookshelfGridColIndexOutOfBoundsException | BookshelfGridRowIndexOutOfBoundsException
        | NullIndexValueException e) {
      return 0;
    }
  };

  /**
   * PrivatePattern Rule #2.
   * 
   */
  public static final Function<Bookshelf, Integer> checkPattern2 = bs -> {
    int tilesMatched = 0;

    try {
      if (bs.getBookshelfGridAt(1, 1).getType() == (TileType.PLANT)) {
        tilesMatched++;
      }
      if (bs.getBookshelfGridAt(2, 0).getType() == (TileType.CAT)) {
        tilesMatched++;
      }
      if (bs.getBookshelfGridAt(2, 2).getType() == (TileType.GAME)) {
        tilesMatched++;
      }
      if (bs.getBookshelfGridAt(3, 4).getType() == (TileType.BOOK)) {
        tilesMatched++;
      }
      if (bs.getBookshelfGridAt(4, 3).getType() == (TileType.TROPHY)) {
        tilesMatched++;
      }
      if (bs.getBookshelfGridAt(5, 4).getType() == (TileType.FRAME)) {
        tilesMatched++;
      }
      return tilesMatched;
    } catch (BookshelfGridColIndexOutOfBoundsException | BookshelfGridRowIndexOutOfBoundsException
        | NullIndexValueException e) {
      return 0;
    }
  };

  /**
   * PrivatePattern Rule #3.
   * 
   */
  public static final Function<Bookshelf, Integer> checkPattern3 = bs -> {
    int tilesMatched = 0;

    try {
      if (bs.getBookshelfGridAt(1, 0).getType() == (TileType.FRAME)) {
        tilesMatched++;
      }
      if (bs.getBookshelfGridAt(1, 3).getType() == (TileType.GAME)) {
        tilesMatched++;
      }
      if (bs.getBookshelfGridAt(2, 2).getType() == (TileType.PLANT)) {
        tilesMatched++;
      }
      if (bs.getBookshelfGridAt(3, 1).getType() == (TileType.CAT)) {
        tilesMatched++;
      }
      if (bs.getBookshelfGridAt(3, 4).getType() == (TileType.TROPHY)) {
        tilesMatched++;
      }
      if (bs.getBookshelfGridAt(5, 0).getType() == (TileType.BOOK)) {
        tilesMatched++;
      }
      return tilesMatched;
    } catch (BookshelfGridColIndexOutOfBoundsException | BookshelfGridRowIndexOutOfBoundsException
        | NullIndexValueException e) {
      return 0;
    }
  };

  /**
   * PrivatePattern Rule #4.
   * 
   */
  public static final Function<Bookshelf, Integer> checkPattern4 = bs -> {
    int tilesMatched = 0;

    try {
      if (bs.getBookshelfGridAt(0, 4).getType() == (TileType.GAME)) {
        tilesMatched++;
      }
      if (bs.getBookshelfGridAt(2, 0).getType() == (TileType.TROPHY)) {
        tilesMatched++;
      }
      if (bs.getBookshelfGridAt(2, 2).getType() == (TileType.FRAME)) {
        tilesMatched++;
      }
      if (bs.getBookshelfGridAt(3, 3).getType() == (TileType.PLANT)) {
        tilesMatched++;
      }
      if (bs.getBookshelfGridAt(4, 1).getType() == (TileType.BOOK)) {
        tilesMatched++;
      }
      if (bs.getBookshelfGridAt(4, 2).getType() == (TileType.CAT)) {
        tilesMatched++;
      }
      return tilesMatched;
    } catch (BookshelfGridColIndexOutOfBoundsException | BookshelfGridRowIndexOutOfBoundsException
        | NullIndexValueException e) {
      return 0;
    }
  };

  /**
   * PrivatePattern Rule #5.
   * 
   */
  public static final Function<Bookshelf, Integer> checkPattern5 = bs -> {
    int tilesMatched = 0;

    try {
      if (bs.getBookshelfGridAt(1, 1).getType() == (TileType.TROPHY)) {
        tilesMatched++;
      }
      if (bs.getBookshelfGridAt(3, 1).getType() == (TileType.FRAME)) {
        tilesMatched++;
      }
      if (bs.getBookshelfGridAt(3, 2).getType() == (TileType.BOOK)) {
        tilesMatched++;
      }
      if (bs.getBookshelfGridAt(4, 4).getType() == (TileType.PLANT)) {
        tilesMatched++;
      }
      if (bs.getBookshelfGridAt(5, 0).getType() == (TileType.GAME)) {
        tilesMatched++;
      }
      if (bs.getBookshelfGridAt(5, 3).getType() == (TileType.CAT)) {
        tilesMatched++;
      }
      return tilesMatched;
    } catch (BookshelfGridColIndexOutOfBoundsException | BookshelfGridRowIndexOutOfBoundsException
        | NullIndexValueException e) {
      return 0;
    }
  };

  /**
   * PrivatePattern Rule #6.
   * 
   */
  public static final Function<Bookshelf, Integer> checkPattern6 = bs -> {
    int tilesMatched = 0;

    try {
      if (bs.getBookshelfGridAt(0, 2).getType() == (TileType.TROPHY)) {
        tilesMatched++;
      }
      if (bs.getBookshelfGridAt(0, 4).getType() == (TileType.CAT)) {
        tilesMatched++;
      }
      if (bs.getBookshelfGridAt(2, 3).getType() == (TileType.BOOK)) {
        tilesMatched++;
      }
      if (bs.getBookshelfGridAt(4, 1).getType() == (TileType.GAME)) {
        tilesMatched++;
      }
      if (bs.getBookshelfGridAt(4, 3).getType() == (TileType.FRAME)) {
        tilesMatched++;
      }
      if (bs.getBookshelfGridAt(5, 0).getType() == (TileType.PLANT)) {
        tilesMatched++;
      }
      return tilesMatched;
    } catch (BookshelfGridColIndexOutOfBoundsException | BookshelfGridRowIndexOutOfBoundsException
        | NullIndexValueException e) {
      return 0;
    }
  };

  /**
   * PrivatePattern Rule #7.
   * 
   */
  public static final Function<Bookshelf, Integer> checkPattern7 = bs -> {
    int tilesMatched = 0;

    try {
      if (bs.getBookshelfGridAt(0, 0).getType() == (TileType.CAT)) {
        tilesMatched++;
      }
      if (bs.getBookshelfGridAt(1, 3).getType() == (TileType.FRAME)) {
        tilesMatched++;
      }
      if (bs.getBookshelfGridAt(2, 1).getType() == (TileType.PLANT)) {
        tilesMatched++;
      }
      if (bs.getBookshelfGridAt(3, 0).getType() == (TileType.TROPHY)) {
        tilesMatched++;
      }
      if (bs.getBookshelfGridAt(4, 4).getType() == (TileType.GAME)) {
        tilesMatched++;
      }
      if (bs.getBookshelfGridAt(5, 2).getType() == (TileType.BOOK)) {
        tilesMatched++;
      }
      return tilesMatched;
    } catch (BookshelfGridColIndexOutOfBoundsException | BookshelfGridRowIndexOutOfBoundsException
        | NullIndexValueException e) {
      return 0;
    }
  };

  /**
   * PrivatePattern Rule #8.
   * 
   */
  public static final Function<Bookshelf, Integer> checkPattern8 = bs -> {
    int tilesMatched = 0;

    try {
      if (bs.getBookshelfGridAt(0, 4).getType() == (TileType.FRAME)) {
        tilesMatched++;
      }
      if (bs.getBookshelfGridAt(1, 1).getType() == (TileType.CAT)) {
        tilesMatched++;
      }
      if (bs.getBookshelfGridAt(2, 2).getType() == (TileType.TROPHY)) {
        tilesMatched++;
      }
      if (bs.getBookshelfGridAt(3, 0).getType() == (TileType.PLANT)) {
        tilesMatched++;
      }
      if (bs.getBookshelfGridAt(4, 3).getType() == (TileType.BOOK)) {
        tilesMatched++;
      }
      if (bs.getBookshelfGridAt(5, 3).getType() == (TileType.GAME)) {
        tilesMatched++;
      }
      return tilesMatched;
    } catch (BookshelfGridColIndexOutOfBoundsException | BookshelfGridRowIndexOutOfBoundsException
        | NullIndexValueException e) {
      return 0;
    }
  };

  /**
   * PrivatePattern Rule #9.
   * 
   */
  public static final Function<Bookshelf, Integer> checkPattern9 = bs -> {
    int tilesMatched = 0;

    try {
      if (bs.getBookshelfGridAt(0, 2).getType() == (TileType.GAME)) {
        tilesMatched++;
      }
      if (bs.getBookshelfGridAt(2, 2).getType() == (TileType.CAT)) {
        tilesMatched++;
      }
      if (bs.getBookshelfGridAt(3, 4).getType() == (TileType.BOOK)) {
        tilesMatched++;
      }
      if (bs.getBookshelfGridAt(4, 1).getType() == (TileType.TROPHY)) {
        tilesMatched++;
      }
      if (bs.getBookshelfGridAt(4, 4).getType() == (TileType.PLANT)) {
        tilesMatched++;
      }
      if (bs.getBookshelfGridAt(5, 0).getType() == (TileType.FRAME)) {
        tilesMatched++;
      }
      return tilesMatched;
    } catch (BookshelfGridColIndexOutOfBoundsException | BookshelfGridRowIndexOutOfBoundsException
        | NullIndexValueException e) {
      return 0;
    }
  };

  /**
   * PrivatePattern Rule #10.
   * 
   */
  public static final Function<Bookshelf, Integer> checkPattern10 = bs -> {
    int tilesMatched = 0;

    try {
      if (bs.getBookshelfGridAt(0, 4).getType() == (TileType.TROPHY)) {
        tilesMatched++;
      }
      if (bs.getBookshelfGridAt(1, 1).getType() == (TileType.GAME)) {
        tilesMatched++;
      }
      if (bs.getBookshelfGridAt(2, 0).getType() == (TileType.BOOK)) {
        tilesMatched++;
      }
      if (bs.getBookshelfGridAt(3, 3).getType() == (TileType.CAT)) {
        tilesMatched++;
      }
      if (bs.getBookshelfGridAt(4, 1).getType() == (TileType.FRAME)) {
        tilesMatched++;
      }
      if (bs.getBookshelfGridAt(5, 3).getType() == (TileType.PLANT)) {
        tilesMatched++;
      }
      return tilesMatched;
    } catch (BookshelfGridColIndexOutOfBoundsException | BookshelfGridRowIndexOutOfBoundsException
        | NullIndexValueException e) {
      return 0;
    }
  };

  /**
   * PrivatePattern Rule #11.
   * 
   */
  public static final Function<Bookshelf, Integer> checkPattern11 = bs -> {
    int tilesMatched = 0;

    try {
      if (bs.getBookshelfGridAt(0, 2).getType() == (TileType.PLANT)) {
        tilesMatched++;
      }
      if (bs.getBookshelfGridAt(1, 1).getType() == (TileType.BOOK)) {
        tilesMatched++;
      }
      if (bs.getBookshelfGridAt(2, 0).getType() == (TileType.GAME)) {
        tilesMatched++;
      }
      if (bs.getBookshelfGridAt(3, 2).getType() == (TileType.FRAME)) {
        tilesMatched++;
      }
      if (bs.getBookshelfGridAt(4, 4).getType() == (TileType.CAT)) {
        tilesMatched++;
      }
      if (bs.getBookshelfGridAt(5, 3).getType() == (TileType.TROPHY)) {
        tilesMatched++;
      }
      return tilesMatched;
    } catch (BookshelfGridColIndexOutOfBoundsException | BookshelfGridRowIndexOutOfBoundsException
        | NullIndexValueException e) {
      return 0;
    }
  };

  /**
   * PrivatePattern Rule #12.
   * 
   */
  public static final Function<Bookshelf, Integer> checkPattern12 = bs -> {
    int tilesMatched = 0;

    try {
      if (bs.getBookshelfGridAt(0, 2).getType() == (TileType.BOOK)) {
        tilesMatched++;
      }
      if (bs.getBookshelfGridAt(1, 1).getType() == (TileType.PLANT)) {
        tilesMatched++;
      }
      if (bs.getBookshelfGridAt(2, 2).getType() == (TileType.FRAME)) {
        tilesMatched++;
      }
      if (bs.getBookshelfGridAt(3, 3).getType() == (TileType.TROPHY)) {
        tilesMatched++;
      }
      if (bs.getBookshelfGridAt(4, 4).getType() == (TileType.GAME)) {
        tilesMatched++;
      }
      if (bs.getBookshelfGridAt(5, 0).getType() == (TileType.CAT)) {
        tilesMatched++;
      }
      return tilesMatched;
    } catch (BookshelfGridColIndexOutOfBoundsException | BookshelfGridRowIndexOutOfBoundsException
        | NullIndexValueException e) {
      return 0;
    }
  };

  /**
   * The list of {@link PrivatePattern} containing all the 12 different patterns.
   * 
   */
  public static final List<PrivatePattern> patternsArray = List.of(
      (new PrivatePattern(checkPattern1)),
      (new PrivatePattern(checkPattern2)),
      (new PrivatePattern(checkPattern3)),
      (new PrivatePattern(checkPattern4)),
      (new PrivatePattern(checkPattern5)),
      (new PrivatePattern(checkPattern6)),
      (new PrivatePattern(checkPattern7)),
      (new PrivatePattern(checkPattern8)),
      (new PrivatePattern(checkPattern9)),
      (new PrivatePattern(checkPattern10)),
      (new PrivatePattern(checkPattern11)),
      (new PrivatePattern(checkPattern12)));

  /**
   * Method used to get random PrivatePattern between the 12 possible.
   * 
   * @return a random {@link PrivatePattern}.
   * 
   */
  public static PrivatePattern getRandomPattern() {
    return patternsArray.get(random.nextInt(patternsArray.size()));
  }
}
