package it.polimi.is23am10.client.userinterface;

import java.util.Comparator;
import java.util.List;

import it.polimi.is23am10.server.network.messages.AbstractMessage;
import it.polimi.is23am10.server.network.virtualview.VirtualView;

public final class CommandLineInterface implements UserInterface {

  public void displaySplashScreen() {
    System.out.println("MyShelfie - Copyright: CranioCreations");
    System.out.println("To start, choose if [j]oining an existing match or [c]reating one:");
  }

  public void displayAvailableGames(List<VirtualView> availableGames) {
    if (availableGames.isEmpty()) {
      System.out.println("No available game to join at the moment. Please create one.");
    } else {
      System.out.println("Here a list of the available game. Join one by typing the relative index.");
      availableGames
          .forEach(ag -> System.out.println(String.format("[{}] - {}/{} joined - GameId: {}",
              availableGames.indexOf(ag), ag.getPlayers().size(), ag.getMaxPlayers(), ag.getGameId())));
    }
  }

  public void displayVirtualView(VirtualView vw) {
    System.out.println("Current state of the game:");
    if (vw.isEnded()) {
      System.out.println("Game is over. Here the leaderboard:");
      vw.getPlayers()
          .stream()
          .sorted(Comparator.comparing(p -> p.getScore().getTotalScore(), Comparator.reverseOrder()))
          .forEach(p -> System.out
              .println(String.format("{} - Score: {}", p.getPlayerName(), p.getScore().getTotalScore())));
      System.out.println(String.format("WINNER: {}", vw.getWinnerPlayer()));
    } else {
      if (vw.isLastRound()) {
        System.out.println("Attention! Someone already completed their Bookshelf: this is last round");
      }
      System.out.println(String.format("Now playing: {} - ", vw.getActivePlayer().getPlayerName()));
      // TODO: Prettyprint bookshelfs and board [blocked by #107]
      System.out.println(
          "Now make your move specifying the `XY` of the tiles you want to pick from board (max 3) and finally the column of your bookshelf you want to put them in.");
      System.out.println("E.g. `12 22 35 A` moves the three tiles specified into column A");
    }
  }

  public void displayChatMessage(AbstractMessage message) {
    System.out.println(String.format("NEW MESSAGE from {}:{}", message.getSender(), message.getMessage()));
  }

}
