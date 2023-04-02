package it.polimi.is23am10.utils;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import it.polimi.is23am10.items.board.Board;
import it.polimi.is23am10.items.board.exceptions.BoardGridColIndexOutOfBoundsException;
import it.polimi.is23am10.items.board.exceptions.BoardGridRowIndexOutOfBoundsException;
import it.polimi.is23am10.items.board.exceptions.InvalidNumOfPlayersException;
import it.polimi.is23am10.items.board.exceptions.NullNumOfPlayersException;
import it.polimi.is23am10.items.board.exceptions.WrongCharGameBoardStringException;
import it.polimi.is23am10.items.board.exceptions.WrongLengthGameBoardStringException;
import it.polimi.is23am10.items.bookshelf.Bookshelf;
import it.polimi.is23am10.items.bookshelf.exceptions.BookshelfGridColIndexOutOfBoundsException;
import it.polimi.is23am10.items.bookshelf.exceptions.BookshelfGridRowIndexOutOfBoundsException;
import it.polimi.is23am10.items.bookshelf.exceptions.WrongCharBookshelfStringException;
import it.polimi.is23am10.items.bookshelf.exceptions.WrongLengthBookshelfStringException;
import it.polimi.is23am10.utils.exceptions.MovesNotLessThanThreeException;
import it.polimi.is23am10.utils.exceptions.NotEnoughSlotsException;
import it.polimi.is23am10.utils.exceptions.NullIndexValueException;
import it.polimi.is23am10.utils.exceptions.TilesInCornerException;
import it.polimi.is23am10.utils.exceptions.TilesInDiagonalException;
import it.polimi.is23am10.utils.exceptions.TilesWithoutOneFreeSideException;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class to check the valid and illegal moves.
 */
public class MoveValidatorTest {

  private Board gameBoard;
  private Bookshelf bookshelf;

  @BeforeEach
  public void setUp()
      throws InvalidNumOfPlayersException,
      NullNumOfPlayersException, NullPointerException,
      WrongLengthBookshelfStringException,
      WrongCharBookshelfStringException,
      WrongLengthGameBoardStringException, WrongCharGameBoardStringException {
    bookshelf = new Bookshelf(
        "XXXXC"
            + "XXXCC"
            + "XXXPP"
            + "XPPPP"
            + "XCCCC"
            + "TTTTT");

    gameBoard = new Board(4,
        "XXXXXXXXX"
            + "XXXXXXXXX"
            + "XXCCPTCXX"
            + "XXBFBPCXX"
            + "CGTFGTFXX"
            + "XXXCTFXXX"
            + "XXXXPFXXX"
            + "XXXPCFXXX"
            + "XXXXXXXXX");
  }

  @Test
  public void IsValidMoveOnBoard_should_return_true() 
      throws BoardGridRowIndexOutOfBoundsException,
      BoardGridColIndexOutOfBoundsException, NullIndexValueException,
      MovesNotLessThanThreeException, TilesWithoutOneFreeSideException,
      TilesInCornerException, TilesInDiagonalException {
    Map<Coordinates, Coordinates> selectedCoordinates = new HashMap<>();

    final Coordinates boardCoord1 = new Coordinates(2, 3);
    final Coordinates boardCoord2 = new Coordinates(2, 4);
    final Coordinates boardCoord3 = new Coordinates(2, 5);
    final Coordinates bsCoord1 = new Coordinates(4, 0);
    final Coordinates bsCoord2 = new Coordinates(3, 0);
    final Coordinates bsCoord3 = new Coordinates(2, 0);
    selectedCoordinates.put(boardCoord1, bsCoord1);
    selectedCoordinates.put(boardCoord2, bsCoord2);
    selectedCoordinates.put(boardCoord3, bsCoord3);

    assertTrue(MoveValidator.isValidMoveOnBoard(gameBoard, selectedCoordinates));
  }

