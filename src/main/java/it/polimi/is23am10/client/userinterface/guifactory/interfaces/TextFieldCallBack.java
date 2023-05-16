package it.polimi.is23am10.client.userinterface.guifactory.interfaces;

/**
 * The TextFieldCallBack interface represents a callback that is invoked when a text field event occurs.
 */
@FunctionalInterface
public interface TextFieldCallBack {

  /**
   * Called when a text field event occurs.
   *
   * @param s The string value representing the event.
   */
  void call(String s);
}

