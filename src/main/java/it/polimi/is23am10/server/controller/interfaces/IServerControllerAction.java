package it.polimi.is23am10.server.controller.interfaces;

import it.polimi.is23am10.server.command.AbstractCommand;
import it.polimi.is23am10.server.command.AddPlayerCommand;
import it.polimi.is23am10.server.command.GetAvailableGamesCommand;
import it.polimi.is23am10.server.command.MoveTilesCommand;
import it.polimi.is23am10.server.command.SendChatMessageCommand;
import it.polimi.is23am10.server.command.StartGameCommand;
import it.polimi.is23am10.server.command.AbstractCommand.Opcode;
import it.polimi.is23am10.server.controller.ServerControllerRmiBindings;
import it.polimi.is23am10.server.controller.ServerControllerState;
import it.polimi.is23am10.server.controller.ServerDebugPrefixString;
import it.polimi.is23am10.server.controller.exceptions.NullGameHandlerInstance;
import it.polimi.is23am10.server.controller.interfaces.IServerControllerAction;
import it.polimi.is23am10.server.model.factory.exceptions.DuplicatePlayerNameException;
import it.polimi.is23am10.server.model.factory.exceptions.NullPlayerNamesException;
import it.polimi.is23am10.server.model.game.exceptions.FullGameException;
import it.polimi.is23am10.server.model.game.exceptions.InvalidMaxPlayerException;
import it.polimi.is23am10.server.model.game.exceptions.NullAssignedPatternException;
import it.polimi.is23am10.server.model.game.exceptions.NullMaxPlayerException;
import it.polimi.is23am10.server.model.game.exceptions.PlayerNotFoundException;
import it.polimi.is23am10.server.model.items.board.exceptions.BoardGridColIndexOutOfBoundsException;
import it.polimi.is23am10.server.model.items.board.exceptions.BoardGridRowIndexOutOfBoundsException;
import it.polimi.is23am10.server.model.items.board.exceptions.InvalidNumOfPlayersException;
import it.polimi.is23am10.server.model.items.board.exceptions.NullNumOfPlayersException;
import it.polimi.is23am10.server.model.items.bookshelf.exceptions.BookshelfGridColIndexOutOfBoundsException;
import it.polimi.is23am10.server.model.items.bookshelf.exceptions.BookshelfGridRowIndexOutOfBoundsException;
import it.polimi.is23am10.server.model.items.bookshelf.exceptions.NullTileException;
import it.polimi.is23am10.server.model.items.card.exceptions.AlreadyInitiatedPatternException;
import it.polimi.is23am10.server.model.items.card.exceptions.NegativeMatchedBlockCountException;
import it.polimi.is23am10.server.model.items.card.exceptions.NullMatchedBlockCountException;
import it.polimi.is23am10.server.model.items.card.exceptions.NullScoreBlockListException;
import it.polimi.is23am10.server.model.items.scoreblock.exceptions.NotValidScoreBlockValueException;
import it.polimi.is23am10.server.model.player.Player;
import it.polimi.is23am10.server.model.player.exceptions.NullPlayerBookshelfException;
import it.polimi.is23am10.server.model.player.exceptions.NullPlayerIdException;
import it.polimi.is23am10.server.model.player.exceptions.NullPlayerNameException;
import it.polimi.is23am10.server.model.player.exceptions.NullPlayerPrivateCardException;
import it.polimi.is23am10.server.model.player.exceptions.NullPlayerScoreBlocksException;
import it.polimi.is23am10.server.model.player.exceptions.NullPlayerScoreException;
import it.polimi.is23am10.server.network.gamehandler.GameHandler;
import it.polimi.is23am10.server.network.gamehandler.exceptions.NullPlayerConnector;
import it.polimi.is23am10.server.network.messages.AvailableGamesMessage;
import it.polimi.is23am10.server.network.messages.ErrorMessage;
import it.polimi.is23am10.server.network.messages.ErrorMessage.ErrorSeverity;
import it.polimi.is23am10.server.network.playerconnector.AbstractPlayerConnector;
import it.polimi.is23am10.server.network.playerconnector.PlayerConnectorSocket;
import it.polimi.is23am10.server.network.playerconnector.exceptions.NullSocketConnectorException;
import it.polimi.is23am10.server.network.virtualview.VirtualView;
import it.polimi.is23am10.utils.ErrorTypeString;
import it.polimi.is23am10.utils.exceptions.NullIndexValueException;
import it.polimi.is23am10.utils.exceptions.WrongBookShelfPicksException;
import it.polimi.is23am10.utils.exceptions.WrongGameBoardPicksException;
import it.polimi.is23am10.utils.exceptions.WrongMovesNumberException;
import it.polimi.is23am10.server.network.playerconnector.PlayerConnectorRmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * The server controller action interface definition.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
@SuppressWarnings({ "checkstyle:abbreviationaswordinnamecheck" })
public interface IServerControllerAction extends Remote {
  /**
   * Execute the client request.
   *
   * @param connector The connector to a player.
   * @param command   The command to be executed.
   * @throws RemoteException
   *
   */
  void execute(AbstractPlayerConnector connector, AbstractCommand command) throws RemoteException;

