package it.polimi.is23am10.client;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;

import com.google.gson.Gson;

import it.polimi.is23am10.client.userinterface.UserInterface;
import it.polimi.is23am10.server.network.messages.AbstractMessage;
import it.polimi.is23am10.server.network.playerconnector.PlayerConnectorSocket;
import it.polimi.is23am10.server.network.virtualview.VirtualView;

/**
 * A client using Socket as communication method.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public class SocketClient extends Client {

  /**
   * A {@link Gson} instance to serialize and deserialize commands.
   * 
   */
  Gson gson = new Gson();

  /**
   * Public constructor for client using Socket as communication method.
   * 
   * @param pc player connector
   * @param ui user interface
   */
  public SocketClient(PlayerConnectorSocket pc, UserInterface ui) throws UnknownHostException {
    super(pc, ui);
  }

  /**
   * Client core cycle.
   * Send user requested commands and read updates.
   * 
   */
  @Override
  public void run() {
    // PlayerConnector's msg queue is not used at this time as we don't have multi
    // source message inputs to handle,
    // hence there is no need to buffer them as at server level. Here we can just
    // live update the view upon receiving an update in a FIFO manner.
    // Consider using a lighter connector.
    PlayerConnectorSocket playerConnectorSocket = (PlayerConnectorSocket) playerConnector;
    while (playerConnectorSocket.getConnector().isConnected()) {

      // TODO: implement the following
      // if any new user request, process it (if virtual view has not declared that it
      // is this player turn, skip).

      // retrieve and show server messages
      try {
        AbstractMessage serverMessage = parseServerMessage(playerConnectorSocket);
        if (serverMessage != null) {
          showServerMessage(serverMessage);
        }
      } catch (IOException e) {
        // TODO: integrate custom logger
        System.out.println("Failed to retrieve information from server, your game context may not be updated");
      }
    }

    // TODO: integrate custom logger
    System.out.println("Connection with the server ended");
  }

  /**
   * Parse the server payload.
   * 
   * @param pc The socket player connector.
   * @return The parsed {@link AbstractMessage}.
   * @throws IOException.
   *
   */
  protected AbstractMessage parseServerMessage(PlayerConnectorSocket pc) throws IOException {
    DataInputStream dis = new DataInputStream(pc.getConnector().getInputStream());
    BufferedReader br = new BufferedReader(new InputStreamReader(dis));
    String payload = br.readLine();
    return payload == null ? null : gson.fromJson(payload, AbstractMessage.class);
  }

  /**
   * Parse the server payload.
   * 
   * @param pc The socket player connector.
   *
   */
  protected void showServerMessage(AbstractMessage msg) {
    switch (msg.getMessageType()) {
      case CHAT_MESSAGE:
        userInterface.displayChatMessage(msg);
        break;
      case GAME_SNAPSHOT:
        userInterface.displayVirtualView(gson.fromJson(msg.getMessage(), VirtualView.class));
        break;
      case ERROR_MESSAGE:
        userInterface.displayErrorMessage(msg);
        break;
    }
  }
}
