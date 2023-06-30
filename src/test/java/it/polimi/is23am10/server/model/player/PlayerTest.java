package it.polimi.is23am10.server.model.player;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import it.polimi.is23am10.server.model.factory.PlayerFactory;
import it.polimi.is23am10.server.model.factory.exceptions.DuplicatePlayerNameException;
import it.polimi.is23am10.server.model.factory.exceptions.NullPlayerNamesException;
import it.polimi.is23am10.server.model.game.Game;
import it.polimi.is23am10.server.model.game.exceptions.NullAssignedPatternException;
import it.polimi.is23am10.server.model.items.bookshelf.Bookshelf;
import it.polimi.is23am10.server.model.items.bookshelf.exceptions.BookshelfGridColIndexOutOfBoundsException;
import it.polimi.is23am10.server.model.items.bookshelf.exceptions.BookshelfGridRowIndexOutOfBoundsException;
import it.polimi.is23am10.server.model.items.bookshelf.exceptions.WrongCharBookshelfStringException;
import it.polimi.is23am10.server.model.items.bookshelf.exceptions.WrongLengthBookshelfStringException;
import it.polimi.is23am10.server.model.items.card.PrivateCard;
import it.polimi.is23am10.server.model.items.card.exceptions.AlreadyInitiatedPatternException;
import it.polimi.is23am10.server.model.items.card.exceptions.NegativeMatchedBlockCountException;
import it.polimi.is23am10.server.model.items.card.exceptions.NullMatchedBlockCountException;
import it.polimi.is23am10.server.model.items.card.exceptions.NullScoreBlockListException;
import it.polimi.is23am10.server.model.items.scoreblock.ScoreBlock;
import it.polimi.is23am10.server.model.items.scoreblock.exceptions.NotValidScoreBlockValueException;
import it.polimi.is23am10.server.model.pattern.PrivatePattern;
import it.polimi.is23am10.server.model.player.exceptions.NullPlayerBookshelfException;
import it.polimi.is23am10.server.model.player.exceptions.NullPlayerIdException;
import it.polimi.is23am10.server.model.player.exceptions.NullPlayerNameException;
import it.polimi.is23am10.server.model.player.exceptions.NullPlayerPrivateCardException;
import it.polimi.is23am10.server.model.player.exceptions.NullPlayerScoreBlocksException;
import it.polimi.is23am10.server.model.player.exceptions.NullPlayerScoreException;
import it.polimi.is23am10.server.model.score.Score;
import it.polimi.is23am10.utils.exceptions.NullIndexValueException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/** Test class to check getters and setters from the Player class. */
@SuppressWarnings({
  "checkstyle:methodname",
  "checkstyle:abbreviationaswordinnamecheck",
  "checkstyle:linelengthcheck",
  "checkstyle:onetoplevelclasscheck",
  "checkstyle:variabledeclarationusagedistancecheck",
  "checkstyle:operatorwrapcheck",
  "checkstyle:multiplevariabledeclarationscheck",
  "checkstyle:membernamecheck",
  "checkstyle:nonemptyatclausedescriptioncheck",
  "checkstyle:typenamecheck"
})
public class PlayerTest {
  @Test
  public void OVERRIDE_should_OVERRIDE()
      throws NullPlayerNameException,
          NullPlayerIdException,
          NullPlayerBookshelfException,
          NullPlayerScoreException,
          NullPlayerPrivateCardException,
          NullPlayerScoreBlocksException,
          DuplicatePlayerNameException,
          AlreadyInitiatedPatternException,
          NullPlayerNamesException,
          NullAssignedPatternException {
    Game game = new Game();
    Player player = PlayerFactory.getNewPlayer("newPlayer", game);
    Player playerBrother = PlayerFactory.getNewPlayer("newPlayer", game);
    Object outsideTheFamily = new Object();
    assertEquals(
        player.hashCode(), player.getPlayerID().hashCode() * player.getPlayerName().hashCode());
    assertEquals(player, playerBrother);
    assertNotEquals(player, outsideTheFamily);
  }

  @Nested
  class setPlayerID_tests {

    ArrayList<String> players;
    Player p;
    Game game;

    @BeforeEach
    void setPlayerID_tests_setup()
        throws NullPlayerNameException,
            NullPlayerIdException,
            NullPlayerBookshelfException,
            NullPlayerScoreException,
            NullPlayerPrivateCardException,
            NullPlayerScoreBlocksException,
            DuplicatePlayerNameException,
            AlreadyInitiatedPatternException,
            NullPlayerNamesException,
            NullAssignedPatternException {
      game = new Game();
      p = PlayerFactory.getNewPlayer("myNewPlayer", game);
    }

