package it.polimi.is23am10.command;

import com.google.gson.Gson;

public class AbstractCommand {

  public enum Opcode {
    START,
    NULL
  }

  protected Opcode opcode;

  protected final Gson gson = new Gson();

  AbstractCommand(Opcode opcode) {
    this.opcode = opcode != null ? opcode : Opcode.NULL;
  }

  @Override
  public String toString() {
    return "";
  }

  public Opcode getOpcode() {
    return opcode;
  }
}
