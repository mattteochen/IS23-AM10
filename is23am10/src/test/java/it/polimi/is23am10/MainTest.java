package it.polimi.is23am10;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import it.polimi.is23am10.Main;

/**
 * Unit test for simple App.
 */
class MainTest {
  /**
  * Rigorous Test :-).
  */
  @Test
  void shouldAnswerWithTrue() {
    assertTrue(true);

    final String expected = "I am alive";
    Main app = new Main();
    assertEquals(app.test(), expected);
  }
}
