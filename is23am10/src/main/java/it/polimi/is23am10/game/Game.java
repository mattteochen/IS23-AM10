package it.polimi.is23am10.game;

import it.polimi.is23am10.factory.PlayerFactory;
import it.polimi.is23am10.factory.exceptions.DuplicatePlayerNameException;
import it.polimi.is23am10.factory.exceptions.NullPlayerNamesException;
import it.polimi.is23am10.game.exceptions.InvalidBoardTileSelectionException;
import it.polimi.is23am10.game.exceptions.InvalidMaxPlayerException;
import it.polimi.is23am10.game.exceptions.NullMaxPlayerException;
import it.polimi.is23am10.items.board.Board;
import it.polimi.is23am10.items.board.exceptions.BoardGridColIndexOutOfBoundsException;
import it.polimi.is23am10.items.board.exceptions.BoardGridRowIndexOutOfBoundsException;
import it.polimi.is23am10.items.board.exceptions.InvalidNumOfPlayersException;
import it.polimi.is23am10.items.board.exceptions.NullNumOfPlayersException;
import it.polimi.is23am10.items.bookshelf.exceptions.BookshelfGridColIndexOutOfBoundsException;
import it.polimi.is23am10.items.bookshelf.exceptions.BookshelfGridRowIndexOutOfBoundsException;
import it.polimi.is23am10.items.bookshelf.exceptions.NullTileException;
import it.polimi.is23am10.items.card.SharedCard;
import it.polimi.is23am10.items.card.exceptions.AlreadyInitiatedPatternException;
import it.polimi.is23am10.items.card.exceptions.NullScoreBlockListException;
import it.polimi.is23am10.items.tile.Tile;
import it.polimi.is23am10.player.Player;
import it.polimi.is23am10.player.exceptions.NullPlayerBookshelfException;
import it.polimi.is23am10.player.exceptions.NullPlayerIdException;
import it.polimi.is23am10.player.exceptions.NullPlayerNameException;
import it.polimi.is23am10.player.exceptions.NullPlayerPrivateCardException;
import it.polimi.is23am10.player.exceptions.NullPlayerScoreBlocksException;
import it.polimi.is23am10.player.exceptions.NullPlayerScoreException;
<<<<<<< HEAD
<<<<<<< HEAD
=======
import it.polimi.is23am10.utils.IndexValidator;
=======
import it.polimi.is23am10.utils.Coordinates;
>>>>>>> 20bb7c3 (implemented activePlayerMove and conditions when game ends)
import it.polimi.is23am10.utils.exceptions.NullIndexValueException;

