package it.polimi.is23am10.game.exceptions;

import it.polimi.is23am10.pattern.SharedPattern;

/**
 * Custom exception to null {@link SharedPattern} instances.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public class NullAssignedSharedPatternException extends Exception {
  public NullAssignedSharedPatternException() {
    super("Null shared pattern instance");
  }
}
