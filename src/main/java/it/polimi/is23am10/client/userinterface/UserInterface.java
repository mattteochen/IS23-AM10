package it.polimi.is23am10.client.userinterface;

import java.util.List;

import it.polimi.is23am10.server.network.messages.AbstractMessage;
import it.polimi.is23am10.server.network.virtualview.VirtualView;

/**
 * The user interface. Implemented by CommandLineInterface (CLI) and GraphicUserInterface (GUI).
 * Only exposes the output methods. Each implementation will handle input processing accordingly.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public interface UserInterface {
  
  /**
   * Method called when first running the game.
   * 
   */
  void displaySplashScreen();

  /**
   * Method called when the user wants to join an existing
   * game and has to pick between the available ones.
   * It assumes to receive only valid games.
   */
  void displayAvailableGames(List<VirtualView> availableGames);

  /**
   * Method called to display the state of the game.
   * 
   */
  void displayVirtualView(VirtualView vw);

  /**
   * Method called to notify the user of a new message.
   * 
   */
  void displayChatMessage(AbstractMessage message);

  //TODO: Add other needed display methods if needed
}
