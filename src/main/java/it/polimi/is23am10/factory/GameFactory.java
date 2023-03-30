package it.polimi.is23am10.factory;

import it.polimi.is23am10.factory.exceptions.DuplicatePlayerNameException;
import it.polimi.is23am10.factory.exceptions.NullPlayerNamesException;
import it.polimi.is23am10.game.Game;
import it.polimi.is23am10.game.exceptions.InvalidMaxPlayerException;
import it.polimi.is23am10.game.exceptions.NullAssignedPatternException;
import it.polimi.is23am10.game.exceptions.NullMaxPlayerException;
import it.polimi.is23am10.items.board.exceptions.InvalidNumOfPlayersException;
import it.polimi.is23am10.items.board.exceptions.NullNumOfPlayersException;
import it.polimi.is23am10.items.card.SharedCard;
import it.polimi.is23am10.items.card.exceptions.AlreadyInitiatedPatternException;
import it.polimi.is23am10.player.exceptions.NullPlayerBookshelfException;
import it.polimi.is23am10.player.exceptions.NullPlayerIdException;
import it.polimi.is23am10.player.exceptions.NullPlayerNameException;
import it.polimi.is23am10.player.exceptions.NullPlayerPrivateCardException;
import it.polimi.is23am10.player.exceptions.NullPlayerScoreBlocksException;
import it.polimi.is23am10.player.exceptions.NullPlayerScoreException;
import main.java.it.polimi.is23am10.game.exceptions.FullGameException;

import java.util.Arrays;

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
   * 
   */
  public static Game getNewGame(String startingPlayerName, Integer maxPlayerNum)
      throws NullMaxPlayerException, InvalidMaxPlayerException, NullPlayerNameException,
      NullPlayerIdException, NullPlayerBookshelfException, NullPlayerScoreException,
      NullPlayerPrivateCardException, NullPlayerScoreBlocksException,
      DuplicatePlayerNameException, AlreadyInitiatedPatternException,
      NullPlayerNamesException, InvalidNumOfPlayersException, NullNumOfPlayersException,
      NullAssignedPatternException,FullGameException {

    Game game = new Game();
    SharedCard firstCard = new SharedCard(game.getAssignedSharedPatterns());
    game.addAssignedSharedPattern(firstCard.getPattern());
    SharedCard secondCard = new SharedCard(game.getAssignedSharedPatterns());
    game.addAssignedSharedPattern(secondCard.getPattern());

    game.setMaxPlayers(maxPlayerNum);
    game.addPlayer(startingPlayerName);
    game.setGameBoard();
    game.setSharedCards(Arrays.asList(firstCard, secondCard));
    game.setEnded(false);

    return game;
  }
}
