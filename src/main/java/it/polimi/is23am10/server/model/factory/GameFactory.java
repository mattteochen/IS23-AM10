package it.polimi.is23am10.server.model.factory;

import java.util.Arrays;

import it.polimi.is23am10.server.model.factory.exceptions.DuplicatePlayerNameException;
import it.polimi.is23am10.server.model.factory.exceptions.NullPlayerNamesException;
import it.polimi.is23am10.server.model.game.Game;
import it.polimi.is23am10.server.model.game.Game.GameStatus;
import it.polimi.is23am10.server.model.game.exceptions.FullGameException;
import it.polimi.is23am10.server.model.game.exceptions.InvalidMaxPlayerException;
import it.polimi.is23am10.server.model.game.exceptions.NullAssignedPatternException;
import it.polimi.is23am10.server.model.game.exceptions.NullMaxPlayerException;
import it.polimi.is23am10.server.model.game.exceptions.PlayerNotFoundException;
import it.polimi.is23am10.server.model.items.board.exceptions.InvalidNumOfPlayersException;
import it.polimi.is23am10.server.model.items.board.exceptions.NullNumOfPlayersException;
import it.polimi.is23am10.server.model.items.card.SharedCard;
import it.polimi.is23am10.server.model.items.card.exceptions.AlreadyInitiatedPatternException;
import it.polimi.is23am10.server.model.items.scoreblock.exceptions.NotValidScoreBlockValueException;
import it.polimi.is23am10.server.model.player.exceptions.NullPlayerBookshelfException;
import it.polimi.is23am10.server.model.player.exceptions.NullPlayerIdException;
import it.polimi.is23am10.server.model.player.exceptions.NullPlayerNameException;
import it.polimi.is23am10.server.model.player.exceptions.NullPlayerPrivateCardException;
import it.polimi.is23am10.server.model.player.exceptions.NullPlayerScoreBlocksException;
import it.polimi.is23am10.server.model.player.exceptions.NullPlayerScoreException;

/**
 * The GameFactory class definition.
 * This creates a new {@link Game} object.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
@SuppressWarnings({ "checkstyle:nonemptyatclausedescriptioncheck" })
public final class GameFactory {
  /**
   * Private constructor.
   *
   */
  private GameFactory() {

  }

  /**
   * Create a new {@link Game} instance.
   *
   * @param startingPlayerName The starting player name, who has requested the
   *                           game.
   * @param maxPlayerNum       The desired maximum player number, specified by the
   *                           first player.
   * @throws NullMaxPlayerException
   * @throws InvalidMaxPlayerException
   * @throws NullPlayerNameException
   * @throws NullPlayerIdException
   * @throws NullPlayerBookshelfException
   * @throws NullPlayerScoreException
   * @throws NullPlayerPrivateCardException
   * @throws NullPlayerScoreBlocksException
   * @throws DuplicatePlayerNameException
   * @throws AlreadyInitiatedPatternException
   * @throws NullPlayerNamesException
   * @throws InvalidNumOfPlayersException
   * @throws NullNumOfPlayersException
   * @throws NullAssignedPatternException
   * @throws NotValidScoreBlockValueException
   * @throws PlayerNotFoundException
   * 
   */
  public static Game getNewGame(String startingPlayerName, Integer maxPlayerNum)
      throws NullMaxPlayerException, InvalidMaxPlayerException, NullPlayerNameException,
      NullPlayerIdException, NullPlayerBookshelfException, NullPlayerScoreException,
      NullPlayerPrivateCardException, NullPlayerScoreBlocksException,
      DuplicatePlayerNameException, AlreadyInitiatedPatternException,
      NullPlayerNamesException, InvalidNumOfPlayersException, NullNumOfPlayersException,
      NullAssignedPatternException,FullGameException, NotValidScoreBlockValueException, PlayerNotFoundException {

    Game game = new Game();
    game.setMaxPlayers(maxPlayerNum);
    SharedCard firstCard = new SharedCard(game.getAssignedSharedPatterns(), game.getMaxPlayer());
    game.addAssignedSharedPattern(firstCard.getPattern());
    SharedCard secondCard = new SharedCard(game.getAssignedSharedPatterns(), game.getMaxPlayer());
    game.addAssignedSharedPattern(secondCard.getPattern());

    game.addPlayer(startingPlayerName);
    // For testing purposes, setting active player to the "creator" player. Will be randomized once all players join.
    game.setActivePlayer(game.getPlayerByName(startingPlayerName));
    game.setGameBoard();
    game.setSharedCards(Arrays.asList(firstCard, secondCard));
    game.setStatus(GameStatus.WAITING_FOR_PLAYERS);

    return game;
  }
}
