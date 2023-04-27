package it.polimi.is23am10.client.userinterface;

import java.util.Comparator;
import java.util.List;

import it.polimi.is23am10.client.userinterface.helpers.CLIStrings;
import it.polimi.is23am10.client.userinterface.helpers.OutputWrapper;
import it.polimi.is23am10.server.network.messages.ChatMessage;
import it.polimi.is23am10.server.network.messages.ErrorMessage;
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

  public final OutputWrapper ow;

  public CommandLineInterface(boolean showDebug) {
    ow = new OutputWrapper(showDebug);
  }

  /**
   * {@inheritDoc}
   */
  public void displaySplashScreen() {
    ow.info(CLIStrings.welcomeString, true);
    ow.info(CLIStrings.joinOrCreateString, false);
  }

  /**
   * {@inheritDoc}
   */
  public void displayAvailableGames(List<VirtualView> availableGames) {
    ow.debug(availableGames.toString(), false);
    if (availableGames.isEmpty()) {
      ow.warning(CLIStrings.noGamesString, false);
    } else {
      ow.info(CLIStrings.listGamesString, true);
      availableGames
          .forEach(ag -> ow.info(String.format(CLIStrings.availableGameString,
              availableGames.indexOf(ag), ag.getPlayers().size(), ag.getMaxPlayers(), ag.getGameId()), false));
    }
  }

  /**
   * {@inheritDoc}
   */
  public void displayVirtualView(VirtualView vw) {
    ow.debug(vw.toString(), false);
    ow.info(CLIStrings.currentStateString, true);
    if (vw.isEnded()) {
      ow.info(CLIStrings.gameOverString, false);
      vw.getPlayers()
          .stream()
          .sorted(Comparator.comparing(p -> p.getScore().getTotalScore(), Comparator.reverseOrder()))
          .forEach(p -> ow.info(
              String.format(CLIStrings.playerScoreString, p.getPlayerName(), p.getScore().getTotalScore()), false));
      ow.info(String.format(CLIStrings.winnerString, vw.getWinnerPlayer().getPlayerName()), false);
    } else {
      if (vw.isLastRound()) {
        ow.warning(CLIStrings.lastRoundString, false);
      }
      ow.info(String.format(CLIStrings.nowPlaying, vw.getActivePlayer().getPlayerName()), false);

      ow.show(vw.getGameBoard(), false);
      ow.show(vw, false);

      ow.info(CLIStrings.moveTilesInviteString, false);
      ow.info(CLIStrings.moveTilesExampleString, false);
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

}
