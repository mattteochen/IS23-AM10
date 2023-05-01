package it.polimi.is23am10.client;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import it.polimi.is23am10.client.userinterface.CommandLineInterface;
import it.polimi.is23am10.client.userinterface.UserInterface;
import it.polimi.is23am10.client.userinterface.helpers.CLIStrings;
import it.polimi.is23am10.server.command.AbstractCommand;
import it.polimi.is23am10.server.command.AddPlayerCommand;
import it.polimi.is23am10.server.command.GetAvailableGamesCommand;
import it.polimi.is23am10.server.command.MoveTilesCommand;
import it.polimi.is23am10.server.command.StartGameCommand;
import it.polimi.is23am10.server.controller.ServerControllerAction;
import it.polimi.is23am10.server.controller.ServerControllerRmiBindings;
import it.polimi.is23am10.server.controller.interfaces.IServerControllerAction;
import it.polimi.is23am10.server.model.player.Player;
import it.polimi.is23am10.server.network.messages.AbstractMessage;
import it.polimi.is23am10.server.network.messages.AvailableGamesMessage;
import it.polimi.is23am10.server.network.messages.ErrorMessage;
import it.polimi.is23am10.server.network.messages.ErrorMessage.ErrorSeverity;
import it.polimi.is23am10.server.network.playerconnector.AbstractPlayerConnector;
import it.polimi.is23am10.server.network.playerconnector.PlayerConnectorRmi;
import it.polimi.is23am10.server.network.playerconnector.interfaces.IPlayerConnector;
import it.polimi.is23am10.server.network.virtualview.VirtualView;
import it.polimi.is23am10.utils.CommandSyntaxValidator;
import it.polimi.is23am10.utils.Coordinates;

