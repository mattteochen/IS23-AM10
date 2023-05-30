package it.polimi.is23am10.client.userinterface.guifactory;

import it.polimi.is23am10.client.Client;
import it.polimi.is23am10.client.userinterface.guifactory.interfaces.ButtonCallBack;
import it.polimi.is23am10.client.userinterface.guifactory.interfaces.TextFieldCallBack;
import it.polimi.is23am10.client.userinterface.helpers.CLIStrings;
import it.polimi.is23am10.server.model.items.board.Board;
import it.polimi.is23am10.server.model.items.bookshelf.Bookshelf;
import it.polimi.is23am10.server.model.items.tile.Tile;
import it.polimi.is23am10.server.model.items.tile.Tile.TileType;
import it.polimi.is23am10.server.network.messages.AbstractMessage;
import it.polimi.is23am10.server.network.messages.ChatMessage;
import it.polimi.is23am10.server.network.messages.ErrorMessage;
import it.polimi.is23am10.server.network.messages.AbstractMessage.MessageType;
import it.polimi.is23am10.server.network.messages.ErrorMessage.ErrorSeverity;
import it.polimi.is23am10.server.network.virtualview.VirtualPlayer;
import it.polimi.is23am10.server.network.virtualview.VirtualView;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * GUI scene factory. Creates various GUI scenes for the application. Each scene
 * is created as a
 * static method in this class. The class also contains helper methods for
 * creating GUI components.
 *
 * <p>
 * The class uses JavaFX for UI rendering.
 *
 * <p>
 * The factory methods return JavaFX Scene objects that can be used to set the
 * application's
 * stage. The stages are stored in a static map for easy access.
 *
 * <p>
 * This class is not meant to be instantiated. All methods and members are
 * static.
 *
 * <p>
 * Example usage: ``` Scene splashScreenScene =
 * GuiFactory.getSplashScreenScene();
 * GuiFactory.stages.put(GuiFactory.SCENE.SPLASH_SCREEN, splashScreenScene);
 * primaryStage.setScene(splashScreenScene); primaryStage.show(); ```
 *
 * <p>
 * Note: This is a simplified example and assumes the necessary JavaFX setup has
 * been done.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public final class GuiFactory {

  private GuiFactory() {
  }

  /**
   * An enum representing different scenes that can be created by the factory. Add
   * more values to
   * represent additional scenes in the application.
   */
  public enum SCENE {
    SPLASH_SCREEN,
    ENTER_GAME_SELECTION,
    CREATE_GAME,
    JOIN_GAME,
    WAIT_GAME,
    GAME_SNAPSHOT,
    END_GAME
  }

  /** The default {@link Scene} width. */
  private static final int SCENE_WIDTH = 1280;

  /** The default {@link Scene} height. */
  private static final int SCENE_HEIGHT = 720;

  /** The {@link Stage} reference. */
  public static Stage mainStage;

  /** A mapping from a {@link SCENE} to a {@link Scene}. */
  public static Map<SCENE, Scene> stages = Collections.synchronizedMap(new HashMap<>());

  /**
   * Check if the current client is the active player.
   *
   * @param vv The {@link VirtualView}.
   * @return The requested flag.
   */
  protected static boolean isThisPlayerTurn(VirtualView vv) {
    return vv.getActivePlayer().getPlayerName().equals(getMyPlayerName());
  }

  /**
   * Helper method to retrieve the current player's name from PC.
   * 
   * @return Player name.
   */
  protected static String getMyPlayerName() {
    try {
      return Client.getPlayerConnector().getPlayer().getPlayerName();
    } catch (RemoteException e) {
      return "";
    }
  }

  /**
   * Creates a {@link BackgroundImage} with the specified image path and
   * brightness.
   *
   * @param path       The path to the image file.
   * @param brightness The brightness value to apply to the image (-1.0 to 1.0).
   * @return The created BackgroundImage object.
   */
  protected static BackgroundImage getBgImg(String path, double brightness) {

    Image img = null;
    try {
      img = new Image(path);
    } catch (Exception e) {
      // TODO: handle
    }

    ImageView imgView = new ImageView(img);
    ColorAdjust cadj = new ColorAdjust();
    cadj.setBrightness(brightness);
    imgView.setEffect(cadj);

    return new BackgroundImage(
        imgView.getImage(),
        BackgroundRepeat.NO_REPEAT,
        BackgroundRepeat.NO_REPEAT,
        BackgroundPosition.DEFAULT,
        new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false));
  }

  /**
   * Creates a {@link Label} with the specified content, font weight, and size.
   *
   * @param content The text content of the label.
   * @param weight  The font weight of the label.
   * @param size    The font size of the label.
   * @return The created Label object.
   */
  protected static Label getLabel(String content, FontWeight weight, int size, Color... colors) {
    Label l = new Label(content);
    l.setFont(Font.font("Arial", weight, size));
    if (colors != null && colors.length > 0) {
      l.setTextFill(colors[0]);
    }
    return l;
  }

  /**
   * Creates a {@link TextField} with the specified placeholder, width, height,
   * font weight, font
   * size, and callback.
   *
   * @param placeholder The placeholder text for the text field.
   * @param w           The desired width of the text field.
   * @param h           The desired width of the text field.
   * @param weight      The font weight of the text field.
   * @param size        The font size of the text field.
   * @param cb          The callback function to be executed when the text field
   *                    action is triggered.
   * @return The created TextField object.
   */
  protected static TextField getTextField(
      String placeholder, int w, int h, FontWeight weight, int size, TextFieldCallBack cb) {
    TextField t = new TextField(placeholder);
    t.setMaxWidth(w);
    t.setMinWidth(h);
    t.setFont(Font.font("Arial", weight, size));
    if (cb != null) {
      t.setOnAction(event -> cb.call(t.getText()));
    }
    return t;
  }

  /**
   * Creates a {@link Button} with the specified content, callback function, and
   * optional text
   * fields.
   *
   * @param content The text content of the button.
   * @param cb      The callback function to be executed when the button is
   *                clicked.
   * @param tfs     Optional text fields to be passed as arguments to the callback
   *                function.
   * @return The created Button object.
   */
  protected static Button getButton(String content, ButtonCallBack cb, TextField... tfs) {
    Button b = new Button(content);
    if (cb != null) {
      b.setOnAction(event -> cb.call(tfs));
    }
    return b;
  }

  /**
   * Method that creates and shows the alert modal for error messages
   * 
   * @param sp  The stack pane.
   * @param msg Message to be displayed.
   */
  public static void getErrorMessage(StackPane sp, ErrorMessage msg) {
    Alert alert = msg.getErrorSeverity() == ErrorSeverity.WARNING ? new Alert(AlertType.WARNING)
        : new Alert(AlertType.ERROR);
    alert.setTitle("Error");
    alert.setHeaderText("An error occurred");
    alert.setContentText(msg.getMessage());

    // Display the dialog modally
    alert.showAndWait();
  }

  /**
   * Executes a callback inside of JavaFX thread. Mandatory for
   * any scene-change related task.
   *
   * @param r The callback to be executed.
   */
  public static void executeOnJavaFX(Runnable r) {
    Platform.runLater(r);
  }

  /**
   * Creates the splash screen scene. The splash screen scene consists of a stack
   * pane with a
   * background and an input name widget. The background image is retrieved from
   * the
   * SplashScreenFactory class. The input name widget contains a label, a text
   * field, and a confirm
   * button. The confirm button triggers the CallBack.confirmNameCallBack.
   *
   * @return The created splash screen scene.
   */
  public static Scene getSplashScreenScene() {
    StackPane root = new StackPane();

    root.setBackground(new Background(SplashScreenFactory.getSplashScreenBg()));
    root.getChildren().add(SplashScreenFactory.getInputNameWidget());

    return new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
  }

  /**
   * Creates the enter game selection scene. The enter game selection scene
   * consists of a stack pane
   * with a background and a selection widget. The background image is retrieved
   * from the
   * EnterGameSelectionScreenFactory class. The selection widget contains a label
   * and two buttons
   * (create new game and join game).
   *
   * @return The created enter game selection scene.
   */
  public static Scene getEnterGameSelectionScene() {
    StackPane root = new StackPane();

    root.setBackground(new Background(EnterGameSelectionScreenFactory.getSelectionBg()));
    root.getChildren().add(EnterGameSelectionScreenFactory.getSelectionWidget());

    return new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
  }

  /**
   * Creates and returns a new JavaFX Scene for the create new game selection
   * screen.
   *
   * @return The JavaFX Scene for the create new game selection screen.
   */
  public static Scene getCreateNewGameSelectionScene() {
    StackPane root = new StackPane();

    root.setBackground(new Background(NewGameFactory.getBg()));
    root.getChildren().add(NewGameFactory.getCreateGameWidget());

    return new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
  }

  /**
   * Creates and returns a new JavaFX Scene for the join game selection screen.
   *
   * @return The JavaFX Scene for the join game selection screen.
   */
  public static Scene getCreateJoinScene(List<VirtualView> vvs) {
    StackPane root = new StackPane();

    root.setBackground(new Background(JoinGameFactory.getBg()));
    root.getChildren().add(JoinGameFactory.getJoinGameWidget(vvs));

    return new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
  }

  /**
   * Creates and returns a new JavaFX Scene for the wait game screen.
   *
   * @return The JavaFX Scene for the join game wait screen.
   */
  public static Scene getWaitGameScene() {
    StackPane root = new StackPane();

    root.setBackground(new Background(WaitGameFactory.getBg()));
    root.getChildren().add(WaitGameFactory.getWaitingWidget());

    return new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
  }

  /**
   * Creates and returns a new JavaFX Scene for the game snapshot.
   *
   * @return The JavaFX Scene for the game snapshot.
   */
  public static Scene getGameSnapshotScene(VirtualView vv) {
    StackPane root = new StackPane();

    root.setBackground(new Background(GameSnapshotFactory.getBg()));
    root.getChildren().add(GameSnapshotFactory.getGameWidget(vv));

    return new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
  }

  /**
   * Creates and returns a new JavaFX Scene for the game snapshot.
   *
   * @return The JavaFX Scene for the game snapshot.
   */
  public static Scene getEndGameScene(VirtualView vv) {
    StackPane root = new StackPane();

    root.setBackground(new Background(EndGameSceneFactory.getBg()));
    root.getChildren().add(EndGameSceneFactory.getEndWidget(vv));

    return new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
  }

  /**
   * Factory class for creating the splash screen UI components. Contains methods
   * to create the
   * background image, input name label, input name text field, and the entire
   * input name widget.
   *
   * <p>
   * This class is not meant to be instantiated. All methods and members are
   * static.
   */
  class SplashScreenFactory {
    private static final String SPLASH_SCREEN_IMG_PATH = "file:src/main/resources/assets/in_lobby.png";

    protected static TextField textField;

    /**
     * Creates a {@link BackgroundImage} for the splash screen using the specified
     * image path and
     * brightness.
     *
     * @return The created BackgroundImage object for the splash screen.
     */
    protected static BackgroundImage getSplashScreenBg() {
      return getBgImg(SPLASH_SCREEN_IMG_PATH, -0.5);
    }

    /**
     * Creates a {@link Label} for the input name with the specified content, font
     * weight, and size.
     *
     * @return The created Label object for the input name.
     */
    protected static Label getInputNameLabel() {
      return getLabel("Please choose your name", FontWeight.BOLD, 40);
    }

    /**
     * Creates a {@link TextField} for entering the name with the specified
     * placeholder, width,
     * height, font weight, font size, and callback.
     *
     * @return The created TextField object for entering the name.
     */
    protected static TextField getInputNameTextField() {
      textField = getTextField("Type here", 300, 300, FontWeight.NORMAL, 20, null);
      return textField;
    }

    /**
     * Creates a {@link VBox} that contains the input name label, input name text
     * field, and confirm
     * button.
     *
     * @return The created VBox widget for the input name.
     */
    protected static VBox getInputNameWidget() {
      VBox box = new VBox();
      box.setAlignment(Pos.CENTER);
      box.setSpacing(10);
      box.getChildren()
          .addAll(
              getInputNameLabel(),
              getInputNameTextField(),
              getButton("Confirm", CallBack.confirmNameCallBack, textField));
      return box;
    }
  }

  /**
   * GUI end game screen factory. Creates GUI components for the enter game end
   * screen.
   */
  class EndGameSceneFactory {
    private static final String SELECTION_SCREEN_IMG_PATH = "file:src/main/resources/assets/in_game.png";

    /**
     * Retrieves the background image for the end game selection screen.
     *
     * @return The background image for the end game selection screen.
     */
    protected static BackgroundImage getBg() {
      return getBgImg(SELECTION_SCREEN_IMG_PATH, -0.5);
    }

    /**
     * Retrieves the label for the enter game selection screen.
     *
     * @return The label for the enter game selection screen.
     */
    protected static Label getEndGameLabel() {
      return getLabel(CLIStrings.gameOverString, FontWeight.BOLD, 40);
    }

    /**
     * Retrieves the scores leaderboard.
     *
     * @return The scores box UI component.
     */
    protected static VBox getScores(VirtualView vv) {
      VBox root = new VBox();
      root.setAlignment(Pos.CENTER);
      root.setSpacing(10);
      vv.getPlayers().stream()
          .sorted(
              Comparator.comparing(p -> p.getScore().getVisibleScore(), Comparator.reverseOrder()))
          .forEach(
              p -> root.getChildren()
                  .add(
                      getLabel(
                          String.format(
                              CLIStrings.playerScoreString,
                              p.getPlayerName(),
                              p.getScore().getTotalScore()),
                          FontWeight.BOLD,
                          30)));
      return root;
    }

    /**
     * Retrieves the widget (container) for the end game screen.
     *
     * @return The widget (container) for the end game screen.
     */
    protected static VBox getEndWidget(VirtualView vv) {
      VBox root = new VBox();
      root.setAlignment(Pos.CENTER);
      root.setSpacing(20);
      root.getChildren().addAll(getEndGameLabel(), getScores(vv));
      return root;
    }
  }

  /**
   * GUI enter game selection screen factory. Creates GUI components for the enter
   * game selection
   * screen.
   */
  class EnterGameSelectionScreenFactory {
    private static final String SELECTION_SCREEN_IMG_PATH = "file:src/main/resources/assets/in_lobby.png";

    /**
     * Retrieves the background image for the enter game selection screen.
     *
     * @return The background image for the enter game selection screen.
     */
    protected static BackgroundImage getSelectionBg() {
      return getBgImg(SELECTION_SCREEN_IMG_PATH, -0.5);
    }

    /**
     * Retrieves the label for the enter game selection screen.
     *
     * @return The label for the enter game selection screen.
     */
    protected static Label getSelectionOptionLabel() {
      return getLabel("Please choose your one option", FontWeight.BOLD, 40);
    }

    /**
     * Retrieves the widget (container) for the enter game selection screen.
     *
     * @return The widget (container) for the enter game selection screen.
     */
    protected static VBox getSelectionWidget() {
      HBox hbox = new HBox();
      hbox.setAlignment(Pos.CENTER);
      hbox.setSpacing(10);
      hbox.getChildren()
          .addAll(
              getButton("Create new game", CallBack.createNewGameCallBack),
              getButton("Join game", CallBack.joinGameCallBack));
      VBox vbox = new VBox();
      vbox.setAlignment(Pos.CENTER);
      vbox.setSpacing(10);
      vbox.getChildren().addAll(getSelectionOptionLabel(), hbox);
      return vbox;
    }
  }

  /**
   * The NewGameFactory class provides static methods to create various UI
   * components for setting up
   * a new game.
   */
  class NewGameFactory {
    private static final String SPLASH_SCREEN_IMG_PATH = "file:src/main/resources/assets/in_lobby.png";

    /** A map of text fields associated with player numbers. */
    protected static Map<String, TextField> textFields = Map.of(
        "2", new TextField(),
        "3", new TextField(),
        "4", new TextField());

    /**
     * Retrieves the background image.
     *
     * @return The background image.
     */
    protected static BackgroundImage getBg() {
      return getBgImg(SPLASH_SCREEN_IMG_PATH, 0.5);
    }

    /**
     * Creates a label for inputting the player number.
     *
     * @return The label for inputting the player number.
     */
    protected static Label getInputPlayerNumberLabel() {
      return getLabel("Choose max players number", FontWeight.BOLD, 40);
    }

    /**
     * Creates a button for selecting the player number.
     *
     * @param n The player number to set on the button.
     * @return The button for selecting the player number.
     */
    protected static Button getPlayerNumButton(String n) {
      TextField tf = textFields.get(n);
      tf.setText(n);
      return getButton(n, CallBack.confirmPlayerNumCallBack, tf);
    }

    /**
     * Creates a VBox container with input widgets for setting up a new game.
     *
     * @return The VBox container with input widgets for setting up a new game.
     */
    protected static VBox getCreateGameWidget() {
      HBox hbox = new HBox();
      hbox.setAlignment(Pos.CENTER);
      hbox.setSpacing(30);
      hbox.getChildren()
          .addAll(getPlayerNumButton("2"), getPlayerNumButton("3"), getPlayerNumButton("4"));
      VBox vbox = new VBox();
      vbox.setAlignment(Pos.CENTER);
      vbox.setSpacing(10);
      vbox.getChildren().addAll(getInputPlayerNumberLabel(), hbox);
      return vbox;
    }
  }

  /**
   * The JoinGameFactory class provides static methods to create various UI
   * components for setting
   * up a game to join.
   */
  class JoinGameFactory {
    private static final String SPLASH_SCREEN_IMG_PATH = "file:src/main/resources/assets/in_lobby.png";

    /**
     * Retrieves the background image.
     *
     * @return The background image.
     */
    protected static BackgroundImage getBg() {
      return getBgImg(SPLASH_SCREEN_IMG_PATH, 0.5);
    }

    /**
     * Creates a label for inputting the server selection.
     *
     * @return The label for inputting the game server selection.
     */
    protected static Label getInputJoinGameLabel() {
      return getLabel("Select a game server", FontWeight.BOLD, 40);
    }

    /**
     * Creates a button for selecting the game id.
     *
     * @param index The game server index.
     * @param vv    The game server virtual view.
     * @return The button for selecting the game server.
     */
    protected static Button getGameIdButton(String index, VirtualView vv) {
      String id = vv.getGameId().toString();
      TextField tf = new TextField(id);
      tf.setText(index);
      return getButton(id, CallBack.confirmJoinGameCallBack, tf);
    }

    /**
     * Creates a VBox container with input widgets for setting up game server
     * selection.
     *
     * @return The VBox container with input widgets for choosing the server to
     *         join.
     */
    protected static VBox getJoinGameWidget(List<VirtualView> vvs) {
      List<Button> buttons = new ArrayList<>();
      for (int i = 0; i < vvs.size(); i++) {
        buttons.add(getGameIdButton(String.valueOf(i), vvs.get(i)));
      }

      ListView<Button> listView = new ListView<>();

      HBox hbox = new HBox();
      hbox.setAlignment(Pos.CENTER);
      hbox.setSpacing(30);
      if (buttons.size() > 0) {
        listView.setItems(FXCollections.observableArrayList(buttons));
        listView.setPrefWidth(400);
      }
      hbox.getChildren()
          .add(
              buttons.size() > 0
                  ? listView
                  : getLabel("No available servers!", FontWeight.BOLD, 30, Color.RED));
      VBox vbox = new VBox();
      vbox.setAlignment(Pos.CENTER);
      vbox.setSpacing(10);
      vbox.getChildren().addAll(getInputJoinGameLabel(), hbox);
      return vbox;
    }
  }

  /**
   * GUI waiting for a game to start. Creates GUI components for the waiting
   * phase.
   */
  class WaitGameFactory {
    private static final String SELECTION_SCREEN_IMG_PATH = "file:src/main/resources/assets/in_wait.png";

    /**
     * Retrieves the background image for the waiting game.
     *
     * @return The background image for the waiting game.
     */
    protected static BackgroundImage getBg() {
      return getBgImg(SELECTION_SCREEN_IMG_PATH, 0.5);
    }

    /**
     * Retrieves the label for the waiting game.
     *
     * @return The label for the waiting game.
     */
    protected static Label getWaitingLabel() {
      return getLabel("Waiting for other players...", FontWeight.BOLD, 40);
    }

    /**
     * Retrieves the widget (container) for the waiting game.
     *
     * @return The widget (container) for the waiting game.
     */
    protected static VBox getWaitingWidget() {
      VBox vbox = new VBox();
      vbox.setAlignment(Pos.CENTER);
      vbox.setSpacing(10);
      vbox.getChildren().addAll(getWaitingLabel());
      return vbox;
    }
  }

  /**
   * GUI waiting for a game snapshot. Creates GUI components for the game
   * snapshot.
   */
  public class GameSnapshotFactory {
    private static final String SELECTION_SCREEN_IMG_PATH = "file:src/main/resources/assets/in_game.png";
    private static final String FRAME_IMG = "file:src/main/resources/assets/frame.png";
    private static final String CAT_IMG = "file:src/main/resources/assets/cat.png";
    private static final String BOOK_IMG = "file:src/main/resources/assets/book.png";
    private static final String GAME_IMG = "file:src/main/resources/assets/game.png";
    private static final String PLANT_IMG = "file:src/main/resources/assets/plant.png";
    private static final String TROPHY_IMG = "file:src/main/resources/assets/trophy.png";
    private static final String EMPTY_IMG = "file:src/main/resources/assets/empty.png";
    private static final String PRIVATE_ONE = "file:src/main/resources/assets/p1.png";
    private static final String PRIVATE_TWO = "file:src/main/resources/assets/p2.png";
    private static final String PRIVATE_THREE = "file:src/main/resources/assets/p3.png";
    private static final String PRIVATE_FOUR = "file:src/main/resources/assets/p4.png";
    private static final String PRIVATE_FIVE = "file:src/main/resources/assets/p5.png";
    private static final String PRIVATE_SIX = "file:src/main/resources/assets/p6.png";
    private static final String PRIVATE_SEVEN = "file:src/main/resources/assets/p7.png";
    private static final String PRIVATE_EIGHT = "file:src/main/resources/assets/p8.png";
    private static final String PRIVATE_NINE = "file:src/main/resources/assets/p9.png";
    private static final String PRIVATE_TEN = "file:src/main/resources/assets/p10.png";
    private static final String PRIVATE_ELEVEN = "file:src/main/resources/assets/p11.png";
    private static final String PRIVATE_TWELVE = "file:src/main/resources/assets/p12.png";
    private static final String COMMON_ONE = "file:src/main/resources/assets/c1.png";
    private static final String COMMON_TWO = "file:src/main/resources/assets/c2.png";
    private static final String COMMON_THREE = "file:src/main/resources/assets/c3.png";
    private static final String COMMON_FOUR = "file:src/main/resources/assets/c4.png";
    private static final String COMMON_FIVE = "file:src/main/resources/assets/c5.png";
    private static final String COMMON_SIX = "file:src/main/resources/assets/c6.png";
    private static final String COMMON_SEVEN = "file:src/main/resources/assets/c7.png";
    private static final String COMMON_EIGHT = "file:src/main/resources/assets/c8.png";
    private static final String COMMON_NINE = "file:src/main/resources/assets/c9.png";
    private static final String COMMON_TEN = "file:src/main/resources/assets/c10.png";
    private static final String COMMON_ELEVEN = "file:src/main/resources/assets/c11.png";
    private static final String COMMON_TWELVE = "file:src/main/resources/assets/c12.png";

    /** Associate a {@link TileType} to a image path. */
    private static Map<TileType, String> imagesMap = Map.of(
        TileType.FRAME, FRAME_IMG,
        TileType.CAT, CAT_IMG,
        TileType.GAME, GAME_IMG,
        TileType.BOOK, BOOK_IMG,
        TileType.PLANT, PLANT_IMG,
        TileType.TROPHY, TROPHY_IMG,
        TileType.EMPTY, EMPTY_IMG);

    /** Associate a private card index to its image path. */
    protected static Map<Integer, String> privateCardMap = new HashMap<>() {
      {
        put(1, PRIVATE_ONE);
        put(2, PRIVATE_TWO);
        put(3, PRIVATE_THREE);
        put(4, PRIVATE_FOUR);
        put(5, PRIVATE_FIVE);
        put(6, PRIVATE_SIX);
        put(7, PRIVATE_SEVEN);
        put(8, PRIVATE_EIGHT);
        put(9, PRIVATE_NINE);
        put(10, PRIVATE_TEN);
        put(11, PRIVATE_ELEVEN);
        put(12, PRIVATE_TWELVE);
      }
    };

    /** Associate a common goal card index to its image path. */
    protected static Map<Integer, String> commonCardMap = new HashMap<>() {
      {
        put(1, COMMON_ONE);
        put(2, COMMON_TWO);
        put(3, COMMON_THREE);
        put(4, COMMON_FOUR);
        put(5, COMMON_FIVE);
        put(6, COMMON_SIX);
        put(7, COMMON_SEVEN);
        put(8, COMMON_EIGHT);
        put(9, COMMON_NINE);
        put(10, COMMON_TEN);
        put(11, COMMON_ELEVEN);
        put(12, COMMON_TWELVE);
      }
    };

    /** Chat input text field. */
    protected static TextField textField;

    /** Chat messages history. */
    protected static ListView<String> chatMessagesListView;

    /**
     * Retrieves the background image for the waiting game.
     *
     * @return The background image for the waiting game.
     */
    protected static BackgroundImage getBg() {
      return getBgImg(SELECTION_SCREEN_IMG_PATH, 0.5);
    }

    /**
     * Retrieves the game board {@link GridPane}.
     *
     * @param vv The virtual view.
     * @return The game board {@link GridPane}.
     */
    private static GridPane getGameBoard(VirtualView vv) {
      GridPane gp = new GridPane();
      gp.setHgap(5);
      gp.setVgap(5);
      gp.setPadding(new Insets(20));

      Tile[][] b = vv.getGameBoard().getBoardGrid();
      for (int i = 0; i < Board.BOARD_GRID_ROWS; i++) {
        for (int j = 0; j < Board.BOARD_GRID_COLS; j++) {
          ImageView imgView = new ImageView(new Image(imagesMap.get(b[i][j].getType())));
          imgView.setFitWidth(55);
          imgView.setFitHeight(55);
          final int row = i;
          final int col = j;
          ColorAdjust caj = new ColorAdjust();
          caj.setBrightness(0);
          imgView.setEffect(caj);
          imgView.setOnMouseClicked(
              event -> {
                if (!isThisPlayerTurn(vv) || b[row][col].isEmpty()) {
                  return;
                }
                if (caj.getBrightness() == 0) {
                  UserMoveBuilder.appendTile(row, col);
                } else {
                  UserMoveBuilder.removeTile(row, col);
                }
                caj.setBrightness(caj.getBrightness() == 0 ? caj.getBrightness() - 0.3 : 0);
              });
          gp.add(imgView, j + 1, i + 1);
        }
      }
      return gp;
    }

    /**
     * Retrieves the bookshelf board {@link Bookshelf}.
     *
     * @param bs The {@link Bookshelf}.
     * @return The bookshelf {@link GridPane}.
     */
    private static GridPane getBookShelfGridPane(Bookshelf bs) {
      GridPane gp = new GridPane();
      gp.setHgap(5);
      gp.setVgap(5);
      gp.setPadding(new Insets(10));

      Tile[][] b = bs.getBookshelfGrid();
      for (int i = 0; i < b.length; i++) {
        for (int j = 0; j < b[i].length; j++) {
          ImageView imgView = new ImageView(new Image(imagesMap.get(b[i][j].getType())));
          imgView.setFitWidth(40);
          imgView.setFitHeight(40);

          // buil the command
          final int col = j;
          imgView.setOnMouseClicked(
              event -> {
                UserMoveBuilder.appendDestCol(col);
                CallBack.moveTileCallBack.call(UserMoveBuilder.getMove());
                UserMoveBuilder.clear();
              });
          gp.add(imgView, j, i);
        }
      }
      return gp;
    }

    /**
     * Retrieves the bookshelf boards {@link Bookshelf} of all players with the
     * player name.
     *
     * @param vv The virtual view.
     * @return A list of bookshelf {@link VBox}.
     */
    private static GridPane getPlayerBookShelf(VirtualView vv) {

      for (VirtualPlayer vp : vv.getPlayers()) {
        if (vp.getPlayerName().equals(getMyPlayerName())) {
          return getBookShelfGridPane(vp.getBookshelf());
        }
      }
      return null;
    }

    /**
     * Retrieves the private card image.
     *
     * @param vv The virtual view.
     * @return The private card image.
     */
    private static ImageView getPrivateCard(VirtualView vv) {
      try {
        for (VirtualPlayer vp : vv.getPlayers()) {
          if (vp.getPlayerName().equals(Client.getPlayerConnector().getPlayer().getPlayerName())) {
            Image img = new Image(privateCardMap.get(vp.getPrivateCardIndex()));
            ImageView imgView = new ImageView(img);
            imgView.setFitWidth(137 * 1.2);
            imgView.setFitHeight(207.9 * 1.2);
            return imgView;
          }
        }
      } catch (RemoteException e) {
      }
      return null;
    }

    /**
     * Retrieves the common goal card images.
     *
     * @param vv The virtual view.
     * @return A list of common goal images.
     */
    private static List<ImageView> getCommonGoalCard(VirtualView vv) {
      List<ImageView> imgs = new ArrayList<>();
      imgs.add(new ImageView(new Image(commonCardMap.get(vv.getSharedCards().get(0)))));
      imgs.add(new ImageView(new Image(commonCardMap.get(vv.getSharedCards().get(1)))));
      for (ImageView i : imgs) {
        i.setFitWidth(138.5 * 1.4);
        i.setFitHeight(91.3 * 1.4);
      }
      return imgs;
    }

    /**
     * Retrieves the game score board and other relevant informations.
     *
     * @param vv The virtual view.
     * @return The score board.
     */
    protected static HBox getLeaderboard(VirtualView vv) {
      VBox status = new VBox();
      status.setAlignment(Pos.CENTER);
      status.setSpacing(5);
      status.getChildren().add(getLabel("Status", FontWeight.BLACK, 15));
      VBox players = new VBox();
      players.setAlignment(Pos.CENTER);
      players.setSpacing(5);
      players.getChildren().add(getLabel("Players", FontWeight.BOLD, 15));
      VBox firstPlayer = new VBox();
      firstPlayer.setAlignment(Pos.CENTER);
      firstPlayer.setSpacing(5);
      firstPlayer
          .getChildren()
          .addAll(
              getLabel("First player", FontWeight.BOLD, 15),
              getLabel(vv.getFirstPlayer().getPlayerName(), FontWeight.BOLD, 13));
      VBox roles = new VBox();
      roles.setAlignment(Pos.CENTER);
      roles.setSpacing(5);
      roles
          .getChildren()
          .addAll(
              getLabel("Active turn", FontWeight.BOLD, 15),
              getLabel(vv.getActivePlayer().getPlayerName(), FontWeight.BOLD, 13));
      VBox bsPoints = new VBox();
      bsPoints.setAlignment(Pos.CENTER);
      bsPoints.setSpacing(5);
      bsPoints.getChildren().add(getLabel("BookShelf points", FontWeight.BOLD, 15));
      VBox scoreBlocksPoints = new VBox();
      scoreBlocksPoints.setAlignment(Pos.CENTER);
      scoreBlocksPoints.setSpacing(5);
      scoreBlocksPoints.getChildren().add(getLabel("Score blocks points", FontWeight.BOLD, 15));
      VBox pvtPoints = new VBox();
      pvtPoints.setAlignment(Pos.CENTER);
      pvtPoints.setSpacing(5);
      pvtPoints.getChildren().add(getLabel("Private points", FontWeight.BOLD, 15));
      VBox extraPoints = new VBox();
      extraPoints.setAlignment(Pos.CENTER);
      extraPoints.setSpacing(5);
      extraPoints.getChildren().add(getLabel("Extra points", FontWeight.BOLD, 15));
      VBox totalScore = new VBox();
      totalScore.setAlignment(Pos.CENTER);
      totalScore.setSpacing(5);
      totalScore.getChildren().add(getLabel("Total score", FontWeight.BOLD, 15));
      for (VirtualPlayer vp : vv.getPlayers()) {
        status
            .getChildren()
            .add(
                getLabel(
                    vp.getIsConnected() ? "ONLINE" : "OFFLINE",
                    FontWeight.BOLD,
                    13,
                    vp.getIsConnected() ? Color.GREEN : Color.RED));
        players.getChildren().add(getLabel(vp.getPlayerName(), FontWeight.BOLD, 13));
        bsPoints
            .getChildren()
            .add(getLabel(vp.getScore().getBookshelfPoints().toString(), FontWeight.BOLD, 13));
        scoreBlocksPoints
            .getChildren()
            .add(getLabel(vp.getScore().getScoreBlockPoints().toString(), FontWeight.BOLD, 13));
        pvtPoints
            .getChildren()
            .add(
                getLabel(
                    vp.getScore().getPrivatePoints() == -1
                        ? "?"
                        : vp.getScore().getPrivatePoints().toString(),
                    FontWeight.BOLD,
                    13));
        extraPoints
            .getChildren()
            .add(getLabel(vp.getScore().getExtraPoint().toString(), FontWeight.BOLD, 13));
        totalScore
            .getChildren()
            .add(getLabel(vp.getScore().getStringTotalScore(), FontWeight.BOLD, 13));
      }
      HBox root = new HBox();
      root.setSpacing(30);
      root.setAlignment(Pos.CENTER);
      root.getChildren()
          .addAll(
              status,
              players,
              bsPoints,
              scoreBlocksPoints,
              pvtPoints,
              extraPoints,
              totalScore,
              roles,
              firstPlayer);
      return root;
    }

    /**
     * Method used to retrieve the input text field to send messages.
     * 
     * @return the textfield.
     */
    protected static TextField getChatTextField() {
      textField = getTextField("Enter your message here", 180, 100, FontWeight.NORMAL, 12, null);
      return textField;
    }

    /**
     * Method used to retrieve the horizontal component of input textfield and send
     * button.
     * 
     * @return send message box.
     */
    protected static HBox getSendMessageBox() {
      HBox hbox = new HBox();
      hbox.setSpacing(5);
      hbox.setAlignment(Pos.CENTER_RIGHT);
      hbox.getChildren().addAll(
          getChatTextField(),
          getButton("Send", CallBack.sendMessageCallBack, textField));
      return hbox;
    };

    /**
     * Method used to retrieve the chat history component.
     * 
     * @return chat history component.
     */
    protected static VBox getChatHistory() {
      VBox chatHistory = new VBox();
      chatMessagesListView = new ListView<String>();
      chatMessagesListView.setStyle("-fx-control-inner-background: #B0721E");
      chatMessagesListView.setPrefWidth(300);

      // this 'magheggio' is needed to have a message that doesn't overflow in chat
      chatMessagesListView.setCellFactory(list -> new ListCell<String>() {
        private final Text textNode = new Text();
        {
          textNode.wrappingWidthProperty().bind(chatMessagesListView.widthProperty().subtract(20));
        }

        @Override
        protected void updateItem(String item, boolean empty) {
          super.updateItem(item, empty);

          if (empty || item == null) {
            setGraphic(null);
          } else {
            textNode.setText(item);
            setGraphic(textNode);
          }
        }
      });

      chatHistory.setAlignment(Pos.CENTER);
      chatHistory.getChildren().addAll(
          chatMessagesListView);
      return chatHistory;
    }

    /**
     * Method used to retrieve the whole chat component.
     * 
     * @return chat component.
     */
    protected static VBox getChat() {
      VBox chat = new VBox();
      chat.setSpacing(10);
      chat.setAlignment(Pos.BOTTOM_RIGHT);
      chat.getChildren().addAll(
          getChatHistory(),
          getSendMessageBox());

      return chat;
    }

    /**
     * Private method used to retrieve a VBox containing the buttons
     * to navigate to all bookshelves.
     */
    private static VBox getBSButtons(VirtualView vv) {
      VBox bsButtons = new VBox();
      bsButtons.setSpacing(5);
      bsButtons.setAlignment(Pos.CENTER);
      for (VirtualPlayer vp : vv.getPlayers()) {
        bsButtons.getChildren().add(
            getButton(String.format("Show %s's bookshelf", vp.getPlayerName()), CallBack.switchToPlayerBookshelf,
                new TextField(vp.getBookshelf().getBookshelfString()), new TextField(vp.getPlayerName())));
      }
      return bsButtons;
    } 

    /**
     * Retrieves the widget (container) for game snapshot.
     *
     * @return The widget (container) for the game snapshot.
     */
    protected static VBox getGameWidget(VirtualView vv) {
      VBox commonGoals = new VBox();
      commonGoals.setAlignment(Pos.CENTER);
      commonGoals.setSpacing(10);
      commonGoals.getChildren().addAll(getCommonGoalCard(vv));
      VBox playerItems = new VBox();
      playerItems.setAlignment(Pos.CENTER);
      playerItems.setSpacing(10);
      playerItems.getChildren().add(getLabel("Your Private Card", FontWeight.BOLD, 20));
      playerItems.getChildren().add(getPrivateCard(vv));
      playerItems.getChildren().add(getLabel("Common Goals", FontWeight.BOLD, 20));
      playerItems.getChildren().add(commonGoals);
      VBox gameBoard = new VBox();
      gameBoard.setSpacing(10);
      gameBoard.setAlignment(Pos.CENTER);
      gameBoard.getChildren().addAll(getLabel("Game Board", FontWeight.BOLD, 20), getGameBoard(vv));
      VBox bookShelf = new VBox();
      bookShelf.setSpacing(10);
      bookShelf.setAlignment(Pos.CENTER);
      bookShelf
          .getChildren()
          .addAll(getLabel(String.format("%s's Bookshelf", vv.getActivePlayer().getPlayerName()), FontWeight.BOLD, 20), getBookShelfGridPane(vv.getActivePlayer().getBookshelf()), getBSButtons(vv));
      VBox chat = new VBox();
      chat.setAlignment(Pos.BOTTOM_RIGHT);
      chat.setPadding(new Insets(0, 15, 15, 0));
      chat.getChildren().addAll(getLabel("Chat", FontWeight.BOLD, 20), getChat());
      HBox gameStage = new HBox();
      gameStage.setAlignment(Pos.CENTER);
      gameStage.getChildren().addAll(gameBoard, bookShelf, playerItems, chat);
      gameStage.setSpacing(10);
      VBox root = new VBox();
      root.setAlignment(Pos.CENTER);
      root.setSpacing(20);
      root.getChildren().addAll(getLeaderboard(vv), gameStage);
      return root;
    }

    /**
     * Method used to update the bookshelf currently on stage.
     * 
     * @param sp scene's stackpane.
     * @param bs bookshelf to show.
     * @param bsOwner string name of the BS owner.
     */
    public static void updateBookshelf(StackPane sp, Bookshelf bs, String bsOwner) {
      VBox root = (VBox) sp.getChildren().get(0);
      HBox gameStage = (HBox) root.getChildren().get(1);
      VBox bookShelf = (VBox) gameStage.getChildren().get(1);
      Label BSLabel = (Label) bookShelf.getChildren().get(0);
      BSLabel.setText(String.format("%s's Bookshelf", bsOwner));
      bookShelf.getChildren().set(1, getBookShelfGridPane(bs));
    }

    /**
     * Method used to update the current game widget scene dynamically. Must be
     * wrapped in executeOnJavaFX
     * when called to make sure it's properly runned into JavaFX's thread.
     * 
     * @param oldSP The old stack pane to update.
     * @param oldVw The old virtual view to check what changed.
     * @param newVw The new virtual view.
     */
    public static void updateGameWidget(StackPane oldSP, VirtualView oldVw, VirtualView newVw) {
      VBox root = (VBox) oldSP.getChildren().get(0);
      root.getChildren().set(0, GameSnapshotFactory.getLeaderboard(newVw));

      HBox gameStage = (HBox) root.getChildren().get(1);
      VBox gameBoard = (VBox) gameStage.getChildren().get(0);
      VBox bookShelf = (VBox) gameStage.getChildren().get(1);

      if (!oldVw.getGameBoard().equals(newVw.getGameBoard())) {
        gameBoard.getChildren().set(1, GameSnapshotFactory.getGameBoard(newVw));
      }

      updateBookshelf(oldSP, newVw.getActivePlayer().getBookshelf(), newVw.getActivePlayer().getPlayerName());
      bookShelf.getChildren().set(2, getBSButtons(newVw));
    }

    /**
     * Method used to update the chat widget dynamically.
     *
     * @param oldSP it's the old stackpane of the chat.
     * @param msg   the message to be added.
     */
    public static void updateChatHistory(StackPane oldSP, AbstractMessage msg) {
      VBox root = (VBox) oldSP.getChildren().get(0);

      HBox gameStage = (HBox) root.getChildren().get(1);
      VBox chat = (VBox) gameStage.getChildren().get(3);
      VBox chatBox = (VBox) chat.getChildren().get(1);
      VBox chatHistory = (VBox) chatBox.getChildren().get(0);
      ListView<String> chatMessages = (ListView<String>) chatHistory.getChildren().get(0);
      if (msg.getMessageType() == MessageType.CHAT_MESSAGE) {
        ChatMessage chatMsg = (ChatMessage) msg;
        if (chatMsg.isBroadcast()) {
          chatMessages.getItems().add(chatMsg.getSender().getPlayerName() + ": " + chatMsg.getMessage());
        } else {
          chatMessages.getItems().add(
              chatMsg.getSender().getPlayerName() + " > " + chatMsg.getReceiverName() + ": " + chatMsg.getMessage());
        }
      } else if (msg.getClass() == ErrorMessage.class) {
        ErrorMessage infoMsg = (ErrorMessage) msg;
        chatMessages.getItems().add(infoMsg.getErrorSeverity() + ": " + infoMsg.getMessage());
      }
      chatMessages.scrollTo(chatMessages.getItems().size() - 1);
    }
  }

  /**
   * The UserMoveBuilder class is a utility class that helps in constructing a
   * user's move. It
   * provides methods to append tile coordinates and destination column to the
   * move, clear the move,
   * and retrieve the constructed move as a string.
   */
  class UserMoveBuilder {
    private static String move = "";

    /**
     * Appends the tile coordinates to the move.
     *
     * @param row The row index of the tile.
     * @param col The column index of the tile.
     */
    public static void appendTile(int row, int col) {
      // commands work on col first
      move += String.valueOf(col) + String.valueOf(row) + " ";
    }

    /**
     * Removes a tile from the move based on its coordinates.
     *
     * @param row The row index of the tile to remove.
     * @param col The column index of the tile to remove.
     */
    public static void removeTile(int row, int col) {
      if (move.length() < 3) {
        return;
      }
      String m = String.valueOf(col) + String.valueOf(row) + " ";
      int index = move.indexOf(m);
      if (index < 0) {
        return;
        // TODO: show internal error
      }
      move = move.replaceAll(move.substring(index, index + m.length()), "");
    }

    /**
     * Appends the destination column to the move.
     *
     * @param col The destination column index.
     */
    public static void appendDestCol(int col) {
      move += Character.valueOf((char) ('A' + col));
    }

    /** Clears the move by resetting it to an empty string. */
    public static void clear() {
      move = "";
    }

    /**
     * Returns the constructed move as a string.
     *
     * @return The move string.
     */
    public static String getMove() {
      return move;
    }
  }
}
