package it.polimi.is23am10.chat;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import it.polimi.is23am10.chat.AbstractMessage.MessageType;
import it.polimi.is23am10.factory.PlayerFactory;
import it.polimi.is23am10.factory.exceptions.DuplicatePlayerNameException;
import it.polimi.is23am10.factory.exceptions.NullPlayerNamesException;
import it.polimi.is23am10.game.Game;
import it.polimi.is23am10.game.exceptions.NullAssignedPatternException;
import it.polimi.is23am10.items.card.exceptions.AlreadyInitiatedPatternException;
import it.polimi.is23am10.player.Player;
import it.polimi.is23am10.player.exceptions.NullPlayerBookshelfException;
import it.polimi.is23am10.player.exceptions.NullPlayerIdException;
import it.polimi.is23am10.player.exceptions.NullPlayerNameException;
import it.polimi.is23am10.player.exceptions.NullPlayerPrivateCardException;
import it.polimi.is23am10.player.exceptions.NullPlayerScoreBlocksException;
import it.polimi.is23am10.player.exceptions.NullPlayerScoreException;

public class ChatMessageTest {
  @Test
  public void constructor_should_create_ChatMessage() 
    throws NullPlayerNameException, NullPlayerIdException, NullPlayerBookshelfException,
    NullPlayerScoreException, NullPlayerPrivateCardException, NullPlayerScoreBlocksException, 
    DuplicatePlayerNameException, AlreadyInitiatedPatternException, NullPlayerNamesException, NullAssignedPatternException {
    
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
