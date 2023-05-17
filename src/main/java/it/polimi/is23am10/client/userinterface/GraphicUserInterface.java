package it.polimi.is23am10.client.userinterface;

import java.io.Serializable;
import java.util.List;

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
public final class GraphicUserInterface implements UserInterface, Serializable {

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
    // TODO Auto-generated method stub
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
