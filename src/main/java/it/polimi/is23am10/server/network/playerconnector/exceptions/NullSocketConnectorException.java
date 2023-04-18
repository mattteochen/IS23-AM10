package it.polimi.is23am10.server.network.playerconnector.exceptions;

/**
 * Custom exception to handle null socket client connector.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public class NullSocketConnectorException extends Exception {
  public NullSocketConnectorException() {
    super("Null connector instance");
  }
}
