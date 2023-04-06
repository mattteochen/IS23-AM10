package it.polimi.is23am10.utils;

import static org.junit.Assert.assertThrows;

import it.polimi.is23am10.items.board.Board;
import it.polimi.is23am10.items.board.exceptions.BoardGridColIndexOutOfBoundsException;
import it.polimi.is23am10.items.board.exceptions.BoardGridRowIndexOutOfBoundsException;
import it.polimi.is23am10.items.board.exceptions.InvalidNumOfPlayersException;
import it.polimi.is23am10.items.board.exceptions.NullNumOfPlayersException;
import it.polimi.is23am10.items.bookshelf.Bookshelf;
import it.polimi.is23am10.items.bookshelf.exceptions.BookshelfGridColIndexOutOfBoundsException;
import it.polimi.is23am10.items.bookshelf.exceptions.BookshelfGridRowIndexOutOfBoundsException;
import it.polimi.is23am10.items.bookshelf.exceptions.NullTileException;
import it.polimi.is23am10.items.tile.Tile;
import it.polimi.is23am10.items.tile.Tile.TileType;
import it.polimi.is23am10.utils.exceptions.NullIndexValueException;
import it.polimi.is23am10.utils.exceptions.WrongBookShelfPicksException;
import it.polimi.is23am10.utils.exceptions.WrongGameBoardPicksException;
import it.polimi.is23am10.utils.exceptions.WrongMovesNumberException;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MovesValidatorTest {

  Tile tile;
  Tile emptyTile;
  Board board;
  Bookshelf bookBookshelf;
  Bookshelf notEmptyBookShelf;

  @BeforeEach
  void setup()
      throws InvalidNumOfPlayersException, NullNumOfPlayersException,
      BookshelfGridColIndexOutOfBoundsException,
      BookshelfGridRowIndexOutOfBoundsException, NullIndexValueException, NullTileException {
    tile = new Tile(TileType.BOOK);
    emptyTile = new Tile(TileType.EMPTY);
    board = new Board(4);
    bookBookshelf = new Bookshelf();
    notEmptyBookShelf = new Bookshelf();
    notEmptyBookShelf.setBookshelfGridIndex(5, 0, tile);
    notEmptyBookShelf.setBookshelfGridIndex(4, 0, tile);
    notEmptyBookShelf.setBookshelfGridIndex(3, 0, tile);
    notEmptyBookShelf.setBookshelfGridIndex(2, 0, tile);
    notEmptyBookShelf.setBookshelfGridIndex(1, 0, tile);
  }

  @Test
  void validator_should_validate_correct_moves()
      throws BoardGridRowIndexOutOfBoundsException, BoardGridColIndexOutOfBoundsException,
      BookshelfGridColIndexOutOfBoundsException, BookshelfGridRowIndexOutOfBoundsException,
      WrongMovesNumberException,
      WrongGameBoardPicksException, NullIndexValueException,
      WrongBookShelfPicksException, NullTileException {
    Map<Coordinates, Coordinates> moves = new HashMap<>();
    moves.put(new Coordinates(4, 0), new Coordinates(5, 0));
    moves.put(new Coordinates(5, 0), new Coordinates(4, 0));

    MovesValidator.validateGameMoves(moves, bookBookshelf, board);

    bookBookshelf.setBookshelfGridIndex(5, 0, new Tile(TileType.CAT));
    moves.put(new Coordinates(4, 0), new Coordinates(3, 0));
    moves.put(new Coordinates(5, 0), new Coordinates(4, 0));

    MovesValidator.validateGameMoves(moves, bookBookshelf, board);
  }

  @Test
  void validator_should_throw_WrongMovesNumberException()
      throws BoardGridRowIndexOutOfBoundsException, BoardGridColIndexOutOfBoundsException,
      BookshelfGridColIndexOutOfBoundsException, BookshelfGridRowIndexOutOfBoundsException,
      WrongMovesNumberException,
      WrongGameBoardPicksException, NullIndexValueException,
      WrongBookShelfPicksException, NullTileException {
    Map<Coordinates, Coordinates> moves = new HashMap<>();
    moves.put(new Coordinates(3, 0), new Coordinates(4, 0));
    moves.put(new Coordinates(4, 0), new Coordinates(5, 0));
    moves.put(new Coordinates(5, 0), new Coordinates(3, 0));
    moves.put(new Coordinates(6, 0), new Coordinates(2, 0));

    assertThrows(WrongMovesNumberException.class,
        () -> MovesValidator.validateGameMoves(moves, bookBookshelf, board));
  }

  @Test
  void validator_should_throws_WrongGameBoardPicksException()
      throws BoardGridRowIndexOutOfBoundsException, BoardGridColIndexOutOfBoundsException,
      BookshelfGridColIndexOutOfBoundsException, BookshelfGridRowIndexOutOfBoundsException,
      WrongMovesNumberException,
      WrongGameBoardPicksException, NullIndexValueException,
      WrongBookShelfPicksException, NullTileException {
    Map<Coordinates, Coordinates> moves = new HashMap<>();
    // diagonal choices
    moves.put(new Coordinates(4, 0), new Coordinates(5, 0));
    moves.put(new Coordinates(5, 1), new Coordinates(4, 0));

    assertThrows(WrongGameBoardPicksException.class,
        () -> MovesValidator.validateGameMoves(moves, bookBookshelf, board));

    // gap between choices
    moves.clear();
    moves.put(new Coordinates(4, 0), new Coordinates(5, 0));
    moves.put(new Coordinates(4, 2), new Coordinates(4, 0));

    assertThrows(WrongGameBoardPicksException.class,
        () -> MovesValidator.validateGameMoves(moves, bookBookshelf, board));

    // empty tile
    moves.clear();
    moves.put(new Coordinates(3, 0), new Coordinates(5, 0));
    moves.put(new Coordinates(3, 1), new Coordinates(4, 0));
    assertThrows(WrongGameBoardPicksException.class,
        () -> MovesValidator.validateGameMoves(moves, bookBookshelf, board));

    // not empty side
    moves.clear();
    moves.put(new Coordinates(4, 3), new Coordinates(5, 0));
    moves.put(new Coordinates(4, 2), new Coordinates(4, 0));

    assertThrows(WrongGameBoardPicksException.class,
        () -> MovesValidator.validateGameMoves(moves, bookBookshelf, board));
  }

  @Test
  void validator_should_throws_WrongBookShelfPicksException()
      throws BoardGridRowIndexOutOfBoundsException, BoardGridColIndexOutOfBoundsException,
      BookshelfGridColIndexOutOfBoundsException, BookshelfGridRowIndexOutOfBoundsException,
      WrongMovesNumberException,
      WrongGameBoardPicksException, NullIndexValueException,
      WrongBookShelfPicksException, NullTileException {
    Map<Coordinates, Coordinates> moves = new HashMap<>();
    // diagonal choices
    moves.put(new Coordinates(4, 0), new Coordinates(5, 0));
    moves.put(new Coordinates(5, 0), new Coordinates(4, 1));

    assertThrows(WrongBookShelfPicksException.class,
        () -> MovesValidator.validateGameMoves(moves, bookBookshelf, board));

    // no sufficient space
    moves.clear();
    moves.put(new Coordinates(4, 0), new Coordinates(5, 0));
    moves.put(new Coordinates(5, 0), new Coordinates(4, 0));

    assertThrows(WrongBookShelfPicksException.class,
        () -> MovesValidator.validateGameMoves(moves, notEmptyBookShelf, board));

    // no adjacent selection
    moves.clear();
    moves.put(new Coordinates(4, 0), new Coordinates(5, 0));
    moves.put(new Coordinates(5, 0), new Coordinates(3, 0));
    assertThrows(WrongBookShelfPicksException.class,
        () -> MovesValidator.validateGameMoves(moves, bookBookshelf, board));

    // put on top of a non empty tile
    moves.clear();
    notEmptyBookShelf.setBookshelfGridIndex(5, 0, emptyTile);
    notEmptyBookShelf.setBookshelfGridIndex(4, 0, emptyTile);
    notEmptyBookShelf.setBookshelfGridIndex(3, 0, emptyTile);
    notEmptyBookShelf.setBookshelfGridIndex(2, 0, emptyTile);
    notEmptyBookShelf.setBookshelfGridIndex(1, 0, emptyTile);
    moves.put(new Coordinates(4, 0), new Coordinates(3, 0));
    moves.put(new Coordinates(5, 0), new Coordinates(4, 0));

    assertThrows(WrongBookShelfPicksException.class,
        () -> MovesValidator.validateGameMoves(moves, notEmptyBookShelf, board));

    // put in a not empty tile
    moves.clear();
    notEmptyBookShelf.setBookshelfGridIndex(5, 0, tile);
    notEmptyBookShelf.setBookshelfGridIndex(4, 0, emptyTile);
    notEmptyBookShelf.setBookshelfGridIndex(3, 0, emptyTile);
    notEmptyBookShelf.setBookshelfGridIndex(2, 0, emptyTile);
    notEmptyBookShelf.setBookshelfGridIndex(1, 0, emptyTile);
    moves.put(new Coordinates(4, 0), new Coordinates(5, 0));
    moves.put(new Coordinates(5, 0), new Coordinates(4, 0));

    assertThrows(WrongBookShelfPicksException.class,
        () -> MovesValidator.validateGameMoves(moves, notEmptyBookShelf, board));
  }
}
