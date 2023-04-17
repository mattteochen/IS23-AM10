package it.polimi.is23am10.client.userinterface;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import it.polimi.is23am10.client.userinterface.helpers.CLIStrings;
import it.polimi.is23am10.server.model.factory.GameFactory;
import it.polimi.is23am10.server.model.factory.PlayerFactory;
import it.polimi.is23am10.server.model.factory.exceptions.DuplicatePlayerNameException;
import it.polimi.is23am10.server.model.factory.exceptions.NullPlayerNamesException;
import it.polimi.is23am10.server.model.game.Game;
import it.polimi.is23am10.server.model.game.exceptions.FullGameException;
import it.polimi.is23am10.server.model.game.exceptions.InvalidMaxPlayerException;
import it.polimi.is23am10.server.model.game.exceptions.NullAssignedPatternException;
import it.polimi.is23am10.server.model.game.exceptions.NullMaxPlayerException;
import it.polimi.is23am10.server.model.game.exceptions.PlayerNotFoundException;
import it.polimi.is23am10.server.model.items.board.exceptions.InvalidNumOfPlayersException;
import it.polimi.is23am10.server.model.items.board.exceptions.NullNumOfPlayersException;
import it.polimi.is23am10.server.model.items.card.exceptions.AlreadyInitiatedPatternException;
import it.polimi.is23am10.server.model.items.scoreblock.exceptions.NotValidScoreBlockValueException;
import it.polimi.is23am10.server.model.player.Player;
import it.polimi.is23am10.server.model.player.exceptions.NullPlayerBookshelfException;
import it.polimi.is23am10.server.model.player.exceptions.NullPlayerIdException;
import it.polimi.is23am10.server.model.player.exceptions.NullPlayerNameException;
import it.polimi.is23am10.server.model.player.exceptions.NullPlayerPrivateCardException;
import it.polimi.is23am10.server.model.player.exceptions.NullPlayerScoreBlocksException;
import it.polimi.is23am10.server.model.player.exceptions.NullPlayerScoreException;
import it.polimi.is23am10.server.model.score.Score;
import it.polimi.is23am10.server.network.messages.AbstractMessage;
import it.polimi.is23am10.server.network.messages.ChatMessage;
import it.polimi.is23am10.server.network.virtualview.VirtualView;

public class CommandLineInterfaceTest {
  private final PrintStream standardOut = System.out;
  private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
  private static CommandLineInterface cli;

  @BeforeEach
  public void setUp() {
    System.setOut(new PrintStream(outputStreamCaptor));
    cli = new CommandLineInterface();
  }

  @Disabled("Blocked by #115")
  @Test
  void displaySplashScreen_should_display_splash_screen() {
    cli.displaySplashScreen();
    assertEquals(CLIStrings.welcomeString + "\n" + CLIStrings.joinOrCreateString, outputStreamCaptor.toString().trim());
  }

  @Disabled("Blocked by #115")
  @Test
  void displayAvailableGames_should_display_noGames_string() {
    cli.displayAvailableGames(List.of());
    assertEquals(CLIStrings.noGamesString, outputStreamCaptor.toString().trim());
  }

  @Disabled("Blocked by #115")
  @Test
  void displayAvailableGames_should_display_games() throws NullMaxPlayerException, InvalidMaxPlayerException,
      NullPlayerNameException, NullPlayerIdException, NullPlayerBookshelfException, NullPlayerScoreException,
      NullPlayerPrivateCardException, NullPlayerScoreBlocksException, DuplicatePlayerNameException,
      AlreadyInitiatedPatternException, NullPlayerNamesException, InvalidNumOfPlayersException,
      NullNumOfPlayersException, NullAssignedPatternException, FullGameException, NotValidScoreBlockValueException,
      PlayerNotFoundException {

    VirtualView vw1 = new VirtualView(GameFactory.getNewGame("bob", 2));
    VirtualView vw2 = new VirtualView(GameFactory.getNewGame("nicoletta", 3));
    List<VirtualView> games = List.of(
        vw1, vw2);
    cli.displayAvailableGames(games);

    final String expectedString = String.format("%s\n%s\n%s",
        CLIStrings.listGamesString,
        String.format(CLIStrings.availableGameString, 0, vw1.getPlayers().size(), vw1.getMaxPlayers(), vw1.getGameId()),
        String.format(CLIStrings.availableGameString, 1, vw2.getPlayers().size(), vw2.getMaxPlayers(),
            vw2.getGameId()));

    assertEquals(expectedString, outputStreamCaptor.toString().trim());
  }

