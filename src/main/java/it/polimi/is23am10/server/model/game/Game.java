package it.polimi.is23am10.server.model.game;

import it.polimi.is23am10.server.controller.exceptions.NullGameHandlerInstance;
import it.polimi.is23am10.server.model.factory.PlayerFactory;
import it.polimi.is23am10.server.model.factory.exceptions.DuplicatePlayerNameException;
import it.polimi.is23am10.server.model.factory.exceptions.NullPlayerNamesException;
import it.polimi.is23am10.server.model.game.exceptions.FullGameException;
import it.polimi.is23am10.server.model.game.exceptions.InvalidMaxPlayerException;
import it.polimi.is23am10.server.model.game.exceptions.InvalidPlayersNumberException;
import it.polimi.is23am10.server.model.game.exceptions.NullAssignedPatternException;
import it.polimi.is23am10.server.model.game.exceptions.NullMaxPlayerException;
import it.polimi.is23am10.server.model.game.exceptions.NullPlayerException;
import it.polimi.is23am10.server.model.game.exceptions.PlayerNotFoundException;
import it.polimi.is23am10.server.model.items.board.Board;
import it.polimi.is23am10.server.model.items.board.exceptions.BoardGridColIndexOutOfBoundsException;
import it.polimi.is23am10.server.model.items.board.exceptions.BoardGridRowIndexOutOfBoundsException;
import it.polimi.is23am10.server.model.items.board.exceptions.InvalidNumOfPlayersException;
import it.polimi.is23am10.server.model.items.board.exceptions.NullNumOfPlayersException;
import it.polimi.is23am10.server.model.items.bookshelf.Bookshelf;
import it.polimi.is23am10.server.model.items.bookshelf.exceptions.BookshelfGridColIndexOutOfBoundsException;
import it.polimi.is23am10.server.model.items.bookshelf.exceptions.BookshelfGridRowIndexOutOfBoundsException;
import it.polimi.is23am10.server.model.items.bookshelf.exceptions.NullTileException;
import it.polimi.is23am10.server.model.items.card.SharedCard;
import it.polimi.is23am10.server.model.items.card.exceptions.AlreadyInitiatedPatternException;
import it.polimi.is23am10.server.model.items.card.exceptions.NegativeMatchedBlockCountException;
import it.polimi.is23am10.server.model.items.card.exceptions.NullMatchedBlockCountException;
import it.polimi.is23am10.server.model.items.card.exceptions.NullScoreBlockListException;
import it.polimi.is23am10.server.model.items.tile.Tile;
import it.polimi.is23am10.server.model.pattern.PrivatePattern;
import it.polimi.is23am10.server.model.pattern.SharedPattern;
import it.polimi.is23am10.server.model.player.Player;
import it.polimi.is23am10.server.model.player.exceptions.NullPlayerBookshelfException;
import it.polimi.is23am10.server.model.player.exceptions.NullPlayerIdException;
import it.polimi.is23am10.server.model.player.exceptions.NullPlayerNameException;
import it.polimi.is23am10.server.model.player.exceptions.NullPlayerPrivateCardException;
import it.polimi.is23am10.server.model.player.exceptions.NullPlayerScoreBlocksException;
import it.polimi.is23am10.server.model.player.exceptions.NullPlayerScoreException;
import it.polimi.is23am10.utils.Coordinates;
import it.polimi.is23am10.utils.MovesValidator;
import it.polimi.is23am10.utils.exceptions.NullIndexValueException;
import it.polimi.is23am10.utils.exceptions.WrongBookShelfPicksException;
import it.polimi.is23am10.utils.exceptions.WrongGameBoardPicksException;
import it.polimi.is23am10.utils.exceptions.WrongMovesNumberException;

import java.io.Serializable;
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
public class Game implements Serializable {

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
   * List of {@link Player} type.
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
   * The instance {@link Board} type.
   * 
   */
  private Board gameBoard;

  /**
   * List of {@link SharedCard} type containing two randomly selected
   * shared cards for this game.
   */
  private List<SharedCard> sharedCards;

  /**
   * All the possible status the game can be in.
   * 
   */
  public enum GameStatus {
    WAITING_FOR_PLAYERS,
    STARTED,
    LAST_ROUND,
    ENDED
  }

  /**
   * The current status of the game.
   * 
   */
  private GameStatus status;

  /**
   * A cache to store already used shared patterns.
   * 
   */
  private transient List<SharedPattern<Predicate<Bookshelf>>> assignedSharedPatterns;

  /**
   * A cache to store already used private patterns.
   * 
   */
  private transient List<PrivatePattern<Function<Bookshelf, Integer>>> assignedPrivatePatterns;

