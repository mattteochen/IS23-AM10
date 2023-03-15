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
            "PCFGF" +
            "FTBFC" +
            "FCCBF" +
            "CGGFT" +
            "PPCBC" +
            "BCTPC");

    Bookshelf testPattern2 = new Bookshelf(
            "CCBGF" +
            "FPBFC" +
            "CCGGF" +
            "CBGFB" +
            "PPCTC" +
            "BCBPF");

    Bookshelf testPattern3 = new Bookshelf(
            "CCBGF" +
            "FTBGC" +
            "FCPGF" +
            "CCGFT" +
            "PPCBC" +
            "BCBPC");
          
    Bookshelf testPattern4 = new Bookshelf(
            "CCBGG" +
            "FTBFC" +
            "TCFGF" +
            "CBGPT" +
            "PBCBC" +
            "BCBPC");

    Bookshelf testPattern5 = new Bookshelf(
            "CCBGF" +
            "FTBFC" +
            "FCCGF" +
            "CFBFT" +
            "PPCBP" +
            "GCBCC");
    
    Bookshelf testPattern6 = new Bookshelf(
            "CCTGC" +
            "FTBFC" +
            "FCCBF" +
            "CBGFT" +
            "PGCFC" +
            "PCBPC");
    Bookshelf testPattern7 = new Bookshelf(
            "CCBGF" +
            "FTBFC" +
            "FPCGF" +
            "TBGFT" +
            "PPCBG" +
            "BCBPC");
    Bookshelf testPattern8 = new Bookshelf(
            "CCBGF" +
            "FCBFC" +
            "FCTGF" +
            "PBGFT" +
            "PPCBC" +
            "BCBGC");
    Bookshelf testPattern9 = new Bookshelf(
            "CCGGF" +
            "FTBFC" +
            "FCCGF" +
            "CBGFB" +
            "PTCBP" +
            "FCBPC");

    Bookshelf testPattern10 = new Bookshelf(
            "CCBGT" +
            "FGBFC" +
            "BCCGF" +
            "CBGCT" +
            "PFCFC" +
            "BCBPC");

    Bookshelf testPattern11 = new Bookshelf(
            "CCPGF" +
            "FBBFC" +
            "GCCGF" +
            "CBFFT" +
            "PPCBC" +
            "BCBTC");

    Bookshelf testPattern12 = new Bookshelf(
            "CCBGF" +
            "FPBFC" +
            "FCFGF" +
            "CBGTT" +
            "PPCBG" +
            "CCBPC");
      

    assertTrue(PrivatePatternFactory.checkPattern1.apply(testPattern1) == 6);
    assertTrue(PrivatePatternFactory.checkPattern2.apply(testPattern2) == 6);
    assertTrue(PrivatePatternFactory.checkPattern3.apply(testPattern3) == 6);
    assertTrue(PrivatePatternFactory.checkPattern4.apply(testPattern4) == 6);
    assertTrue(PrivatePatternFactory.checkPattern5.apply(testPattern5) == 6);
    assertTrue(PrivatePatternFactory.checkPattern6.apply(testPattern6) == 6);
    assertTrue(PrivatePatternFactory.checkPattern7.apply(testPattern7) == 6);
    assertTrue(PrivatePatternFactory.checkPattern8.apply(testPattern8) == 6);
    assertTrue(PrivatePatternFactory.checkPattern9.apply(testPattern9) == 6);
    assertTrue(PrivatePatternFactory.checkPattern10.apply(testPattern10) == 6);
    assertTrue(PrivatePatternFactory.checkPattern11.apply(testPattern11) == 6);
    assertTrue(PrivatePatternFactory.checkPattern12.apply(testPattern12) == 6);
  };
}
