package it.polimi.is23am10.server.controller;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import it.polimi.is23am10.server.controller.exceptions.NullGameHandlerInstance;
import it.polimi.is23am10.server.model.factory.GameFactory;
import it.polimi.is23am10.server.model.factory.PlayerFactory;
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
import it.polimi.is23am10.server.model.player.exceptions.NullPlayerBookshelfException;
import it.polimi.is23am10.server.model.player.exceptions.NullPlayerIdException;
import it.polimi.is23am10.server.model.player.exceptions.NullPlayerNameException;
import it.polimi.is23am10.server.model.player.exceptions.NullPlayerPrivateCardException;
import it.polimi.is23am10.server.model.player.exceptions.NullPlayerScoreBlocksException;
import it.polimi.is23am10.server.model.player.exceptions.NullPlayerScoreException;
import it.polimi.is23am10.server.network.gamehandler.GameHandler;
import it.polimi.is23am10.server.network.gamehandler.exceptions.NullPlayerConnector;
import it.polimi.is23am10.server.network.playerconnector.PlayerConnectorSocket;
import it.polimi.is23am10.server.network.playerconnector.exceptions.NullBlockingQueueException;
import it.polimi.is23am10.server.network.playerconnector.exceptions.NullSocketConnectorException;
import java.net.Socket;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingQueue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
@SuppressWarnings({
  "checkstyle:methodname",
  "checkstyle:abbreviationaswordinnamecheck",
  "checkstyle:linelengthcheck"
})
class ServerControllerStateTest {

  Game mockGame;

  @BeforeEach
  void setup()
      throws NullMaxPlayerException,
          InvalidMaxPlayerException,
          NullPlayerNameException,
          NullPlayerIdException,
          NullPlayerBookshelfException,
          NullPlayerScoreException,
          NullPlayerPrivateCardException,
          NullPlayerScoreBlocksException,
          DuplicatePlayerNameException,
          AlreadyInitiatedPatternException,
          NullPlayerNamesException,
          InvalidNumOfPlayersException,
          NullNumOfPlayersException,
          NullAssignedPatternException,
          FullGameException,
          NotValidScoreBlockValueException,
          PlayerNotFoundException {
    ServerControllerState.getGamePools().clear();
    ServerControllerState.getPlayersPool().clear();
    mockGame = GameFactory.getNewGame("Max", 4);
  }

  @Test
  void ADD_GAME_HANDLER_should_ADD_NEW_GAME()
      throws NullMaxPlayerException,
          InvalidMaxPlayerException,
          NullPlayerNameException,
          NullPlayerIdException,
          NullPlayerBookshelfException,
          NullPlayerScoreException,
          NullPlayerPrivateCardException,
          NullPlayerScoreBlocksException,
          DuplicatePlayerNameException,
          AlreadyInitiatedPatternException,
          NullPlayerNamesException,
          InvalidNumOfPlayersException,
          NullNumOfPlayersException,
          NullGameHandlerInstance,
          NullAssignedPatternException,
          FullGameException,
          NotValidScoreBlockValueException,
          PlayerNotFoundException {

    ServerControllerState.addGameHandler(new GameHandler("Steve", 2));

    assertEquals(1, ServerControllerState.getGamePools().size());
  }

  @Test
  void ADD_GAME_HANDLER_should_NOT_HAVE_DUPLICATES()
      throws NullMaxPlayerException,
          InvalidMaxPlayerException,
          NullPlayerNameException,
          NullPlayerIdException,
          NullPlayerBookshelfException,
          NullPlayerScoreException,
          NullPlayerPrivateCardException,
          NullPlayerScoreBlocksException,
          DuplicatePlayerNameException,
          AlreadyInitiatedPatternException,
          NullPlayerNamesException,
          InvalidNumOfPlayersException,
          NullNumOfPlayersException,
          NullGameHandlerInstance,
          NullAssignedPatternException,
          FullGameException,
          NotValidScoreBlockValueException,
          PlayerNotFoundException {

    GameHandler handler = new GameHandler("Steve", 2);
    ServerControllerState.addGameHandler(handler);
    ServerControllerState.addGameHandler(handler);
    ServerControllerState.addGameHandler(new GameHandler("Steve", 4));
    assertEquals(2, ServerControllerState.getGamePools().size());
  }

