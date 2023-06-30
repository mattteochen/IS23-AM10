package it.polimi.is23am10.server.model.game;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import it.polimi.is23am10.server.controller.exceptions.NullGameHandlerInstance;
import it.polimi.is23am10.server.model.factory.GameFactory;
import it.polimi.is23am10.server.model.factory.PlayerFactory;
import it.polimi.is23am10.server.model.factory.exceptions.DuplicatePlayerNameException;
import it.polimi.is23am10.server.model.factory.exceptions.NullPlayerNamesException;
import it.polimi.is23am10.server.model.game.Game.GameStatus;
import it.polimi.is23am10.server.model.game.exceptions.FullGameException;
import it.polimi.is23am10.server.model.game.exceptions.InvalidBoardTileSelectionException;
import it.polimi.is23am10.server.model.game.exceptions.InvalidMaxPlayerException;
import it.polimi.is23am10.server.model.game.exceptions.InvalidPlayersNumberException;
import it.polimi.is23am10.server.model.game.exceptions.NullAssignedPatternException;
import it.polimi.is23am10.server.model.game.exceptions.NullMaxPlayerException;
import it.polimi.is23am10.server.model.game.exceptions.NullPlayerException;
import it.polimi.is23am10.server.model.game.exceptions.PlayerNotFoundException;
import it.polimi.is23am10.server.model.items.board.Board;
import it.polimi.is23am10.server.model.items.board.exceptions.BoardGridColIndexOutOfBoundsException;
import it.polimi.is23am10.server.model.items.board.exceptions.BoardGridRowIndexOutOfBoundsException;
import it.polimi.is23am10.server.model.items.board.exceptions.InvalidNumOfPlayersException;
import it.polimi.is23am10.server.model.items.board.exceptions.NullNumOfPlayersException;
import it.polimi.is23am10.server.model.items.bookshelf.Bookshelf;
import it.polimi.is23am10.server.model.items.bookshelf.exceptions.BookshelfGridColIndexOutOfBoundsException;
import it.polimi.is23am10.server.model.items.bookshelf.exceptions.BookshelfGridRowIndexOutOfBoundsException;
import it.polimi.is23am10.server.model.items.bookshelf.exceptions.NullTileException;
import it.polimi.is23am10.server.model.items.bookshelf.exceptions.WrongCharBookshelfStringException;
import it.polimi.is23am10.server.model.items.bookshelf.exceptions.WrongLengthBookshelfStringException;
import it.polimi.is23am10.server.model.items.card.exceptions.AlreadyInitiatedPatternException;
import it.polimi.is23am10.server.model.items.card.exceptions.NegativeMatchedBlockCountException;
import it.polimi.is23am10.server.model.items.card.exceptions.NullMatchedBlockCountException;
import it.polimi.is23am10.server.model.items.card.exceptions.NullScoreBlockListException;
import it.polimi.is23am10.server.model.items.scoreblock.exceptions.NotValidScoreBlockValueException;
import it.polimi.is23am10.server.model.items.tile.Tile;
import it.polimi.is23am10.server.model.items.tile.Tile.TileType;
import it.polimi.is23am10.server.model.player.Player;
import it.polimi.is23am10.server.model.player.exceptions.NullPlayerBookshelfException;
import it.polimi.is23am10.server.model.player.exceptions.NullPlayerIdException;
import it.polimi.is23am10.server.model.player.exceptions.NullPlayerNameException;
import it.polimi.is23am10.server.model.player.exceptions.NullPlayerPrivateCardException;
import it.polimi.is23am10.server.model.player.exceptions.NullPlayerScoreBlocksException;
import it.polimi.is23am10.server.model.player.exceptions.NullPlayerScoreException;
import it.polimi.is23am10.utils.Coordinates;
import it.polimi.is23am10.utils.exceptions.NullIndexValueException;
import it.polimi.is23am10.utils.exceptions.WrongBookShelfPicksException;
import it.polimi.is23am10.utils.exceptions.WrongGameBoardPicksException;
import it.polimi.is23am10.utils.exceptions.WrongMovesNumberException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/** Class of tests for Game. */
@SuppressWarnings({
  "checkstyle:methodname",
  "checkstyle:abbreviationaswordinnamecheck",
  "checkstyle:linelengthcheck",
  "checkstyle:onetoplevelclasscheck",
  "checkstyle:variabledeclarationusagedistancecheck",
  "checkstyle:operatorwrapcheck",
  "checkstyle:multiplevariabledeclarationscheck",
  "checkstyle:membernamecheck"
})
public class GameTest {

