package it.polimi.is23am10.server.gamehandler;

import it.polimi.is23am10.server.factory.GameFactory;
import it.polimi.is23am10.server.factory.exceptions.DuplicatePlayerNameException;
import it.polimi.is23am10.server.game.Game;
import it.polimi.is23am10.server.game.exceptions.InvalidMaxPlayerException;
import it.polimi.is23am10.server.playerconnector.PlayerConnector;
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
  private List<PlayerConnector> playerConnectors;

  /**
   * Constructor.
   * 
   * @param firstPlayerName The match starting player name.
   * @param maxPlayersNum The chosen max players for this match.
   *
   */
  public GameHandler(String firstPlayerName, Integer maxPlayersNum) {
    try {
      this.game = GameFactory.getNewGame(firstPlayerName, maxPlayersNum);
    } catch (DuplicatePlayerNameException e) {
      logger.error("Duplicate player name");
      //TODO handle
    } catch (InvalidMaxPlayerException e) {
      logger.error("Invalid max player value requested");
      //TODO handle
    } catch (Exception e) {
      logger.error(e);
      //TODO handle
    }
  }

  /**
   * game getter.
   * 
   * @return The current game instance containing the game state.
   *
   */
  public synchronized Game getGame() {
    return game;
  }

  /**
   * playerConnectors getter.
   *
   * @return The current game instance containing the game state.
   *
   */
  public synchronized List<PlayerConnector> getPlayerConnectors() {
    return playerConnectors;
  }

  /**
   * Add a new player connector from socket.
   * Will accept a built instance of {@link PlayerConnector}
   *
   * @param playerConnector The connector to be added to the current game.
   *
   */
  public synchronized void addPlayerConnector(PlayerConnector playerConnector) {
    playerConnectors.add(playerConnector);
  }
}
