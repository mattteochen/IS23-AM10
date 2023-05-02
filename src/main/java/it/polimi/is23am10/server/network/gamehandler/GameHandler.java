package it.polimi.is23am10.server.network.gamehandler;

import it.polimi.is23am10.server.model.factory.GameFactory;
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
import it.polimi.is23am10.server.model.items.card.exceptions.AlreadyInitiatedPatternException;
import it.polimi.is23am10.server.model.items.scoreblock.exceptions.NotValidScoreBlockValueException;
import it.polimi.is23am10.server.model.player.exceptions.NullPlayerBookshelfException;
import it.polimi.is23am10.server.model.player.exceptions.NullPlayerIdException;
import it.polimi.is23am10.server.model.player.exceptions.NullPlayerNameException;
import it.polimi.is23am10.server.model.player.exceptions.NullPlayerPrivateCardException;
import it.polimi.is23am10.server.model.player.exceptions.NullPlayerScoreBlocksException;
import it.polimi.is23am10.server.model.player.exceptions.NullPlayerScoreException;
import it.polimi.is23am10.server.network.gamehandler.exceptions.NullPlayerConnector;
import it.polimi.is23am10.server.network.messages.GameMessage;
import it.polimi.is23am10.server.network.playerconnector.AbstractPlayerConnector;
import it.polimi.is23am10.server.network.virtualview.VirtualView;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
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
  private Set<AbstractPlayerConnector> playerConnectors =
      Collections.synchronizedSet(new HashSet<>());

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
   * @throws NullAssignedPatternException
   * @throws FullGameException
   * @throws NotValidScoreBlockValueException
   * @throws PlayerNotFoundException
   *
   */
  public GameHandler(String firstPlayerName, Integer maxPlayersNum)
      throws NullMaxPlayerException, InvalidMaxPlayerException, NullPlayerNameException,
      NullPlayerIdException, NullPlayerBookshelfException, NullPlayerScoreException,
      NullPlayerPrivateCardException, NullPlayerScoreBlocksException, DuplicatePlayerNameException,
      AlreadyInitiatedPatternException, NullPlayerNamesException, InvalidNumOfPlayersException,
      NullNumOfPlayersException, NullAssignedPatternException, FullGameException, NotValidScoreBlockValueException, PlayerNotFoundException {
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
   * Getter for {@link AbstractPlayerConnector} list instance.
   *
   * @return The current game instance containing the game state.
   *
   */
  public synchronized Set<AbstractPlayerConnector> getPlayerConnectors() {
    return playerConnectors;
  }

  /**
   * Add a new player connector from socket server.
   * Will accept a built instance of {@link AbstractPlayerConnector}
   *
   * @param playerConnector The connector to be added to the current game.
   * @throws NullPlayerConnector
   *
   */
  public void addPlayerConnector(AbstractPlayerConnector playerConnector)
      throws NullPlayerConnector {
    if (playerConnector == null) {
      throw new NullPlayerConnector();
    }
    playerConnectors.add(playerConnector);
  }

  /**
   * Push a new game state to the message queue for each connected player.
   *
   * @throws InterruptedException
   *
   */
  public void pushGameState() throws InterruptedException {
    // iterating over the Collections.synchronizedList requires synch.
    synchronized (playerConnectors) {
      for (AbstractPlayerConnector pc : playerConnectors) {
        VirtualView gameCopy = new VirtualView(game);
        if (game.getStatus() != GameStatus.ENDED) {
          gameCopy.getPlayers()
          .stream()
          .filter(p -> !p.getPlayerName().equals(pc.getPlayer().getPlayerName()))
          .forEach(p -> p.obfuscatePrivateCard());
        }
        // synch is performed by the blocking queue.
        pc.addMessageToQueue(new GameMessage(gameCopy));
      }
    }
  }

  /**
   * {@inheritDoc}
   *
   */
  public boolean equals(Object obj) {
    if (!(obj instanceof GameHandler)) {
      return false;
    }
    GameHandler casted = (GameHandler) obj;
    return game.getGameId().equals(casted.getGame().getGameId());
  }

  /**
   * {@inheritDoc}
   *
   */
  public int hashCode() {
    return game.getGameId().hashCode();
  }
}
