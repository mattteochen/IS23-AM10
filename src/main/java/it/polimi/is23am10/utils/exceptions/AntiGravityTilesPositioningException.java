package it.polimi.is23am10.utils.exceptions;

/**
 * Custom exception for handling overlapping or flying positioned tiles in
 * bookshelf.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public class AntiGravityTilesPositioningException extends Exception {
  public AntiGravityTilesPositioningException() {
    super("Positioned tiles in bookshelf are not following the gravity rules");
  }
}
