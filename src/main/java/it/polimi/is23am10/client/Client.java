package it.polimi.is23am10.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import it.polimi.is23am10.client.userinterface.UserInterface;
import it.polimi.is23am10.client.userinterface.helpers.CLIStrings;
import it.polimi.is23am10.server.model.game.Game.GameStatus;
import it.polimi.is23am10.server.model.player.Player;
import it.polimi.is23am10.server.model.player.exceptions.NullPlayerNameException;
import it.polimi.is23am10.server.network.messages.AbstractMessage;
import it.polimi.is23am10.server.network.messages.AvailableGamesMessage;
import it.polimi.is23am10.server.network.messages.ErrorMessage;
import it.polimi.is23am10.server.network.messages.GameMessage;
import it.polimi.is23am10.server.network.messages.ErrorMessage.ErrorSeverity;
import it.polimi.is23am10.server.network.messages.ChatMessage;
import it.polimi.is23am10.server.network.playerconnector.AbstractPlayerConnector;
import it.polimi.is23am10.server.network.playerconnector.interfaces.IPlayerConnector;
import it.polimi.is23am10.server.network.virtualview.VirtualView;
import it.polimi.is23am10.utils.CommandSyntaxValidator;
import it.polimi.is23am10.utils.Coordinates;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
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
    gson = new GsonBuilder()
    .registerTypeAdapter(AbstractMessage.class, new MessageDeserializer())
    .create();
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
   * List of available games set when a message from getAvailableGames is
   * received.
   */
  protected List<VirtualView> availableGames;

  /**
   * Game id reference.
   */
  protected UUID gameIdRef;

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
        VirtualView v = gson.fromJson(msg.getMessage(), VirtualView.class);
        setVirtualView(v);
        setGameIdRef(v.getGameId());
        userInterface.displayVirtualView(v);
        break;
      case ERROR_MESSAGE:
        userInterface.displayError((ErrorMessage) msg);
        break;
      case AVAILABLE_GAMES:
        Type listOfMyClassObject = new TypeToken<ArrayList<VirtualView>>() {}.getType();
        List<VirtualView> availableGamesList = gson.fromJson(msg.getMessage(), listOfMyClassObject);
        setAvailableGames(availableGamesList);
        userInterface.displayAvailableGames(availableGamesList);
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
   * Available games param setter.
   * 
   * @param ag list of available games
   */
  protected void setAvailableGames(List<VirtualView> ag) {
    this.availableGames = ag;
  }

 /**
  * GameIdRef setter.
  * @param id game id ref
  */ 
  protected void setGameIdRef(UUID id){
    this.gameIdRef = id;
  }

 /**
  * GameIdRef getter.
  *
  */ 
  synchronized protected UUID getGameIdRef(){
    return gameIdRef;
  }

  /**
   * Virtual view getter.
   * 
   * @return virtual view
   */
  protected VirtualView getVirtualView(){
    return virtualView;
  }

  /**
   * Virtual view setter.
   * 
   * @return virtual view
   */
  protected void setVirtualView(VirtualView vv){
    this.virtualView = vv;
  }

  /**
   * Abstract method that send command to get all available games.
   * 
   * @param apc abstract player connector
   * @throws IOException
   * @throws InterruptedException
   */
  abstract void getAvailableGames(AbstractPlayerConnector apc) throws IOException, InterruptedException;

  /**
   * Abstract method that send command to start a new game.
   * 
   * @param apc          abstract player connector
   * @param playerName   selected player name
   * @param maxPlayerNum max number of players selected
   * @throws IOException
   */
  abstract void startGame(AbstractPlayerConnector apc, String playerName, int maxPlayerNum) throws IOException;

  /**
   * Abstract method that send command to add a new player.
   * 
   * @param apc        abstract player connector
   * @param playerName selected player name
   * @param gameId     selected game id
   * @throws IOException
   */
  abstract void addPlayer(AbstractPlayerConnector apc, String playerName, UUID gameId) throws IOException;

  /**
   * Abstract method that send command to move tiles.
   * 
   * @param apc   abstract player connector
   * @param moves map of moves
   * @throws IOException
   */
  abstract void moveTiles(AbstractPlayerConnector apc, Map<Coordinates, Coordinates> moves) throws IOException;

