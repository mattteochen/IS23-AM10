package it.polimi.is23am10.server.gamehandler;

import it.polimi.is23am10.server.factory.GameFactory;
import it.polimi.is23am10.server.factory.exceptions.DuplicatePlayerNameException;
import it.polimi.is23am10.server.factory.exceptions.NullPlayerNamesException;
import it.polimi.is23am10.server.game.Game;
import it.polimi.is23am10.server.game.exceptions.InvalidMaxPlayerException;
import it.polimi.is23am10.server.game.exceptions.NullMaxPlayerException;
import it.polimi.is23am10.server.gamehandler.exceptions.NullPlayerConnector;
import it.polimi.is23am10.server.items.board.exceptions.InvalidNumOfPlayersException;
import it.polimi.is23am10.server.items.board.exceptions.NullNumOfPlayersException;
import it.polimi.is23am10.server.items.card.exceptions.AlreadyInitiatedPatternException;
import it.polimi.is23am10.server.player.exceptions.NullPlayerBookshelfException;
import it.polimi.is23am10.server.player.exceptions.NullPlayerIdException;
import it.polimi.is23am10.server.player.exceptions.NullPlayerNameException;
import it.polimi.is23am10.server.player.exceptions.NullPlayerPrivateCardException;
import it.polimi.is23am10.server.player.exceptions.NullPlayerScoreBlocksException;
import it.polimi.is23am10.server.player.exceptions.NullPlayerScoreException;
import it.polimi.is23am10.server.playerconnector.PlayerConnector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The match class definition.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public class GameHandler {

  /**
   * The logger, an instance of {@link Logger}.
   *
   */
  final Logger logger = LogManager.getLogger(GameHandler.class);

  /**
   * The underlying game state.
   *
   */
  private Game game;

  /**
   * The connected players connectors.
   *
   */
  private List<PlayerConnector> playerConnectors = Collections.synchronizedList(new ArrayList<>());

  /**
   * Constructor.
   *
   * @param firstPlayerName The match starting player name.
   * @param maxPlayersNum   The chosen max players for this match.
   * @throws NullNumOfPlayersException
   * @throws InvalidNumOfPlayersException
   * @throws NullPlayerNamesException
   * @throws AlreadyInitiatedPatternException
   * @throws DuplicatePlayerNameException
   * @throws NullPlayerScoreBlocksException
   * @throws NullPlayerPrivateCardException
   * @throws NullPlayerScoreException
   * @throws NullPlayerBookshelfException
   * @throws NullPlayerIdException
   * @throws NullPlayerNameException
   * @throws InvalidMaxPlayerException
   * @throws NullMaxPlayerException
   *
   */
  public GameHandler(String firstPlayerName, Integer maxPlayersNum)
      throws NullMaxPlayerException, InvalidMaxPlayerException, NullPlayerNameException,
      NullPlayerIdException, NullPlayerBookshelfException, NullPlayerScoreException,
      NullPlayerPrivateCardException, NullPlayerScoreBlocksException, DuplicatePlayerNameException,
      AlreadyInitiatedPatternException, NullPlayerNamesException, InvalidNumOfPlayersException,
      NullNumOfPlayersException {
    this.game = GameFactory.getNewGame(firstPlayerName, maxPlayersNum);
  }

  /**
   * Getter for {@link Game} instance.
   *
   * @return The current game instance containing the game state.
   *
   */
  public synchronized Game getGame() {
    return game;
  }

  /**
   * Getter for {@link PlayerConnector} list instance.
   *
   * @return The current game instance containing the game state.
   *
   */
  public synchronized List<PlayerConnector> getPlayerConnectors() {
    return playerConnectors;
  }

  /**
   * Add a new player connector from socket server.
   * Will accept a built instance of {@link PlayerConnector}
   *
   * @param playerConnector The connector to be added to the current game.
   * @throws NullPlayerConnector
   *
   */
  public void addPlayerConnector(PlayerConnector playerConnector)
      throws NullPlayerConnector {
    if (playerConnector == null) {
      throw new NullPlayerConnector();
    }
    playerConnectors.add(playerConnector);
  }
}