    @Test
    public void setPlayerID_should_set_playerID() throws NullPlayerIdException {
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
  class setScore_tests {

    ArrayList<String> players;
    Player p;
    Game game;

    @BeforeEach
    void setScore_tests_setup()
        throws NullPlayerNameException,
            NullPlayerIdException,
            NullPlayerBookshelfException,
            NullPlayerScoreException,
            NullPlayerPrivateCardException,
            NullPlayerScoreBlocksException,
            DuplicatePlayerNameException,
            AlreadyInitiatedPatternException,
            NullPlayerNamesException,
            NullAssignedPatternException {
      game = new Game();
      p = PlayerFactory.getNewPlayer("myNewPlayer", game);
    }

    @Test
    public void setScore_should_set_Score() throws NullPlayerScoreException {
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
  class setBookshelf_tests {

    ArrayList<String> players;
    Player p;
    Game game;

    @BeforeEach
    void setBookshelf_tests_setup()
        throws NullPlayerNameException,
            NullPlayerIdException,
            NullPlayerBookshelfException,
            NullPlayerScoreException,
            NullPlayerPrivateCardException,
            NullPlayerScoreBlocksException,
            DuplicatePlayerNameException,
            AlreadyInitiatedPatternException,
            NullPlayerNamesException,
            NullAssignedPatternException {
      game = new Game();
      players = new ArrayList<String>();
      p = PlayerFactory.getNewPlayer("myNewPlayer", game);
    }

    @Test
    public void setBookshelf_should_set_Bookshelf() throws NullPlayerBookshelfException {
      Bookshelf dummyBookshelf = new Bookshelf();
      p.setBookshelf(dummyBookshelf);
      assertNotNull(p.getBookshelf());
      assertEquals(dummyBookshelf, p.getBookshelf());
    }

    @Test
    public void setBookshelf_should_throw_NullPlayerBookshelfException()
        throws NullPlayerBookshelfException {
      Bookshelf dummyBookshelf = null;
      assertThrows(NullPlayerBookshelfException.class, () -> p.setBookshelf(dummyBookshelf));
    }
  }

  @Nested
  class setPrivateCard_tests {

    ArrayList<String> players;
    Player p;
    Game game;

    @BeforeEach
    void setPrivateCard_tests_setup()
        throws NullPlayerNameException,
            NullPlayerIdException,
            NullPlayerBookshelfException,
            NullPlayerScoreException,
            NullPlayerPrivateCardException,
            NullPlayerScoreBlocksException,
            DuplicatePlayerNameException,
            AlreadyInitiatedPatternException,
            NullPlayerNamesException,
            NullAssignedPatternException {
      game = new Game();
      p = PlayerFactory.getNewPlayer("myNewPlayer", game);
    }

    @Test
    public void setPrivateCard_should_set_PrivateCard()
        throws NullPlayerPrivateCardException, AlreadyInitiatedPatternException {
      List<PrivatePattern<Function<Bookshelf, Integer>>> usedPatterns = new ArrayList<>();
      PrivateCard dummyPrivateCard = new PrivateCard(usedPatterns);
      p.setPrivateCard(dummyPrivateCard);
      assertNotNull(p.getPrivateCard());
      assertEquals(dummyPrivateCard, p.getPrivateCard());
    }

    @Test
    public void setPrivateCard_should_throw_NullPlayerPrivateCardException()
        throws NullPlayerPrivateCardException {
      PrivateCard dummyPrivateCard = null;
      assertThrows(NullPlayerPrivateCardException.class, () -> p.setPrivateCard(dummyPrivateCard));
    }
  }

  @Nested
  class setScoreBlocks_tests {

    ArrayList<String> players;
    Player p;
    Game game;

    @BeforeEach
    void setScoreBlocks_tests_setup()
        throws NullPlayerNameException,
            NullPlayerIdException,
            NullPlayerBookshelfException,
            NullPlayerScoreException,
            NullPlayerPrivateCardException,
            NullPlayerScoreBlocksException,
            DuplicatePlayerNameException,
            AlreadyInitiatedPatternException,
            NullPlayerNamesException,
            NullAssignedPatternException {
      game = new Game();
      p = PlayerFactory.getNewPlayer("myNewPlayer", game);
    }

    @Test
    public void setScoreBlocks_should_set_ScoreBlocks()
        throws NullPlayerScoreBlocksException, NotValidScoreBlockValueException {
      ScoreBlock dummyScoreBlock1 = new ScoreBlock(4);
      ScoreBlock dummyScoreBlock2 = new ScoreBlock(6);
      List<ScoreBlock> scoreBlocks = Arrays.asList(dummyScoreBlock1, dummyScoreBlock2);
      p.setScoreBlocks(scoreBlocks);
      assertNotNull(p.getScoreBlocks());
      assertNotNull(p.getScoreBlocks().get(0));
      assertNotNull(p.getScoreBlocks().get(1));
      assertEquals(dummyScoreBlock1, p.getScoreBlocks().get(0));
      assertEquals(dummyScoreBlock2, p.getScoreBlocks().get(1));
    }

    @Test
    public void setScoreBlocks_should_throw_NullPlayerScoreBlocksException()
        throws NullPlayerScoreBlocksException {
      List<ScoreBlock> dummyScoreBlocks = null;
      assertThrows(NullPlayerScoreBlocksException.class, () -> p.setScoreBlocks(dummyScoreBlocks));
    }
  }

  /**
   * Test to check that the updateScore() method actually updates all scores.
   *
   * @throws NullPlayerNameException If player name is null.
   * @throws NullPlayerIdException If player id is null.
   * @throws NullPlayerBookshelfException If bookshelf is null.
   * @throws NullPlayerScoreException If player's score object is null.
   * @throws NullPlayerPrivateCardException If player's private card object is null.
   * @throws NullPlayerScoreBlocksException If player's scoreblocks list is null.
   * @throws DuplicatePlayerNameException If player with that name already exists.
   * @throws AlreadyInitiatedPatternException If assigning a pattern to a card that already has one.
   * @throws NullPlayerNamesException If, while adding multiple players, the list of player names is
   *     null.
   * @throws NullPointerException Generic NPE.
   * @throws WrongLengthBookshelfStringException If when building a bookshelf based on string, it is
   *     of an invalid length.
   * @throws WrongCharBookshelfStringException If when building a bookshelf based on string, it
   *     contains an invalid character.
   * @throws NotValidScoreBlockValueException If the value assigned to a scoreblock is not valid.
   * @throws NullMatchedBlockCountException If the number of matched blocks to set is null.
   * @throws NegativeMatchedBlockCountException If the number of matched blocks to set is negative.
   * @throws BookshelfGridColIndexOutOfBoundsException If the bookshelf column index is out of
   *     bounds.
   * @throws BookshelfGridRowIndexOutOfBoundsException If the bookshelf row index is out of bounds.
   * @throws NullIndexValueException If the index provided is null.
   * @throws NullScoreBlockListException If the list of scoreblocks is null.
   * @throws NullAssignedPatternException If the pattern assigned to a card is null.
   */
  @Test
  public void updateScore_should_update_score()
      throws NullPlayerNameException,
          NullPlayerIdException,
          NullPlayerBookshelfException,
          NullPlayerScoreException,
          NullPlayerPrivateCardException,
          NullPlayerScoreBlocksException,
          DuplicatePlayerNameException,
          AlreadyInitiatedPatternException,
          NullPlayerNamesException,
          NullPointerException,
          WrongLengthBookshelfStringException,
          WrongCharBookshelfStringException,
          NotValidScoreBlockValueException,
          NullMatchedBlockCountException,
          NegativeMatchedBlockCountException,
          BookshelfGridColIndexOutOfBoundsException,
          BookshelfGridRowIndexOutOfBoundsException,
          NullIndexValueException,
          NullScoreBlockListException,
          NullAssignedPatternException {

    // It comes from 18 bookshelf points + 6 scoreblocks + 1 extra point for ending the game first.
    // Private points not tested here as factory-dependent.
    final Integer BS_POINTS = 18;
    final Integer SB_POINTS = 6;
    final Integer EG_POINTS = 1;
    Player p = PlayerFactory.getNewPlayer("myNewPlayer", new Game());

    p.setBookshelf(new Bookshelf("PPPXX" + "BPPCX" + "FBFBX" + "TGTGX" + "TTCCC" + "TTTCC"));

    p.setScoreBlocks(List.of(new ScoreBlock(2), new ScoreBlock(4)));

    List<PrivatePattern<Function<Bookshelf, Integer>>> usedPatterns = new ArrayList<>();
    PrivateCard pc = new PrivateCard(usedPatterns);
    p.setPrivateCard(pc);

    p.getScore().setExtraPoint();

    p.updateScore();
    assertEquals(BS_POINTS, p.getScore().getBookshelfPoints());
    assertEquals(SB_POINTS, p.getScore().getScoreBlockPoints());
    assertEquals(EG_POINTS, p.getScore().getExtraPoint());
  }

  @Nested
  class setIsConnected_tests {

    ArrayList<String> players;
    Player p;
    Game game;

    @BeforeEach
    void setIsConnected_tests_setup()
        throws NullPlayerNameException,
            NullPlayerIdException,
            NullPlayerBookshelfException,
            NullPlayerScoreException,
            NullPlayerPrivateCardException,
            NullPlayerScoreBlocksException,
            DuplicatePlayerNameException,
            AlreadyInitiatedPatternException,
            NullAssignedPatternException {
      game = new Game();
      p = PlayerFactory.getNewPlayer("myNewPlayer", game);
    }

    @Test
    public void setIsConnected_should_set_isConnected() throws NullPlayerScoreException {
      assertTrue(p.getIsConnected());
      p.setIsConnected(false);
      assertFalse(p.getIsConnected());
    }
  }
}
