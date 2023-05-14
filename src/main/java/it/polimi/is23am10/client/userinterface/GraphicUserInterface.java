package it.polimi.is23am10.client.userinterface;

import java.util.List;
import java.util.Map;

import javafx.application.Application;
import javafx.stage.Stage;
 
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
   * {@inheritDoc}
   */
  @Override
  public void start(Stage stage) {
    GuiFactory.mainStage = stage;
    GuiFactory.stages = Map.of(
      SCENE.SPLASH_SCREEN, GuiFactory.getSplashScreenScene(),
      SCENE.ENTER_GAME_SELECTION, GuiFactory.getEnterGameSelectionScene()
    ); 

    stage.setTitle("MyShelfie");
    stage.setScene(GuiFactory.stages.get(SCENE.SPLASH_SCREEN));
    stage.show();
  }

  /**
   * {@inheritDoc}
   */
  public String getUserInput() {
    return "test";
    // TODO Auto-generated method stub
  }

  /**
   * {@inheritDoc}
   */
  public void displaySplashScreen() {
    //this will perform javaFX init process and show the welcome screen
    launch();
  }

  /**
   * {@inheritDoc}
   */
  public void displayAvailableGames(List<VirtualView> availableGames) {
    // TODO Auto-generated method stub
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
    // TODO Auto-generated method stub
  }
}
