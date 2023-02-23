package com.is23am10;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {
  /**
  * Rigorous Test :-).
  */
  @Test
  public void shouldAnswerWithTrue() {
    assertTrue(true);

    final String expected = "I am alive";
    App app = new App();
    assertEquals(app.test(), expected);
  }
}