  @Test
  public void constructor_should_create_Game() {
    Game g = new Game();
    assertNotNull(g);
    assertNotNull(g.getGameId());
  }

  @Test
  public void addNullSharedPattern_should_throw_NullAssignedPatternException() {
    Game game = new Game();
    assertThrows(NullAssignedPatternException.class, () -> game.addAssignedSharedPattern(null));
  }

  @Test
  public void addNullPrivatePattern_should_throw_NullAssignedPatternException() {
    Game game = new Game();
    assertThrows(NullAssignedPatternException.class, () -> game.addAssignedPrivatePattern(null));
  }

  @Test
  public void addPlayer_should_throw_FullGameException_if_game_is_full()
      throws NullPlayerNameException,
          NullPlayerIdException,
          NullPlayerBookshelfException,
          NullPlayerScoreException,
          NullPlayerPrivateCardException,
          NullPlayerScoreBlocksException,
          DuplicatePlayerNameException,
          AlreadyInitiatedPatternException,
          NullPlayerNamesException,
          PlayerNotFoundException,
          NullMaxPlayerException,
          InvalidMaxPlayerException,
          InvalidNumOfPlayersException,
          NullNumOfPlayersException,
          NullAssignedPatternException,
          FullGameException,
          NotValidScoreBlockValueException {
    Game game = GameFactory.getNewGame("Optimus", 3);
    game.addPlayer("Morrison");
    game.addPlayer("Hendrix");
    assertThrows(FullGameException.class, () -> game.addPlayer("Jason"));
    assertFalse(game.getPlayerNames().contains("Jason"));
  }

  @Test
  public void setFirstPlayer_should_set_first_player()
      throws NullPlayerNameException,
          NullPlayerIdException,
          NullPlayerBookshelfException,
          NullPlayerScoreException,
          NullPlayerPrivateCardException,
          NullPlayerScoreBlocksException,
          DuplicatePlayerNameException,
          AlreadyInitiatedPatternException,
          NullPlayerNamesException,
          PlayerNotFoundException,
          NullMaxPlayerException,
          InvalidMaxPlayerException,
          InvalidNumOfPlayersException,
          NullNumOfPlayersException,
          NullAssignedPatternException,
          FullGameException,
          NotValidScoreBlockValueException {

    Game g = GameFactory.getNewGame("dummyPlayer", 4);
    Player dummyPlayer = g.getPlayerByName("dummyPlayer");
    g.setFirstPlayer(dummyPlayer);

    assertNotNull(g.getFirstPlayer());
    assertEquals(g.getFirstPlayer(), dummyPlayer);
  }

  @Test
  public void getPlayerByName_should_return_right_player()
      throws NullPlayerNameException,
          NullPlayerIdException,
          NullPlayerBookshelfException,
          NullPlayerScoreException,
          NullPlayerPrivateCardException,
          NullPlayerScoreBlocksException,
          DuplicatePlayerNameException,
          AlreadyInitiatedPatternException,
          NullPlayerNamesException,
          PlayerNotFoundException,
          NullAssignedPatternException,
          FullGameException {
    Game g = new Game();
    final String playerName = "dummyPlayer";
    g.addPlayer(playerName);

    assertNotNull(g.getPlayerByName(playerName));
    assertEquals(g.getPlayerByName(playerName).getPlayerName(), playerName);
  }

  @Nested
  class NextTurnTests {

    Game g;
    Player p1, p2, p3;

