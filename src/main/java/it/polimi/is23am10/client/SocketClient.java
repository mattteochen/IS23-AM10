package it.polimi.is23am10.client;

import it.polimi.is23am10.client.interfaces.AlarmConsumer;
import it.polimi.is23am10.client.userinterface.UserInterface;
import it.polimi.is23am10.server.command.AddPlayerCommand;
import it.polimi.is23am10.server.command.GetAvailableGamesCommand;
import it.polimi.is23am10.server.command.MoveTilesCommand;
import it.polimi.is23am10.server.command.SendChatMessageCommand;
import it.polimi.is23am10.server.command.SnoozeGameTimerCommand;
import it.polimi.is23am10.server.command.StartGameCommand;
import it.polimi.is23am10.server.model.player.exceptions.NullPlayerIdException;
import it.polimi.is23am10.server.network.messages.AbstractMessage;
import it.polimi.is23am10.server.network.messages.ChatMessage;
import it.polimi.is23am10.server.network.messages.ErrorMessage;
import it.polimi.is23am10.server.network.messages.AbstractMessage.MessageType;
import it.polimi.is23am10.server.network.messages.ErrorMessage.ErrorSeverity;
import it.polimi.is23am10.server.network.playerconnector.PlayerConnectorSocket;
import it.polimi.is23am10.utils.Coordinates;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.rmi.RemoteException;
import java.util.Map;
import java.util.UUID;

