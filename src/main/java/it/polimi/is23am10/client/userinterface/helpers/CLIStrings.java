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
  public final static String welcomeString = """
        ███    ███ ██    ██     ███████ ██   ██ ███████ ██      ███████ ██ ███████
        ████  ████  ██  ██      ██      ██   ██ ██      ██      ██      ██ ██
        ██ ████ ██   ████       ███████ ███████ █████   ██      █████   ██ █████
        ██  ██  ██    ██             ██ ██   ██ ██      ██      ██      ██ ██
        ██      ██    ██        ███████ ██   ██ ███████ ███████ ██      ██ ███████

        Politecnico di Milano - Software Engineering Project - Group IS23AM10

      """;
  public final static String joinOrCreateString = "To start, choose if [j]oining an existing match or [c]reating one:";
  public final static String noGamesString = "No available game to join at the moment. Please create one.";
  public final static String listGamesString = "Here a list of the available games. Join one by typing the relative index.";
  public final static String availableGameString = "[%d] - %d/%d joined - GameId: %s";
  public final static String currentStateString = "Current state of the game:";
  public final static String gameOverString = "Game is over. Here the leaderboard:";
  public final static String playerScoreString = "%s - Score: %d";
  public final static String winnerString = "WINNER: %s";
  public final static String lastRoundString = "Attention! Someone already completed their Bookshelf: this is last round";
  public final static String nowPlaying = "Now playing: %s";
  public final static String moveTilesInviteString = "Now make your move specifying the `XY` of the tiles you want to pick from board (max 3) and finally the column of your bookshelf you want to put them in.";
  public final static String moveTilesExampleString = "E.g. `12 22 35 A` moves the three tiles specified into column A";
  public final static String messageString = "(%s -> You): %s";
  public final static String broadcastMessageString = "(%s -> All): %s";
  public final static String errorMessage = "(Server -> You): %s";
  public final static String broadcastErrorString = "(Server -> All): %s";
  public final static String bottomPaddingBoard = "\t⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛\n\n";
  public final static String topPaddingBoard = "\t⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛" + ANSICodes.RED_BOLD + "\sY\t" + ANSICodes.RESET;
  public final static String paddingBookshelf = "\t⬛⬛⬛⬛⬛⬛⬛\t";
  public final static String indexBoard = ANSICodes.RED_BOLD + "\t\sX" + ANSICodes.RESET + " 1 2 3 4 5 6 7 8 9\t";
  public final static String verticalBoardIndex = "⬛\s%d\t";
  public final static String indexBookshelf = "\t\s\sA B C D E\s\t";
  public final static String boardStatus = "\n\tGame board status:\t\n";
  public final static String playerIdx = "\tPlayer #%d\t";
  public final static String blackLargeSquare = "";
  public final static String tabBlackSquare = "\t⬛";
  public final static String blackSquareTab = "⬛\t";
  public final static String tab = "\t";
  public final static String newLine = "\n";
  public final static String doubleNewLine = "\n\n";
  public final static String tableHeader1 = "\t%-" + OutputWrapper.XXS_PADDING + "s | %-" + OutputWrapper.XS_PADDING + "s | %-";
  public final static String tableHeader2 = "s | %-" + OutputWrapper.M_PADDING + "s | %-" + OutputWrapper.XL_PADDING
      + "s | %-" + OutputWrapper.XXL_PADDING + "s | %-" + 14 + "s | %-" + OutputWrapper.M_PADDING + "s | %-"
      + OutputWrapper.S_PADDING + "s ";
  public final static String N = "N.";
  public final static String status = "Status";
  public final static String player = "Player";
  public final static String role = "Role";
  public final static String scoreBlockPoints = "Score Block Points";
  public final static String bookshelfPoints = "Bookshelf Points";
  public final static String extraPoints = "Extra Points";
  public final static String totalScore = "Total Score";
  public final static String privatePoints = "Private Points";
  public final static String tableLines1 = "\t%-" + OutputWrapper.XXS_PADDING + "s | %-" + OutputWrapper.XS_PADDING + "s | %-";
  public final static String tableLines2 = "s | %-" + OutputWrapper.M_PADDING + "s | %-" + OutputWrapper.XL_PADDING
      + "s | %-" + OutputWrapper.XXL_PADDING + "s | %-" + OutputWrapper.L_PADDING + "s | %-" + OutputWrapper.M_PADDING
      + "s | %-" + OutputWrapper.S_PADDING + "s";
  public final static String firstPlayer = "First Player";
  public final static String yourTurn = "Your turn";
  public final static String tableBody1 = "\t#%-" + OutputWrapper.XXS_PADDING + "d| %-" + OutputWrapper.XS_PADDING + "s | %-";
  public final static String tableBody2 = "s | %-" + OutputWrapper.M_PADDING + "s | %-" + OutputWrapper.XL_PADDING
      + "d | %-" + OutputWrapper.XXL_PADDING + "d | %-" + OutputWrapper.L_PADDING + "s | %-" + OutputWrapper.M_PADDING
      + "d | %-" + OutputWrapper.S_PADDING + "d";
  public final static String line = "-";
  public final static String bookshelfError = "🛑 Wrong bookshelf coordinates!";
  public final static String sharedCardsHeader = "\t%-" + OutputWrapper.XL_PADDING + "s | %-";
  public final static String sharedCardHeaderDescription = "s |";
  public final static String sharedCardsBody = "\t#%-" + OutputWrapper.XL_PADDING + "d| %-";
  public final static String sharedCardsBodyDescription = "s |";
  public final static String idx = "Shared Card Idx.";
  public final static String description = "Card Description";
}
