package it.polimi.is23am10.server.controller;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import it.polimi.is23am10.server.command.AbstractCommand;
import it.polimi.is23am10.server.command.AddPlayerCommand;
import it.polimi.is23am10.server.command.StartGameCommand;
import it.polimi.is23am10.server.command.AbstractCommand.Opcode;
import it.polimi.is23am10.server.controller.ServerControllerAction;
import it.polimi.is23am10.server.controller.ServerControllerState;
import it.polimi.is23am10.server.controller.exceptions.NullGameHandlerInstance;
import it.polimi.is23am10.server.model.factory.PlayerFactory;
import it.polimi.is23am10.server.model.factory.exceptions.DuplicatePlayerNameException;
import it.polimi.is23am10.server.model.factory.exceptions.NullPlayerNamesException;
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
import it.polimi.is23am10.server.network.gamehandler.GameHandler;
import it.polimi.is23am10.server.network.messages.AbstractMessage;
import it.polimi.is23am10.server.network.playerconnector.PlayerConnectorSocket;
import it.polimi.is23am10.server.network.playerconnector.exceptions.NullBlockingQueueException;
import it.polimi.is23am10.server.network.playerconnector.exceptions.NullSocketConnectorException;
import it.polimi.is23am10.utils.ErrorTypeString;

import java.net.Socket;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingQueue;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
@SuppressWarnings({ "deprecation", "checkstyle:methodname", "checkstyle:abbreviationaswordinnamecheck",
    "checkstyle:linelengthcheck" })
class ServerControllerActionTest {

  private final Logger logger = LogManager.getLogger(ServerControllerActionTest.class);

  @Spy
  ServerControllerAction serverControllerAction = new ServerControllerAction();

  @BeforeEach
  void setup() {
    // if we don't call below, we will get NullPointerException
    MockitoAnnotations.initMocks(this);
    ServerControllerState.getGamePools().clear();
    ServerControllerState.getPlayersPool().clear();
  }

  @Test
  void EXECUTE_should_EXECUTE_COMMAND() throws NullSocketConnectorException, NullBlockingQueueException {
    Socket socket = new Socket();
    PlayerConnectorSocket playerConnector = new PlayerConnectorSocket(socket, new LinkedBlockingQueue<>());
    AbstractCommand cmd = new StartGameCommand("player", 2);

    serverControllerAction.execute(playerConnector, cmd);
    assertEquals(1, ServerControllerState.getGamePools().size());
  }

  @Test
  void EXECUTE_should_NOT_EXECUTE_NULL_COMMAND() throws NullSocketConnectorException, NullBlockingQueueException {
    Socket socket = new Socket();
    PlayerConnectorSocket playerConnector = new PlayerConnectorSocket(socket, new LinkedBlockingQueue<>());
    AbstractCommand cmd = null;

    serverControllerAction.execute(playerConnector, cmd);
    assertEquals(0, ServerControllerState.getGamePools().size());
  }

  @Test
  void START_CONSUMER_should_CONSUME_START_COMMAND()
      throws NullSocketConnectorException, NullBlockingQueueException, InterruptedException {
    Socket socket = new Socket();
    PlayerConnectorSocket playerConnector = new PlayerConnectorSocket(socket, new LinkedBlockingQueue<>());
    AbstractCommand cmd = new StartGameCommand("Steve", 2);

    assertEquals(null, playerConnector.getPlayer());
    assertEquals(null, playerConnector.getGameId());

    serverControllerAction.startConsumer.accept(logger, playerConnector, cmd);

    assertEquals("Steve", playerConnector.getPlayer().getPlayerName());
    assertEquals(1, ServerControllerState.getGamePools().size());
    assertEquals(1, ServerControllerState.getPlayersPool().size());
    Optional<GameHandler> search = ServerControllerState.getGamePools().stream()
        .filter(g -> g.getGame().getGameId().equals(playerConnector.getGameId())).findFirst();
    assertTrue(search.isPresent());
    assertEquals(1, playerConnector.getMsgQueueSize());
  }

  @Test
  void START_CONSUMER_should_THROW_NullPlayerException()
      throws NullSocketConnectorException, NullBlockingQueueException, InterruptedException {
    Socket socket = new Socket();
    PlayerConnectorSocket playerConnector = new PlayerConnectorSocket(socket, new LinkedBlockingQueue<>());
    AbstractCommand cmd = new StartGameCommand(null, 2);

    assertEquals(null, playerConnector.getPlayer());
    assertEquals(null, playerConnector.getGameId());

    serverControllerAction.startConsumer.accept(logger, playerConnector, cmd);

    assertEquals(0, ServerControllerState.getGamePools().size());
    assertEquals(0, ServerControllerState.getPlayersPool().size());

    assertEquals(1,playerConnector.getMsgQueueSize());
    AbstractMessage errorMsg = playerConnector.getMessageFromQueue();
    assertEquals(ErrorTypeString.ERROR_INITIALIZING_NEW_GAME, errorMsg.getMessage());
  }