/**
 * A client using Socket as communication method.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public class SocketClient extends Client {

  /** The {@link BufferedReader} buffer size. */
  private final int BUFFER_LENGHT = 102400;

  /**
   * The input stream instance to be linked with
   * {@link PlayerConnectorSocket#getConnector()}.
   */
  DataInputStream dis;

  /**
   * The buffer reader instance to be linked with the {@link SocketClient#dis}.
   */
  BufferedReader br;

  /**
   * Public constructor for client using Socket as communication method.
   *
   * @param pc player connector
   * @param ui user interface
   */
  public SocketClient(PlayerConnectorSocket pc, UserInterface ui)
      throws UnknownHostException, RemoteException {
    super(pc, ui);
  }

  /**
   * Thread used to poll for messages from server.
   * 
   */
  private transient Thread messageHandler;

  /**
   * Flag used to signal the client sent a snooze command and
   * is waiting for the ACK reply.
   * 
   */
  private boolean waitingForACK;

  /**
   * Lock for waitingForACK flag.
   * 
   */
  private final Object ackWaitLock = new Object();

  /** Socket alarm snoozer. */
  protected AlarmConsumer snoozer = () -> {
    // skip if the client has not inserted the name: server won't have any reference
    // to player and therefore won't be able to snooze it.
    if (((PlayerConnectorSocket) playerConnector).getPlayer() == null) {
      return;
    }
    try {
      snoozeAlarm();
    } catch (IOException e) {
      userInterface.displayError(
          new ErrorMessage(
              "Internal job failed, you might have lost connection to the server. Try re-joining",
              ErrorSeverity.CRITICAL));
      terminateClient();
    }
  };

  /** {@inheritDoc} */
  @Override
  protected boolean hasJoined() {
    synchronized (playerConnectorLock) {
      PlayerConnectorSocket playerConnectorSocket = (PlayerConnectorSocket) playerConnector;
      return (playerConnectorSocket.getPlayer() != null
          && playerConnectorSocket.getPlayer().getPlayerName() != null
          && gameIdRef != null);
    }
  }

  /**
   * Determine if the socket is connected.
   *
   * @return The requested flag.
   */
  protected boolean isSocketConnected() {
    synchronized (playerConnectorLock) {
      return !((PlayerConnectorSocket) playerConnector).getConnector().isClosed();
    }
  }

  /** Client core cycle. Send user requested commands and read updates. */
  @Override
  public void run() {

    alarm.scheduleAtFixedRate(new AlarmTask(snoozer), ALARM_INITIAL_DELAY_MS, ALARM_INTERVAL_MS);

    try {
      runInputMessageHandler();
    } catch (IOException e) {
      userInterface.displayError(
          new ErrorMessage(
              "Internal module error, please report this message:" + e.getMessage(),
              ErrorSeverity.ERROR));
    }

    while (isSocketConnected() && !hasRequestedDisconnection()) {
      try {
        clientRunnerCore();
      } catch (IOException | InterruptedException | NullPlayerIdException e) {
        userInterface.displayError(
            new ErrorMessage(
                "Internal module error, please report this message:" + e.getMessage(),
                ErrorSeverity.CRITICAL));
        terminateClient();
        return;
      }
    }
  }

  /**
   * Parse the server payload.
   *
   * @return The parsed {@link AbstractMessage}.
   * @throws IOException Possibly thrown by readline.
   */
  protected AbstractMessage parseServerMessage() throws IOException {
    if (playerConnector.getPlayer() != null && playerConnector.getPlayer().getPlayerName() != null) {
      String payload = br.readLine();
      return payload == null ? null : gson.fromJson(payload, AbstractMessage.class);
    } else {
      return null;
    }
  }

  /** {@inheritDoc} */
  @Override
  void getAvailableGames() throws IOException, InterruptedException {
    GetAvailableGamesCommand command = new GetAvailableGamesCommand();
    String req = gson.toJson(command);
    sendMessage(req);
  }

  /** {@inheritDoc} */
  @Override
  void snoozeAlarm() throws IOException {
    String req;
    synchronized (playerConnectorLock) {
      PlayerConnectorSocket playerConnectorSocket = (PlayerConnectorSocket) playerConnector;
      SnoozeGameTimerCommand cmd = new SnoozeGameTimerCommand(playerConnectorSocket.getPlayer().getPlayerName());
      req = gson.toJson(cmd);
    }
    sendMessage(req);
    synchronized (ackWaitLock){
      waitingForACK = true;
    }
  }

  /** {@inheritDoc} */
  @Override
  void startGame(String playerName, int maxPlayerNum) throws IOException {
    StartGameCommand command = new StartGameCommand(playerName, maxPlayerNum);
    String req = gson.toJson(command);
    sendMessage(req);
  }

  /** {@inheritDoc} */
  @Override
  void addPlayer(String playerName, UUID gameId) throws IOException {
    AddPlayerCommand command = new AddPlayerCommand(playerName, gameId);
    String req = gson.toJson(command);
    sendMessage(req);
  }

  /** {@inheritDoc} */
  @Override
  void moveTiles(Map<Coordinates, Coordinates> moves) throws IOException {
    PlayerConnectorSocket playerConnectorSocket = (PlayerConnectorSocket) playerConnector;
    MoveTilesCommand command = new MoveTilesCommand(
        playerConnectorSocket.getPlayer().getPlayerName(),
        playerConnectorSocket.getGameId(),
        moves);
    String req = gson.toJson(command);
    sendMessage(req);
  }

  /** {@inheritDoc} */
  @Override
  void sendChatMessage(ChatMessage msg) throws IOException {
    SendChatMessageCommand command = new SendChatMessageCommand(msg);
    String req = gson.toJson(command);
    sendMessage(req);
  }

  /**
   * Write a message throw the socket.
   *
   * @param req The payload request.
   */
  protected void sendMessage(String req) throws IOException {
    synchronized (playerConnectorLock) {
      PrintWriter epson = new PrintWriter(
          ((PlayerConnectorSocket) playerConnector).getConnector().getOutputStream(),
          true,
          StandardCharsets.UTF_8);
      epson.println(req);
    }
  }

  /**
   * Poll payloads from the socket stream and process {@link AbstractMessage} that
   * can be
   * recognized.
   */
  public void runInputMessageHandler() throws IOException {
    synchronized (playerConnectorLock) {
      dis = new DataInputStream(
          ((PlayerConnectorSocket) playerConnector).getConnector().getInputStream());
    }
    br = new BufferedReader(new InputStreamReader(dis), BUFFER_LENGHT);

    messageHandler = new Thread(
        () -> {
          while (isSocketConnected() && !hasRequestedDisconnection()) {
            // retrieve and show server messages, it includes chat messages
            try {
              AbstractMessage serverMessage = parseServerMessage();
              synchronized (ackWaitLock){
                if (serverMessage != null) {
                  if (serverMessage.getMessageType() == MessageType.SNOOZE_ACK) {
                    waitingForACK = false;
                  }
                  showServerMessage(serverMessage);
                } else {
                  if (waitingForACK) {
                    userInterface.displayError(
                        new ErrorMessage(
                            "No snooze ACK received. Server is probably dead. Closing client.",
                            ErrorSeverity.ERROR));
                    terminateClient();
                    return;
                  }
                }
              }
            } catch (IOException | NullPointerException e) {
              userInterface.displayError(
                  new ErrorMessage(
                      "Internal module error, please report this message:" + e.getMessage(),
                      ErrorSeverity.ERROR));
            }
          }
        });
    messageHandler.start();
  }

}
