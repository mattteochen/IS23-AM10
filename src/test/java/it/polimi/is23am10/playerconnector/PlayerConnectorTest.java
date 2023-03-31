package it.polimi.is23am10.playerconnector;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import it.polimi.is23am10.chat.AbstractMessage;
import it.polimi.is23am10.chat.GameMessage;
import it.polimi.is23am10.factory.GameFactory;
import it.polimi.is23am10.factory.exceptions.DuplicatePlayerNameException;
import it.polimi.is23am10.factory.exceptions.NullPlayerNamesException;
import it.polimi.is23am10.game.Game;
import it.polimi.is23am10.game.exceptions.InvalidMaxPlayerException;
import it.polimi.is23am10.game.exceptions.NullAssignedPatternException;
import it.polimi.is23am10.game.exceptions.NullMaxPlayerException;
import it.polimi.is23am10.items.board.exceptions.InvalidNumOfPlayersException;
import it.polimi.is23am10.items.board.exceptions.NullNumOfPlayersException;
import it.polimi.is23am10.items.card.exceptions.AlreadyInitiatedPatternException;
import it.polimi.is23am10.player.exceptions.NullPlayerBookshelfException;
import it.polimi.is23am10.player.exceptions.NullPlayerIdException;
import it.polimi.is23am10.player.exceptions.NullPlayerNameException;
import it.polimi.is23am10.player.exceptions.NullPlayerPrivateCardException;
import it.polimi.is23am10.player.exceptions.NullPlayerScoreBlocksException;
import it.polimi.is23am10.player.exceptions.NullPlayerScoreException;
import it.polimi.is23am10.playerconnector.exceptions.NullBlockingQueueException;
import it.polimi.is23am10.playerconnector.exceptions.NullSocketConnectorException;
import it.polimi.is23am10.virtualview.VirtualView;
import it.polimi.is23am10.game.exceptions.FullGameException;

import java.net.Socket;
import java.util.Optional;
import java.util.concurrent.LinkedBlockingQueue;
import org.junit.jupiter.api.Test;

@SuppressWarnings({ "checkstyle:methodname", "checkstyle:abbreviationaswordinnamecheck", "checkstyle:linelengthcheck",
    "checkstyle:onetoplevelclasscheck", "checkstyle:variabledeclarationusagedistancecheck",
    "checkstyle:operatorwrapcheck", "checkstyle:multiplevariabledeclarationscheck", "checkstyle:membernamecheck",
    "checkstyle:nonemptyatclausedescriptioncheck", "checkstyle:typenamecheck" })
class PlayerConnectorTest {
  @Test
  void CONSTRUCTOR_should_THROW_NullSocketConnectorException() {
    assertThrows(NullSocketConnectorException.class, () -> new PlayerConnector(null, new LinkedBlockingQueue<>()));
  }

  @Test
  void CONSTRUCTOR_should_THROW_NullBlockingQueueException() {
    assertThrows(NullBlockingQueueException.class, () -> new PlayerConnector(new Socket(), null));
  }

  @Test
  void QUEUE_should_HAVE_MESAGE() throws NullSocketConnectorException, NullBlockingQueueException, InterruptedException,
      NullMaxPlayerException, InvalidMaxPlayerException, NullPlayerNameException, NullPlayerIdException,
      NullPlayerBookshelfException, NullPlayerScoreException, NullPlayerPrivateCardException,
      NullPlayerScoreBlocksException, DuplicatePlayerNameException, AlreadyInitiatedPatternException,
      NullPlayerNamesException, InvalidNumOfPlayersException, NullNumOfPlayersException, NullAssignedPatternException, FullGameException {
    PlayerConnector connector = new PlayerConnector(new Socket(), new LinkedBlockingQueue<>());
    Game game = GameFactory.getNewGame("Test", 2);
    VirtualView virtualView = new VirtualView(game);
    GameMessage message = new GameMessage(game.getFirstPlayer(),virtualView);
    connector.addMessageToQueue(message);

    assertEquals(1, connector.getMsgQueueSize());
  }

  @Test
  void QUEUE_should_TAKE_MESAGE() throws NullSocketConnectorException, NullBlockingQueueException, InterruptedException,
      NullMaxPlayerException, InvalidMaxPlayerException, NullPlayerNameException, NullPlayerIdException,
      NullPlayerBookshelfException, NullPlayerScoreException, NullPlayerPrivateCardException,
      NullPlayerScoreBlocksException, DuplicatePlayerNameException, AlreadyInitiatedPatternException,
      NullPlayerNamesException, InvalidNumOfPlayersException, NullNumOfPlayersException, NullAssignedPatternException, FullGameException {
    PlayerConnector connector = new PlayerConnector(new Socket(), new LinkedBlockingQueue<>());
    Game game = GameFactory.getNewGame("Test", 2);
    VirtualView virtualView = new VirtualView(game);
    GameMessage message = new GameMessage(game.getFirstPlayer(),virtualView);
    connector.addMessageToQueue(message);

    Optional<AbstractMessage> taken = connector.getMessageFromQueue();

    assertEquals(taken.get(), message);
    assertEquals(0, connector.getMsgQueueSize());
  }

  @Test
  void QUEUE_should_TAKE_EMPTY_MESAGE()
      throws NullSocketConnectorException, NullBlockingQueueException, InterruptedException {
    PlayerConnector connector = new PlayerConnector(new Socket(), new LinkedBlockingQueue<>());

    Optional<AbstractMessage> taken = connector.getMessageFromQueue();

    assertFalse(taken.isPresent());
  }
}
