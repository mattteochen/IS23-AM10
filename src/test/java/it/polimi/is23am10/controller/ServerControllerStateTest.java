package it.polimi.is23am10.controller;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import it.polimi.is23am10.controller.exceptions.NullGameHandlerInstance;
import it.polimi.is23am10.factory.PlayerFactory;
import it.polimi.is23am10.factory.exceptions.DuplicatePlayerNameException;
import it.polimi.is23am10.factory.exceptions.NullPlayerNamesException;
import it.polimi.is23am10.game.exceptions.InvalidMaxPlayerException;
import it.polimi.is23am10.game.exceptions.NullAssignedPatternException;
import it.polimi.is23am10.game.exceptions.NullMaxPlayerException;
import it.polimi.is23am10.gamehandler.GameHandler;
import it.polimi.is23am10.gamehandler.exceptions.NullPlayerConnector;
import it.polimi.is23am10.items.board.exceptions.InvalidNumOfPlayersException;
import it.polimi.is23am10.items.board.exceptions.NullNumOfPlayersException;
import it.polimi.is23am10.items.card.exceptions.AlreadyInitiatedPatternException;
import it.polimi.is23am10.player.Player;
import it.polimi.is23am10.player.exceptions.NullPlayerBookshelfException;
import it.polimi.is23am10.player.exceptions.NullPlayerIdException;
import it.polimi.is23am10.player.exceptions.NullPlayerNameException;
import it.polimi.is23am10.player.exceptions.NullPlayerPrivateCardException;
import it.polimi.is23am10.player.exceptions.NullPlayerScoreBlocksException;
import it.polimi.is23am10.player.exceptions.NullPlayerScoreException;
import it.polimi.is23am10.playerconnector.PlayerConnector;
import it.polimi.is23am10.playerconnector.exceptions.NullBlockingQueueException;
import it.polimi.is23am10.playerconnector.exceptions.NullSocketConnectorException;
import it.polimi.is23am10.game.exceptions.FullGameException;

import java.net.Socket;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingQueue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
@SuppressWarnings({ "checkstyle:methodname", "checkstyle:abbreviationaswordinnamecheck", "checkstyle:linelengthcheck" })
class ServerControllerStateTest {

  @BeforeEach
  void setup() {
    ServerControllerState.getGamePools().clear();
    ServerControllerState.getPlayersPool().clear();
  }

  @Test
  void ADD_GAME_HANDLER_should_ADD_NEW_GAME()
      throws NullMaxPlayerException, InvalidMaxPlayerException, NullPlayerNameException,
      NullPlayerIdException, NullPlayerBookshelfException, NullPlayerScoreException, NullPlayerPrivateCardException,
      NullPlayerScoreBlocksException, DuplicatePlayerNameException, AlreadyInitiatedPatternException,
      NullPlayerNamesException, InvalidNumOfPlayersException, NullNumOfPlayersException, NullGameHandlerInstance,
      NullAssignedPatternException, FullGameException {

    ServerControllerState.addGameHandler(new GameHandler("Steve", 2));

    assertEquals(1, ServerControllerState.getGamePools().size());
  }

  @Test
  void ADD_GAME_HANDLER_should_THROW_NullGameHandlerInstance()
      throws NullMaxPlayerException, InvalidMaxPlayerException, NullPlayerNameException,
      NullPlayerIdException, NullPlayerBookshelfException, NullPlayerScoreException, NullPlayerPrivateCardException,
      NullPlayerScoreBlocksException, DuplicatePlayerNameException, AlreadyInitiatedPatternException,
      NullPlayerNamesException, InvalidNumOfPlayersException, NullNumOfPlayersException, NullGameHandlerInstance, FullGameException {

    assertThrows(NullGameHandlerInstance.class, () -> ServerControllerState.addGameHandler(null));
    assertEquals(0, ServerControllerState.getGamePools().size());
  }

