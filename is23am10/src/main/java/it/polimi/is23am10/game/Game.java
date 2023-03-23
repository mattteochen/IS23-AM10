package it.polimi.is23am10.game;

import it.polimi.is23am10.factory.PlayerFactory;
import it.polimi.is23am10.factory.exceptions.DuplicatePlayerNameException;
import it.polimi.is23am10.factory.exceptions.NullPlayerNamesException;
import it.polimi.is23am10.game.exceptions.InvalidBoardTileSelectionException;
import it.polimi.is23am10.game.exceptions.InvalidMaxPlayerException;
import it.polimi.is23am10.game.exceptions.NullAssignedPatternException;
import it.polimi.is23am10.game.exceptions.NullMaxPlayerException;
import it.polimi.is23am10.game.exceptions.NullPlayerException;
import it.polimi.is23am10.game.exceptions.PlayerNotFoundException;
import it.polimi.is23am10.items.board.Board;
import it.polimi.is23am10.items.board.exceptions.BoardGridColIndexOutOfBoundsException;
import it.polimi.is23am10.items.board.exceptions.BoardGridRowIndexOutOfBoundsException;
import it.polimi.is23am10.items.board.exceptions.InvalidNumOfPlayersException;
import it.polimi.is23am10.items.board.exceptions.NullNumOfPlayersException;
import it.polimi.is23am10.items.bookshelf.Bookshelf;
import it.polimi.is23am10.items.bookshelf.exceptions.BookshelfGridColIndexOutOfBoundsException;
import it.polimi.is23am10.items.bookshelf.exceptions.BookshelfGridRowIndexOutOfBoundsException;
import it.polimi.is23am10.items.bookshelf.exceptions.NullTileException;
import it.polimi.is23am10.items.card.SharedCard;
import it.polimi.is23am10.items.card.exceptions.AlreadyInitiatedPatternException;
import it.polimi.is23am10.items.card.exceptions.NullScoreBlockListException;
import it.polimi.is23am10.items.tile.Tile;
import it.polimi.is23am10.pattern.PrivatePattern;
import it.polimi.is23am10.pattern.SharedPattern;
import it.polimi.is23am10.player.Player;
import it.polimi.is23am10.player.exceptions.NullPlayerBookshelfException;
import it.polimi.is23am10.player.exceptions.NullPlayerIdException;
import it.polimi.is23am10.player.exceptions.NullPlayerNameException;
import it.polimi.is23am10.player.exceptions.NullPlayerPrivateCardException;
import it.polimi.is23am10.player.exceptions.NullPlayerScoreBlocksException;
import it.polimi.is23am10.player.exceptions.NullPlayerScoreException;
import it.polimi.is23am10.utils.Coordinates;
import it.polimi.is23am10.utils.exceptions.NullIndexValueException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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
   * Player currently playing.
   */
  private Player activePlayer;

  /**
   * Winner player.
   */
  private Player winnerPlayer;

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
   * A boolean signaling the game is in its last round of rounds.
   * 
   */
  private boolean lastRound;

  /**
   * A cache to store already used shared patterns.
   * 
   */
  private List<SharedPattern<Predicate<Bookshelf>>> assignedSharedPatterns;

  /**
   * A cache to store already used private patterns.
   * 
   */
  private List<PrivatePattern<Function<Bookshelf, Integer>>> assignedPrivatePatterns;

  /**
   * Constructor that assigns the only value that is
   * generated, immutable and not set by factory.
   */
  public Game() {
    gameId = UUID.randomUUID();
    assignedSharedPatterns = new ArrayList<>();
    assignedPrivatePatterns = new ArrayList<>();
  }

  /**
   * Retrieve the already used {@link SharedPattern}s.
   *
   * @return The already assigned {@link SharedPattern}s.
   *
   */
  public List<SharedPattern<Predicate<Bookshelf>>> getAssignedSharedPatterns() {
    return assignedSharedPatterns;
  }

  /**
   * Retrieve the already used {@link PrivatePattern}s.
   *
   * @return The already assigned {@link PrivatePattern}s.
   *
   */
  public List<PrivatePattern<Function<Bookshelf, Integer>>> getAssignedPrivatePatterns() {
    return assignedPrivatePatterns;
  }

  /**
   * Add a new consumed {@link SharedPattern}.
   *
   * @param pattern The {@link SharedPattern} to be added.
   * @throws NullAssignedPatternException
   *
   */
  public void addAssignedSharedPattern(SharedPattern<Predicate<Bookshelf>> pattern)
      throws NullAssignedPatternException {
    if (pattern == null) {
      throw new NullAssignedPatternException("shared");
    }
    assignedSharedPatterns.add(pattern);
  }

  /**
   * Add a new consumed {@link PrivatePattern}.
   *
   * @param pattern The {@link PrivatePattern} to be added.
   * @throws NullAssignedPatternException
   *
   */
  public void addAssignedPrivatePattern(PrivatePattern<Function<Bookshelf, Integer>> pattern)
      throws NullAssignedPatternException {
    if (pattern == null) {
      throw new NullAssignedPatternException("private");
    }
    assignedPrivatePatterns.add(pattern);
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
   * The maxPlayers setter.
   *
   * @param maxPlayers The value to be assigned.
   * @throws NullMaxPlayerException     maxPlayers value is null.
   * @throws InvalidMaxPlayerException.
   *
   */
  public void setMaxPlayers(Integer maxPlayers)
      throws NullMaxPlayerException, InvalidMaxPlayerException {
    if (!validMaxPlayers(maxPlayers)) {
      throw new InvalidMaxPlayerException();
    }
    this.maxPlayers = maxPlayers;
  }

  /**
   * The firstPlayer setter.
   *
   * @param playerName The first player's name.
   *
   */
  public void setFirstPlayer(String playerName) {
    players.stream()
        .filter(player -> player.getPlayerName().equals(playerName))
        .findFirst()
        .ifPresent(player -> {
          this.firstPlayer = player;
        });
  }

  /**
   * Add a new player to the game.
   * 
   * @param playerName The player's name.
   * @throws NullPlayerNamesException         The playerName variable is null.
   * @throws AlreadyInitiatedPatternException
   * @throws DuplicatePlayerNameException
   * @throws NullPlayerScoreBlocksException
   * @throws NullPlayerPrivateCardException
   * @throws NullPlayerScoreException
   * @throws NullPlayerBookshelfException
   * @throws NullPlayerIdException
   * @throws NullPlayerNameException
 * @throws NullAssignedPatternException
   *
   */
  public void addPlayer(String playerName)
      throws NullPlayerNameException, NullPlayerIdException,
      NullPlayerBookshelfException, NullPlayerScoreException,
      NullPlayerPrivateCardException, NullPlayerScoreBlocksException,
      DuplicatePlayerNameException, AlreadyInitiatedPatternException,
      NullPlayerNamesException, NullAssignedPatternException {
    players.add(PlayerFactory.getNewPlayer(playerName, getPlayerNames(), this));
  }

  /**
   * gameBoard setter.
   *
   * @throws InvalidNumOfPlayersException
   * @throws NullNumOfPlayersException
   *
   */
  public void setGameBoard() throws InvalidNumOfPlayersException, NullNumOfPlayersException {
    this.gameBoard = new Board(maxPlayers);
  }

  /**
   * The sharedCards setter.
   *
   */
  public void setSharedCards(List<SharedCard> cards) {
    this.sharedCards = new ArrayList<>();
    sharedCards.add(cards.get(0));
    sharedCards.add(cards.get(1));
  }

  /**
   * The ended setter.
   *
   * @param ended A flag referencing if the game is ended.
   *
   */
  public void setEnded(boolean ended) {
    this.ended = ended;
  }

  /**
   * The lastRound setter.
   * 
   * @param lastRound A flag referencing if the game is in its last round of
   *                  rounds.
   *
   */
  public void setLastRound() {
    this.lastRound = true;
  }

  /**
   * The gameId getter.
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
   * The players getter.
   *
   * @return A list containing all the current players.
   *
   */
  public List<Player> getPlayers() {
    return players;
  }

  /**
   * The firstPlayer getter.
   *
   * @return The game first player.
   *         This player has started the game.
   *
   */
  public Player getFirstPlayer() {
    return firstPlayer;
  }

  /**
   * The gameBoard getter.
   *
   * @return The game board grid.
   *
   */
  public Board getGameBoard() {
    return gameBoard;
  }

  /**
   * The sharedCards getter.
   *
   * @return The assigned shared cards to the current game instance.
   *
   */
  public List<SharedCard> getSharedCard() {
    return sharedCards;
  }

  /**
   * The ended status getter.
   *
   * @return A boolean values stating if the current game is still running or
   *         not.
   *
   */
  public boolean getEnded() {
    return ended;
  }

  /**
   * A last round getter.
   * 
   * @return A boolean values stating if the current game is still running or
   *         not.
   *
   */
  public boolean isLastRound() {
    return this.lastRound;
  }

  /**
   * Retrieve the current players' names.
   *
   * @return A {@link List} containing all the current players' names.
   *
   */
  public List<String> getPlayerNames() {
    return players.stream()
        .map(Player::getPlayerName)
        .collect(Collectors.toList());
  }

  /**
   * Method used to retrieve a player from the list
   * given its name.
   * 
   * @param playerName The player name we are looking for.
   * @return Player matching provided name.
   * @throws PlayerNotFoundException
   */
  public Player getPlayerByName(String playerName) throws NullPlayerNameException, PlayerNotFoundException {
    if (playerName == null) {
      throw new NullPlayerNameException("[Class Game, method getPlayerByName]");
    }
    Optional<Player> player = players.stream()
        .filter(p -> p.getPlayerName().equals(playerName))
        .findFirst();
    if (player.isPresent()) {
      return player.get();
    } else {
      throw new PlayerNotFoundException();
    }
  }

  /**
   * activePlayer setter
   * 
   * @param player
   * @throws NullPointerException
   */
  public void setActivePlayer(Player player) throws NullPointerException {
    if (player == null) {
      throw new NullPointerException("[Class Game, method setActivePlayer]");
    }
    this.activePlayer = player;
  }

  /**
   * winnerPlayer setter
   * 
   * @param player
   * @throws NullPointerException
   */
  public void setWinnerPlayer(Player player) throws NullPlayerException {
    if (player == null) {
      throw new NullPlayerException();
    }
    this.winnerPlayer = player;
  }

  /**
   * activePlayer getter.
   * 
   * @return active player
   */
  public Player getActivePlayer() {
    return activePlayer;
  }

  /**
   * winnerPlayer getter.
   * 
   * @return active player
   */
  public Player getWinnerPlayer() {
    return this.winnerPlayer;
  }

  /**
   * Method that computes active player's Score, updates the view,
   * checks if game is over and if not picks next player
   * 
   * @throws NullScoreBlockListException
   * @throws NullPlayerBookshelfException
   * @throws NullIndexValueException
   * @throws BookshelfGridRowIndexOutOfBoundsException
   * @throws BookshelfGridColIndexOutOfBoundsException
   * @throws NullPointerException
   * @throws NullPlayerException
   */
  public void nextTurn()
      throws BookshelfGridColIndexOutOfBoundsException, BookshelfGridRowIndexOutOfBoundsException,
      NullIndexValueException, NullPlayerBookshelfException, NullScoreBlockListException, NullPlayerException {
    checkWin();
    activePlayer.updateScore();
    gameBoard.refillIfNeeded();
    int idxNextPlayer = (getPlayers().indexOf(activePlayer) + 1) % getPlayers().size();
    if (lastRound && idxNextPlayer == players.indexOf(winnerPlayer)) {
      setEnded(true);
      return;
    }
    setActivePlayer(players.get(idxNextPlayer));
  }

  /**
   * Function that allows the player to take a tile from the board
   * 
   * @param coord coordinates of the tile.
   * @return the tile of the board the player wants to take.
   * @throws BoardGridColIndexOutOfBoundsException
   * @throws BoardGridRowIndexOutOfBoundsException
   * @throws NullIndexValueException
   */
  public Tile takeTileAction(Coordinates coord)
      throws BoardGridRowIndexOutOfBoundsException, BoardGridColIndexOutOfBoundsException, NullIndexValueException {
    return gameBoard.takeTileAt(coord.getRow(), coord.getCol());
  }

  /**
   * Function that puts a tile inside the active player's bookshelf.
   * 
   * @param t     Tile taken from the board.
   * @param coord Coordinates of the bookshelf.
   * @throws BookshelfGridColIndexOutOfBoundsException
   * @throws BookshelfGridRowIndexOutOfBoundsException
   * @throws NullIndexValueException
   * @throws NullTileException
   */
  public void putTileAction(Tile t, Coordinates coord) throws BookshelfGridColIndexOutOfBoundsException,
      BookshelfGridRowIndexOutOfBoundsException, NullIndexValueException, NullTileException {
    activePlayer.getBookshelf().setBookshelfGridIndex(coord.getRow(), coord.getCol(), t);
  }

  /**
   * Function that checks if there's a winner and sets flags of lastRound and
   * ended
   * accordingly.
   * 
   * @throws NullPlayerException
   * 
   */
  public void checkWin() throws NullPlayerException {
    if (this.activePlayer.getBookshelf().isBookshelfFull()) {
      setWinnerPlayer(activePlayer);
      if (this.activePlayer.equals(this.firstPlayer)) {
        setEnded(true);
      } else {
        setLastRound();
      }
    }
  }

  /**
   * Method that plays the active player's turn.
   * It's important to understand the structure of the Hashmap, which allows to
   * find a correspondence between the coordinates of the taken tile of the board
   * and the coordinates of the player's bookshelf where he/she/they wants to put
   * the taken tile in.
   * 
   * Note that I'm assuming all the params given to the method are valid since the
   * input validation will be implemented client side in the selection of those
   * coordinates.
   * 
   * @param selectedCoordinates Map containing the coordinates of selected tiles
   *                            from board as key and the corresponding
   *                            coordinates of the active player bookshelf as
   *                            value.
   * @throws BoardGridColIndexOutOfBoundsException
   * @throws BoardGridRowIndexOutOfBoundsException
   * @throws InvalidBoardTileSelectionException
   * @throws NullIndexValueException
   * @throws BookshelfGridColIndexOutOfBoundsException
   * @throws BookshelfGridRowIndexOutOfBoundsException
   * @throws NullTileException
   * @throws NullPointerException
   * @throws NullPlayerBookshelfException
   * @throws NullScoreBlockListException
   * @throws NullPlayerException
   */
  public void activePlayerMove(Map<Coordinates, Coordinates> selectedCoordinates)
      throws BoardGridColIndexOutOfBoundsException, BoardGridRowIndexOutOfBoundsException,
      NullIndexValueException, BookshelfGridColIndexOutOfBoundsException,
      BookshelfGridRowIndexOutOfBoundsException, NullTileException, NullPlayerBookshelfException,
      NullScoreBlockListException, NullPlayerException {
    for (Map.Entry<Coordinates, Coordinates> entry : selectedCoordinates.entrySet()) {
      Coordinates boardCoord = entry.getKey();
      Coordinates bsCoord = entry.getValue();
      Tile takenTile = takeTileAction(boardCoord);
      putTileAction(takenTile, bsCoord);
    }
    nextTurn();
  }

}