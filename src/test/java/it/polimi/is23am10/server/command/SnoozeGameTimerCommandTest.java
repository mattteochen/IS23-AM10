package it.polimi.is23am10.server.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import it.polimi.is23am10.server.command.AbstractCommand.Opcode;

@SuppressWarnings({ "checkstyle:methodname", "checkstyle:abbreviationaswordinnamecheck", "checkstyle:linelengthcheck" })
class SnoozeGameTimerCommandTest {
  @Test
  void EQUALS_should_COMPARE_CONTENT_EQUALITY() {

    class Utils extends AbstractCommand {
      Utils(Opcode op) {
        super(op);
      }
    }

    final SnoozeGameTimerCommand steve = new SnoozeGameTimerCommand("Steve");
    final SnoozeGameTimerCommand steveBrother = new SnoozeGameTimerCommand("Steve");
    final SnoozeGameTimerCommand alice = new SnoozeGameTimerCommand("Alice");
    final Utils anotherCommand = new Utils(Opcode.NULL);

    assertEquals(steve, steveBrother);
    assertEquals(steve, steve);
    assertNotEquals(steve, alice);
    assertNotEquals(steve, anotherCommand);
  }

  @Test
  void HASH_CODE_should_GIVE_CUSTOM_HASH_CODE() {
    final SnoozeGameTimerCommand steve = new SnoozeGameTimerCommand("Steve");

    assertEquals(steve.hashCode(), steve.getPlayerName().hashCode());
  }
}
