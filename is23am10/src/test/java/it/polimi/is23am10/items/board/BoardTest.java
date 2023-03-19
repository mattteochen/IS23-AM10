package it.polimi.is23am10.items.board;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotEquals;

import org.junit.jupiter.api.Test;

import it.polimi.is23am10.items.board.Board;
import it.polimi.is23am10.items.board.exceptions.BoardGridColIndexOutOfBoundsException;
import it.polimi.is23am10.items.board.exceptions.BoardGridRowIndexOutOfBoundsException;
import it.polimi.is23am10.items.board.exceptions.InvalidNumOfPlayersException;
import it.polimi.is23am10.items.board.exceptions.NullNumOfPlayersException;
import it.polimi.is23am10.items.tile.Tile.TileType;
import it.polimi.is23am10.utils.exceptions.NullIndexValueException;

public class BoardTest {
    @Test
    public void constructor_should_create_Board()
            throws InvalidNumOfPlayersException, NullNumOfPlayersException,
            BoardGridRowIndexOutOfBoundsException, BoardGridColIndexOutOfBoundsException, NullIndexValueException {
        // 132 total tiles, 37 removed from sack in constructor
        final Integer EXPECTED_TILES = 132 - 37;
        final Integer NUM_PLAYERS = 3;

        Board b = new Board(NUM_PLAYERS);

        assertNotNull(b);
        assertNotNull(b.getBoardGrid());
        assertNotNull(b.getTileSackSize());
        assertEquals(EXPECTED_TILES, b.getTileSackSize());
        assertNotNull(b.getTileFromSack());

        for (int i = 0; i < Board.BOARD_GRID_ROWS; i++) {
            for (int j = 0; j < Board.BOARD_GRID_COLS; j++) {
                if (b.getBlackMapAt(i, j) <= NUM_PLAYERS) {
                    assertNotEquals(TileType.EMPTY, b.takeTileAt(i, j).getType());
                }
            }
        }
    }

    @Test
    public void constructor_should_throw_InvalidNumOfPlayersException() {
        final Integer NUM_PLAYERS = 7;
        assertThrows(InvalidNumOfPlayersException.class, () -> new Board(NUM_PLAYERS));
    }

    @Test
    public void constructor_should_throw_NullNumOfPlayersException() {
        assertThrows(NullNumOfPlayersException.class, () -> new Board(null));
    }

    @Test
    public void getTileAt_should_return_right_tile() throws InvalidNumOfPlayersException, NullNumOfPlayersException,
            BoardGridRowIndexOutOfBoundsException, BoardGridColIndexOutOfBoundsException, NullIndexValueException {
        final Integer NUM_PLAYERS = 3;
        final Integer row = 3;
        final Integer col = 5;
        Board b = new Board(NUM_PLAYERS);
        assertEquals(b.getTileAt(row, col), b.getBoardGrid()[row][col]);
    }

    @Test
    public void getTileAt_should_throw_BoardGridColIndexOutOfBoundsException()
            throws InvalidNumOfPlayersException, NullNumOfPlayersException, BoardGridRowIndexOutOfBoundsException,
            BoardGridColIndexOutOfBoundsException, NullIndexValueException {
        final Integer NUM_PLAYERS = 3;
        final Integer row = 3;
        final Integer col = 12;
        Board b = new Board(NUM_PLAYERS);
        assertThrows(BoardGridColIndexOutOfBoundsException.class, () -> b.getTileAt(row, col));
    }

    @Test
    public void getTileAt_should_throw_BoardGridRowIndexOutOfBoundsException()
            throws InvalidNumOfPlayersException, NullNumOfPlayersException, BoardGridRowIndexOutOfBoundsException,
            BoardGridColIndexOutOfBoundsException, NullIndexValueException {
        final Integer NUM_PLAYERS = 3;
        final Integer row = 12;
        final Integer col = 3;
        Board b = new Board(NUM_PLAYERS);
        assertThrows(BoardGridRowIndexOutOfBoundsException.class, () -> b.getTileAt(row, col));
    }

    @Test
    public void removeTileAt_should_remove_tile() throws InvalidNumOfPlayersException, NullNumOfPlayersException,
            BoardGridRowIndexOutOfBoundsException, BoardGridColIndexOutOfBoundsException, NullIndexValueException {
        final Integer NUM_PLAYERS = 3;
        final Integer row = 3;
        final Integer col = 5;
        Board b = new Board(NUM_PLAYERS);
        b.removeTileAt(row, col);
        assertEquals(b.getTileAt(row, col).getType(), TileType.EMPTY);
    }

    @Test
    public void refill_should_be_needed() throws InvalidNumOfPlayersException, NullNumOfPlayersException, BoardGridColIndexOutOfBoundsException, BoardGridRowIndexOutOfBoundsException, NullIndexValueException {
        final Integer NUM_PLAYERS = 3;
        Board b = new Board(NUM_PLAYERS);
        //removing tiles in order to have a board that need to be refilled
        for(int row = 0; row < Board.BOARD_GRID_ROWS; row++){
            for(int col = 0; col < Board.BOARD_GRID_COLS; col++){
                if((row+col) % 2 == 0){
                    b.removeTileAt(row, col);
                }
            }
        }
        assertTrue(b.isRefillNeeded());
    }

    @Test
    public void refill_should_not_be_needed() throws InvalidNumOfPlayersException, NullNumOfPlayersException, BoardGridColIndexOutOfBoundsException, BoardGridRowIndexOutOfBoundsException, NullIndexValueException {
        final Integer NUM_PLAYERS = 3;
        Board b = new Board(NUM_PLAYERS);

        assertFalse(b.isRefillNeeded());
    }

    @Test
    public void refillIfNeeded_should_refill_correctly() throws InvalidNumOfPlayersException, NullNumOfPlayersException, BoardGridColIndexOutOfBoundsException, BoardGridRowIndexOutOfBoundsException, NullIndexValueException{
        final Integer NUM_PLAYERS = 3;
        Board b = new Board(NUM_PLAYERS);
        //removing tiles in order to have a board that need to be refilled
        for(int row = 0; row < Board.BOARD_GRID_ROWS; row++){
            for(int col = 0; col < Board.BOARD_GRID_COLS; col++){
                if((row+col) % 2 == 0){
                    b.removeTileAt(row, col);
                }
            }
        }
        
        b.refillIfNeeded();

        for (int i = 0; i < Board.BOARD_GRID_ROWS; i++) {
            for (int j = 0; j < Board.BOARD_GRID_COLS; j++) {
                if (b.getBlackMapAt(i, j) <= NUM_PLAYERS) {
                    assertNotEquals(TileType.EMPTY, b.takeTileAt(i, j).getType());
                }
            }
        }
    }
}