  @Test
  public void IsValidMoveOnBoard_should_throw_MovesNotLessThanThreeException()
      throws BoardGridRowIndexOutOfBoundsException,
      BoardGridColIndexOutOfBoundsException, NullIndexValueException,
      MovesNotLessThanThreeException, TilesWithoutOneFreeSideException,
      TilesInCornerException, TilesInDiagonalException {
    Map<Coordinates, Coordinates> selectedCoordinates = new HashMap<>();

    final Coordinates boardCoord1 = new Coordinates(1, 1);
    final Coordinates boardCoord2 = new Coordinates(2, 1);
    final Coordinates boardCoord3 = new Coordinates(3, 1);
    final Coordinates boardCoord4 = new Coordinates(4, 1);
    final Coordinates bsCoord1 = new Coordinates(1, 0);
    final Coordinates bsCoord2 = new Coordinates(2, 0);
    final Coordinates bsCoord3 = new Coordinates(3, 0);
    final Coordinates bsCoord4 = new Coordinates(3, 0);
    selectedCoordinates.put(boardCoord1, bsCoord1);
    selectedCoordinates.put(boardCoord2, bsCoord2);
    selectedCoordinates.put(boardCoord3, bsCoord3);
    selectedCoordinates.put(boardCoord4, bsCoord4);

    assertThrows(MovesNotLessThanThreeException.class,
        () -> MoveValidator.isValidMoveOnBoard(gameBoard, selectedCoordinates));
  }

  @Test
  public void IsValidMoveOnBoard_should_throw_TilesWithoutOneFreeSideException() {
    Map<Coordinates, Coordinates> selectedCoordinates = new HashMap<>();

    final Coordinates boardCoord1 = new Coordinates(2, 3);
    final Coordinates boardCoord2 = new Coordinates(3, 3);
    final Coordinates boardCoord3 = new Coordinates(4, 3);
    final Coordinates bsCoord1 = new Coordinates(4, 0);
    final Coordinates bsCoord2 = new Coordinates(3, 0);
    final Coordinates bsCoord3 = new Coordinates(2, 0);
    selectedCoordinates.put(boardCoord1, bsCoord1);
    selectedCoordinates.put(boardCoord2, bsCoord2);
    selectedCoordinates.put(boardCoord3, bsCoord3);

    assertThrows(TilesWithoutOneFreeSideException.class,
        () -> MoveValidator.isValidMoveOnBoard(gameBoard, selectedCoordinates));
  }

  @Test
  public void IsValidMoveOnBoard_should_throw_TilesInDiagonalException() {
    Map<Coordinates, Coordinates> selectedCoordinates = new HashMap<>();

    final Coordinates boardCoord1 = new Coordinates(7, 4);
    final Coordinates boardCoord2 = new Coordinates(6, 5);
    final Coordinates bsCoord1 = new Coordinates(4, 0);
    final Coordinates bsCoord2 = new Coordinates(3, 0);
    selectedCoordinates.put(boardCoord1, bsCoord1);
    selectedCoordinates.put(boardCoord2, bsCoord2);

    assertThrows(TilesInDiagonalException.class,
        () -> MoveValidator.isValidMoveOnBoard(gameBoard, selectedCoordinates));
  }

  @Test
  public void IsValidMoveOnBookshelf_should_return_true()
      throws BookshelfGridColIndexOutOfBoundsException,
      BookshelfGridRowIndexOutOfBoundsException, NotEnoughSlotsException,
      NullIndexValueException {
    Map<Coordinates, Coordinates> selectedCoordinates = new HashMap<>();

    final Coordinates boardCoord1 = new Coordinates(2, 3);
    final Coordinates boardCoord2 = new Coordinates(2, 4);
    final Coordinates boardCoord3 = new Coordinates(2, 5);
    final Coordinates bsCoord1 = new Coordinates(1, 0);
    final Coordinates bsCoord2 = new Coordinates(2, 0);
    final Coordinates bsCoord3 = new Coordinates(3, 0);
    selectedCoordinates.put(boardCoord1, bsCoord1);
    selectedCoordinates.put(boardCoord2, bsCoord2);
    selectedCoordinates.put(boardCoord3, bsCoord3);

    assertTrue(MoveValidator.isValidMoveOnBookshelf(bookshelf, selectedCoordinates.values()));
  }

  @Test
  public void IsValidMoveOnBookshelf_should_throw_NotEnoughSlotsException() {
    Map<Coordinates, Coordinates> selectedCoordinates = new HashMap<>();

    final Coordinates boardCoord1 = new Coordinates(2, 3);
    final Coordinates boardCoord2 = new Coordinates(2, 4);
    final Coordinates boardCoord3 = new Coordinates(2, 5);
    final Coordinates bsCoord1 = new Coordinates(1, 3);
    final Coordinates bsCoord2 = new Coordinates(2, 3);
    final Coordinates bsCoord3 = new Coordinates(3, 3);
    selectedCoordinates.put(boardCoord1, bsCoord1);
    selectedCoordinates.put(boardCoord2, bsCoord2);
    selectedCoordinates.put(boardCoord3, bsCoord3);

    assertThrows(NotEnoughSlotsException.class,
        () -> MoveValidator.isValidMoveOnBookshelf(bookshelf, selectedCoordinates.values()));
  }
}
