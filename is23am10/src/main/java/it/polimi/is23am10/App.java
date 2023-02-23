package it.polimi.is23am10;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Hello world!.
 *
 */
@Getter
@AllArgsConstructor
public class App  {

  private String appName;

  public static void main(String[] args) {
    System.out.println("Hello World!");
  }

  public String test() {
    return "I am alive";
  }
}
