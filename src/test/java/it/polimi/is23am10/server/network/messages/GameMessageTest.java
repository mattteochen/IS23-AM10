package it.polimi.is23am10.server.network.messages;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import it.polimi.is23am10.server.model.factory.GameFactory;
import it.polimi.is23am10.server.model.factory.exceptions.DuplicatePlayerNameException;
import it.polimi.is23am10.server.model.factory.exceptions.NullPlayerNamesException;
import it.polimi.is23am10.server.model.game.Game;
import it.polimi.is23am10.server.model.game.exceptions.FullGameException;
import it.polimi.is23am10.server.model.game.exceptions.InvalidMaxPlayerException;
import it.polimi.is23am10.server.model.game.exceptions.NullAssignedPatternException;
import it.polimi.is23am10.server.model.game.exceptions.NullMaxPlayerException;
import it.polimi.is23am10.server.model.game.exceptions.PlayerNotFoundException;
import it.polimi.is23am10.server.model.items.board.exceptions.InvalidNumOfPlayersException;
import it.polimi.is23am10.server.model.items.board.exceptions.NullNumOfPlayersException;
import it.polimi.is23am10.server.model.items.card.exceptions.AlreadyInitiatedPatternException;
import it.polimi.is23am10.server.model.items.scoreblock.exceptions.NotValidScoreBlockValueException;
import it.polimi.is23am10.server.model.player.exceptions.NullPlayerBookshelfException;
import it.polimi.is23am10.server.model.player.exceptions.NullPlayerIdException;
import it.polimi.is23am10.server.model.player.exceptions.NullPlayerNameException;
import it.polimi.is23am10.server.model.player.exceptions.NullPlayerPrivateCardException;
import it.polimi.is23am10.server.model.player.exceptions.NullPlayerScoreBlocksException;
import it.polimi.is23am10.server.model.player.exceptions.NullPlayerScoreException;
import it.polimi.is23am10.server.network.messages.AbstractMessage.MessageType;
import it.polimi.is23am10.server.network.virtualview.VirtualView;

/**
 * Tests for the game message class
 */
public class GameMessageTest {
  @Test
  public void constructor_should_create_GameMessage() 
    throws NullPlayerNameException, NullPlayerIdException, NullPlayerBookshelfException, NullPlayerScoreException,
    NullPlayerPrivateCardException, NullPlayerScoreBlocksException, DuplicatePlayerNameException, 
    AlreadyInitiatedPatternException, NullPlayerNamesException, NullAssignedPatternException, NullMaxPlayerException,
    InvalidMaxPlayerException, InvalidNumOfPlayersException, NullNumOfPlayersException, FullGameException, PlayerNotFoundException, NotValidScoreBlockValueException {
    
    final Game game = GameFactory.getNewGame("sender", 4);
    game.assignPlayers();
    final VirtualView virtualView = new VirtualView(game);
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
