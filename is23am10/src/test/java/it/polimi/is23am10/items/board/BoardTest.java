package it.polimi.is23am10.items.board;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertNotEquals;

import org.junit.jupiter.api.Test;

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
        BoardGridRowIndexOutOfBoundsException, BoardGridColIndexOutOfBoundsException, NullIndexValueException{
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
                if (b.blackMapAt(i,j) <= NUM_PLAYERS) {
                    assertNotEquals(TileType.EMPTY, b.getTileAt(i,j));
                }
            }
        }
    }

    @Test
    public void constructor_should_throw_InvalidNumOfPlayersException(){
        final Integer NUM_PLAYERS = 7;
        assertThrows(InvalidNumOfPlayersException.class, () -> new Board(NUM_PLAYERS));
    }

    @Test
    public void constructor_should_throw_NullNumOfPlayersException(){
        assertThrows(InvalidNumOfPlayersException.class, () -> new Board(null));
    }
    
}
