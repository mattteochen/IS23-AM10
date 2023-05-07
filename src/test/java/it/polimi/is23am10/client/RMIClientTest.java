package it.polimi.is23am10.client;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingQueue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import it.polimi.is23am10.client.userinterface.CommandLineInterface;
import it.polimi.is23am10.client.userinterface.UserInterface;
import it.polimi.is23am10.server.controller.interfaces.IServerControllerAction;
import it.polimi.is23am10.server.model.game.Game.GameStatus;
import it.polimi.is23am10.server.model.items.board.Board;
import it.polimi.is23am10.server.model.items.board.exceptions.BoardGridColIndexOutOfBoundsException;
import it.polimi.is23am10.server.model.items.board.exceptions.BoardGridRowIndexOutOfBoundsException;
import it.polimi.is23am10.server.model.items.board.exceptions.InvalidNumOfPlayersException;
import it.polimi.is23am10.server.model.items.board.exceptions.NullNumOfPlayersException;
import it.polimi.is23am10.server.model.items.bookshelf.Bookshelf;
import it.polimi.is23am10.server.model.items.tile.Tile.TileType;
import it.polimi.is23am10.server.model.player.Player;
import it.polimi.is23am10.server.model.player.exceptions.NullPlayerIdException;
import it.polimi.is23am10.server.model.player.exceptions.NullPlayerNameException;
import it.polimi.is23am10.server.network.messages.AbstractMessage;
import it.polimi.is23am10.server.network.messages.ErrorMessage;
import it.polimi.is23am10.server.network.playerconnector.PlayerConnectorRmi;
import it.polimi.is23am10.server.network.playerconnector.exceptions.NullBlockingQueueException;
import it.polimi.is23am10.server.network.playerconnector.exceptions.NullSocketConnectorException;
import it.polimi.is23am10.server.network.playerconnector.interfaces.IPlayerConnector;
import it.polimi.is23am10.server.network.virtualview.VirtualPlayer;
import it.polimi.is23am10.server.network.virtualview.VirtualView;
import it.polimi.is23am10.utils.exceptions.NullIndexValueException;

/**
 * Rmi client tests.
 */
@RunWith(MockitoJUnitRunner.class)
@SuppressWarnings({ "deprecation", "unchecked", "checkstyle:methodname", "checkstyle:abbreviationaswordinnamecheck",
    "checkstyle:linelengthcheck", "checkstyle:onetoplevelclasscheck" })
public class RMIClientTest {
  @Mock
  PlayerConnectorRmi playerConnectorRmi;

  @Mock
  InetAddress serverAddress;

  @Mock
  VirtualView virtualView;

  @Mock
  UserInterface userInterface;

  @Mock
  Registry rmiRegistry;

  @Mock
  IServerControllerAction serverControllerActionServer;

  @Mock
  IPlayerConnector playerConnectorServer;

  @Spy
  @InjectMocks
  RMIClient rmiClient;

  @BeforeEach
  public void setup() {
    // if we don't call below, we will get NullPointerException
    MockitoAnnotations.initMocks(this);
  }

  @Test
  void constructor_should_create_RMIClientTest()
      throws NullSocketConnectorException, NullBlockingQueueException, UnknownHostException, RemoteException {
    PlayerConnectorRmi pc = new PlayerConnectorRmi(new LinkedBlockingQueue<>());
    UserInterface ui = new CommandLineInterface(true);

    Client rmiClient = new RMIClient(pc, ui, null, null, null);

    assertNotNull(rmiClient);
  }

  @Test
  @Disabled
  void run_should_runCoreCycle() throws IOException, InterruptedException, NullSocketConnectorException, NullBlockingQueueException, NullPlayerNameException {
    AbstractMessage msg = new ErrorMessage("New Message", null);
    BufferedReader br = mock(BufferedReader.class);

    doReturn(false, true).when(rmiClient).hasRequestedDisconnection();
    doReturn("ale").when(rmiClient).handlePlayerNameSelection(playerConnectorRmi, br);
    doNothing().when(rmiClient).handleGameSelection(playerConnectorRmi, br, "ale");
    when(playerConnectorServer.getMessageFromQueue()).thenReturn(msg);
    doNothing().when(rmiClient).showServerMessage(msg);

    rmiClient.run();

    verify(rmiClient, times(2)).hasRequestedDisconnection();
  }

  @Test
  void handlePlayerNameSelection_should_run_playerNameSelection() throws IOException, InterruptedException, NullSocketConnectorException, NullBlockingQueueException, NullPlayerNameException {
    AbstractMessage msg = new ErrorMessage("New Message", null);
    BufferedReader br = mock(BufferedReader.class); 
    Player p = mock(Player.class);
    String pn = "Benny";
    doReturn(pn).when(br).readLine();
    when(playerConnectorRmi.getPlayer()).thenReturn(p);
    when(playerConnectorServer.getMessageFromQueue()).thenReturn(msg);
    doNothing().when(rmiClient).showServerMessage(msg);

    assertEquals(rmiClient.handlePlayerNameSelection(playerConnectorRmi, br), pn);
  }

  @Test
  @Disabled
  void handleGameSelection_should_run_gameSelection() throws IOException, InterruptedException, NullSocketConnectorException, NullBlockingQueueException, NullPlayerNameException, NotBoundException {
    BufferedReader br = mock(BufferedReader.class); 
    List<VirtualView> ag = List.of(mock(VirtualView.class));
    String pn = "Benny";
    String command = "c 2";
    UUID gId = UUID.randomUUID();

    rmiClient.setAvailableGames(ag);
    doNothing().when(rmiClient).getAvailableGames(playerConnectorRmi);
    doNothing().when(rmiClient).lookupInit();
    doReturn(command).when(br).readLine();
    when(rmiClient.getGameIdRef()).thenReturn(gId,gId);

    rmiClient.handleGameSelection(playerConnectorRmi, br, pn);

    assertEquals(gId, playerConnectorRmi.getGameId());
  }

  @Test
  void handleCommand_should_run_handleCommand() throws IOException, InterruptedException, NullSocketConnectorException, NullBlockingQueueException, NullPlayerNameException, NotBoundException, InvalidNumOfPlayersException, NullNumOfPlayersException, BoardGridRowIndexOutOfBoundsException, BoardGridColIndexOutOfBoundsException, NullIndexValueException {
    BufferedReader br = mock(BufferedReader.class); 
    String pn = "Benny";
    String command = "move 42 -> 06";
    VirtualView vw = mock(VirtualView.class);
    Player p = mock(Player.class);
    VirtualPlayer vp = mock(VirtualPlayer.class);

    rmiClient.setVirtualView(vw);
    doReturn(command).when(br).readLine();
    doReturn(vw).when(rmiClient).getVirtualView();
    when(playerConnectorRmi.getPlayer()).thenReturn(p);
    when(vw.getActivePlayer()).thenReturn(vp);
    when(p.getPlayerName()).thenReturn(pn);
    when(vp.getPlayerName()).thenReturn(pn);
    when(vw.getStatus()).thenReturn(GameStatus.STARTED);
    when(vp.getBookshelf()).thenReturn(new Bookshelf());
    when(vw.getGameBoard()).thenReturn(new Board(4));


    rmiClient.handleCommands(playerConnectorRmi, br);
  }

}
