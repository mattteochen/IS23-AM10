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
import it.polimi.is23am10.server.command.MoveTilesCommand;
import it.polimi.is23am10.server.command.StartGameCommand;
import it.polimi.is23am10.server.command.AbstractCommand.Opcode;
import it.polimi.is23am10.server.controller.exceptions.NullGameHandlerInstance;
import it.polimi.is23am10.server.model.player.Player;
import it.polimi.is23am10.server.network.messages.AbstractMessage;
import it.polimi.is23am10.server.network.messages.ErrorMessage;
import it.polimi.is23am10.server.network.playerconnector.PlayerConnectorSocket;
import it.polimi.is23am10.utils.ErrorTypeString;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

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
    
    while (playerConnector != null && playerConnector.getConnector().isConnected()) {
      try {
        AbstractCommand command = buildCommand();
        serverControllerAction.execute(playerConnector, command);
        update();
      } catch (IOException e) {
        logger.error("Failed to retrieve socket payload", e);
      } catch (JsonIOException | JsonSyntaxException e) {
        logger.error("Failed to parse socket payload", e);
      } catch (InterruptedException e) {
        logger.error("Failed get response message from the queue", e);
        // note, we are not raising the interrupted flag as we don't want to stop this
        // thread.
      }
    }

    if (!playerConnector.getConnector().isConnected() && playerConnector.getPlayer() != null) {
      playerConnector.getPlayer().setIsConnected(false);
      logger.info("Player {} disconnected", playerConnector.getPlayer().getPlayerName());
      try {
        ServerControllerState.getGameHandlerByUUID(
            playerConnector.getGameId()).getPlayerConnectors()
            .forEach(pc -> {
              try {
                pc.addMessageToQueue(
                    new ErrorMessage(playerConnector.getPlayer().getPlayerName() + "disconnected from the game."));
              } catch (InterruptedException e) {
                logger.error("{} {}", ErrorTypeString.ERROR_INTERRUPTED, e);
              }
            });
      } catch (NullGameHandlerInstance e) {
        logger.error(" {} {}", ErrorTypeString.ERROR_ADDING_HANDLER, e);
      }
    }

  }

  /**
   * Build the response message and sent it to the client when any game update is
   * available.
   *
   * @throws IOException
   * @throws InterruptedException
   * @throws InvalidMessageTypeException
   * 
   */
  protected void update() throws InterruptedException, IOException {
    Optional<AbstractMessage> optMessage = playerConnector.getMessageFromQueue();
    if (optMessage.isPresent()) {
      AbstractMessage message = optMessage.get();
      PrintWriter printer = new PrintWriter(playerConnector.getConnector().getOutputStream(), true,
          StandardCharsets.UTF_8);
      printer.println(message.getMessage());
      logger.info("{} sent to client {}", message.getMessageType(), message.getMessage());
    }
  }

  /**
   * Build the player command deserializing the byte stream.
   * The gson deserialization returns a base class type but if the byte stream
   * contained a derived type, this can be casted at runtime on the need.
   *
   * @return An instance of the command object.
   * @throws IOException
   * @throws JsonIOException
   * @throws JsonSyntaxException
   * 
   */
  protected AbstractCommand buildCommand()
      throws IOException, JsonIOException, JsonSyntaxException {
    BufferedReader reader = new BufferedReader(
        new InputStreamReader(playerConnector.getConnector().getInputStream()));
    String payload = null;
    if (reader.ready()) {
      payload = reader.readLine();
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
        default:
          throw new JsonParseException("Unknown class name: " + className);
      }
    }
  }
}