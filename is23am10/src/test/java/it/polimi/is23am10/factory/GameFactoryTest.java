package it.polimi.is23am10.factory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

import it.polimi.is23am10.factory.Exceptions.DuplicatePlayerNameException;
import it.polimi.is23am10.factory.Exceptions.NullPlayerNamesException;
import it.polimi.is23am10.game.Game;
import it.polimi.is23am10.game.exceptions.InvalidMaxPlayerException;
import it.polimi.is23am10.game.exceptions.NullMaxPlayerException;
import it.polimi.is23am10.items.board.exceptions.InvalidNumOfPlayersException;
import it.polimi.is23am10.items.board.exceptions.NullNumOfPlayersException;
import it.polimi.is23am10.items.card.exceptions.AlreadyInitiatedPatternException;
import it.polimi.is23am10.player.Exceptions.NullPlayerIdException;
import it.polimi.is23am10.player.Exceptions.NullPlayerLibraryException;
import it.polimi.is23am10.player.Exceptions.NullPlayerNameException;
import it.polimi.is23am10.player.Exceptions.NullPlayerPrivateCardException;
import it.polimi.is23am10.player.Exceptions.NullPlayerScoreBlocksException;
import it.polimi.is23am10.player.Exceptions.NullPlayerScoreException;

/**
 * Test class to check Player Factory work
 * and exceptions thrown
 */
public class GameFactoryTest {

    String dummyPlayerName = "myNewPlayer";
    
    @Test
    public void getNewGame_should_return_player() 
    throws NullMaxPlayerException, InvalidMaxPlayerException, NullPlayerNameException,
    NullPlayerIdException, NullPlayerLibraryException, NullPlayerScoreException, NullPlayerPrivateCardException,
    NullPlayerScoreBlocksException, DuplicatePlayerNameException, AlreadyInitiatedPatternException,
    NullPlayerNamesException, InvalidNumOfPlayersException, NullNumOfPlayersException {

        Integer dummyPlayerNum = 4;

        Game g = GameFactory.getNewGame(dummyPlayerName, dummyPlayerNum);

        assertEquals(dummyPlayerName, g.getFirstPlayer().getPlayerName());
        assertEquals(dummyPlayerNum, g.getMaxPlayer());
        assertNotNull(g.getGameBoard());
        assertNotNull(g.getSharedCard());
        assertEquals(false,g.getEnded());
    }
    
    @Test
    public void getNewGame_should_throw_NullMaxPlayerException(){
        assertThrows(NullMaxPlayerException.class, () -> GameFactory.getNewGame(dummyPlayerName, null));
    }
    
    @Test
    public void getNewGame_should_throw_InvalidMaxPlayerException(){
        assertThrows(InvalidMaxPlayerException.class, () -> GameFactory.getNewGame(dummyPlayerName, 7));
    }
}
