package it.polimi.is23am10.client.userinterface;

import java.util.Comparator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
   * Logger instance.
   *
   */
  protected final Logger logger = LogManager.getLogger(CommandLineInterface.class);

  /**
   * {@inheritDoc}
   */
  public void displaySplashScreen() {
    logger.info(CLIStrings.welcomeString);
    logger.info(CLIStrings.joinOrCreateString);
  }

  /**
   * {@inheritDoc}
   */
  public void displayAvailableGames(List<VirtualView> availableGames) {
    if (availableGames.isEmpty()) {
      logger.warn(CLIStrings.noGamesString);
    } else {
      logger.info(CLIStrings.listGamesString);
      availableGames
          .forEach(ag -> logger.info(String.format(CLIStrings.availableGameString,
              availableGames.indexOf(ag), ag.getPlayers().size(), ag.getMaxPlayers(), ag.getGameId())));
    }
  }

  /**
   * {@inheritDoc}
   */
  public void displayVirtualView(VirtualView vw) {
    logger.info(CLIStrings.currentStateString);
    if (vw.isEnded()) {
      logger.info(CLIStrings.gameOverString);
      vw.getPlayers()
          .stream()
          .sorted(Comparator.comparing(p -> p.getScore().getTotalScore(), Comparator.reverseOrder()))
          .forEach(p -> logger.info(String.format(CLIStrings.playerScoreString, p.getPlayerName(), p.getScore().getTotalScore())));
      logger.info(String.format(CLIStrings.winnerString, vw.getWinnerPlayer().getPlayerName()));
    } else {
      if (vw.isLastRound()) {
        logger.warn(CLIStrings.lastRoundString);
      }
      logger.info(String.format(CLIStrings.nowPlaying, vw.getActivePlayer().getPlayerName()));
      // TODO: Prettyprint bookshelfs and board [blocked by #107]
      logger.info(CLIStrings.moveTilesInviteString);
      logger.info(CLIStrings.moveTilesExampleString);
    }
  }

  /**
   * {@inheritDoc}
   */
  public void displayChatMessage(AbstractMessage message) {
    logger.info(String.format(CLIStrings.messageString, message.getSender().getPlayerName(), message.getMessage()));
  }

  /**
   *
   * {@inheritDoc}
   */
  public void displayErrorMessage(AbstractMessage message) {
    logger.info(String.format(CLIStrings.errorString, message.getMessage()));
  }

}