  @Test
  void START_CONSUMER_should_THROW_nullMaxPlayersValue()
      throws NullSocketConnectorException, NullBlockingQueueException, InterruptedException {
    Socket socket = new Socket();
    PlayerConnectorSocket playerConnector = new PlayerConnectorSocket(socket, new LinkedBlockingQueue<>());
    AbstractCommand cmd = new StartGameCommand("Steve", null);

    assertEquals(null, playerConnector.getPlayer());
    assertEquals(null, playerConnector.getGameId());

    serverControllerAction.startConsumer.accept(logger, playerConnector, cmd);

    assertEquals(0, ServerControllerState.getGamePools().size());
    assertEquals(0, ServerControllerState.getPlayersPool().size());

    AbstractMessage errorMsg = playerConnector.getMessageFromQueue();
    assertEquals(ErrorTypeString.ERROR_INITIALIZING_NEW_GAME, errorMsg.getMessage());
  }

  @Test
  void START_CONSUMER_should_THROW_invalidNumOfPlayers()
      throws NullSocketConnectorException, NullBlockingQueueException, InterruptedException {
    Socket socket = new Socket();
    PlayerConnectorSocket playerConnector = new PlayerConnectorSocket(socket, new LinkedBlockingQueue<>());
    AbstractCommand cmd = new StartGameCommand("Steve", 56);

    assertEquals(null, playerConnector.getPlayer());
    assertEquals(null, playerConnector.getGameId());

    serverControllerAction.startConsumer.accept(logger, playerConnector, cmd);

    assertEquals(0, ServerControllerState.getGamePools().size());
    assertEquals(0, ServerControllerState.getPlayersPool().size());

    AbstractMessage errorMsg = playerConnector.getMessageFromQueue();
    assertEquals(ErrorTypeString.ERROR_ADDING_PLAYERS, errorMsg.getMessage());
  }

  @Test
  void START_CONSUMER_should_THROW_nullPlayerConnector() throws NullSocketConnectorException {
    PlayerConnectorSocket playerConnector = null;
    AbstractCommand cmd = new StartGameCommand("Steve", 2);

    serverControllerAction.startConsumer.accept(logger, playerConnector, cmd);

    assertEquals(0, ServerControllerState.getGamePools().size());
    assertEquals(0, ServerControllerState.getPlayersPool().size());
  }

  @Test
  void ADD_PLAYER_should_CONSUME_ADD_PLAYER_COMMAND() throws NullSocketConnectorException, NullGameHandlerInstance,
      NullMaxPlayerException, InvalidMaxPlayerException, NullPlayerNameException, NullPlayerIdException,
      NullPlayerBookshelfException, NullPlayerScoreException, NullPlayerPrivateCardException,
      NullPlayerScoreBlocksException, DuplicatePlayerNameException, AlreadyInitiatedPatternException,
      NullPlayerNamesException, InvalidNumOfPlayersException, NullNumOfPlayersException, NullBlockingQueueException,
      NullAssignedPatternException, FullGameException, NotValidScoreBlockValueException, PlayerNotFoundException {
    Socket socket = new Socket();
    PlayerConnectorSocket playerConnector = new PlayerConnectorSocket(socket, new LinkedBlockingQueue<>());

    GameHandler handler = new GameHandler("Max", 2);
    ServerControllerState.addGameHandler(handler);

    AbstractCommand cmd = new AddPlayerCommand("Steve", handler.getGame().getGameId());

    assertEquals(null, playerConnector.getPlayer());
    assertEquals(null, playerConnector.getGameId());

    final int oldPlayerConnectors = ServerControllerState.getPlayersPool().size();

    serverControllerAction.addPlayerConsumer.accept(logger, playerConnector, cmd);

    assertEquals("Steve", playerConnector.getPlayer().getPlayerName());
    assertEquals(handler.getGame().getGameId(), playerConnector.getGameId());

    assertEquals(oldPlayerConnectors + 1, ServerControllerState.getPlayersPool().size());
    assertTrue(handler.getPlayerConnectors().contains(playerConnector));
    assertTrue(handler.getGame().getPlayerNames().contains("Steve"));
    assertEquals(1, playerConnector.getMsgQueueSize());
  }

