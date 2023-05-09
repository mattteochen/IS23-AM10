package it.polimi.is23am10.server.network.messages;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Disabled;
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
import it.polimi.is23am10.server.model.player.Player;
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
public class AvailableGamesMessageTest {
  @Test
  public void constructor_should_create_AvailableGamesMessage() 
    throws NullPlayerNameException, NullPlayerIdException, NullPlayerBookshelfException, NullPlayerScoreException,
    NullPlayerPrivateCardException, NullPlayerScoreBlocksException, DuplicatePlayerNameException, 
    AlreadyInitiatedPatternException, NullPlayerNamesException, NullAssignedPatternException, NullMaxPlayerException,
    InvalidMaxPlayerException, InvalidNumOfPlayersException, NullNumOfPlayersException, FullGameException, PlayerNotFoundException, NotValidScoreBlockValueException {
    
    final Game game1 = GameFactory.getNewGame("pippo", 4);
    final Game game2 = GameFactory.getNewGame("pluto", 2);
    final List<VirtualView> games = List.of(new VirtualView(game1), new VirtualView(game2));

    final Player mockPlayer = new Player();

    final AvailableGamesMessage agm = new AvailableGamesMessage(games, mockPlayer);

    assertNotNull(agm.getMessage());
    
    assertNotNull(agm.getMessageType()); 
    assertEquals(MessageType.AVAILABLE_GAMES, agm.getMessageType());
  }
}
