package it.polimi.is23am10.controller;

import it.polimi.is23am10.command.AbstractCommand;
import it.polimi.is23am10.command.AddPlayerCommand;
import it.polimi.is23am10.command.MoveTilesCommand;
import it.polimi.is23am10.command.AbstractCommand.Opcode;
import it.polimi.is23am10.command.StartGameCommand;
import it.polimi.is23am10.controller.exceptions.AddPlayerCommandSerializationErrorException;
import it.polimi.is23am10.controller.exceptions.MoveTileCommandSerializationErrorException;
import it.polimi.is23am10.controller.exceptions.NullGameHandlerInstance;
import it.polimi.is23am10.controller.exceptions.StartCommandSerializationErrorException;
import it.polimi.is23am10.controller.interfaces.ControllerConsumer;
import it.polimi.is23am10.controller.interfaces.ControllerConsumer;
import it.polimi.is23am10.controller.interfaces.IServerControllerAction;
import it.polimi.is23am10.controller.interfaces.IServerControllerActionRmi;
import it.polimi.is23am10.factory.exceptions.DuplicatePlayerNameException;
import it.polimi.is23am10.factory.exceptions.NullPlayerNamesException;
import it.polimi.is23am10.game.exceptions.InvalidMaxPlayerException;
import it.polimi.is23am10.game.exceptions.NullAssignedPatternException;
import it.polimi.is23am10.game.exceptions.NullMaxPlayerException;
import it.polimi.is23am10.gamehandler.GameHandler;
import it.polimi.is23am10.gamehandler.exceptions.NullPlayerConnector;
import it.polimi.is23am10.items.board.exceptions.InvalidNumOfPlayersException;
import it.polimi.is23am10.items.board.exceptions.NullNumOfPlayersException;
import it.polimi.is23am10.items.card.exceptions.AlreadyInitiatedPatternException;
import it.polimi.is23am10.player.exceptions.NullPlayerBookshelfException;
import it.polimi.is23am10.player.exceptions.NullPlayerIdException;
import it.polimi.is23am10.player.exceptions.NullPlayerNameException;
import it.polimi.is23am10.player.exceptions.NullPlayerPrivateCardException;
import it.polimi.is23am10.player.exceptions.NullPlayerScoreBlocksException;
import it.polimi.is23am10.player.exceptions.NullPlayerScoreException;
import it.polimi.is23am10.playerconnector.AbstractPlayerConnector;
import it.polimi.is23am10.playerconnector.PlayerConnector;
import java.util.Map;
import java.util.UUID;
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
public class ServerControllerActionRmiImpl implements
    IServerControllerActionRmi, IServerControllerAction {

  /**
   * The logger, an instance of {@link Logger}.
   *
   */
  private static final Logger logger = LogManager.getLogger(ServerControllerActionRmiImpl.class);

  /**
   * A helper mapping to link a {@link Opcode} to the relative worker callback.
   *
   */
  private final Map<Opcode, ControllerConsumer> actions = Map.of(
      Opcode.START, startConsumer,
      Opcode.ADD_PLAYER, addPlayerConsumer,
      Opcode.MOVE_TILES, moveTilesConsumer);

  /**
   * Apply the callback to a specific {@link Opcode} received from a
   * {@link PlayerConnector}.
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
