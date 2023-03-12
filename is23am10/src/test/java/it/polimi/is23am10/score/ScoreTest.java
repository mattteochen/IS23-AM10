package it.polimi.is23am10.score;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

public class ScoreTest {
    @Test
    public void constructor_should_set_zeros(){
        Score s = new Score();
        Integer zero = 0;
        assertEquals(zero, s.getExtraPoint());
        assertEquals(zero, s.getLibraryPoints());
        assertEquals(zero, s.getScoreBlockPoints());
        assertEquals(zero, s.getPrivatePoints());
    }
    
    @Test
    public void setExtraPoint_should_set_extraPoint(){
        Score s = new Score();
        Integer one = 1;
        s.setExtraPoint();
        assertEquals(one, s.getExtraPoint());
    }

    // TODO: following Score setters refactoring add tests accordingly 

}