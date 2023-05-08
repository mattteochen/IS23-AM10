package it.polimi.is23am10.server.controller;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;

import it.polimi.is23am10.server.command.AbstractCommand;
import it.polimi.is23am10.server.command.AddPlayerCommand;
import it.polimi.is23am10.server.command.GetAvailableGamesCommand;
import it.polimi.is23am10.server.command.MoveTilesCommand;
import it.polimi.is23am10.server.command.StartGameCommand;
import it.polimi.is23am10.server.controller.exceptions.NullGameHandlerInstance;
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
import it.polimi.is23am10.server.network.gamehandler.GameHandler;
import it.polimi.is23am10.server.network.messages.GameMessage;
import it.polimi.is23am10.server.network.playerconnector.PlayerConnectorSocket;
import it.polimi.is23am10.server.network.playerconnector.exceptions.NullBlockingQueueException;
import it.polimi.is23am10.server.network.playerconnector.exceptions.NullSocketConnectorException;
import it.polimi.is23am10.server.network.virtualview.VirtualView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingQueue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

@SuppressWarnings({ "checkstyle:methodname", "checkstyle:abbreviationaswordinnamecheck", "checkstyle:linelengthcheck",
    "checkstyle:onetoplevelclasscheck", "checkstyle:membernamecheck", "checkstyle:OuterTypeFilenameCheck" })
class TestingPurposesClass {
  String s = "Before you marry a person, you should first make them use a computer with slow Internet to see who they really are";
}

@SuppressWarnings({ "checkstyle:methodname", "checkstyle:abbreviationaswordinnamecheck", "checkstyle:linelengthcheck",
    "checkstyle:onetoplevelclasscheck" })
class TestingPurposesClass2 {
  String className = "I never forget a face but in your case, I’ll be glad to make an exception";
}

@RunWith(MockitoJUnitRunner.class)
@SuppressWarnings({ "deprecation", "unchecked", "checkstyle:methodname", "checkstyle:abbreviationaswordinnamecheck",
    "checkstyle:linelengthcheck", "checkstyle:onetoplevelclasscheck" })
class ServerControllerSocketTest {

  @Mock
  PlayerConnectorSocket playerConnector;

  @Mock
  ServerControllerAction serverControllerAction;

  @Spy
  @InjectMocks
  ServerControllerSocket controller;

  @BeforeEach
  void setup() {
    // if we don't call below, we will get NullPointerException
    MockitoAnnotations.initMocks(this);
  }

  @Test
  void CONSTRUCTOR_should_BUILD_OBJECT() throws NullSocketConnectorException, NullBlockingQueueException {
    Socket socket = new Socket();
    ServerControllerSocket testController = new ServerControllerSocket(
        new PlayerConnectorSocket(socket, new LinkedBlockingQueue<>()),
        new ServerControllerAction());
    assertNotNull(testController);
  }

  @Test
  void RUN_should_START_CONTROLLER() throws JsonIOException, JsonSyntaxException, IOException, InterruptedException {
    Socket mockSocket = Mockito.mock(Socket.class);
    StartGameCommand cmd = new StartGameCommand("test", 2);
    Player mockPlayer = Mockito.mock(Player.class);
    when(playerConnector.getPlayer()).thenReturn(mockPlayer);

    when(playerConnector.getConnector()).thenReturn(mockSocket);
    when(mockSocket.isConnected()).thenReturn(true, false);
    doReturn(cmd).when(controller).buildCommand();
    doNothing().when(controller).update();
    controller.run();

    verify(playerConnector, times(2)).getConnector();
    verify(mockSocket, times(2)).isConnected();
  }

  @Test
  void RUN_should_THROW_IOException() throws JsonIOException, JsonSyntaxException, IOException {
    Socket mockSocket = Mockito.mock(Socket.class);
    Player mockPlayer = Mockito.mock(Player.class);
    when(playerConnector.getPlayer()).thenReturn(mockPlayer);

    when(playerConnector.getConnector()).thenReturn(mockSocket);
    when(mockSocket.isConnected()).thenReturn(true, false);
    doThrow(IOException.class).when(controller).buildCommand();
    controller.run();

    verify(serverControllerAction, times(0)).execute(any(), any());
  }

  @Test
  void RUN_should_THROW_JSONException() throws JsonIOException, JsonSyntaxException, IOException {
    Socket mockSocket = Mockito.mock(Socket.class);
    Player mockPlayer = Mockito.mock(Player.class);
    when(playerConnector.getPlayer()).thenReturn(mockPlayer);
    when(playerConnector.getConnector()).thenReturn(mockSocket);
    when(mockSocket.isConnected()).thenReturn(true, true, false);
    doThrow(JsonIOException.class, JsonSyntaxException.class).when(controller).buildCommand();
    controller.run();

    verify(serverControllerAction, times(0)).execute(any(), any());
  }

