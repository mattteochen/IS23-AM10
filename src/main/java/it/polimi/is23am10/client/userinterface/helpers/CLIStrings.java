package it.polimi.is23am10.client.userinterface.helpers;

/**
 * An helper class containing all the strings to display
 * to the user through UserInterface.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public final class CLIStrings {
  public final static String welcomeString = 
    """
      ███    ███ ██    ██     ███████ ██   ██ ███████ ██      ███████ ██ ███████ 
      ████  ████  ██  ██      ██      ██   ██ ██      ██      ██      ██ ██      
      ██ ████ ██   ████       ███████ ███████ █████   ██      █████   ██ █████   
      ██  ██  ██    ██             ██ ██   ██ ██      ██      ██      ██ ██      
      ██      ██    ██        ███████ ██   ██ ███████ ███████ ██      ██ ███████
      
      Politecnico di Milano - Software Engineering Project - Group IS23AM10
      
    """;
  public final static String joinOrCreateString = "To start, choose if [j]oining an existing match or [c]reating one. If joining a game you should specify the game index, if creating a new one specify the number of maximum players.";
  public final static String noGamesString = "No available game to join at the moment. Please create one.";
  public final static String insertPlayerNameString = "Insert your player name here:";
  public final static String listGamesString = "Here a list of the available games. Join one by typing the relative index.";
  public final static String availableGameString = "[%d] - %d/%d joined - GameId: %s";
  public final static String currentStateString = "Current state of the game:";
  public final static String gameOverString = "Game is over. Here the leaderboard:";
  public final static String playerScoreString = "%s - Score: %d";
  public final static String winnerString = "WINNER: %s";
  public final static String lastRoundString = "Attention! Someone already completed their Bookshelf: this is last round";
  public final static String nowPlaying = "Now playing: %s";
  public final static String moveTilesInviteString =  "Now make your move specifying the `XY` of the tile you want to pick from board (max 3) followed by the coordinates of the bookshelf you want to put the tile in.";
  public final static String moveTilesExampleString =  "E.g. `12 22 25 57` moves the first tile (1,2) to the bookshelf coords (2,2), the second tile from the board coord(2,5) to the bookshelf coord (5,7)";
  public final static String messageString =  "(%s -> You): %s";
  public final static String broadcastMessageString = "(%s -> All): %s";
  public final static String errorMessage =  "(Server -> You): %s";
  public final static String broadcastErrorString = "(Server -> All): %s";
}
