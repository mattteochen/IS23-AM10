package it.polimi.is23am10.game;

import it.polimi.is23am10.player.Player;
import it.polimi.is23am10.player.Exceptions.NullPlayerIdException;
import it.polimi.is23am10.player.Exceptions.NullPlayerLibraryException;
import it.polimi.is23am10.player.Exceptions.NullPlayerNameException;
import it.polimi.is23am10.player.Exceptions.NullPlayerPrivateCardException;
import it.polimi.is23am10.player.Exceptions.NullPlayerScoreBlocksException;
import it.polimi.is23am10.player.Exceptions.NullPlayerScoreException;
import it.polimi.is23am10.factory.PlayerFactory;
import it.polimi.is23am10.factory.Exceptions.DuplicatePlayerNameException;
import it.polimi.is23am10.factory.Exceptions.NullPlayerNamesException;
import it.polimi.is23am10.game.exceptions.InvalidMaxPlayerException;
import it.polimi.is23am10.game.exceptions.NullMaxPlayerException;
import it.polimi.is23am10.items.board.Board;
import it.polimi.is23am10.items.board.exceptions.InvalidNumOfPlayersException;
import it.polimi.is23am10.items.board.exceptions.NullNumOfPlayersException;
import it.polimi.is23am10.items.card.SharedCard;
import it.polimi.is23am10.items.card.exceptions.AlreadyInitiatedPatternException;

import java.util.UUID;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.List;

/**
 * The Game class definition.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public class Game {

  /**
   * The minimum number of players in a game instance.
   * 
   */
  private static final Integer MIN_PLAYER_NUM = 2;

  /**
   * The maximum number of players in a game instance.
   * 
   */
  private static final Integer MAX_PLAYER_NUM = 4;

  /**
   * A randomly generated {@link UUID} id.
   * 
   */
  private UUID gameId;

  /**
   * The max number of players.
   * Setting a default value to avoid undefined behaviors.
   * 
   */
  private Integer maxPlayers = MIN_PLAYER_NUM;

  /**
   * List of {@Player} type.
   * This instance must never be null.
   * 
   */
  private List<Player> players = new ArrayList<>();

  /**
   * A randomly chosen first player of the game.
   * 
   */
  private Player firstPlayer;

  /**
   * The instance {@Board} type.
   * 
   */
  private Board gameBoard;

  /**
   * List of {@SharedCard} type containing two randomly selected
   * shared cards for this game.
   */
  private List<SharedCard> sharedCards;

  /**
   * A boolean signaling the game is ended.
   * 
   */
  private boolean ended;

  /**
   * Constructor that assigns the only value that is
   * generated, immutable and not set by factory
   */
  public Game() {
    gameId = UUID.randomUUID();
  }

  /**
   * Check if a maxPlayer value is correct.
   * 
   * @param maxPlayers The value to be controlled.
   * @throws NullMaxPlayerException.
   *
   */
  private boolean validMaxPlayers(Integer maxPlayers) throws NullMaxPlayerException {
    if (maxPlayers == null) {
      throw new NullMaxPlayerException();
    }
    return maxPlayers >= MIN_PLAYER_NUM && maxPlayers <= MAX_PLAYER_NUM;
  }

  /**
   * maxPlayers setter.
   * 
   * @param maxPlayers The value to be assigned.
   * @throws NullMaxPlayerException.
   * @throws InvalidMaxPlayerException.
   *
   */
  public void setMaxPlayers(Integer maxPlayers) throws NullMaxPlayerException, InvalidMaxPlayerException {
    if (!validMaxPlayers(maxPlayers)) {
      throw new InvalidMaxPlayerException();
    }
    this.maxPlayers = maxPlayers;
  }

  /**
   * firstPlayer setter.
   * 
   * @param playerName The name chosen from the first player.
   * @throws NullPlayerNameException.
   * @throws NullPlayerIdException.
   * @throws NullPlayerLibraryException.
   * @throws NullPlayerScoreException.
   * @throws NullPlayerPrivateCardException.
   * @throws NullPlayerScoreBlocksException.
   * @throws DuplicatePlayerNameException.
   * @throws AlreadyInitiatedPatternException.
   * @throws NullPlayerNamesException.
   *
   */
  public void setFirstPlayer(String playerName)
      throws NullPlayerNameException, NullPlayerIdException, NullPlayerLibraryException, NullPlayerScoreException,
      NullPlayerPrivateCardException, NullPlayerScoreBlocksException, DuplicatePlayerNameException,
      AlreadyInitiatedPatternException, NullPlayerNamesException {
    firstPlayer = PlayerFactory.getNewPlayer(playerName, getPlayerNames());
    players.add(firstPlayer);
  }

  /**
   * gameBoard setter.
   * 
   * @throws InvalidNumOfPlayersException.
   * @throws NullNumOfPlayersException.
   *
   */
  public void setGameBoard() throws InvalidNumOfPlayersException, NullNumOfPlayersException {
    this.gameBoard = new Board(maxPlayers);
  }

  /**
   * sharedCards setter.
   * 
   * @throws AlreadyInitiatedPatternException.
   *
   */
  public void setSharedCards() throws AlreadyInitiatedPatternException {
    this.sharedCards = new ArrayList<>();
    sharedCards.add(new SharedCard());
    sharedCards.add(new SharedCard());
  }

  /**
   * ended setter.
   * 
   * @param ended A flag referencing if the game is ended.
   *
   */
  public void setEnded(boolean ended) {
    this.ended = ended;
  }

  /**
   * gameId getter.
   * 
   * @return The game id.
   *
   */
  public UUID getGameId() {
    return gameId;
  }

  /**
   * maxPlayer getter.
   * 
   * @return The maximum number of players for the current game instance.
   *
   */
  public Integer getMaxPlayer() {
    return maxPlayers;
  }

  /**
   * players getter.
   * 
   * @return A list containing all the current players.
   *
   */
  public List<Player> getPlayers() {
    return players;
  }

  /**
   * firstPlayer getter.
   * 
   * @return The game first player.
   *         This player has started the game.
   *
   */
  public Player getFirstPlayer() {
    return firstPlayer;
  }

  /**
   * gameBoard getter.
   * 
   * @return The game board grid.
   *
   */
  public Board getGameBoard() {
    return gameBoard;
  }

  /**
   * sharedCards getter.
   * 
   * @return The assigned shared cards to the current game instance.
   *
   */
  public List<SharedCard> getSharedCard() {
    return sharedCards;
  }

  /**
   * ended getter.
   * 
   * @return A boolean values stating if the current game is still running or
   *         not.
   *
   */
  public boolean getEnded() {
    return ended;
  }

  /**
   * Retrieve the current players' names.
   * 
   * @return A {@link List} containing all the current players' names.
   *
   */
  private List<String> getPlayerNames() {
    return players.stream()
        .map(Player::getPlayerName)
        .collect(Collectors.toList());
  }
}