>>>>>>> f4cf0ad (added Coordinates class and implemented nextTurn() Game method)
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
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
   * Winning player.
   */
  private Player winningPlayer;

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
   * A boolean signaling the game is in its last lap of rounds.
   * 
   */
  private boolean lastLap;

  /**
   * Constructor that assigns the only value that is
   * generated, immutable and not set by factory.
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
   * firstPlayer setter.
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
   *
   */
  public void addPlayer(String playerName)
      throws NullPlayerNameException, NullPlayerIdException,
      NullPlayerBookshelfException, NullPlayerScoreException,
      NullPlayerPrivateCardException, NullPlayerScoreBlocksException,
      DuplicatePlayerNameException, AlreadyInitiatedPatternException,
      NullPlayerNamesException {
    players.add(PlayerFactory.getNewPlayer(playerName, getPlayerNames()));
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
   * lastLap setter.
   * 
   * @param lastLap A flag referencing if the game is in its last lap of rounds.
   *
   */
  public void setLastLap(boolean lastLap) {
    this.lastLap = lastLap;
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
  public List<String> getPlayerNames() {
    return players.stream()
        .map(Player::getPlayerName)
        .collect(Collectors.toList());
  }

  /**
   * Method used to retrieve a player from the list
   * given its name.
<<<<<<< HEAD
   *
   * @param playerName
   * @return Player matching provided name.
   */
  private Player getPlayerByName(String playerName) {
    return new Player(); // TODO: Replace with actual logic
=======
   * 
   * @param playerName The player name we are looking for.
   * @return Player matching provided name.
   */
  public Optional<Player> getPlayerByName(String playerName) throws NullPlayerNameException {
    if (playerName == null) {
      throw new NullPlayerNameException("[Class Game, method getPlayerByName]");
    }
    return players.stream()
        .filter(p -> p.getPlayerName().equals(playerName))
        .findFirst();
  }

  /**
   * activePlayer setter
   * 
   * @param player
   * @throws NullPointerException
   */
  private void setActivePlayer(Player player) throws NullPointerException {
    if (player == null) {
      throw new NullPointerException("[Class Game, method setActivePlayer]");
    }
    this.activePlayer = player;
  }

  /**
   * winningPlayer setter
   * 
   * @param player
   * @throws NullPointerException
   */
  private void setWinningPlayer(Player player) throws NullPointerException {
    if (player == null) {
      throw new NullPointerException("[Class Game, method setWinningPlayer]");
    }
    this.winningPlayer = player;
  }

  /**
   * activePlayer getter.
   * 
   * @return active player
   */
  public Player getActivePlayer() {
    return activePlayer;
>>>>>>> f4cf0ad (added Coordinates class and implemented nextTurn() Game method)
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
   */
<<<<<<< HEAD
  public void nextTurn() {
    // TODO: Replace with actual logic
=======
  private void nextTurn()
      throws NullPointerException, BookshelfGridColIndexOutOfBoundsException, BookshelfGridRowIndexOutOfBoundsException,
      NullIndexValueException, NullPlayerBookshelfException, NullScoreBlockListException {
    activePlayer.updateScore();
    gameBoard.refillIfNeeded();
    int idxNextPlayer = players.indexOf(activePlayer) == players.size() - 1 ? 0 : players.indexOf(activePlayer) + 1;
<<<<<<< HEAD
    setActivePlayer(players.get(idxNextPlayer));
>>>>>>> f4cf0ad (added Coordinates class and implemented nextTurn() Game method)
  }

  /**
   * Function that checks if the tile has an adjacent {@link TileType#EMPTY} tile.
   * 
   * @param tRow tile row index on board.
   * @param tCol tile col index on board.
   * @return true if
   * @throws NullIndexValueException
   * @throws BoardGridColIndexOutOfBoundsException
   * @throws BoardGridRowIndexOutOfBoundsException
   * @throws InvalidBoardTileSelectionException
   */
  private boolean hasAdjacentEmptyTile(int tRow, int tCol)
      throws BoardGridRowIndexOutOfBoundsException, BoardGridColIndexOutOfBoundsException, NullIndexValueException {
    // Not doing input validation because it is done previously
    if (gameBoard.getTileAt(tRow + 1, tCol).getType() != TileType.EMPTY &&
        gameBoard.getTileAt(tRow - 1, tCol).getType() != TileType.EMPTY &&
        gameBoard.getTileAt(tRow, tCol + 1).getType() != TileType.EMPTY &&
        gameBoard.getTileAt(tRow, tCol - 1).getType() != TileType.EMPTY) {
      return false;
=======
    if(lastLap && idxNextPlayer == players.indexOf(winningPlayer)){
      setEnded(true);
      return;
>>>>>>> 20bb7c3 (implemented activePlayerMove and conditions when game ends)
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
  private Tile takeTileAction(Coordinates coord) throws BoardGridRowIndexOutOfBoundsException, BoardGridColIndexOutOfBoundsException, NullIndexValueException{
    return gameBoard.takeTileAt(coord.getRow(), coord.getCol());
  }

  /**
   * Function that puts a tile inside the active player's bookshelf.
   * 
   * @param t Tile taken from the board.
   * @param coord Coordinates of the bookshelf.
   * @throws BookshelfGridColIndexOutOfBoundsException
   * @throws BookshelfGridRowIndexOutOfBoundsException
   * @throws NullIndexValueException
   * @throws NullTileException
   */
  public void putTileAction(Tile t, Coordinates coord) throws BookshelfGridColIndexOutOfBoundsException, BookshelfGridRowIndexOutOfBoundsException, NullIndexValueException, NullTileException{
    activePlayer.getBookshelf().setBookshelfGridIndex(coord.getRow(), coord.getCol(), t);
  }

  /**
   * Method that plays the active player's turn.
   * It's important to understand the structure of the Hashmap, which allows to find a correspondence between the coordinates of the taken tile of the board 
   * and the coordinates of the player's bookshelf where he/she/they wants to put the taken tile in.
   * 
   * Note that I'm assuming all the params given to the method are valid since the input validation will be implemented client side in the selection of those coordinates.
   * 
   * @param selectedCoordinates Map containing the coordinates of selected tiles from board as key and the corresponding coordinates of the active player bookshelf as value.
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
   */
  public void activePlayerMove(Map<Coordinates,Coordinates> selectedCoordinates) throws BoardGridColIndexOutOfBoundsException, BoardGridRowIndexOutOfBoundsException, InvalidBoardTileSelectionException, NullIndexValueException, BookshelfGridColIndexOutOfBoundsException, BookshelfGridRowIndexOutOfBoundsException, NullTileException, NullPointerException, NullPlayerBookshelfException, NullScoreBlockListException
    {
    for(Map.Entry<Coordinates,Coordinates> entry: selectedCoordinates.entrySet()){
      Coordinates boardCoord = entry.getKey();
      Coordinates bsCoord = entry.getValue();
      Tile takenTile = takeTileAction(boardCoord);
      putTileAction(takenTile,bsCoord);
    }
    if (this.activePlayer.getBookshelf().isBookshelfFull()) {
      setWinningPlayer(activePlayer);
      if(this.activePlayer.equals(this.firstPlayer)){
        setLastLap(true);
      }
      else{
        setEnded(true);
      }
    }
    nextTurn();
  }

}
