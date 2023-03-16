package it.polimi.is23am10.server.factory;

import it.polimi.is23am10.server.factory.exceptions.DuplicatePlayerNameException;
import it.polimi.is23am10.server.factory.exceptions.NullPlayerNamesException;
import it.polimi.is23am10.server.game.Game;
import it.polimi.is23am10.server.game.exceptions.InvalidMaxPlayerException;
import it.polimi.is23am10.server.game.exceptions.NullMaxPlayerException;
import it.polimi.is23am10.server.items.board.exceptions.InvalidNumOfPlayersException;
import it.polimi.is23am10.server.items.board.exceptions.NullNumOfPlayersException;
import it.polimi.is23am10.server.items.card.exceptions.AlreadyInitiatedPatternException;
import it.polimi.is23am10.server.player.exceptions.NullPlayerBookshelfException;
import it.polimi.is23am10.server.player.exceptions.NullPlayerIdException;
import it.polimi.is23am10.server.player.exceptions.NullPlayerNameException;
import it.polimi.is23am10.server.player.exceptions.NullPlayerPrivateCardException;
import it.polimi.is23am10.server.player.exceptions.NullPlayerScoreBlocksException;
import it.polimi.is23am10.server.player.exceptions.NullPlayerScoreException;

/**
 * The GameFactory class definition.
 * This creates a new {@link Game} object.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
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
   * 
   */
  public static Game getNewGame(String startingPlayerName, Integer maxPlayerNum)
      throws NullMaxPlayerException, InvalidMaxPlayerException, NullPlayerNameException,
      NullPlayerIdException, NullPlayerBookshelfException, NullPlayerScoreException, NullPlayerPrivateCardException,
      NullPlayerScoreBlocksException, DuplicatePlayerNameException, AlreadyInitiatedPatternException,
      NullPlayerNamesException, InvalidNumOfPlayersException, NullNumOfPlayersException {
    Game game = new Game();

    game.setMaxPlayers(maxPlayerNum);
    game.addPlayer(startingPlayerName);
    game.setGameBoard();
    game.setSharedCards();
    game.setEnded(false);

    return game;
  }
}
