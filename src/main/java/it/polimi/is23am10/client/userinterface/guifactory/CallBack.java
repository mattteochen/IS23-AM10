package it.polimi.is23am10.client.userinterface.guifactory;

import it.polimi.is23am10.client.userinterface.GraphicUserInterface;
import it.polimi.is23am10.client.userinterface.UserInterface;
import it.polimi.is23am10.client.userinterface.guifactory.GuiFactory.SCENE;
import it.polimi.is23am10.client.userinterface.guifactory.interfaces.BookShelfSelectionCallBack;
import it.polimi.is23am10.client.userinterface.guifactory.interfaces.ButtonCallBack;
import it.polimi.is23am10.client.userinterface.helpers.CommandsBuilder;

/**
 * The CallBack class provides various callback implementations used in the GUI. It includes
 * callback implementations for button actions and bookshelf selection events.
 */
public final class CallBack {

  /**
   * A callback implementation for confirming a name. This callback prints the entered text and adds
   * it to the message queue of the GraphicUserInterface.
   */
  public static ButtonCallBack confirmNameCallBack =
      (tfs) -> {
        GraphicUserInterface.addMsgQueue(tfs[0].getText());
      };

  /**
   * A callback implementation for confirming the number of players. This callback prints the
   * entered text and adds the create game command to the message queue of the GraphicUserInterface.
   */
  public static ButtonCallBack confirmPlayerNumCallBack =
      (tfs) -> {
        GraphicUserInterface.addMsgQueue(CommandsBuilder.buildCreateGameCmd(tfs[0].getText()));
      };

  /**
   * A callback implementation for confirming joining a game. This callback prints the entered text
   * and adds the join game command to the message queue of the GraphicUserInterface.
   */
  public static ButtonCallBack confirmJoinGameCallBack =
      (tfs) -> {
        GraphicUserInterface.addMsgQueue(CommandsBuilder.buildJoinGameCmd(tfs[0].getText()));
      };

  /**
   * A callback implementation for joining a game. This callback changes the scene to the join game
   * scene.
   */
  public static ButtonCallBack joinGameCallBack =
      (tfs) -> {
        GuiFactory.executeOnJavaFX(
            () -> GuiFactory.mainStage.setScene(GuiFactory.stages.get(SCENE.JOIN_GAME)));
      };

  /**
   * A callback implementation for creating a new game. This callback changes the scene to the
   * create game scene.
   */
  public static ButtonCallBack createNewGameCallBack =
      (tfs) -> {
        GuiFactory.executeOnJavaFX(
            () -> GuiFactory.mainStage.setScene(GuiFactory.stages.get(SCENE.CREATE_GAME)));
      };

  /**
   * A callback implementation for moving a tile on the bookshelf. This callback adds the move tile
   * command to the message queue of the GraphicUserInterface.
   */
  public static BookShelfSelectionCallBack moveTileCallBack =
      (move) -> {
        GraphicUserInterface.addMsgQueue(CommandsBuilder.moveTileCmd(move));
      };

  /**
   * A callback implementation to send a chat message. This callback adds the send chat message
   * command to the message queue of the GraphicUserInterface.
   * 
   */
  public static ButtonCallBack sendMessageCallBack =
      (tfs) -> {
        if(tfs[0].getText().stripLeading().length() > 0){
          GraphicUserInterface.addMsgQueue(CommandsBuilder.sendChatMessageCmd(tfs[0].getText()));
        }
        tfs[0].clear();
      };
}
