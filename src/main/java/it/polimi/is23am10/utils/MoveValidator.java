package it.polimi.is23am10.utils;

import it.polimi.is23am10.items.board.Board;
import it.polimi.is23am10.items.board.exceptions.BoardGridColIndexOutOfBoundsException;
import it.polimi.is23am10.items.board.exceptions.BoardGridRowIndexOutOfBoundsException;
import it.polimi.is23am10.items.bookshelf.Bookshelf;
import it.polimi.is23am10.items.bookshelf.exceptions.BookshelfGridColIndexOutOfBoundsException;
import it.polimi.is23am10.items.bookshelf.exceptions.BookshelfGridRowIndexOutOfBoundsException;
import it.polimi.is23am10.items.tile.Tile;
import it.polimi.is23am10.utils.exceptions.AntiGravityTilesPositioningException;
import it.polimi.is23am10.utils.exceptions.InvalidNumOfMovesException;
import it.polimi.is23am10.utils.exceptions.NotEnoughSlotsException;
import it.polimi.is23am10.utils.exceptions.NullIndexValueException;
import it.polimi.is23am10.utils.exceptions.TilesInDiagonalException;
import it.polimi.is23am10.utils.exceptions.TilesNotAdjacentException;
import it.polimi.is23am10.utils.exceptions.TilesNotInTheSameColException;
import it.polimi.is23am10.utils.exceptions.TilesWithoutOneFreeSideException;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.stream.Collectors;

