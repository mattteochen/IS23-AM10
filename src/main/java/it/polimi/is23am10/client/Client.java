package it.polimi.is23am10.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.google.gson.Gson;

import it.polimi.is23am10.client.userinterface.UserInterface;
import it.polimi.is23am10.client.userinterface.helpers.CLIStrings;
import it.polimi.is23am10.server.model.player.Player;
import it.polimi.is23am10.server.model.player.exceptions.NullPlayerNameException;
import it.polimi.is23am10.server.network.messages.AbstractMessage;
import it.polimi.is23am10.server.network.messages.ErrorMessage;
import it.polimi.is23am10.server.network.messages.ErrorMessage.ErrorSeverity;
import it.polimi.is23am10.server.network.messages.ChatMessage;
import it.polimi.is23am10.server.network.playerconnector.AbstractPlayerConnector;
import it.polimi.is23am10.server.network.playerconnector.interfaces.IPlayerConnector;
import it.polimi.is23am10.server.network.virtualview.VirtualView;
import it.polimi.is23am10.utils.CommandSyntaxValidator;
import it.polimi.is23am10.utils.Coordinates;

/**
 * An abstract class representing the app running in client mode. Holds the
 * three
 * elements needed for proper functioning:
 * <ul>
 * <li>Networking: Player Connector</li>
 * <li>Game state: VirtualView</li>
 * <li>UI/UX: UserInterface</li>
 * </ul>
 * 
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 * 
 */
public abstract class Client implements Runnable {

  /**
   * Protected constructor for client using Socket as communication method.
   * 
   * @param pc player connector
   * @param ui user interface
   */
  protected Client(IPlayerConnector pc, UserInterface ui) throws UnknownHostException {
    playerConnector = pc;
    userInterface = ui;
    serverAddress = InetAddress.getLocalHost();
    gson = new Gson();
    requestedDisconnection = false;
  }

  /**
   * Clean disconnection request.
   * 
   */
  private boolean requestedDisconnection;

  /**
   * A {@link Gson} instance to serialize and deserialize commands.
   * 
   */
  protected Gson gson;

  /**
   * The server host IP address.
   * 
   */
  protected InetAddress serverAddress;

  /**
   * Player connector. Allows the client to communicate with the server
   * and receive updates (game snapshots, chat messages)
   */
  protected IPlayerConnector playerConnector;

  /**
   * Instance of the game currently played on client.
   * Initially null, filled when joining games, updated constantly
   * at each turn with the updated instance arriving in playerConnector's queue
   * Completely replaced when starting new games.
   */
  protected VirtualView virtualView;

  /**
   * Interface used for communicating with the user. Can be either
   * graphical or textual. Only output methods are exposed by interface.
   */
  protected UserInterface userInterface;

  /**
   * Detected if the use has requested a clean disconnection.
   *
   * @returns The disconnection flag.
   *
   */
  protected boolean hasRequestedDisconnection() {
    return requestedDisconnection;
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
        userInterface.displayChatMessage((ChatMessage) msg);
        break;
      case GAME_SNAPSHOT:
        userInterface.displayVirtualView(gson.fromJson(msg.getMessage(), VirtualView.class));
        break;
      case ERROR_MESSAGE:
        userInterface.displayError((ErrorMessage) msg);
        break;
      default:
        break;
    }
  }

  /**
   * Abstract method that creates and run the message handler.
   * 
   */
  public abstract void getMessageHandler();

  /**
   * Abstract method that send command to get all available games.
   * @param apc abstract player connector
   * @throws IOException
   * @throws InterruptedException
   */
  abstract void getAvailableGames(AbstractPlayerConnector apc) throws IOException, InterruptedException;

  /**
   * Abstract method that send command to start a new game.
   * @param apc abstract player connector
   * @param playerName selected player name
   * @param maxPlayerNum max number of players selected
   * @throws IOException
   */
  abstract void startGame(AbstractPlayerConnector apc, String playerName, int maxPlayerNum) throws IOException;

