package it.polimi.is23am10.client;

import java.io.IOException;
import java.io.BufferedReader;
import java.net.UnknownHostException;
import java.rmi.registry.Registry;
import java.util.Map;
import java.util.UUID;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import it.polimi.is23am10.client.userinterface.UserInterface;
import it.polimi.is23am10.client.userinterface.helpers.CLIStrings;
import it.polimi.is23am10.server.command.AbstractCommand;
import it.polimi.is23am10.server.command.AddPlayerCommand;
import it.polimi.is23am10.server.command.GetAvailableGamesCommand;
import it.polimi.is23am10.server.command.MoveTilesCommand;
import it.polimi.is23am10.server.command.SendChatMessageCommand;
import it.polimi.is23am10.server.command.StartGameCommand;
import it.polimi.is23am10.server.controller.ServerControllerAction;
import it.polimi.is23am10.server.controller.ServerControllerRmiBindings;
import it.polimi.is23am10.server.controller.interfaces.IServerControllerAction;
import it.polimi.is23am10.server.model.player.exceptions.NullPlayerIdException;
import it.polimi.is23am10.server.model.player.exceptions.NullPlayerNameException;
import it.polimi.is23am10.server.network.messages.AbstractMessage;
import it.polimi.is23am10.server.network.messages.ChatMessage;
import it.polimi.is23am10.server.network.messages.ErrorMessage;
import it.polimi.is23am10.server.network.messages.ErrorMessage.ErrorSeverity;
import it.polimi.is23am10.server.network.playerconnector.AbstractPlayerConnector;
import it.polimi.is23am10.server.network.playerconnector.PlayerConnectorRmi;
import it.polimi.is23am10.server.network.playerconnector.interfaces.IPlayerConnector;
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
    playerConnectorServer = pcs;
    serverControllerActionServer = scas;
    rmiRegistry = reg;
  }

  /**
   * Perform all the needed lookups.
   * A gentle reminder that the {@link IPlayerConnector} bind can only be found
   * after a join or creation action.
   *
   * @param pc The player connector.
   * 
   */
  protected void lookupInit() throws RemoteException, NotBoundException {
    playerConnectorServer = (IPlayerConnector) rmiRegistry
        .lookup(ServerControllerRmiBindings.getPlayerConnectorRebindId((PlayerConnectorRmi) playerConnector));
    playerConnectorServer.setPlayer(playerConnector.getPlayer());
  }

  /**
   * Client core cycle.
   * Send user requested commands and read updates.
   * 
   */
  @Override
  public void run() {
    final PlayerConnectorRmi playerConnectorRmi = (PlayerConnectorRmi) playerConnector;
    while (!hasRequestedDisconnection()) {
      try {
        clientRunnerCore(playerConnectorRmi);
      } catch (IOException | InterruptedException e) {
        System.out.println("🛑 " + e.getMessage());
      } catch (NullPlayerNameException | NullPlayerIdException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
  }

  /**
   * {@inheritDoc}
   *
   */
  @Override
  protected void handleGameSelection(AbstractPlayerConnector apc, BufferedReader br,
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
            UUID selectedGameId = availableGames.get(Integer.parseInt(idx)).getGameId();
            System.out.println("Joining game "+selectedGameId);
            addPlayer(apc, selectedPlayerName, selectedGameId);
            try {
              lookupInit();
            } catch(NotBoundException e) {
              userInterface.displayError(new ErrorMessage("Failed to connect to the server, abortig the request", ErrorSeverity.CRITICAL));
            }
            runMessageHandler();
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
            try {
              lookupInit();
            } catch(NotBoundException e) {
              userInterface.displayError(new ErrorMessage("Failed to connect to the server, abortig the request", ErrorSeverity.CRITICAL));
            }
            runMessageHandler();
            while(getGameIdRef() == null){
            }
            apc.setGameId(getGameIdRef());
            System.out.println("Created game");
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
   * {@inheritDoc}
   *
   */
  @Override
  void getAvailableGames(AbstractPlayerConnector apc)
    throws RemoteException {
    AbstractMessage msg = serverControllerActionServer.execute(new GetAvailableGamesCommand());
    showServerMessage(msg);
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
 * {@inheritDoc}
 * 
 */
 @Override
 void sendChatMessage(AbstractPlayerConnector apc, ChatMessage msg) throws IOException {
  AbstractCommand command = new SendChatMessageCommand(msg);
  serverControllerActionServer.execute(apc, command);
 }

  /**
   * Method override that creates and starts message handler thread.
   * To be started after the {@link RMIClient#lookupInit}.
   */
  @Override
  public void runMessageHandler(){
    Thread messageHandler = new Thread(()->{
      while(!hasRequestedDisconnection()){
        try {
          AbstractMessage msg = playerConnectorServer.getMessageFromQueue();
          if (msg != null) {
            showServerMessage(msg);
          }
        } catch (InterruptedException | RemoteException e) {
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
