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
import it.polimi.is23am10.playerconnector.PlayerConnector;
import it.polimi.is23am10.playerconnector.exceptions.NullSocketConnectorException;
import java.net.Socket;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
class ServerControllerActionTest {

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
  void EXECUTE_green_path() throws NullSocketConnectorException {
    Socket socket = new Socket();
    PlayerConnector playerConnector = new PlayerConnector(socket);
    AbstractCommand cmd = new StartGameCommand("player", 2);

    serverControllerAction.execute(playerConnector, cmd);
    assertEquals(1, ServerControllerState.getGamePools().size());
  }

  @Test
  void EXECUTE_NULL_COMMAND_green_path() throws NullSocketConnectorException {
    Socket socket = new Socket();
    PlayerConnector playerConnector = new PlayerConnector(socket);
    AbstractCommand cmd = null;

    serverControllerAction.execute(playerConnector, cmd);
    assertEquals(0, ServerControllerState.getGamePools().size());
  }

  @Test
  void START_CONSUMER_green_path() throws NullSocketConnectorException {
    Socket socket = new Socket();
    PlayerConnector playerConnector = new PlayerConnector(socket);
    AbstractCommand cmd = new StartGameCommand("Steve", 2);

    assertEquals(null, playerConnector.getPlayerName());
    assertEquals(null, playerConnector.getGameId());

    serverControllerAction.startConsumer.accept(playerConnector, cmd);

    assertEquals("Steve", playerConnector.getPlayerName());
    assertEquals(1, ServerControllerState.getGamePools().size());
    assertEquals(1, ServerControllerState.getPlayersPool().size());
    assertEquals(
        ServerControllerState.getGamePools().get(0).getGame().getGameId(),
        playerConnector.getGameId());
  }

  @Test
  void START_CONSUMER_throws_nullPlayerValue() throws NullSocketConnectorException {
    Socket socket = new Socket();
    PlayerConnector playerConnector = new PlayerConnector(socket);
    AbstractCommand cmd = new StartGameCommand(null, 2);

    assertEquals(null, playerConnector.getPlayerName());
    assertEquals(null, playerConnector.getGameId());

    serverControllerAction.startConsumer.accept(playerConnector, cmd);

    assertEquals(0, ServerControllerState.getGamePools().size());
    assertEquals(0, ServerControllerState.getPlayersPool().size());
  }

  @Test
  void START_CONSUMER_throws_nullMaxPlayersValue() throws NullSocketConnectorException {
    Socket socket = new Socket();
    PlayerConnector playerConnector = new PlayerConnector(socket);
    AbstractCommand cmd = new StartGameCommand("Steve", null);

    assertEquals(null, playerConnector.getPlayerName());
    assertEquals(null, playerConnector.getGameId());

    serverControllerAction.startConsumer.accept(playerConnector, cmd);

    assertEquals(0, ServerControllerState.getGamePools().size());
    assertEquals(0, ServerControllerState.getPlayersPool().size());
  }

  @Test
  void START_CONSUMER_throws_invalidNumOfPlayers() throws NullSocketConnectorException {
    Socket socket = new Socket();
    PlayerConnector playerConnector = new PlayerConnector(socket);
    AbstractCommand cmd = new StartGameCommand("Steve", 56);

    assertEquals(null, playerConnector.getPlayerName());
    assertEquals(null, playerConnector.getGameId());

    serverControllerAction.startConsumer.accept(playerConnector, cmd);

    assertEquals(0, ServerControllerState.getGamePools().size());
    assertEquals(0, ServerControllerState.getPlayersPool().size());
  }

  @Test
  void START_CONSUMER_throws_nullPlayerConnector() throws NullSocketConnectorException {
    PlayerConnector playerConnector = null;
    AbstractCommand cmd = new StartGameCommand("Steve", 2);

    serverControllerAction.startConsumer.accept(playerConnector, cmd);

    assertEquals(1, ServerControllerState.getGamePools().size());
    assertEquals(0, ServerControllerState.getPlayersPool().size());
  }