  /**
   * Random object used to pick starting player and player positions.
   */
  private final transient Random random = new Random();

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
   * @throws NullAssignedPatternException If the pattern assigned to a card is null.
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
   * @throws NullAssignedPatternException If the pattern assigned to a card is null.
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
   * @throws NullMaxPlayerException If no value for maximum number of players in the game is provided..
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
   * @throws NullMaxPlayerException If no value for maximum number of players in the game is provided.
   * @throws InvalidMaxPlayerException If value for maximum number of players in the game is not valid.
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
   * @param playerToSet The first player's name.
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
   * @throws NullPlayerNamesException If, while adding multiple players, the list of player names is null.
   * @throws AlreadyInitiatedPatternException If assigning a pattern to a card that already has one.
   * @throws DuplicatePlayerNameException If player with that name already exists.
   * @throws NullPlayerScoreBlocksException If player's scoreblocks list is null.
   * @throws NullPlayerPrivateCardException If player's private card object is null.
   * @throws NullPlayerScoreException If player's score object is null.
   * @throws NullPlayerBookshelfException If bookshelf is null.
   * @throws NullPlayerIdException If player id is null.
   * @throws NullPlayerNameException If player name is null.
   * @throws NullAssignedPatternException If the pattern assigned to a card is null.
   *
   */
  private void addPlayer(Player player) {
    final Integer position = players.isEmpty() ? 0 : random.nextInt(players.size());
    players.add(position, player);
    if (players.size() == maxPlayers) {
      setStatus(GameStatus.STARTED);
      assignPlayers();
    }
  }

  /**
   * Creates and adds a new player to the game. Position is randomly determined,
   * as position in players list is the order in the game.
   *
   * @param playerName The player's name.
   * @return The instance of created player.
   * @throws NullPlayerNamesException If, while adding multiple players, the list of player names is null.
   * @throws AlreadyInitiatedPatternException If assigning a pattern to a card that already has one.
   * @throws DuplicatePlayerNameException If player with that name already exists.
   * @throws NullPlayerScoreBlocksException If player's scoreblocks list is null.
   * @throws NullPlayerPrivateCardException If player's private card object is null.
   * @throws NullPlayerScoreException If player's score object is null.
   * @throws NullPlayerBookshelfException If bookshelf is null.
   * @throws NullPlayerIdException If player id is null.
   * @throws NullPlayerNameException If player name is null.
   * @throws NullAssignedPatternException If the pattern assigned to a card is null.
   * @throws FullGameException If game is full, on player trying to join.
   */
  public Player addPlayer(String playerName)
      throws NullPlayerNamesException, NullPlayerNameException, NullPlayerIdException,
      NullPlayerBookshelfException, NullPlayerScoreException, NullPlayerPrivateCardException,
      NullPlayerScoreBlocksException, DuplicatePlayerNameException, AlreadyInitiatedPatternException,
      FullGameException, NullAssignedPatternException {
    if (getPlayers().size() == getMaxPlayer()) {
      throw new FullGameException(
          playerName + "could not be added, because the game reached its maximum number of players");
    }
    Player playerToAdd = PlayerFactory.getNewPlayer(playerName, this);
    addPlayer(playerToAdd);
    return playerToAdd;
  }

