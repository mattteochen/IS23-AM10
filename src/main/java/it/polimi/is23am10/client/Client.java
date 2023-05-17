package it.polimi.is23am10.client;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Timer;
import java.util.TimerTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import it.polimi.is23am10.client.interfaces.AlarmConsumer;
import it.polimi.is23am10.client.userinterface.UserInterface;
import it.polimi.is23am10.server.model.game.Game.GameStatus;
import it.polimi.is23am10.server.model.items.board.exceptions.BoardGridColIndexOutOfBoundsException;
import it.polimi.is23am10.server.model.items.board.exceptions.BoardGridRowIndexOutOfBoundsException;
import it.polimi.is23am10.server.model.items.bookshelf.exceptions.BookshelfGridColIndexOutOfBoundsException;
import it.polimi.is23am10.server.model.items.bookshelf.exceptions.BookshelfGridRowIndexOutOfBoundsException;
import it.polimi.is23am10.server.model.player.Player;
import it.polimi.is23am10.server.model.player.exceptions.NullPlayerIdException;
import it.polimi.is23am10.server.model.player.exceptions.NullPlayerNameException;
import it.polimi.is23am10.server.network.messages.AbstractMessage;
import it.polimi.is23am10.server.network.messages.AvailableGamesMessage;
import it.polimi.is23am10.server.network.messages.ChatMessage;
import it.polimi.is23am10.server.network.messages.ErrorMessage;
import it.polimi.is23am10.server.network.messages.ErrorMessage.ErrorSeverity;
import it.polimi.is23am10.server.network.messages.GameMessage;
import it.polimi.is23am10.server.network.playerconnector.AbstractPlayerConnector;
import it.polimi.is23am10.server.network.playerconnector.interfaces.IPlayerConnector;
import it.polimi.is23am10.server.network.virtualview.VirtualView;
import it.polimi.is23am10.utils.CommandSyntaxValidator;
import it.polimi.is23am10.utils.Coordinates;
import it.polimi.is23am10.utils.ErrorTypeString;
import it.polimi.is23am10.utils.MoveCommandHelper;
import it.polimi.is23am10.utils.MovesValidator;
import it.polimi.is23am10.utils.exceptions.NullIndexValueException;
import it.polimi.is23am10.utils.exceptions.WrongBookShelfPicksException;
import it.polimi.is23am10.utils.exceptions.WrongGameBoardPicksException;
import it.polimi.is23am10.utils.exceptions.WrongMovesNumberException;

/**
 * Custom lock object.
 */
class LockObject implements Serializable {
}

