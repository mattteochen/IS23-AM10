package it.polimi.is23am10.server.controller;

import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import it.polimi.is23am10.server.controller.exceptions.NullRmiServerException;
import it.polimi.is23am10.server.controller.exceptions.NullRmiStubException;
import it.polimi.is23am10.server.controller.interfaces.IServerControllerAction;
import it.polimi.is23am10.server.network.playerconnector.AbstractPlayerConnector;
import it.polimi.is23am10.server.network.playerconnector.PlayerConnectorRmi;
import it.polimi.is23am10.server.network.playerconnector.interfaces.IPlayerConnector;

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
  private static IServerControllerAction serverControllerActionRmiServer;

  /**
   * RMI {@link ServerControllerAction} stub instance.
   *
   */
  private static IServerControllerAction serverControllerActionRmiStub;

  /**
   * RMI {@link IPlayerConnector} server instances.
   *
   */
  private static List<IPlayerConnector> playerConnectorsRmiServer = new ArrayList<>();

  /**
   * RMI {@link IPlayerConnector} stub instances.
   *
   */
  private static List<IPlayerConnector> playerConnectorsRmiStub = new ArrayList<>();

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
   * Add a rmi server for {@link IPlayerConnector}.
   *
   * @param s The rmi server.
   *
   */
  public static void addRmiServer(IPlayerConnector s) throws NullRmiServerException {
    if (s == null) {
      throw new NullRmiServerException();
    }
    playerConnectorsRmiServer.add(s);
  }

  /**
   * Add a rmi stub for {@link IPlayerConnector}.
   *
   * @param s The rmi stub.
   *
   */
  public static void addRmiStub(IPlayerConnector s) throws NullRmiStubException {
    if (s == null) {
      throw new NullRmiStubException();
    }
    playerConnectorsRmiStub.add(s);
  }

  /**
   * Get the {@link PlayerConnectorRmi} rebind string as [className][playerName].
   *
   * @param pc The player connector.
   *
   */
  public static String getPlayerConnectorRebindId(AbstractPlayerConnector pc) {
    return "rmi://68.219.239.114/" + pc.getClass().getName() + "/" + pc.getPlayer().getPlayerName();
  }

  /**
   * Rebind a {@link PlayerConnectorRmi}.
   *
   * @param pc The player connector.
   *
   */
  public static void rebindPlayerConnector(AbstractPlayerConnector pc) throws RemoteException {
    IPlayerConnector rmiServer = pc;
    IPlayerConnector rmiStub = (IPlayerConnector) UnicastRemoteObject.exportObject((IPlayerConnector) pc, DONT_CARE);
    ServerControllerRmiBindings.addRmiStub(rmiStub);
    ServerControllerRmiBindings.addRmiServer(rmiServer);
    ServerControllerRmiBindings.getRmiRegistry().rebind(getPlayerConnectorRebindId(pc), pc);
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
    ServerControllerRmiBindings.getRmiRegistry().rebind("rmi://68.219.239.114/" + IServerControllerAction.class.getName(),
        ServerControllerRmiBindings.getServerControllerActionRmiStub());
  }
}
