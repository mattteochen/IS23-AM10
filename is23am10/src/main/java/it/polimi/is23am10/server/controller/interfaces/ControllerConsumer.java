package it.polimi.is23am10.server.controller.interfaces;

import it.polimi.is23am10.server.command.AbstractCommand;
import it.polimi.is23am10.server.playerconnector.PlayerConnector;

@FunctionalInterface
public interface ControllerConsumer<T extends AbstractCommand> {
  void accept(PlayerConnector connector, T command);
}

