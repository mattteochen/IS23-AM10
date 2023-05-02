package it.polimi.is23am10.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.LinkedBlockingQueue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import com.google.gson.Gson;

import it.polimi.is23am10.client.userinterface.CommandLineInterface;
import it.polimi.is23am10.client.userinterface.UserInterface;
import it.polimi.is23am10.server.model.player.Player;
import it.polimi.is23am10.server.network.messages.AbstractMessage;
import it.polimi.is23am10.server.network.messages.ChatMessage;
import it.polimi.is23am10.server.network.messages.ErrorMessage;
import it.polimi.is23am10.server.network.messages.GameMessage;
import it.polimi.is23am10.server.network.messages.ErrorMessage.ErrorSeverity;
import it.polimi.is23am10.server.network.playerconnector.PlayerConnectorSocket;
import it.polimi.is23am10.server.network.playerconnector.exceptions.NullBlockingQueueException;
import it.polimi.is23am10.server.network.playerconnector.exceptions.NullSocketConnectorException;
import it.polimi.is23am10.server.network.virtualview.VirtualView;

@Disabled
public class SocketClientTest {

  @Mock
  PlayerConnectorSocket playerConnectorSocket;

  @Mock
  InetAddress serverAddress;

  @Mock
  VirtualView virtualView;

  @Mock
  UserInterface userInterface;

  @Spy
  @InjectMocks
  SocketClient socketClient;

  @BeforeEach
  public void setup() {
    // if we don't call below, we will get NullPointerException
    MockitoAnnotations.initMocks(this);
  }

  @Test
  void constructor_should_create_SocketClientTest()
      throws NullSocketConnectorException, NullBlockingQueueException, UnknownHostException {
    Socket socket = new Socket();
    PlayerConnectorSocket pc = new PlayerConnectorSocket(socket, new LinkedBlockingQueue<>());
    UserInterface ui = new CommandLineInterface(true);

    Client socketClient = new SocketClient(pc, ui);

    assertNotNull(socketClient);
  }

  @Test
  void run_should_runCoreCycle() throws IOException {
    Socket mockSocket = Mockito.mock(Socket.class);
    AbstractMessage msg = new ErrorMessage("New Message", null);

    when(playerConnectorSocket.getConnector()).thenReturn(mockSocket);
    when(mockSocket.isConnected()).thenReturn(true, false);
    doReturn(msg).when(socketClient).parseServerMessage(playerConnectorSocket);
    doNothing().when(socketClient).showServerMessage(msg);

    socketClient.run();

    verify(playerConnectorSocket, times(2)).getConnector();
    verify(mockSocket, times(2)).isConnected();
  }

  @Test
  void run_should_throwIOException() throws IOException {
    Socket mockSocket = Mockito.mock(Socket.class);

    when(playerConnectorSocket.getConnector()).thenReturn(mockSocket);
    when(mockSocket.isConnected()).thenReturn(true, false);
    doThrow(IOException.class).when(socketClient).parseServerMessage(playerConnectorSocket);

    socketClient.run();

    verify(playerConnectorSocket, times(2)).getConnector();
    verify(mockSocket, times(2)).isConnected();
  }

  @Test
  void parseServerMessage_should_parseMessage() throws IOException {
    Player p = Mockito.mock(Player.class);
    Socket mockSocket = Mockito.mock(Socket.class);
    AbstractMessage msg = new ErrorMessage("New Message", p, ErrorSeverity.WARNING);
    Gson gson = new Gson();
    String json = gson.toJson(msg);
    ByteArrayInputStream inputStream = new ByteArrayInputStream(json.getBytes());

    when(playerConnectorSocket.getConnector()).thenReturn(mockSocket);
    when(mockSocket.getInputStream()).thenReturn(inputStream);

    // being lazy: there is no equals overriden
    AbstractMessage parsedMsg = socketClient.parseServerMessage(playerConnectorSocket);
    assertEquals(parsedMsg.getMessage(), msg.getMessage());
  }

  // correctenss of the below tests must be handled by the implementer class
  @Test
  void showServerMessage_should_showErrorMessage() {
    ErrorMessage msg = new ErrorMessage("New Message", null);

    doNothing().when(userInterface).displayError(msg);
    socketClient.showServerMessage(msg);

    verify(userInterface, times(1)).displayError(msg);
  }

  @Test
  void showServerMessage_should_showChatMessage() {
    ChatMessage msg = new ChatMessage(null, "Let's rewrite this in Golang", null);

    doNothing().when(userInterface).displayChatMessage(msg);
    socketClient.showServerMessage(msg);

    verify(userInterface, times(1)).displayChatMessage(msg);
  }

  @Test
  void showServerMessage_should_gameSnapShot() {
    AbstractMessage msg = new GameMessage(null);

    doNothing().when(userInterface).displayVirtualView(null);
    socketClient.showServerMessage(msg);

    verify(userInterface, times(1)).displayVirtualView(null);
  }
}
