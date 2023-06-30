package it.polimi.is23am10.server.model.factory;

import it.polimi.is23am10.server.model.factory.exceptions.DuplicatePlayerNameException;
import it.polimi.is23am10.server.model.game.Game;
import it.polimi.is23am10.server.model.game.exceptions.NullAssignedPatternException;
import it.polimi.is23am10.server.model.items.bookshelf.Bookshelf;
import it.polimi.is23am10.server.model.items.card.PrivateCard;
import it.polimi.is23am10.server.model.items.card.exceptions.AlreadyInitiatedPatternException;
import it.polimi.is23am10.server.model.player.Player;
import it.polimi.is23am10.server.model.player.exceptions.NullPlayerBookshelfException;
import it.polimi.is23am10.server.model.player.exceptions.NullPlayerIdException;
import it.polimi.is23am10.server.model.player.exceptions.NullPlayerNameException;
import it.polimi.is23am10.server.model.player.exceptions.NullPlayerPrivateCardException;
import it.polimi.is23am10.server.model.player.exceptions.NullPlayerScoreBlocksException;
import it.polimi.is23am10.server.model.player.exceptions.NullPlayerScoreException;
import it.polimi.is23am10.server.model.score.Score;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The PlayerFactory class definition. This creates a new {@link Player} object.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
@SuppressWarnings({"checkstyle:nonemptyatclausedescriptioncheck"})
public class PlayerFactory {

  /** Private constructor. */
  private PlayerFactory() {}

  /**
   * Check if a player name has already been used across the game instance.
   *
   * @param playerName The chosen player name.
   * @param playerNames Current game instance already available players names.
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
   * Build a new {@link Player} object. This method has the ownership to ensure unique player names
   * inside a game instance.
   *
   * @param playerName The chosen player name.
   * @param game The game reference instance where the new player will be added.
   * @throws AlreadyInitiatedPatternException If assigning a pattern to a card that already has one.
   * @throws DuplicatePlayerNameException If player with that name already exists.
   * @throws NullPlayerIdException If player id is null.
   * @throws NullPlayerBookshelfException If bookshelf is null.
   * @throws NullPlayerNameException If player name is null.
   * @throws NullPlayerPrivateCardException If player's private card object is null.
   * @throws NullPlayerScoreBlocksException If player's scoreblocks list is null.
   * @throws NullPlayerScoreException If player's score object is null.
   * @throws NullAssignedPatternException If the pattern assigned to a card is null.
   */
  public static Player getNewPlayer(String playerName, Game game)
      throws NullPlayerNameException,
          NullPlayerIdException,
          NullPlayerBookshelfException,
          NullPlayerScoreException,
          NullPlayerPrivateCardException,
          NullPlayerScoreBlocksException,
          DuplicatePlayerNameException,
          AlreadyInitiatedPatternException,
          NullAssignedPatternException {

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
    instance.setIsConnected(true);

    return instance;
  }
}