  @Test
  void ADD_GAME_HANDLER_should_THROW_NullGameHandlerInstance()
      throws NullMaxPlayerException,
          InvalidMaxPlayerException,
          NullPlayerNameException,
          NullPlayerIdException,
          NullPlayerBookshelfException,
          NullPlayerScoreException,
          NullPlayerPrivateCardException,
          NullPlayerScoreBlocksException,
          DuplicatePlayerNameException,
          AlreadyInitiatedPatternException,
          NullPlayerNamesException,
          InvalidNumOfPlayersException,
          NullNumOfPlayersException,
          NullGameHandlerInstance,
          FullGameException {

    assertThrows(NullGameHandlerInstance.class, () -> ServerControllerState.addGameHandler(null));
    assertEquals(0, ServerControllerState.getGamePools().size());
  }

  @Test
  void REMOVE_GAME_HANDLER_should_REMOVE_GAME_INSTANCE()
      throws NullMaxPlayerException,
          InvalidMaxPlayerException,
          NullPlayerNameException,
          NullPlayerIdException,
          NullPlayerBookshelfException,
          NullPlayerScoreException,
          NullPlayerPrivateCardException,
          NullPlayerScoreBlocksException,
          DuplicatePlayerNameException,
          AlreadyInitiatedPatternException,
          NullPlayerNamesException,
          InvalidNumOfPlayersException,
          NullNumOfPlayersException,
          NullGameHandlerInstance,
          NullAssignedPatternException,
          FullGameException,
          NotValidScoreBlockValueException,
          PlayerNotFoundException {

    final GameHandler handler = new GameHandler("Steve", 2);
    final GameHandler handler2 = new GameHandler("Luke", 2);
    ServerControllerState.addGameHandler(handler);

    assertEquals(1, ServerControllerState.getGamePools().size());
    ServerControllerState.removeGameHandlerById(null);
    assertEquals(1, ServerControllerState.getGamePools().size());
    ServerControllerState.removeGameHandlerById(handler2.getGame().getGameId());
    assertEquals(1, ServerControllerState.getGamePools().size());
    ServerControllerState.removeGameHandlerById(handler.getGame().getGameId());
    assertEquals(0, ServerControllerState.getGamePools().size());
  }

  @Test
  void ADD_PLAYER_CONNECTOR_should_THROW_NullGameHandlerInstance()
      throws NullMaxPlayerException,
          InvalidMaxPlayerException,
          NullPlayerNameException,
          NullPlayerIdException,
          NullPlayerBookshelfException,
          NullPlayerScoreException,
          NullPlayerPrivateCardException,
          NullPlayerScoreBlocksException,
          DuplicatePlayerNameException,
          AlreadyInitiatedPatternException,
          NullPlayerNamesException,
          InvalidNumOfPlayersException,
          NullNumOfPlayersException,
          NullGameHandlerInstance,
          NullSocketConnectorException,
          NullPlayerConnector,
          NullBlockingQueueException,
          FullGameException,
          NullAssignedPatternException {

    PlayerConnectorSocket playerConnector =
        new PlayerConnectorSocket(new Socket(), new LinkedBlockingQueue<>());
    playerConnector.setPlayer(PlayerFactory.getNewPlayer("Steve", mockGame));
    playerConnector.setGameId(UUID.randomUUID());
    ServerControllerState.addPlayerConnector(playerConnector);
    assertEquals(1, ServerControllerState.getPlayersPool().size());
  }

