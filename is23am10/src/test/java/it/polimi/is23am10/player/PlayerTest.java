package it.polimi.is23am10.player;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;

import it.polimi.is23am10.factory.Exceptions.DuplicatePlayerNameException;
import it.polimi.is23am10.factory.Exceptions.NullPlayerNamesException;
import it.polimi.is23am10.items.card.PrivateCard;
import it.polimi.is23am10.items.card.exceptions.AlreadyInitiatedPatternException;
import it.polimi.is23am10.items.library.Library;
import it.polimi.is23am10.items.scoreblock.ScoreBlock;
import it.polimi.is23am10.items.scoreblock.exceptions.NotValidScoreBlockValueException;
import it.polimi.is23am10.player.Exceptions.NullPlayerIdException;
import it.polimi.is23am10.player.Exceptions.NullPlayerLibraryException;
import it.polimi.is23am10.player.Exceptions.NullPlayerNameException;
import it.polimi.is23am10.player.Exceptions.NullPlayerPrivateCardException;
import it.polimi.is23am10.player.Exceptions.NullPlayerScoreBlocksException;
import it.polimi.is23am10.player.Exceptions.NullPlayerScoreException;
import it.polimi.is23am10.score.Score;
import it.polimi.is23am10.factory.PlayerFactory;

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
            throws NullPlayerNameException, NullPlayerIdException, NullPlayerLibraryException, NullPlayerScoreException,
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
            throws NullPlayerNameException, NullPlayerIdException, NullPlayerLibraryException, NullPlayerScoreException,
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
    class setLibrary_tests{

        ArrayList<String> players;
        Player p;

        @BeforeEach
        void setLibrary_tests_setup()
            throws NullPlayerNameException, NullPlayerIdException, NullPlayerLibraryException, NullPlayerScoreException,
            NullPlayerPrivateCardException, NullPlayerScoreBlocksException, DuplicatePlayerNameException,
            AlreadyInitiatedPatternException, NullPlayerNamesException {
            players = new ArrayList<String>();
            p = PlayerFactory.getNewPlayer("myNewPlayer", players);
        }
        
        @Test
        public void setLibrary_should_set_Library() throws NullPlayerLibraryException{
            Library dummyLibrary = new Library();
            p.setLibrary(dummyLibrary);
            assertNotNull(p.getLibrary());
            assertEquals(dummyLibrary, p.getLibrary());
        }
    
        @Test
        public void setLibrary_should_throw_NullPlayerLibraryException() throws NullPlayerLibraryException {
            Library dummyLibrary = null;
            assertThrows(NullPlayerLibraryException.class, () -> p.setLibrary(dummyLibrary));
        }

    }
    
    @Nested
    class setPrivateCard_tests{

        ArrayList<String> players;
        Player p;

        @BeforeEach
        void setPrivateCard_tests_setup()
            throws NullPlayerNameException, NullPlayerIdException, NullPlayerLibraryException, NullPlayerScoreException,
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
            throws NullPlayerNameException, NullPlayerIdException, NullPlayerLibraryException, NullPlayerScoreException,
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
    
}
