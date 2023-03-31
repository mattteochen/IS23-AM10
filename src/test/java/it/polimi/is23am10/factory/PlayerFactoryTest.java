package it.polimi.is23am10.factory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import it.polimi.is23am10.factory.exceptions.DuplicatePlayerNameException;
import it.polimi.is23am10.factory.exceptions.NullPlayerNamesException;
import it.polimi.is23am10.game.Game;
import it.polimi.is23am10.game.exceptions.FullGameException;
import it.polimi.is23am10.game.exceptions.NullAssignedPatternException;
import it.polimi.is23am10.items.card.exceptions.AlreadyInitiatedPatternException;
import it.polimi.is23am10.player.Player;
import it.polimi.is23am10.player.exceptions.NullPlayerBookshelfException;
import it.polimi.is23am10.player.exceptions.NullPlayerIdException;
import it.polimi.is23am10.player.exceptions.NullPlayerNameException;
import it.polimi.is23am10.player.exceptions.NullPlayerPrivateCardException;
import it.polimi.is23am10.player.exceptions.NullPlayerScoreBlocksException;
import it.polimi.is23am10.player.exceptions.NullPlayerScoreException;
import java.util.ArrayList;
import java.util.UUID;
import org.junit.jupiter.api.Test;

/**
 * Test class to check Player Factory work
 * and exceptions thrown.
 */
@SuppressWarnings({ "checkstyle:methodname", "checkstyle:abbreviationaswordinnamecheck", "checkstyle:linelengthcheck",
    "checkstyle:onetoplevelclasscheck", "checkstyle:variabledeclarationusagedistancecheck" })
public class PlayerFactoryTest {

  @Test
  public void getNewPlayer_should_return_player()
      throws NullPlayerNameException, NullPlayerIdException, NullPlayerBookshelfException,
      NullPlayerScoreException,
      NullPlayerPrivateCardException, NullPlayerScoreBlocksException, DuplicatePlayerNameException,
      AlreadyInitiatedPatternException, NullPlayerNamesException, NullAssignedPatternException {
    ArrayList<String> players = new ArrayList<String>();
    Player p = PlayerFactory.getNewPlayer("myNewPlayer", new Game());

    assertEquals("myNewPlayer", p.getPlayerName());
    assertEquals(UUID.nameUUIDFromBytes("myNewPlayer".getBytes()), p.getPlayerID());
    assertNotNull(p.getBookshelf());
    assertNotNull(p.getPrivateCard());
    assertNotNull(p.getScore());
    assertNotNull(p.getScoreBlocks());
  }

  @Test
  public void getNewPlayer_should_throw_DuplicatePlayerNameException()
      throws NullPlayerNameException, NullPlayerIdException, NullPlayerBookshelfException,
      NullPlayerScoreException,
      NullPlayerPrivateCardException, NullPlayerScoreBlocksException, DuplicatePlayerNameException,
      AlreadyInitiatedPatternException, NullPlayerNamesException, FullGameException, NullAssignedPatternException {
    Game g = new Game();
    g.addPlayer("myNewPlayer");
    assertThrows(DuplicatePlayerNameException.class,
        () -> PlayerFactory.getNewPlayer("myNewPlayer", g));
  }
}
