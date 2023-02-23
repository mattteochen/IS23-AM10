package it.polimi.is23am10;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Unit test for simple App.
 */
class AppTest {
  /**
  * Rigorous Test :-).
  */
  @Test
  void shouldAnswerWithTrue() {
    assertTrue(true);

    final String expected = "I am alive";
    App app = new App("New App");
    assertEquals(expected, app.test());

    assertEquals("New App", app.getAppName());
  }
}
