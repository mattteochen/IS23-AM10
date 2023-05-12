package it.polimi.is23am10.client.userinterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import it.polimi.is23am10.client.userinterface.exceptions.NoUserInputsException;
import it.polimi.is23am10.client.userinterface.helpers.CLIStrings;
import it.polimi.is23am10.client.userinterface.helpers.OutputWrapper;
import it.polimi.is23am10.server.model.game.Game.GameStatus;
import it.polimi.is23am10.server.network.messages.ChatMessage;
import it.polimi.is23am10.server.network.messages.ErrorMessage;
import it.polimi.is23am10.server.network.messages.ErrorMessage.ErrorSeverity;
import it.polimi.is23am10.server.network.virtualview.VirtualView;

/**
 * A client interface using command line as I/O.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public final class CommandLineInterface implements UserInterface {

  /**
   * Output Wrapper, entrypoint for all UI output functions.
   * 
   */
  public final OutputWrapper ow;

  /**
   * Queue containing all the inputs the user sent trough readline.
   * Content to be consumed by client controller on premise.
   * 
   */
  private final BlockingQueue<String> userInputList;

  /**
   * Buffered reader used by async read thread to get user input.
   * 
   */
  private final BufferedReader br;


  /**
   * Public constructor for CLI.
   * 
   * @param showDebug parameter to be used for debug purposes.
   */
  public CommandLineInterface(boolean showDebug) {
    ow = new OutputWrapper(showDebug);
    userInputList = new LinkedBlockingQueue<String>();
    br = new BufferedReader(new InputStreamReader(System.in));
  }

  /**
   * {@inheritDoc}
   */
  public String getUserInput() {
    return userInputList.poll();
  }

  /**
   * {@inheritDoc}
   */
  public void displaySplashScreen() {
    ow.info(CLIStrings.welcomeString, true);
    ow.info(CLIStrings.insertPlayerNameString, false);
    runInputHandler();
  }

  /**
   * {@inheritDoc}
   */
  public void displayGameJoinGuide() {
    ow.info(CLIStrings.joinOrCreateString, true);
    ow.info(CLIStrings.joinExisting, false);
    ow.info(CLIStrings.createGame, false);
  }

  /**
   * {@inheritDoc}
   */
  public void displayAvailableGames(List<VirtualView> availableGames) {
    ow.debug(availableGames.toString(), false);
    if (availableGames.isEmpty()) {
      ow.warning(CLIStrings.noGamesString, false);
    } else {
      ow.info(CLIStrings.listGamesString, false);
     for (VirtualView ag : availableGames){
      ow.info(String.format(CLIStrings.availableGameString,
              availableGames.indexOf(ag), ag.getPlayers().size(), ag.getMaxPlayers(), ag.getGameId()), false);
     }
    }
  }

  /**
   * {@inheritDoc}
   */
  public void displayVirtualView(VirtualView vw) {
    ow.debug(vw.toString(), false);
    ow.info(CLIStrings.currentStateString, true);
    if (vw.getStatus() == GameStatus.ENDED) {
      ow.info(CLIStrings.gameOverString, false);
      vw.getPlayers()
          .stream()
          .sorted(Comparator.comparing(p -> p.getScore().getVisibleScore(), Comparator.reverseOrder()))
          .forEach(p -> ow.info(
              String.format(CLIStrings.playerScoreString, p.getPlayerName(), p.getScore().getTotalScore()), false));
      ow.info(String.format(CLIStrings.winnerString, vw.getWinnerPlayer().getPlayerName()), false);
    } else {
      if (vw.getStatus() == GameStatus.WAITING_FOR_PLAYERS) {
        ow.warning(String.format(CLIStrings.waitingForPlayers, vw.getPlayers().size(), vw.getMaxPlayers(), vw.getGameId()), false);
      } else {
        if (vw.getStatus() == GameStatus.LAST_ROUND) {
          ow.warning(CLIStrings.lastRoundString, false);
        }
        ow.info(String.format(CLIStrings.nowPlaying, vw.getActivePlayer().getPlayerName()), false);
  
        ow.show(vw, false);
  
        ow.info(CLIStrings.moveTilesInviteString, false);
        ow.info(CLIStrings.moveTilesExampleString, false);
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  public void displayChatMessage(ChatMessage message) {
    ow.debug(message.toString(), false);
    final String msgString = message.isBroadcast() ? CLIStrings.broadcastMessageString : CLIStrings.messageString;
    ow.chat(String.format(msgString, message.getSender().getPlayerName(), message.getMessage()), false);
  }

  /**
   * {@inheritDoc}}
   */
  public void displayError(ErrorMessage errorMessage) {
    ow.debug(errorMessage.toString(), false);
    final String msgString = errorMessage.isBroadcast() ? CLIStrings.broadcastErrorString : CLIStrings.errorMessage;
    switch (errorMessage.getErrorSeverity()) {
      case WARNING:
        ow.warning(String.format(msgString, errorMessage.getMessage()), false);
        break;
      case ERROR:
        ow.error(String.format(msgString, errorMessage.getMessage()), false);
        break;
      case CRITICAL:
        ow.critical(String.format(msgString, errorMessage.getMessage()), false);
        break;
      default:
        break;
    }
  }

  /**
   * CLI method to retrieve user input and add it to the queue
   * for it to be consumed when needed by controller. 
   */
  public void runInputHandler() {
    final Thread inputHandler = new Thread(() -> {
      try {
        while(true) {
          String newLine = br.readLine();
          if (newLine != null && !newLine.equals("")) {
            userInputList.add(newLine);
          }
        }
      } catch (IOException e) {
        displayError(new ErrorMessage(CLIStrings.inputError, ErrorSeverity.ERROR));
      }
    });
    inputHandler.start();
  }

}