  @Test
  void ADD_PLAYER_CONNECTOR_should_THROW_DuplicatePlayerNameException()
      throws NullMaxPlayerException,
          InvalidMaxPlayerException,
          NullPlayerNameException,
          NullPlayerIdException,
          NullPlayerBookshelfException,
          NullPlayerScoreException,
          NullPlayerPrivateCardException,
          NullPlayerScoreBlocksException,
          DuplicatePlayerNameException,
          AlreadyInitiatedPatternException,
          NullPlayerNamesException,
          InvalidNumOfPlayersException,
          NullNumOfPlayersException,
          NullGameHandlerInstance,
          NullSocketConnectorException,
          NullPlayerConnector,
          NullBlockingQueueException,
          FullGameException,
          NullAssignedPatternException {

    PlayerConnectorSocket playerConnector =
        new PlayerConnectorSocket(new Socket(), new LinkedBlockingQueue<>());
    playerConnector.setPlayer(PlayerFactory.getNewPlayer("Steve", mockGame));
    playerConnector.setGameId(UUID.randomUUID());
    ServerControllerState.addPlayerConnector(playerConnector);
    assertThrows(
        DuplicatePlayerNameException.class,
        () -> ServerControllerState.addPlayerConnector(playerConnector));
    assertEquals(1, ServerControllerState.getPlayersPool().size());
  }

  @Test
  void ADD_PLAYER_CONNECTOR_should_THROW_NullPlayerConnector()
      throws NullMaxPlayerException,
          InvalidMaxPlayerException,
          NullPlayerNameException,
          NullPlayerIdException,
          NullPlayerBookshelfException,
          NullPlayerScoreException,
          NullPlayerPrivateCardException,
          NullPlayerScoreBlocksException,
          DuplicatePlayerNameException,
          AlreadyInitiatedPatternException,
          NullPlayerNamesException,
          InvalidNumOfPlayersException,
          NullNumOfPlayersException,
          NullGameHandlerInstance,
          NullSocketConnectorException,
          NullPlayerConnector,
          FullGameException {

    assertThrows(NullPlayerConnector.class, () -> ServerControllerState.addPlayerConnector(null));
    assertEquals(0, ServerControllerState.getPlayersPool().size());
  }

  @Test
  void REMOVE_PLAYER_CONNECTOR_should_REMOVE_PLAYER_CONNECTOR()
      throws NullMaxPlayerException,
          InvalidMaxPlayerException,
          NullPlayerNameException,
          NullPlayerIdException,
          NullPlayerBookshelfException,
          NullPlayerScoreException,
          NullPlayerPrivateCardException,
          NullPlayerScoreBlocksException,
          DuplicatePlayerNameException,
          AlreadyInitiatedPatternException,
          NullPlayerNamesException,
          InvalidNumOfPlayersException,
          NullNumOfPlayersException,
          NullGameHandlerInstance,
          NullSocketConnectorException,
          NullPlayerConnector,
          NullBlockingQueueException,
          NullAssignedPatternException,
          FullGameException,
          PlayerNotFoundException,
          NotValidScoreBlockValueException {

    GameHandler handler = new GameHandler("Steve", 2);
    PlayerConnectorSocket steve =
        new PlayerConnectorSocket(new Socket(), new LinkedBlockingQueue<>());
    steve.setPlayer(handler.getGame().getPlayerByName("Steve"));
    PlayerConnectorSocket alice =
        new PlayerConnectorSocket(new Socket(), new LinkedBlockingQueue<>());
    alice.setPlayer(PlayerFactory.getNewPlayer("Alice", handler.getGame()));
    steve.setGameId(handler.getGame().getGameId());
    steve.setPlayer(PlayerFactory.getNewPlayer("Steve", mockGame));
    alice.setGameId(handler.getGame().getGameId());
    alice.setPlayer(PlayerFactory.getNewPlayer("Alice", mockGame));
    ServerControllerState.addPlayerConnector(steve);

    assertEquals(1, ServerControllerState.getPlayersPool().size());
    ServerControllerState.removePlayerByGame(handler.getGame().getGameId(), alice.getPlayer());
    assertEquals(1, ServerControllerState.getPlayersPool().size());
    ServerControllerState.removePlayerByGame(null, null);
    assertEquals(1, ServerControllerState.getPlayersPool().size());
    ServerControllerState.removePlayerByGame(handler.getGame().getGameId(), steve.getPlayer());
    assertEquals(0, ServerControllerState.getPlayersPool().size());
  }

