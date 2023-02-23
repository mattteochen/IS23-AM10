package it.polimi.is23am10;

import lombok.Getter;
import lombok.Setter;

/**
 * Hello world!.
 *
 */
@Getter
@Setter
public class App  {

  private String appName;

  public App(String name) {
    appName = name;
  }

  public static void main(String[] args) {
    System.out.println("Hello World!");
  }

  public String test() {
    return "I am alive";
  }
}
