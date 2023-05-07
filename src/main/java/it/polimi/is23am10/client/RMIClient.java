package it.polimi.is23am10.client;

import java.net.UnknownHostException;
import java.rmi.registry.Registry;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import it.polimi.is23am10.client.interfaces.AlarmConsumer;
import it.polimi.is23am10.client.userinterface.UserInterface;
import it.polimi.is23am10.server.command.AbstractCommand;
import it.polimi.is23am10.server.command.SnoozeGameTimerCommand;
import it.polimi.is23am10.server.command.StartGameCommand;
import it.polimi.is23am10.server.controller.ServerControllerAction;
import it.polimi.is23am10.server.controller.ServerControllerRmiBindings;
import it.polimi.is23am10.server.controller.interfaces.IServerControllerAction;
import it.polimi.is23am10.server.model.player.Player;
import it.polimi.is23am10.server.network.messages.AbstractMessage;
import it.polimi.is23am10.server.network.playerconnector.PlayerConnectorRmi;
import it.polimi.is23am10.server.network.playerconnector.interfaces.IPlayerConnector;

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
   * Rmi alarm snoozer.
   * 
   */
  protected AlarmConsumer snoozer = () -> {
    //TODO: refactor after this https://github.com/mattteochen/IS23-AM10/issues/121
    if (!hasJoined()) {
      return;
    }
    try {
      PlayerConnectorRmi playerConnectorRmi = (PlayerConnectorRmi) playerConnector;
      SnoozeGameTimerCommand cmd = new SnoozeGameTimerCommand(playerConnectorRmi.getPlayer().getPlayerName());
      serverControllerActionServer.execute(playerConnectorRmi, cmd);
    } catch(RemoteException e) {
      System.out.println("🛑 " + e.getMessage());
    }
  };

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
   * {@inheritdoc}.
   * 
   */
  @Override
  protected boolean hasJoined() {
    //TODO: consider further checks as gameID
    PlayerConnectorRmi playerConnectorRmi = (PlayerConnectorRmi) playerConnector;
    return (playerConnectorRmi.getPlayer() != null &&
      playerConnectorRmi.getPlayer().getPlayerName() != null);
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

    alarm.scheduleAtFixedRate(new AlarmTask(snoozer),
      ALARM_INITIAL_DELAY_MS, ALARM_INTERVAL_MS);

    final PlayerConnectorRmi playerConnectorRmi = (PlayerConnectorRmi) playerConnector;

    while (!hasRequestedDisconnection()) {

      // TODO: implement user requests
      // if any new user request, process it (if virtual view has not declared that it
      // is this player turn, skip).

      // Some hints for the above's implementer: This is a start game request demo, enable this to test, to be removed for the real client request.
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

      // retrieve and show server messages
      try {
        // rmi binding not available
        if (!hasJoined) {
          continue;
        }
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
  }
}
