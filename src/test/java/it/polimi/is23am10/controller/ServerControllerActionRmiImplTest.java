package it.polimi.is23am10.controller;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import it.polimi.is23am10.command.AbstractCommand;
import it.polimi.is23am10.command.AbstractCommand.Opcode;
import it.polimi.is23am10.command.AddPlayerCommand;
import it.polimi.is23am10.command.StartGameCommand;
import it.polimi.is23am10.controller.exceptions.AddPlayerCommandSerializationErrorException;
import it.polimi.is23am10.controller.exceptions.NullGameHandlerInstance;
import it.polimi.is23am10.controller.exceptions.StartCommandSerializationErrorException;
import it.polimi.is23am10.factory.exceptions.DuplicatePlayerNameException;
import it.polimi.is23am10.factory.exceptions.NullPlayerNamesException;
import it.polimi.is23am10.game.exceptions.InvalidMaxPlayerException;
import it.polimi.is23am10.game.exceptions.NullAssignedPatternException;
import it.polimi.is23am10.game.exceptions.NullMaxPlayerException;
import it.polimi.is23am10.gamehandler.GameHandler;
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
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
@SuppressWarnings({ "deprecation", "checkstyle:methodname", "checkstyle:abbreviationaswordinnamecheck",
    "checkstyle:linelengthcheck" })
class ServerControllerActionRmiImplTest {

  @Spy
  ServerControllerActionRmiImpl serverControllerAction = new ServerControllerActionRmiImpl();

  @BeforeEach
  void setup() {
    // if we don't call below, we will get NullPointerException
    MockitoAnnotations.initMocks(this);
    ServerControllerState.getGamePools().clear();
    ServerControllerState.getPlayersPool().clear();
  }

  @Test
  void EXECUTE_should_EXECUTE_COMMAND() throws NullSocketConnectorException, NullBlockingQueueException {
    AbstractCommand cmd = new StartGameCommand("player", 2);

    serverControllerAction.execute(null, cmd);
    assertEquals(1, ServerControllerState.getGamePools().size());
  }

  @Test
  void EXECUTE_should_NOT_EXECUTE_NULL_COMMAND() throws NullSocketConnectorException, NullBlockingQueueException {
    AbstractCommand cmd = null;

    serverControllerAction.execute(null, cmd);
    assertEquals(0, ServerControllerState.getGamePools().size());
  }

  @Test
  void START_CONSUMER_should_CONSUME_START_COMMAND()
      throws NullSocketConnectorException, NullBlockingQueueException, InterruptedException {
    AbstractCommand cmd = new StartGameCommand("Steve", 2);

    serverControllerAction.startConsumer.accept(cmd);

    assertEquals(1, ServerControllerState.getGamePools().size());
  }

  @Test
  void START_CONSUMER_should_THROW_NullPlayerException()
      throws NullSocketConnectorException, NullBlockingQueueException {
    AbstractCommand cmd = new StartGameCommand(null, 2);

    serverControllerAction.startConsumer.accept(cmd);

    assertEquals(0, ServerControllerState.getGamePools().size());
  }

  @Test
  void START_CONSUMER_should_THROW_nullMaxPlayersValue()
      throws NullSocketConnectorException, NullBlockingQueueException {
    AbstractCommand cmd = new StartGameCommand("Steve", null);

    serverControllerAction.startConsumer.accept(cmd);

    assertEquals(0, ServerControllerState.getGamePools().size());
  }

  @Test
  void START_CONSUMER_should_THROW_invalidNumOfPlayers()
      throws NullSocketConnectorException, NullBlockingQueueException {
    AbstractCommand cmd = new StartGameCommand("Steve", 56);

    serverControllerAction.startConsumer.accept(cmd);

    assertEquals(0, ServerControllerState.getGamePools().size());
  }

  @Test
  void START_CONSUMER_should_THROW_StartCommandSerializationErrorException()
      throws NullSocketConnectorException, NullBlockingQueueException {

    class Utils extends AbstractCommand {
      Utils(Opcode op) {
        super(op);
      }
    }

    AbstractCommand cmd = new Utils(Opcode.START);

    assertThrows(StartCommandSerializationErrorException.class,
        () -> serverControllerAction.startConsumer.accept(cmd));
  }