  @Test
  void ADD_PLAYER_CONSUMER_should_not_ADD_PLAYER_IF_GAME_NO_MATCH()
      throws NullSocketConnectorException, NullGameHandlerInstance,
      NullMaxPlayerException, InvalidMaxPlayerException, NullPlayerNameException, NullPlayerIdException,
      NullPlayerBookshelfException, NullPlayerScoreException, NullPlayerPrivateCardException,
      NullPlayerScoreBlocksException, DuplicatePlayerNameException, AlreadyInitiatedPatternException,
      NullPlayerNamesException, InvalidNumOfPlayersException, NullNumOfPlayersException, NullBlockingQueueException,
      NullAssignedPatternException, FullGameException, NotValidScoreBlockValueException, PlayerNotFoundException, InterruptedException {
    Socket socket = new Socket();
    PlayerConnectorSocket playerConnector = new PlayerConnectorSocket(socket, new LinkedBlockingQueue<>());

    GameHandler handler = new GameHandler("Max", 2);
    ServerControllerState.addGameHandler(handler);

    AbstractCommand cmd = new AddPlayerCommand("Steve", UUID.randomUUID());

    assertEquals(null, playerConnector.getPlayer());
    assertEquals(null, playerConnector.getGameId());

    final int oldPlayerConnectors = ServerControllerState.getPlayersPool().size();

    serverControllerAction.addPlayerConsumer.accept(logger, playerConnector, cmd);

    assertEquals(null, playerConnector.getPlayer());
    assertEquals(null, playerConnector.getGameId());

    assertEquals(oldPlayerConnectors, ServerControllerState.getPlayersPool().size());
    assertFalse(handler.getPlayerConnectors().contains(playerConnector));
    assertFalse(handler.getGame().getPlayerNames().contains("Steve"));

    assertEquals(1, playerConnector.getMsgQueueSize());
    AbstractMessage errorMsg = playerConnector.getMessageFromQueue();
    assertEquals(ErrorTypeString.ERROR_ADDING_PLAYERS, errorMsg.getMessage());
  }

  @Test
  void ADD_PLAYER_CONSUMER_should_not_ADD_PLAYER_IF_GAME_IS_FULL()
      throws NullSocketConnectorException, NullGameHandlerInstance,
      NullMaxPlayerException, InvalidMaxPlayerException, NullPlayerNameException, NullPlayerIdException,
      NullPlayerBookshelfException, NullPlayerScoreException, NullPlayerPrivateCardException,
      NullPlayerScoreBlocksException, DuplicatePlayerNameException, AlreadyInitiatedPatternException,
      NullPlayerNamesException, InvalidNumOfPlayersException, NullNumOfPlayersException, NullBlockingQueueException,
      NullAssignedPatternException, FullGameException, NotValidScoreBlockValueException, PlayerNotFoundException, InterruptedException {
    Socket socket = new Socket();
    GameHandler handler = new GameHandler("Max", 2);
    ServerControllerState.addGameHandler(handler);

    PlayerConnectorSocket steve = new PlayerConnectorSocket(socket, new LinkedBlockingQueue<>());
    AbstractCommand steveCmd = new AddPlayerCommand("Steve", handler.getGame().getGameId());

    final int oldPlayerConnectors = ServerControllerState.getPlayersPool().size();

    assertEquals(null, steve.getPlayer());
    assertEquals(null, steve.getGameId());

    serverControllerAction.addPlayerConsumer.accept(logger, steve, steveCmd);

    assertEquals(oldPlayerConnectors + 1, ServerControllerState.getPlayersPool().size());
    assertTrue(handler.getPlayerConnectors().contains(steve));
    assertTrue(handler.getGame().getPlayerNames().contains("Steve"));

    PlayerConnectorSocket alice = new PlayerConnectorSocket(socket, new LinkedBlockingQueue<>());
    AbstractCommand aliceCmd = new AddPlayerCommand("Alice", handler.getGame().getGameId());

    assertEquals(null, alice.getPlayer());
    assertEquals(null, alice.getGameId());

    // assign alice params to avoid NPE
    alice.setPlayer(PlayerFactory.getNewPlayer("Alice", handler.getGame()));
    alice.setGameId(handler.getGame().getGameId());

    serverControllerAction.addPlayerConsumer.accept(logger, alice, aliceCmd);

    assertEquals(oldPlayerConnectors + 1, ServerControllerState.getPlayersPool().size());
    assertFalse(handler.getPlayerConnectors().contains(alice));
    assertFalse(handler.getGame().getPlayerNames().contains("Alice"));

    assertEquals(1, alice.getMsgQueueSize());
    AbstractMessage errorMsg = alice.getMessageFromQueue();
    assertEquals(ErrorTypeString.ERROR_ADDING_PLAYERS, errorMsg.getMessage());
  }

