package it.polimi.is23am10.controller;

import it.polimi.is23am10.command.AbstractCommand;
import it.polimi.is23am10.command.AbstractCommand.Opcode;
import it.polimi.is23am10.command.StartGameCommand;
import it.polimi.is23am10.controller.exceptions.NullGameHandlerInstance;
import it.polimi.is23am10.controller.exceptions.StartCommandSerializationErrorException;
import it.polimi.is23am10.controller.interfaces.ControllerConsumer;
import it.polimi.is23am10.factory.exceptions.DuplicatePlayerNameException;
import it.polimi.is23am10.factory.exceptions.NullPlayerNamesException;
import it.polimi.is23am10.game.exceptions.InvalidMaxPlayerException;
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
import it.polimi.is23am10.playerconnector.PlayerConnector;
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
public class ServerControllerAction {

  /**
   * The logger, an instance of {@link Logger}.
   *
   */
  private final Logger logger = LogManager.getLogger(ServerControllerAction.class);

  /**
   * The {@link Opcode#START} command callback worker.
   *
   */
  protected final ControllerConsumer startConsumer = (playerConnector, command) -> {
    if (command instanceof StartGameCommand) {
      try {
        String playerName = ((StartGameCommand) command).getStartingPlayerName();
        Integer maxPlayers = ((StartGameCommand) command).getMaxPlayers();
        GameHandler gameHandler = new GameHandler(playerName, maxPlayers);
        // add the new player connector to the game handler.
        gameHandler.addPlayerConnector(playerConnector);
        
        // populate the connector with the game and player reference.
        playerConnector.setGameId(gameHandler.getGame().getGameId());
        playerConnector.setPlayerName(playerName);

        // add the new game handler instance on the game pool.
        ServerControllerState.addGameHandler(gameHandler);
        // add the new player connector instance on the player pool.
        ServerControllerState.addPlayerConnector(playerConnector);

        logger.info("{} Started new game with id {} from {}",
            ServerDebugPrefixString.START_COMMAND_PREFIX,
            gameHandler.getGame().getGameId(), playerName);
      } catch (NullNumOfPlayersException | NullPlayerNamesException | NullPlayerScoreBlocksException
          | NullPlayerPrivateCardException | NullPlayerScoreException | NullPlayerBookshelfException
          | NullPlayerIdException | NullPlayerNameException | NullMaxPlayerException
          | AlreadyInitiatedPatternException e) {
        logger.error("{} Failed to initialize new game request {}",
            ServerDebugPrefixString.START_COMMAND_PREFIX, e);
      } catch (InvalidNumOfPlayersException | InvalidMaxPlayerException
          | DuplicatePlayerNameException e) {
        logger.error("{} {}", ServerDebugPrefixString.START_COMMAND_PREFIX, e);
      } catch (NullGameHandlerInstance e) {
        logger.error("{} Failed the game instance creation {}",
            ServerDebugPrefixString.START_COMMAND_PREFIX, e);
      } catch (NullPlayerConnector e) {
        logger.error("{} Failed to add player connector {}",
            ServerDebugPrefixString.START_COMMAND_PREFIX, e);
      } 
    } else {
      logger.error("{} Failed to obtain deserialized START command",
          ServerDebugPrefixString.START_COMMAND_PREFIX);
      throw new StartCommandSerializationErrorException(StartGameCommand.class.toString());
    }
  };

  /**
   * A helper mapping to link a {@link Opcode} to the relative worker callback.
   *
   */
  private final Map<Opcode, ControllerConsumer> actions = Map.of(
      Opcode.START, startConsumer);

  /**
   * Apply the callback to a specific {@link Opcode} received from a {@link PlayerConnector}.
   *
   * @param connector The player connector instance.
   * @param command   The deserialized command.
   *
   */
  public void execute(PlayerConnector connector, AbstractCommand command) {
    actions.get(command.getOpcode()).accept(connector, command);
  }
}