    @BeforeEach
    void setNextTurn_tests_setup()
        throws NullPlayerNameException,
            NullPlayerIdException,
            NullPlayerBookshelfException,
            NullPlayerScoreException,
            NullPlayerPrivateCardException,
            NullPlayerScoreBlocksException,
            DuplicatePlayerNameException,
            AlreadyInitiatedPatternException,
            NullPlayerNamesException,
            NullMaxPlayerException,
            InvalidMaxPlayerException,
            InvalidNumOfPlayersException,
            NullNumOfPlayersException,
            NullPointerException,
            PlayerNotFoundException,
            NullPlayerException,
            InvalidPlayersNumberException,
            NullAssignedPatternException,
            FullGameException,
            NotValidScoreBlockValueException {

      final Integer dummyPlayerNum = 3;

      g = GameFactory.getNewGame("player1", dummyPlayerNum);
      p1 = g.getPlayerByName("player1");
      p2 = PlayerFactory.getNewPlayer("player2", g);
      p3 = PlayerFactory.getNewPlayer("player3", g);

      // Using addPlayers() method I can force the play order
      g.addPlayers(List.of(p2, p3));
      g.setFirstPlayer(p2);
      g.setActivePlayer(p2);
    }

    @Test
    public void nextTurn_should_change_active_player_to_next_player()
        throws NullPointerException,
            BookshelfGridColIndexOutOfBoundsException,
            BookshelfGridRowIndexOutOfBoundsException,
            NullIndexValueException,
            NullPlayerBookshelfException,
            NullScoreBlockListException,
            NullPlayerNameException,
            PlayerNotFoundException,
            NullPlayerException,
            NullMatchedBlockCountException,
            NegativeMatchedBlockCountException,
            NullGameHandlerInstance {
      assertEquals(g.getActivePlayer(), p2);
      g.nextTurn();
      assertEquals(g.getActivePlayer(), p3);
      g.nextTurn();
      assertEquals(g.getActivePlayer(), p1);
      g.nextTurn();
      assertEquals(g.getActivePlayer(), p2);
    }

    @Test
    public void nextTurn_should_skip_inactive_player()
        throws NullPointerException,
            BookshelfGridColIndexOutOfBoundsException,
            BookshelfGridRowIndexOutOfBoundsException,
            NullIndexValueException,
            NullPlayerBookshelfException,
            NullScoreBlockListException,
            NullPlayerNameException,
            PlayerNotFoundException,
            NullPlayerException,
            NullMatchedBlockCountException,
            NegativeMatchedBlockCountException,
            NullGameHandlerInstance {
      p3.setIsConnected(false);
      assertEquals(g.getActivePlayer(), p2);
      g.nextTurn();
      assertEquals(g.getActivePlayer(), p1);
      g.nextTurn();
      assertEquals(g.getActivePlayer(), p2);
      g.nextTurn();
      assertEquals(g.getActivePlayer(), p1);
    }

    @Test
    public void nextTurn_should_end_game_if_last_lap_finishes()
        throws NullPlayerNameException,
            BookshelfGridColIndexOutOfBoundsException,
            BookshelfGridRowIndexOutOfBoundsException,
            NullIndexValueException,
            NullPlayerBookshelfException,
            NullScoreBlockListException,
            NullPlayerException,
            PlayerNotFoundException,
            NullPointerException,
            NullMatchedBlockCountException,
            NegativeMatchedBlockCountException,
            NullGameHandlerInstance {

      g.setActivePlayer(p1);
      g.setWinnerPlayer(p1);
      g.setStatus(GameStatus.LAST_ROUND);
      assertFalse(g.getStatus() == GameStatus.ENDED);
      g.nextTurn();
      assertTrue(g.getStatus() == GameStatus.ENDED);
    }

    @Test
    public void nextTurn_should_end_game_if_one_player_remains()
        throws NullPlayerNameException,
            BookshelfGridColIndexOutOfBoundsException,
            BookshelfGridRowIndexOutOfBoundsException,
            NullIndexValueException,
            NullPlayerBookshelfException,
            NullScoreBlockListException,
            NullPlayerException,
            PlayerNotFoundException,
            NullPointerException,
            NullMatchedBlockCountException,
            NegativeMatchedBlockCountException,
            NullGameHandlerInstance {

      g.setActivePlayer(p1);
      g.setWinnerPlayer(p1);
      assertFalse(g.getStatus() == GameStatus.ENDED);
      p2.setIsConnected(false);
      p3.setIsConnected(false);
      g.nextTurn();
      assertTrue(g.getStatus() == GameStatus.ENDED);
    }

