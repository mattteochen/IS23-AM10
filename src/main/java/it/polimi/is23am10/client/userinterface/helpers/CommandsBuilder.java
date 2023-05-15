package it.polimi.is23am10.client.userinterface.helpers;

/**
 * Game mode string constants.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public final class CommandsBuilder {
  public final static String CREATE_GAME = "c";

  public final static String JOIN_GAME = "j";

  public final static String buildCreateGameCmd(String pn) {
    return CREATE_GAME + " " + pn;
  }

  public final static String buildJoinGameCmd(String id) {
    return JOIN_GAME + " " + id;
  }
}
