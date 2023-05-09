package it.polimi.is23am10.utils;

/**
 * Helper class to validate user input strings.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public class CommandSyntaxValidator {

  /**
   * Max number of players.
   */
  private final static int MAX_PLAYERS_LIMIT = 4;

  /**
   * Min number of players.
   */
  private final static int MIN_PLAYERS_LIMIT = 2;

  /**
   * Empty constructor.
   */
  private CommandSyntaxValidator() {
  }

  /**
   * Method validator of game index input.
   *
   * @param s string with game index chosen.
   * @param n index max.
   * @return true if valid.
   */
  public static boolean validateGameIdx(String s, Integer n) {
    // Null parameters
    if (s == null || n == null) {
      return false;
    }
    // Index out of bounds
    try {
      if (Integer.parseInt(s) < 0 || Integer.parseInt(s) >= n) {
        return false;
      }
    } catch (NumberFormatException e) {
      return false;
    }
    return true;
  }

  /**
   * Method validator of max player input.
   *
   * @param s string with game index chosen.
   * @return true if valid.
   */
  public static boolean validateMaxPlayer(String s) {
    // Null parameter
    if (s == null) {
      return false;
    }
    // Index out of bounds
    if (Integer.parseInt(s) < MIN_PLAYERS_LIMIT || Integer.parseInt(s) > MAX_PLAYERS_LIMIT) {
      return false;
    }
    return true;
  }

  /**
   * Method validator of coordinates input.
   *
   * @param s string with game index chosen.
   * @return true if valid.
   */
  public static boolean validateCoord(String s) {
    
    // Null parameter or invalid length
    if (s == null || s.length() != 2) {
      System.out.println("coord length error");
      return false;
    }

    // First and second char are digits 
    if (s.substring(0, 0).chars().allMatch(Character::isDigit)
        || s.substring(1, 1).chars().allMatch(Character::isDigit)) {
      return true; 
    }
    System.out.println("coord not digit");
    return false;
  }

}
