package it.polimi.is23am10.client.userinterface.helpers;

import java.util.Map;

/**
 * An helper class containing all the strings to display to the user through UserInterface.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public final class CLIStrings {
  public static final String welcomeString =
      """
      ███    ███ ██    ██     ███████ ██   ██ ███████ ██      ███████ ██ ███████
      ████  ████  ██  ██      ██      ██   ██ ██      ██      ██      ██ ██
      ██ ████ ██   ████       ███████ ███████ █████   ██      █████   ██ █████
      ██  ██  ██    ██             ██ ██   ██ ██      ██      ██      ██ ██
      ██      ██    ██        ███████ ██   ██ ███████ ███████ ██      ██ ███████

      Politecnico di Milano - Software Engineering Project - Group IS23AM10

    """;
  public static final String joinOrCreateString =
      "Great! Please choose how to proceed. You can create a new game or join an existing one.";
  public static final String joinExisting =
      " - To join an existing game run the command `j [idx]` specifying by the index of the game.";
  public static final String createGame =
      " - To create a new game run the command `c [2-4]` specifying the number of players.";
  public static final String noGamesString =
      "No available game to join at the moment. Please create one.";
  public static final String insertPlayerNameString = "Insert your player name here:";
  public static final String listGamesString =
      "Here a list of the available games. Join one by typing the relative index.";
  public static final String disconnectedPlayers = " (%d disconnected)";
  public static final String availableGameString = "[%d] - %d/%d joined%s - GameId: %s";
  public static final String currentStateString = "Current state of the game:";
  public static final String gameOverString = "Game is over. Here the leaderboard:";
  public static final String playerScoreString = "%s - Score: %d";
  public static final String winnerString = "WINNER: %s";
  public static final String lastRoundString =
      "Attention! Someone already completed their Bookshelf: this is last round";
  public static final String nowPlaying = "Now playing: %s";
  public static final String moveTilesInviteString =
      "Now make your move specifying the `XY` of the tile you want to pick from board (max 3)"
          + " followed by the column index of the bookshelf you want to put the tile in.";
  public static final String moveTilesExampleString =
      "E.g. `move 12 22 25 A` moves the tiles with coordinates (1,2),(2,5) and (5,7) to the"
          + " bookshelf first three available spots in your bookshelf column 'A' in that order.";
  public static final String messageStringReceiver = "(%s -> You): %s";
  public static final String messageStringSender = "(You -> %s): %s";
  public static final String broadcastMessageString = "(%s -> All): %s";
  public static final String errorMessage = "(Server -> You): %s";
  public static final String broadcastErrorString = "(Server -> All): %s";
  public static final String bottomPaddingBoard = "\t⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛\n\n";
  public static final String topPaddingBoard =
      "\t⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛" + ANSICodes.RED_BOLD + "\sY\t" + ANSICodes.RESET;
  public static final String paddingBookshelf = "\t⬛⬛⬛⬛⬛⬛⬛\t";
  public static final String indexBoard =
      ANSICodes.RED_BOLD + "\t\sX" + ANSICodes.RESET + " 0 1 2 3 4 5 6 7 8\t";
  public static final String verticalBoardIndex = "⬛\s%d\t";
  public static final String indexBookshelf = "\t\s\sA B C D E\s\t";
  public static final String boardStatus = "\n\tGame board status:\t\n";
  public static final String playerIdx = "\tPlayer #%d\t";
  public static final String blackLargeSquare = "";
  public static final String tabBlackSquare = "\t⬛";
  public static final String blackSquareTab = "⬛\t";
  public static final String tab = "\t";
  public static final String newLine = "\n";
  public static final String doubleNewLine = "\n\n";
  public static final String tableHeader1 =
      "\t%-" + OutputWrapper.XXS_PADDING + "s | %-" + OutputWrapper.XS_PADDING + "s | %-";
  public static final String tableHeader2 =
      "s | %-"
          + OutputWrapper.M_PADDING
          + "s | %-"
          + OutputWrapper.XL_PADDING
          + "s | %-"
          + OutputWrapper.XXL_PADDING
          + "s | %-"
          + 14
          + "s | %-"
          + OutputWrapper.M_PADDING
          + "s | %-"
          + OutputWrapper.S_PADDING
          + "s ";
  public static final String N = "No.";
  public static final String status = "Status";
  public static final String player = "Player";
  public static final String role = "Role";
  public static final String scoreBlockPoints = "Score Block Points";
  public static final String bookshelfPoints = "Bookshelf Points";
  public static final String extraPoints = "Extra Points";
  public static final String totalScore = "Total Score";
  public static final String privatePoints = "Private Points";
  public static final String tableLines1 =
      "\t%-" + OutputWrapper.XXS_PADDING + "s | %-" + OutputWrapper.XS_PADDING + "s | %-";
  public static final String tableLines2 =
      "s | %-"
          + OutputWrapper.M_PADDING
          + "s | %-"
          + OutputWrapper.XL_PADDING
          + "s | %-"
          + OutputWrapper.XXL_PADDING
          + "s | %-"
          + OutputWrapper.L_PADDING
          + "s | %-"
          + OutputWrapper.M_PADDING
          + "s | %-"
          + OutputWrapper.S_PADDING
          + "s";
  public static final String firstPlayer = "First Player";
  public static final String yourTurn = "Your turn";
  public static final String tableBody1 =
      "\t#%-" + OutputWrapper.XXS_PADDING + "d| %-" + OutputWrapper.XS_PADDING + "s | %-";
  public static final String tableBody2 =
      "s | %-"
          + OutputWrapper.M_PADDING
          + "s | %-"
          + OutputWrapper.XL_PADDING
          + "d | %-"
          + OutputWrapper.XXL_PADDING
          + "d | %-"
          + OutputWrapper.L_PADDING
          + "s | %-"
          + OutputWrapper.M_PADDING
          + "d | %-"
          + OutputWrapper.S_PADDING
          + "s";
  public static final String line = "-";
  public static final String bookshelfError = "Wrong bookshelf coordinates!";
  public static final String inputError = "Can't read your commands. Please re-join.";
  public static final String sharedCardsHeader = "\t%-" + OutputWrapper.XL_PADDING + "s | %-";
  public static final String sharedCardHeaderDescription = "s ";
  public static final String sharedCardsBody = "\t#%-" + OutputWrapper.XL_PADDING + "d| %-";
  public static final String sharedCardsBodyDescription = "s ";
  public static final String idx = "Shared Card Idx.";
  public static final String description = "Card Description";
  public static final String privateCardIdx = "\tPrivate Card #%d\t";
  public static final Map<Integer, String> sharedPatternsDesc =
      Map.ofEntries(
          Map.entry(
              1,
              "Two groups of four tiles of the same type forming a 2x2 square shape. The tile type"
                  + " of the two squares has to be the same."),
          Map.entry(
              2,
              "At least two full columns (filled with six tiles), having tiles of all different"
                  + " types."),
          Map.entry(
              3,
              "Four separated groups made of four adjacent tiles of the same type. The tile's type"
                  + " of different groups can be different."),
          Map.entry(
              4,
              "Six separated groups made of two adjacent tiles of the same type. The tile type of"
                  + " different groups can be different."),
          Map.entry(
              5,
              "At least three full columns (filled with six tiles), having maximum three different"
                  + " tile types per column."),
          Map.entry(
              6,
              "At least two full rows (filled with five tiles), having tiles of all different"
                  + " types."),
          Map.entry(
              7,
              "At least four full rows (filled with five tiles), having maximum three different"
                  + " tile types per row."),
          Map.entry(8, "The four tiles at the corners of the bookshelf are of the same type."),
          Map.entry(
              9,
              "At least eight tiles of the same type. There are no restrictions concerning their"
                  + " positions."),
          Map.entry(10, "Five tiles of the same type, forming an X shape."),
          Map.entry(11, "Five tiles of the same type forming a diagonal."),
          Map.entry(
              12,
              "Five columns with ascending or descending height. Starting from the first or the"
                  + " last column, the next column has to have one tile more. The tile types are"
                  + " not considered."));
  public static final String waitingForPlayers =
      "Waiting for game to be full before starting, %d/%d joined. GameID: %s";
}
