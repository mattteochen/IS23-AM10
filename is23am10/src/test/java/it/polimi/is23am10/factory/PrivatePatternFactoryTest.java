package it.polimi.is23am10.factory;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import it.polimi.is23am10.items.bookshelf.Bookshelf;
import it.polimi.is23am10.items.bookshelf.exceptions.WrongCharBookshelfStringException;
import it.polimi.is23am10.items.bookshelf.exceptions.WrongLengthBookshelfStringException;
import it.polimi.is23am10.items.tile.exceptions.WrongTileTypeException;

public class PrivatePatternFactoryTest {
  /**
   * 
   * @throws WrongLengthBookshelfStringException
   * @throws WrongCharBookshelfStringException
   * @throws NullPointerException
   * @throws WrongTileTypeException
   */

  @Test
  public void RULE1_should_find_ZERO_matches()
    throws WrongLengthBookshelfStringException, WrongCharBookshelfStringException, NullPointerException, WrongTileTypeException {
    Bookshelf testPattern1ZeroMatches = new Bookshelf(
      "CCCGF" +
      "FTBFF" +
      "FCCFF" +
      "CFGFT" +
      "PPCBC" +
      "BCFPC");

    assertEquals(0, PrivatePatternFactory.checkPattern1.apply(testPattern1ZeroMatches));
  };

  @Test
  public void RULE1_should_find_SIX_matches()
  throws WrongLengthBookshelfStringException, WrongCharBookshelfStringException, NullPointerException, WrongTileTypeException {
    Bookshelf testPattern1 = new Bookshelf(
      "PCFGF" +
      "FTBFC" +
      "FCCBF" +
      "CGGFT" +
      "PPCBC" +
      "BCTPC");

    assertEquals(6, PrivatePatternFactory.checkPattern1.apply(testPattern1));
  };

  @Test
  public void RULE2_should_find_ZERO_matches()
  throws WrongLengthBookshelfStringException, WrongCharBookshelfStringException, NullPointerException, WrongTileTypeException {
    Bookshelf testPattern2ZeroMatches = new Bookshelf(
      "CCBGF" +
      "FFBFC" +
      "FCFGF" +
      "CBGFF" +
      "PPCFC" +
      "BCBPB");

    assertEquals(0, PrivatePatternFactory.checkPattern2.apply(testPattern2ZeroMatches));
  };

  @Test
  public void RULE2_should_find_SIX_matches()
  throws WrongLengthBookshelfStringException, WrongCharBookshelfStringException, NullPointerException, WrongTileTypeException {
    Bookshelf testPattern2 = new Bookshelf(
      "CCBGF" +
      "FPBFC" +
      "CCGGF" +
      "CBGFB" +
      "PPCTC" +
      "BCBPF");

    assertEquals(6, PrivatePatternFactory.checkPattern2.apply(testPattern2));
  };

  @Test
  public void RULE3_should_find_ZERO_matches()
  throws WrongLengthBookshelfStringException, WrongCharBookshelfStringException, NullPointerException, WrongTileTypeException {
    Bookshelf testPattern3ZeroMatches = new Bookshelf(
      "CCBGF" +
      "BTBBC" +
      "FCBGF" +
      "CBGFB" +
      "PPCBC" +
      "FCBPC");

    assertEquals(0, PrivatePatternFactory.checkPattern3.apply(testPattern3ZeroMatches));
  };

  @Test
  public void RULE3_should_find_SIX_matches()
  throws WrongLengthBookshelfStringException, WrongCharBookshelfStringException, NullPointerException, WrongTileTypeException {
    Bookshelf testPattern3 = new Bookshelf(
      "CCBGF" +
      "FTBGC" +
      "FCPGF" +
      "CCGFT" +
      "PPCBC" +
      "BCBPC");

    assertEquals(6, PrivatePatternFactory.checkPattern3.apply(testPattern3));
  };

  @Test
  public void RULE4_should_find_ZERO_matches()
  throws WrongLengthBookshelfStringException, WrongCharBookshelfStringException, NullPointerException, WrongTileTypeException {
    Bookshelf testPattern4ZeroMatches = new Bookshelf(
      "CCBGF" +
      "FTBFC" +
      "BCBGF" +
      "CBGBT" +
      "PCBFC" +
      "BCBPC");

    assertEquals(0, PrivatePatternFactory.checkPattern4.apply(testPattern4ZeroMatches));;
  };

