package it.polimi.is23am10.messages;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
import it.polimi.is23am10.messages.GameMessage;
import it.polimi.is23am10.messages.AbstractMessage.MessageType;
import it.polimi.is23am10.player.Player;
import it.polimi.is23am10.player.exceptions.NullPlayerBookshelfException;
import it.polimi.is23am10.player.exceptions.NullPlayerIdException;
import it.polimi.is23am10.player.exceptions.NullPlayerNameException;
import it.polimi.is23am10.player.exceptions.NullPlayerPrivateCardException;
import it.polimi.is23am10.player.exceptions.NullPlayerScoreBlocksException;
import it.polimi.is23am10.player.exceptions.NullPlayerScoreException;
import it.polimi.is23am10.virtualview.VirtualView;

/**
 * Tests for the game message class
 */
public class GameMessageTest {
  @Test
  public void constructor_should_create_GameMessage() 
    throws NullPlayerNameException, NullPlayerIdException, NullPlayerBookshelfException, NullPlayerScoreException,
    NullPlayerPrivateCardException, NullPlayerScoreBlocksException, DuplicatePlayerNameException, 
    AlreadyInitiatedPatternException, NullPlayerNamesException, NullAssignedPatternException, NullMaxPlayerException,
    InvalidMaxPlayerException, InvalidNumOfPlayersException, NullNumOfPlayersException, FullGameException, PlayerNotFoundException {
    
    final Game game = GameFactory.getNewGame("sender", 4);
    game.assignPlayers();
    final VirtualView virtualView = new VirtualView(game);
    final Player sp = game.getPlayerByName("sender");
    final GameMessage gm = new GameMessage(virtualView);
    final String chatMessage = gm.gson.toJson(virtualView);

    assertNotNull(gm);

    assertNotNull(gm.getMessage()); 
    assertEquals(chatMessage, gm.getMessage());

    assertNotNull(gm.getMessageType()); 
    assertEquals(MessageType.GAME_SNAPSHOT, gm.getMessageType());

    assertNotNull(gm.getGame());
    assertEquals(virtualView, gm.getGame());
  }
}
