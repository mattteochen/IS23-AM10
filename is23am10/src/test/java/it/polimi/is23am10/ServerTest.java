package it.polimi.is23am10;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyByte;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import it.polimi.is23am10.Server.ServerStatus;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import org.apache.logging.log4j.Logger;
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
public class ServerTest {

  @Mock
  ServerSocket serverSocket;

  @Mock
  ExecutorService threadPool;

  @Mock
  Logger logger;

  @Spy
  @InjectMocks
  Server server;

  @BeforeEach
	public void setup() {
		//if we don't call below, we will get NullPointerException
		MockitoAnnotations.initMocks(this);
	}

  @Test
  public void START_SOCKET_SERVER_green_path() throws IOException {
    when(serverSocket.isClosed()).thenReturn(true, false, true, false);
    assertEquals(ServerStatus.STOPPED, server.status());

    doNothing().when(server).initServerSocket();

    when(serverSocket.accept()).thenReturn(Mockito.mock(Socket.class));
    doNothing().when(threadPool).execute(any());
    server.start();
    assertEquals(ServerStatus.STARTED, server.status());

		verify(threadPool, Mockito.times(1)).execute(any());
  }

  @Test
  public void SOCKET_SERVER_ACCEPT_throws_IOException() throws IOException {
    when(serverSocket.isClosed()).thenReturn(false, true);

    doNothing().when(server).initServerSocket();

    when(serverSocket.accept()).thenThrow(IOException.class);
    server.start();
    verify(server).start();
  }

  @Test
  public void STOP_SOCKET_SERVER_green_path() throws IOException {
    when(serverSocket.isClosed()).thenReturn(true, false, true, false, false, true);
    assertEquals(ServerStatus.STOPPED, server.status());

    doNothing().when(server).initServerSocket();

    when(serverSocket.accept()).thenReturn(Mockito.mock(Socket.class));
    doNothing().when(threadPool).execute(any());
    server.start();
    assertEquals(ServerStatus.STARTED, server.status());

    doNothing().when(serverSocket).close();
    server.stop();
    assertEquals(ServerStatus.STOPPED, server.status());
  }

  @Test
  public void START_SOCKET_SERVER_throws_IOException() throws IOException {
    doThrow(IOException.class).when(server).initServerSocket();
    assertThrows(IOException.class, () -> server.start());
  }

  @Test
  public void STOP_SOCKET_SERVER_throws_IOException() throws IOException {
    doThrow(IOException.class).when(serverSocket).close();
    assertThrows(IOException.class, () -> server.stop());
  }
}
