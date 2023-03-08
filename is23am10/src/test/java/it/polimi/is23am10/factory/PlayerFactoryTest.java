package it.polimi.is23am10.factory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import it.polimi.is23am10.factory.Exceptions.DuplicatePlayerNameException;
import it.polimi.is23am10.factory.Exceptions.NullPlayerNamesException;
import it.polimi.is23am10.items.card.exceptions.AlreadyInitiatedPatternException;
import it.polimi.is23am10.player.Player;
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
public class PlayerFactoryTest {
    
    @Test
    public void getNewPlayer_should_return_player() 
    throws NullPlayerNameException, NullPlayerIdException, NullPlayerLibraryException, NullPlayerScoreException,
            NullPlayerPrivateCardException, NullPlayerScoreBlocksException, DuplicatePlayerNameException,
            AlreadyInitiatedPatternException, NullPlayerNamesException{
        ArrayList<String> players = new ArrayList<String>();
        Player p = PlayerFactory.getNewPlayer("myNewPlayer", players);

        assertEquals("myNewPlayer", p.getPlayerName());
        assertEquals(UUID.nameUUIDFromBytes("myNewPlayer".getBytes()), p.getPlayerID());
        assertNotNull(p.getLibrary());
        assertNotNull(p.getPrivateCard());
        assertNotNull(p.getScore());
        assertNotNull(p.getScoreBlocks());
    }
    
    @Test
    public void getNewPlayer_should_throw_DuplicatePlayerNameException()
    throws NullPlayerNameException, NullPlayerIdException, NullPlayerLibraryException, NullPlayerScoreException,
        NullPlayerPrivateCardException, NullPlayerScoreBlocksException, DuplicatePlayerNameException,
        AlreadyInitiatedPatternException, NullPlayerNamesException{
        ArrayList<String> players = new ArrayList<String>();
        players.add("myNewPlayer");
        assertThrows(DuplicatePlayerNameException.class, () -> PlayerFactory.getNewPlayer("myNewPlayer", players));
    }
    
    @Test
    public void getNewPlayer_should_throw_NullPlayerNamesException()
    throws NullPlayerNameException, NullPlayerIdException, NullPlayerLibraryException, NullPlayerScoreException,
        NullPlayerPrivateCardException, NullPlayerScoreBlocksException, DuplicatePlayerNameException,
        AlreadyInitiatedPatternException, NullPlayerNamesException{
        assertThrows(NullPlayerNamesException.class, () -> PlayerFactory.getNewPlayer("myPlayerName", null));
    }
}
