package it.polimi.is23am10.server.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;

import it.polimi.is23am10.server.command.AbstractCommand;
import it.polimi.is23am10.server.command.AddPlayerCommand;
import it.polimi.is23am10.server.command.GetAvailableGamesCommand;
import it.polimi.is23am10.server.command.MoveTilesCommand;
import it.polimi.is23am10.server.command.SendChatMessageCommand;
import it.polimi.is23am10.server.command.SnoozeGameTimerCommand;
import it.polimi.is23am10.server.command.StartGameCommand;
import it.polimi.is23am10.server.command.AbstractCommand.Opcode;
import it.polimi.is23am10.server.controller.exceptions.NullGameHandlerInstance;
import it.polimi.is23am10.server.network.messages.AbstractMessage;
import it.polimi.is23am10.server.network.messages.ErrorMessage;
import it.polimi.is23am10.server.network.messages.ErrorMessage.ErrorSeverity;
import it.polimi.is23am10.server.network.playerconnector.PlayerConnectorSocket;
import it.polimi.is23am10.utils.Coordinates;
import it.polimi.is23am10.utils.ErrorTypeString;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.rmi.RemoteException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The server controller class definition.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
@SuppressWarnings({ "checkstyle:nonemptyatclausedescriptioncheck" })
public final class ServerControllerSocket implements Runnable {

  /**
   * The logger, an instance of {@link Logger}.
   *
   */
  protected Logger logger = LogManager.getLogger(ServerControllerSocket.class);

  /**
   * The single client connection instance of type {@link PlayerConnectorSocket}.
   * This is the entry and exit point for out responsive application.
   *
   */
  protected PlayerConnectorSocket playerConnector;

  /**
   * The {@link Gson} serializer and deserializer for game's
   * {@link AbstractCommand}.
   * We need a custom {@link Gson} instance as we have to deserialize polymorphic
   * objects {@link AbstractCommand}.
   *
   */
  protected Gson gson = new GsonBuilder()
      .registerTypeAdapter(AbstractCommand.class, new CommandDeserializer())
      .registerTypeAdapter(Coordinates.class, new CoordinatesDeserializer())
      .create();

  /**
   * The action taker instance. This works on a given command {@link Opcode}.
   *
   */
  protected ServerControllerAction serverControllerAction;

  /**
   * Constructor.
   *
   * @param playerConnector        The already build player connector instance
   *                               with the low level socket instance.
   * @param serverControllerAction The server action taker instance.
   */
  public ServerControllerSocket(PlayerConnectorSocket playerConnector,
      ServerControllerAction serverControllerAction) {
    this.playerConnector = playerConnector;
    this.serverControllerAction = serverControllerAction;
  }

  /**
   * {@inheritDoc}
   *
   */
  @Override
  public void run() {

    while (playerConnector != null && !playerConnector.getConnector().isClosed()) {
      try {
        AbstractCommand command = buildCommand();
        serverControllerAction.execute(playerConnector, command);
        update();
      } catch (IOException e) {
        logger.error("Failed to retrieve socket payload", e);
        break;
      } catch (JsonIOException | JsonSyntaxException e) {
        logger.error("Failed to parse socket payload", e);
      } catch (InterruptedException e) {
        logger.error("Failed get response message from the queue", e);
        // note, we are not raising the interrupted flag as we don't want to stop this
        // thread.
      }
    }
    /* 
    playerConnector.getPlayer().setIsConnected(false);
    logger.info("Player {} disconnected", playerConnector.getPlayer().getPlayerName());
    try {
      ServerControllerState.getGameHandlerByUUID(
          playerConnector.getGameId()).getPlayerConnectors()
          .stream()
          .filter(pc -> !pc.getPlayer().getPlayerName().equals(playerConnector.getPlayer().getPlayerName()))
          .forEach(pc -> {
            try {
              pc.notify(
                  new ErrorMessage(String.format(ErrorTypeString.WARNING_PLAYER_DISCONNECT,
                      playerConnector.getPlayer().getPlayerName()), ErrorSeverity.WARNING));
            } catch (InterruptedException | RemoteException e) {
              logger.error("{} {}", ErrorTypeString.ERROR_MESSAGE_DELIVERY, e);
            }
          });
    } catch (NullGameHandlerInstance e) {
      logger.error(" {} {}", ErrorTypeString.ERROR_ADDING_HANDLER, e);
    }
    */
  }
  /**
   * Build the response message and sent it to the client when any game update is
   * available.
   *
   * @throws IOException          On output stream failure.
   * @throws InterruptedException On queue message retrieval failure.
   * 
   */
  protected void update() throws InterruptedException, IOException {
    AbstractMessage msg = playerConnector.getMessageFromQueue();
    if (msg != null) {
      PrintWriter printer;
      synchronized (playerConnector.getConnector()) {
        printer = new PrintWriter(playerConnector.getConnector().getOutputStream(), true,
            StandardCharsets.UTF_8);
        printer.println(gson.toJson(msg));
      }
      logger.info("{} sent to client {}", msg.getMessageType(), msg.getMessage());
    }
  }

