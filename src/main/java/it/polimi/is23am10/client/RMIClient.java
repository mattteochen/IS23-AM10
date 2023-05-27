package it.polimi.is23am10.client;

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
import it.polimi.is23am10.server.controller.interfaces.IServerControllerAction;
import it.polimi.is23am10.server.model.player.exceptions.NullPlayerIdException;
import it.polimi.is23am10.server.network.messages.AbstractMessage;
import it.polimi.is23am10.server.network.messages.ChatMessage;
import it.polimi.is23am10.server.network.messages.ErrorMessage;
import it.polimi.is23am10.server.network.messages.ErrorMessage.ErrorSeverity;
import it.polimi.is23am10.server.network.playerconnector.PlayerConnectorRmi;
import it.polimi.is23am10.utils.Coordinates;
import java.io.IOException;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.Map;
import java.util.UUID;

/**
 * A client using RMI as communication method.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public class RMIClient extends Client {

  /** The rmi registry. */
  protected Registry rmiRegistry;

  /** The {@link ServerControllerAction} server object. */
  protected transient IServerControllerAction serverControllerActionServer;

  /**
   * Rmi alarm snoozer.
   * 
   */
  protected transient AlarmConsumer snoozer = () -> {
    if (!hasJoined()) {
      return;
    }
    try {
      snoozeAlarm();
    } catch(RemoteException e) {
      userInterface.displayError(
        new ErrorMessage("Internal job failed, you might have lost connection to the server. Try re-joining", ErrorSeverity.ERROR));
      terminateClient();
      return;
    }
  };


  /**
   * Public constructor for client using RMI as communication method.
   *
   * @param pc Player connector.
   * @param ui User interface.
   * @param scas Server controller action server reference.
   * @param reg Rmi registry instance.
   */
  public RMIClient(
      PlayerConnectorRmi pc, UserInterface ui, IServerControllerAction scas, Registry reg)
      throws UnknownHostException, RemoteException {
    super(pc, ui);
    serverControllerActionServer = scas;
    rmiRegistry = reg;
  }

  /** {@inheritDoc} */
  @Override
  protected boolean hasJoined() {
    synchronized (playerConnectorLock) {
      PlayerConnectorRmi playerConnectorRmi = (PlayerConnectorRmi) playerConnector;
      return (playerConnectorRmi.getPlayer() != null
          && playerConnectorRmi.getPlayer().getPlayerName() != null
          && gameIdRef != null);
    }
  }

  /** Client core cycle. Send user requested commands and read updates. */
  @Override
  public void run() {

    alarm.scheduleAtFixedRate(new AlarmTask(snoozer), ALARM_INITIAL_DELAY_MS, ALARM_INTERVAL_MS);

    while (!hasRequestedDisconnection()) {
      try {
        clientRunnerCore();
      } catch (IOException | InterruptedException | NullPlayerIdException e) {
        userInterface.displayError(
            new ErrorMessage(
                "Internal module error, please report this message: " + e.getMessage(),
                ErrorSeverity.CRITICAL));
        terminateClient();
        return;
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  void getAvailableGames() throws RemoteException {
    AbstractMessage msg = serverControllerActionServer.execute(new GetAvailableGamesCommand());
    showServerMessage(msg);
  }

  /** {@inheritDoc} */
  @Override
  void snoozeAlarm() throws RemoteException {
    synchronized (playerConnectorLock) {
      SnoozeGameTimerCommand cmd =
          new SnoozeGameTimerCommand(
              ((PlayerConnectorRmi) playerConnector).getPlayer().getPlayerName());
      serverControllerActionServer.execute(((PlayerConnectorRmi) playerConnector), cmd);
    }
  }

  /** {@inheritDoc} */
  @Override
  void startGame(String playerName, int maxPlayerNum) throws IOException {
    synchronized (playerConnectorLock) {
      AbstractCommand command = new StartGameCommand(playerName, maxPlayerNum);
      serverControllerActionServer.execute((PlayerConnectorRmi) playerConnector, command);
    }
  }

  /** {@inheritDoc} */
  @Override
  void addPlayer(String playerName, UUID gameId) throws IOException {
    synchronized (playerConnectorLock) {
      AbstractCommand command = new AddPlayerCommand(playerName, gameId);
      serverControllerActionServer.execute((PlayerConnectorRmi) playerConnector, command);
    }
  }

  /** {@inheritDoc} */
  @Override
  void moveTiles(Map<Coordinates, Coordinates> moves) throws IOException {
    synchronized (playerConnectorLock) {
      PlayerConnectorRmi playerConnectorRmi = (PlayerConnectorRmi) playerConnector;
      AbstractCommand command =
          new MoveTilesCommand(
              playerConnectorRmi.getPlayer().getPlayerName(),
              playerConnectorRmi.getGameId(),
              moves);
      serverControllerActionServer.execute((PlayerConnectorRmi) playerConnector, command);
    }
  }

  /** {@inheritDoc} */
  @Override
  void sendChatMessage(ChatMessage msg) throws IOException {
    synchronized (playerConnectorLock) {
      AbstractCommand command = new SendChatMessageCommand(msg);
      serverControllerActionServer.execute((PlayerConnectorRmi) playerConnector, command);
    }
  }
}
