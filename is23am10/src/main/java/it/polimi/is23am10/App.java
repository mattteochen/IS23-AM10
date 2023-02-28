package it.polimi.is23am10;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Hello world!.
 *
 */
public class App  {
  public static void main(String[] args) {

    final Logger logger = LogManager.getLogger(App.class);

    logger.info("This is a Log4j2 logged message");
  }

  public String test() {
    return "I am alive";
  }
}