  /**
   * Build the player command deserializing the byte stream.
   * The gson deserialization returns a base class type but if the byte stream
   * contained a derived type, this can be casted at runtime on the need.
   *
   * @return An instance of the command object.
   * @throws IOException         On output stream failure.
   * @throws JsonIOException     On serialization failure due to I/O.
   * @throws JsonSyntaxException On serialization failure due to malformed JSON.
   * 
   */
  protected AbstractCommand buildCommand()
      throws IOException, JsonIOException, JsonSyntaxException {
    BufferedReader reader;
    String payload = null;
    synchronized (playerConnector.getConnector()) {
      reader = new BufferedReader(
          new InputStreamReader(playerConnector.getConnector().getInputStream()));
      if (reader.ready()) {
        payload = reader.readLine();
      }
    }
    if (payload != null) {
      logger.info("Socket buffer reader received {}", payload);
    }
    return gson.fromJson(payload, AbstractCommand.class);
  }

  /**
   * Custom deserializer class definition for {@link Gson} usage.
   * This works on polymorphic {@link AbstractCommand} objects.
   * 
   */
  class CommandDeserializer implements JsonDeserializer<AbstractCommand> {
    @Override
    public AbstractCommand deserialize(
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
        case "it.polimi.is23am10.server.command.StartGameCommand":
          return context.deserialize(jsonObject, StartGameCommand.class);
        case "it.polimi.is23am10.server.command.AddPlayerCommand":
          return context.deserialize(jsonObject, AddPlayerCommand.class);
        case "it.polimi.is23am10.server.command.MoveTilesCommand":
          return context.deserialize(jsonObject, MoveTilesCommand.class);
        case "it.polimi.is23am10.server.command.GetAvailableGamesCommand":
          return context.deserialize(jsonObject, GetAvailableGamesCommand.class);
        case "it.polimi.is23am10.server.command.SendChatMessageCommand":
          return context.deserialize(jsonObject, SendChatMessageCommand.class);
        case "it.polimi.is23am10.server.command.SnoozeGameTimerCommand":
          return context.deserialize(jsonObject, SnoozeGameTimerCommand.class);
        default:
          throw new JsonParseException("Unknown class name: " + className);
      }
    }
  }

  /**
   * Custom deserializer class definition for {@link Gson} usage.
   * This works on {@link Coordinates} objects.
   * 
   */
  class CoordinatesDeserializer implements JsonDeserializer<Coordinates> {
    @Override
    public Coordinates deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
        throws JsonParseException {
      JsonObject obj = json.getAsJsonObject();
      int row = obj.get("row").getAsInt();
      int col = obj.get("col").getAsInt();
      return new Coordinates(row, col);
    }
  }
}
