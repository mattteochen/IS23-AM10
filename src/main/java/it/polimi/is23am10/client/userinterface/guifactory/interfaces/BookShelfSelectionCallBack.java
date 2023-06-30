package it.polimi.is23am10.client.userinterface.guifactory.interfaces;

/**
 * The BookShelfSelectionCallBack interface represents a callback for bookshelf selection events. It
 * defines a single method, `call`, which is invoked when a bookshelf selection event occurs.
 */
@FunctionalInterface
public interface BookShelfSelectionCallBack {

  /**
   * The `call` method is invoked when a bookshelf selection event occurs.
   *
   * @param action The action associated with the bookshelf selection event.
   */
  void call(String action);
}
