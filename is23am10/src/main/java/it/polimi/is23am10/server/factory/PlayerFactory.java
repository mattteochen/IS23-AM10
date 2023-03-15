package it.polimi.is23am10.server.factory;

import it.polimi.is23am10.server.factory.exceptions.DuplicatePlayerNameException;
import it.polimi.is23am10.server.factory.exceptions.NullPlayerNamesException;
import it.polimi.is23am10.server.items.bookshelf.Bookshelf;
import it.polimi.is23am10.server.items.card.PrivateCard;
import it.polimi.is23am10.server.items.card.exceptions.AlreadyInitiatedPatternException;
import it.polimi.is23am10.server.player.Player;
import it.polimi.is23am10.server.player.exceptions.NullPlayerBookshelfException;
import it.polimi.is23am10.server.player.exceptions.NullPlayerIdException;
import it.polimi.is23am10.server.player.exceptions.NullPlayerNameException;
import it.polimi.is23am10.server.player.exceptions.NullPlayerPrivateCardException;
import it.polimi.is23am10.server.player.exceptions.NullPlayerScoreBlocksException;
import it.polimi.is23am10.server.player.exceptions.NullPlayerScoreException;
import it.polimi.is23am10.server.score.Score;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The PlayerFactory class definition.
 * This creates a new {@link Player} object.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public class PlayerFactory {

  /**
   * Private constructor.
   * 
   */
  private PlayerFactory() {
  }

  /**
   * Check if a player name has already been used across the game instance.
   * 
   * @param playerName  The chosen player name.
   * @param playerNames Current game instance already available players names.
   * 
   */
  private static boolean isPlayerNameDuplicate(String playerName, List<String> playerNames) {
    for (String name : playerNames) {
      if (name.equals(playerName)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Build a new {@link Player} object.
   * This method has the ownership to ensure unique player names inside
   * a game instance.
   * 
   * @param playerName  The chosen player name.
   * @param playerNames Current game instance already available players names.
   * @throws AlreadyInitiatedPatternException
   * @throws DuplicatePlayerNameException
   * @throws NullPlayerIdException
   * @throws NullPlayerBookshelfException
   * @throws NullPlayerNameException
   * @throws NullPlayerPrivateCardException
   * @throws NullPlayerScoreBlocksException
   * @throws NullPlayerScoreException
   * @throws NullPlayerNamesException
   * 
   */
  public static Player getNewPlayer(String playerName, List<String> playerNames)
      throws NullPlayerNameException, NullPlayerIdException, NullPlayerBookshelfException, NullPlayerScoreException,
      NullPlayerPrivateCardException, NullPlayerScoreBlocksException, DuplicatePlayerNameException,
      AlreadyInitiatedPatternException, NullPlayerNamesException {

    /**
     * Consumer must handle this {@link DuplicatePlayerNameException}.
     * 
     */

    if (playerName == null) {
      throw new NullPlayerNameException(
          "[Class PlayerFactory, method getNewPlayer]: attribute playerName must not be null");
    }

    if (playerNames == null) {
      throw new NullPlayerNamesException(
          "[Class PlayerFactory, method getNewPlayer]: attribute playerNames must not be null");
    }

    if (isPlayerNameDuplicate(playerName, playerNames)) {
      throw new DuplicatePlayerNameException(
          "[Class PlayerFactory, method getNewPlayer]: The name " + playerName + " already exists");
    }

    Player instance = new Player();

    instance.setPlayerID(UUID.nameUUIDFromBytes(playerName.getBytes()));
    instance.setPlayerName(playerName);
    instance.setScore(new Score());
    instance.setBookshelf(new Bookshelf());
    instance.setPrivateCard(new PrivateCard());
    instance.setScoreBlocks(new ArrayList<>());

    return instance;
  }

}