    @Test
    public void nextTurn_should_refill_board_if_needed()
        throws BoardGridColIndexOutOfBoundsException,
            BoardGridRowIndexOutOfBoundsException,
            NullIndexValueException,
            NullPointerException,
            BookshelfGridColIndexOutOfBoundsException,
            BookshelfGridRowIndexOutOfBoundsException,
            NullPlayerBookshelfException,
            NullScoreBlockListException,
            NullPlayerException,
            NullMatchedBlockCountException,
            NegativeMatchedBlockCountException,
            NullGameHandlerInstance {
      // removing tiles as long as the board needs to be refilled
      for (int row = 0; row < Board.BOARD_GRID_ROWS; row++) {
        for (int col = 0; col < Board.BOARD_GRID_COLS; col++) {
          if ((row + col) % 2 == 0) {
            g.getGameBoard().removeTileAt(row, col);
          }
        }
      }
      assertTrue(g.getGameBoard().isRefillNeeded());
      g.nextTurn();
      assertFalse(g.getGameBoard().isRefillNeeded());
    }
  }

  @Test
  public void putTileAction_should_put_tile_in_active_player_library()
      throws NullMaxPlayerException,
          InvalidMaxPlayerException,
          NullPlayerNameException,
          NullPlayerIdException,
          NullPlayerBookshelfException,
          NullPlayerScoreException,
          NullPlayerPrivateCardException,
          NullPlayerScoreBlocksException,
          DuplicatePlayerNameException,
          AlreadyInitiatedPatternException,
          NullPlayerNamesException,
          InvalidNumOfPlayersException,
          NullNumOfPlayersException,
          BoardGridRowIndexOutOfBoundsException,
          BoardGridColIndexOutOfBoundsException,
          NullIndexValueException,
          BookshelfGridColIndexOutOfBoundsException,
          BookshelfGridRowIndexOutOfBoundsException,
          NullTileException,
          NullPointerException,
          PlayerNotFoundException,
          NullAssignedPatternException,
          FullGameException,
          NotValidScoreBlockValueException {
    final Integer row = 2;
    final Integer col = 4;
    final Integer numMaxPlayers = 3;
    final Tile t = new Tile(TileType.CAT);
    final Coordinates coord = new Coordinates(row, col);
    Game g = GameFactory.getNewGame("dummyPlayer", numMaxPlayers);
    g.setActivePlayer(g.getPlayerByName("dummyPlayer"));
    g.putTileAction(t, coord);
    assertEquals(g.getActivePlayer().getBookshelf().getBookshelfGridAt(row, col), t);
  }

  @Nested
  class ActivePlayerMoveTests {

    Game g;
    Player p1, p2, p3;

    @BeforeEach
    void activePlayerMove_tests_setup()
        throws NullPlayerNameException,
            NullPlayerIdException,
            NullPlayerBookshelfException,
            NullPlayerScoreException,
            NullPlayerPrivateCardException,
            NullPlayerScoreBlocksException,
            DuplicatePlayerNameException,
            AlreadyInitiatedPatternException,
            NullPlayerNamesException,
            NullMaxPlayerException,
            InvalidMaxPlayerException,
            InvalidNumOfPlayersException,
            NullNumOfPlayersException,
            NullPointerException,
            PlayerNotFoundException,
            NullPlayerException,
            InvalidPlayersNumberException,
            NullAssignedPatternException,
            FullGameException,
            NotValidScoreBlockValueException {

      Integer dummyPlayerNum = 3;
      g = GameFactory.getNewGame("player1", dummyPlayerNum);

      p1 = g.getPlayerByName("player1");
      p2 = PlayerFactory.getNewPlayer("player2", g);
      p3 = PlayerFactory.getNewPlayer("player3", g);

      // Using addPlayers() method I can force the play order
      g.addPlayers(List.of(p2, p3));
      g.setFirstPlayer(p3);
      g.setActivePlayer(p2);
    }

