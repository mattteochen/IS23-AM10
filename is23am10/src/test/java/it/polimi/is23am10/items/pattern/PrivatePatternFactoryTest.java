package it.polimi.is23am10.items.pattern;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

import it.polimi.is23am10.items.bookshelf.Bookshelf;
import it.polimi.is23am10.items.bookshelf.exceptions.WrongCharBookshelfStringException;
import it.polimi.is23am10.items.bookshelf.exceptions.WrongLengthBookshelfStringException;
import it.polimi.is23am10.items.tile.exceptions.WrongTileTypeException;
import it.polimi.is23am10.pattern.PrivatePatternFactory;

public class PrivatePatternFactoryTest {

  @Test
  public void TEST_PRIVATE_PATTERNS_SHOULD_RETURN_TRUE()
      throws WrongLengthBookshelfStringException, WrongCharBookshelfStringException,
      NullPointerException, WrongTileTypeException {
    Bookshelf testPattern1 = new Bookshelf(
            "CCBGF" +
            "FTBFC" +
            "FCCGF" +
            "CBGFT" +
            "PPCBC" +
            "BCBPC");

    Bookshelf testPattern2 = new Bookshelf(
            "CCBGF" +
            "FTBFC" +
            "FCCGF" +
            "CBGFT" +
            "PPCBC" +
            "BCBPC");

    Bookshelf testPattern3 = new Bookshelf(
            "CCBGF" +
            "FTBFC" +
            "FCCGF" +
            "CBGFT" +
            "PPCBC" +
            "BCBPC");
          
    Bookshelf testPattern4 = new Bookshelf(
            "CCBGF" +
            "FTBFC" +
            "FCCGF" +
            "CBGFT" +
            "PPCBC" +
            "BCBPC");

    Bookshelf testPattern5 = new Bookshelf(
            "CCBGF" +
            "FTBFC" +
            "FCCGF" +
            "CBGFT" +
            "PPCBC" +
            "BCBPC");
    
    Bookshelf testPattern6 = new Bookshelf(
            "CCBGF" +
            "FTBFC" +
            "FCCGF" +
            "CBGFT" +
            "PPCBC" +
            "BCBPC");
    Bookshelf testPattern7 = new Bookshelf(
            "CCBGF" +
            "FTBFC" +
            "FCCGF" +
            "CBGFT" +
            "PPCBC" +
            "BCBPC");
    Bookshelf testPattern8 = new Bookshelf(
            "CCBGF" +
            "FTBFC" +
            "FCCGF" +
            "CBGFT" +
            "PPCBC" +
            "BCBPC");
    Bookshelf testPattern9 = new Bookshelf(
            "CCBGF" +
            "FTBFC" +
            "FCCGF" +
            "CBGFT" +
            "PPCBC" +
            "BCBPC");

    Bookshelf testPattern10 = new Bookshelf(
            "CCBGF" +
            "FTBFC" +
            "FCCGF" +
            "CBGFT" +
            "PPCBC" +
            "BCBPC");

    Bookshelf testPattern11 = new Bookshelf(
            "CCBGF" +
            "FTBFC" +
            "FCCGF" +
            "CBGFT" +
            "PPCBC" +
            "BCBPC");

    Bookshelf testPattern12 = new Bookshelf(
            "CCBGF" +
            "FTBFC" +
            "FCCGF" +
            "CBGFT" +
            "PPCBC" +
            "BCBPC");

    assertTrue(PrivatePatternFactory.checkPattern1.test(testPattern1));

  };
}
