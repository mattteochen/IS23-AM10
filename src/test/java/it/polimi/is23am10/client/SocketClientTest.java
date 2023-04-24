package it.polimi.is23am10.client;

import static org.junit.Assert.assertNotNull;

import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;

import org.junit.jupiter.api.Test;

import it.polimi.is23am10.client.userinterface.CommandLineInterface;
import it.polimi.is23am10.client.userinterface.UserInterface;
import it.polimi.is23am10.server.network.playerconnector.PlayerConnectorSocket;
import it.polimi.is23am10.server.network.playerconnector.exceptions.NullBlockingQueueException;
import it.polimi.is23am10.server.network.playerconnector.exceptions.NullSocketConnectorException;

public class SocketClientTest {
  @Test
  void constructor_should_create_SocketClientTest() throws NullSocketConnectorException, NullBlockingQueueException {
    Socket socket = new Socket();
    PlayerConnectorSocket pc = new PlayerConnectorSocket(socket, new LinkedBlockingQueue<>());
    UserInterface ui = new CommandLineInterface(true);

    Client socketClient = new SocketClient(pc, ui);

    assertNotNull(socketClient);
  }
}