  @Test
  void ADD_PLAYER_should_CONSUME_ADD_PLAYER_COMMAND() throws NullSocketConnectorException, NullGameHandlerInstance,
      NullMaxPlayerException, InvalidMaxPlayerException, NullPlayerNameException, NullPlayerIdException,
      NullPlayerBookshelfException, NullPlayerScoreException, NullPlayerPrivateCardException,
      NullPlayerScoreBlocksException, DuplicatePlayerNameException, AlreadyInitiatedPatternException,
      NullPlayerNamesException, InvalidNumOfPlayersException, NullNumOfPlayersException, NullBlockingQueueException,
      NullAssignedPatternException {
    GameHandler handler = new GameHandler("Max", 2);
    ServerControllerState.addGameHandler(handler);

    AbstractCommand cmd = new AddPlayerCommand("Steve", handler.getGame().getGameId());

    serverControllerAction.addPlayerConsumer.accept(cmd);

    assertTrue(handler.getGame().getPlayerNames().contains("Steve"));
  }

  @Test
  void ADD_PLAYER_CONSUMER_should_not_ADD_PLAYER_IF_GAME_NO_MATCH()
      throws NullSocketConnectorException, NullGameHandlerInstance,
      NullMaxPlayerException, InvalidMaxPlayerException, NullPlayerNameException, NullPlayerIdException,
      NullPlayerBookshelfException, NullPlayerScoreException, NullPlayerPrivateCardException,
      NullPlayerScoreBlocksException, DuplicatePlayerNameException, AlreadyInitiatedPatternException,
      NullPlayerNamesException, InvalidNumOfPlayersException, NullNumOfPlayersException, NullBlockingQueueException,
      NullAssignedPatternException {
    GameHandler handler = new GameHandler("Max", 2);
    ServerControllerState.addGameHandler(handler);

    AbstractCommand cmd = new AddPlayerCommand("Steve", UUID.randomUUID());

    serverControllerAction.addPlayerConsumer.accept(cmd);

    assertFalse(handler.getGame().getPlayerNames().contains("Steve"));
    assertEquals(1, handler.getGame().getPlayers().size());
  }

  @Test
  void ADD_PLAYER_CONSUMER_should_THROW_nullPlayerValue()
      throws NullSocketConnectorException, NullMaxPlayerException, InvalidMaxPlayerException, NullPlayerNameException,
      NullPlayerIdException, NullPlayerBookshelfException, NullPlayerScoreException, NullPlayerPrivateCardException,
      NullPlayerScoreBlocksException, DuplicatePlayerNameException, AlreadyInitiatedPatternException,
      NullPlayerNamesException, InvalidNumOfPlayersException, NullNumOfPlayersException, NullGameHandlerInstance,
      NullBlockingQueueException, NullAssignedPatternException {
    GameHandler handler = new GameHandler("Max", 2);
    ServerControllerState.addGameHandler(handler);
    AbstractCommand cmd = new AddPlayerCommand(null, handler.getGame().getGameId());

    serverControllerAction.addPlayerConsumer.accept(cmd);
    assertEquals(1, handler.getGame().getPlayers().size());
  }

  @Test
  void ADD_PLAYER_CONSUMER_should_THROW_DuplicatePlayerNameException()
      throws NullSocketConnectorException, NullGameHandlerInstance,
      NullMaxPlayerException, InvalidMaxPlayerException, NullPlayerNameException, NullPlayerIdException,
      NullPlayerBookshelfException, NullPlayerScoreException, NullPlayerPrivateCardException,
      NullPlayerScoreBlocksException, DuplicatePlayerNameException, AlreadyInitiatedPatternException,
      NullPlayerNamesException, InvalidNumOfPlayersException, NullNumOfPlayersException, NullBlockingQueueException,
      NullAssignedPatternException {
    GameHandler handler = new GameHandler("Max", 2);
    ServerControllerState.addGameHandler(handler);

    AbstractCommand steveCmd = new AddPlayerCommand("Steve", handler.getGame().getGameId());

    serverControllerAction.addPlayerConsumer.accept(steveCmd);

    assertTrue(handler.getGame().getPlayerNames().contains("Steve"));

    AbstractCommand steveBrotherCmd = new AddPlayerCommand("Steve", handler.getGame().getGameId());
    serverControllerAction.addPlayerConsumer.accept(steveBrotherCmd);
    assertEquals(2, handler.getGame().getPlayers().size());
  }

  @Test
  void ADD_PLAYER_CONSUMER_should_THROW_AddPlayerCommandSerializationErrorException()
      throws NullSocketConnectorException, NullBlockingQueueException {
    class Utils extends AbstractCommand {
      Utils(Opcode op) {
        super(op);
      }
    }

    AbstractCommand cmd = new Utils(Opcode.START);

    assertThrows(AddPlayerCommandSerializationErrorException.class,
        () -> serverControllerAction.addPlayerConsumer.accept(cmd));
  }
}
