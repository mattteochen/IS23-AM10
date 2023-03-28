package it.polimi.is23am10.controller.interfaces;

import it.polimi.is23am10.command.AbstractCommand;
import it.polimi.is23am10.playerconnector.PlayerConnector;

public interface IServerControllerAction {
  void execute(PlayerConnector connector, AbstractCommand command);
}
