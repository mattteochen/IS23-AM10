
package it.polimi.is23am10.client.userinterface.guistate;

/**
 * GUI states catched from user inputs.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public final class GuiState {
  
  /**
   * The player name.
   * 
   */
  private static String playerName;

  /**
   * Sets the player name.
   * 
   * @param pn The player name to set.
   */
  public static void setPlayerName(String pn) {
    System.out.println(pn);
    playerName = pn;
  }

  /**
   * Gets the player name.
   * 
   * @return The player name.
   */
  public static String getPlayerName() {
    return playerName;
  }
}