  @Test
  void REMOVE_GAME_HANDLER_PLAYER_CONNECTORS_should_REMOVE_PLAYER_CONNECTOR_IN_GAME_HANDLER()
      throws NullMaxPlayerException,
          InvalidMaxPlayerException,
          NullPlayerNameException,
          NullPlayerIdException,
          NullPlayerBookshelfException,
          NullPlayerScoreException,
          NullPlayerPrivateCardException,
          NullPlayerScoreBlocksException,
          DuplicatePlayerNameException,
          AlreadyInitiatedPatternException,
          NullPlayerNamesException,
          InvalidNumOfPlayersException,
          NullNumOfPlayersException,
          NullGameHandlerInstance,
          NullSocketConnectorException,
          NullPlayerConnector,
          NullBlockingQueueException,
          NullAssignedPatternException,
          FullGameException,
          NotValidScoreBlockValueException,
          PlayerNotFoundException {

    GameHandler handler = new GameHandler("Steve", 2);
    GameHandler handler2 = new GameHandler("Bob", 2);
    PlayerConnectorSocket steve =
        new PlayerConnectorSocket(new Socket(), new LinkedBlockingQueue<>());
    steve.setPlayer(PlayerFactory.getNewPlayer("Steve", mockGame));
    steve.setGameId(UUID.randomUUID());
    PlayerConnectorSocket alice =
        new PlayerConnectorSocket(new Socket(), new LinkedBlockingQueue<>());
    alice.setPlayer(PlayerFactory.getNewPlayer("Alice", mockGame));
    alice.setGameId(UUID.randomUUID());
    PlayerConnectorSocket bob =
        new PlayerConnectorSocket(new Socket(), new LinkedBlockingQueue<>());
    bob.setPlayer(PlayerFactory.getNewPlayer("Bob", mockGame));
    bob.setGameId(UUID.randomUUID());
    handler.addPlayerConnector(steve);
    handler.addPlayerConnector(alice);
    handler2.addPlayerConnector(bob);
    steve.setGameId(handler.getGame().getGameId());
    steve.getPlayer().setPlayerName("Steve");
    alice.setGameId(handler.getGame().getGameId());
    alice.getPlayer().setPlayerName("Alice");
    bob.setGameId(handler.getGame().getGameId());
    bob.getPlayer().setPlayerName("Bob");
    ServerControllerState.addGameHandler(handler);
    ServerControllerState.addGameHandler(handler2);
    ServerControllerState.addPlayerConnector(steve);
    ServerControllerState.addPlayerConnector(alice);
    ServerControllerState.addPlayerConnector(bob);

    assertEquals(2, ServerControllerState.getGamePools().size());
    assertEquals(3, ServerControllerState.getPlayersPool().size());
    ServerControllerState.removeGameHandlerById(handler.getGame().getGameId());
    assertEquals(1, ServerControllerState.getGamePools().size());
    assertEquals(1, ServerControllerState.getPlayersPool().size());
  }

  @Test
  public void getGameHandlerByUUID_should_throw_NullGameHandlerInstance() {
    assertThrows(
        NullGameHandlerInstance.class,
        () -> ServerControllerState.getGameHandlerByUUID(UUID.randomUUID()));
  }

  @Test
  public void getGameHandlerByUUID_should_return_handler()
      throws NullMaxPlayerException,
          InvalidMaxPlayerException,
          NullPlayerNameException,
          NullPlayerIdException,
          NullPlayerBookshelfException,
          NullPlayerScoreException,
          NullPlayerPrivateCardException,
          NullPlayerScoreBlocksException,
          DuplicatePlayerNameException,
          AlreadyInitiatedPatternException,
          NullPlayerNamesException,
          InvalidNumOfPlayersException,
          NullNumOfPlayersException,
          NullAssignedPatternException,
          NullGameHandlerInstance,
          FullGameException,
          NotValidScoreBlockValueException,
          PlayerNotFoundException {

    GameHandler handler = new GameHandler("Steve", 2);
    ServerControllerState.addGameHandler(handler);
    GameHandler returnedHandler =
        ServerControllerState.getGameHandlerByUUID(handler.getGame().getGameId());
    assertNotNull(returnedHandler);
    assertEquals(handler, returnedHandler);
  }
}
