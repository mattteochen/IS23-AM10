package it.polimi.is23am10.game;

import it.polimi.is23am10.factory.PlayerFactory;
import it.polimi.is23am10.factory.exceptions.DuplicatePlayerNameException;
import it.polimi.is23am10.factory.exceptions.NullPlayerNamesException;
import it.polimi.is23am10.game.exceptions.InvalidBoardTileSelectionException;
import it.polimi.is23am10.game.exceptions.InvalidMaxPlayerException;
import it.polimi.is23am10.game.exceptions.InvalidPlayersNumberException;
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
import java.util.Random;
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
  public void setFirstPlayer(Player playerToSet) {
    players.stream()
        .filter(player -> player.equals(playerToSet))
        .findFirst()
        .ifPresent(player -> firstPlayer = player);
  }

  /**
   * Add a new player to the game. Position is randomly determined,
   * as position in players list is the order in the game.
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
  private void addPlayer(Player player) {
    //TODO: class level random is colliding with gson
    Random random = new Random();
    final Integer position = players.isEmpty() ? 0 : random.nextInt(players.size());
    players.add(position, player);
  }
  
  /**
   * Creates and adds a new player to the game. Position is randomly determined,
   * as position in players list is the order in the game.
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
   * @return instance of created player
   * @throws NullAssignedPatternException
   */
  public Player addPlayer(String playerName)
      throws NullPlayerNamesException, NullPlayerNameException, NullPlayerIdException, 
      NullPlayerBookshelfException, NullPlayerScoreException, NullPlayerPrivateCardException, 
      NullPlayerScoreBlocksException, DuplicatePlayerNameException, AlreadyInitiatedPatternException, NullAssignedPatternException {
    Player playerToAdd = PlayerFactory.getNewPlayer(playerName, getPlayerNames(), this);
    addPlayer(playerToAdd);
    return playerToAdd;
  }

  /**
   * Function that adds multiple players to game
   * @param players list of players to add
   * @throws NullPlayerException
   * @throws InvalidPlayersNumberException
   * @throws DuplicatePlayerNameException
   */
  public void addPlayers(List<Player> players) 
      throws NullPlayerException, InvalidPlayersNumberException, DuplicatePlayerNameException{
    
    if (players == null) {
      throw new NullPlayerException();
    }
    if ((players.size() + this.players.size()) != maxPlayers) {
      throw new InvalidPlayersNumberException();
    }
    for (Player newPlayer : players) {
      if (PlayerFactory.isPlayerNameDuplicate(newPlayer.getPlayerName(), getPlayerNames())) {
        throw new DuplicatePlayerNameException(
            "[Class Game, method addPlayers]: The name " + newPlayer.getPlayerName() + " already exists");
      }
    }
    this.players.addAll(players);
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
   * Method to set the active player (playing this turn)
   * 
   * @param player player to set as active
   */
  protected void setActivePlayer(Player player) {
    this.activePlayer = player;
  }

  /**
   * winnerPlayer setter. To be called by {@link Game#endGame()} only
   * 
   * @param player
   */
  protected void setWinnerPlayer(Player player) {
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
      NullIndexValueException, NullPlayerBookshelfException, NullScoreBlockListException {
    
    activePlayer.updateScore();
    checkEndGame();
    if(!(getEnded())) {
      gameBoard.refillIfNeeded();
      int nextPlayerIdx = (getPlayers().indexOf(activePlayer) + 1) % getPlayers().size();
      setActivePlayer(players.get(nextPlayerIdx));
    }
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
   * Quick helper function to determine if the player is the last in turn.
   *
   * @param playerToCheck
   * @return is playerToCheck the last one in turn
   */
  private boolean isLastPlayer(Player playerToCheck){
    final Integer idxDiff = players.indexOf(playerToCheck) - players.indexOf(firstPlayer);
    return (idxDiff == -1 || idxDiff == (maxPlayers-1));
  }

  /**
   * Function that checks if there's a player who completed
   * their bookshelf and sets flags accordingly.
   * 
   */
  public void checkEndGame() {
    if (activePlayer.getBookshelf().isBookshelfFull()) {
      activePlayer.getScore().setExtraPoint();
      // When one player completes their bookshelf, last turn starts
      setLastRound();
    }
    // Regardless of bookshelf, if last player and lastRound, end game
    if (lastRound && isLastPlayer(activePlayer)) {
      endGame();
    }
  }

  /**
   * Helper method to be passed to {@link Game#endGame()}
   * in order to determine the winner, according to game rules:
   * In case of score parity, last player in turn wins
   *
   * @param p1 first player
   * @param p2 second player
   * @return player who should win between two
   */
  private Player decideWinner(Player p1, Player p2){
    final Integer p1Score = p1.getScore().getTotalScore();
    final Integer p2Score = p2.getScore().getTotalScore();

    if (p1Score.equals(p2Score)) {
      // Positions relative to firstPlayer can be negative -> Modular arithmetics
      Integer startingPos1 = players.indexOf(p1) - players.indexOf(firstPlayer);
      startingPos1 = startingPos1 > 0 ? startingPos1 : startingPos1 + maxPlayers;
      Integer startingPos2 = players.indexOf(p2) - players.indexOf(firstPlayer); 
      startingPos2 = startingPos2 > 0 ? startingPos2 : startingPos2 + maxPlayers;
      return (startingPos1 > startingPos2 ? p1 : p2);
    } else{
      return (p1Score > p2Score ? p1 : p2);
    }
  }

  /**
   * Helper method that sets the game as ended
   * and declares the winner
   */
  private void endGame() {
    setEnded(true);
    players.stream()
      .reduce(this::decideWinner)
      .ifPresent(this::setWinnerPlayer);
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
      NullScoreBlockListException {
    for (Map.Entry<Coordinates, Coordinates> entry : selectedCoordinates.entrySet()) {
      Coordinates boardCoord = entry.getKey();
      Coordinates bsCoord = entry.getValue();
      Tile takenTile = takeTileAction(boardCoord);
      putTileAction(takenTile, bsCoord);
    }
    nextTurn();
  }

}