  @Test
  void ADD_PLAYER_CONSUMER_should_THROW_nullPlayerValue()
      throws NullSocketConnectorException, NullMaxPlayerException, InvalidMaxPlayerException, NullPlayerNameException,
      NullPlayerIdException, NullPlayerBookshelfException, NullPlayerScoreException, NullPlayerPrivateCardException,
      NullPlayerScoreBlocksException, DuplicatePlayerNameException, AlreadyInitiatedPatternException,
      NullPlayerNamesException, InvalidNumOfPlayersException, NullNumOfPlayersException, NullGameHandlerInstance,
      NullBlockingQueueException, NullAssignedPatternException, FullGameException, NotValidScoreBlockValueException, PlayerNotFoundException, InterruptedException {
    Socket socket = new Socket();
    PlayerConnectorSocket playerConnector = new PlayerConnectorSocket(socket, new LinkedBlockingQueue<>());
    GameHandler handler = new GameHandler("Max", 2);
    ServerControllerState.addGameHandler(handler);
    AbstractCommand cmd = new AddPlayerCommand(null, handler.getGame().getGameId());

    assertEquals(null, playerConnector.getPlayer());
    assertEquals(null, playerConnector.getGameId());

    serverControllerAction.addPlayerConsumer.accept(logger, playerConnector, cmd);

    assertEquals(null, playerConnector.getPlayer());
    assertEquals(null, playerConnector.getGameId());
        
    assertEquals(1,playerConnector.getMsgQueueSize());
    AbstractMessage errorMsg = playerConnector.getMessageFromQueue();
    assertEquals(ErrorTypeString.ERROR_ADDING_PLAYERS, errorMsg.getMessage());
  }


  @Test
  void ADD_PLAYER_CONSUMER_should_THROW_DuplicatePlayerNameException()
      throws NullSocketConnectorException, NullGameHandlerInstance,
      NullMaxPlayerException, InvalidMaxPlayerException, NullPlayerNameException, NullPlayerIdException,
      NullPlayerBookshelfException, NullPlayerScoreException, NullPlayerPrivateCardException,
      NullPlayerScoreBlocksException, DuplicatePlayerNameException, AlreadyInitiatedPatternException,
      NullPlayerNamesException, InvalidNumOfPlayersException, NullNumOfPlayersException, NullBlockingQueueException,
      NullAssignedPatternException, FullGameException, NotValidScoreBlockValueException, PlayerNotFoundException, InterruptedException {

    Socket socket = new Socket();
    PlayerConnectorSocket steve = new PlayerConnectorSocket(socket, new LinkedBlockingQueue<>());

    GameHandler handler = new GameHandler("Max", 2);
    ServerControllerState.addGameHandler(handler);

    AbstractCommand steveCmd = new AddPlayerCommand("Steve", handler.getGame().getGameId());

    assertEquals(null, steve.getPlayer());
    assertEquals(null, steve.getGameId());

    final int oldPlayerConnectors = ServerControllerState.getPlayersPool().size();

    serverControllerAction.addPlayerConsumer.accept(logger, steve, steveCmd);

    assertEquals("Steve", steve.getPlayer().getPlayerName());
    assertEquals(handler.getGame().getGameId(), steve.getGameId());

    assertEquals(oldPlayerConnectors + 1, ServerControllerState.getPlayersPool().size());
    assertTrue(handler.getPlayerConnectors().contains(steve));
    assertTrue(handler.getGame().getPlayerNames().contains("Steve"));

    PlayerConnectorSocket steveBrother = new PlayerConnectorSocket(socket, new LinkedBlockingQueue<>());
    AbstractCommand steveBrotherCmd = new AddPlayerCommand("Steve", handler.getGame().getGameId());
    serverControllerAction.addPlayerConsumer.accept(logger, steveBrother, steveBrotherCmd);
    assertEquals(oldPlayerConnectors + 1, ServerControllerState.getPlayersPool().size());

    assertEquals(1,steveBrother.getMsgQueueSize());
    AbstractMessage errorMsg = steveBrother.getMessageFromQueue();
    assertEquals(ErrorTypeString.ERROR_ADDING_PLAYERS, errorMsg.getMessage());
  }

  @Test
  void ADD_PLAYER_CONSUMER_should_THROW_nullPlayerConnector()
      throws NullSocketConnectorException, NullMaxPlayerException, InvalidMaxPlayerException, NullPlayerNameException,
      NullPlayerIdException, NullPlayerBookshelfException, NullPlayerScoreException, NullPlayerPrivateCardException,
      NullPlayerScoreBlocksException, DuplicatePlayerNameException, AlreadyInitiatedPatternException,
      NullPlayerNamesException, InvalidNumOfPlayersException, NullNumOfPlayersException, NullGameHandlerInstance,
      NullAssignedPatternException, FullGameException, NotValidScoreBlockValueException, PlayerNotFoundException {
    PlayerConnectorSocket steve = null;

    GameHandler handler = new GameHandler("Max", 2);
    ServerControllerState.addGameHandler(handler);

    AbstractCommand steveCmd = new AddPlayerCommand("Steve", handler.getGame().getGameId());

    serverControllerAction.addPlayerConsumer.accept(logger, steve, steveCmd);

    assertFalse(handler.getPlayerConnectors().contains(steve));
  }
}