/**
 * An abstract class representing the app running in client mode. Holds the three elements needed
 * for proper functioning:
 *
 * <ul>
 *   <li>Networking: Player Connector
 *   <li>Game state: VirtualView
 *   <li>UI/UX: UserInterface
 * </ul>
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public abstract class Client extends UnicastRemoteObject implements IClient {
  /**
   * Custom lock object.
   * 
   */
  private LockObject gameRefLock = new LockObject();

  /**
   * Custom lock object.
   * 
   */
  private LockObject hasDuplicateLock = new LockObject();

  /**
   * Custom lock object.
   * 
   */
  private LockObject virtualViewLock = new LockObject();

  /** Custom lock object. */
  private static Object availableServersLock = new Object();

  /** Custom lock object. */
  private static Object playerConnectorLock = new Object();

  /**
   * Protected constructor for client using Socket as communication method.
   *
   * @param pc Player connector.
   * @param ui User interface.
   * @throws UnknownHostException On localhost retrieval failure.
   */
  protected Client(IPlayerConnector pc, UserInterface ui) throws UnknownHostException, RemoteException {
    playerConnector = pc;
    userInterface = ui;
    serverAddress = InetAddress.getLocalHost();
    gson =
        new GsonBuilder()
            .registerTypeAdapter(AbstractMessage.class, new MessageDeserializer())
            .create();
    requestedDisconnection = false;
    alarm = new Timer();
    clientStatus = ClientGameStatus.INIT;
  }

  /**
   * Check if the current client has joined a game or not.
   *
   * @return The requested flag.
   */
  protected abstract boolean hasJoined();

  /** Client alarm interval in milliseconds. */
  protected final int ALARM_INTERVAL_MS = 5000;

  /** Client alarm initial delay in milliseconds. */
  protected final int ALARM_INITIAL_DELAY_MS = 0;

  /** Clean disconnection request. */
  private boolean requestedDisconnection;

  /** Duplicate name error flag. */
  private boolean hasDuplicateName;

  /**
   * A {@link Gson} instance to serialize and deserialize commands.
   * 
   */
  protected transient Gson gson;

  /**
   * The server host IP address.
   * 
   */
  protected transient InetAddress serverAddress;

  /** The three possible states in which the client can be. */
  public enum ClientGameStatus {
    /** Starting state. Player hasn't selected name yet. */
    INIT,
    /** Player selected name but not yet the game. */
    GAME_SELECTION,
    /** Player is in the game. */
    PLAYING
  }

  /** Current status of the client. */
  protected ClientGameStatus clientStatus;

  /**
   * Player connector. Allows the client to communicate with the server and receive updates (game
   * snapshots, chat messages)
   */
  protected static IPlayerConnector playerConnector;

  /**
   * Retrieve the player connector intance.
   *
   * @return The {@link IPlayerConnector}.
   */
  // TODO: add getter
  public static IPlayerConnector getPlayerConnector() {
    synchronized (playerConnectorLock) {
      return playerConnector;
    }
  }

  /**
   * Instance of the game currently played on client. Initially null, filled when joining games,
   * updated constantly at each turn with the updated instance arriving in playerConnector's queue
   * Completely replaced when starting new games.
   */
  protected VirtualView virtualView;

  /**
   * Interface used for communicating with the user. Can be either graphical or textual. Only output
   * methods are exposed by interface.
   */
  public UserInterface userInterface;

  /** List of available games set when a message from getAvailableGames is received. */
  protected static List<VirtualView> availableGames;

  /** Game id reference. */
  protected UUID gameIdRef;

  /*
   * Application timer.
   */
  protected transient Timer alarm;

  /**
   * Detected if the use has requested a clean disconnection.
   *
   * @return The disconnection flag.
   */
  protected boolean hasRequestedDisconnection() {
    return requestedDisconnection;
  }

  /**
   * Show the received message to the client.
   * 
   * @param msg The message. Its dynamic type is inferred by {@link Gson}.
   * @throws RemoteException
   *
   */
  public void showServerMessage(AbstractMessage msg) throws RemoteException {
    if (gson == null) {
      gson = new GsonBuilder()
          .registerTypeAdapter(AbstractMessage.class, new MessageDeserializer())
          .create();
    }
    switch (msg.getMessageType()) {
      case CHAT_MESSAGE:
        userInterface.displayChatMessage((ChatMessage) msg);
        break;
      case GAME_SNAPSHOT:
        VirtualView v = gson.fromJson(msg.getMessage(), VirtualView.class);
        setVirtualView(v);
        // do not overwrite game id
        if (getGameIdRef() == null) {
          setGameIdRef(v.getGameId());
        }
        userInterface.displayVirtualView(v);
        break;
      case ERROR_MESSAGE:
        userInterface.displayError((ErrorMessage) msg);
        if (msg.getMessage().equals(ErrorTypeString.ERROR_ADDING_PLAYERS)) {
          setHasDuplicateName(true);
        }
        break;
      case AVAILABLE_GAMES:
        Type listOfMyClassObject = new TypeToken<ArrayList<VirtualView>>() {}.getType();
        List<VirtualView> availableGamesList = gson.fromJson(msg.getMessage(), listOfMyClassObject);
        setActiveGameServers(availableGamesList);
        userInterface.displayAvailableGames(availableGamesList);
        break;
      default:
        break;
    }
  }

  /**
   * Abstract method that run the client into the game.
   * 
   */
  public abstract void run();

  /**
   * Available games param setter.
   *
   * @param ag list of available games
   */
  protected void setActiveGameServers(List<VirtualView> ag) {
    synchronized (availableServersLock) {
      availableGames = ag;
    }
  }

  /**
   * Available games param getter.
   *
   * @return The list of available games
   */
  public static List<VirtualView> getActiveGameServers() {
    synchronized (availableServersLock) {
      return availableGames;
    }
  }

  /**
   * GameIdRef setter.
   *
   * @param id game id ref
   */
  protected void setGameIdRef(UUID id) {
    synchronized (gameRefLock) {
      this.gameIdRef = id;
    }
  }

  /** GameIdRef getter. */
  protected UUID getGameIdRef() {
    synchronized (gameRefLock) {
      return gameIdRef;
    }
  }

  /**
   * Duplicate name flag setter.
   *
   * @param b flag
   */
  protected void setHasDuplicateName(boolean b) {
    synchronized (hasDuplicateLock) {
      this.hasDuplicateName = b;
    }
  }

  /**
   * Duplicate name flag getter.
   *
   * @return flag
   */
  protected boolean getHasDuplicateName() {
    synchronized (hasDuplicateLock) {
      return hasDuplicateName;
    }
  }

  /**
   * User interface setter.
   *
   * @param ui user interface.
   */
  protected void setUserInterface(UserInterface ui) {
    this.userInterface = ui;
  }

  /**
   * User interface getter.
   *
   * @return user interface.
   */
  protected UserInterface getUserInterface() {
    return userInterface;
  }

  /**
   * Virtual view getter.
   *
   * @return virtual view
   */
  protected VirtualView getVirtualView() {
    synchronized (virtualViewLock) {
      return virtualView;
    }
  }

  /** Virtual view setter. */
  protected void setVirtualView(VirtualView vv) {
    synchronized (virtualViewLock) {
      this.virtualView = vv;
    }
  }

  /**
   * Client status getter.
   *
   * @return status.
   */
  public ClientGameStatus getClientStatus() {
    return clientStatus;
  }

  /**
   * Client status setter.
   *
   * @param clientStatus new status of client.
   */
  public void setClientStatus(ClientGameStatus clientStatus) {
    this.clientStatus = clientStatus;
  }

  /**
   * Abstract method that send command to get all available games.
   *
   * @param apc abstract player connector
   * @throws IOException
   * @throws InterruptedException
   */
  abstract void getAvailableGames(AbstractPlayerConnector apc)
      throws IOException, InterruptedException;

  /**
   * Abstract method that send command to start a new game.
   *
   * @param apc abstract player connector
   * @param playerName selected player name
   * @param maxPlayerNum max number of players selected
   * @throws IOException
   */
  abstract void startGame(AbstractPlayerConnector apc, String playerName, int maxPlayerNum)
      throws IOException;

  /**
   * Abstract method that send command to add a new player.
   *
   * @param apc abstract player connector
   * @param playerName selected player name
   * @param gameId selected game id
   * @throws IOException
   */
  abstract void addPlayer(AbstractPlayerConnector apc, String playerName, UUID gameId)
      throws IOException;

  /**
   * Abstract method that send command to move tiles.
   *
   * @param apc abstract player connector
   * @param moves map of moves
   * @throws IOException
   */
  abstract void moveTiles(AbstractPlayerConnector apc, Map<Coordinates, Coordinates> moves)
      throws IOException;

  /**
   * Abstract function that send chat message
   *
   * @param apc abstract player connector
   * @param msg chat message
   * @throws IOException
   */
  abstract void sendChatMessage(AbstractPlayerConnector apc, ChatMessage msg) throws IOException;

  /**
   * Abstract function that snoozes virtual alarm.
   *
   * @param apc abstract player connector
   * @param msg chat message
   * @throws IOException
   */
  abstract void snoozeAlarm() throws IOException;

  /**
   * Handling function for move tiles command.
   *
   * @param apc abstract player connector
   * @throws IOException
   */
  protected void handleCommands(AbstractPlayerConnector apc) throws IOException {

    if (clientStatus == ClientGameStatus.GAME_SELECTION) {
      clientStatus = ClientGameStatus.PLAYING;
    }

    String fullCommand = userInterface.getUserInput();

    if (fullCommand != null) {
      String command = fullCommand.stripLeading().split(" ")[0];

      switch (command) {
        case "chat":
          if (fullCommand.stripLeading().split(" ").length > 1) {
            if (fullCommand.stripLeading().split("\"").length > 1) {
              // This selects only the part between double quotes which is gonna be the
              // message sent.
              String msg = fullCommand.split("\"")[1];
              // If the second string begins with double quotes,
              // there's no receiver and the message is broadcast
              if (fullCommand.stripLeading().split(" ")[1].startsWith("\"")) {
                sendChatMessage(apc, new ChatMessage(apc.getPlayer(), msg));
              } else {
                String receiverName = fullCommand.split(" ")[1];
                sendChatMessage(apc, new ChatMessage(apc.getPlayer(), msg, receiverName));
              }
            }
          }
          break;
        case "logout":
          // TODO: add logout command
          break;
        case "move":
          if (getVirtualView() == null) {
            userInterface.displayError(
                new ErrorMessage("Wait the game to be loaded", ErrorSeverity.ERROR));
            break;
          }
          if (apc.getPlayer()
                  .getPlayerName()
                  .equals(getVirtualView().getActivePlayer().getPlayerName())
              && getVirtualView().getStatus() != GameStatus.WAITING_FOR_PLAYERS) {

            Map<Coordinates, Coordinates> moves = new HashMap<Coordinates, Coordinates>();
            List<Coordinates> boardCoords = new ArrayList<Coordinates>();
            List<Coordinates> bsCoords = new ArrayList<Coordinates>();

            // Reads a string containing coordinates of a tile and the column index
            String[] moveArgs = fullCommand.stripLeading().split(" ");
            for (int maxArgs = 0; maxArgs < 4 && moveArgs.length - (maxArgs + 1) > 0; maxArgs++) {

              /*
               * If we receive a board coordinate input add it to the list, otherwise if it is
               * a
               * column index we are at the end of the move command and we convert that idx
               * to the right bookshelf coordinates. Then we add the mapping between board
               * coordinates
               * and bookshelf coordinates.
               */
              if (CommandSyntaxValidator.validateCoord(moveArgs[maxArgs + 1])) {
                String coordBoard = moveArgs[maxArgs + 1];
                Integer colBoardCoord = coordBoard.charAt(0) - '0';
                Integer rowBoardCoord = coordBoard.charAt(1) - '0';
                boardCoords.add(new Coordinates(rowBoardCoord, colBoardCoord));
              } else if (CommandSyntaxValidator.validateColIdx(moveArgs[maxArgs + 1])) {
                String idx = moveArgs[maxArgs + 1];
                try {
                  // Transform idx to list of coords
                  // NB: boardCoords.size() is the number of moves done
                  bsCoords =
                      MoveCommandHelper.fromColIdxToCoord(
                          idx,
                          getVirtualView().getActivePlayer().getBookshelf(),
                          boardCoords.size());
                  // I put the coords into the map
                  for (int i = 0; i < boardCoords.size(); i++) {
                    moves.put(boardCoords.get(i), bsCoords.get(i));
                  }
                  break;
                } catch (BookshelfGridColIndexOutOfBoundsException
                    | BookshelfGridRowIndexOutOfBoundsException
                    | NullIndexValueException
                    | WrongBookShelfPicksException e) {
                  userInterface.displayError(new ErrorMessage(e.getMessage(), ErrorSeverity.ERROR));
                  break;
                }
              } else {
                break;
              }
            }
            // Checks if no valid moves were added
            if (moves.isEmpty()) {
              userInterface.displayError(
                  new ErrorMessage("No valid moves found.", ErrorSeverity.ERROR));
            } else {
              try {
                MovesValidator.validateGameMoves(
                    moves,
                    virtualView.getActivePlayer().getBookshelf(),
                    virtualView.getGameBoard());
                moveTiles(apc, moves);
              } catch (BoardGridRowIndexOutOfBoundsException
                  | BoardGridColIndexOutOfBoundsException
                  | BookshelfGridColIndexOutOfBoundsException
                  | BookshelfGridRowIndexOutOfBoundsException
                  | WrongMovesNumberException
                  | WrongGameBoardPicksException
                  | NullIndexValueException
                  | WrongBookShelfPicksException e) {
                userInterface.displayError(
                    new ErrorMessage("Invalid move:" + e.getMessage(), ErrorSeverity.ERROR));
                break;
              }
            }
            break;
          } else {
            userInterface.displayError(new ErrorMessage("Not your turn.", ErrorSeverity.WARNING));
          }
          break;
        default:
          break;
      }
    }
  }

  /**
   * Handling function for name selection.
   *
   * @param apc abstract player connector
   * @return player name selected
   * @throws IOException
   */
  protected String handlePlayerNameSelection(AbstractPlayerConnector apc) throws IOException {
    // Select only the string before the space if the client writes more words
    String selectedPlayerName = userInterface.getUserInput();
    if (selectedPlayerName != null) {
      selectedPlayerName = selectedPlayerName.stripLeading();
      Player p = new Player();
      apc.setPlayer(p);
      try {
        apc.getPlayer().setPlayerName(selectedPlayerName);
      } catch (NullPlayerNameException e) {
        userInterface.displayError(new ErrorMessage("Null player name", ErrorSeverity.ERROR));
        return null;
      }
      return selectedPlayerName;
    } else {
      return null;
    }
  }

  /**
   * Empty mock of lookup init for Socket client which is overrided in RMI client
   *
   * @throws RemoteException
   * @throws NotBoundException
   */
  protected void lookupInit() throws RemoteException, NotBoundException {}

  /**
   * Handling function for game selection.
   *
   * @param apc abstract player connector
   * @param selectedPlayerName player name selected
   * @throws IOException
   * @throws InterruptedException
   */
  protected void handleGameSelection(AbstractPlayerConnector apc, String selectedPlayerName)
      throws IOException, InterruptedException {

    // Executed if I still haven't selected a game
    if (apc.getGameId() == null) {

      // We use the check over client status to perform one-time actions
      // like displaying stuff and sending
      if (clientStatus == ClientGameStatus.INIT) {
        userInterface.displayGameJoinGuide();
        clientStatus = ClientGameStatus.GAME_SELECTION;
        getAvailableGames(apc);
      }

      // TODO: use userinterface
      String fullCommand = userInterface.getUserInput();
      if (fullCommand != null) {
        String command = fullCommand.stripLeading().split(" ")[0];
        Integer maxPlayers = null;
        switch (command) {
          case "j":
            if (fullCommand.split(" ").length > 1) {
              String idx = fullCommand.split(" ")[1];
              if (CommandSyntaxValidator.validateGameIdx(idx, availableGames.size())) {
                UUID selectedGameId = availableGames.get(Integer.parseInt(idx)).getGameId();
                addPlayer(apc, selectedPlayerName, selectedGameId);
                /*
                 * Since the gameId ref is set when the message handler receives a GAME_SNAPSHOT
                 * message
                 * and since that GAME_SNAPSHOT message is received only when the player is
                 * added correctly to the game (so there's not duplicate name exception),
                 * here we know that even if the duplicateName flag is set after a few seconds,
                 * the player will not be added by mistake because it exits the while loop
                 * because of the gameId flag.
                 */
                while (getGameIdRef() == null && !getHasDuplicateName()) {}
                if (getHasDuplicateName()) {
                  userInterface.displayError(
                      new ErrorMessage("Failed to add player, retry", ErrorSeverity.CRITICAL));
                  setHasDuplicateName(false);
                  break;
                }
                apc.setGameId(getGameIdRef());
              } else {
                userInterface.displayError(
                    new ErrorMessage("Failed to select game", ErrorSeverity.CRITICAL));
              }
            } else {
              userInterface.displayError(
                  new ErrorMessage("Insert value of max players", ErrorSeverity.ERROR));
            }
            break;
          case "c":
            if (fullCommand.split(" ").length > 1) {
              String numMaxPlayers = fullCommand.split(" ")[1];
              if (CommandSyntaxValidator.validateMaxPlayer(numMaxPlayers)) {
                maxPlayers = Integer.parseInt(numMaxPlayers);
                startGame(apc, selectedPlayerName, maxPlayers);
                while (getGameIdRef() == null) {
                }
                apc.setGameId(getGameIdRef());
              } else {
                userInterface.displayError(
                    new ErrorMessage("Failed to create game", ErrorSeverity.CRITICAL));
              }
            } else {
              userInterface.displayError(
                  new ErrorMessage("Insert value of max players", ErrorSeverity.ERROR));
            }
            break;
          case "q":
            apc.setPlayer(new Player());
            apc.setGameId(null);
            break;
          default:
            break;
        }
      }
    }
  }

  /**
   * The client game core machine state.
   *
   * @param pc player connector instance.
   * @throws IOException
   * @throws NullPlayerIdException
   * @throws InterruptedException
   */
  protected void clientRunnerCore(AbstractPlayerConnector pc)
      throws IOException, InterruptedException, NullPlayerIdException {

    // First I'm gonna ask the player name
    if (pc.getPlayer() == null || pc.getPlayer().getPlayerName() == null) {
      String pn = null;
      userInterface.displaySplashScreen();
      while (pn == null || pn.equals("")) {
        pn = handlePlayerNameSelection(pc);
      }
      pc.getPlayer().setPlayerID(UUID.nameUUIDFromBytes(pn.getBytes()));
    }

    // Execute if the client is not connected to a game.
    if (pc.getGameId() == null) {
      handleGameSelection(pc, pc.getPlayer().getPlayerName());
    } else {
      // Executed if the client is connected to a game.
      handleCommands(pc);
    }
  }

  /**
   * Custom deserializer class definition for {@link Gson} usage. This works on polymorphic {@link
   * AbstractMessage} objects.
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

  /** The timer schedule execution class. */
  protected class AlarmTask extends TimerTask {
    /** The consumer to be executed. */
    AlarmConsumer task;

    /**
     * Constructor.
     *
     * @param task The consumer to be assigned.
     */
    public AlarmTask(AlarmConsumer task) {
      this.task = task;
    }

    /** {@inheritDoc} */
    public void run() {
      task.start();
    }
  }
}
