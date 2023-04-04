package it.polimi.is23am10.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Map;
import java.util.HashMap;
import java.util.UUID;

import it.polimi.is23am10.utils.Coordinates;

import it.polimi.is23am10.command.AbstractCommand.Opcode;
import org.junit.jupiter.api.Test;

@SuppressWarnings({ "checkstyle:methodname", "checkstyle:abbreviationaswordinnamecheck", "checkstyle:linelengthcheck" })
class MoveTilesCommandTest {
  @Test
  void EQUALS_should_COMPARE_CONTENT_EQUALITY() {

    class Utils extends AbstractCommand {
      Utils(Opcode op) {
        super(op);
      }
    }

    String uuidSeed = "TEST123";
    UUID id1 = UUID.nameUUIDFromBytes(uuidSeed.getBytes());
    UUID id1_duplicate = UUID.nameUUIDFromBytes(uuidSeed.getBytes());
    UUID id2 = UUID.randomUUID();
    Map<Coordinates, Coordinates> coordMap1 = new HashMap<>();
    Map<Coordinates, Coordinates> coordMap1_duplicate = new HashMap<>();
    Map<Coordinates, Coordinates> coordMap2 = new HashMap<>();

    final MoveTilesCommand item1 = new MoveTilesCommand("Steve", id1, coordMap1);
    final MoveTilesCommand item1_duplicate = new MoveTilesCommand("Steve", id1_duplicate, coordMap1_duplicate);
    final MoveTilesCommand item2 = new MoveTilesCommand("Alice", id2, coordMap2);
    final Utils anotherCommand = new Utils(Opcode.NULL);

    assertNotNull(item1);
    assertNotNull(item1_duplicate);
    assertEquals(item1, item1_duplicate);
    assertNotEquals(item1, item2);
    assertNotEquals(item1, anotherCommand);
  }

  @Test
  void HASH_CODE_should_GIVE_CUSTOM_HASH_CODE() {
    UUID id1 = UUID.randomUUID();
    Map<Coordinates, Coordinates> coordMap1 = new HashMap<>();
    final MoveTilesCommand item1 = new MoveTilesCommand("Player", id1, coordMap1);

    assertEquals(item1.hashCode(), "Player".hashCode() * item1.getGameId().hashCode() * item1.getMoves().hashCode());
  }
}
