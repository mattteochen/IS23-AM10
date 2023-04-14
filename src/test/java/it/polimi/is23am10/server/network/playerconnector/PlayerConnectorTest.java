package it.polimi.is23am10.server.network.playerconnector;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import it.polimi.is23am10.server.model.factory.GameFactory;
import it.polimi.is23am10.server.model.factory.exceptions.DuplicatePlayerNameException;
import it.polimi.is23am10.server.model.factory.exceptions.NullPlayerNamesException;
import it.polimi.is23am10.server.model.game.Game;
import it.polimi.is23am10.server.model.game.exceptions.FullGameException;
import it.polimi.is23am10.server.model.game.exceptions.InvalidMaxPlayerException;
import it.polimi.is23am10.server.model.game.exceptions.NullAssignedPatternException;
import it.polimi.is23am10.server.model.game.exceptions.NullMaxPlayerException;
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
import it.polimi.is23am10.server.network.messages.AbstractMessage;
import it.polimi.is23am10.server.network.messages.GameMessage;
import it.polimi.is23am10.server.network.playerconnector.PlayerConnectorSocket;
import it.polimi.is23am10.server.network.playerconnector.exceptions.NullBlockingQueueException;
import it.polimi.is23am10.server.network.playerconnector.exceptions.NullSocketConnectorException;
import it.polimi.is23am10.server.network.virtualview.VirtualView;

import java.net.Socket;
import java.util.Optional;
import java.util.concurrent.LinkedBlockingQueue;
import org.junit.jupiter.api.Test;

@SuppressWarnings({ "checkstyle:methodname", "checkstyle:abbreviationaswordinnamecheck", "checkstyle:linelengthcheck",
    "checkstyle:onetoplevelclasscheck", "checkstyle:variabledeclarationusagedistancecheck",
    "checkstyle:operatorwrapcheck", "checkstyle:multiplevariabledeclarationscheck", "checkstyle:membernamecheck",
    "checkstyle:nonemptyatclausedescriptioncheck", "checkstyle:typenamecheck" })
class PlayerConnectorSocketTest {
  @Test
  void CONSTRUCTOR_should_THROW_NullSocketConnectorException() {
    assertThrows(NullSocketConnectorException.class, () -> new PlayerConnectorSocket(null, new LinkedBlockingQueue<>()));
  }

  @Test
  void CONSTRUCTOR_should_THROW_NullBlockingQueueException() {
    assertThrows(NullBlockingQueueException.class, () -> new PlayerConnectorSocket(new Socket(), null));
  }

  @Test
  void QUEUE_should_HAVE_MESAGE() throws NullSocketConnectorException, NullBlockingQueueException, InterruptedException,
      NullMaxPlayerException, InvalidMaxPlayerException, NullPlayerNameException, NullPlayerIdException,
      NullPlayerBookshelfException, NullPlayerScoreException, NullPlayerPrivateCardException,
      NullPlayerScoreBlocksException, DuplicatePlayerNameException, AlreadyInitiatedPatternException,
      NullPlayerNamesException, InvalidNumOfPlayersException, NullNumOfPlayersException, NullAssignedPatternException, FullGameException, NotValidScoreBlockValueException {
    PlayerConnectorSocket connector = new PlayerConnectorSocket(new Socket(), new LinkedBlockingQueue<>());
    Game game = GameFactory.getNewGame("Test", 2);
    VirtualView virtualView = new VirtualView(game);
    GameMessage message = new GameMessage(virtualView);
    connector.addMessageToQueue(message);

    assertEquals(1, connector.getMsgQueueSize());
  }

  @Test
  void QUEUE_should_TAKE_MESAGE() throws NullSocketConnectorException, NullBlockingQueueException, InterruptedException,
      NullMaxPlayerException, InvalidMaxPlayerException, NullPlayerNameException, NullPlayerIdException,
      NullPlayerBookshelfException, NullPlayerScoreException, NullPlayerPrivateCardException,
      NullPlayerScoreBlocksException, DuplicatePlayerNameException, AlreadyInitiatedPatternException,
      NullPlayerNamesException, InvalidNumOfPlayersException, NullNumOfPlayersException, NullAssignedPatternException, FullGameException, NotValidScoreBlockValueException {
    PlayerConnectorSocket connector = new PlayerConnectorSocket(new Socket(), new LinkedBlockingQueue<>());
    Game game = GameFactory.getNewGame("Test", 2);
    VirtualView virtualView = new VirtualView(game);
    GameMessage message = new GameMessage(virtualView);
    connector.addMessageToQueue(message);

    Optional<AbstractMessage> taken = connector.getMessageFromQueue();

    assertEquals(taken.get(), message);
    assertEquals(0, connector.getMsgQueueSize());
  }

  @Test
  void QUEUE_should_TAKE_EMPTY_MESAGE()
      throws NullSocketConnectorException, NullBlockingQueueException, InterruptedException {
    PlayerConnectorSocket connector = new PlayerConnectorSocket(new Socket(), new LinkedBlockingQueue<>());

    Optional<AbstractMessage> taken = connector.getMessageFromQueue();

    assertFalse(taken.isPresent());
  }
}
