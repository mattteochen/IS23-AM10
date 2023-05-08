package it.polimi.is23am10.server.network.playerconnector;

import java.util.concurrent.LinkedBlockingQueue;

import it.polimi.is23am10.server.network.messages.AbstractMessage;
import it.polimi.is23am10.server.network.playerconnector.exceptions.NullBlockingQueueException;

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
   * @throws NullBlockingQueueException If providing a null queue when building player connector.
   *
   */
  public PlayerConnectorRmi(LinkedBlockingQueue<AbstractMessage> msgQueue)
      throws NullBlockingQueueException {
    super(msgQueue);
  }
}
