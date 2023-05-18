package it.polimi.is23am10.client.userinterface.guifactory.interfaces;

import javafx.scene.control.TextField;

/**
 * The ButtonCallBack interface represents a callback function that is invoked when a button is clicked.
 * It defines a single method 'call' that accepts an array of TextField objects as parameters.
 * Implementations of this interface can define custom logic to be executed when the button is clicked.
 */
@FunctionalInterface
public interface ButtonCallBack {
  /**
   * Executes the callback function when the button is clicked.
   *
   * @param tfs An array of TextField objects representing the text fields associated with the button click.
   */
  void call(TextField... tfs);
}

