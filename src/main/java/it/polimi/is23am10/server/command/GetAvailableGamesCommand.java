package it.polimi.is23am10.server.command;

/**
 * The command used by player to get the list of the games they can join.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public class GetAvailableGamesCommand extends AbstractCommand {

  /**
   * An utility to be used during deserialization processes.
   * 
   */
  @SuppressWarnings("unused")
  private final String className = this.getClass().getName();

  /**
   * Constructor.
   *
   */
  public GetAvailableGamesCommand() {
    super(Opcode.GET_GAMES);
  }

  /**
   * {@inheritDoc}
   *
   */
  @Override
  public boolean equals(Object obj) {
    return (obj instanceof GetAvailableGamesCommand);
  }
}
