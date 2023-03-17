package it.polimi.is23am10.playerconnector;

import static org.junit.Assert.assertThrows;

import it.polimi.is23am10.playerconnector.exceptions.NullSocketConnectorException;
import org.junit.jupiter.api.Test;

class PlayerConnectorTest {
  @Test
  void CONSTRUCTOR_throws_NullSocketConnectorException() {
    assertThrows(NullSocketConnectorException.class, () -> new PlayerConnector(null));
  }
}
