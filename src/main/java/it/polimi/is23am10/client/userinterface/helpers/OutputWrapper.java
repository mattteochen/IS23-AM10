package it.polimi.is23am10.client.userinterface.helpers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import it.polimi.is23am10.client.userinterface.CommandLineInterface;
import it.polimi.is23am10.server.model.factory.GameFactory;
import it.polimi.is23am10.server.model.factory.exceptions.DuplicatePlayerNameException;
import it.polimi.is23am10.server.model.factory.exceptions.NullPlayerNamesException;
import it.polimi.is23am10.server.model.game.Game;
import it.polimi.is23am10.server.model.game.exceptions.FullGameException;
import it.polimi.is23am10.server.model.game.exceptions.InvalidMaxPlayerException;
import it.polimi.is23am10.server.model.game.exceptions.NullAssignedPatternException;
import it.polimi.is23am10.server.model.game.exceptions.NullMaxPlayerException;
import it.polimi.is23am10.server.model.game.exceptions.PlayerNotFoundException;
import it.polimi.is23am10.server.model.items.board.Board;
import it.polimi.is23am10.server.model.items.board.exceptions.InvalidNumOfPlayersException;
import it.polimi.is23am10.server.model.items.board.exceptions.NullNumOfPlayersException;
import it.polimi.is23am10.server.model.items.bookshelf.Bookshelf;
import it.polimi.is23am10.server.model.items.bookshelf.exceptions.BookshelfGridColIndexOutOfBoundsException;
import it.polimi.is23am10.server.model.items.bookshelf.exceptions.BookshelfGridRowIndexOutOfBoundsException;
import it.polimi.is23am10.server.model.items.card.exceptions.AlreadyInitiatedPatternException;
import it.polimi.is23am10.server.model.items.scoreblock.exceptions.NotValidScoreBlockValueException;
import it.polimi.is23am10.server.model.items.tile.Tile;
import it.polimi.is23am10.server.model.items.tile.Tile.TileType;
import it.polimi.is23am10.server.model.player.Player;
import it.polimi.is23am10.server.model.player.exceptions.NullPlayerBookshelfException;
import it.polimi.is23am10.server.model.player.exceptions.NullPlayerIdException;
import it.polimi.is23am10.server.model.player.exceptions.NullPlayerNameException;
import it.polimi.is23am10.server.model.player.exceptions.NullPlayerPrivateCardException;
import it.polimi.is23am10.server.model.player.exceptions.NullPlayerScoreBlocksException;
import it.polimi.is23am10.server.model.player.exceptions.NullPlayerScoreException;
import it.polimi.is23am10.server.network.messages.ChatMessage;
import it.polimi.is23am10.server.network.messages.ErrorMessage;
import it.polimi.is23am10.server.network.messages.ErrorMessage.ErrorSeverity;
import it.polimi.is23am10.server.network.virtualview.VirtualPlayer;
import it.polimi.is23am10.server.network.virtualview.VirtualView;
import it.polimi.is23am10.utils.exceptions.NullIndexValueException;

