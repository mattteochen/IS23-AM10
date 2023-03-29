package it.polimi.is23am10.controller.interfaces;

import it.polimi.is23am10.command.AbstractCommand.Opcode;
import it.polimi.is23am10.command.AbstractCommand;
import it.polimi.is23am10.command.AddPlayerCommand;
import it.polimi.is23am10.command.MoveTilesCommand;
import it.polimi.is23am10.command.StartGameCommand;
import it.polimi.is23am10.controller.ServerControllerState;
import it.polimi.is23am10.controller.ServerDebugPrefixString;
import it.polimi.is23am10.controller.exceptions.AddPlayerCommandSerializationErrorException;
import it.polimi.is23am10.controller.exceptions.MoveTileCommandSerializationErrorException;
import it.polimi.is23am10.controller.exceptions.NullGameHandlerInstance;
import it.polimi.is23am10.controller.exceptions.StartCommandSerializationErrorException;
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
import it.polimi.is23am10.playerconnector.AbstractPlayerConnector;
import java.util.UUID;

public interface IServerControllerAction {
  /**
   * Execute the client request.
   *
   * @param connector The connector to a player.
   * @param commanD The command to be executed.
   *
   */
  void execute(AbstractPlayerConnector connector, AbstractCommand command);

  /**
   * Update all the connected players game state by sending a {@link Game}
   * message.
   *
   * @param handler The current game instance handler.
   * @throws InterruptedException
   *
   */
  static void updateAllPlayers(GameHandler handler) throws InterruptedException {
    if (handler != null) {
      handler.pushGameState();
    }
  }

  /**
   * The {@link Opcode#START} command callback worker.
   * TODO: add client responces.
   *
   */
  final ControllerConsumer startConsumer = (logger, playerConnector, command) -> {
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
        // TODO: as we have a null connector, the model should expose something to
        // remove the player.
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
  final ControllerConsumer addPlayerConsumer = (logger, playerConnector, command) -> {
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
        // TODO: as we have a null connector, the model should expose something to
        // remove the player.
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
  final ControllerConsumer moveTilesConsumer = (logger, playerConnector, command) -> {
    if (command instanceof MoveTilesCommand) {
      try {
        GameHandler handler = ServerControllerState.getGameHandlerByUUID(((MoveTilesCommand) command).getGameId());
        // I check that the player performing the action is the one actually set as
        // active player
        if (handler.getGame().getActivePlayer().getPlayerName()
            .equals(((MoveTilesCommand) command).getMovingPlayer())) {
          // TODO: implement moves
        }
        logger.info("{} Operated Tile move for {} in game {}",
            ServerDebugPrefixString.MOVE_TILES_COMMAND_PREFIX, handler.getGame().getActivePlayer().getPlayerName(),
            handler.getGame().getGameId());
      } catch (NullGameHandlerInstance e) {
        logger.error("{} Failed to get game handler from command {}",
            ServerDebugPrefixString.MOVE_TILES_COMMAND_PREFIX, e);
      }
    } else {
      logger.error("{} Failed to obtain deserialized MOVE_TILES command",
          ServerDebugPrefixString.MOVE_TILES_COMMAND_PREFIX);
      throw new MoveTileCommandSerializationErrorException(MoveTilesCommand.class.toString());
    }
  };
}
