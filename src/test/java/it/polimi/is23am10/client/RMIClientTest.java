package it.polimi.is23am10.client;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.Optional;
import java.util.concurrent.LinkedBlockingQueue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import it.polimi.is23am10.client.userinterface.CommandLineInterface;
import it.polimi.is23am10.client.userinterface.UserInterface;
import it.polimi.is23am10.server.controller.interfaces.IServerControllerAction;
import it.polimi.is23am10.server.network.messages.AbstractMessage;
import it.polimi.is23am10.server.network.messages.ErrorMessage;
import it.polimi.is23am10.server.network.playerconnector.PlayerConnectorRmi;
import it.polimi.is23am10.server.network.playerconnector.exceptions.NullBlockingQueueException;
import it.polimi.is23am10.server.network.playerconnector.exceptions.NullSocketConnectorException;
import it.polimi.is23am10.server.network.playerconnector.interfaces.IPlayerConnector;
import it.polimi.is23am10.server.network.virtualview.VirtualView;

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
    UserInterface ui = new CommandLineInterface();

    Client rmiClient = new RMIClient(pc, ui, null, null, null);

    assertNotNull(rmiClient);
  }

  @Test
  void run_should_runCoreCycle() throws IOException, InterruptedException {
    AbstractMessage msg = new ErrorMessage("New Message", null);

    doReturn(false, true).when(rmiClient).hasRequestedDisconnection();
    rmiClient.hasJoined = true;
    when(playerConnectorServer.getMessageFromQueue()).thenReturn(msg);
    doNothing().when(rmiClient).showServerMessage(msg);

    rmiClient.run();

    verify(rmiClient, times(2)).hasRequestedDisconnection();
  }

  @Test
  void run_should_throwRemoteException() throws IOException, InterruptedException {
    doReturn(false, true).when(rmiClient).hasRequestedDisconnection();
    rmiClient.hasJoined = true;
    doThrow(RemoteException.class).when(playerConnectorServer).getMessageFromQueue();

    rmiClient.run();

    verify(rmiClient, times(2)).hasRequestedDisconnection();
  }

  @Test
  void run_should_throwNullPointerException() throws IOException, InterruptedException {
    doReturn(false, true).when(rmiClient).hasRequestedDisconnection();
    rmiClient.hasJoined = true;
    doThrow(NullPointerException.class).when(playerConnectorServer).getMessageFromQueue();

    rmiClient.run();

    verify(rmiClient, times(2)).hasRequestedDisconnection();
  }
}
