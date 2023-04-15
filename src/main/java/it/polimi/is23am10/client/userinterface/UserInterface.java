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
  
  void displaySplashScreen();

  void displayAvailableGames(List<VirtualView> availableGames);

  void displayVirtualView(VirtualView vw);

  void displayChatMessage(AbstractMessage message);

  //TODO: Add other needed display methods if needed
}
