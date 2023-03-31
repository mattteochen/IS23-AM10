package it.polimi.is23am10.utils;

import it.polimi.is23am10.items.board.Board;
import it.polimi.is23am10.items.board.exceptions.BoardGridColIndexOutOfBoundsException;
import it.polimi.is23am10.items.board.exceptions.BoardGridRowIndexOutOfBoundsException;
import it.polimi.is23am10.utils.exceptions.MovesNotLessThanThreeException;
import it.polimi.is23am10.utils.exceptions.NullIndexValueException;
import it.polimi.is23am10.utils.exceptions.TilesInCornerException;
import it.polimi.is23am10.utils.exceptions.TilesInDiagonalException;
import it.polimi.is23am10.utils.exceptions.TilesWithoutOneFreeSideException;
import java.util.Arrays;
import java.util.Map;

/**
 * The validator for player's move on the game board and on the player's
 * bookshelf.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public class MoveValidator {

  private MoveValidator() {

  }

  /**
   * Input validation for Player's move on board.
   *
   * @param gameBoard The game board.
   * @param selectedCoordinates map of coordinates representing the selected moves.
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

    if (movesAccepted(selectedCoordinates)) {
      if (hasOneFreeSide(gameBoard, selectedCoordinates)) {
        if (areTileNotCorner(gameBoard, selectedCoordinates)) {
          if (tilesNotDiagonal(selectedCoordinates)) {
            return true;
          } else {
            throw new TilesInDiagonalException();
          }
        } else {
          throw new TilesInCornerException();
        }
      } else {
        throw new TilesWithoutOneFreeSideException();
      }
    } else {
      throw new MovesNotLessThanThreeException();
    }
  }

  /**
   * Private method to check if the selected tiles are not disposed in diagonal.
   *
   * @param selectedCoordinates map of coordinates representing the selected moves.
   * @return true if the selected tiles are not in diagonal, false otherwise.
   */
  private static boolean tilesNotDiagonal(Map<Coordinates, Coordinates> selectedCoordinates) {
    int[] rows = new int[selectedCoordinates.size()];
    int[] cols = new int[selectedCoordinates.size()];

    int i = 0;
    for (Coordinates coord : selectedCoordinates.keySet()) {
      rows[i] = coord.getRow();
      cols[i] = coord.getCol();
      i++;
    }
    int minRow = Arrays.stream(rows).min().getAsInt();
    int maxRow = Arrays.stream(rows).max().getAsInt();
    int minCol = Arrays.stream(cols).min().getAsInt();
    int maxCol = Arrays.stream(cols).max().getAsInt();

    return (maxRow - minRow == 0 && maxCol - minCol == 0);
  }

  /**
   * Input validation for Player's move on the bookshelf.
   *
   * @param selectedCoordinates map of coordinates representing the selected moves.
   * @return True if the move is valid.
   */
  public static boolean isValidMoveOnBookshelf(Map<Coordinates, Coordinates> selectedCoordinates) {
    int prevCol = -1;
    for (Map.Entry<Coordinates, Coordinates> entry : selectedCoordinates.entrySet()) {
      Coordinates bsCoord = entry.getValue();
      int col = bsCoord.getCol();
      if (prevCol == -1) {
        prevCol = col;
      } else if (prevCol != col) {
        return false;
      }
    }
    return true;
  }

  /**
   * Private method to check if a tile in a selected set has at least one free
   * side.
   *
   * @param gameBoard The game board
   * @param selectedTiles map of coordinates representing the selected moves.
   * @return true if at least one selected tile has a free side, false otherwise
   * @throws BoardGridRowIndexOutOfBoundsException
   * @throws BoardGridColIndexOutOfBoundsException
   * @throws NullIndexValueException
   */
  private static boolean hasOneFreeSide(Board gameBoard,
      Map<Coordinates, Coordinates> selectedTiles)
      throws BoardGridRowIndexOutOfBoundsException,
      BoardGridColIndexOutOfBoundsException,
      NullIndexValueException {
    for (Coordinates coord : selectedTiles.keySet()) {
      int row = coord.getRow();
      int col = coord.getCol();
      if (gameBoard.getTileAt(row, col + 1).isEmpty()
          || gameBoard.getTileAt(row, col - 1).isEmpty()
          || gameBoard.getTileAt(row + 1, col).isEmpty()
          || gameBoard.getTileAt(row - 1, col).isEmpty()) {
        return true;
      }
    }
    return false;
  }

  /**
   * Determines whether a set of selected coordinates is valid for a move.
   *
   * @param selectedCoordinates map of coordinates representing the selected moves.
   * @return true if the number of selected coordinates is less than or equal to 3, false otherwise
   */
  private static boolean movesAccepted(Map<Coordinates, Coordinates> selectedCoordinates) {
    return selectedCoordinates.size() <= 3;
  }

  /**
   * Private method to check if no selected tiles are in a corner.
   *
   * @param gameBoard The game board
   * @param selectedCoordinates map of coordinates representing the selected moves.
   * @return true if no selected tiles are in corner.
   * @throws BoardGridRowIndexOutOfBoundsException
   * @throws BoardGridColIndexOutOfBoundsException
   * @throws NullIndexValueException
   */
  public static boolean areTileNotCorner(Board gameBoard,
      Map<Coordinates, Coordinates> selectedCoordinates)
      throws BoardGridRowIndexOutOfBoundsException,
      BoardGridColIndexOutOfBoundsException, NullIndexValueException {

    for (Coordinates coord : selectedCoordinates.keySet()) {
      int row = coord.getRow();
      int col = coord.getCol();

      int numAdjacentTiles = 0;
      if (gameBoard.getTileAt(row, col + 1).isEmpty()) {
        numAdjacentTiles++;
      }
      if (gameBoard.getTileAt(row, col - 1).isEmpty()) {
        numAdjacentTiles++;
      }
      if (gameBoard.getTileAt(row + 1, col).isEmpty()) {
        numAdjacentTiles++;
      }
      if (gameBoard.getTileAt(row - 1, col).isEmpty()) {
        numAdjacentTiles++;
      }
      if (numAdjacentTiles >= 2) {
        return false;
      }
    }
    return true;
  }
}