package it.polimi.is23am10.server.playerconnector;

import it.polimi.is23am10.server.playerconnector.exceptions.NullSocketConnectorException;
import java.net.Socket;
import java.util.UUID;

/**
 * The player connector class definition.
 * This class is responsible to handle clients' sockets connections.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public class PlayerConnector {

  /**
   * The player name inside a game session.
   *
   */
  private String playerName;

  /**
   * The unique {@link Game} id reference.
   *
   */
  private UUID gameID;

  /**
   * The socket connection reference.
   *
   */
  private Socket connector;

  /**
   * Constructor.
   *
   *
   * @param connector The {@link Socket} instance linked to a player client.
   * @throws NullSocketConnectorException
   *
   */
  public PlayerConnector(Socket connector) throws NullSocketConnectorException {
    if (connector == null) {
      throw new NullSocketConnectorException();
    }
    this.connector = connector;
  }

  public Socket getConnector() {
    return connector;
  }
}
