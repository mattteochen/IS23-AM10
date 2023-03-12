package it.polimi.is23am10.factory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import it.polimi.is23am10.factory.exceptions.DuplicatePlayerNameException;
import it.polimi.is23am10.factory.exceptions.NullPlayerNamesException;
import it.polimi.is23am10.game.Game;
import it.polimi.is23am10.game.exceptions.InvalidMaxPlayerException;
import it.polimi.is23am10.game.exceptions.NullMaxPlayerException;
import it.polimi.is23am10.items.board.exceptions.InvalidNumOfPlayersException;
import it.polimi.is23am10.items.board.exceptions.NullNumOfPlayersException;
import it.polimi.is23am10.items.card.exceptions.AlreadyInitiatedPatternException;
import it.polimi.is23am10.player.Player;
import it.polimi.is23am10.player.exceptions.NullPlayerIdException;
import it.polimi.is23am10.player.exceptions.NullPlayerBookshelfException;
import it.polimi.is23am10.player.exceptions.NullPlayerNameException;
import it.polimi.is23am10.player.exceptions.NullPlayerPrivateCardException;
import it.polimi.is23am10.player.exceptions.NullPlayerScoreBlocksException;
import it.polimi.is23am10.player.exceptions.NullPlayerScoreException;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

/**
 * Test class to check Player Factory work
 * and exceptions thrown
 */
public class GameFactoryTest {

    String dummyPlayerName = "myNewPlayer";

    @Test
    public void getNewGame_should_return_player()
            throws NullMaxPlayerException, InvalidMaxPlayerException, NullPlayerNameException,
            NullPlayerIdException, NullPlayerBookshelfException, NullPlayerScoreException, NullPlayerPrivateCardException,
            NullPlayerScoreBlocksException, DuplicatePlayerNameException, AlreadyInitiatedPatternException,
            NullPlayerNamesException, InvalidNumOfPlayersException, NullNumOfPlayersException {

        Integer dummyPlayerNum = 4;

        Game g = GameFactory.getNewGame(dummyPlayerName, dummyPlayerNum);

        List<String> expectedPLayedNames = Arrays.asList(dummyPlayerName);

        List<String> emptyList = new ArrayList<>();

        List<Player> expectedPlayers = Arrays.asList(PlayerFactory.getNewPlayer(dummyPlayerName, emptyList));

        assertNull(g.getFirstPlayer());
        assertEquals(dummyPlayerNum, g.getMaxPlayer());
        assertNotNull(g.getGameBoard());
        assertNotNull(g.getSharedCard());
        assertEquals(false, g.getEnded());
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