    @Test
    public void activePlayerMove_should_move_tiles_correctly_from_board_to_bs()
        throws BoardGridColIndexOutOfBoundsException,
            BoardGridRowIndexOutOfBoundsException,
            BookshelfGridColIndexOutOfBoundsException,
            BookshelfGridRowIndexOutOfBoundsException,
            NullPointerException,
            InvalidBoardTileSelectionException,
            NullIndexValueException,
            NullTileException,
            NullPlayerBookshelfException,
            NullScoreBlockListException,
            NullPlayerNameException,
            PlayerNotFoundException,
            NullPlayerException,
            NullMatchedBlockCountException,
            NegativeMatchedBlockCountException,
            WrongMovesNumberException,
            WrongGameBoardPicksException,
            WrongBookShelfPicksException,
            NullGameHandlerInstance {
      final Integer boardRow = 0;
      final Integer boardCol = 3;
      final Integer bsRow = 5;
      final Integer bsCol = 0;
      final Coordinates boardCoord = new Coordinates(boardRow, boardCol);
      final Coordinates bsCoord = new Coordinates(bsRow, bsCol);
      final Tile t = g.getGameBoard().getTileAt(boardRow, boardCol);
      Map<Coordinates, Coordinates> coordMap = new HashMap<>();

      assertNotNull(g.getGameBoard().getTileAt(boardRow, boardCol));

      coordMap.put(boardCoord, bsCoord);
      g.activePlayerMove(coordMap);

      assertEquals(TileType.EMPTY, g.getGameBoard().getTileAt(boardRow, boardCol).getType());
      /*
       * I'm not checking the active player because activePlayerMove calls nextTurn,
       * so the active player changes
       */
      assertEquals(t.getType(), p2.getBookshelf().getBookshelfGridAt(bsRow, bsCol).getType());
    }

    @Test
    public void checkWin_should_set_winner()
        throws NullPlayerBookshelfException,
            NullPointerException,
            WrongLengthBookshelfStringException,
            WrongCharBookshelfStringException,
            NullPlayerException {

      Bookshelf fullBookshelf =
          new Bookshelf("CCCCC" + "CCCCC" + "PPPPP" + "PPPPP" + "CCCCC" + "TTTTT");
      g.getActivePlayer().setBookshelf(fullBookshelf);
      g.checkEndGame();
      ;

      // is ending immediately because winner is also first player of the game
      assertTrue(g.getStatus() == GameStatus.ENDED);
      assertEquals(g.getWinnerPlayer(), g.getActivePlayer());
    }

    @Test
    public void checkWin_should_not_set_winner()
        throws NullPlayerBookshelfException,
            NullPointerException,
            WrongLengthBookshelfStringException,
            WrongCharBookshelfStringException,
            NullPlayerException {

      Bookshelf fullBookshelf =
          new Bookshelf("XXCXX" + "CCCCC" + "PPPPP" + "PPPPP" + "CCCCC" + "TTTTT");
      g.getActivePlayer().setBookshelf(fullBookshelf);
      g.checkEndGame();

      assertFalse(g.getStatus() == GameStatus.ENDED);
      assertNull(g.getWinnerPlayer());
    }

    @Test
    public void checkWin_should_give_extra_point_only_to_winner()
        throws NullPlayerBookshelfException,
            NullPointerException,
            WrongLengthBookshelfStringException,
            WrongCharBookshelfStringException,
            NullPlayerException,
            BookshelfGridColIndexOutOfBoundsException,
            BookshelfGridRowIndexOutOfBoundsException,
            NullIndexValueException,
            NullScoreBlockListException,
            NullMatchedBlockCountException,
            NegativeMatchedBlockCountException {
      g.setActivePlayer(p1);
      Bookshelf fullBookshelf =
          new Bookshelf("CCCCC" + "CCCCC" + "PPPPP" + "PPPPP" + "CCCCC" + "TTTTT");
      g.getActivePlayer().setBookshelf(fullBookshelf);
      g.checkEndGame();
      assertTrue(g.getStatus() == GameStatus.LAST_ROUND);

      g.getActivePlayer().setBookshelf(fullBookshelf);
      g.nextTurn();
      assertEquals(1, p1.getScore().getExtraPoint());
      assertEquals(0, p2.getScore().getExtraPoint());
    }
  }
}
