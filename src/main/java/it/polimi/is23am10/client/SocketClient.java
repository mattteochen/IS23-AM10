package it.polimi.is23am10.client;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import it.polimi.is23am10.client.userinterface.CommandLineInterface;
import it.polimi.is23am10.client.userinterface.UserInterface;
import it.polimi.is23am10.client.userinterface.helpers.CLIStrings;
import it.polimi.is23am10.client.userinterface.helpers.OutputWrapper;
import it.polimi.is23am10.server.command.AbstractCommand;
import it.polimi.is23am10.server.command.AddPlayerCommand;
import it.polimi.is23am10.server.command.GetAvailableGamesCommand;
import it.polimi.is23am10.server.command.MoveTilesCommand;
import it.polimi.is23am10.server.command.StartGameCommand;
import it.polimi.is23am10.server.controller.ServerControllerAction;
import it.polimi.is23am10.server.controller.ServerControllerState;
import it.polimi.is23am10.server.model.factory.GameFactory;
import it.polimi.is23am10.server.model.factory.PlayerFactory;
import it.polimi.is23am10.server.model.player.Player;
import it.polimi.is23am10.server.model.player.exceptions.NullPlayerNameException;
import it.polimi.is23am10.server.network.messages.AbstractMessage;
import it.polimi.is23am10.server.network.messages.AvailableGamesMessage;
import it.polimi.is23am10.server.network.messages.ErrorMessage;
import it.polimi.is23am10.server.network.messages.ErrorMessage.ErrorSeverity;
import it.polimi.is23am10.server.network.playerconnector.AbstractPlayerConnector;
import it.polimi.is23am10.server.network.playerconnector.PlayerConnectorSocket;
import it.polimi.is23am10.server.network.virtualview.VirtualView;
import it.polimi.is23am10.utils.CommandSyntaxValidator;
import it.polimi.is23am10.utils.Coordinates;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

    /*
     * The default values are null, I will set those values separately to
     * handle input errors in a separate way. This allows us, once selected the
     * game, to reinsert only the player name if wrong.
     */
    String selectedPlayerName = null;
    UUID selectedGameId = null;

    System.out.println(CLIStrings.welcomeString);

    while (playerConnectorSocket.getConnector().isConnected() && !hasRequestedDisconnection()) {
      // TODO: implement user requests
      // if any new user request, process it (if virtual view has not declared that it
      // is this player turn, skip).

      try {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // Execute if the client is not connected to a game.
        if (playerConnectorSocket.getGameId() == null) {
          
          // First I'm gonna ask the player name
          if (selectedPlayerName == null) {
            selectedPlayerName = handlePlayerNameSelection(playerConnectorSocket, br);
          } 
          
          if (selectedPlayerName != null) {
            handleGameSelection(playerConnectorSocket, selectedGameId, br, selectedPlayerName);
          }
        } else {
          // Executed if the client is connected to a game.

          // This allows the player to play the turn if he's the active player
          if (playerConnectorSocket.getPlayer().getIsActivePlayer()) {
            handleMoveCommand(playerConnectorSocket, br);
          }

          // I can send messages or logout whether it's my turn or not
          String fullCommand = br.readLine();
          String command = fullCommand.split(" ")[0];
          switch (command) {
            case "chat":
              // TODO: add send chat message command
              break;
            case "logout":
              // TODO: add logout command
              break;
            default:
          }
        }
      } catch (IOException | InterruptedException e) {
        System.out.println("🛑 " + e.getMessage());
      }

      // Some hints for the above's implementer: This is a start game request demo,
      // enable this to test, to be removed for the real client request.
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

  /**
   * Socket implementation of a method that send command to get all available
   * games.
   * 
   * @throws IOException
   */
  @Override
  void getAvailableGames(AbstractPlayerConnector apc)
      throws IOException, InterruptedException {
    GetAvailableGamesCommand command = new GetAvailableGamesCommand();
    HashMap<Integer, VirtualView> mapIndexVirtualView = new HashMap<Integer, VirtualView>();
    Integer gameIdx = 0;
    String req = gson.toJson(command);
    PrintWriter epson = new PrintWriter(((PlayerConnectorSocket) apc).getConnector().getOutputStream(), true,
        StandardCharsets.UTF_8);
    epson.println(req);
  };

  /**
   * Socket implementation of a method that send command to start a new game.
   * 
   * @throws IOException
   */
  @Override
  void startGame(AbstractPlayerConnector apc, String playerName, int maxPlayerNum) throws IOException {
    StartGameCommand command = new StartGameCommand(playerName, maxPlayerNum);
    String req = gson.toJson(command);
    PrintWriter epson = new PrintWriter(((PlayerConnectorSocket) apc).getConnector().getOutputStream(), true,
        StandardCharsets.UTF_8);
    epson.println(req);
  };

  /**
   * Socket implementation of a method that send command to add a new player.
   * 
   * @throws IOException
   */
  @Override
  void addPlayer(AbstractPlayerConnector apc, String playerName, UUID gameId) throws IOException {
    AddPlayerCommand command = new AddPlayerCommand(playerName, gameId);
    String req = gson.toJson(command);
    PrintWriter epson = new PrintWriter(((PlayerConnectorSocket) apc).getConnector().getOutputStream(), true,
        StandardCharsets.UTF_8);
    epson.println(req);
  };

  /**
   * Socket implementation of a method that send command to move tiles.
   * 
   * @throws IOException
   */
  @Override
  void moveTiles(AbstractPlayerConnector apc, Map<Coordinates, Coordinates> moves) throws IOException {
    MoveTilesCommand command = new MoveTilesCommand(apc.getPlayer().getPlayerName(), apc.getGameId(), moves);
    String req = gson.toJson(command);
    PrintWriter epson = new PrintWriter(((PlayerConnectorSocket) apc).getConnector().getOutputStream(), true,
        StandardCharsets.UTF_8);
    epson.println(req);
  };

  @Override
  public void getMessageHandler(){
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
