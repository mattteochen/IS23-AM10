package it.polimi.is23am10.utils;

import it.polimi.is23am10.server.model.items.board.Board;
import it.polimi.is23am10.server.model.items.board.exceptions.BoardGridColIndexOutOfBoundsException;
import it.polimi.is23am10.server.model.items.board.exceptions.BoardGridRowIndexOutOfBoundsException;
import it.polimi.is23am10.server.model.items.bookshelf.Bookshelf;
import it.polimi.is23am10.server.model.items.bookshelf.exceptions.BookshelfGridColIndexOutOfBoundsException;
import it.polimi.is23am10.server.model.items.bookshelf.exceptions.BookshelfGridRowIndexOutOfBoundsException;
import it.polimi.is23am10.utils.exceptions.NullIndexValueException;
import it.polimi.is23am10.utils.exceptions.WrongBookShelfPicksException;
import it.polimi.is23am10.utils.exceptions.WrongGameBoardPicksException;
import it.polimi.is23am10.utils.exceptions.WrongMovesNumberException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The game movement validator class definition.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public class MovesValidator {

  /** Private constructor. */
  private MovesValidator() {}

  /**
   * Checks if the given coordinate is in the zero index.
   *
   * @param c the coordinate to be checked
   * @return true if the coordinate is in the zero index, false otherwise
   */
  private static boolean isCoordinateInZeroIndex(Coordinates c) {
    return c.getCol() == 0 || c.getRow() == 0;
  }

  /**
   * Checks if the given coordinate is in the max index.
   *
   * @param c the coordinate to be checked
   * @return true if the coordinate is in the max index, false otherwise
   */
  private static boolean isCoordinateInMaxIndex(Coordinates c) {
    return c.getCol() == Board.BOARD_GRID_COLS - 1 || c.getRow() == Board.BOARD_GRID_ROWS - 1;
  }

  /**
   * Checks if any side of the tile in the given coordinate is empty.
   *
   * @param c the coordinate to be checked
   * @param board the game board to check for empty tiles
   * @return true if there's an empty side, false otherwise
   * @throws BoardGridRowIndexOutOfBoundsException If the board row index is out of bounds. if the
   *     row index is out of bounds
   * @throws BoardGridColIndexOutOfBoundsException If the board column index is out of bounds. if
   *     the column index is out of bounds
   * @throws NullIndexValueException If the index provided is null. if the value at the given index
   *     is null
   */
  private static boolean hasEmptySide(Coordinates c, Board board)
      throws BoardGridRowIndexOutOfBoundsException,
          BoardGridColIndexOutOfBoundsException,
          NullIndexValueException {
    return board.getTileAt(c.getRow() - 1, c.getCol()).isEmpty()
        || board.getTileAt(c.getRow() + 1, c.getCol()).isEmpty()
        || board.getTileAt(c.getRow(), c.getCol() - 1).isEmpty()
        || board.getTileAt(c.getRow(), c.getCol() + 1).isEmpty();
  }

  /**
   * Checks if the values in the given list are adjacent to each other.
   *
   * @param values the list of values to be checked. Must be pre-sorted for this to work.
   * @return true if the values are adjacent, false otherwise
   */
  private static boolean areAdjacent(List<Integer> values) {
    for (Integer i = 0; i < values.size() - 1; i++) {
      if (values.get(i + 1) - values.get(i) != 1) {
        return false;
      }
    }
    return true;
  }

  /**
   * Checks if the lowest row value is greater than or equal to the number of rows in the bookshelf.
   *
   * @param lowestRow the lowest row value
   * @return true if the lowest row value is greater than or equal to the number of rows in the
   *     bookshelf, false otherwise
   */
  private static boolean placeFromBottom(Integer lowestRow) {
    return lowestRow >= Bookshelf.BOOKSHELF_ROWS - 1;
  }

  /**
   * Validates the bookshelf picks by checking if they're valid.
   *
   * @param moves the map of coordinates representing the bookshelf picks
   * @param bookShelf the bookshelf to check the picks against
   * @throws WrongBookShelfPicksException If the game moves are invalid because of bookshelf
   *     placement. if the bookshelf picks are invalid
   * @throws BookshelfGridColIndexOutOfBoundsException If the bookshelf column index is out of
   *     bounds. if the column index is out of bounds
   * @throws BookshelfGridRowIndexOutOfBoundsException If the bookshelf row index is out of bounds.
   *     if the row index is out of bounds
   * @throws NullIndexValueException If the index provided is null. if the value at the given index
   *     is null
   */
  protected static void validateBookShelfPicks(Collection<Coordinates> moves, Bookshelf bookShelf)
      throws WrongBookShelfPicksException,
          BookshelfGridColIndexOutOfBoundsException,
          BookshelfGridRowIndexOutOfBoundsException,
          NullIndexValueException {

    // check different picked columns, they have to be 1
    long columns = moves.stream().map(e -> e.getCol()).distinct().count();
    if (columns > 1) {
      throw new WrongBookShelfPicksException(
          "Invalid bookshelf picks, selected " + columns + " columns. Only one allowed");
    }

    // we can safely get the first column value as they are all the same
    final Integer columnValue = moves.iterator().next().getCol();
    // check the empty rows inside the selected column
    if (bookShelf.getFreeRowsInCol(columnValue) < moves.size()) {
      throw new WrongBookShelfPicksException(
          "Invalid bookshelf picks, selected a column with no sufficient space");
    }

    // check that the selected spots inside the bookshelf are adjacent
    List<Integer> rowValues =
        moves.stream().map(e -> e.getRow()).sorted().collect(Collectors.toList());
    if (!areAdjacent(rowValues)) {
      throw new WrongBookShelfPicksException(
          "Invalid bookshelf picks, selected not sequential rows");
    }

    // check that the lowest tile to put into the bookshelf has a non empty tile
    // under it
    // or if it is the first tile in the column, check that the lowest spot is empty
    Integer lowestRow = rowValues.get(rowValues.size() - 1);
    if (!placeFromBottom(lowestRow)
        && bookShelf.getBookshelfGridAt(lowestRow + 1, columnValue).isEmpty()) {
      throw new WrongBookShelfPicksException("Invalid bookshelf picks, gap between tiles");
    } else if (placeFromBottom(lowestRow)
        && !bookShelf.getBookshelfGridAt(lowestRow, columnValue).isEmpty()) {
      throw new WrongBookShelfPicksException(
          "Invalid bookshelf picks, bookshelf position not empty");
    }
  }

  /**
   * Validates the game board picks for the given moves and board.
   *
   * @param moves the map of coordinates to coordinates representing the moves made on the board
   * @param board the game board to validate the moves on
   * @throws WrongGameBoardPicksException If the game moves are invalid because of board picking. if
   *     the picks made on the board are invalid
   * @throws BoardGridRowIndexOutOfBoundsException If the board row index is out of bounds. if a row
   *     index in the moves is out of bounds of the board
   * @throws BoardGridColIndexOutOfBoundsException If the board column index is out of bounds. if a
   *     column index in the moves is out of bounds of the board
   * @throws NullIndexValueException If the index provided is null. if a null coordinate is present
   *     in the moves
   */
  protected static void validateGameBoardPicks(Collection<Coordinates> moves, Board board)
      throws WrongGameBoardPicksException,
          BoardGridRowIndexOutOfBoundsException,
          BoardGridColIndexOutOfBoundsException,
          NullIndexValueException {
    // perform pick up check
    long rowCount = moves.stream().map(c -> c.getRow()).distinct().count();
    long colCount = moves.stream().map(c -> c.getCol()).distinct().count();

    if (!(rowCount == 1 || colCount == 1)) {
      throw new WrongGameBoardPicksException(
          "Invalid tile picks from the game board, detected "
              + rowCount
              + "row selection and "
              + colCount
              + " column selection");
    }

    List<Integer> rowValues =
        moves.stream().map(e -> e.getRow()).sorted().collect(Collectors.toList());
    List<Integer> colValues =
        moves.stream().map(e -> e.getCol()).sorted().collect(Collectors.toList());

    if (!areAdjacent(colValues) && !areAdjacent(rowValues)) {
      throw new WrongGameBoardPicksException("Invalid pick movement, gap between chosen tiles");
    }

    // perform checks, every tile must have an empty side
    for (Coordinates c : moves) {
      // handle empty tile picks
      if (board.getTileAt(c.getRow(), c.getCol()).isEmpty()) {
        throw new WrongGameBoardPicksException("Invalid pick movement, empty tile");
      }

      // tiles in the first row/col or last row/col have always a side with no tiles
      if (isCoordinateInZeroIndex(c) || isCoordinateInMaxIndex(c)) {
        continue;
      }

      // consumer should handle index out the bounds exception: malformed input
      if (!hasEmptySide(c, board)) {
        throw new WrongGameBoardPicksException(
            "Invalid pick movement, the tile at row "
                + c.getRow()
                + " and col "
                + c.getCol()
                + " has all its sides full");
      }
    }
  }

  /**
   * Validates the game moves for the given moves, bookshelf and board.
   *
   * @param moves the map of coordinates to coordinates representing the moves made on the board
   * @param bookshelf the bookshelf to validate the moves on
   * @param board the game board to validate the moves on
   * @throws WrongMovesNumberException If the game moves are in an illegal number. if the number of
   *     moves made is less than 1 or greater than 3
   * @throws WrongGameBoardPicksException If the game moves are invalid because of board picking. if
   *     the picks made on the board are invalid
   * @throws BoardGridRowIndexOutOfBoundsException If the board row index is out of bounds. if a row
   *     index in the moves is out of bounds of the board
   * @throws BoardGridColIndexOutOfBoundsException If the board column index is out of bounds. if a
   *     column index in the moves is out of bounds of the board
   * @throws NullIndexValueException If the index provided is null. if a null coordinate is present
   *     in the moves
   * @throws BookshelfGridColIndexOutOfBoundsException If the bookshelf column index is out of
   *     bounds. if a column index in the moves is out of bounds of the bookshelf
   * @throws BookshelfGridRowIndexOutOfBoundsException If the bookshelf row index is out of bounds.
   *     if a row index in the moves is out of bounds of the bookshelf
   * @throws WrongBookShelfPicksException If the game moves are invalid because of bookshelf
   *     placement. if the picks made on the bookshelf are invalid
   */
  public static void validateGameMoves(
      Map<Coordinates, Coordinates> moves, Bookshelf bookshelf, Board board)
      throws WrongMovesNumberException,
          BoardGridRowIndexOutOfBoundsException,
          BoardGridColIndexOutOfBoundsException,
          WrongGameBoardPicksException,
          NullIndexValueException,
          BookshelfGridColIndexOutOfBoundsException,
          BookshelfGridRowIndexOutOfBoundsException,
          WrongBookShelfPicksException {
    if (moves.size() < 1 || moves.size() > 3) {
      throw new WrongMovesNumberException();
    }

    validateGameBoardPicks(moves.keySet(), board);
    validateBookShelfPicks(moves.values(), bookshelf);
  }
}
