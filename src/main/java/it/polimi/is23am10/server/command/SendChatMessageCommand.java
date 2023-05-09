package it.polimi.is23am10.server.command;

import it.polimi.is23am10.server.network.messages.ChatMessage;

/**
 * The command used by player to send a chat message.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public class SendChatMessageCommand extends AbstractCommand {

  /**
   * An utility to be used during deserialization processes.
   * 
   */
  @SuppressWarnings("unused")
  private final String className = this.getClass().getName();

  /**
   * Chat message to be sent
   */
  private ChatMessage msg;

  /**
   * Constructor.
   *
   */
  public SendChatMessageCommand(ChatMessage m) {
    super(Opcode.SEND_CHAT_MESSAGE);
    this.msg = m;
  }

  /**
   * Chat message getter.
   *
   * @return chat message.
   */
  public ChatMessage getChatMessage() {
    return msg;
  }


  /**
   * {@inheritDoc}
   *
   */
  @Override
  public boolean equals(Object obj) {
    return (obj instanceof SendChatMessageCommand);
  }
}