/**
 * The validator for player's move on the game board and on the player's
 * bookshelf.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public final class MoveValidator {

  private MoveValidator() {

  }

  /**
   * Input validation for Player's move on board.
   *
   * @param gameBoard                The game board.
   * @param selectedBoardCoordinates Collection of coordinates representing the
   *                                 selected moves.
   * @return True is the move is valid.
   * @throws BoardGridRowIndexOutOfBoundsException
   * @throws BoardGridColIndexOutOfBoundsException
   * @throws NullIndexValueException
   * @throws InvalidNumOfMovesException
   * @throws TilesWithoutOneFreeSideException
   * @throws TilesInCornerException
   * @throws TilesInDiagonalException
   * @throws TilesNotAdjacentException
   */
  public static boolean isValidMoveOnBoard(Board gameBoard,
      Collection<Coordinates> selectedBoardCoordinates)
      throws InvalidNumOfMovesException,
      TilesInDiagonalException, TilesNotAdjacentException,
      BoardGridRowIndexOutOfBoundsException, BoardGridColIndexOutOfBoundsException,
      NullIndexValueException, TilesWithoutOneFreeSideException {
    if (!areSelectedTilesAdjacent(selectedBoardCoordinates)) {
      throw new TilesNotAdjacentException();
    }
    if (!checkNumberOfMoves(selectedBoardCoordinates)) {
      throw new InvalidNumOfMovesException();
    }
    if (!tilesNotDiagonal(selectedBoardCoordinates)) {
      throw new TilesInDiagonalException();
    }
    if (!hasOneFreeSide(gameBoard, selectedBoardCoordinates)) {
      throw new TilesWithoutOneFreeSideException();
    }
    return true;
  }

  /**
   * Private method to check if the selected tiles are not disposed in diagonal.
   *
   * @param selectedBoardCoordinates Collection of coordinates representing the
   *                                 selected moves.
   * @return True if the selected tiles are not in diagonal, false otherwise.
   */
  private static boolean tilesNotDiagonal(Collection<Coordinates> selectedBoardCoordinates) {
    return ((new HashSet<>(selectedBoardCoordinates
        .stream()
        .map(Coordinates::getCol)
        .collect(Collectors.toSet()))
        .size() == 1)
        || new HashSet<>(selectedBoardCoordinates
            .stream()
            .map(Coordinates::getRow)
            .collect(Collectors.toSet()))
            .size() == 1);

  }

  /**
   * Input validation for Player's move on the bookshelf.
   *
   * @param selectedBookshelfCoordinates Collection of coordinates representing
   *                                     the selected moves.
   * @return True if the move is valid.
   * @throws NotEnoughSlotsException
   * @throws NullIndexValueException
   * @throws BookshelfGridRowIndexOutOfBoundsException
   * @throws BookshelfGridColIndexOutOfBoundsException
   * @throws TilesNotInTheSameColException
   * @throws TilesNotAdjacentException
   * @throws AntiGravityTilesPositioningException
   */
  public static boolean isValidMoveOnBookshelf(Bookshelf bookshelf,
      Collection<Coordinates> selectedBookshelfCoordinates)
      throws NotEnoughSlotsException, BookshelfGridColIndexOutOfBoundsException,
      BookshelfGridRowIndexOutOfBoundsException, NullIndexValueException,
      TilesNotInTheSameColException, TilesNotAdjacentException,
      AntiGravityTilesPositioningException {
    int colIndex = selectedBookshelfCoordinates.iterator().next().getCol();
    int requestedSlots = selectedBookshelfCoordinates.size();
    if (!selectedTilesInTheSameBookshelfCol(selectedBookshelfCoordinates)) {
      throw new TilesNotInTheSameColException();
    }
    if (!hasEnoughFreeSlotsInColumn(bookshelf, colIndex, requestedSlots)) {
      throw new NotEnoughSlotsException();
    }
    if (!areSelectedTilesAdjacent(selectedBookshelfCoordinates)) {
      throw new TilesNotAdjacentException();
    }
    if (!firstAvailableSlot(bookshelf, selectedBookshelfCoordinates)) {
      throw new AntiGravityTilesPositioningException();
    }
    return true;
  }

  /**
   * Method to ensure the selected tiles in the bookshelf took the first available slot.
   *
   * @param bookshelf                    The private player bookshelf
   * @param selectedBookshelfCoordinates The collection of selected tiles moved in
   *                                     the bookshelf.
   * @return True if the moved tiles in the bookshelf do not overlap and do not
   *         fly.
   * @throws NullIndexValueException
   * @throws BookshelfGridRowIndexOutOfBoundsException
   * @throws BookshelfGridColIndexOutOfBoundsException
   */
  private static boolean firstAvailableSlot(Bookshelf bookshelf,
      Collection<Coordinates> selectedBookshelfCoordinates)
      throws BookshelfGridColIndexOutOfBoundsException,
      BookshelfGridRowIndexOutOfBoundsException, NullIndexValueException {
    int colIndex = selectedBookshelfCoordinates.iterator().next().getCol();
    int maxRowIndex = selectedBookshelfCoordinates.stream()
        .max(Comparator.comparing(Coordinates::getRow))
        .get().getRow();
    for (int i = 0; i < Bookshelf.BOOKSHELF_ROWS; i++) {
      if (bookshelf.getBookshelfGridAt(i, colIndex).isEmpty() && i < maxRowIndex) {
        return false;
      }
    }
    return true;
  }

  /**
   * Method for the validation of selected adjacent tiles on the board and
   * bookshelf.
   *
   * @param selectedCoordinates Collection of selected tiles coordinates.
   * @return True if the selected tiles are adjacent, false otherwise.
   */
  private static boolean areSelectedTilesAdjacent(Collection<Coordinates> selectedCoordinates) {
    int[] cols = new int[selectedCoordinates.size()];
    int[] rows = new int[selectedCoordinates.size()];
    int i = 0;
    for (Coordinates c : selectedCoordinates) {
      cols[i] = c.getCol();
      rows[i] = c.getRow();
      i++;
    }
    for (i = 0; i < selectedCoordinates.size() - 1; i++) {
      if (cols[i + 1] - cols[i] > 1 || rows[i + 1] - rows[i] > 1) {
        return false;
      }
    }
    return true;
  }

  /**
   * Checks if the coordinates on the bookshelf are on the same column.
   *
   * @param selectedBookshelfCoordinates Collection of bookshelf coordinates.
   * @return True if the tiles are entered on the same bs column.
   */
  private static boolean selectedTilesInTheSameBookshelfCol(Collection<Coordinates> selectedBookshelfCoordinates) {
    return (new HashSet<>(selectedBookshelfCoordinates
        .stream()
        .map(Coordinates::getCol)
        .collect(Collectors.toSet()))
        .size() == 1);
  }

  /**
   * Checks if the column in the given bookshelf has enough free slots for the
   * selected tiles.
   *
   * @param playerBookshelf The player's private bookshelf.
   * @param numberOfTiles   The number of tiles to put in the bookshelf.
   * @return True if the column has enough free slots, false otherwise
   * @throws NullIndexValueException
   * @throws BookshelfGridRowIndexOutOfBoundsException
   * @throws BookshelfGridColIndexOutOfBoundsException
   */
  private static boolean hasEnoughFreeSlotsInColumn(Bookshelf bookshelf,
      int colIndex, int requestedSlots)
      throws BookshelfGridColIndexOutOfBoundsException,
      BookshelfGridRowIndexOutOfBoundsException, NullIndexValueException {
    int freeSlots = 0;
    for (int i = 0; i < Bookshelf.BOOKSHELF_ROWS; i++) {
      Tile t = bookshelf.getBookshelfGridAt(i, colIndex);
      if (t.isEmpty()) {
        freeSlots++;
      }
    }
    return (freeSlots >= requestedSlots);
  }

  /**
   * Private method to check if the tiles in the collection had at least one free
   * side.
   *
   * @param gameBoard                The game board
   * @param selectedBoardCoordinates Coordinates of the selected tiles to be
   *                                 checked.
   * @return True if the selected set of tiles had for each tile
   *         at least one free side, false otherwise.
   * @throws BoardGridRowIndexOutOfBoundsException
   * @throws BoardGridColIndexOutOfBoundsException
   * @throws NullIndexValueException
   */
  private static boolean hasOneFreeSide(Board gameBoard,
      Collection<Coordinates> selectedBoardCoordinates)
      throws BoardGridRowIndexOutOfBoundsException,
      BoardGridColIndexOutOfBoundsException,
      NullIndexValueException {
    for (Coordinates c : selectedBoardCoordinates) {
      int row = c.getRow();
      int col = c.getCol();
      if (!(gameBoard.getTileAt(row, col + 1).isEmpty()
          || gameBoard.getTileAt(row, col - 1).isEmpty()
          || gameBoard.getTileAt(row + 1, col).isEmpty()
          || gameBoard.getTileAt(row - 1, col).isEmpty())) {
        return false;
      }
    }
    return true;
  }

  /**
   * Determines whether a set of selected coordinates is valid for a move.
   *
   * @param selectedBoardCoordinates Collection of coordinates representing the
   *                                 selected moves.
   * @return True if the number of selected coordinates is less than or equal to
   *         3, false otherwise
   */
  private static boolean checkNumberOfMoves(Collection<Coordinates> selectedBoardCoordinates) {
    return selectedBoardCoordinates.isEmpty() && selectedBoardCoordinates.size() <= 3;
  }
}