/**
 * An helper class with all the methods needed to properly print
 * CLI messages for client. It wraps System.out enriching it with colors.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public final class OutputWrapper {

  /**
   * Enum containing the output types.
   */
  public enum OutputLevel {
    DEBUG,
    INFO,
    CHAT,
    WARNING,
    ERROR,
    CRITICAL
  }

  private final Integer CLEAN_SCREEN_REPS = 100;

  // Variable padding sizes for string formatting.
  public static final Integer XXS_PADDING = 4;
  public static final Integer XS_PADDING = 6;
  public static final Integer S_PADDING = 11;
  public static final Integer M_PADDING = 12;
  public static final Integer L_PADDING = 14;
  public static final Integer XL_PADDING = 16;
  public static final Integer XXL_PADDING = 18;
  public static final Integer HUNDRED_PADDING = 100;
  public static final Integer MIN_PADDING_FOR_NAMES = 15;

  /**
   * A flag relative to the instance of {@link OutputWrapper}
   * set with constructor, used for showing or hiding debug lines.
   */
  private boolean showDebug = false;

  /**
   * A map that associates output levels to their string
   * template. A template uses ANSI codes to display colors.
   */
  private final Map<OutputLevel, String> debugTemplates = Map.of(
      OutputLevel.DEBUG, ANSICodes.BLUE + "🔎 %s" + ANSICodes.RESET,
      OutputLevel.INFO, "%s",
      OutputLevel.CHAT, ANSICodes.GREEN + "💬 %s" + ANSICodes.RESET,
      OutputLevel.WARNING, ANSICodes.YELLOW + "🔶 %s" + ANSICodes.RESET,
      OutputLevel.ERROR, ANSICodes.RED + "🔴 %s" + ANSICodes.RESET,
      OutputLevel.CRITICAL, ANSICodes.RED_BACKGROUND_BRIGHT + "⚫️ %s" + ANSICodes.RESET);

  /**
   * A map that associates TileType to their Java source code encoding.
   * 
   */
  private static final Map<TileType, String> emojiMap = Map.of(
      TileType.BOOK, "📔", // NOTEBOOK WITH DECORATIVE COVER
      TileType.CAT, "🐈", // CAT
      TileType.FRAME, "🟧", // ORANGE SQUARE ,
      TileType.GAME, "🎮", // VIDEO GAME,
      TileType.PLANT, "🍀", // FOUR LEAF CLOVER
      TileType.TROPHY, "🏆", // TROPHY
      TileType.EMPTY, "⬜" // WHITE LARGE SQUARE
  );

  /**
   * A map that associates a boolean referring to the online/offline player status
   * to green/red emoticon. *
   */
  private static final Map<Boolean, String> onlineOffline = Map.of(
      true, "🟢", // ONLINE
      false, "🔴" // OFFLINE
  );

  /**
   * Public constructor for OutputWrapper.
   *
   * @param showDebug instance-specific debug flag.
   */
  public OutputWrapper(boolean showDebug) {
    this.showDebug = showDebug;
  }

  /**
   * Prints a debug line on console.
   *
   * @param string     Debug string to display.
   * @param cleanFirst Flag to set if message should be preceded by a console
   *                   clean.
   */
  public void debug(String string, boolean cleanFirst) {
    if (showDebug) {
      printString(OutputLevel.DEBUG, string, cleanFirst);
    }
  }

  /**
   * Prints a info line on console.
   *
   * @param string     Info string to display.
   * @param cleanFirst Flag to set if message should be preceded by a console
   *                   clean.
   */
  public void info(String string, boolean cleanFirst) {
    printString(OutputLevel.INFO, string, cleanFirst);
  }

  /**
   * Private method to print repeated sequence of char.
   */
  private static String repeatString(String str, int count) {
    return String.join("", Collections.nCopies(count, str));
  }

  /**
   * Print the current game status on console.
   *
   * @param vw         The virtualView.
   * @param cleanFirst Flag to set if message should be preceded by a console
   *                   clean.
   * @throws BookshelfGridColIndexOutOfBoundsException
   * @throws BookshelfGridRowIndexOutOfBoundsException
   * @throws NullIndexValueException
   */
  public void show(VirtualView vw, boolean cleanFirst) {

    // Print game board.
    Board gameBoard = vw.getGameBoard();

    // Header.
    info(CLIStrings.boardStatus, false);

    // Index.
    info(CLIStrings.indexBoard, false);

    // Top padding.
    info(CLIStrings.topPaddingBoard, false);

    // Body.
    for (int i = 0; i < Board.BOARD_GRID_ROWS; i++) {
      StringBuilder row = new StringBuilder();
      row.append(CLIStrings.tabBlackSquare);
      for (int j = 0; j < Board.BOARD_GRID_COLS; j++) {
        Tile tile = gameBoard.getBoardGrid()[i][j];
        row.append(emojiMap.get(tile.getType()));
      }
      row.append(String.format(CLIStrings.verticalBoardIndex, (i + 1)));
      info(row.toString(), false);
    }

    // Bottom padding.
    info(CLIStrings.bottomPaddingBoard, false);

    List<VirtualPlayer> players = vw.getPlayers();

    int maxLength = players.stream()
        .mapToInt(p -> p.getPlayerName().length())
        .max()
        .orElse(MIN_PADDING_FOR_NAMES);

    // Print Player Status.
    StringBuilder playersStatus = new StringBuilder();
    // Header.
    playersStatus
        .append(String.format(CLIStrings.tableHeader1 + maxLength + CLIStrings.tableHeader2,
            CLIStrings.N, CLIStrings.status, CLIStrings.player, CLIStrings.role,
            CLIStrings.bookshelfPoints, CLIStrings.scoreBlockPoints, CLIStrings.privatePoints, CLIStrings.extraPoints,
            CLIStrings.totalScore))
        .append(CLIStrings.newLine)
        .append(
            String.format(CLIStrings.tableLines1 + maxLength + CLIStrings.tableLines2,
                repeatString(CLIStrings.line, XXS_PADDING), repeatString(CLIStrings.line, XS_PADDING),
                repeatString(CLIStrings.line, maxLength), repeatString(CLIStrings.line, M_PADDING),
                repeatString(CLIStrings.line, XL_PADDING), repeatString(CLIStrings.line, XXL_PADDING),
                repeatString(CLIStrings.line, L_PADDING), repeatString(CLIStrings.line, M_PADDING),
                repeatString(CLIStrings.line, S_PADDING)))
        .append(CLIStrings.newLine);

    // Body.
    int pos = 1;
    for (VirtualPlayer vp : players) {

      String status = onlineOffline.get(vp.getIsConnected());
      String player = vp.getPlayerName();
      String role = "";
      int totalScore = vp.getScore().getTotalScore();
      int extraPoints = vp.getScore().getExtraPoint();
      int bookshelfPoints = vp.getScore().getBookshelfPoints();
      int scoreBlocksPoint = vp.getScore().getScoreBlockPoints();
      int privatePoints = vp.getScore().getPrivatePoints();

      if (vw.getFirstPlayer().equals(vp)) {
        role = CLIStrings.firstPlayer;
      }
      if (vw.getActivePlayer().equals(vp)) {
        role = CLIStrings.yourTurn;
      }

      playersStatus
          .append(String.format(CLIStrings.tableBody1 + maxLength + CLIStrings.tableBody2,
              pos++, status, player, role, bookshelfPoints, scoreBlocksPoint, privatePoints, extraPoints, totalScore))
          .append(CLIStrings.newLine);
    }
    info(playersStatus.toString(), false);

    // Print Bookshelfs.
    // Name.
    StringBuilder name = new StringBuilder();
    name.append(CLIStrings.newLine); // New Line for esthetic purpose.
    pos = 1;
    for (VirtualPlayer vp : players) {
      name.append(String.format(CLIStrings.playerIdx, pos++));
    }
    info(name.toString(), false);

    // Index.
    StringBuilder playerIndex = new StringBuilder();
    playerIndex.append(CLIStrings.newLine); // New Line for esthetic purpose.
    for (VirtualPlayer vp : players) {
      playerIndex.append(CLIStrings.indexBookshelf);
    }
    info(playerIndex.toString(), false);

    // Top padding.
    StringBuilder topPadding = new StringBuilder();
    for (VirtualPlayer vp : players) {
      topPadding.append(CLIStrings.paddingBookshelf);
    }
    info(topPadding.toString(), false);

    // Body.
    for (int i = 0; i < Bookshelf.BOOKSHELF_ROWS; i++) {
      StringBuilder row = new StringBuilder();
      for (VirtualPlayer vp : players) {
        Bookshelf b = vp.getBookshelf();
        row.append(CLIStrings.tabBlackSquare);
        for (int j = 0; j < Bookshelf.BOOKSHELF_COLS; j++) {
          try {
            row.append(emojiMap.get(b.getBookshelfGridAt(i, j).getType()));
          } catch (BookshelfGridColIndexOutOfBoundsException
              | BookshelfGridRowIndexOutOfBoundsException
              | NullIndexValueException e) {
            error(CLIStrings.bookshelfError, false);
          }
        }
        row.append(CLIStrings.blackSquareTab);
      }
      info(row.toString(), false);
    }

    // Bottom padding.
    StringBuilder bottomPadding = new StringBuilder();
    for (VirtualPlayer vp : players) {
      bottomPadding.append(CLIStrings.paddingBookshelf);
    }
    bottomPadding.append(CLIStrings.doubleNewLine); // New Line for esthetic purpose.
    info(bottomPadding.toString(), false);

    // Print Shared Cards descriptions.
    StringBuilder sharedCards = new StringBuilder();

    maxLength = vw.getSharedCards().stream()
        .mapToInt(p -> CLIStrings.sharedPatternsDesc.get(p).length())
        .max()
        .orElse(HUNDRED_PADDING);

    // Header.
    sharedCards
        .append(String.format(CLIStrings.sharedCardsHeader + maxLength + CLIStrings.sharedCardHeaderDescription ,
            CLIStrings.idx, CLIStrings.description))
        .append(CLIStrings.newLine)
        .append(
            String
                .format(String.format(CLIStrings.sharedCardsHeader + maxLength + CLIStrings.sharedCardHeaderDescription,
                    repeatString(CLIStrings.line, XL_PADDING), repeatString(CLIStrings.line, maxLength))))
        .append(CLIStrings.newLine);

    // Body.
    for (Integer sc : vw.getSharedCards()) {
      sharedCards
          .append(String.format(CLIStrings.sharedCardsBody + maxLength + CLIStrings.sharedCardsBodyDescription, sc,
              CLIStrings.sharedPatternsDesc.get(sc)))
          .append(CLIStrings.newLine);
    }
    info(sharedCards.toString(), false);

    // Print Private Cards.
    StringBuilder privateCards = new StringBuilder();
    privateCards.append(CLIStrings.newLine); // New Line for esthetic purpose.
    pos = 1;
    // Header
    for (VirtualPlayer vp : players) { 
      privateCards.append(String.format(CLIStrings.privateCardIdx, pos++));
    }
    info(privateCards.toString(), false);

    // Index.
    StringBuilder bsIndex = new StringBuilder();
    bsIndex.append(CLIStrings.newLine); // New Line for esthetic purpose.
    for (VirtualPlayer vp : players) {
      bsIndex.append(CLIStrings.indexBookshelf);
    }
    info(bsIndex.toString(), false); 

    // Top padding.
    topPadding = new StringBuilder();
    for (VirtualPlayer vp : players) {
      topPadding.append(CLIStrings.paddingBookshelf);
    }
    info(topPadding.toString(), false);

    // Body.
    for (int i = 0; i < Bookshelf.BOOKSHELF_ROWS; i++) {
      StringBuilder row = new StringBuilder();
      for (VirtualPlayer vp : players) {
        Bookshelf b = PrivatePatternsHelper.getBookshelf(vp.getPrivateCardIndex());
        row.append(CLIStrings.tabBlackSquare);
        for (int j = 0; j < Bookshelf.BOOKSHELF_COLS; j++) {
          try {
            row.append(emojiMap.get(b.getBookshelfGridAt(i, j).getType()));
          } catch (BookshelfGridColIndexOutOfBoundsException
              | BookshelfGridRowIndexOutOfBoundsException
              | NullIndexValueException e) {
            error(CLIStrings.bookshelfError, false);
          }
        }
        row.append(CLIStrings.blackSquareTab);
      }
      info(row.toString(), false);
    }

    // Bottom padding.
    bottomPadding = new StringBuilder();
    for (VirtualPlayer vp : players) {
      bottomPadding.append(CLIStrings.paddingBookshelf);
    }
    bottomPadding.append(CLIStrings.doubleNewLine); // New Line for esthetic purpose.
    info(bottomPadding.toString(), false);
  }

  /**
   * Prints a chat message on console.
   *
   * @param string     Message string to display.
   * @param cleanFirst Flag to set if message should be preceded by a console
   *                   clean.
   */
  public void chat(String string, boolean cleanFirst) {
    printString(OutputLevel.CHAT, string, cleanFirst);
  }

  /**
   * Prints a warning line on console.
   *
   * @param string     Warning string to display.
   * @param cleanFirst Flag to set if message should be preceded by a console
   *                   clean.
   */
  public void warning(String string, boolean cleanFirst) {
    printString(OutputLevel.WARNING, string, cleanFirst);
  }

  /**
   * Prints a error line on console.
   *
   * @param string     Error string to display.
   * @param cleanFirst Flag to set if message should be preceded by a console
   *                   clean.
   */
  public void error(String string, boolean cleanFirst) {
    printString(OutputLevel.ERROR, string, cleanFirst);
  }

  /**
   * Prints a critical error line on console.
   *
   * @param string     Critical error string to display.
   * @param cleanFirst Flag to set if message should be preceded by a console
   *                   clean.
   */
  public void critical(String string, boolean cleanFirst) {
    printString(OutputLevel.CRITICAL, string, cleanFirst);
  }

  /**
   * Helper method to call to clean the console.
   * 
   */
  public void clean() {
    for (int i = 0; i < CLEAN_SCREEN_REPS; i++) {
      System.out.println();
    }
  }

  /**
   * Helper method to get current timestamp to show when in debug mode.
   *
   * @return Formatted timestamp as string.
   */
  private String getTimestamp() {
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    ZonedDateTime now = ZonedDateTime.now(ZoneId.systemDefault());
    return dtf.format(now);
  }

  /**
   * Helper method used by tests to retrieve the string to be printed
   * before actually printing it.
   *
   * @param level  {@link OutputLevel} of the message.
   * @param string The string of the message to display.
   * @return The formatted string ready to be printed.
   */
  public String getString(OutputLevel level, String string) {
    String template = debugTemplates.get(level);
    if (showDebug) {
      template = String.format("[%s] %s", getTimestamp(), template);
    }
    return String.format(template, string);
  }

  /**
   * Public method to print a string. Used from CLI.
   *
   * @param level      {@link OutputLevel} of the message.
   * @param string     The string of the message to display.
   * @param cleanFirst Flag that resets the console before print if true.
   */
  public void printString(OutputLevel level, String string, boolean cleanFirst) {
    if (cleanFirst) {
      clean();
    }
    System.out.println(getString(level, string));
  }

  /**
   * Setter for the debug flag.
   *
   * @param toSet Debug flag.
   */
  public void setDebug(boolean toSet) {
    showDebug = toSet;
  }

  // Runnable method to eyeball the various outputs.
  // TODO: Remove in prod / once all outputs are testable from actual client.
  public static void main(String[] args)
      throws NullPlayerNameException, IOException, NullMaxPlayerException,
      InvalidMaxPlayerException,
      NullPlayerIdException, NullPlayerBookshelfException,
      NullPlayerScoreException, NullPlayerPrivateCardException,
      NullPlayerScoreBlocksException, DuplicatePlayerNameException,
      AlreadyInitiatedPatternException,
      NullPlayerNamesException, InvalidNumOfPlayersException,
      NullNumOfPlayersException, NullAssignedPatternException,
      FullGameException, NotValidScoreBlockValueException, PlayerNotFoundException,
      NullPointerException {
    OutputWrapper ow = new OutputWrapper(true);

    ow.debug("THIS IS A DEBUG MESSAGE", false);
    ow.info("THIS IS A INFO MESSAGE", false);
    ow.chat("THIS IS A CHAT MESSAGE", false);
    ow.warning("THIS IS A WARNING!!", false);
    ow.error("THIS IS AN ERROR!!!", false);
    ow.critical("THIS IS A CRITICAL ERROR!", false);

    CommandLineInterface cli = new CommandLineInterface(false);

    Player p1 = new Player();
    p1.setPlayerName("Ned Flanders");

    cli.displaySplashScreen();
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    cli.displayChatMessage(new ChatMessage(p1, "Salve salvino"));
    String choice = reader.readLine();

    Game g = GameFactory.getNewGame("Romolo", 4);
    g.addPlayer("Numa Pompilio");
    g.addPlayer("Anco Marzio");
    g.addPlayer("Lucio Tarquinio Prisco");

    VirtualView vw1 = new VirtualView(GameFactory.getNewGame("bob", 2));
    VirtualView vw2 = new VirtualView(g);
    List<VirtualView> games = List.of(
        vw1, vw2);
    cli.displayAvailableGames(games);

    cli.displayError(new ErrorMessage("L'ultimo che finisce è scemo", ErrorSeverity.WARNING));

    String gameChoice = reader.readLine();

    cli.displayVirtualView(vw2);
  }
}