  @Test
  public void RULE4_should_find_SIX_matches()
  throws WrongLengthBookshelfStringException, WrongCharBookshelfStringException, NullPointerException, WrongTileTypeException {
    Bookshelf testPattern4 = new Bookshelf(
      "CCBGG" +
      "FTBFC" +
      "TCFGF" +
      "CBGPT" +
      "PBCBC" +
      "BCBPC");

    assertEquals(6, PrivatePatternFactory.checkPattern4.apply(testPattern4));
  };

  @Test
  public void RULE5_should_find_ZERO_matches()
  throws WrongLengthBookshelfStringException, WrongCharBookshelfStringException, NullPointerException, WrongTileTypeException {
    Bookshelf testPattern5ZeroMatches = new Bookshelf(
      "CCBGF" +
      "FCBFC" +
      "FCCGF" +
      "CCCFT" +
      "PPCBC" +
      "BCBBC");

    assertEquals(0, PrivatePatternFactory.checkPattern5.apply(testPattern5ZeroMatches));
  };

  @Test
  public void RULE5_should_find_SIX_matches()
  throws WrongLengthBookshelfStringException, WrongCharBookshelfStringException, NullPointerException, WrongTileTypeException {
    Bookshelf testPattern5 = new Bookshelf(
      "CCBGF" +
      "FTBFC" +
      "FCCGF" +
      "CFBFT" +
      "PPCBP" +
      "GCBCC");

    assertEquals(6, PrivatePatternFactory.checkPattern5.apply(testPattern5));
  };

  @Test
  public void RULE6_should_find_ZERO_matches()
  throws WrongLengthBookshelfStringException, WrongCharBookshelfStringException, NullPointerException, WrongTileTypeException {
    Bookshelf testPattern6ZeroMatches = new Bookshelf(
      "CCFGF" +
      "FTBFC" +
      "FCCFF" +
      "CBGFT" +
      "PFCBC" +
      "FCBPC");

    assertEquals(0, PrivatePatternFactory.checkPattern6.apply(testPattern6ZeroMatches));
  };

  @Test
  public void RULE6_should_find_SIX_matches()
  throws WrongLengthBookshelfStringException, WrongCharBookshelfStringException, NullPointerException, WrongTileTypeException {
    Bookshelf testPattern6 = new Bookshelf(
      "CCTGC" +
      "FTBFC" +
      "FCCBF" +
      "CBGFT" +
      "PGCFC" +
      "PCBPC");

    assertEquals(6, PrivatePatternFactory.checkPattern6.apply(testPattern6));
  };

  @Test
  public void RULE7_should_find_ZERO_matches()
  throws WrongLengthBookshelfStringException, WrongCharBookshelfStringException, NullPointerException, WrongTileTypeException {
    Bookshelf testPattern7ZeroMatches = new Bookshelf(
      "FCBGF" +
      "FTBBC" +
      "FBCGF" +
      "BBGFT" +
      "PPCBB" +
      "BCFPC");

    assertEquals(0, PrivatePatternFactory.checkPattern7.apply(testPattern7ZeroMatches));
  };

  @Test
  public void RULE7_should_find_SIX_matches()
  throws WrongLengthBookshelfStringException, WrongCharBookshelfStringException, NullPointerException, WrongTileTypeException {
    Bookshelf testPattern7 = new Bookshelf(
      "CCBGF" +
      "FTBFC" +
      "FPCGF" +
      "TBGFT" +
      "PPCBG" +
      "BCBPC");

    assertEquals(6, PrivatePatternFactory.checkPattern7.apply(testPattern7));
  };

  @Test
  public void RULE8_should_find_ZERO_matches()
  throws WrongLengthBookshelfStringException, WrongCharBookshelfStringException, NullPointerException, WrongTileTypeException {
    Bookshelf testPattern8ZeroMatches = new Bookshelf(
      "CCBGB" +
      "FBBFC" +
      "FCBGF" +
      "BBGFT" +
      "PPCCC" +
      "BCBCC");

    assertEquals(0, PrivatePatternFactory.checkPattern8.apply(testPattern8ZeroMatches));
  };

