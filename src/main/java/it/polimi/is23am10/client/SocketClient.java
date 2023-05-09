package it.polimi.is23am10.client;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.UUID;

import it.polimi.is23am10.client.interfaces.AlarmConsumer;
import it.polimi.is23am10.client.userinterface.UserInterface;
import it.polimi.is23am10.server.command.AddPlayerCommand;
import it.polimi.is23am10.server.command.GetAvailableGamesCommand;
import it.polimi.is23am10.server.command.MoveTilesCommand;
import it.polimi.is23am10.server.command.SendChatMessageCommand;
import it.polimi.is23am10.server.command.StartGameCommand;
import it.polimi.is23am10.server.model.player.exceptions.NullPlayerIdException;
import it.polimi.is23am10.server.model.player.exceptions.NullPlayerNameException;
import it.polimi.is23am10.server.command.SnoozeGameTimerCommand;
import it.polimi.is23am10.server.command.StartGameCommand;
import it.polimi.is23am10.server.command.SnoozeGameTimerCommand;
import it.polimi.is23am10.server.command.StartGameCommand;
import it.polimi.is23am10.server.model.player.Player;
import it.polimi.is23am10.server.network.messages.AbstractMessage;
import it.polimi.is23am10.server.network.messages.ChatMessage;
import it.polimi.is23am10.server.network.playerconnector.AbstractPlayerConnector;
import it.polimi.is23am10.server.network.playerconnector.PlayerConnectorSocket;
import it.polimi.is23am10.utils.Coordinates;

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
   * {@inheritDoc}
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
      try {
        clientRunnerCore(playerConnectorSocket);
      } catch (IOException | InterruptedException e) {
        System.out.println("🛑 " + e.getMessage());
      } catch (NullPlayerNameException | NullPlayerIdException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }

    System.out.println("🛑 Connection with the server ended");
  }

  /**
   * Parse the server payload.
   * 
   * @param pc The socket player connector.
   * @return The parsed {@link AbstractMessage}.
   * @throws IOException Possibly thrown by readline.
   *
   */
  protected AbstractMessage parseServerMessage(PlayerConnectorSocket pc) throws IOException {
    DataInputStream dis = new DataInputStream(pc.getConnector().getInputStream());
    BufferedReader br = new BufferedReader(new InputStreamReader(dis));
    String payload = br.readLine();
    return payload == null ? null : gson.fromJson(payload, AbstractMessage.class);
  }

  /**
   * {@inheritDoc}
   *
   */
  @Override
  void getAvailableGames(AbstractPlayerConnector apc)
      throws IOException, InterruptedException {
    GetAvailableGamesCommand command = new GetAvailableGamesCommand();
    String req = gson.toJson(command);
    sendMessage(req, apc);
  };

  /**
   * {@inheritDoc}
   *
   */
  @Override
  void startGame(AbstractPlayerConnector apc, String playerName, int maxPlayerNum) throws IOException {
    StartGameCommand command = new StartGameCommand(playerName, maxPlayerNum);
    String req = gson.toJson(command);
    sendMessage(req, apc);
  };

  /**
   * {@inheritDoc}
   *
   */
  @Override
  void addPlayer(AbstractPlayerConnector apc, String playerName, UUID gameId) throws IOException {
    AddPlayerCommand command = new AddPlayerCommand(playerName, gameId);
    String req = gson.toJson(command);
    sendMessage(req, apc);
  };

   /**
   * {@inheritDoc}
   *
   */
  @Override
  void moveTiles(AbstractPlayerConnector apc, Map<Coordinates, Coordinates> moves) throws IOException {
    MoveTilesCommand command = new MoveTilesCommand(apc.getPlayer().getPlayerName(), apc.getGameId(), moves);
    String req = gson.toJson(command);
    System.out.println(req);
    sendMessage(req, apc);
  };

    
  /**
   * {@inheritDoc}
   * 
   */
   @Override
  void sendChatMessage(AbstractPlayerConnector apc, ChatMessage msg) throws IOException {
    SendChatMessageCommand command = new SendChatMessageCommand(msg);
    String req = gson.toJson(command);
    sendMessage(req, apc);
  }

  /**
   * Write a message throw the socket.
   *
   * @param req The payload request.
   * @param apc The player connector instance.
   * @throws IOException.
   * 
   */
  protected void sendMessage(String req, AbstractPlayerConnector apc) throws IOException {
    PrintWriter epson = new PrintWriter(((PlayerConnectorSocket) apc).getConnector().getOutputStream(), true,
        StandardCharsets.UTF_8);
    epson.println(req);
  }

  /**
   * {@inheritDoc}
   *
   */
  @Override
  public void runMessageHandler(){
    PlayerConnectorSocket playerConnectorSocket = (PlayerConnectorSocket) playerConnector;
    Thread messageHandler = new Thread(()->{
      while(playerConnectorSocket.getConnector().isConnected() && !hasRequestedDisconnection()){
        // retrieve and show server messages, it includes chat messages
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
    });
    messageHandler.start();
  }
}
