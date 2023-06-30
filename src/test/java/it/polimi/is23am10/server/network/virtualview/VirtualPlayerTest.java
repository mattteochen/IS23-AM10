package it.polimi.is23am10.server.network.virtualview;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import it.polimi.is23am10.server.model.factory.PlayerFactory;
import it.polimi.is23am10.server.model.factory.exceptions.DuplicatePlayerNameException;
import it.polimi.is23am10.server.model.game.Game;
import it.polimi.is23am10.server.model.game.exceptions.NullAssignedPatternException;
import it.polimi.is23am10.server.model.items.card.exceptions.AlreadyInitiatedPatternException;
import it.polimi.is23am10.server.model.player.Player;
import it.polimi.is23am10.server.model.player.exceptions.NullPlayerBookshelfException;
import it.polimi.is23am10.server.model.player.exceptions.NullPlayerIdException;
import it.polimi.is23am10.server.model.player.exceptions.NullPlayerNameException;
import it.polimi.is23am10.server.model.player.exceptions.NullPlayerPrivateCardException;
import it.polimi.is23am10.server.model.player.exceptions.NullPlayerScoreBlocksException;
import it.polimi.is23am10.server.model.player.exceptions.NullPlayerScoreException;
import org.junit.jupiter.api.Test;

/** Tests for virtual player class */
public class VirtualPlayerTest {
  @Test
  public void constructor_should_create_VirtualPlayer()
      throws NullPlayerNameException,
          NullPlayerIdException,
          NullPlayerBookshelfException,
          NullPlayerScoreException,
          NullPlayerPrivateCardException,
          NullPlayerScoreBlocksException,
          DuplicatePlayerNameException,
          AlreadyInitiatedPatternException,
          NullAssignedPatternException {

    Player p = PlayerFactory.getNewPlayer("myPlayer", new Game());
    VirtualPlayer vp = new VirtualPlayer(p);

    assertNotNull(vp);
    assertEquals(p.getBookshelf(), vp.getBookshelf());
    assertEquals(p.getPlayerID(), vp.getPlayerId());
    assertEquals(p.getPlayerName(), vp.getPlayerName());
    assertEquals(p.getPrivateCard().getPattern().getIndex(), vp.getPrivateCardIndex());
    assertEquals(p.getScore(), vp.getScore());
    assertEquals(p.getIsConnected(), vp.getIsConnected());
  }
}
