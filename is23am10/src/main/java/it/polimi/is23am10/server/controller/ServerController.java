package it.polimi.is23am10.server.controller;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import it.polimi.is23am10.server.command.AbstractCommand;
import it.polimi.is23am10.server.command.AbstractCommand.Opcode;
import it.polimi.is23am10.server.gamehandler.exceptions.NullPlayerConnector;
import it.polimi.is23am10.server.playerconnector.PlayerConnector;
import it.polimi.is23am10.server.playerconnector.exceptions.NullSocketConnectorException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
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
public final class ServerController implements Runnable {

  /**
   * The logger, an instance of {@link Logger}.
   *
   */
  private final Logger logger = LogManager.getLogger(ServerController.class);

  /**
   * The single client connection instance of type {@link PlayerConnector}.
   * This is the entry and exit point for out responsive application.
   *
   */
  private PlayerConnector playerConnector;

  /**
   * The {@link Gson} serializer and deserializer for game's {@link AbstractCommand}.
   *
   */
  private final Gson gson = new Gson();

  /**
   * The action taker instance. This works on a given command {@link Opcode}.
   *
   */
  private final ServerControllerAction worker = new ServerControllerAction();
  
  /**
   * Constructor.
   *
   * @param connector The {@link Socket} instance from the server handler.
   * 
   */
  public ServerController(Socket connector) {
    try {
      this.playerConnector = new PlayerConnector(connector);
      ServerControllerState.addPlayerConnector(this.playerConnector);
    } catch (NullSocketConnectorException | NullPlayerConnector e) {
      logger.error(e);
    } 
  }

  /**
   * {@inheritDoc}
   *
   */
  @Override
  public synchronized void run() {
    while (playerConnector != null && playerConnector.getConnector().isConnected()) {
      try {
        AbstractCommand command = buildCommand();

        logger.info("Received command: {}", command);

        worker.execute(playerConnector, command);
      } catch (IOException e) {
        logger.error("Failed to retrieve socket payload", e);
      } catch (JsonIOException | JsonSyntaxException e) {
        logger.error("Failed to parse socket payload", e);
      }
    }
  }

  /**
   * Build the player command deserializing the byte stream.
   * The gson deserialization returns a base class type but if the byte stream contained
   * a derived type, this can be casted at runtime on the need.
   *
   * @return An instance of the command object.
   * 
   */
  private synchronized AbstractCommand buildCommand()
      throws IOException, JsonIOException, JsonSyntaxException {
    Reader reader = new InputStreamReader(
        playerConnector.getConnector().getInputStream(), StandardCharsets.UTF_8);
    return gson.fromJson(reader, AbstractCommand.class);
  }
}