  @Test
  void START_CONSUMER_throws_StartCommandSerializationErrorException() throws NullSocketConnectorException {

    class Utils extends AbstractCommand {
      Utils(Opcode op) {
        super(op);
      }
    }

    Socket socket = new Socket();
    PlayerConnector playerConnector = new PlayerConnector(socket);
    AbstractCommand cmd = new Utils(Opcode.START);

    assertThrows(StartCommandSerializationErrorException.class,
        () -> serverControllerAction.startConsumer.accept(playerConnector, cmd));
  }

  @Test
  void ADD_PLAYER_CONSUMER_green_path() throws NullSocketConnectorException, NullGameHandlerInstance,
      NullMaxPlayerException, InvalidMaxPlayerException, NullPlayerNameException, NullPlayerIdException,
      NullPlayerBookshelfException, NullPlayerScoreException, NullPlayerPrivateCardException,
      NullPlayerScoreBlocksException, DuplicatePlayerNameException, AlreadyInitiatedPatternException,
      NullPlayerNamesException, InvalidNumOfPlayersException, NullNumOfPlayersException {
    Socket socket = new Socket();
    PlayerConnector playerConnector = new PlayerConnector(socket);

    GameHandler handler = new GameHandler("Max", 2);
    ServerControllerState.addGameHandler(handler);

    AbstractCommand cmd = new AddPlayerCommand("Steve", handler.getGame().getGameId());

    assertEquals(null, playerConnector.getPlayerName());
    assertEquals(null, playerConnector.getGameId());

    final int oldPlayerConnectors = ServerControllerState.getPlayersPool().size();

    serverControllerAction.addPlayerConsumer.accept(playerConnector, cmd);

    assertEquals("Steve", playerConnector.getPlayerName());
    assertEquals(handler.getGame().getGameId(), playerConnector.getGameId());

    assertEquals(oldPlayerConnectors + 1, ServerControllerState.getPlayersPool().size());
    assertTrue(handler.getPlayerConnectors().contains(playerConnector));
    assertTrue(handler.getGame().getPlayerNames().contains("Steve"));
  }

  @Test
  void ADD_PLAYER_CONSUMER_noMatch_green_path() throws NullSocketConnectorException, NullGameHandlerInstance,
      NullMaxPlayerException, InvalidMaxPlayerException, NullPlayerNameException, NullPlayerIdException,
      NullPlayerBookshelfException, NullPlayerScoreException, NullPlayerPrivateCardException,
      NullPlayerScoreBlocksException, DuplicatePlayerNameException, AlreadyInitiatedPatternException,
      NullPlayerNamesException, InvalidNumOfPlayersException, NullNumOfPlayersException {
    Socket socket = new Socket();
    PlayerConnector playerConnector = new PlayerConnector(socket);

    GameHandler handler = new GameHandler("Max", 2);
    ServerControllerState.addGameHandler(handler);

    AbstractCommand cmd = new AddPlayerCommand("Steve", UUID.randomUUID());

    assertEquals(null, playerConnector.getPlayerName());
    assertEquals(null, playerConnector.getGameId());

    final int oldPlayerConnectors = ServerControllerState.getPlayersPool().size();

    serverControllerAction.addPlayerConsumer.accept(playerConnector, cmd);

    assertEquals(null, playerConnector.getPlayerName());
    assertEquals(null, playerConnector.getGameId());

    assertEquals(oldPlayerConnectors, ServerControllerState.getPlayersPool().size());
    assertFalse(handler.getPlayerConnectors().contains(playerConnector));
    assertFalse(handler.getGame().getPlayerNames().contains("Steve"));
  }

  @Test
  void ADD_PLAYER_CONSUMER_throws_nullPlayerValue()
      throws NullSocketConnectorException, NullMaxPlayerException, InvalidMaxPlayerException, NullPlayerNameException,
      NullPlayerIdException, NullPlayerBookshelfException, NullPlayerScoreException, NullPlayerPrivateCardException,
      NullPlayerScoreBlocksException, DuplicatePlayerNameException, AlreadyInitiatedPatternException,
      NullPlayerNamesException, InvalidNumOfPlayersException, NullNumOfPlayersException, NullGameHandlerInstance {
    Socket socket = new Socket();
    PlayerConnector playerConnector = new PlayerConnector(socket);
    GameHandler handler = new GameHandler("Max", 2);
    ServerControllerState.addGameHandler(handler);
    AbstractCommand cmd = new AddPlayerCommand(null, handler.getGame().getGameId());

    assertEquals(null, playerConnector.getPlayerName());
    assertEquals(null, playerConnector.getGameId());

    serverControllerAction.addPlayerConsumer.accept(playerConnector, cmd);

    assertEquals(null, playerConnector.getPlayerName());
    assertEquals(null, playerConnector.getGameId());
  }