  /**
   * Execute the client {@link GetAvailableGamesCommand}.
   * This is intended to be used under the RMI connection protocol when the client's playerConnector has no power to read the msg queue.
   *
   * @param command   The command to be executed.
   * @returns an {@link AvailableGamesMessage} responce.
   * @throws RemoteException
   *
   */
  AvailableGamesMessage execute(GetAvailableGamesCommand command) throws RemoteException;

  /**
   * The {@link Opcode#START} command callback worker.
   *
   */
  final ControllerConsumer<Void, AbstractCommand> startConsumer = (logger, playerConnector, command) -> {
    ErrorMessage errorMsg = null;

    try {
      if (playerConnector == null) {
        throw new NullPlayerConnector();
      }

      final String playerName = ((StartGameCommand) command).getStartingPlayerName();
      final Integer maxPlayers = ((StartGameCommand) command).getMaxPlayers();
      GameHandler gameHandler = new GameHandler(playerName, maxPlayers);
      final Player playerRef = gameHandler.getGame().getPlayerByName(playerName);

      // populate the connector with the game and player reference.
      playerConnector.setGameId(gameHandler.getGame().getGameId());
      playerConnector.setPlayer(playerRef);

      // add the new game handler instance on the game pool.
      ServerControllerState.addGameHandler(gameHandler);
      // add the new player connector instance on the player pool.
      ServerControllerState.addPlayerConnector(playerConnector);

      // if RMI, rebind the connector
      if (playerConnector.getClass() == PlayerConnectorRmi.class) {
        ServerControllerRmiBindings.rebindPlayerConnector(playerConnector);
      }

      // add the new player connector to the game handler.
      gameHandler.addPlayerConnector(playerConnector);

      logger.info("{} Started new game with id {} from {}",
          ServerDebugPrefixString.START_COMMAND_PREFIX,
          gameHandler.getGame().getGameId(), playerName);

      // send the game model update to all the connected players
      gameHandler.pushGameState();
    } catch (NullNumOfPlayersException | NullPlayerNamesException | NullPlayerScoreBlocksException
        | NullPlayerPrivateCardException | NullPlayerScoreException | NullPlayerBookshelfException
        | NullPlayerIdException | NullPlayerNameException
        | NullMaxPlayerException | AlreadyInitiatedPatternException
        | NullAssignedPatternException | PlayerNotFoundException
        | NotValidScoreBlockValueException e) {
      logger.error("{} {} {}",
          ServerDebugPrefixString.START_COMMAND_PREFIX,
          ErrorTypeString.ERROR_INITIALIZING_NEW_GAME, e);
      errorMsg = new ErrorMessage(ErrorTypeString.ERROR_INITIALIZING_NEW_GAME, ErrorSeverity.ERROR);
    } catch (InvalidNumOfPlayersException | InvalidMaxPlayerException | DuplicatePlayerNameException
        | FullGameException e) {
      logger.error("{} {} {}",
          ServerDebugPrefixString.START_COMMAND_PREFIX,
          ErrorTypeString.ERROR_ADDING_PLAYERS, e);
      errorMsg = new ErrorMessage(ErrorTypeString.ERROR_ADDING_PLAYERS, ErrorSeverity.ERROR);
    } catch (NullGameHandlerInstance e) {
      logger.error("{} {} {}",
          ServerDebugPrefixString.START_COMMAND_PREFIX,
          ErrorTypeString.ERROR_ADDING_HANDLER, e);
    } catch (NullPlayerConnector e) {
      // TODO: as we have a null connector, the model should expose something to
      // remove the player.
      logger.error("{} {} {}",
          ServerDebugPrefixString.START_COMMAND_PREFIX,
          ErrorTypeString.ERROR_ADDING_CONNECTOR, e);
      // Not adding the error here since it will not be possible to be sent
      // to player if there is no valid player connector.
    } catch (InterruptedException e) {
      logger.error("{} {} {}",
          ServerDebugPrefixString.START_COMMAND_PREFIX,
          ErrorTypeString.ERROR_INTERRUPTED, e);
      errorMsg = new ErrorMessage(ErrorTypeString.ERROR_INTERRUPTED, ErrorSeverity.ERROR);
    } catch (RemoteException e) {
      logger.error("{} {} {}",
          ServerDebugPrefixString.START_COMMAND_PREFIX,
          ErrorTypeString.ERROR_RMI_EXPOSURE, e);
    } finally {
      if (errorMsg != null) {
        try {
          playerConnector.addMessageToQueue(errorMsg);
        } catch (InterruptedException e) {
          logger.error("{} {} {}",
              ServerDebugPrefixString.START_COMMAND_PREFIX,
              ErrorTypeString.ERROR_INTERRUPTED, e);
        }
      }
    }
    return null;
  };

