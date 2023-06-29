package it.polimi.is23am10.client.userinterface;

import it.polimi.is23am10.client.Client;
import it.polimi.is23am10.client.userinterface.guifactory.GuiFactory;
import it.polimi.is23am10.client.userinterface.guifactory.GuiFactory.SCENE;
import it.polimi.is23am10.server.model.game.Game.GameStatus;

import java.io.Serializable;
import java.util.List;

import it.polimi.is23am10.server.network.messages.ChatMessage;
import it.polimi.is23am10.server.network.messages.ErrorMessage;
import it.polimi.is23am10.server.network.messages.ErrorMessage.ErrorSeverity;
import it.polimi.is23am10.server.network.virtualview.VirtualView;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * A VirtualView pair class definition.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
class VirtualViewPair {
  /**
   * The new {@link VirtualView}.
   */
  VirtualView newVw;

  /**
   * The old {@link VirtualView}.
   */
  VirtualView oldVw;

  /**
   * Notify the receiver that this pair is intended to be treated as null.
   */
  boolean isNull;

  /**
   * Constructor.
   *
   * @param o The old {@link VirtualView} instance.
   * @param n The new {@link VirtualView} instance.
   * @param in The isNull flag.
   *
   */
  public VirtualViewPair(VirtualView o, VirtualView n, boolean in) {
    newVw = n;
    oldVw = o;
    isNull = in;
  }
}

class VirtualViewSceneHandler {

  /**
   * Custom lock object.
   */
  private static final Object stopGameSnapshotHandlerLock = new Object();

  /**
   * A list {@link VirtualViewPair} to be shown.
   */
  private static final BlockingQueue<VirtualViewPair> gameSnapshots = new LinkedBlockingQueue<VirtualViewPair>();

  /**
   * Boolean flag to stop the game scene handler thread.
   */
  private static boolean stopGameSnapshotHandler = false;

  /**
   * Setter for {@link VirtualViewSceneHandler#stopGameSnapshotHandler}
   *
   * @param flag The flag to be set.
   */
  protected static void setStopGameSnapshotHandler(boolean flag) {
    synchronized(stopGameSnapshotHandlerLock) {
      stopGameSnapshotHandler = flag;
    }
  }

  /**
   * Getter for {@link VirtualViewSceneHandler#stopGameSnapshotHandler}
   *
   * @return The flag status.
   */
  protected static boolean getStopGameSnapshotHandler() {
    synchronized(stopGameSnapshotHandlerLock) {
      return stopGameSnapshotHandler;
    }
  }

  /**
   * Add a new {@link VirtualViewPair} object to the {@link VirtualViewSceneHandler#gameSnapshots}.
   *
   * @param vwp The {@link VirtualViewPair}.
   */
  protected static void addVirtualViewPair(VirtualViewPair vwp) {
    gameSnapshots.add(vwp);
  }

  protected static void notifyVirtualViewPairQueue() {
    gameSnapshots.notifyAll();
  }

  /**
   * Run the game scene handler thread.
   *
   */
  protected static void runGameSnapShotHandler() {
    new Thread(() -> {
      while(!getStopGameSnapshotHandler()) {
        VirtualViewPair vwp = null;
        try {
          vwp = gameSnapshots.take();
          if (vwp != null) {
            if (vwp.isNull) {
              break;
            }
            if (vwp.newVw.getStatus() == GameStatus.ENDED) {
              GuiFactory.stages.put(SCENE.END_GAME, GuiFactory.getEndGameScene(vwp.newVw));
              GuiFactory.executeOnJavaFX(
                  () -> GuiFactory.mainStage.setScene(GuiFactory.stages.get(SCENE.END_GAME)));
            } else {
              if (vwp.newVw.getStatus() == GameStatus.WAITING_FOR_PLAYERS) {
                GuiFactory.executeOnJavaFX(
                    () -> GuiFactory.mainStage.setScene(GuiFactory.stages.get(SCENE.WAIT_GAME)));
              } else {
                if (GuiFactory.stages.get(SCENE.GAME_SNAPSHOT) == null) {
                  GuiFactory.stages.put(SCENE.GAME_SNAPSHOT, GuiFactory.getGameSnapshotScene(vwp.newVw));
                  GuiFactory.executeOnJavaFX(
                      () -> GuiFactory.mainStage.setScene(GuiFactory.stages.get(SCENE.GAME_SNAPSHOT)));
                } else {
                  StackPane root = (StackPane) GuiFactory.mainStage.getScene().getRoot();
                  final VirtualView oldVw = vwp.oldVw;
                  final VirtualView newVw = vwp.newVw;
                  GuiFactory.executeOnJavaFX(() -> GuiFactory.GameSnapshotFactory.updateGameWidget(root, oldVw, newVw));
                }
              }
            }
          }
        } catch(InterruptedException e) {

        } catch(NullPointerException e) {

        }
      }
    }).start();
  }
}

