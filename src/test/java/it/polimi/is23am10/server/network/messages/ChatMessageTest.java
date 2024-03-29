package it.polimi.is23am10.server.network.messages;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import it.polimi.is23am10.server.model.factory.PlayerFactory;
import it.polimi.is23am10.server.model.factory.exceptions.DuplicatePlayerNameException;
import it.polimi.is23am10.server.model.factory.exceptions.NullPlayerNamesException;
import it.polimi.is23am10.server.model.game.Game;
import it.polimi.is23am10.server.model.game.exceptions.NullAssignedPatternException;
import it.polimi.is23am10.server.model.items.card.exceptions.AlreadyInitiatedPatternException;
import it.polimi.is23am10.server.model.player.Player;
import it.polimi.is23am10.server.model.player.exceptions.NullPlayerBookshelfException;
import it.polimi.is23am10.server.model.player.exceptions.NullPlayerIdException;
import it.polimi.is23am10.server.model.player.exceptions.NullPlayerNameException;
import it.polimi.is23am10.server.model.player.exceptions.NullPlayerPrivateCardException;
import it.polimi.is23am10.server.model.player.exceptions.NullPlayerScoreBlocksException;
import it.polimi.is23am10.server.model.player.exceptions.NullPlayerScoreException;
import it.polimi.is23am10.server.network.messages.AbstractMessage.MessageType;
import org.junit.jupiter.api.Test;

/** Tests for chat message class */
public class ChatMessageTest {
  @Test
  public void constructor_should_create_ChatMessage()
      throws NullPlayerNameException,
          NullPlayerIdException,
          NullPlayerBookshelfException,
          NullPlayerScoreException,
          NullPlayerPrivateCardException,
          NullPlayerScoreBlocksException,
          DuplicatePlayerNameException,
          AlreadyInitiatedPatternException,
          NullPlayerNamesException,
          NullAssignedPatternException {

    final Player sp = PlayerFactory.getNewPlayer("sender", new Game());
    final Player rp = PlayerFactory.getNewPlayer("receiver", new Game());
    final String chatMessage = "prova123";

    final ChatMessage cm1 = new ChatMessage(sp, chatMessage);
    final ChatMessage cm2 = new ChatMessage(sp, chatMessage, rp);

    assertNotNull(cm1);
    assertNotNull(cm2);

    assertNull(cm1.getReceiver());
    assertNotNull(cm2.getReceiver());
    assertEquals(rp, cm2.getReceiver());

    assertNotNull(cm1.getMessage());
    assertNotNull(cm2.getMessage());
    assertEquals(chatMessage, cm1.getMessage());
    assertEquals(chatMessage, cm2.getMessage());

    assertNotNull(cm1.getMessageType());
    assertNotNull(cm2.getMessageType());
    assertEquals(MessageType.CHAT_MESSAGE, cm1.getMessageType());
    assertEquals(MessageType.CHAT_MESSAGE, cm2.getMessageType());

    assertNotNull(cm1.getSender());
    assertNotNull(cm2.getSender());
    assertEquals(sp, cm1.getSender());
    assertEquals(sp, cm2.getSender());
  }
}
