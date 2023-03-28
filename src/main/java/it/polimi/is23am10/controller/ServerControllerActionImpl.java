package it.polimi.is23am10.controller;

import it.polimi.is23am10.command.AbstractCommand;
import it.polimi.is23am10.command.AbstractCommand.Opcode;
import it.polimi.is23am10.command.AddPlayerCommand;
import it.polimi.is23am10.command.MoveTilesCommand;
import it.polimi.is23am10.command.StartGameCommand;
import it.polimi.is23am10.controller.exceptions.AddPlayerCommandSerializationErrorException;
import it.polimi.is23am10.controller.exceptions.NullGameHandlerInstance;
import it.polimi.is23am10.controller.exceptions.StartCommandSerializationErrorException;
import it.polimi.is23am10.controller.interfaces.ControllerConsumer;
import it.polimi.is23am10.controller.interfaces.IServerControllerAction;
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
import it.polimi.is23am10.playerconnector.PlayerConnector;
import java.util.Map;
import java.util.Optional;
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
public class ServerControllerActionImpl implements IServerControllerAction {

  /**
   * The logger, an instance of {@link Logger}.
   *
   */
  private final Logger logger = LogManager.getLogger(ServerControllerActionImpl.class);

  /**
   * The {@link Opcode#START} command callback worker.
   * TODO: add client responces.
   *
   */
  protected final ControllerConsumer startConsumer = (playerConnector, command) -> {
    if (command instanceof StartGameCommand) {
      try {
        String playerName = ((StartGameCommand) command).getStartingPlayerName();
        Integer maxPlayers = ((StartGameCommand) command).getMaxPlayers();
        GameHandler gameHandler = new GameHandler(playerName, maxPlayers);

        // add the new game handler instance on the game pool.
        ServerControllerState.addGameHandler(gameHandler);
        // add the new player connector instance on the player pool.
        ServerControllerState.addPlayerConnector(playerConnector);

        // add the new player connector to the game handler.
        gameHandler.addPlayerConnector(playerConnector);

        // populate the connector with the game and player reference.
        playerConnector.setGameId(gameHandler.getGame().getGameId());
        playerConnector.setPlayerName(playerName);

        logger.info("{} Started new game with id {} from {}",
            ServerDebugPrefixString.START_COMMAND_PREFIX,
            gameHandler.getGame().getGameId(), playerName);

        // send the game model update to all the connected players
        updateAllPlayers(gameHandler);
      } catch (NullNumOfPlayersException | NullPlayerNamesException | NullPlayerScoreBlocksException
          | NullPlayerPrivateCardException | NullPlayerScoreException | NullPlayerBookshelfException
          | NullPlayerIdException | NullPlayerNameException | NullMaxPlayerException
          | AlreadyInitiatedPatternException | NullAssignedPatternException e) {
        logger.error("{} Failed to initialize new game request {}",
            ServerDebugPrefixString.START_COMMAND_PREFIX, e);
      } catch (InvalidNumOfPlayersException | InvalidMaxPlayerException
          | DuplicatePlayerNameException e) {
        logger.error("{} {}", ServerDebugPrefixString.START_COMMAND_PREFIX, e);
      } catch (NullGameHandlerInstance e) {
        logger.error("{} Failed the game instance creation {}",
            ServerDebugPrefixString.START_COMMAND_PREFIX, e);
      } catch (NullPlayerConnector e) {
        //TODO: as we have a null connector, the model should expose something to remove the player.
        logger.error("{} Failed to add player connector {}",
            ServerDebugPrefixString.START_COMMAND_PREFIX, e);
      } catch (InterruptedException e) {
        logger.error("{} Failed to push update to all player connectors {}",
            ServerDebugPrefixString.START_COMMAND_PREFIX, e);
      }
    } else {
      logger.error("{} Failed to obtain deserialized START command",
          ServerDebugPrefixString.START_COMMAND_PREFIX);
      throw new StartCommandSerializationErrorException(StartGameCommand.class.toString());
    }
  };

