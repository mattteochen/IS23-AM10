package it.polimi.is23am10.utils.exceptions;

/**
 * Custom exception for number of free slots in the bookshelf.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */

public class NotEnoughSlotsException extends Exception {
  public NotEnoughSlotsException() {
    super("The number on selected tiles exceed the number of free slots in the bookshelf column");
  }
}
