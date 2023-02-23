package src.test.example;

import static org.junit.Assert.assertTrue;

import javax.swing.JApplet;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;

import src.example.Example;

public class ExampleTest {
  @BeforeEach
  public void prep() {
    System.out.println("Runnig test...");
  }

  @Test
  public void test() {
    //System.out.println("Runnig test...");
    final String expected = "Java";
    Example ex = new Example();
    assertTrue(ex.getMessage().equals(expected));
  }
}