/**
 * A client using RMI as communication method.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public class RMIClient extends Client {

  /**
   * The rmi registry.
   * 
   */
  protected Registry rmiRegistry;

  /**
   * The {@link ServerControllerAction} server object.
   * 
   */
  protected IServerControllerAction serverControllerActionServer;

  /**
   * The {@link IPlayerConnector} server object.
   * 
   */
  protected IPlayerConnector playerConnectorServer;

  /**
   * A flag stating if the player has joined or not a game.
   * 
   */
  protected boolean hasJoined;

  /**
   * Public constructor for client using RMI as communication method.
   * 
   * @param pc   Player connector.
   * @param ui   User interface.
   * @param pcs  Player connector server reference.
   * @param scas Server controller action server reference.
   * @param reg  Rmi registry instance.
   */
  public RMIClient(PlayerConnectorRmi pc, UserInterface ui, IPlayerConnector pcs, IServerControllerAction scas,
      Registry reg) throws UnknownHostException {
    super(pc, ui);
    hasJoined = false;
    playerConnectorServer = pcs;
    serverControllerActionServer = scas;
    rmiRegistry = reg;
  }

  /**
   * Perform all the needed lookups.
   * A gentle reminder that the {@link IPlayerConnector} bind can only be found
   * after {@link RMIClient#hasJoined} is true.
   *
   * @param pc The player connector.
   * 
   */
  protected void lookupInit() throws RemoteException, NotBoundException {
    playerConnectorServer = (IPlayerConnector) rmiRegistry
        .lookup(ServerControllerRmiBindings.getPlayerConnectorRebindId((PlayerConnectorRmi) playerConnector));
  }

  /**
   * Client core cycle.
   * Send user requested commands and read updates.
   * 
   */
  @Override
  public void run() {
    final PlayerConnectorRmi playerConnectorRmi = (PlayerConnectorRmi) playerConnector;

    /*
     * The default values are null, I will set those values separately to
     * handle input errors in a separate way. This allows us, once selected the
     * game, to reinsert only the player name if wrong.
     */
    String selectedPlayerName = null;
    UUID selectedGameId = null;

    System.out.println(CLIStrings.welcomeString);

    while (!hasRequestedDisconnection()) {

      try {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // Execute if the client is not connected to a game.
        if (playerConnectorRmi.getGameId() == null) {
          
          // First I'm gonna ask the player name
          if (selectedPlayerName == null) {
            selectedPlayerName = handlePlayerNameSelection(playerConnectorRmi, br);
          } 

          if (selectedPlayerName != null) {
            handleGameSelection(playerConnectorRmi, selectedGameId, br, selectedPlayerName);
          }
        } else {
          // Executed if the client is connected to a game.

          // This allows the player to play the turn if he's the active player
          if (playerConnectorRmi.getPlayer().getIsActivePlayer()) {
            handleMoveCommand(playerConnectorRmi, br);
          }

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

      // TODO: implement user requests
      // if any new user request, process it (if virtual view has not declared that it
      // is this player turn, skip).

      // Some hints for the above's implementer: This is a start game request demo,
      // enable this to test, to be removed for the real client request.
      // TODO: remove when not needed anymore
      /*
       * try {
       * String playerName = "Rmi client";
       * AbstractCommand command = new StartGameCommand(playerName, 4);
       * 
       * //MANDATORY FOR THE LOOKUP: fill the connector with the chosen name as it
       * will be the identifier across all player connectors
       * //consider using a lighter player as only the name is used
       * Player p = new Player();
       * p.setPlayerName(playerName);
       * playerConnectorRmi.setPlayer(p);
       * 
       * //execute the command
       * serverControllerActionServer.execute(playerConnectorRmi, command);
       * hasJoined = true;
       * //from now on, the server has exposed the binding, let's connect
       * lookupInit();
       * //once the lookup is done, assign the current playerConnector to server ref
       * //playerConnector as for move tiles commands an equality comparison is done
       * //over the whole Player class
       * } catch (Exception e) {
       * System.out.println("🛑 " + e.getMessage());
       * }
       */

    }
  }

   /**
   * {@inheritDoc}
   *
   */
  @Override
  void getAvailableGames(AbstractPlayerConnector apc)
      throws IOException, InterruptedException {
    HashMap<Integer, VirtualView> mapIndexVirtualView = new HashMap<Integer, VirtualView>();
    Integer gameIdx = 0;
    AbstractCommand command = new GetAvailableGamesCommand();
    serverControllerActionServer.execute(apc, command);
  }

   /**
   * {@inheritDoc}
   *
   */
  @Override
  void startGame(AbstractPlayerConnector apc, String playerName, int maxPlayerNum)
      throws IOException {
    AbstractCommand command = new StartGameCommand(playerName, maxPlayerNum);
    serverControllerActionServer.execute(apc, command);
  }

   /**
   * {@inheritDoc}
   *
   */
  @Override
  void addPlayer(AbstractPlayerConnector apc, String playerName, UUID gameId) throws IOException {
    AbstractCommand command = new AddPlayerCommand(playerName, gameId);
    serverControllerActionServer.execute(apc, command);
  }

   /**
   * {@inheritDoc}
   *
   */
  @Override
  void moveTiles(AbstractPlayerConnector apc, Map<Coordinates, Coordinates> moves)
      throws IOException {
    AbstractCommand command = new MoveTilesCommand(
        apc.getPlayer().getPlayerName(), apc.getGameId(), moves);
    serverControllerActionServer.execute(apc, command);
  }

  /**
   * Method override that creates and starts message handler thread
   */
  @Override
  public void getMessageHandler(){
    PlayerConnectorRmi playerConnectorRMI = (PlayerConnectorRmi) playerConnector;
    Thread messageHandler = new Thread(()->{
      while(!hasRequestedDisconnection()){
        try {
          // rmi binding not available
          if (!hasJoined) {
            continue;
          }
          AbstractMessage msg = playerConnectorRMI.getMessageFromQueue();
          if (msg != null) {
            showServerMessage(msg);
          } else {
            // TODO: replace with custom logger
            System.out.println("🛑 Received null message");
          }
        } catch (InterruptedException e) {
          // TODO: replace with custom logger
          System.out.println("🛑 Failed to retrive message from server, bad context state " + e.getMessage());
        } catch (NullPointerException e) {
          // TODO: replace with custom logger
          System.out.println("🛑 Null pointer " + e.getMessage());
        }
      }
    });
    messageHandler.start();
  }
}
