package it.polimi.is23am10.score;

/**
 * The Score object.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public class Score {
  /**
   * Integer referencing the extrapoint given to the first player 
   * to complete their library.
   * 
   */
  private Integer extraPoints;

  /**
   * Integer referencing the points the player receives from 
   * the groups of same type tiles in their library.
   * 
   */
  private Integer libraryPoints;

  /**
   * Integer referencing the points the player receives from 
   * completing shared goals.
   * 
   */
  private Integer scoreBlockPoints;

  /**
   * Integer referencing the points the player receives from
   * completing their private (secret) goal.
   * 
   */
  private Integer privatePoints;

  /**
   * Constructor.
   * Set all the default values.
   * 
   */
  public Score() {
    extraPoints = 0;
    libraryPoints = 0;
    scoreBlockPoints = 0;
    privatePoints = 0;
  }
}
