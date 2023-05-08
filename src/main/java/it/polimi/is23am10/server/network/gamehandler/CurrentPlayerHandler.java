
package it.polimi.is23am10.server.network.gamehandler;

import it.polimi.is23am10.server.model.player.Player;

/**
 * The current player handler class definition.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public class CurrentPlayerHandler {

  /**
   * The active player instance.
   *
   */
  private Player player;
  
  /**
   * The active player turn starting time in ms.
   *
   */
  private long startPlayingTimeMs;

  /**
   * The notified flag if a first inactivity has been alreasy notified.
   *
   */
  private boolean notified;

  /**
   * The player setter.
   *
   * @param p the player to be assigned.
   *
   */
  public void setPlayer(Player p) {
    player = p;
  }

  /**
   * The start playing time setter.
   *
   * @param time the player turn starting time in ms.
   *
   */
  public void setStartPlayingTimeMs(long time) {
    startPlayingTimeMs = time;
  }

  /**
   * The notified setter.
   *
   * @param f the notified flag to be set.
   *
   */
  public void setNotified(boolean f) {
    notified = f;
  }

  /**
   * The player getter.
   *
   * @return the assigned player.
   *
   */
  public Player getPlayer() {
    return player;
  }

  /** The start playing time getter.
   *
   * @return the player turn starting time in ms.
   *
   */
  public long getStartPlayingTimeMs() {
    return startPlayingTimeMs;
  }

  /**
   * The notified getter.
   *
   */
  public boolean getNotified() {
    return notified;
  }
}