  @Test
  void ADD_PLAYER_CONSUMER_throws_DuplicatePlayerNameException()
      throws NullSocketConnectorException, NullGameHandlerInstance,
      NullMaxPlayerException, InvalidMaxPlayerException, NullPlayerNameException, NullPlayerIdException,
      NullPlayerBookshelfException, NullPlayerScoreException, NullPlayerPrivateCardException,
      NullPlayerScoreBlocksException, DuplicatePlayerNameException, AlreadyInitiatedPatternException,
      NullPlayerNamesException, InvalidNumOfPlayersException, NullNumOfPlayersException {

    Socket socket = new Socket();
    PlayerConnector steve = new PlayerConnector(socket);

    GameHandler handler = new GameHandler("Max", 2);
    ServerControllerState.addGameHandler(handler);

    AbstractCommand steveCmd = new AddPlayerCommand("Steve", handler.getGame().getGameId());

    assertEquals(null, steve.getPlayerName());
    assertEquals(null, steve.getGameId());

    final int oldPlayerConnectors = ServerControllerState.getPlayersPool().size();

    serverControllerAction.addPlayerConsumer.accept(steve, steveCmd);

    assertEquals("Steve", steve.getPlayerName());
    assertEquals(handler.getGame().getGameId(), steve.getGameId());

    assertEquals(oldPlayerConnectors + 1, ServerControllerState.getPlayersPool().size());
    assertTrue(handler.getPlayerConnectors().contains(steve));
    assertTrue(handler.getGame().getPlayerNames().contains("Steve"));

    PlayerConnector steveBrother = new PlayerConnector(socket);
    AbstractCommand steveBrotherCmd = new AddPlayerCommand("Steve", handler.getGame().getGameId());
    serverControllerAction.addPlayerConsumer.accept(steveBrother, steveBrotherCmd);
    assertEquals(oldPlayerConnectors + 1, ServerControllerState.getPlayersPool().size());
    assertFalse(handler.getPlayerConnectors().contains(steveBrother));
  }

  @Test
  void ADD_PLAYER_CONSUMER_throws_nullPlayerConnector()
      throws NullSocketConnectorException, NullMaxPlayerException, InvalidMaxPlayerException, NullPlayerNameException,
      NullPlayerIdException, NullPlayerBookshelfException, NullPlayerScoreException, NullPlayerPrivateCardException,
      NullPlayerScoreBlocksException, DuplicatePlayerNameException, AlreadyInitiatedPatternException,
      NullPlayerNamesException, InvalidNumOfPlayersException, NullNumOfPlayersException, NullGameHandlerInstance {
    PlayerConnector steve = null;

    GameHandler handler = new GameHandler("Max", 2);
    ServerControllerState.addGameHandler(handler);

    AbstractCommand steveCmd = new AddPlayerCommand("Steve", handler.getGame().getGameId());

    serverControllerAction.addPlayerConsumer.accept(steve, steveCmd);

    assertFalse(handler.getPlayerConnectors().contains(steve));
  }

  @Test
  void ADD_PLAYER_CONSUMER_throws_AddPlayerCommandSerializationErrorException()
      throws NullSocketConnectorException {
    class Utils extends AbstractCommand {
      Utils(Opcode op) {
        super(op);
      }
    }

    Socket socket = new Socket();
    PlayerConnector playerConnector = new PlayerConnector(socket);
    AbstractCommand cmd = new Utils(Opcode.START);

    assertThrows(AddPlayerCommandSerializationErrorException.class,
        () -> serverControllerAction.addPlayerConsumer.accept(playerConnector, cmd));
  }
}