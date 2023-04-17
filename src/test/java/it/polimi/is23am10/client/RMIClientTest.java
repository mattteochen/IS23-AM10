package it.polimi.is23am10.client;

import static org.junit.Assert.assertNotNull;

import java.util.concurrent.LinkedBlockingQueue;

import org.junit.jupiter.api.Test;

import it.polimi.is23am10.client.userinterface.CommandLineInterface;
import it.polimi.is23am10.client.userinterface.UserInterface;
import it.polimi.is23am10.server.network.playerconnector.PlayerConnectorRmi;
import it.polimi.is23am10.server.network.playerconnector.exceptions.NullBlockingQueueException;
import it.polimi.is23am10.server.network.playerconnector.exceptions.NullSocketConnectorException;

public class RMIClientTest {
  @Test
  void constructor_should_create_RMIClientTest() throws NullSocketConnectorException, NullBlockingQueueException {
    PlayerConnectorRmi pc = new PlayerConnectorRmi(new LinkedBlockingQueue<>());
    UserInterface ui = new CommandLineInterface();

    Client rmiClient = new RMIClient(pc, ui);

    assertNotNull(rmiClient);
  }
}
