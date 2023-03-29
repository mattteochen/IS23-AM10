package it.polimi.is23am10.playerconnector;

import it.polimi.is23am10.game.Game;
import it.polimi.is23am10.playerconnector.exceptions.NullBlockingQueueException;
import it.polimi.is23am10.playerconnector.exceptions.NullSocketConnectorException;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;

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
public class PlayerConnector extends AbstractPlayerConnector {
  /**
   * The socket connection reference.
   *
   */
  private transient Socket connector;

  /**
   * Constructor.
   *
   *
   * @param connector The {@link Socket} instance linked to a player client.
   * @param msgQueue  The message queue instance.
   * @throws NullSocketConnectorException
   * @throws NullBlockingQueueException
   *
   */
  public PlayerConnector(Socket connector, LinkedBlockingQueue<Game> msgQueue)
      throws NullSocketConnectorException, NullBlockingQueueException {
    super(msgQueue);
    if (connector == null) {
      throw new NullSocketConnectorException();
    }
    this.connector = connector;
  }

  /**
   * Getter for {@link Socket}, the low level connector.
   *
   * @return The socket connector.
   *
   */
  public synchronized Socket getConnector() {
    return connector;
  }
}
