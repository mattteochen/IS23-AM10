package it.polimi.is23am10.server.model.factory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.polimi.is23am10.server.model.factory.GameFactory;
import it.polimi.is23am10.server.model.factory.exceptions.DuplicatePlayerNameException;
import it.polimi.is23am10.server.model.factory.exceptions.NullPlayerNamesException;
import it.polimi.is23am10.server.model.game.Game;
import it.polimi.is23am10.server.model.game.Game.GameStatus;
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

/**
 * Test class to check Player Factory work
 * and exceptions thrown.
 */
@SuppressWarnings({ "checkstyle:methodname", "checkstyle:abbreviationaswordinnamecheck", "checkstyle:linelengthcheck",
    "checkstyle:onetoplevelclasscheck", "checkstyle:variabledeclarationusagedistancecheck" })
public class GameFactoryTest {

  String dummyPlayerName = "myNewPlayer";

  @BeforeEach
  public void clear_used_pattern_list_to_avoid_using_all_patterns_in_tests() {
  }

  @Test
  public void getNewGame_should_return_player()
      throws NullMaxPlayerException, InvalidMaxPlayerException, NullPlayerNameException,
      NullPlayerIdException, NullPlayerBookshelfException, NullPlayerScoreException,
      NullPlayerPrivateCardException,
      NullPlayerScoreBlocksException, DuplicatePlayerNameException, AlreadyInitiatedPatternException,
      NullPlayerNamesException, InvalidNumOfPlayersException, NullNumOfPlayersException, NullAssignedPatternException, 
      FullGameException, PlayerNotFoundException, NotValidScoreBlockValueException {

    Integer dummyPlayerNum = 4;

    Game g = GameFactory.getNewGame(dummyPlayerName, dummyPlayerNum);

    List<String> expectedPLayedNames = Arrays.asList(dummyPlayerName);

    List<Player> expectedPlayers = Arrays.asList(g.getPlayerByName(dummyPlayerName));

    assertNull(g.getFirstPlayer());
    assertEquals(dummyPlayerNum, g.getMaxPlayer());
    assertNotNull(g.getGameBoard());
    assertNotNull(g.getSharedCard());
    assertNotEquals(GameStatus.ENDED, g.getStatus());
    assertEquals(expectedPLayedNames, g.getPlayerNames());
    assertEquals(expectedPlayers.size(), g.getPlayers().size());
  }

  @Test
  public void getNewGame_should_throw_NullMaxPlayerException() {
    assertThrows(NullMaxPlayerException.class, () -> GameFactory.getNewGame(dummyPlayerName, null));
  }

  @Test
  public void getNewGame_should_throw_InvalidMaxPlayerException() {
    assertThrows(InvalidMaxPlayerException.class, () -> GameFactory.getNewGame(dummyPlayerName, 7));
  }
}
