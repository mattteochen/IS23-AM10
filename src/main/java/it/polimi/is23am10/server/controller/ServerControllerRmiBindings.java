package it.polimi.is23am10.server.controller;

import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import it.polimi.is23am10.server.controller.exceptions.NullRmiServerException;
import it.polimi.is23am10.server.controller.exceptions.NullRmiStubException;
import it.polimi.is23am10.server.controller.interfaces.IServerControllerAction;

public final class ServerControllerRmiBindings {

  /**
   * RMI don't care port flag.
   *
   */
  private static final int DONT_CARE = 0;

  /**
   * RMI global registry service.
   *
   */
  private static Registry globalRmiRegistry;

  /**
   * RMI {@link ServerControllerAction} server instance.
   *
   */
  @SuppressWarnings("unused")
  private static IServerControllerAction serverControllerActionRmiServer;

  /**
   * RMI {@link ServerControllerAction} stub instance.
   *
   */
  private static IServerControllerAction serverControllerActionRmiStub;

  /**
   * Private constructor.
   *
   */
  private ServerControllerRmiBindings() {
  }

  /**
   * Retrieve the Server rmi registry.
   *
   * @return The registry.
   *
   */
  public static Registry getRmiRegistry() {
    return globalRmiRegistry;
  }

  /**
   * Set the rmi registry.
   *
   * @param r The registry to be set.
   *
   */
  public static void setRmiRegistry(Registry r) {
    globalRmiRegistry = r;
  }

  /**
   * Retrieve the rmi stub for {@link ServerControllerAction}.
   *
   * @return The stub.
   *
   */
  public static IServerControllerAction getServerControllerActionRmiStub() {
    return serverControllerActionRmiStub;
  }

  /**
   * Set the rmi server for {@link ServerControllerAction}.
   *
   * @param s The rmi server.
   *
   */
  public static void setServerControllerActionServer(IServerControllerAction s) throws NullRmiServerException {
    if (s == null) {
      throw new NullRmiServerException();
    }
    serverControllerActionRmiServer = s;
  }

  /**
   * Set the rmi stub for {@link ServerControllerAction}.
   *
   * @param s The rmi stub.
   *
   */
  public static void setServerControllerActionStub(IServerControllerAction s) throws NullRmiServerException {
    if (s == null) {
      throw new NullRmiStubException();
    }
    serverControllerActionRmiStub = s;
  }

  /**
   * Rebind a {@link ServerControllerAction}.
   *
   * @param sca The {@link ServerControllerAction} instance.
   *
   */
  public static void rebindServerControllerAction(IServerControllerAction sca) throws RemoteException {
    ServerControllerRmiBindings.setServerControllerActionServer(sca);
    ServerControllerRmiBindings
        .setServerControllerActionStub((IServerControllerAction) UnicastRemoteObject.exportObject(sca, DONT_CARE));
    ServerControllerRmiBindings.getRmiRegistry().rebind(IServerControllerAction.class.getName(),
        ServerControllerRmiBindings.getServerControllerActionRmiStub());
  }
}
