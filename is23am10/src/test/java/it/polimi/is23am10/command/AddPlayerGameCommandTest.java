package it.polimi.is23am10.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import it.polimi.is23am10.command.AbstractCommand.Opcode;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class AddPlayerGameCommandTest {

  final UUID uuid = UUID.randomUUID();

  @Test
  void EQUALS_green_path() {

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
  void HASH_CODE_green_path() {
    final AddPlayerCommand steve = new AddPlayerCommand("Steve", uuid);

    assertEquals(steve.hashCode(), steve.getPlayerName().hashCode() * uuid.hashCode());
  }
}