  @Test
  void REMOVE_GAME_HANDLER_should_REMOVE_GAME_INSTANCE()
      throws NullMaxPlayerException, InvalidMaxPlayerException, NullPlayerNameException,
      NullPlayerIdException, NullPlayerBookshelfException, NullPlayerScoreException, NullPlayerPrivateCardException,
      NullPlayerScoreBlocksException, DuplicatePlayerNameException, AlreadyInitiatedPatternException,
      NullPlayerNamesException, InvalidNumOfPlayersException, NullNumOfPlayersException, NullGameHandlerInstance,
      NullAssignedPatternException, FullGameException {

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
      throws NullMaxPlayerException, InvalidMaxPlayerException, NullPlayerNameException,
      NullPlayerIdException, NullPlayerBookshelfException, NullPlayerScoreException, NullPlayerPrivateCardException,
      NullPlayerScoreBlocksException, DuplicatePlayerNameException, AlreadyInitiatedPatternException,
      NullPlayerNamesException, InvalidNumOfPlayersException, NullNumOfPlayersException, NullGameHandlerInstance,
      NullSocketConnectorException, NullPlayerConnector, NullBlockingQueueException, FullGameException {

    PlayerConnector playerConnector = new PlayerConnector(new Socket(), new LinkedBlockingQueue<>());
    ServerControllerState.addPlayerConnector(playerConnector);
    assertEquals(1, ServerControllerState.getPlayersPool().size());
  }

  @Test
  void ADD_PLAYER_CONNECTOR_should_THROW_NullPlayerConnector()
      throws NullMaxPlayerException, InvalidMaxPlayerException, NullPlayerNameException,
      NullPlayerIdException, NullPlayerBookshelfException, NullPlayerScoreException, NullPlayerPrivateCardException,
      NullPlayerScoreBlocksException, DuplicatePlayerNameException, AlreadyInitiatedPatternException,
      NullPlayerNamesException, InvalidNumOfPlayersException, NullNumOfPlayersException, NullGameHandlerInstance,
      NullSocketConnectorException, NullPlayerConnector, FullGameException {

    assertThrows(NullPlayerConnector.class, () -> ServerControllerState.addPlayerConnector(null));
    assertEquals(0, ServerControllerState.getPlayersPool().size());
  }

  @Test
  void REMOVE_PLAYER_CONNECTOR_should_REMOVE_PLAYER_CONNECTOR()
      throws NullMaxPlayerException, InvalidMaxPlayerException, NullPlayerNameException,
      NullPlayerIdException, NullPlayerBookshelfException, NullPlayerScoreException, NullPlayerPrivateCardException,
      NullPlayerScoreBlocksException, DuplicatePlayerNameException, AlreadyInitiatedPatternException,
      NullPlayerNamesException, InvalidNumOfPlayersException, NullNumOfPlayersException, NullGameHandlerInstance,
      NullSocketConnectorException, NullPlayerConnector, NullBlockingQueueException, NullAssignedPatternException, FullGameException {

    GameHandler handler = new GameHandler("Steve", 2);
    PlayerConnector steve = new PlayerConnector(new Socket(), new LinkedBlockingQueue<>());
    steve.setPlayer(PlayerFactory.getNewPlayer("Steve", List.of(), handler.getGame()));
    PlayerConnector alice = new PlayerConnector(new Socket(), new LinkedBlockingQueue<>());
    alice.setPlayer(PlayerFactory.getNewPlayer("Alice", List.of(), handler.getGame()));
    steve.setGameId(handler.getGame().getGameId());
    steve.getPlayer().setPlayerName("Steve");
    alice.setGameId(handler.getGame().getGameId());
    alice.getPlayer().setPlayerName("Alice");
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
      throws NullMaxPlayerException, InvalidMaxPlayerException, NullPlayerNameException,
      NullPlayerIdException, NullPlayerBookshelfException, NullPlayerScoreException, NullPlayerPrivateCardException,
      NullPlayerScoreBlocksException, DuplicatePlayerNameException, AlreadyInitiatedPatternException,
      NullPlayerNamesException, InvalidNumOfPlayersException, NullNumOfPlayersException, NullGameHandlerInstance,
      NullSocketConnectorException, NullPlayerConnector, NullBlockingQueueException, NullAssignedPatternException, FullGameException {

    GameHandler handler = new GameHandler("Steve", 2);
    GameHandler handler2 = new GameHandler("Bob", 2);
    PlayerConnector steve = new PlayerConnector(new Socket(), new LinkedBlockingQueue<>());
    steve.setPlayer(PlayerFactory.getNewPlayer("Steve", List.of(), handler.getGame()));;
    PlayerConnector alice = new PlayerConnector(new Socket(), new LinkedBlockingQueue<>());
    alice.setPlayer(PlayerFactory.getNewPlayer("Alice", List.of(), handler.getGame()));
    PlayerConnector bob = new PlayerConnector(new Socket(), new LinkedBlockingQueue<>());
    bob.setPlayer(PlayerFactory.getNewPlayer("Bob", List.of(), handler2.getGame()));
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
    assertThrows(NullGameHandlerInstance.class, () -> ServerControllerState.getGameHandlerByUUID(UUID.randomUUID()));
  }

  @Test
  public void getGameHandlerByUUID_should_return_handler() 
      throws NullMaxPlayerException, InvalidMaxPlayerException, NullPlayerNameException, NullPlayerIdException, 
      NullPlayerBookshelfException, NullPlayerScoreException, NullPlayerPrivateCardException, 
      NullPlayerScoreBlocksException, DuplicatePlayerNameException, AlreadyInitiatedPatternException, 
      NullPlayerNamesException, InvalidNumOfPlayersException, NullNumOfPlayersException, NullAssignedPatternException,
      NullGameHandlerInstance, FullGameException {
    
    GameHandler handler = new GameHandler("Steve", 2);
    ServerControllerState.addGameHandler(handler);
    GameHandler returnedHandler = ServerControllerState.getGameHandlerByUUID(handler.getGame().getGameId()); 
    assertNotNull(returnedHandler);
    assertEquals(handler, returnedHandler);
  }
}