/**
 * A client interface using a graphic UI as I/O.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public final class GraphicUserInterface extends Application implements UserInterface, Serializable {

  /**
   * Queue containing all the inputs the user sent trough readline. Content to be
   * consumed by client
   * controller on premise.
   */
  private static final BlockingQueue<String> userInputQueue = new LinkedBlockingQueue<>();

  /**
   * Constructor.
   */
  public GraphicUserInterface() {
    VirtualViewSceneHandler.runGameSnapShotHandler();
  }

  /**
   * Add a new message to the input list queue.
   *
   * @param msg The message to be added.
   */
  public static void addMsgQueue(String msg) {
    userInputQueue.add(msg);
  }

  /**
   * Retrirve the head of the message queue.
   *
   * @return The oldest message.
   */
  public static String takeMsgQueue() {
    try {
      return userInputQueue.isEmpty() ? null : userInputQueue.take();
    } catch (InterruptedException e) {
      return null;
    }
  }

  /** {@inheritDoc} */
  @Override
  public void start(Stage stage) {
    GuiFactory.mainStage = stage;
    stage.setResizable(false);
    Map<SCENE, Scene> m = Map.of(
        SCENE.SPLASH_SCREEN, GuiFactory.getSplashScreenScene(),
        SCENE.ENTER_GAME_SELECTION, GuiFactory.getEnterGameSelectionScene(),
        SCENE.CREATE_GAME, GuiFactory.getCreateNewGameSelectionScene(),
        SCENE.WAIT_GAME, GuiFactory.getWaitGameScene()
    // some scenes require the {@link VirtualView}, they are built later
    );
    GuiFactory.stages.putAll(m);

    stage.setTitle("MyShelfie - IS23AM10");
    stage.setScene(GuiFactory.stages.get(SCENE.SPLASH_SCREEN));
    stage.setOnCloseRequest(e -> {
      Client.setForceCloseApplication(true);
      VirtualViewSceneHandler.addVirtualViewPair(new VirtualViewPair(null, null, true));
      VirtualViewSceneHandler.setStopGameSnapshotHandler(true);
    });
    stage.show();
  }

  /** {@inheritDoc} */
  public String getUserInput() {
    return takeMsgQueue();
  }

  /** {@inheritDoc} */
  public void displaySplashScreen() {
    // this will perform javaFX init process and show the first scene
    new Thread(() -> {
      launch();
    }).start();
  }

  /** {@inheritDoc} */
  public void displayAvailableGames(List<VirtualView> availableGames) {
    GuiFactory.stages.put(
        SCENE.JOIN_GAME, GuiFactory.getCreateJoinScene(Client.getActiveGameServers()));
  }

  //For RMI: This was the original implementation (with GUI update from remote calls), but it does have some functional issues.
  //We are aware that the turnaround that we have chosen (to save the data and trigger the scene from another thread) does not reflect the RMI paradigm at 100%
  //
  // /** {@inheritDoc} */
  // public void displayVirtualView(VirtualView old, VirtualView vw) {
  //   if (vw.getStatus() == GameStatus.ENDED) {
  //     GuiFactory.stages.put(SCENE.END_GAME, GuiFactory.getEndGameScene(vw));
  //     GuiFactory.executeOnJavaFX(
  //         () -> GuiFactory.mainStage.setScene(GuiFactory.stages.get(SCENE.END_GAME)));
  //   } else {
  //     if (vw.getStatus() == GameStatus.WAITING_FOR_PLAYERS) {
  //       GuiFactory.executeOnJavaFX(
  //           () -> GuiFactory.mainStage.setScene(GuiFactory.stages.get(SCENE.WAIT_GAME)));
  //     } else {
  //       if (GuiFactory.stages.get(SCENE.GAME_SNAPSHOT) == null) {
  //         GuiFactory.stages.put(SCENE.GAME_SNAPSHOT, GuiFactory.getGameSnapshotScene(vw));
  //         GuiFactory.executeOnJavaFX(
  //             () -> GuiFactory.mainStage.setScene(GuiFactory.stages.get(SCENE.GAME_SNAPSHOT)));
  //       } else {
  //         StackPane root = (StackPane) GuiFactory.mainStage.getScene().getRoot();
  //         GuiFactory.executeOnJavaFX(() -> GuiFactory.GameSnapshotFactory.updateGameWidget(root, old, vw));
  //       }
  //     }
  //   }
  // }

  /** {@inheritDoc} */
  public void displayVirtualView(VirtualView old, VirtualView vw) {
    VirtualViewSceneHandler.addVirtualViewPair(new VirtualViewPair(old, vw, false));
  }

  /** {@inheritDoc} */
  public void displayChatMessage(ChatMessage message) {
    StackPane root = (StackPane) GuiFactory.mainStage.getScene().getRoot();
    GuiFactory.executeOnJavaFX(
        () -> GuiFactory.GameSnapshotFactory.updateChatHistory(root, message));
  }

  /** {@inheritDoc}} */
  public void displayError(ErrorMessage errorMessage) {
    StackPane root = (StackPane) GuiFactory.mainStage.getScene().getRoot();
    if(errorMessage.getErrorSeverity() != ErrorSeverity.INFO){
      GuiFactory.executeOnJavaFX(
        () -> GuiFactory.getErrorMessage(root, errorMessage)
      );
      if (errorMessage.getMessage().contains("You have been disconnected")) {
        try {
          Thread.sleep(2000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        Client.setForceCloseApplication(true); 
      }
    } else {
      GuiFactory.executeOnJavaFX(
        () -> GuiFactory.GameSnapshotFactory.updateChatHistory(root, errorMessage)
      );
    }
  }

  /** {@inheritDoc} */
  public void terminateUserInterface() {
    Platform.exit();
  }

  /** {@inheritDoc} */
  @Override
  public void displayGameJoinGuide() {
    GuiFactory.executeOnJavaFX(
        () -> GuiFactory.mainStage.setScene(GuiFactory.stages.get(SCENE.ENTER_GAME_SELECTION)));
  }
}
