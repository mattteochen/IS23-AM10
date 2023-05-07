package it.polimi.is23am10.server.controller;

import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import it.polimi.is23am10.server.network.playerconnector.AbstractPlayerConnector;

/**
 * The server client activity checker class definition.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
@SuppressWarnings({ "checkstyle:nonemptyatclausedescriptioncheck" })
public final class ClientActivityChecker implements Runnable {

  /**
   * The logger, an instance of {@link Logger}.
   *
   */
  protected Logger logger = LogManager.getLogger(ClientActivityChecker.class);
  
  /**
   * The connected players.
   *
   */
  protected Set<AbstractPlayerConnector> pcs;

  /**
   * The max inactivity time: 2 minutes.
   *
   */
  protected final long MAX_SNOOZE_TIMEOUT_MS = 1000 * 60 * 2;

  /**
   * {@inheritDoc}
   *
   */
  @Override
  public void run() {
    while(true) {
      checkAllPlayers();
    } 
  }

  /**
   * Perform activity check on all connected players.
   *
   */
  protected void checkAllPlayers() {
    try {
      pcs = ServerControllerState.getPlayersPool();
      synchronized(pcs) {
        for (AbstractPlayerConnector pc : pcs) {
          if (pc.getPlayer().getIsConnected() && expired(pc.getLastSnoozeMs(), System.currentTimeMillis())) {
            logger.info("Detected inactivity for {}, disconnecting the player", pc.getPlayer().getPlayerName());
            pc.getPlayer().setIsConnected(false);
          }
        }
      }
    } catch(NullPointerException e) {
      logger.error("Server status corrupted {}", e);
    }
  }

  /**
   * Check if a player activity is expired or not.
   *
   * @param lhs The left hand side of the operation.
   * @param rhs The right hand side of the operation.
   * @return The requested check result.
   *
   */
  protected boolean expired(long lhs, long rhs) {
    return rhs - lhs > MAX_SNOOZE_TIMEOUT_MS;
  }
}
