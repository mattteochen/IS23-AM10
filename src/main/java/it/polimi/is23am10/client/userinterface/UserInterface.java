package it.polimi.is23am10.client.userinterface;

import java.util.List;

import it.polimi.is23am10.server.network.messages.ChatMessage;
import it.polimi.is23am10.server.network.messages.ErrorMessage;
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
   * Method used to retrieve the strings the user might have sent trough their 
   * input interface. GUI adds these strings on button clicks while CLI adds them on return.
   * 
   * @return The first item in the list. Null if empty.
   */
  String getUserInput();
  
  /**
   * Method called when first running the game.
   * 
   */
  void displaySplashScreen();

  /**
   * Method to display a quick help guide to explain how
   * to join the games or create one. In GUI enables buttons.
   */
  void displayGameJoinGuide();

  /**
   * Method called when the user wants to join an existing
   * game and has to pick between the available ones.
   * It assumes to receive only valid games.
   * 
   * @param availableGames A list of valid joinable games.
   */
  void displayAvailableGames(List<VirtualView> availableGames);

  /**
   * Method called to display the state of the game.
   * 
   * @param vw {@link VirtualView} of the game to display.
   */
  void displayVirtualView(VirtualView old, VirtualView vw);

  /**
   * Method called to notify the user of a new chat message.
   * 
   * @param message The message to show to the user.
   */
  void displayChatMessage(ChatMessage message);

  /**
   * Method called to notify the user of an error.
   * 
   * @param errorMessage message to show to the user.
   */
  void displayError(ErrorMessage errorMessage);

  /**
   * Method used when game is over to close input and eventually show infos.
   *
   */
  void terminateUserInterface();

  //TODO: Add other needed display methods if needed
}
