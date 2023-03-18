package it.polimi.is23am10.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import it.polimi.is23am10.command.AbstractCommand.Opcode;
import org.junit.jupiter.api.Test;

class StartGameCommandTest {
  @Test
  void EQUALS_green_path() {

    class Utils extends AbstractCommand {
      Utils(Opcode op) {
        super(op);
      }
    }

    final StartGameCommand steve = new StartGameCommand("Steve", 2);
    final StartGameCommand steveBrother = new StartGameCommand("Steve", 2);
    final StartGameCommand alice = new StartGameCommand("Alice", 2);
    final Utils anotherCommand = new Utils(Opcode.NULL);

    assertEquals(steve, steveBrother);
    assertEquals(steve, steve);
    assertNotEquals(steve, alice);
    assertNotEquals(steve, anotherCommand);
  }

  @Test
  void HASH_CODE_green_path() {
    final StartGameCommand steve = new StartGameCommand("Steve", 2);

    assertEquals(steve.hashCode(), steve.getStartingPlayerName().hashCode());
  }
}