  @Test
  public void RULE8_should_find_SIX_matches()
  throws WrongLengthBookshelfStringException, WrongCharBookshelfStringException, NullPointerException, WrongTileTypeException {
    Bookshelf testPattern8 = new Bookshelf(
      "CCBGF" +
      "FCBFC" +
      "FCTGF" +
      "PBGFT" +
      "PPCBC" +
      "BCBGC");

    assertEquals(6, PrivatePatternFactory.checkPattern8.apply(testPattern8));
  };

  @Test
  public void RULE9_should_find_ZERO_matches()
  throws WrongLengthBookshelfStringException, WrongCharBookshelfStringException, NullPointerException, WrongTileTypeException {
    Bookshelf testPattern9ZeroMatches = new Bookshelf(
      "CCPGF" +
      "FTBFC" +
      "FCPGF" +
      "CBGFP" +
      "PBCBB" +
      "PCBPC");

    assertEquals(0, PrivatePatternFactory.checkPattern9.apply(testPattern9ZeroMatches));
  };

  @Test
  public void RULE9_should_find_SIX_matches()
  throws WrongLengthBookshelfStringException, WrongCharBookshelfStringException, NullPointerException, WrongTileTypeException {
    Bookshelf testPattern9 = new Bookshelf(
      "CCGGF" +
      "FTBFC" +
      "FCCGF" +
      "CBGFB" +
      "PTCBP" +
      "FCBPC");

    assertEquals(6, PrivatePatternFactory.checkPattern9.apply(testPattern9));
  };

  @Test
  public void RULE10_should_find_ZERO_matches()
  throws WrongLengthBookshelfStringException, WrongCharBookshelfStringException, NullPointerException, WrongTileTypeException {
    Bookshelf testPattern10ZeroMatches = new Bookshelf(
      "CCBGP" +
      "FPBFC" +
      "PCCGF" +
      "CBGPT" +
      "PPCFC" +
      "BCBBC");

    assertEquals(0, PrivatePatternFactory.checkPattern10.apply(testPattern10ZeroMatches));
  };

  @Test
  public void RULE10_should_find_SIX_matches()
  throws WrongLengthBookshelfStringException, WrongCharBookshelfStringException, NullPointerException, WrongTileTypeException {
    Bookshelf testPattern10 = new Bookshelf(
      "CCBGT" +
      "FGBFC" +
      "BCCGF" +
      "CBGCT" +
      "PFCFC" +
      "BCBPC");

    assertEquals(6, PrivatePatternFactory.checkPattern10.apply(testPattern10));
  };

  @Test
  public void RULE11_should_find_ZERO_matches()
  throws WrongLengthBookshelfStringException, WrongCharBookshelfStringException, NullPointerException, WrongTileTypeException {
    Bookshelf testPattern11ZeroMatches = new Bookshelf(
      "CCFGF" +
      "FFBFC" +
      "FCCGF" +
      "CBBFT" +
      "PPCBB" +
      "BCBBC");

    assertEquals(0, PrivatePatternFactory.checkPattern11.apply(testPattern11ZeroMatches));
  };

  @Test
  public void RULE11_should_find_SIX_matches()
  throws WrongLengthBookshelfStringException, WrongCharBookshelfStringException, NullPointerException, WrongTileTypeException {
    Bookshelf testPattern11 = new Bookshelf(
      "CCPGF" +
      "FBBFC" +
      "GCCGF" +
      "CBFFT" +
      "PPCBC" +
      "BCBTC");

    assertEquals(6, PrivatePatternFactory.checkPattern11.apply(testPattern11));
  };

  @Test
  public void RULE12_should_find_ZERO_matches()
  throws WrongLengthBookshelfStringException, WrongCharBookshelfStringException, NullPointerException, WrongTileTypeException {
    Bookshelf testPattern12ZeroMatches = new Bookshelf(
      "CCFGF" +
      "FFBFC" +
      "FCBGF" +
      "CBGBT" +
      "PPCBB" +
      "BCBPC");

    assertEquals(0, PrivatePatternFactory.checkPattern12.apply(testPattern12ZeroMatches));;
  };

  @Test
  public void RULE12_should_find_SIX_matches()
  throws WrongLengthBookshelfStringException, WrongCharBookshelfStringException, NullPointerException, WrongTileTypeException {
    Bookshelf testPattern12 = new Bookshelf(
      "CCBGF" +
      "FPBFC" +
      "FCFGF" +
      "CBGTT" +
      "PPCBG" +
      "CCBPC");

    assertEquals(6, PrivatePatternFactory.checkPattern12.apply(testPattern12));
  };
}