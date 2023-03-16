package it.polimi.is23am10.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import it.polimi.is23am10.server.App;

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
    App app = new App();
    assertEquals(app.test(), expected);
  }
}
