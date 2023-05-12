package it.polimi.is23am10.client.userinterface.exceptions;

/**
 * Custom exception to handle empty user inputs.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public class NoUserInputsException extends Exception{
  public NoUserInputsException() {
    super("Can't retrieve any user input as the list is empty.");
  }
}
