package it.polimi.is23am10.controller;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import it.polimi.is23am10.command.StartGameCommand;
import it.polimi.is23am10.playerconnector.PlayerConnector;
import it.polimi.is23am10.playerconnector.exceptions.NullSocketConnectorException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.Socket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

class TestingPurposesClass {
  String s = "Before you marry a person, you should first make them use a computer with slow Internet to see who they really are";
}

class TestingPurposesClass2 {
  String className = "I never forget a face but in your case, I’ll be glad to make an exception";
}

@RunWith(MockitoJUnitRunner.class)
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
  void CONSTRUCTOR_green_path() throws NullSocketConnectorException {
    Socket socket = new Socket();
    ServerController testController =
        new ServerController(new PlayerConnector(socket), new ServerControllerAction());
    assertNotNull(testController);
  }

  @Test
  void RUN_green_path() throws JsonIOException, JsonSyntaxException, IOException {
    Socket mockSocket = Mockito.mock(Socket.class);
    StartGameCommand cmd = new StartGameCommand("test", 2);

    when(playerConnector.getConnector()).thenReturn(mockSocket);
    when(mockSocket.isConnected()).thenReturn(true, false);
    doReturn(cmd).when(controller).buildCommand();
    controller.run();

    verify(playerConnector, times(2)).getConnector();
    verify(mockSocket, times(2)).isConnected();
  }

  @Test
  void RUN_throws_IOException() throws JsonIOException, JsonSyntaxException, IOException {
    Socket mockSocket = Mockito.mock(Socket.class);

    when(playerConnector.getConnector()).thenReturn(mockSocket);
    when(mockSocket.isConnected()).thenReturn(true, false);
    doThrow(IOException.class).when(controller).buildCommand();
    controller.run();

    verify(serverControllerAction, times(0)).execute(any(), any());
  }

  @Test
  void RUN_throws_JSONException() throws JsonIOException, JsonSyntaxException, IOException {
    Socket mockSocket = Mockito.mock(Socket.class);

    when(playerConnector.getConnector()).thenReturn(mockSocket);
    when(mockSocket.isConnected()).thenReturn(true, true, false);
    doThrow(JsonIOException.class, JsonSyntaxException.class).when(controller).buildCommand();
    controller.run();

    verify(serverControllerAction, times(0)).execute(any(), any());
  }

  @Test
  void BUILD_COMMAND_green_path() throws JsonIOException, JsonSyntaxException, IOException {
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
  void BUILD_COMMAND_throws_JsonParseException_noClassName() throws JsonIOException, JsonSyntaxException, IOException {

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
  void BUILD_COMMAND_throws_JsonParseException_wrongClassName() throws JsonIOException, JsonSyntaxException, IOException {

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
