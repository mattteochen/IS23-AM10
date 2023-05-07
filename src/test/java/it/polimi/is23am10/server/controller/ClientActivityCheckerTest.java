package it.polimi.is23am10.server.controller;

import it.polimi.is23am10.server.command.AbstractCommand;
import it.polimi.is23am10.server.command.StartGameCommand;
import it.polimi.is23am10.server.network.playerconnector.PlayerConnectorSocket;
import it.polimi.is23am10.server.network.playerconnector.exceptions.NullBlockingQueueException;
import it.polimi.is23am10.server.network.playerconnector.exceptions.NullSocketConnectorException;

import static org.junit.Assert.assertTrue;

import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
@SuppressWarnings({ "deprecation", "checkstyle:methodname", "checkstyle:abbreviationaswordinnamecheck",
    "checkstyle:linelengthcheck" })
class ClientActivityCheckerTest {

  ClientActivityChecker checker = new ClientActivityChecker();

  ServerControllerAction serverControllerAction = new ServerControllerAction();

  @BeforeEach
  void setup() {
    // if we don't call below, we will get NullPointerException
    MockitoAnnotations.initMocks(this);
    ServerControllerState.getGamePools().clear();
    ServerControllerState.getPlayersPool().clear();
  }

  @Test
  void CHECK_ALL_PLAYERS_should_PERFORM_EXPIRATION_CHECK() throws NullSocketConnectorException, NullBlockingQueueException {
    Socket socket = new Socket();
    PlayerConnectorSocket playerConnector = new PlayerConnectorSocket(socket, new LinkedBlockingQueue<>());
    AbstractCommand cmd = new StartGameCommand("player", 2);

    serverControllerAction.execute(playerConnector, cmd);
    assertTrue(playerConnector.getPlayer().getIsConnected());
    checker.checkAllPlayers();
    assertTrue(playerConnector.getPlayer().getIsConnected());

    playerConnector.setLastSnoozeMs(playerConnector.getLastSnoozeMs() - (1000 * 60 * 3));
    checker.checkAllPlayers();
    assertTrue(!playerConnector.getPlayer().getIsConnected());
  }
}
