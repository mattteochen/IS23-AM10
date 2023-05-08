package it.polimi.is23am10.client;

import java.net.InetAddress;
import java.net.UnknownHostException;

import com.google.gson.Gson;

import it.polimi.is23am10.client.userinterface.UserInterface;
import it.polimi.is23am10.server.network.messages.AbstractMessage;
import it.polimi.is23am10.server.network.messages.ErrorMessage;
import it.polimi.is23am10.server.network.messages.ChatMessage;
import it.polimi.is23am10.server.network.playerconnector.interfaces.IPlayerConnector;
import it.polimi.is23am10.server.network.virtualview.VirtualView;

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
   * @param pc Player connector.
   * @param ui User interface.
   * @throws UnknownHostException On localhost retrieval failure.
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
  public UserInterface userInterface;

  /**
   * Detected if the use has requested a clean disconnection.
   *
   * @return The disconnection flag.
   *
   */
  protected boolean hasRequestedDisconnection() {
    return requestedDisconnection;
  }

  /**
   * Show the message parsed.
   * 
   * @param msg The message to show.
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
    }
  }
}
