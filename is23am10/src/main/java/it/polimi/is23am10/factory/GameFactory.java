package it.polimi.is23am10.factory;

import it.polimi.is23am10.factory.Exceptions.DuplicatePlayerNameException;
import it.polimi.is23am10.factory.Exceptions.NullPlayerNamesException;
import it.polimi.is23am10.game.Game;
import it.polimi.is23am10.game.exceptions.InvalidMaxPlayerException;
import it.polimi.is23am10.game.exceptions.NullMaxPlayerException;
import it.polimi.is23am10.items.board.exceptions.InvalidNumOfPlayersException;
import it.polimi.is23am10.items.board.exceptions.NullNumOfPlayersException;
import it.polimi.is23am10.items.card.exceptions.AlreadyInitiatedPatternException;
import it.polimi.is23am10.player.Exceptions.NullPlayerIdException;
import it.polimi.is23am10.player.Exceptions.NullPlayerLibraryException;
import it.polimi.is23am10.player.Exceptions.NullPlayerNameException;
import it.polimi.is23am10.player.Exceptions.NullPlayerPrivateCardException;
import it.polimi.is23am10.player.Exceptions.NullPlayerScoreBlocksException;
import it.polimi.is23am10.player.Exceptions.NullPlayerScoreException;

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
	 * @param startingPlayerName The starting player name, who has requested the game.
	 * @param maxPlayerNum The desired maximum player number, specified by the first player.
	 * @throws NullMaxPlayerException 
	 * @throws InvalidMaxPlayerException 
	 * @throws NullPlayerNameException
	 * @throws NullPlayerIdException
	 * @throws NullPlayerLibraryException
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
			NullPlayerIdException, NullPlayerLibraryException, NullPlayerScoreException, NullPlayerPrivateCardException,
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
