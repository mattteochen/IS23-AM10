package it.polimi.is23am10.utils.exceptions;

/**
 * Custom exception for illegal selected tiles without one free side.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public class TilesWithoutOneFreeSideException extends Exception{
  public TilesWithoutOneFreeSideException() {
    super("Selected tiles without one free side");
  }
}
