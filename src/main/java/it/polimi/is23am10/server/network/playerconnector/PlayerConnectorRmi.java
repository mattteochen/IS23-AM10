package it.polimi.is23am10.server.network.playerconnector;

import java.rmi.RemoteException;
import java.util.concurrent.LinkedBlockingQueue;

import it.polimi.is23am10.client.Client;
import it.polimi.is23am10.client.IClient;
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
   * The {@link Client} reference.
   */
  private IClient client;

  /**
   * Constructor.
   *
   * @param msgQueue The message queue instance.
   * @throws NullBlockingQueueException If providing a null queue when building player connector.
   *
   */
  public PlayerConnectorRmi(LinkedBlockingQueue<AbstractMessage> msgQueue, IClient client)
      throws NullBlockingQueueException {
    super(msgQueue);
    this.client = client;
  }

  /**
   * Retrive the {@link Client} reference.
   *
   * @return The client reference.
   *
   */
  public IClient getClient() {
    return client;
  }

  /**
   * Set the {@link Client} reference.
   *
   * @param client The client reference.
   *
   */
  public void setClient(IClient client) {
    this.client = client;
  }

  /** {@inheritDoc} */
  @Override
  public void notify(AbstractMessage msg) throws InterruptedException, RemoteException {
    if (msg == null) {
      return;
    }
    client.showServerMessage(msg);
  } 
}
