package it.polimi.is23am10.rmi;

import java.rmi.RemoteException;

import it.polimi.is23am10.command.AbstractCommand;
import it.polimi.is23am10.command.StartGameCommand;

public class MessengerServiceImpl implements MessengerService {

  private AbstractCommand command;
  
  @Override
  public String sendMessage(String clientMessage) {
    return "Client Message".equals(clientMessage) ? "Server Message" : null;
  }

  public String unexposedMethod() {
    return null;
  }
  
  public AbstractCommand getCommand() throws RemoteException {
    return command;
  }

  public void setStartGameCommand(String playerName, Integer numPlayers) throws RemoteException{
    this.command = new StartGameCommand(playerName, numPlayers);
  }
}
