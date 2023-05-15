package it.polimi.is23am10.client.userinterface.guifactory;

import java.util.Map;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import it.polimi.is23am10.client.userinterface.GraphicUserInterface;
import it.polimi.is23am10.client.userinterface.helpers.CommandsBuilder;
import it.polimi.is23am10.server.network.virtualview.VirtualView;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.ColorAdjust;

interface ButtonCallBack {
  void call(Scene s, TextField ...tfs);
}

interface TextFieldCallBack {
  void call(String s);
} 

/**
 * GUI scene factory.
 * Creates various GUI scenes for the application.
 * Each scene is created as a static method in this class.
 * The class also contains helper methods for creating GUI components.
 * 
 * The class uses JavaFX for UI rendering.
 * 
 * The factory methods return JavaFX Scene objects that can be used to set the application's stage.
 * The stages are stored in a static map for easy access.
 * 
 * This class is not meant to be instantiated.
 * All methods and members are static.
 * 
 * Example usage:
 * ```
 * Scene splashScreenScene = GuiFactory.getSplashScreenScene();
 * GuiFactory.stages.put(GuiFactory.SCENE.SPLASH_SCREEN, splashScreenScene);
 * primaryStage.setScene(splashScreenScene);
 * primaryStage.show();
 * ```
 * 
 * Note: This is a simplified example and assumes the necessary JavaFX setup has been done.
 * 
 * @see Scene
 * @see Stage
 * @see GuiState
 * @see Button
 * @see TextField
 * @see Label
 * @see BackgroundImage
 * @see Background
 * @see ColorAdjust
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public final class GuiFactory {
  /**
   * An enum representing different scenes that can be created by the factory.
   * Add more values to represent additional scenes in the application.
   */
  public enum SCENE {
    SPLASH_SCREEN,
    ENTER_GAME_SELECTION,
    CREATE_GAME,
    JOIN_GAME
  }

  private static final int SCENE_WIDTH = 1140;

  private static final int SCENE_HEIGHT = 760;

  public static Stage mainStage;

  public static Map<SCENE, Scene> stages = Collections.synchronizedMap(new HashMap<>());

  private static ButtonCallBack confirmNameCallBack = (s, tfs) -> {
    System.out.println("TextField " + tfs[0].getText());
    GraphicUserInterface.addMsgQueue(tfs[0].getText());
  };

  private static ButtonCallBack confirmPlayerNumCallBack = (s, tfs) -> {
    System.out.println("TextField " + tfs[0].getText());
    GraphicUserInterface.addMsgQueue(CommandsBuilder.buildCreateGameCmd(tfs[0].getText()));
  };

  private static ButtonCallBack joinGameCallBack = (s, tfs) -> {
    changeScene(() -> mainStage.setScene(stages.get(SCENE.JOIN_GAME)));
  };

  private static ButtonCallBack createNewGameCallBack = (s, tfs) -> {
    changeScene(() -> mainStage.setScene(stages.get(SCENE.CREATE_GAME)));
  };

  /**
   * Creates a {@link BackgroundImage} with the specified image path and brightness.
   * 
   * @param path The path to the image file.
   * @param brightness The brightness value to apply to the image (-1.0 to 1.0).
   * @return The created BackgroundImage object.
   */
  protected static BackgroundImage getBgImg(String path, double brightness) {

    Image img = null;
    try {
      img = new Image(path);
    } catch(Exception e) {
      //TODO: handle
      System.out.println(e);
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
      new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false)
    );
  }

  /**
   * Creates a {@link Label} with the specified content, font weight, and size.
   * 
   * @param content The text content of the label.
   * @param weight The font weight of the label.
   * @param size The font size of the label.
   * @return The created Label object.
   */
  protected static Label getLabel(String content, FontWeight weight, int size, Color ...colors) {
    Label l =  new Label(content);
    l.setFont(Font.font("Arial", weight, size));
    if (colors != null && colors.length > 0) {
      l.setTextFill(colors[0]);
    }
    return l;
  }

  /**
   * Creates a {@link TextField} with the specified placeholder, width, height, font weight, font size, and callback.
   * 
   * @param placeholder The placeholder text for the text field.
   * @param w The desired width of the text field.
   * @param h The desired width of the text field.
   * @param weight The font weight of the text field.
   * @param size The font size of the text field.
   * @param cb The callback function to be executed when the text field action is triggered.
   * @return The created TextField object.
   */
  protected static TextField getTextField(String placeholder, int w, int h, FontWeight weight, int size, TextFieldCallBack cb) {
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
   * Creates a {@link Button} with the specified content, callback function, and optional text fields.
   *
   * @param content The text content of the button.
   * @param cb The callback function to be executed when the button is clicked.
   * @param tfs Optional text fields to be passed as arguments to the callback function.
   * @return The created Button object.
   */
  protected static Button getButton(String content, ButtonCallBack cb, TextField ...tfs) {
    Button b = new Button(content);
    if (cb != null) {
      b.setOnAction(event -> cb.call(stages.get(SCENE.ENTER_GAME_SELECTION), tfs));
    }
    return b;
  }

  /**
   * Perform a scene change.
   * 
   * @param r The callback to be executed.
   */
  public static void changeScene(Runnable r) {
    Platform.runLater(r);
  }

  /**
   * Creates the splash screen scene.
   * The splash screen scene consists of a stack pane with a background and an input name widget.
   * The background image is retrieved from the SplashScreenFactory class.
   * The input name widget contains a label, a text field, and a confirm button.
   * The confirm button triggers the confirmNameCallBack.
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
   * Creates the enter game selection scene.
   * The enter game selection scene consists of a stack pane with a background and a selection widget.
   * The background image is retrieved from the EnterGameSelectionScreenFactory class.
   * The selection widget contains a label and two buttons (create new game and join game).
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
   * Creates and returns a new JavaFX Scene for the create new game selection screen.
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
   * Factory class for creating the splash screen UI components.
   * Contains methods to create the background image, input name label,
   * input name text field, and the entire input name widget.
   * 
   * This class is not meant to be instantiated.
   * All methods and members are static.
   */
  class SplashScreenFactory {
    private static final String SPLASH_SCREEN_IMG_PATH = "file:welcome_bg.jpg";

    protected static TextField textField;

    /**
     * Creates a {@link BackgroundImage} for the splash screen using the specified image path and brightness.
     * 
     * @return The created BackgroundImage object for the splash screen.
     */
    protected static BackgroundImage getSplashScreenBg() {
      return getBgImg(SPLASH_SCREEN_IMG_PATH, -0.5);
    }

    /**
     * Creates a {@link Label} for the input name with the specified content, font weight, and size.
     * 
     * @return The created Label object for the input name.
     */
    protected static Label getInputNameLabel() {
      return getLabel("Please choose your name", FontWeight.BOLD, 40);
    }

    /**
     * Creates a {@link TextField} for entering the name with the specified placeholder, width, height,
     * font weight, font size, and callback.
     * 
     * @return The created TextField object for entering the name.
     */
    protected static TextField getInputNameTextField() {
      textField = getTextField("Type here", 300, 300, FontWeight.NORMAL, 20, null);
      return textField;
    }

    /**
     * Creates a {@link VBox} that contains the input name label, input name text field, and confirm button.
     * 
     * @return The created VBox widget for the input name.
     */
    protected static VBox getInputNameWidget() {
      VBox box = new VBox();
      box.setAlignment(Pos.CENTER);
      box.setSpacing(10);
      box.getChildren().addAll(getInputNameLabel(), getInputNameTextField(), getButton("Confirm", confirmNameCallBack, textField));
      return box;
    }
  }

  /**
   * GUI enter game selection screen factory.
   * Creates GUI components for the enter game selection screen.
   */
  class EnterGameSelectionScreenFactory {
    private static final String SELECTION_SCREEN_IMG_PATH = "file:welcome_bg.jpg";

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
      hbox.getChildren().addAll(getButton("Create new game", createNewGameCallBack), getButton("Join game", joinGameCallBack));
      VBox vbox = new VBox();
      vbox.setAlignment(Pos.CENTER);
      vbox.setSpacing(10);
      vbox.getChildren().addAll(getSelectionOptionLabel(), hbox);
      return vbox;
    }
  }

  /**
   * The NewGameFactory class provides static methods to create various UI components
   * for setting up a new game.
   */
  class NewGameFactory {
    private static final String SPLASH_SCREEN_IMG_PATH = "file:welcome_bg.jpg";

    /**
     * A map of text fields associated with player numbers.
     */
    protected static Map<String, TextField> textFields = Map.of(
      "2", new TextField(),
      "3", new TextField(),
      "4", new TextField()
    );

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
      return getButton(n, confirmPlayerNumCallBack, tf);
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
      hbox.getChildren().addAll(getPlayerNumButton("2"), getPlayerNumButton("3"), getPlayerNumButton("4"));
      VBox vbox = new VBox();
      vbox.setAlignment(Pos.CENTER);
      vbox.setSpacing(10);
      vbox.getChildren().addAll(getInputPlayerNumberLabel(), hbox);
      return vbox;
    }
  }

  /**
   * The JoinGameFactory class provides static methods to create various UI components
   * for setting up a game to join.
   */
  class JoinGameFactory {
    private static final String SPLASH_SCREEN_IMG_PATH = "file:welcome_bg.jpg";

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
     * @param vv The game server virtual view.
     * @return The button for selecting the game server.
     */
    protected static Button getGameIdButton(String index, VirtualView vv) {
      String id = vv.getGameId().toString();
      TextField tf = new TextField(id);
      tf.setText(index);
      return getButton(id, confirmPlayerNumCallBack, tf);
    }

    /**
     * Creates a VBox container with input widgets for setting up game server selection.
     *
     * @return The VBox container with input widgets for choosing the server to join.
     */
    protected static VBox getJoinGameWidget(List<VirtualView> vvs) {
      List<Button> buttons = new ArrayList<>();
      for (int i=0; i<vvs.size(); i++) {
        buttons.add(getGameIdButton(String.valueOf(i), vvs.get(i)));
      }

      HBox hbox = new HBox();
      hbox.setAlignment(Pos.CENTER);
      hbox.setSpacing(30);
      if (buttons.size() > 0) {
        hbox.getChildren().addAll(buttons);
      } else {
        hbox.getChildren().add(getLabel("No available servers!", FontWeight.BOLD, 30, Color.RED));
      }
      VBox vbox = new VBox();
      vbox.setAlignment(Pos.CENTER);
      vbox.setSpacing(10);
      vbox.getChildren().addAll(getInputJoinGameLabel(), hbox);
      return vbox;
    }
  }
}
