package it.polimi.is23am10.server.controller;

import it.polimi.is23am10.server.command.AbstractCommand;
import it.polimi.is23am10.server.command.AbstractCommand.Opcode;
import it.polimi.is23am10.server.controller.interfaces.ControllerConsumer;
import it.polimi.is23am10.server.controller.interfaces.IServerControllerAction;
import it.polimi.is23am10.server.network.playerconnector.AbstractPlayerConnector;
import it.polimi.is23am10.server.network.playerconnector.PlayerConnectorSocket;

import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The server controller action class definition.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public class ServerControllerAction implements IServerControllerAction {

  /**
   * The logger, an instance of {@link Logger}.
   *
   */
  private final Logger logger = LogManager.getLogger(ServerControllerAction.class);

  /**
   * A helper mapping to link a {@link Opcode} to the relative worker callback.
   *
   */
  private final Map<Opcode, ControllerConsumer> actions = Map.of(
      Opcode.START, startConsumer,
      Opcode.ADD_PLAYER, addPlayerConsumer,
      Opcode.MOVE_TILES, moveTilesConsumer,
      Opcode.GET_GAMES, getAvailableGamesConsumer);

  /**
   * Apply the callback to a specific {@link Opcode} received from a
   * {@link PlayerConnectorSocket}.
   *
   * @param connector The player connector instance.
   * @param command   The deserialized command.
   *
   */
  @Override
  public void execute(AbstractPlayerConnector connector, AbstractCommand command) {
    if (command == null || connector == null) {
      return;
    }
    actions.get(command.getOpcode()).accept(logger, connector, command);
  }
}
