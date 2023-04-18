package it.polimi.is23am10.client;

import it.polimi.is23am10.client.userinterface.UserInterface;
import it.polimi.is23am10.server.network.playerconnector.PlayerConnectorSocket;

/**
 * A client using Socket as communication method.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public class SocketClient extends Client {

  /**
   * Public constructor for client using Socket as communication method.
   * 
   * @param pc player connector
   * @param ui user interface
   */
  public SocketClient(PlayerConnectorSocket pc, UserInterface ui) {
    userInterface = ui;
    playerConnector = pc;
  }
  
}
