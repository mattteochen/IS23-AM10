package it.polimi.is23am10.virtualview;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import it.polimi.is23am10.factory.PlayerFactory;
import it.polimi.is23am10.factory.exceptions.DuplicatePlayerNameException;
import it.polimi.is23am10.game.Game;
import it.polimi.is23am10.game.exceptions.NullAssignedPatternException;
import it.polimi.is23am10.items.card.exceptions.AlreadyInitiatedPatternException;
import it.polimi.is23am10.player.Player;
import it.polimi.is23am10.player.exceptions.NullPlayerBookshelfException;
import it.polimi.is23am10.player.exceptions.NullPlayerIdException;
import it.polimi.is23am10.player.exceptions.NullPlayerNameException;
import it.polimi.is23am10.player.exceptions.NullPlayerPrivateCardException;
import it.polimi.is23am10.player.exceptions.NullPlayerScoreBlocksException;
import it.polimi.is23am10.player.exceptions.NullPlayerScoreException;

/**
 * Tests for virtual player class
 */
public class VirtualPlayerTest {
  @Test
  public void constructor_should_create_VirtualPlayer() 
    throws NullPlayerNameException, NullPlayerIdException, NullPlayerBookshelfException, NullPlayerScoreException, 
    NullPlayerPrivateCardException, NullPlayerScoreBlocksException, DuplicatePlayerNameException, AlreadyInitiatedPatternException, 
    NullAssignedPatternException {
    
    Player p = PlayerFactory.getNewPlayer("myPlayer", new Game());
    VirtualPlayer vp = new VirtualPlayer(p);

    assertNotNull(vp);
    assertEquals(p.getBookshelf(), vp.getBookshelf());
    assertEquals(p.getPlayerID(), vp.getPlayerId());
    assertEquals(p.getPlayerName(), vp.getPlayerName());
    assertEquals(p.getPrivateCard().getPattern().getIndex(), vp.getPrivateCardIndex());
    assertEquals(p.getScore(), vp.getScore());
  }

}
