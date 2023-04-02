package it.polimi.is23am10.utils;

import it.polimi.is23am10.items.board.Board;
import it.polimi.is23am10.items.board.exceptions.BoardGridColIndexOutOfBoundsException;
import it.polimi.is23am10.items.board.exceptions.BoardGridRowIndexOutOfBoundsException;
import it.polimi.is23am10.items.bookshelf.Bookshelf;
import it.polimi.is23am10.items.bookshelf.exceptions.BookshelfGridColIndexOutOfBoundsException;
import it.polimi.is23am10.items.bookshelf.exceptions.BookshelfGridRowIndexOutOfBoundsException;
import it.polimi.is23am10.items.tile.Tile;
import it.polimi.is23am10.utils.exceptions.MovesNotLessThanThreeException;
import it.polimi.is23am10.utils.exceptions.NotEnoughSlotsException;
import it.polimi.is23am10.utils.exceptions.NullIndexValueException;
import it.polimi.is23am10.utils.exceptions.TilesInCornerException;
import it.polimi.is23am10.utils.exceptions.TilesInDiagonalException;
import it.polimi.is23am10.utils.exceptions.TilesWithoutOneFreeSideException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
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
   * @param gameBoard           The game board.
   * @param selectedCoordinates Map of coordinates representing the selected
   *                            moves.
   * @return True is the move is valid.
   * @throws BoardGridRowIndexOutOfBoundsException
   * @throws BoardGridColIndexOutOfBoundsException
   * @throws NullIndexValueException
   * @throws MovesNotLessThanThreeException
   * @throws TilesWithoutOneFreeSideException
   * @throws TilesInCornerException
   * @throws TilesInDiagonalException
   */
  public static boolean isValidMoveOnBoard(Board gameBoard,
      Map<Coordinates, Coordinates> selectedCoordinates)
      throws BoardGridRowIndexOutOfBoundsException,
      BoardGridColIndexOutOfBoundsException,
      NullIndexValueException, MovesNotLessThanThreeException,
      TilesWithoutOneFreeSideException, TilesInCornerException,
      TilesInDiagonalException {

    if (!movesAccepted(selectedCoordinates)) {
      throw new MovesNotLessThanThreeException();
    }
    if (!tilesNotDiagonal(gameBoard, selectedCoordinates)) {
      throw new TilesInDiagonalException();
    }
    return true;
  }

  /**
   * Private method to check if the selected tiles are not disposed in diagonal.
   *
   * @param selectedCoordinates Map of coordinates representing the selected
   *                            moves.
   * @return True if the selected tiles are not in diagonal, false otherwise.
   * @throws TilesWithoutOneFreeSideException
   * @throws NullIndexValueException
   * @throws BoardGridColIndexOutOfBoundsException
   * @throws BoardGridRowIndexOutOfBoundsException
   */
  private static boolean tilesNotDiagonal(Board gameBoard,
      Map<Coordinates, Coordinates> selectedCoordinates)
      throws TilesWithoutOneFreeSideException, BoardGridRowIndexOutOfBoundsException,
      BoardGridColIndexOutOfBoundsException, NullIndexValueException {
    int[] rows = new int[selectedCoordinates.size()];
    int[] cols = new int[selectedCoordinates.size()];
    int i = 0;
    for (Coordinates coord : selectedCoordinates.keySet()) {
      if (!hasOneFreeSide(gameBoard, coord)) {
        throw new TilesWithoutOneFreeSideException();
      }
      rows[i] = coord.getRow();
      cols[i] = coord.getCol();
      i++;
    }
    return (Arrays.stream(rows).allMatch(s -> s == rows[0])
        || Arrays.stream(cols).allMatch(s -> s == cols[0]));

  }

  /**
   * Input validation for Player's move on the bookshelf.
   *
   * @param selectedCoordinates Map of coordinates representing the selected
   *                            moves.
   * @return True if the move is valid.
   * @throws NotEnoughSlotsException
   * @throws NullIndexValueException
   * @throws BookshelfGridRowIndexOutOfBoundsException
   * @throws BookshelfGridColIndexOutOfBoundsException
   */
  public static boolean isValidMoveOnBookshelf(Bookshelf bookshelf,
      Collection<Coordinates> selectedCoordinates)
      throws NotEnoughSlotsException, BookshelfGridColIndexOutOfBoundsException,
      BookshelfGridRowIndexOutOfBoundsException, NullIndexValueException {
    int colIndex = selectedCoordinates.iterator().next().getCol();
    int requestedSlots = selectedCoordinates.size();
    if (hasEnoughFreeSlotsInColumn(bookshelf, colIndex, requestedSlots)) {
      return (new HashSet<>(selectedCoordinates
          .stream()
          .map(Coordinates::getCol)
          .collect(Collectors.toSet()))
          .size() == 1);
    } else {
      throw new NotEnoughSlotsException();
    }
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
   * Private method to check if a tile in a selected set has at least one free
   * side.
   *
   * @param gameBoard The game board
   * @param coord     Coordinates of the selected tile to be checked.
   * @return True if the selected tile has one free side, false otherwise.
   * @throws BoardGridRowIndexOutOfBoundsException
   * @throws BoardGridColIndexOutOfBoundsException
   * @throws NullIndexValueException
   */
  private static boolean hasOneFreeSide(Board gameBoard,
      Coordinates coord)
      throws BoardGridRowIndexOutOfBoundsException,
      BoardGridColIndexOutOfBoundsException,
      NullIndexValueException {
    int row = coord.getRow();
    int col = coord.getCol();
    int freeSides = 0;
    if (gameBoard.getTileAt(row, col + 1).isEmpty()) {
      freeSides++;
    }
    if (gameBoard.getTileAt(row, col - 1).isEmpty()) {
      freeSides++;
    }
    if (gameBoard.getTileAt(row + 1, col).isEmpty()) {
      freeSides++;
    }
    if (gameBoard.getTileAt(row - 1, col).isEmpty()) {
      freeSides++;
    }
    return freeSides > 0;
  }

  /**
   * Determines whether a set of selected coordinates is valid for a move.
   *
   * @param selectedCoordinates Map of coordinates representing the selected
   *                            moves.
   * @return True if the number of selected coordinates is less than or equal to
   *         3, false otherwise
   */
  private static boolean movesAccepted(Map<Coordinates, Coordinates> selectedCoordinates) {
    return selectedCoordinates.size() > 0 && selectedCoordinates.size() <= 3;
  }
}