  /**
   * The {@link Opcode#ADD_PLAYER} command callback worker.
   *
   */
  final ControllerConsumer<Void, AbstractCommand> addPlayerConsumer = (logger, playerConnector, command) -> {
    ErrorMessage errorMsg = null;

    try {
      if (playerConnector == null) {
        throw new NullPlayerConnector();
      }

      final String playerName = ((AddPlayerCommand) command).getPlayerName();
      final UUID gameId = ((AddPlayerCommand) command).getGameId();
      final GameHandler gameHandler = ServerControllerState.getGameHandlerByUUID(gameId);

      /*
       * Checks if the client is trying to reconnect to the game ,
       * so if there's already an inactive Player in the game with that name,
       * if found one we're executing the if statement and replacing the old socket
       * connector with a new one connected, otherwise the else branch is executed
       * and the player is normally added to the game.
       */
      if (gameHandler.getGame().getPlayerNames().contains(playerName) &&
          !gameHandler.getGame().getPlayerByName(playerName).getIsConnected()) {
        try {
          ((PlayerConnectorSocket) gameHandler.getPlayerConnectors()
              .stream()
              .filter(pc -> !pc.getPlayer().getPlayerName().equals(playerName))
              .findFirst()
              .get())
              .setConnector(((PlayerConnectorSocket) playerConnector).getConnector());

          gameHandler.getGame().getPlayerByName(playerName).setIsConnected(true);
          gameHandler.getPlayerConnectors()
              .forEach(pc -> {
                try {
                  pc.addMessageToQueue(
                      new ErrorMessage(playerName + " reconnected to the game.", ErrorSeverity.WARNING));
                } catch (InterruptedException e) {
                  logger.error("{} {}", ErrorTypeString.ERROR_INTERRUPTED, e);
                }
              });

        } catch (NullSocketConnectorException e) {
          logger.error("{} {} {}",
              ServerDebugPrefixString.START_COMMAND_PREFIX,
              ErrorTypeString.ERROR_ADDING_PLAYERS, e);
        }
      } else {
        // add the new player in the game model.
        // note, it is essential that the the model is updated first
        // to avoid wrong parameters in connectors if any exception
        // will be thrown from the model.
        gameHandler.getGame().addPlayer(playerName);

        final Player playerRef = gameHandler.getGame().getPlayerByName(playerName);

        // populate the connector with the game and player reference.
        playerConnector.setGameId(gameId);
        playerConnector.setPlayer(playerRef);

        // add the new player connector instance to the game's player pool.
        gameHandler.addPlayerConnector(playerConnector);

        // add the new player connector instance on the player pool.
        ServerControllerState.addPlayerConnector(playerConnector);
      }

      // if RMI, rebind the connector
      if (playerConnector.getClass() == PlayerConnectorRmi.class) {
        ServerControllerRmiBindings.rebindPlayerConnector(playerConnector);
      }

      logger.info("{} Added new player {} to game {}",
          ServerDebugPrefixString.ADD_PLAYER_COMMAND_PREFIX,
          playerName, gameId);

      // send the game model update to all the connected players
      gameHandler.pushGameState();
    } catch (NullPlayerNamesException | NullPlayerScoreBlocksException
        | NullPlayerPrivateCardException | NullPlayerScoreException | NullPlayerBookshelfException
        | NullPlayerIdException | NullPlayerNameException | AlreadyInitiatedPatternException
        | NullAssignedPatternException | PlayerNotFoundException e) {
      logger.error("{} {} {}",
          ServerDebugPrefixString.ADD_PLAYER_COMMAND_PREFIX,
          ErrorTypeString.ERROR_ADDING_PLAYERS, e);
      errorMsg = new ErrorMessage(ErrorTypeString.ERROR_ADDING_PLAYERS, ErrorSeverity.ERROR);
    } catch (DuplicatePlayerNameException | FullGameException e) {
      logger.error("{} {} {}",
          ServerDebugPrefixString.ADD_PLAYER_COMMAND_PREFIX,
          ErrorTypeString.ERROR_ADDING_PLAYERS, e);
      errorMsg = new ErrorMessage(ErrorTypeString.ERROR_ADDING_PLAYERS, ErrorSeverity.ERROR);
    } catch (NullPlayerConnector e) {
      // TODO: as we have a null connector, the model should expose something to
      // remove the player.
      logger.error("{} {} {}",
          ServerDebugPrefixString.ADD_PLAYER_COMMAND_PREFIX,
          ErrorTypeString.ERROR_ADDING_CONNECTOR, e);
    } catch (InterruptedException e) {
      logger.error("{} {} {}",
          ServerDebugPrefixString.ADD_PLAYER_COMMAND_PREFIX,
          ErrorTypeString.ERROR_INTERRUPTED, e);
      errorMsg = new ErrorMessage(ErrorTypeString.ERROR_INTERRUPTED, ErrorSeverity.ERROR);
    } catch (NullGameHandlerInstance e) {
      logger.error("{} {} {}",
          ServerDebugPrefixString.ADD_PLAYER_COMMAND_PREFIX,
          ErrorTypeString.ERROR_ADDING_HANDLER, e);
      errorMsg = new ErrorMessage(ErrorTypeString.ERROR_ADDING_PLAYERS, ErrorSeverity.ERROR);
    } catch (RemoteException e) {
      logger.error("{} {} {}",
          ServerDebugPrefixString.ADD_PLAYER_COMMAND_PREFIX,
          ErrorTypeString.ERROR_RMI_EXPOSURE, e);
    } finally {
      if (errorMsg != null) {
        try {
          playerConnector.addMessageToQueue(errorMsg);
        } catch (InterruptedException e) {
          logger.error("{} {} {}",
              ServerDebugPrefixString.ADD_PLAYER_COMMAND_PREFIX,
              ErrorTypeString.ERROR_INTERRUPTED, e);
        }
      }
    }
    return null;
  };

