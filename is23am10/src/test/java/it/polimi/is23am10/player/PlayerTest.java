package it.polimi.is23am10.player;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import it.polimi.is23am10.factory.PlayerFactory;
import it.polimi.is23am10.factory.exceptions.DuplicatePlayerNameException;
import it.polimi.is23am10.factory.exceptions.NullPlayerNamesException;
import it.polimi.is23am10.items.bookshelf.Bookshelf;
import it.polimi.is23am10.items.bookshelf.exceptions.BookshelfGridColIndexOutOfBoundsException;
import it.polimi.is23am10.items.bookshelf.exceptions.BookshelfGridRowIndexOutOfBoundsException;
import it.polimi.is23am10.items.bookshelf.exceptions.WrongCharBookshelfStringException;
import it.polimi.is23am10.items.bookshelf.exceptions.WrongLengthBookshelfStringException;
import it.polimi.is23am10.items.card.PrivateCard;
import it.polimi.is23am10.items.card.exceptions.AlreadyInitiatedPatternException;
import it.polimi.is23am10.items.card.exceptions.NegativeMatchedBlockCountException;
import it.polimi.is23am10.items.card.exceptions.NullMatchedBlockCountException;
import it.polimi.is23am10.items.card.exceptions.NullScoreBlockListException;
import it.polimi.is23am10.items.scoreblock.ScoreBlock;
import it.polimi.is23am10.items.scoreblock.exceptions.NotValidScoreBlockValueException;
import it.polimi.is23am10.items.tile.exceptions.WrongTileTypeException;
import it.polimi.is23am10.player.exceptions.NullPlayerBookshelfException;
import it.polimi.is23am10.player.exceptions.NullPlayerIdException;
import it.polimi.is23am10.player.exceptions.NullPlayerNameException;
import it.polimi.is23am10.player.exceptions.NullPlayerPrivateCardException;
import it.polimi.is23am10.player.exceptions.NullPlayerScoreBlocksException;
import it.polimi.is23am10.player.exceptions.NullPlayerScoreException;
import it.polimi.is23am10.score.Score;
import it.polimi.is23am10.utils.exceptions.NullIndexValueException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;


/**
 * Test class to check getters and setters from
 * the Player class
 */
public class PlayerTest {

  @Nested
  class setPlayerID_tests{

      ArrayList<String> players;
      Player p;

      @BeforeEach
      void setPlayerID_tests_setup()
          throws NullPlayerNameException, NullPlayerIdException, NullPlayerBookshelfException, NullPlayerScoreException,
          NullPlayerPrivateCardException, NullPlayerScoreBlocksException, DuplicatePlayerNameException,
          AlreadyInitiatedPatternException, NullPlayerNamesException {
          players = new ArrayList<String>();
          p = PlayerFactory.getNewPlayer("myNewPlayer", players);
      }
      
      @Test
      public void setPlayerID_should_set_playerID() throws NullPlayerIdException{
          UUID dummyUuid = UUID.randomUUID();
          p.setPlayerID(dummyUuid);
          assertNotNull(p.getPlayerID());
          assertEquals(dummyUuid, p.getPlayerID());
      }
  
      @Test
      public void setPlayerID_should_throw_NullPlayerIdException() throws NullPlayerIdException {
          UUID dummyUuid = null;
          assertThrows(NullPlayerIdException.class, () -> p.setPlayerID(dummyUuid));
      }

  }

  @Nested
  class setScore_tests{

      ArrayList<String> players;
      Player p;

      @BeforeEach
      void setScore_tests_setup()
          throws NullPlayerNameException, NullPlayerIdException, NullPlayerBookshelfException, NullPlayerScoreException,
          NullPlayerPrivateCardException, NullPlayerScoreBlocksException, DuplicatePlayerNameException,
          AlreadyInitiatedPatternException, NullPlayerNamesException {
          players = new ArrayList<String>();
          p = PlayerFactory.getNewPlayer("myNewPlayer", players);
      }
      
      @Test
      public void setScore_should_set_Score() throws NullPlayerScoreException{
          Score dummyScore = new Score();
          p.setScore(dummyScore);
          assertNotNull(p.getScore());
          assertEquals(dummyScore, p.getScore());
      }
  
      @Test
      public void setScore_should_throw_NullPlayerScoreException() throws NullPlayerScoreException {
          Score dummyScore = null;
          assertThrows(NullPlayerScoreException.class, () -> p.setScore(dummyScore));
      }

  }
  
  @Nested
  class setBookshelf_tests{

      ArrayList<String> players;
      Player p;

      @BeforeEach
      void setBookshelf_tests_setup()
          throws NullPlayerNameException, NullPlayerIdException, NullPlayerBookshelfException, NullPlayerScoreException,
          NullPlayerPrivateCardException, NullPlayerScoreBlocksException, DuplicatePlayerNameException,
          AlreadyInitiatedPatternException, NullPlayerNamesException {
          players = new ArrayList<String>();
          p = PlayerFactory.getNewPlayer("myNewPlayer", players);
      }
      
      @Test
      public void setBookshelf_should_set_Bookshelf() throws NullPlayerBookshelfException{
          Bookshelf dummyBookshelf = new Bookshelf();
          p.setBookshelf(dummyBookshelf);
          assertNotNull(p.getBookshelf());
          assertEquals(dummyBookshelf, p.getBookshelf());
      }
  
      @Test
      public void setBookshelf_should_throw_NullPlayerBookshelfException() throws NullPlayerBookshelfException {
          Bookshelf dummyBookshelf = null;
          assertThrows(NullPlayerBookshelfException.class, () -> p.setBookshelf(dummyBookshelf));
      }

  }
  
  @Nested
  class setPrivateCard_tests{

