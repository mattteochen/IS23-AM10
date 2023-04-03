package it.polimi.is23am10.virtualview;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import it.polimi.is23am10.factory.GameFactory;
import it.polimi.is23am10.factory.exceptions.DuplicatePlayerNameException;
import it.polimi.is23am10.factory.exceptions.NullPlayerNamesException;
import it.polimi.is23am10.game.Game;
import it.polimi.is23am10.game.exceptions.FullGameException;
import it.polimi.is23am10.game.exceptions.InvalidMaxPlayerException;
import it.polimi.is23am10.game.exceptions.NullAssignedPatternException;
import it.polimi.is23am10.game.exceptions.NullMaxPlayerException;
import it.polimi.is23am10.game.exceptions.PlayerNotFoundException;
import it.polimi.is23am10.items.board.exceptions.InvalidNumOfPlayersException;
import it.polimi.is23am10.items.board.exceptions.NullNumOfPlayersException;
import it.polimi.is23am10.items.card.exceptions.AlreadyInitiatedPatternException;
import it.polimi.is23am10.player.exceptions.NullPlayerBookshelfException;
import it.polimi.is23am10.player.exceptions.NullPlayerIdException;
import it.polimi.is23am10.player.exceptions.NullPlayerNameException;
import it.polimi.is23am10.player.exceptions.NullPlayerPrivateCardException;
import it.polimi.is23am10.player.exceptions.NullPlayerScoreBlocksException;
import it.polimi.is23am10.player.exceptions.NullPlayerScoreException;

/**
 * Tests for virtual view class.
 */
public class VirtualViewTest {
  @Test
  public void constructor_should_create_VirtualView() 
    throws NullMaxPlayerException, InvalidMaxPlayerException, NullPlayerNameException, NullPlayerIdException, 
    NullPlayerBookshelfException, NullPlayerScoreException, NullPlayerPrivateCardException, NullPlayerScoreBlocksException, 
    DuplicatePlayerNameException, AlreadyInitiatedPatternException, NullPlayerNamesException, InvalidNumOfPlayersException, 
    NullNumOfPlayersException, NullAssignedPatternException, FullGameException, PlayerNotFoundException {
    
    Game g = GameFactory.getNewGame("test", 4);
    g.assignPlayers();
    VirtualView vv = new VirtualView(g);

    assertNotNull(vv);
    assertEquals(new VirtualPlayer(g.getActivePlayer()), vv.getActivePlayer());
    assertEquals(g.getEnded(), vv.isEnded());
    assertEquals(g.isLastRound(), vv.isLastRound());
    assertEquals(new VirtualPlayer(g.getFirstPlayer()), vv.getActivePlayer());
    assertEquals(g.getGameBoard(), vv.getGameBoard());
    assertEquals(g.getGameId(), vv.getGameId());
    assertEquals(g.getMaxPlayer(), vv.getMaxPlayers());
    assertEquals(g.getPlayers().stream().map(p -> new VirtualPlayer(p)).collect(Collectors.toList()), vv.getPlayers());
    assertEquals(g.getSharedCard().stream().map(sc -> sc.getPattern().getIndex()).collect(Collectors.toList()), vv.getSharedCardsIndexes());

  }
}