  /**
   * The {@link Opcode#GET_GAMES} command callback worker.
   *
   */
  final ControllerConsumer<Void, AbstractCommand> getAvailableGamesConsumer = (logger, playerConnector, command) -> {

    List<VirtualView> availableGames = ServerControllerState.getGamePools()
        .stream()
        .map(gh -> gh.getGame())
        .filter(g -> g.getPlayers().size() < g.getMaxPlayer())
        .map(g -> new VirtualView(g))
        .collect(Collectors.toList());

    try {
      playerConnector.addMessageToQueue(new AvailableGamesMessage(availableGames, playerConnector.getPlayer()));
    } catch (InterruptedException e) {
      logger.error("{} {} {}",
          ServerDebugPrefixString.START_COMMAND_PREFIX,
          ErrorTypeString.ERROR_INTERRUPTED, e);
    }
    return null;
  };

  /**
   * The {@link Opcode#GET_GAMES} command callback worker for RMI.
   * Note that playerConnector field is expected to be null.
   *
   */
  final ControllerConsumer<AvailableGamesMessage, GetAvailableGamesCommand> getAvailableGamesConsumerRmi = (logger, playerConnector, command) -> {
    List<VirtualView> availableGames = ServerControllerState.getGamePools()
    .stream()
    .map(gh -> gh.getGame())
    .filter(g -> g.getPlayers().size() < g.getMaxPlayer())
    .map(g -> new VirtualView(g))
    .collect(Collectors.toList());

    return new AvailableGamesMessage(availableGames);
  };

