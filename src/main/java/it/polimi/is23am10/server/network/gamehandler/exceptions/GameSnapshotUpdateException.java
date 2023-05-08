package it.polimi.is23am10.server.network.gamehandler.exceptions;

import it.polimi.is23am10.server.model.game.Game;

/**
 * Custom exception to wrap InterruptedException on game snapshot updates.
 * Used in order to catch it preferentially with respect to other IEs.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public class GameSnapshotUpdateException extends Exception {
  public GameSnapshotUpdateException(Game g) {
    super("InterruptedException recorded while sending game " + g.getGameId());
  }
}
