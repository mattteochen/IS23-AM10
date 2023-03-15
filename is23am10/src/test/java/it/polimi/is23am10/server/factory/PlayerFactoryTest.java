package it.polimi.is23am10.server.factory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import it.polimi.is23am10.server.factory.PlayerFactory;
import it.polimi.is23am10.server.factory.exceptions.DuplicatePlayerNameException;
import it.polimi.is23am10.server.factory.exceptions.NullPlayerNamesException;
import it.polimi.is23am10.server.items.card.exceptions.AlreadyInitiatedPatternException;
import it.polimi.is23am10.server.player.Player;
import it.polimi.is23am10.server.player.exceptions.NullPlayerBookshelfException;
import it.polimi.is23am10.server.player.exceptions.NullPlayerIdException;
import it.polimi.is23am10.server.player.exceptions.NullPlayerNameException;
import it.polimi.is23am10.server.player.exceptions.NullPlayerPrivateCardException;
import it.polimi.is23am10.server.player.exceptions.NullPlayerScoreBlocksException;
import it.polimi.is23am10.server.player.exceptions.NullPlayerScoreException;

/**
 * Test class to check Player Factory work
 * and exceptions thrown
 */
public class PlayerFactoryTest {
    
    @Test
    public void getNewPlayer_should_return_player() 
    throws NullPlayerNameException, NullPlayerIdException, NullPlayerBookshelfException, NullPlayerScoreException,
            NullPlayerPrivateCardException, NullPlayerScoreBlocksException, DuplicatePlayerNameException,
            AlreadyInitiatedPatternException, NullPlayerNamesException{
        ArrayList<String> players = new ArrayList<String>();
        Player p = PlayerFactory.getNewPlayer("myNewPlayer", players);

        assertEquals("myNewPlayer", p.getPlayerName());
        assertEquals(UUID.nameUUIDFromBytes("myNewPlayer".getBytes()), p.getPlayerID());
        assertNotNull(p.getBookshelf());
        assertNotNull(p.getPrivateCard());
        assertNotNull(p.getScore());
        assertNotNull(p.getScoreBlocks());
    }
    
    @Test
    public void getNewPlayer_should_throw_DuplicatePlayerNameException()
    throws NullPlayerNameException, NullPlayerIdException, NullPlayerBookshelfException, NullPlayerScoreException,
        NullPlayerPrivateCardException, NullPlayerScoreBlocksException, DuplicatePlayerNameException,
        AlreadyInitiatedPatternException, NullPlayerNamesException{
        ArrayList<String> players = new ArrayList<String>();
        players.add("myNewPlayer");
        assertThrows(DuplicatePlayerNameException.class, () -> PlayerFactory.getNewPlayer("myNewPlayer", players));
    }
    
    @Test
    public void getNewPlayer_should_throw_NullPlayerNamesException()
    throws NullPlayerNameException, NullPlayerIdException, NullPlayerBookshelfException, NullPlayerScoreException,
        NullPlayerPrivateCardException, NullPlayerScoreBlocksException, DuplicatePlayerNameException,
        AlreadyInitiatedPatternException, NullPlayerNamesException{
        assertThrows(NullPlayerNamesException.class, () -> PlayerFactory.getNewPlayer("myPlayerName", null));
    }
}
