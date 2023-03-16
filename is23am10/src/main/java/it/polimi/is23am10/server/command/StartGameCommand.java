package it.polimi.is23am10.server.command;

public class StartGameCommand extends AbstractCommand {

  private String startingPlayerName;

  private Integer maxPlayers;

  public StartGameCommand(String startingPlayerName, Integer maxPlayers) {
    super(Opcode.START);
    this.startingPlayerName = startingPlayerName;  
    this.maxPlayers = maxPlayers;
  }

  @Override
  public String toString() {
    return gson.toJson(this);
  }

  public String getStartingPlayerName() {
    return startingPlayerName;
  }

  public Integer getMaxPlayers() {
    return maxPlayers;
  }
}
