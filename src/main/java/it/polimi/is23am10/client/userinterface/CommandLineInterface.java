package it.polimi.is23am10.client.userinterface;

import java.util.Comparator;
import java.util.List;

import it.polimi.is23am10.client.userinterface.helpers.CLIStrings;
import it.polimi.is23am10.server.network.messages.AbstractMessage;
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
   * {@inheritDoc}
   */
  public void displaySplashScreen() {
    System.out.println(CLIStrings.welcomeString);
    System.out.println(CLIStrings.joinOrCreateString);
  }

  /**
   * {@inheritDoc}
   */
  public void displayAvailableGames(List<VirtualView> availableGames) {
    if (availableGames.isEmpty()) {
      System.out.println(CLIStrings.noGamesString);
    } else {
      System.out.println(CLIStrings.listGamesString);
      availableGames
          .forEach(ag -> System.out.println(String.format(CLIStrings.availableGameString,
              availableGames.indexOf(ag), ag.getPlayers().size(), ag.getMaxPlayers(), ag.getGameId())));
    }
  }

  /**
   * {@inheritDoc}
   */
  public void displayVirtualView(VirtualView vw) {
    System.out.println(CLIStrings.currentStateString);
    if (vw.isEnded()) {
      System.out.println(CLIStrings.gameOverString);
      vw.getPlayers()
          .stream()
          .sorted(Comparator.comparing(p -> p.getScore().getTotalScore(), Comparator.reverseOrder()))
          .forEach(p -> System.out
              .println(String.format(CLIStrings.playerScoreString, p.getPlayerName(), p.getScore().getTotalScore())));
      System.out.println(String.format(CLIStrings.winnerString, vw.getWinnerPlayer().getPlayerName()));
    } else {
      if (vw.isLastRound()) {
        System.out.println(CLIStrings.lastRoundString);
      }
      System.out.println(String.format(CLIStrings.nowPlaying, vw.getActivePlayer().getPlayerName()));
      // TODO: Prettyprint bookshelfs and board [blocked by #107]
      System.out.println(CLIStrings.moveTilesInviteString);
      System.out.println(CLIStrings.moveTilesExampleString);
    }
  }

  /**
   * {@inheritDoc}
   */
  public void displayChatMessage(AbstractMessage message) {
    System.out.println(String.format(CLIStrings.messageString, message.getSender().getPlayerName(), message.getMessage()));
  }

}