  /**
   * Function that adds multiple players to game.
   *
   * @param players List of players to add.
   * @throws NullPlayerException If player object is null.
   * @throws InvalidPlayersNumberException If number of players to add is invalid.
   * @throws DuplicatePlayerNameException If player with that name already exists.
   */
  public void addPlayers(List<Player> players)
      throws NullPlayerException, InvalidPlayersNumberException, DuplicatePlayerNameException {

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
   * GameBoard setter.
   *
   * @throws InvalidNumOfPlayersException If, while adding multiple players, there is an invalid number of them.
   * @throws NullNumOfPlayersException If the number of players provided when filling the board is null.
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
   * The status setter.
   *
   * @param status The status to set.
   *
   */
  public void setStatus(GameStatus status) {
    this.status = status;
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
   * MaxPlayer getter.
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
   * @return The current status of the game.
   *
   */
  public GameStatus getStatus() {
    return status;
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
   * @throws PlayerNotFoundException If the player with the name provided is not found.
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
   * Method to set the active player (playing this turn).
   *
   * @param player Player to set as active
   */
  public void setActivePlayer(Player player) {
    this.activePlayer = player;
  }

  /**
   * WinnerPlayer setter. To be called by {@link Game#endGame()} only.
   *
   * @param player The winning player to set.
   */
  public void setWinnerPlayer(Player player) {
    this.winnerPlayer = player;
  }

  /**
   * ActivePlayer getter.
   *
   * @return The active player.
   */
  public Player getActivePlayer() {
    return activePlayer;
  }

  /**
   * WinnerPlayer getter.
   *
   * @return The winning player.
   */
  public Player getWinnerPlayer() {
    return this.winnerPlayer;
  }

  /**
   * Method to assign the scoreBlocks. For both shared cards
   * their pattern is checked against activePlayer's BS.
   * If pattern is satisfied AND player didn't get a scoreBlock
   * from that card, the first available SB is given to the player.
   * 
   */
  private void assignScoreBlocks() {
    sharedCards.forEach(c -> {
      if (c.getPattern().getRule().test(activePlayer.getBookshelf()) && !c.getCardWinners().contains(activePlayer)) {
        c.addCardWinner(activePlayer);
        activePlayer.addScoreBlock(c.getScoreBlocks().remove(0));
      }
    });
  }

  /**
   * Method that computes active player's Score, updates the view,
   * checks if game is over and if not picks next player.
   *
   * @throws NullScoreBlockListException If the list of scoreblocks is null.
   * @throws NullPlayerBookshelfException If bookshelf is null.
   * @throws NullIndexValueException If the index provided is null.
   * @throws BookshelfGridRowIndexOutOfBoundsException If the bookshelf row index is out of bounds.
   * @throws BookshelfGridColIndexOutOfBoundsException If the bookshelf column index is out of bounds.
   * @throws NegativeMatchedBlockCountException If the number of matched blocks to set is negative.
   * @throws NullMatchedBlockCountException If the number of matched blocks to set is null.
   * @throws NullPointerException Generic NPE.
   */
  public void nextTurn()
      throws BookshelfGridColIndexOutOfBoundsException, BookshelfGridRowIndexOutOfBoundsException,
      NullIndexValueException, NullPlayerBookshelfException, NullScoreBlockListException, NullPointerException,
      NullMatchedBlockCountException, NegativeMatchedBlockCountException {
    activePlayer.setIsActivePlayer(false);
    assignScoreBlocks();
    activePlayer.updateScore();
    checkEndGame();
    
    /*
     * If there's only one player left, checkEndGame() will eventually 
     * end the game here setting the ended flag to true, otherwise
     * if the game has two or more players still connected we're entering
     * this part of code to decide next player playing 
     */
    if (getStatus() != GameStatus.ENDED) {
      gameBoard.refillIfNeeded();
      int nextPlayerIdx = (getPlayers().indexOf(activePlayer) + 1) % getPlayers().size();
      
      while(!players.get(nextPlayerIdx).getIsConnected()){
        nextPlayerIdx = (nextPlayerIdx+ 1) % getPlayers().size();
      }
      setActivePlayer(players.get(nextPlayerIdx));
      players.get(nextPlayerIdx).setIsActivePlayer(true);
    }
  }

  /**
   * Function that allows the player to take a tile from the board.
   *
   * @param coord The coordinates of the tile.
   * @return The tile of the board the player wants to take.
   * @throws BoardGridColIndexOutOfBoundsException If the board column index is out of bounds.
   * @throws BoardGridRowIndexOutOfBoundsException If the board row index is out of bounds.
   * @throws NullIndexValueException If the index provided is null.
   */
  public Tile takeTileAction(Coordinates coord)
      throws BoardGridRowIndexOutOfBoundsException, BoardGridColIndexOutOfBoundsException,
      NullIndexValueException {
    return gameBoard.takeTileAt(coord.getRow(), coord.getCol());
  }

  /**
   * Function that puts a tile inside the active player's bookshelf.
   *
   * @param t     Tile taken from the board.
   * @param coord Coordinates of the bookshelf.
   * @throws BookshelfGridColIndexOutOfBoundsException If the bookshelf column index is out of bounds.
   * @throws BookshelfGridRowIndexOutOfBoundsException If the bookshelf row index is out of bounds.
   * @throws NullIndexValueException If the index provided is null.
   * @throws NullTileException If the tile is null.
   */
  public void putTileAction(Tile t, Coordinates coord)
      throws BookshelfGridColIndexOutOfBoundsException, BookshelfGridRowIndexOutOfBoundsException,
      NullIndexValueException, NullTileException {
    activePlayer.getBookshelf().setBookshelfGridIndex(coord.getRow(), coord.getCol(), t);
  }

  /**
   * Quick helper function to determine if the player is the last in turn.
   *
   * @param playerToCheck A reference player instance on which to operate the
   *                      check.
   * @return Is playerToCheck the last one in turn
   */
  private boolean isLastPlayer(Player playerToCheck) {
    final Integer idxDiff = players.indexOf(playerToCheck) - players.indexOf(firstPlayer);
    return (idxDiff == -1 || idxDiff == (maxPlayers - 1));
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
      setStatus(GameStatus.LAST_ROUND);
    }
    // Regardless of bookshelf, if last player and lastRound, end game
    if ((getStatus() == GameStatus.LAST_ROUND && isLastPlayer(activePlayer)) 
        || players.stream().filter(p -> p.getIsConnected()).count() <= 1) {
      endGame();
    }
  }

  /**
   * Helper method to be passed to {@link Game#endGame()}
   * in order to determine the winner, according to game rules:
   * In case of score parity, last player in turn wins.
   *
   * @param p1 First player
   * @param p2 Second player
   * @return Player who should win between two
   */
  private Player decideWinner(Player p1, Player p2) {
    final Integer p1Score = p1.getScore().getTotalScore();
    final Integer p2Score = p2.getScore().getTotalScore();

    if (p1Score.equals(p2Score)) {
      // Positions relative to firstPlayer can be negative -> Modular arithmetics
      Integer startingPos1 = players.indexOf(p1) - players.indexOf(firstPlayer);
      startingPos1 = startingPos1 > 0 ? startingPos1 : startingPos1 + maxPlayers;
      Integer startingPos2 = players.indexOf(p2) - players.indexOf(firstPlayer);
      startingPos2 = startingPos2 > 0 ? startingPos2 : startingPos2 + maxPlayers;
      return (startingPos1 > startingPos2 ? p1 : p2);
    } else {
      return (p1Score > p2Score ? p1 : p2);
    }
  }

  /**
   * Method that is called when all players joined
   * the game and the first one should be picked.
   * Can be used in tests to force starting a game before
   * the players threshold is met.
   */
  public void assignPlayers() {
    Player choosenFirstPlayer = players.get(random.nextInt(players.size()));
    activePlayer = choosenFirstPlayer;
    activePlayer.setIsActivePlayer(true);
    firstPlayer = choosenFirstPlayer;
  }

  /**
   * Helper method that sets the game as ended
   * and declares the winner.
   */
  private void endGame() {
    setStatus(GameStatus.ENDED);
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
   * <p>
   * Note that I'm assuming all the params given to the method are valid since the
   * input validation will be implemented client side in the selection of those
   * coordinates.
   * </p>
   *
   * @param selectedCoordinates Map containing the coordinates of selected tiles.
   *                            from board as key and the corresponding
   *                            coordinates of the active player bookshelf as
   *                            value.
   * @throws BoardGridColIndexOutOfBoundsException If the board column index is out of bounds.
   * @throws BoardGridRowIndexOutOfBoundsException If the board row index is out of bounds.
   * @throws NullIndexValueException If the index provided is null.
   * @throws BookshelfGridColIndexOutOfBoundsException If the bookshelf column index is out of bounds.
   * @throws BookshelfGridRowIndexOutOfBoundsException If the bookshelf row index is out of bounds.
   * @throws NullTileException If the tile is null.
   * @throws NullPointerException Generic NPE.
   * @throws NullPlayerBookshelfException If bookshelf is null.
   * @throws NullScoreBlockListException If the list of scoreblocks is null.
   * @throws NegativeMatchedBlockCountException If the number of matched blocks to set is negative.
   * @throws NullMatchedBlockCountException If the number of matched blocks to set is null.
   * @throws WrongBookShelfPicksException If the game moves are invalid because of bookshelf placement.
   * @throws WrongGameBoardPicksException If the game moves are invalid because of board picking.
   * @throws WrongMovesNumberException If the game moves are in an illegal number.
   * @throws NullGameHandlerInstance If the game handler is null.
   */
  public void activePlayerMove(Map<Coordinates, Coordinates> selectedCoordinates)
      throws BoardGridColIndexOutOfBoundsException, BoardGridRowIndexOutOfBoundsException,
      NullIndexValueException, BookshelfGridColIndexOutOfBoundsException,
      BookshelfGridRowIndexOutOfBoundsException, NullTileException, NullPlayerBookshelfException,
      NullScoreBlockListException, NullPointerException, NullMatchedBlockCountException,
      NegativeMatchedBlockCountException,
      WrongMovesNumberException, WrongGameBoardPicksException, WrongBookShelfPicksException, NullGameHandlerInstance {

    MovesValidator.validateGameMoves(selectedCoordinates, activePlayer.getBookshelf(), gameBoard);

    for (Map.Entry<Coordinates, Coordinates> entry : selectedCoordinates.entrySet()) {
      Coordinates boardCoord = entry.getKey();
      Coordinates bsCoord = entry.getValue();
      Tile takenTile = takeTileAction(boardCoord);
      putTileAction(takenTile, bsCoord);
    }
    nextTurn();
  }
}
