package it.polimi.is23am10.server.controller;

import it.polimi.is23am10.server.command.AbstractCommand;
import it.polimi.is23am10.server.command.AddPlayerCommand;
import it.polimi.is23am10.server.command.StartGameCommand;
import it.polimi.is23am10.server.model.factory.GameFactory;
import it.polimi.is23am10.server.model.game.Game;
import it.polimi.is23am10.server.network.gamehandler.GameHandler;
import it.polimi.is23am10.server.network.gamehandler.exceptions.GameSnapshotUpdateException;
import it.polimi.is23am10.server.network.messages.AbstractMessage;
import it.polimi.is23am10.server.network.playerconnector.AbstractPlayerConnector;
import it.polimi.is23am10.server.network.playerconnector.PlayerConnectorSocket;
import it.polimi.is23am10.server.network.playerconnector.exceptions.NullBlockingQueueException;
import it.polimi.is23am10.server.network.playerconnector.exceptions.NullSocketConnectorException;
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
import it.polimi.is23am10.server.model.player.Player;
import it.polimi.is23am10.server.model.player.exceptions.NullPlayerBookshelfException;
import it.polimi.is23am10.server.model.player.exceptions.NullPlayerIdException;
import it.polimi.is23am10.server.model.player.exceptions.NullPlayerNameException;
import it.polimi.is23am10.server.model.player.exceptions.NullPlayerPrivateCardException;
import it.polimi.is23am10.server.model.player.exceptions.NullPlayerScoreBlocksException;
import it.polimi.is23am10.server.model.player.exceptions.NullPlayerScoreException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
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
class ClientConnectionCheckerTest {

  ClientConnectionChecker checker = new ClientConnectionChecker(1000);

  ServerControllerAction serverControllerAction = new ServerControllerAction();

  @BeforeEach
  void setup() {
    // if we don't call below, we will get NullPointerException
    MockitoAnnotations.initMocks(this);
    ServerControllerState.getGamePools().clear();
    ServerControllerState.getPlayersPool().clear();
  }

  @Test
  void CHECK_ALL_PLAYERS_CONNECTION_should_PERFORM_CONNECTION_CHECK() throws NullSocketConnectorException, NullBlockingQueueException, GameSnapshotUpdateException {
    Socket socket = new Socket();
    PlayerConnectorSocket playerConnector = new PlayerConnectorSocket(socket, new LinkedBlockingQueue<>());
    AbstractCommand cmd = new StartGameCommand("player", 2);

    serverControllerAction.execute(playerConnector, cmd);
    assertTrue(playerConnector.getPlayer().getIsConnected());
    checker.checkAllPlayers();
    assertTrue(playerConnector.getPlayer().getIsConnected());

    playerConnector.setLastSnoozeMs(playerConnector.getLastSnoozeMs() - (1000 * 60 * 2));
    checker.checkAllPlayers();
    assertFalse(playerConnector.getPlayer().getIsConnected());
  }

  @Test
  void CHECK_ACTIVE_PLAYERS_ACTIVITY_should_PERFORM_EXPIRATION_CHECK()
      throws NullSocketConnectorException, NullBlockingQueueException,
      NullMaxPlayerException, InvalidMaxPlayerException, NullPlayerNameException,
      NullPlayerIdException, NullPlayerBookshelfException, NullPlayerScoreException,
      NullPlayerPrivateCardException, NullPlayerScoreBlocksException,DuplicatePlayerNameException,
      AlreadyInitiatedPatternException, NullPlayerNamesException, InvalidNumOfPlayersException,
      NullNumOfPlayersException, NullAssignedPatternException, FullGameException,
      PlayerNotFoundException, NotValidScoreBlockValueException, InterruptedException, GameSnapshotUpdateException {

    AbstractPlayerConnector steve = new PlayerConnectorSocket(new Socket(), new LinkedBlockingQueue<>());
    AbstractPlayerConnector mike = new PlayerConnectorSocket(new Socket(), new LinkedBlockingQueue<>());
    AbstractPlayerConnector alice = new PlayerConnectorSocket(new Socket(), new LinkedBlockingQueue<>());

    serverControllerAction.execute(steve, new StartGameCommand("Steve", 3));
    GameHandler gh = ServerControllerState.getGamePools().iterator().next();
    serverControllerAction.execute(mike, new AddPlayerCommand("Mike", gh.getGame().getGameId()));
    serverControllerAction.execute(alice, new AddPlayerCommand("Alice", gh.getGame().getGameId()));

    AbstractPlayerConnector playing = gh.getGame().getActivePlayer().equals(steve.getPlayer())
      ? steve : gh.getGame().getActivePlayer().equals(mike.getPlayer()) ? mike : alice;

    int size = playing.getMsgQueueSize();

    assertTrue(playing.getPlayer().getIsConnected());
    Thread.sleep(1000);
    checker.checkActivePlayersInactivity();
    //client has been notified
    assertTrue(playing.getPlayer().getIsConnected());
    assertEquals(size+1, playing.getMsgQueueSize());
    Thread.sleep(1000);
    checker.checkActivePlayersInactivity();
    assertFalse(playing.getPlayer().getIsConnected());
    //client has been notified and disconnected
    assertEquals(size+2, playing.getMsgQueueSize());

    //check new active player
    Player newActive = gh.getGame().getActivePlayer();
    assertNotEquals(newActive.getPlayerName(), playing.getPlayer().getPlayerName());
  }
}
