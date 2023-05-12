package it.polimi.is23am10.client;

import java.io.IOException;
import java.net.UnknownHostException;
import java.rmi.registry.Registry;
import java.util.Map;
import java.util.UUID;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import it.polimi.is23am10.client.interfaces.AlarmConsumer;
import it.polimi.is23am10.client.userinterface.UserInterface;
import it.polimi.is23am10.server.command.AbstractCommand;
import it.polimi.is23am10.server.command.AddPlayerCommand;
import it.polimi.is23am10.server.command.GetAvailableGamesCommand;
import it.polimi.is23am10.server.command.MoveTilesCommand;
import it.polimi.is23am10.server.command.SendChatMessageCommand;
import it.polimi.is23am10.server.command.SnoozeGameTimerCommand;
import it.polimi.is23am10.server.command.StartGameCommand;
import it.polimi.is23am10.server.controller.ServerControllerAction;
import it.polimi.is23am10.server.controller.ServerControllerRmiBindings;
import it.polimi.is23am10.server.controller.interfaces.IServerControllerAction;
import it.polimi.is23am10.server.model.player.exceptions.NullPlayerIdException;
import it.polimi.is23am10.server.network.messages.AbstractMessage;
import it.polimi.is23am10.server.network.messages.ChatMessage;
import it.polimi.is23am10.server.network.messages.ErrorMessage;
import it.polimi.is23am10.server.network.messages.ErrorMessage.ErrorSeverity;
import it.polimi.is23am10.server.network.playerconnector.AbstractPlayerConnector;
import it.polimi.is23am10.server.network.playerconnector.PlayerConnectorRmi;
import it.polimi.is23am10.server.network.playerconnector.interfaces.IPlayerConnector;
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
   * Rmi alarm snoozer.
   * 
   */
  protected AlarmConsumer snoozer = () -> {
    if (!hasJoined()) {
      return;
    }
    try {
      snoozeAlarm();
    } catch(RemoteException e) {
      userInterface.displayError(
        new ErrorMessage("Internal job failed, you might loose game connection", ErrorSeverity.ERROR));
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
    playerConnectorServer = pcs;
    serverControllerActionServer = scas;
    rmiRegistry = reg;
  }

  /**
   * {@inheritDoc}
   *
   */
  @Override
  protected boolean hasJoined() {
    PlayerConnectorRmi playerConnectorRmi = (PlayerConnectorRmi) playerConnector;
    return (playerConnectorRmi.getPlayer() != null
      && playerConnectorRmi.getPlayer().getPlayerName() != null
      && gameIdRef != null);
  }

  /**
   * Perform all the needed lookups.
   * A gentle reminder that the {@link IPlayerConnector} bind can only be found
   * after a join or creation action.
   *
   */
  @Override
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

    alarm.scheduleAtFixedRate(new AlarmTask(snoozer),
      ALARM_INITIAL_DELAY_MS, ALARM_INTERVAL_MS);

    final PlayerConnectorRmi playerConnectorRmi = (PlayerConnectorRmi) playerConnector;
    while (!hasRequestedDisconnection()) {
      try {
        clientRunnerCore(playerConnectorRmi);
      } catch (IOException | InterruptedException | NullPlayerIdException e) {
        userInterface.displayError(new ErrorMessage("Internal module error, please report this message:" + e.getMessage(), ErrorSeverity.CRITICAL));
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
  void snoozeAlarm()
    throws RemoteException {
    PlayerConnectorRmi playerConnectorRmi = (PlayerConnectorRmi) playerConnector;
    SnoozeGameTimerCommand cmd = new SnoozeGameTimerCommand(playerConnectorRmi.getPlayer().getPlayerName());
    serverControllerActionServer.execute(playerConnectorRmi, cmd);
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
  public Thread runMessageHandler(){
    Thread messageHandler = new Thread(()->{
      while(hasRequestedDisconnection()){
        try {
          AbstractMessage msg = playerConnectorServer.getMessageFromQueue();
          if (msg != null) {
            showServerMessage(msg);
          }
        } catch (InterruptedException | RemoteException | NullPointerException e) {
          userInterface.displayError(new ErrorMessage("Internal module error, please report this message:" + e.getMessage(), ErrorSeverity.CRITICAL));
        }
      }
    });
    messageHandler.start();
    return messageHandler;
  }
}
