package it.polimi.is23am10;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import it.polimi.is23am10.Server.ServerStatus;
import it.polimi.is23am10.config.ServerConfigContext;
import it.polimi.is23am10.config.ServerConfigDefault;
import it.polimi.is23am10.controller.interfaces.IServerControllerActionRmi;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.registry.Registry;
import java.util.concurrent.ExecutorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
@SuppressWarnings({ "deprecation", "checkstyle:methodname", "checkstyle:abbreviationaswordinnamecheck",
    "checkstyle:linelengthcheck",
    "checkstyle:onetoplevelclasscheck", "checkstyle:variabledeclarationusagedistancecheck",
    "checkstyle:operatorwrapcheck", "checkstyle:multiplevariabledeclarationscheck", "checkstyle:membernamecheck",
    "checkstyle:nonemptyatclausedescriptioncheck", "checkstyle:typenamecheck" })
class ServerTest {

  @Mock
  ServerSocket serverSocket;

  @Mock
  ExecutorService executorService;

  @Mock
  IServerControllerActionRmi rmiServer;

  @Mock
  IServerControllerActionRmi rmiStub;

  @Mock
  Registry rmiRegistry;

  @Spy
  @InjectMocks
  Server server;

  ServerConfigContext ctx = new ServerConfigContext(ServerConfigDefault.SERVER_SOCKET_PORT,
      ServerConfigDefault.SERVER_RMI_PORT,
      ServerConfigDefault.MAX_CLIENT_CONNECTION, ServerConfigDefault.KEEP_ALIVE);

  @BeforeEach
  public void setup() {
    // if we don't call below, we will get NullPointerException
    MockitoAnnotations.initMocks(this);
  }

  @Test
  void START_SOCKET_SERVER_should_START_SOCKET_SERVER() throws IOException {
    when(serverSocket.isClosed()).thenReturn(true, false, true, false);
    assertEquals(ServerStatus.STOPPED, server.status());

    when(serverSocket.accept()).thenReturn(Mockito.mock(Socket.class));
    doNothing().when(executorService).execute(any());
    server.start(ctx);
    assertEquals(ServerStatus.STARTED, server.status());

    verify(executorService, Mockito.times(1)).execute(any());
  }

  @Test
  void SOCKET_SERVER_ACCEPT_should_THROW_IOException() throws IOException {
    when(serverSocket.isClosed()).thenReturn(false, true);

    when(serverSocket.accept()).thenThrow(IOException.class);
    server.start(ctx);
    verify(server).start(ctx);
  }

  @Test
  void STOP_SOCKET_SERVER_should_STOP_SERVER() throws IOException {
    when(serverSocket.isClosed()).thenReturn(true, false, true, false, false, true);
    assertEquals(ServerStatus.STOPPED, server.status());

    when(serverSocket.accept()).thenReturn(Mockito.mock(Socket.class));
    doNothing().when(executorService).execute(any());
    server.start(ctx);
    assertEquals(ServerStatus.STARTED, server.status());

    doNothing().when(serverSocket).close();
    server.stop();
    assertEquals(ServerStatus.STOPPED, server.status());
  }

  @Test
  void STOP_SOCKET_SERVER_should_THROW_IOException() throws IOException {
    doThrow(IOException.class).when(serverSocket).close();
    assertThrows(IOException.class, () -> server.stop());
  }
}