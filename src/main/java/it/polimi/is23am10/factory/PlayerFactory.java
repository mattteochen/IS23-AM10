package it.polimi.is23am10.factory;

import it.polimi.is23am10.factory.exceptions.DuplicatePlayerNameException;
import it.polimi.is23am10.factory.exceptions.NullPlayerNamesException;
import it.polimi.is23am10.game.Game;
import it.polimi.is23am10.game.exceptions.NullAssignedPatternException;
import it.polimi.is23am10.items.bookshelf.Bookshelf;
import it.polimi.is23am10.items.card.PrivateCard;
import it.polimi.is23am10.items.card.exceptions.AlreadyInitiatedPatternException;
import it.polimi.is23am10.player.Player;
import it.polimi.is23am10.player.exceptions.NullPlayerBookshelfException;
import it.polimi.is23am10.player.exceptions.NullPlayerIdException;
import it.polimi.is23am10.player.exceptions.NullPlayerNameException;
import it.polimi.is23am10.player.exceptions.NullPlayerPrivateCardException;
import it.polimi.is23am10.player.exceptions.NullPlayerScoreBlocksException;
import it.polimi.is23am10.player.exceptions.NullPlayerScoreException;
import it.polimi.is23am10.score.Score;
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
@SuppressWarnings({ "checkstyle:nonemptyatclausedescriptioncheck" })
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
  public static boolean isPlayerNameDuplicate(String playerName, List<String> playerNames) {
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
   * @param game The game reference instance where the new player will be added.
   * @throws AlreadyInitiatedPatternException
   * @throws DuplicatePlayerNameException
   * @throws NullPlayerIdException
   * @throws NullPlayerBookshelfException
   * @throws NullPlayerNameException
   * @throws NullPlayerPrivateCardException
   * @throws NullPlayerScoreBlocksException
   * @throws NullPlayerScoreException
   * @throws NullPlayerNamesException
   * @throws NullAssignedPatternException
   * 
   */
  public static Player getNewPlayer(String playerName, Game game)
      throws NullPlayerNameException, NullPlayerIdException, NullPlayerBookshelfException,
      NullPlayerScoreException, NullPlayerPrivateCardException, NullPlayerScoreBlocksException,
      DuplicatePlayerNameException, AlreadyInitiatedPatternException, NullAssignedPatternException {

    // Consumer must handle this {@link DuplicatePlayerNameException}.

    if (playerName == null) {
      throw new NullPlayerNameException(
          "[Class PlayerFactory, method getNewPlayer]: attribute playerName must not be null");
    }

    if (isPlayerNameDuplicate(playerName, game.getPlayerNames())) {
      throw new DuplicatePlayerNameException(
          "[Class PlayerFactory, method getNewPlayer]: The name " + playerName + " already exists");
    }

    Player instance = new Player();
    PrivateCard privateCard = new PrivateCard(game.getAssignedPrivatePatterns());
    game.addAssignedPrivatePattern(privateCard.getPattern());

    instance.setPlayerID(UUID.nameUUIDFromBytes(playerName.getBytes()));
    instance.setPlayerName(playerName);
    instance.setScore(new Score());
    instance.setBookshelf(new Bookshelf());
    instance.setPrivateCard(privateCard);
    instance.setScoreBlocks(new ArrayList<>());

    return instance;
  }
}