  @Test
  void RUN_should_THROW_InterruptedException()
      throws JsonIOException, JsonSyntaxException, IOException, InterruptedException {
    Socket mockSocket = Mockito.mock(Socket.class);
    StartGameCommand cmd = new StartGameCommand("test", 2);
    Player mockPlayer = Mockito.mock(Player.class);
    when(playerConnector.getPlayer()).thenReturn(mockPlayer);

    when(playerConnector.getConnector()).thenReturn(mockSocket);
    when(mockSocket.isConnected()).thenReturn(true, false);
    doReturn(cmd).when(controller).buildCommand();
    doThrow(InterruptedException.class, JsonSyntaxException.class).when(controller).update();
    controller.run();

    verify(serverControllerAction, times(1)).execute(any(), any());
    assertFalse(Thread.currentThread().isInterrupted());
  }

  @Test
  void BUILD_START_COMMAND_should_BUILD_START_COMMAND() throws JsonIOException, JsonSyntaxException, IOException {
    Socket mockSocket = Mockito.mock(Socket.class);
    StartGameCommand cmd = new StartGameCommand("optimus prime", 4);
    Gson gson = new Gson();
    String json = gson.toJson(cmd);
    ByteArrayInputStream inputStream = new ByteArrayInputStream(json.getBytes());

    when(playerConnector.getConnector()).thenReturn(mockSocket);
    when(mockSocket.getInputStream()).thenReturn(inputStream);
    assertEquals(cmd, (StartGameCommand) controller.buildCommand());
  }

  @Test
  void UPDATE_should_SEND_UPDATE() throws JsonIOException, JsonSyntaxException, IOException, NullMaxPlayerException,
      InvalidMaxPlayerException, NullPlayerNameException, NullPlayerIdException, NullPlayerBookshelfException,
      NullPlayerScoreException, NullPlayerPrivateCardException, NullPlayerScoreBlocksException,
      DuplicatePlayerNameException, AlreadyInitiatedPatternException, NullPlayerNamesException,
      InvalidNumOfPlayersException, NullNumOfPlayersException, InterruptedException, NullAssignedPatternException,
      FullGameException, NotValidScoreBlockValueException, PlayerNotFoundException {
    Socket mockSocket = Mockito.mock(Socket.class);
    Game game = GameFactory.getNewGame("Steve", 4);
    VirtualView virtualView = new VirtualView(game);
    final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    GameMessage message = new GameMessage(virtualView);
    playerConnector.addMessageToQueue(message);

    when(playerConnector.getConnector()).thenReturn(mockSocket);
    when(playerConnector.getMessageFromQueue()).thenReturn(message);
    when(mockSocket.getOutputStream()).thenReturn(outputStream);
    controller.update();
    verify(playerConnector, times(1)).getMessageFromQueue();
    verify(playerConnector, times(2)).getConnector();
    verify(mockSocket, times(1)).getOutputStream();
  }

  @Test
  void BUILD_ADD_PLAYER_COMMAND_should_BUILD_ADD_PLAYER_COMMAND()
      throws JsonIOException, JsonSyntaxException, IOException {
    Socket mockSocket = Mockito.mock(Socket.class);
    AddPlayerCommand cmd = new AddPlayerCommand("optimus prime", UUID.randomUUID());
    Gson gson = new Gson();
    String json = gson.toJson(cmd);
    ByteArrayInputStream inputStream = new ByteArrayInputStream(json.getBytes());

    when(playerConnector.getConnector()).thenReturn(mockSocket);
    when(mockSocket.getInputStream()).thenReturn(inputStream);
    assertEquals(cmd, (AddPlayerCommand) controller.buildCommand());
  }

  @Test
  void MOVE_TILES_PLAYER_COMMAND_should_BUILD_MOVE_TILES_COMMAND()
      throws JsonIOException, JsonSyntaxException, IOException {
    Socket mockSocket = Mockito.mock(Socket.class);
    MoveTilesCommand cmd = new MoveTilesCommand("Steve", UUID.randomUUID(), new HashMap<>());
    Gson gson = new Gson();
    String json = gson.toJson(cmd);
    ByteArrayInputStream inputStream = new ByteArrayInputStream(json.getBytes());

    when(playerConnector.getConnector()).thenReturn(mockSocket);
    when(mockSocket.getInputStream()).thenReturn(inputStream);
    assertEquals(cmd, (MoveTilesCommand) controller.buildCommand());
  }