/**
 * Abstract method that send command to add a new player.
 * @param apc abstract player connector
 * @param playerName selected player name
 * @param gameId selected game id
 * @throws IOException
 */
  abstract void addPlayer(AbstractPlayerConnector apc, String playerName, UUID gameId) throws IOException;

  /**
   * Abstract method that send command to move tiles.
   * 
   * @param apc abstract player connector
   * @param moves map of moves
   * @throws IOException
   */
  abstract void moveTiles(AbstractPlayerConnector apc, Map<Coordinates, Coordinates> moves) throws IOException;

  /**
   * Handling function for move tiles command.
   * 
   * @param apc abstract player connector
   * @param br buffered reader
   * @throws IOException
   */
  protected void handleMoveCommand(AbstractPlayerConnector apc, BufferedReader br) throws IOException {
    System.out.println(CLIStrings.moveTilesInviteString);

    // TODO: show here board and bookshelf

    String fullCommand = br.readLine();
    String command = fullCommand.split(" ")[0];

    switch (command) {
      case "move":
        HashMap<Coordinates, Coordinates> moves = new HashMap<>();

        // reads a string containing coordinates of a tile
        for (int nMove = 0; nMove < 3; nMove++) {
          /*
           * This checks the correct number of moves we are playing,
           * since the single move syntax is "ab -> cd ef -> gh" we want
           * that we have groups of three strings for each move: "ab" "->" "cd".
           * To do so I'm checking that the numbers of strings in the full line
           * (fullCommand)
           * has 3 more strings for each supposed move.
           * If I have for example the last move which is "eb ->", so if it's incomplete,
           * or if it is the fourth move, it will be ignored.
           * 
           */
          if ((fullCommand.split(" ").length - (nMove + 1) * 3) >= 0) {
            String coordBoard = fullCommand.split(" ")[nMove * 3];
            String arrow = fullCommand.split(" ")[nMove * 3 + 1];
            String coordBookshelf = fullCommand.split(" ")[nMove * 3 + 2];

            if (CommandSyntaxValidator.validateCoord(coordBoard)
                && CommandSyntaxValidator.validateCoord(coordBookshelf)
                && arrow.equals("->")) {
              Integer xBoardCoord = coordBoard.charAt(0) - '0';
              Integer yBoardCoord = coordBoard.charAt(1) - '0';
              Integer xBookshelfCoord = coordBookshelf.charAt(0) - '0';
              Integer yBookshelfCoord = coordBookshelf.charAt(1) - '0';
              moves.put(new Coordinates(xBoardCoord, yBoardCoord),
                  new Coordinates(xBookshelfCoord, yBookshelfCoord));
            } else {
              System.out.println("🛑 Invalid syntax of move command.");
              // TODO: throw exception invalid move
            }
          } else {
            break;
          }
        }

        // Checks if no valid moves were added
        if (moves.isEmpty()) {
          System.out.println("🛑 No valid moves found.");
          // TODO: throw an exception
        } else {
          moveTiles(apc, moves);
        }
        break;
      default:
    }
  }

  /**
   * Handling function for name selection.
   * 
   * @param apc abstract player connector
   * @param br buffered reader
   * @return player name selected
   * @throws IOException
   */
  protected String handlePlayerNameSelection(AbstractPlayerConnector apc, BufferedReader br) throws IOException {
    System.out.println(CLIStrings.insertPlayerNameString);
    // Select only the string before the space if the client writes more words
    String selectedPlayerName = br.readLine().split(" ")[0];
    Player p = new Player();
    try {
      p.setPlayerName(selectedPlayerName);
      apc.setPlayer(p);
    } catch (NullPlayerNameException e) {
      // TODO: add custom bla
      e.printStackTrace();
    }
    return selectedPlayerName;
  }

  /**
   *  Handling function for game selection.
   * 
   * @param apc abstract player connector
   * @param selectedGameId game id selected
   * @param br buffered reader
   * @param selectedPlayerName player name selected
   * @throws IOException
   * @throws InterruptedException
   */
  protected void handleGameSelection(AbstractPlayerConnector apc, UUID selectedGameId, BufferedReader br,
      String selectedPlayerName) throws IOException, InterruptedException {
    getAvailableGames(apc);

    System.out.println(CLIStrings.moveTilesInviteString);

    String fullCommand = br.readLine();
    String command = fullCommand.split(" ")[0];
    Integer maxPlayers = null;

    // Executed if I still haven't selected a game
    if (selectedGameId == null) {
      switch (command) {
        case "j":
          String idx = fullCommand.split(" ")[1];
          if (CommandSyntaxValidator.validateGameIdx(idx, 4)) {
            // selectedGameId = availableGames.get(Integer.parseInt(idx)).getGameId();
            selectedGameId = UUID.fromString(fullCommand.split(" ")[2]);
            addPlayer(apc, selectedPlayerName, selectedGameId);
            apc.setGameId(selectedGameId);
          } else {
            userInterface.displayError(new ErrorMessage("Failed to select game", ErrorSeverity.CRITICAL));
          }
          break;
        case "n":
          String numMaxPlayers = fullCommand.split(" ")[1];
          if (CommandSyntaxValidator.validateMaxPlayer(numMaxPlayers)) {
            maxPlayers = Integer.parseInt(numMaxPlayers);
            startGame(apc, selectedPlayerName, maxPlayers);
            apc.setGameId(selectedGameId);
          } else {
            userInterface.displayError(new ErrorMessage("Failed to create game", ErrorSeverity.CRITICAL));
          }
          break;
        case "q":
          selectedPlayerName = null;
          selectedGameId = null;
          break;
        default:
      }
    }
  }

}
