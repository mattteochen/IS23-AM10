package it.polimi.is23am10.controller;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import it.polimi.is23am10.command.AbstractCommand;
import it.polimi.is23am10.command.StartGameCommand;
import it.polimi.is23am10.command.AbstractCommand.Opcode;
import it.polimi.is23am10.controller.exceptions.StartCommandSerializationErrorException;
import it.polimi.is23am10.playerconnector.PlayerConnector;
import it.polimi.is23am10.playerconnector.exceptions.NullSocketConnectorException;
import java.net.Socket;
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

    assertEquals(0, ServerControllerState.getGamePools().size());
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
}