  @Test
  void GET_AVAILABLE_GAMES_COMMAND_should_GET_AVAILABLE_GAMES_COMMAND()
      throws JsonIOException, JsonSyntaxException, IOException {
    Socket mockSocket = Mockito.mock(Socket.class);
    GetAvailableGamesCommand cmd = new GetAvailableGamesCommand();
    Gson gson = new Gson();
    String json = gson.toJson(cmd);
    ByteArrayInputStream inputStream = new ByteArrayInputStream(json.getBytes());

    when(playerConnector.getConnector()).thenReturn(mockSocket);
    when(mockSocket.getInputStream()).thenReturn(inputStream);
    assertEquals(cmd, (GetAvailableGamesCommand) controller.buildCommand());
  }

  @Test
  void BUILD_COMMAND_should_THROW_JsonParseException_noClassName()
      throws JsonIOException, JsonSyntaxException, IOException {

    TestingPurposesClass f = new TestingPurposesClass();

    Socket mockSocket = Mockito.mock(Socket.class);
    Gson gson = new Gson();
    String json = gson.toJson(f);
    ByteArrayInputStream inputStream = new ByteArrayInputStream(json.getBytes());

    when(playerConnector.getConnector()).thenReturn(mockSocket);
    when(mockSocket.getInputStream()).thenReturn(inputStream);
    assertThrows(JsonParseException.class, () -> controller.buildCommand());
  }

  @Test
  void BUILD_COMMAND_should_THROW_JsonParseException_wrongClassName()
      throws JsonIOException, JsonSyntaxException, IOException {

    TestingPurposesClass2 f = new TestingPurposesClass2();

    Socket mockSocket = Mockito.mock(Socket.class);
    Gson gson = new Gson();
    String json = gson.toJson(f);
    ByteArrayInputStream inputStream = new ByteArrayInputStream(json.getBytes());

    when(playerConnector.getConnector()).thenReturn(mockSocket);
    when(mockSocket.getInputStream()).thenReturn(inputStream);
    assertThrows(JsonParseException.class, () -> controller.buildCommand());
  }

  @Test
  void DISCONNECTION_should_send_messages_to_players()
      throws NullMaxPlayerException, InvalidMaxPlayerException, NullPlayerNameException, NullPlayerIdException,
      NullPlayerBookshelfException, NullPlayerScoreException, NullPlayerPrivateCardException,
      NullPlayerScoreBlocksException, DuplicatePlayerNameException, AlreadyInitiatedPatternException,
      NullPlayerNamesException, InvalidNumOfPlayersException, NullNumOfPlayersException, NullAssignedPatternException,
      FullGameException, NotValidScoreBlockValueException, PlayerNotFoundException, IOException, InterruptedException,
      NullSocketConnectorException, NullBlockingQueueException, NullGameHandlerInstance {
    try (MockedStatic<ServerControllerState> utilities = Mockito.mockStatic(ServerControllerState.class)) {
      Socket mockSocket = Mockito.mock(Socket.class);
      PlayerConnectorSocket alivePlayerPC = new PlayerConnectorSocket(mockSocket, new LinkedBlockingQueue<>());
      Player mockPlayer = Mockito.mock(Player.class);
      Player mockAlivePlayer = Mockito.mock(Player.class);
      GameHandler mockGameHandler = Mockito.mock(GameHandler.class);
      utilities.when(() -> ServerControllerState.getGameHandlerByUUID(any()))
          .thenReturn(mockGameHandler);

      AbstractCommand mockCmd = Mockito.mock(AbstractCommand.class);
      when(playerConnector.getPlayer()).thenReturn(mockPlayer);
      when(mockPlayer.getPlayerName()).thenReturn("Steve");
      when(playerConnector.getConnector()).thenReturn(mockSocket);
      when(mockSocket.isConnected()).thenReturn(true, false, false);
      when(mockGameHandler.getPlayerConnectors()).thenReturn(Set.of(alivePlayerPC));
      alivePlayerPC.setPlayer(mockAlivePlayer);
      when(mockAlivePlayer.getPlayerName()).thenReturn("Alice");   
      doReturn(mockCmd).when(controller).buildCommand();
      doNothing().when(serverControllerAction).execute(any(), any());
      controller.run();

      assertFalse(mockPlayer.getIsConnected());
      assertEquals("Steve disconnected from the game.", alivePlayerPC.getMessageFromQueue().getMessage());
    }

  }
}
