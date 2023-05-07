package it.polimi.is23am10.client;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

import it.polimi.is23am10.client.interfaces.AlarmConsumer;
import it.polimi.is23am10.client.userinterface.UserInterface;
import it.polimi.is23am10.server.command.SnoozeGameTimerCommand;
import it.polimi.is23am10.server.command.StartGameCommand;
import it.polimi.is23am10.server.model.player.Player;
import it.polimi.is23am10.server.network.messages.AbstractMessage;
import it.polimi.is23am10.server.network.playerconnector.PlayerConnectorSocket;

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
   * Public constructor for client using Socket as communication method.
   * 
   * @param pc player connector
   * @param ui user interface
   */
  public SocketClient(PlayerConnectorSocket pc, UserInterface ui) throws UnknownHostException {
    super(pc, ui);
  }

  /**
   * Socket alarm snoozer.
   * 
   */
  protected AlarmConsumer snoozer = () -> {
    //TODO: refactor after this https://github.com/mattteochen/IS23-AM10/issues/121
    //skip if the client has not joined the game: server won't have any connector for the current client
    if (!hasJoined()) {
      return;
    }
    try {
      PlayerConnectorSocket playerConnectorSocket = (PlayerConnectorSocket) playerConnector;
      SnoozeGameTimerCommand cmd = new SnoozeGameTimerCommand(playerConnectorSocket.getPlayer().getPlayerName());
      String req = gson.toJson(cmd);
      PrintWriter epson = new
      PrintWriter(playerConnectorSocket.getConnector().getOutputStream(), true,
      StandardCharsets.UTF_8);
      epson.println(req);
    } catch(IOException e) {
      System.out.println("🛑 " + e.getMessage());
    }
  };

  /**
   * {@inheritdoc}.
   * 
   */
  @Override
  protected boolean hasJoined() {
    //TODO: consider further checks as gameID
    PlayerConnectorSocket playerConnectorSocket = (PlayerConnectorSocket) playerConnector;
    return (playerConnectorSocket.getPlayer() != null &&
      playerConnectorSocket.getPlayer().getPlayerName() != null);
  }

  /**
   * Client core cycle.
   * Send user requested commands and read updates.
   * 
   */
  @Override
  public void run() {

    alarm.scheduleAtFixedRate(new AlarmTask(snoozer),
      ALARM_INITIAL_DELAY_MS, ALARM_INTERVAL_MS);

    // PlayerConnector's msg queue is not used at this time as we don't have multi
    // source message inputs to handle,
    // hence there is no need to buffer them as at server level. Here we can just
    // live update the view upon receiving an update in a FIFO manner.
    // Consider using a lighter connector.
    PlayerConnectorSocket playerConnectorSocket = (PlayerConnectorSocket) playerConnector;

    while (playerConnectorSocket.getConnector().isConnected() && !hasRequestedDisconnection()) {

      // TODO: implement user requests
      // if any new user request, process it (if virtual view has not declared that it
      // is this player turn, skip).

      // Some hints for the above's implementer: This is a start game request demo, enable this to test, to be removed for the real client request.
      // TODO: remove when not needed anymore
      /*
       * try {
       * StartGameCommand command = new StartGameCommand("Socket client", 4);
       * String req = gson.toJson(command);
       * PrintWriter epson = new
       * PrintWriter(playerConnectorSocket.getConnector().getOutputStream(), true,
       * StandardCharsets.UTF_8);
       * epson.println(req);
       * } catch(Exception e) {
       * System.out.println("🛑 " + e.getMessage());
       * }
       */

      // retrieve and show server messages
      try {
        AbstractMessage serverMessage = parseServerMessage(playerConnectorSocket);
        if (serverMessage != null) {
          showServerMessage(serverMessage);
        }
      } catch (IOException e) {
        // TODO: integrate custom logger
        System.out.println("🛑 Failed to retrieve information from server, your game context may not be updated");
      }
    }

    // TODO: integrate custom logger
    System.out.println("🛑 Connection with the server ended");
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
}
