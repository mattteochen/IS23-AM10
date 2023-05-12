package it.polimi.is23am10.server.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import it.polimi.is23am10.server.command.AbstractCommand.Opcode;

@SuppressWarnings({ "checkstyle:methodname", "checkstyle:abbreviationaswordinnamecheck", "checkstyle:linelengthcheck" })
class LogOutCommandTest {
  @Test
  void EQUALS_should_COMPARE_CONTENT_EQUALITY() {

    class Utils extends AbstractCommand {
      Utils(Opcode op) {
        super(op);
      }
    }
    String uuidSeed = "TEST123";
    UUID id1 = UUID.nameUUIDFromBytes(uuidSeed.getBytes());
    UUID id2 = UUID.randomUUID();
    final LogOutCommand steve = new LogOutCommand("Steve", id1);
    final LogOutCommand alice = new LogOutCommand("Alice", id2);
    final LogOutCommand alice_homonym = new LogOutCommand("Alice", id1);
    final Utils anotherCommand = new Utils(Opcode.NULL);

    assertEquals(steve, steve);
    assertNotEquals(steve, alice);
    assertNotEquals(alice, alice_homonym);
    assertNotEquals(steve, anotherCommand);
  }

  @Test
  void HASH_CODE_should_GIVE_CUSTOM_HASH_CODE() {
    UUID id1 = UUID.randomUUID();
    final LogOutCommand steve = new LogOutCommand("Steve", id1);

    assertEquals(steve.hashCode(), steve.getPlayerName().hashCode());
  }
}