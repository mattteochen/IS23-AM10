package it.polimi.is23am10.server.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import it.polimi.is23am10.server.command.AbstractCommand.Opcode;
import java.util.UUID;
import org.junit.jupiter.api.Test;

@SuppressWarnings({
  "checkstyle:methodname",
  "checkstyle:abbreviationaswordinnamecheck",
  "checkstyle:linelengthcheck"
})
class AddPlayerGameCommandTest {

  final UUID uuid = UUID.randomUUID();

  @Test
  void EQUALS_should_COMPARE_CONTENT_EQUALITY() {

    class Utils extends AbstractCommand {
      Utils(Opcode op) {
        super(op);
      }
    }

    final AddPlayerCommand steve = new AddPlayerCommand("Steve", uuid);
    final AddPlayerCommand steveBrother = new AddPlayerCommand("Steve", uuid);
    final AddPlayerCommand alice = new AddPlayerCommand("Alice", uuid);
    final Utils anotherCommand = new Utils(Opcode.NULL);

    assertEquals(steve, steveBrother);
    assertEquals(steve, steve);
    assertNotEquals(steve, alice);
    assertNotEquals(steve, anotherCommand);
  }

  @Test
  void HASH_CODE_should_GIVE_CUSTOM_HASH_CODE() {
    final AddPlayerCommand steve = new AddPlayerCommand("Steve", uuid);

    assertEquals(steve.hashCode(), steve.getPlayerName().hashCode() * uuid.hashCode());
  }
}
