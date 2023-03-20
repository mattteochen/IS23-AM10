package it.polimi.is23am10.playerconnector;

import it.polimi.is23am10.playerconnector.exceptions.NullSocketConnectorException;
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
  private UUID gameId;

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

  /**
   * Getter for {@link Socket}, the low level connector.
   *
   * @return The socket connector.
   *
   */
  public synchronized Socket getConnector() {
    return connector;
  }

  /**
   * Getter for the associated game id.
   *
   * @return The game id.
   *
   */
  public synchronized UUID getGameId() {
    return gameId;
  }

  /**
   * Getter for the associated player name.
   *
   * @return The player name as string.
   *
   */
  public synchronized String getPlayerName() {
    return playerName;
  }

  /**
   * Setter for the associated game id.
   *
   * @param gameId The game id to associate to the current player connector.
   *
   */
  public synchronized void setGameId(UUID gameId) {
    this.gameId = gameId;
  }

  /**
   * Setter for the player name.
   *
   * @param playerName The player name to associate to the current player connector.
   *
   */
  public synchronized void setPlayerName(String playerName) {
    this.playerName = playerName;
  }
}
