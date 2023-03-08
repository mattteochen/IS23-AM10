package it.polimi.is23am10.items.board;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.Arrays;
import java.util.Collections;

import it.polimi.is23am10.items.board.exceptions.BoardGridColIndexOutOfBoundsException;
import it.polimi.is23am10.items.board.exceptions.BoardGridRowIndexOutOfBoundsException;
import it.polimi.is23am10.items.board.exceptions.InvalidNumOfPlayersException;
import it.polimi.is23am10.items.board.exceptions.NullNumOfPlayersException;
import it.polimi.is23am10.items.tile.Tile;
import it.polimi.is23am10.items.tile.Tile.TileType;
import it.polimi.is23am10.utils.IndexValidator;
import it.polimi.is23am10.utils.exceptions.NullIndexValueException;

/**
 * Game's board class definition.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public class Board {

  /**
   * The allowed player values.
   *
   */
  public static final List<Integer> allowedNumOfPlayers = Arrays.asList(2, 3, 4);

  /**
   * The game board max row value.
   * 
   */
  public static final Integer BOARD_GRID_ROWS = 9;

  /**
   * The game board max col value.
   * 
   */
  public static final Integer BOARD_GRID_COLS = 9;

  /**
   * The number of tiles for each {@link TileType}.
   * 
   */
  public static final Integer TILE_TYPE_NUM = 22;

  /**
   * The number of players of the current grid.
   * 
   */
  private Integer numOfPlayers;

  /**
   * A fixed 2d array referencing the physical grid instance.
   * 
   */
  private Tile[][] boardGrid;

  /**
   * A fixed 2d array referencing a weight for each boardGrid cell.
   * Each value represents the minimum number of players needed to get that square
   * filled.
   * 
   * <ul>
   * <li>Values >4 are meant for squares out of the board
   * <li>Values <3 instead will always be filled
   * </ul>
   */
  private static final Integer[][] blackMap = {
      {
          9, 9, 9, 3, 4, 9, 9, 9, 9
      },
      {
          9, 9, 9, 2, 2, 4, 9, 9, 9
      },
      {
          9, 9, 3, 2, 2, 2, 3, 9, 9
      },
      {
          9, 4, 2, 2, 2, 2, 2, 2, 3
      },
      {
          4, 2, 2, 2, 2, 2, 2, 2, 4
      },
      {
          3, 2, 2, 2, 2, 2, 2, 4, 9
      },
      {
          9, 9, 3, 2, 2, 2, 3, 9, 9
      },
      {
          9, 9, 9, 4, 2, 2, 9, 9, 9
      },
      {
          9, 9, 9, 9, 4, 3, 9, 9, 9
      }
  };

  /**
   * A list containing the available tiles.
   * 
   */
  private List<Tile> tileSack;

  /**
   * Constructor.
   * 
   * @param numOfPlayers The current game instance number of players.
   * @throws InvalidNumOfPlayersException.
   * @throws NullNumOfPlayersException.
   */
  public Board(Integer numOfPlayers)
      throws InvalidNumOfPlayersException, NullNumOfPlayersException {
    if (!validNumOfPlayers(numOfPlayers)) {
      throw new InvalidNumOfPlayersException(numOfPlayers);
    }
    /**
     * Save a reference about the current number of players.
     * 
     */
    this.numOfPlayers = numOfPlayers;
    this.boardGrid = new Tile[BOARD_GRID_ROWS][BOARD_GRID_COLS];

    createInitialTileSack();

    fillBoardGrid();
  }

  /**
   * Validate the number of players.
   * 
   * @throws NullNumOfPlayersException.
   */
  private boolean validNumOfPlayers(Integer numOfPlayers) throws NullNumOfPlayersException {
    if (numOfPlayers == null) {
      throw new NullNumOfPlayersException();
    }
    return allowedNumOfPlayers.contains(numOfPlayers);
  }

  /**
   * Extract a tile from the sack.
   * This mutate the sack list.
   * 
   * @return The extracted tile.
   * 
   */
  public Tile getTileFromSack() {
    Tile tile = tileSack.get(tileSack.size() - 1);
    tileSack.remove(tileSack.size() - 1);
    return tile;
  }

  /**
   * Create the board tile sack.
   * The complete sack has 22 tiles for each {@link TileType}.
   * 
   * @throws NullTileTypeException.
   */
  private void createInitialTileSack() {        
    tileSack = Stream.of(TileType.values())
      .filter(t -> !t.equals(TileType.EMPTY))
      .map(t -> Stream.generate(() -> new Tile(t)).limit(22))
      .flatMap(stream -> stream)
      .collect(Collectors.toList()
      );
    
    Collections.shuffle(tileSack);
  }

  /**
   * Fill the board grid based on the current player number.
   * Note that this method works both when first filling the
   * board AND when re-filling it partially mid-game
   * 
   */
  public void fillBoardGrid() {
      for (int i = 0; i < Board.BOARD_GRID_ROWS; i++) {
        for (int j = 0; j < Board.BOARD_GRID_COLS; j++) {
          if (blackMap[i][j] <= numOfPlayers) {
            if (boardGrid[i][j] == null || (boardGrid[i][j].getType().equals(TileType.EMPTY))) {
              boardGrid[i][j] = getTileFromSack();
            }
          } else {
            boardGrid[i][j] = new Tile(TileType.EMPTY);
          }
        }
      }
  }

  /**
   * boardGrid getter.
   * 
   * @return The board grid.
   * 
   */
  public Tile[][] getBoardGrid() {
    return boardGrid;
  }

  /**
   * Retrieve the number of tiles remained inside the sack.
   * 
   * @return The remained tiles inside the sack.
   * 
   */
  public Integer getTileSackSize() {
    return tileSack.size();
  }

  /**
   * View the tile in a specific board position.
   * 
   * @param row The row index.
   * @param col The column index.
   * @return The requested tile.
   * @throws BoardGridRowIndexOutOfBoundsException.
   * @throws BoardGridColIndexOutOfBoundsException.
   * @throws NullIndexValueException.
   * 
   */
  public Tile viewTileAt(Integer row, Integer col)
      throws BoardGridRowIndexOutOfBoundsException, BoardGridColIndexOutOfBoundsException, NullIndexValueException {
    if (!IndexValidator.validColIndex(col, Board.BOARD_GRID_COLS)) {
      throw new BoardGridColIndexOutOfBoundsException(col);
    }
    if (!IndexValidator.validRowIndex(row, Board.BOARD_GRID_ROWS)) {
      throw new BoardGridRowIndexOutOfBoundsException(row);
    }
    return boardGrid[row][col];
  }
  
  public Integer blackMapAt(Integer row, Integer col)
      throws BoardGridRowIndexOutOfBoundsException, BoardGridColIndexOutOfBoundsException, NullIndexValueException {
    if (!IndexValidator.validColIndex(col, Board.BOARD_GRID_COLS)) {
      throw new BoardGridColIndexOutOfBoundsException(col);
    }
    if (!IndexValidator.validRowIndex(row, Board.BOARD_GRID_ROWS)) {
      throw new BoardGridRowIndexOutOfBoundsException(row);
    }
    return blackMap[row][col];
  }

  /**
   * Retrieve the tile in a specific board position.
   * This method removes the returned tile.
   * 
   * @param row The row index.
   * @param col The column index.
   * @return The requested tile.
   * @throws BoardGridRowIndexOutOfBoundsException.
   * @throws BoardGridColIndexOutOfBoundsException.
   * @throws NullIndexValueException.
   * 
   */
  public Tile getTileAt(Integer row, Integer col)
      throws BoardGridRowIndexOutOfBoundsException, BoardGridColIndexOutOfBoundsException, NullIndexValueException {
    if (!IndexValidator.validColIndex(col, Board.BOARD_GRID_COLS)) {
      throw new BoardGridColIndexOutOfBoundsException(col);
    }
    if (!IndexValidator.validRowIndex(row, Board.BOARD_GRID_ROWS)) {
      throw new BoardGridRowIndexOutOfBoundsException(row);
    }
    Tile tile = boardGrid[row][col];
    boardGrid[row][col] = new Tile(TileType.EMPTY);
    return tile;
  }
}