    ArrayList<String> players;
    Player p;

    @BeforeEach
    void setPrivateCard_tests_setup()
        throws NullPlayerNameException, NullPlayerIdException, NullPlayerBookshelfException, NullPlayerScoreException,
        NullPlayerPrivateCardException, NullPlayerScoreBlocksException, DuplicatePlayerNameException,
        AlreadyInitiatedPatternException, NullPlayerNamesException {
      players = new ArrayList<String>();
      p = PlayerFactory.getNewPlayer("myNewPlayer", players);
    }
    
    @Test
    public void setPrivateCard_should_set_PrivateCard() throws NullPlayerPrivateCardException, AlreadyInitiatedPatternException{
      PrivateCard dummyPrivateCard = new PrivateCard();
      p.setPrivateCard(dummyPrivateCard);
      assertNotNull(p.getPrivateCard());
      assertEquals(dummyPrivateCard, p.getPrivateCard());
    }

    @Test
    public void setPrivateCard_should_throw_NullPlayerPrivateCardException() throws NullPlayerPrivateCardException {
      PrivateCard dummyPrivateCard = null;
      assertThrows(NullPlayerPrivateCardException.class, () -> p.setPrivateCard(dummyPrivateCard));
    }

  }

    @Nested
    class setScoreBlocks_tests{

        ArrayList<String> players;
        Player p;

        @BeforeEach
        void setScoreBlocks_tests_setup()
            throws NullPlayerNameException, NullPlayerIdException, NullPlayerBookshelfException, NullPlayerScoreException,
            NullPlayerPrivateCardException, NullPlayerScoreBlocksException, DuplicatePlayerNameException,
            AlreadyInitiatedPatternException, NullPlayerNamesException {
            players = new ArrayList<String>();
            p = PlayerFactory.getNewPlayer("myNewPlayer", players);
        }
        
        @Test
        public void setScoreBlocks_should_set_ScoreBlocks() throws NullPlayerScoreBlocksException, NotValidScoreBlockValueException{
            ScoreBlock dummyScoreBlock1 = new ScoreBlock(4);
            ScoreBlock dummyScoreBlock2 = new ScoreBlock(6);
            List<ScoreBlock> scoreBlocks = Arrays.asList(dummyScoreBlock1,dummyScoreBlock2);
            p.setScoreBlocks(scoreBlocks);
            assertNotNull(p.getScoreBlocks());
            assertNotNull(p.getScoreBlocks().get(0));
            assertNotNull(p.getScoreBlocks().get(1));
            assertEquals(dummyScoreBlock1, p.getScoreBlocks().get(0));
            assertEquals(dummyScoreBlock2, p.getScoreBlocks().get(1));
        }
    
        @Test
        public void setScoreBlocks_should_throw_NullPlayerScoreBlocksException() throws NullPlayerScoreBlocksException {
            List<ScoreBlock> dummyScoreBlocks = null;
            assertThrows(NullPlayerScoreBlocksException.class, () -> p.setScoreBlocks(dummyScoreBlocks));
        }

    }
    
    /**
     * Test to check that the updateScore() method 
     * actually updates all scores.
     * 
     * @throws NullPlayerNameException
     * @throws NullPlayerIdException
     * @throws NullPlayerBookshelfException
     * @throws NullPlayerScoreException
     * @throws NullPlayerPrivateCardException
     * @throws NullPlayerScoreBlocksException
     * @throws DuplicatePlayerNameException
     * @throws AlreadyInitiatedPatternException
     * @throws NullPlayerNamesException
     * @throws NullPointerException
     * @throws WrongLengthBookshelfStringException
     * @throws WrongCharBookshelfStringException
     * @throws WrongTileTypeException
     * @throws NotValidScoreBlockValueException
     * @throws NullMatchedBlockCountException
     * @throws NegativeMatchedBlockCountException
     * @throws BookshelfGridColIndexOutOfBoundsException
     * @throws BookshelfGridRowIndexOutOfBoundsException
     * @throws NullIndexValueException
     * @throws NullScoreBlockListException
     */
    @Test
    public void updateScore_should_update_score()
        throws NullPlayerNameException, NullPlayerIdException, NullPlayerBookshelfException, NullPlayerScoreException, NullPlayerPrivateCardException,
        NullPlayerScoreBlocksException, DuplicatePlayerNameException, AlreadyInitiatedPatternException, NullPlayerNamesException, NullPointerException,
        WrongLengthBookshelfStringException, WrongCharBookshelfStringException, WrongTileTypeException, NotValidScoreBlockValueException,
        NullMatchedBlockCountException, NegativeMatchedBlockCountException, BookshelfGridColIndexOutOfBoundsException,
        BookshelfGridRowIndexOutOfBoundsException, NullIndexValueException, NullScoreBlockListException{
    
    // Desired matched blocks count in private card
    final Integer SIX = 6;
    // It comes from 18 bookshelf points + 6 scoreblocks + 12 private card points + 1 extra point for ending the game first.
    final Integer THIRTHYSEVEN = 37;
    Player p = PlayerFactory.getNewPlayer("myNewPlayer", new ArrayList<String>());

    p.setBookshelf(new Bookshelf(
        "PPPXX" +
        "BPPCX" +
        "FBFBX" +
        "TGTGX" +
        "TTCCC" + 
        "TTTCC"
    ));

    p.setScoreBlocks(List.of(
        new ScoreBlock(2),
        new ScoreBlock(4)
    ));

    PrivateCard pc = new PrivateCard();
    pc.setMatchedBlocksCount(SIX);
    p.setPrivateCard(pc);

    p.getScore().setExtraPoint();
    
    p.updateScore();
    assertEquals(THIRTHYSEVEN, p.getScore().getTotalScore());
  }
}
