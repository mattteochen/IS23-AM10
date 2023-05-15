package it.polimi.is23am10.client.userinterface;

import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import it.polimi.is23am10.client.Client;
import it.polimi.is23am10.client.userinterface.guifactory.GuiFactory;
import it.polimi.is23am10.client.userinterface.guifactory.GuiFactory.SCENE;
import it.polimi.is23am10.server.network.messages.AbstractMessage;
import it.polimi.is23am10.server.network.messages.ChatMessage;
import it.polimi.is23am10.server.network.messages.ErrorMessage;
import it.polimi.is23am10.server.network.virtualview.VirtualView;

/**
 * A client interface using a graphic UI as I/O.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public final class GraphicUserInterface extends Application implements UserInterface{

  /**
   * Queue containing all the inputs the user sent trough readline.
   * Content to be consumed by client controller on premise.
   * 
   */
  private static final BlockingQueue<String> userInputQueue = new LinkedBlockingQueue<>();
  
  /**
   * Add a new message to the input list queue.
   *
   * @param msg The message to be added.
   * 
   */
  public static void addMsgQueue(String msg) {
    userInputQueue.add(msg);
  }

  /**
   * Retrirve the head of the message queue.
   *
   * @return The oldest message.
   * 
   */
  public static String takeMsgQueue() {
    try {
      return userInputQueue.isEmpty() ? null : userInputQueue.take();
    } catch(InterruptedException e) {
      return null;
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void start(Stage stage) {
    GuiFactory.mainStage = stage;
    Map<SCENE, Scene> m = Map.of(
      SCENE.SPLASH_SCREEN, GuiFactory.getSplashScreenScene(),
      SCENE.ENTER_GAME_SELECTION, GuiFactory.getEnterGameSelectionScene(),
      SCENE.CREATE_GAME, GuiFactory.getCreateNewGameSelectionScene()
      //JOIN_GAME scene must be created when we receive available games from server, hence in {@link GraphicUserInterface#displayAvailableGames}
    ); 
    GuiFactory.stages.putAll(m);

    stage.setTitle("MyShelfie");
    stage.setScene(GuiFactory.stages.get(SCENE.SPLASH_SCREEN));
    stage.show();
  }

  /**
   * {@inheritDoc}
   */
  public String getUserInput() {
    return takeMsgQueue();
  }

  /**
   * {@inheritDoc}
   */
  public void displaySplashScreen() {
    //this will perform javaFX init process and show the first scene
    new Thread(() -> launch()).start();
  }

  /**
   * {@inheritDoc}
   */
  public void displayAvailableGames(List<VirtualView> availableGames) {
    GuiFactory.stages.put(SCENE.JOIN_GAME, GuiFactory.getCreateJoinScene(Client.getActiveGameServers()));
  }

  /**
   * {@inheritDoc}
   */
  public void displayVirtualView(VirtualView vw) {
    // TODO Auto-generated method stub
  }

  /**
   * {@inheritDoc}
   */
  public void displayChatMessage(ChatMessage message) {
    // TODO Auto-generated method stub
  }

  /**
   * {@inheritDoc}}
   */
  public void displayError(ErrorMessage errorMessage) {
    // TODO Auto-generated method stub
  }

  /**
   *
   * {@inheritDoc}
   */
  public void displayErrorMessage(AbstractMessage message) {
    // TODO Auto-generated method stub
  }
  
  /**
   *
   * {@inheritDoc}
   */
  @Override
  public void displayGameJoinGuide() {
    GuiFactory.changeScene(() -> GuiFactory.mainStage.setScene(GuiFactory.stages.get(SCENE.ENTER_GAME_SELECTION)));
  }
}
