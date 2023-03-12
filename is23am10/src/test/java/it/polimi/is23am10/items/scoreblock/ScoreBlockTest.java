package it.polimi.is23am10.items.scoreblock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import it.polimi.is23am10.items.scoreblock.exceptions.NotValidScoreBlockValueException;

/**
 * Test class to check getters and setters
 * from ScoreBlock class
 */
public class ScoreBlockTest {
    @Test
    public void constructor_should_create_ScoreBlock() throws NotValidScoreBlockValueException{
        Integer value = 4;
        ScoreBlock sb = new ScoreBlock(value);
        assertEquals(value, sb.getScore());
    }
    
    @Test
    public void constructor_should_throw_NotValidScoreBlockValueException() throws NotValidScoreBlockValueException{
        Integer value = 7;
        assertThrows(NotValidScoreBlockValueException.class, () -> new ScoreBlock(value));
    }

    @Test
    public void constructor_should_throw_NotValidScoreBlockValueException_with_null() throws NotValidScoreBlockValueException{
        Integer value = null;
        assertThrows(NotValidScoreBlockValueException.class, () -> new ScoreBlock(value));
    }

}