  @Disabled("Blocked by #115")
  @Test
  void displayVirtualView_should_display_leaderboard()
      throws NullMaxPlayerException, InvalidMaxPlayerException, NullPlayerNameException, NullPlayerIdException,
      NullPlayerBookshelfException, NullPlayerScoreException, NullPlayerPrivateCardException,
      NullPlayerScoreBlocksException, DuplicatePlayerNameException, AlreadyInitiatedPatternException,
      NullPlayerNamesException, InvalidNumOfPlayersException, NullNumOfPlayersException, NullAssignedPatternException,
      FullGameException, NotValidScoreBlockValueException, PlayerNotFoundException {
    final String player1Name = "nicoletta";
    final String player2Name = "giovanni";

    Game g = GameFactory.getNewGame(player1Name, 4);
    g.addPlayer(player2Name);
    Score cheatScore = new Score();
    cheatScore.setExtraPoint();
    Player player2 = g.getPlayerByName(player2Name);
    player2.setScore(cheatScore);
    g.setEnded(true);
    g.setWinnerPlayer(player2);
    VirtualView vw = new VirtualView(g);
    cli.displayVirtualView(vw);

    final String expectedString = String.format("%s\n%s\n%s\n%s\n%s",
        CLIStrings.currentStateString,
        CLIStrings.gameOverString,
        String.format(CLIStrings.playerScoreString, player2Name, 1),
        String.format(CLIStrings.playerScoreString, player1Name, 0),
        String.format(CLIStrings.winnerString, player2Name));

    assertEquals(expectedString, outputStreamCaptor.toString().trim());
  }

  @Disabled("Blocked by #115")
  @Test
  void displayVirtualView_should_display_lastRound() throws NullMaxPlayerException, InvalidMaxPlayerException,
      NullPlayerNameException, NullPlayerIdException, NullPlayerBookshelfException, NullPlayerScoreException,
      NullPlayerPrivateCardException, NullPlayerScoreBlocksException, DuplicatePlayerNameException,
      AlreadyInitiatedPatternException, NullPlayerNamesException, InvalidNumOfPlayersException,
      NullNumOfPlayersException, NullAssignedPatternException, FullGameException, NotValidScoreBlockValueException,
      PlayerNotFoundException {
    Game g = GameFactory.getNewGame("giovanni", 2);
    g.setLastRound();
    VirtualView vw = new VirtualView(g);
    cli.displayVirtualView(vw);
    assertTrue(outputStreamCaptor.toString().contains(CLIStrings.lastRoundString));
  }

  @Disabled("Blocked by #115")
  @Test
  void displayVirtualView_should_display_game_state()
      throws NullMaxPlayerException, InvalidMaxPlayerException, NullPlayerNameException, NullPlayerIdException,
      NullPlayerBookshelfException, NullPlayerScoreException, NullPlayerPrivateCardException,
      NullPlayerScoreBlocksException, DuplicatePlayerNameException, AlreadyInitiatedPatternException,
      NullPlayerNamesException, InvalidNumOfPlayersException, NullNumOfPlayersException, NullAssignedPatternException,
      FullGameException, NotValidScoreBlockValueException, PlayerNotFoundException {
    String playerName = "giovanni";
    Game g = GameFactory.getNewGame(playerName, 2);
    VirtualView vw = new VirtualView(g);
    cli.displayVirtualView(vw);

    final String expectedString = String.format("%s\n%s\n%s\n%s",
        CLIStrings.currentStateString,
        String.format(CLIStrings.nowPlaying, playerName),
        CLIStrings.moveTilesInviteString,
        CLIStrings.moveTilesExampleString);

    assertEquals(expectedString, outputStreamCaptor.toString().trim());
  }

  @Disabled("Blocked by #115")
  @Test
  void displayChatMessage_should_display_message() throws NullPlayerNameException, NullPlayerIdException {
    String textMessage = "valar morghulis";
    String playerName = "Jaqen H'ghar";
    Player p = new Player();
    p.setPlayerID(UUID.nameUUIDFromBytes(playerName.getBytes()));
    p.setPlayerName(playerName);
    AbstractMessage m = new ChatMessage(p, textMessage);
    cli.displayChatMessage(m);

    assertEquals(String.format(CLIStrings.messageString, playerName, textMessage),
        outputStreamCaptor.toString().trim());
  }

  @AfterEach
  public void tearDown() {
    System.setOut(standardOut);
  }
}