/**
 * Abstract function that send chat message
 * @param apc abstract player connector
 * @param msg chat message
 * @throws IOException
 */
  abstract void sendChatMessage(AbstractPlayerConnector apc, ChatMessage msg) throws IOException;

  /**
   * Handling function for move tiles command.
   * 
   * @param apc abstract player connector
   * @param br  buffered reader
   * @throws IOException
   */
  protected void handleCommands(AbstractPlayerConnector apc, BufferedReader br) throws IOException {

    String fullCommand = br.readLine();
    String command = fullCommand.split(" ")[0];

    switch (command) {
      case "chat":
        // This selects only the part between double quotes which is gonna be the message sent.
        String msg = fullCommand.split("\"")[1];
        //If the second string begins with double quotes, there's no receiver and the message is broadcast
        if(fullCommand.split(" ")[1].startsWith("\"")){
          sendChatMessage(apc, new ChatMessage(apc.getPlayer(), msg));
        }else{
          String receiverName = fullCommand.split(" ")[1];
          sendChatMessage(apc, new ChatMessage(apc.getPlayer(), msg, receiverName));
        }
        break;
      case "logout":
        // TODO: add logout command
        break;
      case "move":
        while(getVirtualView() == null){
        }
        if (apc.getPlayer().getPlayerName().equals(getVirtualView().getActivePlayer().getPlayerName()) 
            && getVirtualView().getStatus() != GameStatus.WAITING_FOR_PLAYERS ) {
          Map<Coordinates, Coordinates> moves = new HashMap<Coordinates, Coordinates>();

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
            if ((fullCommand.split(" ").length - (nMove + 1) * 3 + 1) >= 0) {
              String coordBoard = fullCommand.split(" ")[nMove * 3 + 1];
              String arrow = fullCommand.split(" ")[nMove * 3 + 2];
              String coordBookshelf = fullCommand.split(" ")[nMove * 3 + 3];

              if (CommandSyntaxValidator.validateCoord(coordBoard)
                  && CommandSyntaxValidator.validateCoord(coordBookshelf)
                  && arrow.equals("->")) {
                Integer xBoardCoord = coordBoard.charAt(0) - '0';
                Integer yBoardCoord = coordBoard.charAt(1) - '0';
                Integer xBookshelfCoord = coordBookshelf.charAt(0) - '0';
                Integer yBookshelfCoord = coordBookshelf.charAt(1) - '0';
                Coordinates boardCoord = new Coordinates(xBoardCoord, yBoardCoord);
                Coordinates bsCoord = new Coordinates(xBookshelfCoord, yBookshelfCoord);
                moves.put(boardCoord,bsCoord);
              } else {
                System.out.println("🛑 Invalid syntax of move command.");
              }
            } else {
              break;
            }
          }
          // Checks if no valid moves were added
          if (moves.isEmpty()) {
            System.out.println("🛑 No valid moves found.");
          } else {
            System.out.println(moves);
            moveTiles(apc, moves);
          }
          break;
        }else{
          System.out.println("Not your turn");
        }
      default:
    }
  }

  /**
   * Handling function for name selection.
   * 
   * @param apc abstract player connector
   * @param br  buffered reader
   * @return player name selected
   * @throws IOException
   */
  protected String handlePlayerNameSelection(AbstractPlayerConnector apc, BufferedReader br) throws IOException {
    // Select only the string before the space if the client writes more words
    String selectedPlayerName = br.readLine().split(" ")[0];
    Player p = new Player();
    try {
      p.setPlayerName(selectedPlayerName);
      apc.setPlayer(p);
    } catch (NullPlayerNameException e) {
      userInterface.displayError(new ErrorMessage("Null player name", p, ErrorSeverity.ERROR));
    }
    return selectedPlayerName;
  }

  /**
   * Handling function for game selection.
   * 
   * @param apc                abstract player connector
   * @param selectedGameId     game id selected
   * @param br                 buffered reader
   * @param selectedPlayerName player name selected
   * @throws IOException
   * @throws InterruptedException
   * @throws NullPlayerNameException
   */
  protected void handleGameSelection(AbstractPlayerConnector apc, UUID selectedGameId, BufferedReader br,
      String selectedPlayerName) throws IOException, InterruptedException, NullPlayerNameException {
        
        // Executed if I still haven't selected a game
      if (apc.getGameId() == null) {
      getAvailableGames(apc);
  
      System.out.println(CLIStrings.joinOrCreateString);
  
      String fullCommand = br.readLine();
      String command = fullCommand.split(" ")[0];
      Integer maxPlayers = null;
      switch (command) {
        case "j":
          String idx = fullCommand.split(" ")[1];
          if (CommandSyntaxValidator.validateGameIdx(idx, availableGames.size())) {
            selectedGameId = availableGames.get(Integer.parseInt(idx)).getGameId();
            System.out.println("Joining game "+selectedGameId);
            addPlayer(apc, selectedPlayerName, selectedGameId);
            while(getGameIdRef() == null){
            }
            apc.setGameId(getGameIdRef());
            System.out.println("Joined game "+selectedGameId);
          } else {
            userInterface.displayError(new ErrorMessage("Failed to select game", ErrorSeverity.CRITICAL));
          }
          break;
        case "c":
          String numMaxPlayers = fullCommand.split(" ")[1];
          if (CommandSyntaxValidator.validateMaxPlayer(numMaxPlayers)) {
            maxPlayers = Integer.parseInt(numMaxPlayers);
            System.out.println("Creating game");
            startGame(apc, selectedPlayerName, maxPlayers);
            while(getGameIdRef() == null){
            }
            apc.setGameId(getGameIdRef());
            System.out.println("Created game");
            System.out.println(apc.getGameId());
            System.out.println(apc.getPlayer().getPlayerName());
          } else {
            userInterface.displayError(new ErrorMessage("Failed to create game", ErrorSeverity.CRITICAL));
          }
          break;
        case "q":
          apc.getPlayer().setPlayerName(null);
          apc.setGameId(null);
          break;
        default:
      }
    }
  }

  /**
   * Custom deserializer class definition for {@link Gson} usage.
   * This works on polymorphic {@link AbstractCommand} objects.
   * 
   */
  class MessageDeserializer implements JsonDeserializer<AbstractMessage> {
    @Override
    public AbstractMessage deserialize(
        JsonElement json, Type typeOfT, JsonDeserializationContext context)
        throws JsonParseException {
      JsonObject jsonObject = json.getAsJsonObject();

      String className = "";
      try {
        className = jsonObject.get("className").getAsString();
      } catch (Exception e) {
        throw new JsonParseException(e);
      }

      switch (className) {
        case "it.polimi.is23am10.server.network.messages.GameMessage":
          return context.deserialize(jsonObject, GameMessage.class);
        case "it.polimi.is23am10.server.network.messages.ChatMessage":
          return context.deserialize(jsonObject, ChatMessage.class);
        case "it.polimi.is23am10.server.network.messages.AvailableGamesMessage":
          return context.deserialize(jsonObject, AvailableGamesMessage.class);
        case "it.polimi.is23am10.server.network.messages.ErrorMessage":
          return context.deserialize(jsonObject, ErrorMessage.class);
        default:
          throw new JsonParseException("Unknown class name: " + className);
      }
    }
  }
}
