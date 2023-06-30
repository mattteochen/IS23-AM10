package it.polimi.is23am10.server.model.factory;

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
import java.util.Arrays;

/**
 * The GameFactory class definition. This creates a new {@link Game} object.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
@SuppressWarnings({"checkstyle:nonemptyatclausedescriptioncheck"})
public final class GameFactory {
  /** Private constructor. */
  private GameFactory() {}

  /**
   * Create a new {@link Game} instance.
   *
   * @param startingPlayerName The starting player name, who has requested the game.
   * @param maxPlayerNum The desired maximum player number, specified by the first player.
   * @throws NullMaxPlayerException If no value for maximum number of players in the game is
   *     provided.
   * @throws InvalidMaxPlayerException If value for maximum number of players in the game is not
   *     valid.
   * @throws NullPlayerNameException If player name is null.
   * @throws NullPlayerIdException If player id is null.
   * @throws NullPlayerBookshelfException If bookshelf is null.
   * @throws NullPlayerScoreException If player's score object is null.
   * @throws NullPlayerPrivateCardException If player's private card object is null.
   * @throws NullPlayerScoreBlocksException If player's scoreblocks list is null.
   * @throws DuplicatePlayerNameException If player with that name already exists.
   * @throws AlreadyInitiatedPatternException If assigning a pattern to a card that already has one.
   * @throws NullPlayerNamesException If, while adding multiple players, the list of player names is
   *     null.
   * @throws InvalidNumOfPlayersException If, while adding multiple players, there is an invalid
   *     number of them.
   * @throws NullNumOfPlayersException If the number of players provided when filling the board is
   *     null.
   * @throws NullAssignedPatternException If the pattern assigned to a card is null.
   * @throws NotValidScoreBlockValueException If the value assigned to a scoreblock is not valid.
   * @throws PlayerNotFoundException If the player with the name provided is not found.
   */
  public static Game getNewGame(String startingPlayerName, Integer maxPlayerNum)
      throws NullMaxPlayerException,
          InvalidMaxPlayerException,
          NullPlayerNameException,
          NullPlayerIdException,
          NullPlayerBookshelfException,
          NullPlayerScoreException,
          NullPlayerPrivateCardException,
          NullPlayerScoreBlocksException,
          DuplicatePlayerNameException,
          AlreadyInitiatedPatternException,
          NullPlayerNamesException,
          InvalidNumOfPlayersException,
          NullNumOfPlayersException,
          NullAssignedPatternException,
          FullGameException,
          NotValidScoreBlockValueException,
          PlayerNotFoundException {

    Game game = new Game();
    game.setMaxPlayers(maxPlayerNum);
    SharedCard firstCard = new SharedCard(game.getAssignedSharedPatterns(), game.getMaxPlayer());
    game.addAssignedSharedPattern(firstCard.getPattern());
    SharedCard secondCard = new SharedCard(game.getAssignedSharedPatterns(), game.getMaxPlayer());
    game.addAssignedSharedPattern(secondCard.getPattern());

    game.addPlayer(startingPlayerName);
    // For testing purposes, setting active player to the "creator" player. Will be randomized once
    // all players join.
    game.setActivePlayer(game.getPlayerByName(startingPlayerName));
    game.setGameBoard();
    game.setSharedCards(Arrays.asList(firstCard, secondCard));
    game.setStatus(GameStatus.WAITING_FOR_PLAYERS);

    return game;
  }
}