  /**
   * The {@link Opcode#SEND_CHAT_MESSAGE} command callback worker.
   *
   */
  final ControllerConsumer sendChatMessageConsumer = (logger, playerConnector, command) -> {
    try {
      if (playerConnector == null) {
        throw new NullPlayerConnector();
      }
      SendChatMessageCommand scmCommand = (SendChatMessageCommand) command;
      GameHandler handler = ServerControllerState.getGameHandlerByUUID(playerConnector.getGameId());

      if (scmCommand.getChatMessage().isBroadcast()) {
        handler.getPlayerConnectors()
            .stream()
            .filter(pc -> !pc.getPlayer().getPlayerName().equals(playerConnector.getPlayer().getPlayerName()))
            .forEach(pc -> {
              try {
                pc.addMessageToQueue(scmCommand.getChatMessage());
              } catch (InterruptedException e) {
                logger.error("{} {} {}",
                    ServerDebugPrefixString.SEND_CHAT_MESSAGE_COMMAND_PREFIX,
                    ErrorTypeString.ERROR_INTERRUPTED, e);
              }
            });
      } else {
        String receiverName = scmCommand.getChatMessage().getReceiver().getPlayerName();

        handler.getPlayerConnectors()
            .stream()
            .filter(pc -> pc.getPlayer().getPlayerName().equals(receiverName))
            .findFirst()
            .get()
            .addMessageToQueue(scmCommand.getChatMessage());
      }
    } catch (InterruptedException e) {
      logger.error("{} {} {}",
          ServerDebugPrefixString.SEND_CHAT_MESSAGE_COMMAND_PREFIX,
          ErrorTypeString.ERROR_INTERRUPTED, e);
    } catch (NullGameHandlerInstance e) {
      logger.error("{} {} {}",
          ServerDebugPrefixString.SEND_CHAT_MESSAGE_COMMAND_PREFIX,
          ErrorTypeString.ERROR_ADDING_HANDLER, e);
    } catch (NullPlayerConnector e) {
      logger.error("{} {} {}",
          ServerDebugPrefixString.SEND_CHAT_MESSAGE_COMMAND_PREFIX,
          ErrorTypeString.ERROR_ADDING_CONNECTOR, e);
    }
  };

  /**
   * The {@link Opcode#MOVE_TILES} command callback worker.
   *
   */
  final ControllerConsumer<Void, AbstractCommand> moveTilesConsumer = (logger, playerConnector, command) -> {
    ErrorMessage errorMsg = null;

    try {
      MoveTilesCommand mtCommand = (MoveTilesCommand) command;
      GameHandler handler = ServerControllerState.getGameHandlerByUUID(mtCommand.getGameId());
      // I check that the player performing the action is the one actually set as
      // active player
      if (handler.getGame().getActivePlayer().equals(playerConnector.getPlayer())) {
        handler.getGame().activePlayerMove(mtCommand.getMoves());
        handler.pushGameState();
      }
      logger.info("{} Operated Tile move for {} in game {}",
          ServerDebugPrefixString.MOVE_TILES_COMMAND_PREFIX,
          handler.getGame().getActivePlayer().getPlayerName(),
          handler.getGame().getGameId());
    } catch (NullGameHandlerInstance e) {
      logger.error("{} {} {}",
          ServerDebugPrefixString.MOVE_TILES_COMMAND_PREFIX,
          ErrorTypeString.ERROR_ADDING_HANDLER, e);
    } catch (BoardGridColIndexOutOfBoundsException | BoardGridRowIndexOutOfBoundsException
        | BookshelfGridColIndexOutOfBoundsException | BookshelfGridRowIndexOutOfBoundsException
        | NullPointerException | NullIndexValueException | NullTileException
        | NullPlayerBookshelfException | NullScoreBlockListException
        | NullMatchedBlockCountException | NegativeMatchedBlockCountException
        | WrongMovesNumberException | WrongGameBoardPicksException
        | WrongBookShelfPicksException e) {
      logger.warn("{} {} {}",
          ServerDebugPrefixString.MOVE_TILES_COMMAND_PREFIX,
          ErrorTypeString.ERROR_INVALID_MOVE, e);
      errorMsg = new ErrorMessage(ErrorTypeString.ERROR_INVALID_MOVE, ErrorSeverity.ERROR);
    } catch (InterruptedException e) {
      logger.error("{} {} {}",
          ServerDebugPrefixString.MOVE_TILES_COMMAND_PREFIX,
          ErrorTypeString.ERROR_INTERRUPTED, e);
      errorMsg = new ErrorMessage(ErrorTypeString.ERROR_INTERRUPTED, ErrorSeverity.ERROR);
    } finally {
      if (errorMsg != null) {
        try {
          playerConnector.addMessageToQueue(errorMsg);
        } catch (InterruptedException e) {
          logger.error("{} {} {}",
              ServerDebugPrefixString.MOVE_TILES_COMMAND_PREFIX,
              ErrorTypeString.ERROR_INTERRUPTED, e);
        }
      }
    }
    return null;
  };
}
