package it.polimi.is23am10.controller;

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
import it.polimi.is23am10.command.AddPlayerCommand;
import it.polimi.is23am10.command.StartGameCommand;
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
import it.polimi.is23am10.playerconnector.PlayerConnector;
import it.polimi.is23am10.playerconnector.exceptions.NullBlockingQueueException;
import it.polimi.is23am10.playerconnector.exceptions.NullSocketConnectorException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingQueue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

@SuppressWarnings({"checkstyle:methodname", "checkstyle:abbreviationaswordinnamecheck", "checkstyle:linelengthcheck", "checkstyle:onetoplevelclasscheck", "checkstyle:membernamecheck", "checkstyle:OuterTypeFilenameCheck"})
class TestingPurposesClass {
  String s = "Before you marry a person, you should first make them use a computer with slow Internet to see who they really are";
}

@SuppressWarnings({"checkstyle:methodname", "checkstyle:abbreviationaswordinnamecheck", "checkstyle:linelengthcheck", "checkstyle:onetoplevelclasscheck"})
class TestingPurposesClass2 {
  String className = "I never forget a face but in your case, I’ll be glad to make an exception";
}

@RunWith(MockitoJUnitRunner.class)
@SuppressWarnings({"deprecation", "unchecked", "checkstyle:methodname", "checkstyle:abbreviationaswordinnamecheck", "checkstyle:linelengthcheck", "checkstyle:onetoplevelclasscheck"})
class ServerControllerTest {

  @Mock
  PlayerConnector playerConnector;

  @Mock
  ServerControllerAction serverControllerAction;

  @Spy
  @InjectMocks
  ServerController controller;

  @BeforeEach
  void setup() {
    // if we don't call below, we will get NullPointerException
    MockitoAnnotations.initMocks(this);
  }

  @Test
  void CONSTRUCTOR_should_BUILD_OBJECT() throws NullSocketConnectorException, NullBlockingQueueException {
    Socket socket = new Socket();
    ServerController testController = new ServerController(new PlayerConnector(socket, new LinkedBlockingQueue<>()),
        new ServerControllerAction());
    assertNotNull(testController);
  }

  @Test
  void RUN_should_START_CONTROLLER() throws JsonIOException, JsonSyntaxException, IOException, InterruptedException {
    Socket mockSocket = Mockito.mock(Socket.class);
    StartGameCommand cmd = new StartGameCommand("test", 2);

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

    when(playerConnector.getConnector()).thenReturn(mockSocket);
    when(mockSocket.isConnected()).thenReturn(true, false);
    doThrow(IOException.class).when(controller).buildCommand();
    controller.run();

    verify(serverControllerAction, times(0)).execute(any(), any());
  }

  @Test
  void RUN_should_THROW_JSONException() throws JsonIOException, JsonSyntaxException, IOException {
    Socket mockSocket = Mockito.mock(Socket.class);

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
      InvalidNumOfPlayersException, NullNumOfPlayersException, InterruptedException, NullAssignedPatternException {
    Socket mockSocket = Mockito.mock(Socket.class);
    Game game = GameFactory.getNewGame("Steve", 4);
    final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    playerConnector.addMessageToQueue(game);

    when(playerConnector.getConnector()).thenReturn(mockSocket);
    when(playerConnector.getMessageFromQueue()).thenReturn(Optional.of(game));
    when(mockSocket.getOutputStream()).thenReturn(outputStream);
    controller.update();
    verify(playerConnector, times(1)).getMessageFromQueue();
    verify(playerConnector, times(1)).getConnector();
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
}
