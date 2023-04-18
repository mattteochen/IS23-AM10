package it.polimi.is23am10.server.network.playerconnector;

import java.util.concurrent.LinkedBlockingQueue;

import it.polimi.is23am10.server.network.messages.AbstractMessage;
import it.polimi.is23am10.server.network.playerconnector.exceptions.NullBlockingQueueException;
import it.polimi.is23am10.server.network.playerconnector.exceptions.NullSocketConnectorException;

/**
 * The player connector class definition.
 * This class is responsible to handle clients' sockets connections.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
@SuppressWarnings({ "checkstyle:nonemptyatclausedescriptioncheck" })
public class PlayerConnectorRmi extends AbstractPlayerConnector {
  /**
   * Constructor.
   *
   * @param msgQueue The message queue instance.
   * @throws NullBlockingQueueException
   *
   */
  public PlayerConnectorRmi(LinkedBlockingQueue<AbstractMessage> msgQueue)
      throws NullSocketConnectorException, NullBlockingQueueException {
    super(msgQueue);
  }
}
