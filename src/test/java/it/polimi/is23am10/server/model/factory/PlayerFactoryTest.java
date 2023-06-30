package it.polimi.is23am10.server.model.factory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import it.polimi.is23am10.server.model.factory.exceptions.DuplicatePlayerNameException;
import it.polimi.is23am10.server.model.factory.exceptions.NullPlayerNamesException;
import it.polimi.is23am10.server.model.game.Game;
import it.polimi.is23am10.server.model.game.exceptions.FullGameException;
import it.polimi.is23am10.server.model.game.exceptions.NullAssignedPatternException;
import it.polimi.is23am10.server.model.items.card.exceptions.AlreadyInitiatedPatternException;
import it.polimi.is23am10.server.model.player.Player;
import it.polimi.is23am10.server.model.player.exceptions.NullPlayerBookshelfException;
import it.polimi.is23am10.server.model.player.exceptions.NullPlayerIdException;
import it.polimi.is23am10.server.model.player.exceptions.NullPlayerNameException;
import it.polimi.is23am10.server.model.player.exceptions.NullPlayerPrivateCardException;
import it.polimi.is23am10.server.model.player.exceptions.NullPlayerScoreBlocksException;
import it.polimi.is23am10.server.model.player.exceptions.NullPlayerScoreException;
import java.util.UUID;
import org.junit.jupiter.api.Test;

/** Test class to check Player Factory work and exceptions thrown. */
@SuppressWarnings({
  "checkstyle:methodname",
  "checkstyle:abbreviationaswordinnamecheck",
  "checkstyle:linelengthcheck",
  "checkstyle:onetoplevelclasscheck",
  "checkstyle:variabledeclarationusagedistancecheck"
})
public class PlayerFactoryTest {

  @Test
  public void getNewPlayer_should_return_player()
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
    Player p = PlayerFactory.getNewPlayer("myNewPlayer", new Game());

    assertEquals("myNewPlayer", p.getPlayerName());
    assertEquals(UUID.nameUUIDFromBytes("myNewPlayer".getBytes()), p.getPlayerID());
    assertNotNull(p.getBookshelf());
    assertNotNull(p.getPrivateCard());
    assertNotNull(p.getScore());
    assertNotNull(p.getScoreBlocks());
    assertTrue(p.getIsConnected());
  }

  @Test
  public void getNewPlayer_should_throw_DuplicatePlayerNameException()
      throws NullPlayerNameException,
          NullPlayerIdException,
          NullPlayerBookshelfException,
          NullPlayerScoreException,
          NullPlayerPrivateCardException,
          NullPlayerScoreBlocksException,
          DuplicatePlayerNameException,
          AlreadyInitiatedPatternException,
          NullPlayerNamesException,
          FullGameException,
          NullAssignedPatternException {
    Game g = new Game();
    g.addPlayer("myNewPlayer");
    assertThrows(
        DuplicatePlayerNameException.class, () -> PlayerFactory.getNewPlayer("myNewPlayer", g));
  }
}
