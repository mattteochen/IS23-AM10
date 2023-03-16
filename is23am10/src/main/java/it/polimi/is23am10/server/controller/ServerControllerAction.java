package it.polimi.is23am10.server.controller;

import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import it.polimi.is23am10.server.command.AbstractCommand;
import it.polimi.is23am10.server.command.StartGameCommand;
import it.polimi.is23am10.server.command.AbstractCommand.Opcode;
import it.polimi.is23am10.server.controller.interfaces.ControllerConsumer;
import it.polimi.is23am10.server.gamehandler.GameHandler;
import it.polimi.is23am10.server.playerconnector.PlayerConnector;

/**
 * The server controller action class definition.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public class ServerControllerAction {

  /**
   * The logger, an instance of {@link Logger}.
   *
   */
  private final Logger logger = LogManager.getLogger(ServerControllerAction.class);

  private final ControllerConsumer<StartGameCommand> startConsumer
      = (playerConnector, command) -> {
    GameHandler gameHandler = new GameHandler(command.getStartingPlayerName(), command.getMaxPlayers());
    gameHandler.addPlayerConnector(playerConnector);
  };

  private Map<Opcode, ControllerConsumer> actions = Map.of(
      Opcode.START, startConsumer);

  public <T extends AbstractCommand> void takeAction(PlayerConnector connector, T command) {
    actions.get(command.getOpcode()).accept(connector, command);
  }
}
