package it.polimi.is23am10.game;

import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.Test;

public class GameTest {
    @Test
    public void constructor_should_create_Game(){
        Game g = new Game();
        assertNotNull(g);
        assertNotNull(g.getGameId());
    }
    
}