  /**
   * The {@link Opcode#ADD_PLAYER} command callback worker.
   * TODO: add client responces.
   *
   */
  protected final ControllerConsumer addPlayerConsumer = (playerConnector, command) -> {
    if (command instanceof AddPlayerCommand) {
      try {
        String playerName = ((AddPlayerCommand) command).getPlayerName();
        UUID gameId = ((AddPlayerCommand) command).getGameId();

        final GameHandler gameHandler = ServerControllerState.getGameHandlerByUUID(gameId);

        // add the new player in the game model.
        // note, it is essential that the the model is updated first
        // to avoid wrong parameters in connectors if any exception
        // will be thrown from the model.
        gameHandler.getGame().addPlayer(playerName);

        // add the new player connector instance to the game's player pool.
        gameHandler.addPlayerConnector(playerConnector);

        // add the new player connector instance on the player pool.
        ServerControllerState.addPlayerConnector(playerConnector);

        // populate the connector with the game and player reference.
        playerConnector.setGameId(gameId);
        playerConnector.setPlayerName(playerName);

        logger.info("{} Added new player {} to game {}",
            ServerDebugPrefixString.ADD_PLAYER_COMMAND_PREFIX,
            playerName, gameId);
            
        // send the game model update to all the connected players
        updateAllPlayers(gameHandler);
      } catch (NullPlayerNamesException | NullPlayerScoreBlocksException
          | NullPlayerPrivateCardException | NullPlayerScoreException | NullPlayerBookshelfException
          | NullPlayerIdException | NullPlayerNameException | AlreadyInitiatedPatternException
          | NullAssignedPatternException e) {
        logger.error("{} Failed to add new player request to game {}",
            ServerDebugPrefixString.ADD_PLAYER_COMMAND_PREFIX, e);
      } catch (DuplicatePlayerNameException e) {
        logger.error("{} Failed to add new player to game model",
            ServerDebugPrefixString.ADD_PLAYER_COMMAND_PREFIX, e);
      } catch (NullPlayerConnector e) {
        //TODO: as we have a null connector, the model should expose something to remove the player.
        logger.error("{} Failed to add player connector {}",
            ServerDebugPrefixString.ADD_PLAYER_COMMAND_PREFIX, e);
      } catch (InterruptedException e) {
        logger.error("{} Failed to push update to all player connectors {}",
            ServerDebugPrefixString.ADD_PLAYER_COMMAND_PREFIX, e);
      } catch (NullGameHandlerInstance e) {
        logger.error("{} Game handler not found {}",
            ServerDebugPrefixString.ADD_PLAYER_COMMAND_PREFIX, e);
      }
    } else {
      logger.error("{} Failed to obtain deserialized ADD_PLAYER command",
          ServerDebugPrefixString.ADD_PLAYER_COMMAND_PREFIX);
      throw new AddPlayerCommandSerializationErrorException(AddPlayerCommand.class.toString());
    }
  };

  /**
   * The {@link Opcode#MOVE_TILES} command callback worker.
   *
   */
  protected final ControllerConsumer moveTilesConsumer = (playerConnector, command) -> {
    if (command instanceof MoveTilesCommand) {
      try {
        GameHandler handler = ServerControllerState.getGameHandlerByUUID(((MoveTilesCommand) command).getGameId());
        // I check that the player performing the action is the one actually set as active player
        //TODO: add command check to add another layer of security
        if (handler.getGame().getActivePlayer().getPlayerName().equals(playerConnector.getPlayerName())) {
          // TODO: implement moves
        }
      } catch (NullGameHandlerInstance e) {
        logger.error("{} Failed to get game handler from command {}",
            ServerDebugPrefixString.MOVE_TILES_COMMAND_PREFIX, e);
      }
    }
  };

  /**
   * Update all the connected players game state by sending a {@link Game} message.
   *
   * @param handler The current game instance handler.
   * @throws InterruptedException
   *
   */
  protected void updateAllPlayers(GameHandler handler) throws InterruptedException {
    if (handler != null) {
      handler.pushGameState();
    }
  }

  /**
   * A helper mapping to link a {@link Opcode} to the relative worker callback.
   *
   */
  private final Map<Opcode, ControllerConsumer> actions = Map.of(
      Opcode.START, startConsumer,
      Opcode.ADD_PLAYER, addPlayerConsumer,
      Opcode.MOVE_TILES, moveTilesConsumer
    );

  /**
   * Apply the callback to a specific {@link Opcode} received from a
   * {@link PlayerConnector}.
   *
   * @param connector The player connector instance.
   * @param command   The deserialized command.
   *
   */
  public void execute(Optional<PlayerConnector> connector, AbstractCommand command) {
    if (command == null || !connector.isPresent()) {
      return;
    }
    actions.get(command.getOpcode()).accept(connector.get(), command);
  }
}
