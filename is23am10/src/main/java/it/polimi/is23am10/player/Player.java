package it.polimi.is23am10.player;

import it.polimi.is23am10.items.card.PrivateCard;
import it.polimi.is23am10.items.library.Library;
import it.polimi.is23am10.items.score.ScoreBlock;
import it.polimi.is23am10.score.Score;

import java.util.List;
import java.util.UUID;

/**
 * The player object.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 *         Franesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 *         Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 *         Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public class Player {

  /**
   * A randomly generated {@link UUID} id
   * 
   */
  private UUID playerId;

  /**
   * The player name.
   * 
   */
  private String playerName;

  /**
   * The instance {@Score} type.
   * 
   */
  private Score score;

  /**
   * The instance {@Library} type.
   * 
   */
  private Library library;

  /**
   * The instance {@PrivateCard} type.
   * 
   */
  private PrivateCard privateCard;

  /**
   * List of {@ScoreBlock} type.
   * 
   */
  private List<ScoreBlock> scoreBlocks;
}
