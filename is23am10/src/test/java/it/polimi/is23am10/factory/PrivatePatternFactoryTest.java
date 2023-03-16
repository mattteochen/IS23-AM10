package it.polimi.is23am10.factory;

import static org.junit.Assert.assertTrue;

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
    Bookshelf testPattern1ZeroMatch = new Bookshelf(
      "CCCGF" +
      "FTBFF" +
      "FCCFF" +
      "CFGFT" +
      "PPCBC" +
      "BCFPC");

    assertTrue(PrivatePatternFactory.checkPattern1.apply(testPattern1ZeroMatch).equals(0));
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

    assertTrue(PrivatePatternFactory.checkPattern1.apply(testPattern1).equals(6));
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

    assertTrue(PrivatePatternFactory.checkPattern2.apply(testPattern2ZeroMatches).equals(0));
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

    assertTrue(PrivatePatternFactory.checkPattern2.apply(testPattern2).equals(6));
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

    assertTrue(PrivatePatternFactory.checkPattern3.apply(testPattern3ZeroMatches).equals(0));
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

    assertTrue(PrivatePatternFactory.checkPattern3.apply(testPattern3).equals(6));
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

    assertTrue(PrivatePatternFactory.checkPattern4.apply(testPattern4ZeroMatches).equals(0));
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

    assertTrue(PrivatePatternFactory.checkPattern4.apply(testPattern4).equals(6));
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

    assertTrue(PrivatePatternFactory.checkPattern5.apply(testPattern5ZeroMatches).equals(0));
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

    assertTrue(PrivatePatternFactory.checkPattern5.apply(testPattern5).equals(6));
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

    assertTrue(PrivatePatternFactory.checkPattern6.apply(testPattern6ZeroMatches).equals(0));
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

    assertTrue(PrivatePatternFactory.checkPattern6.apply(testPattern6).equals(6));
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

    assertTrue(PrivatePatternFactory.checkPattern7.apply(testPattern7ZeroMatches).equals(0));
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

    assertTrue(PrivatePatternFactory.checkPattern7.apply(testPattern7).equals(6));
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

    assertTrue(PrivatePatternFactory.checkPattern8.apply(testPattern8ZeroMatches).equals(0));
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

    assertTrue(PrivatePatternFactory.checkPattern8.apply(testPattern8).equals(6));
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

    assertTrue(PrivatePatternFactory.checkPattern9.apply(testPattern9ZeroMatches).equals(0));
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

    assertTrue(PrivatePatternFactory.checkPattern9.apply(testPattern9).equals(6));
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

    assertTrue(PrivatePatternFactory.checkPattern10.apply(testPattern10ZeroMatches).equals(0));
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

    assertTrue(PrivatePatternFactory.checkPattern10.apply(testPattern10).equals(6));
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

    assertTrue(PrivatePatternFactory.checkPattern11.apply(testPattern11ZeroMatches).equals(0));
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

    assertTrue(PrivatePatternFactory.checkPattern11.apply(testPattern11).equals(6));
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

    assertTrue(PrivatePatternFactory.checkPattern12.apply(testPattern12ZeroMatches).equals(0));
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

    assertTrue(PrivatePatternFactory.checkPattern12.apply(testPattern12).equals(6));
  };
}