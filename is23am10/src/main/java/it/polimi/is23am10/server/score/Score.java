package it.polimi.is23am10.server.score;

import it.polimi.is23am10.server.score.exceptions.NullPointsException;

/**
 * The Score class definition.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */

// TODO: Change setter methods to accept Objects and compute scores directly in
// setter
public final class Score {
  /**
   * Integer referencing the extra point given to the first player
   * to complete their bookshelf.
   * 
   */
  private Integer extraPoint;

  /**
   * Integer referencing the points the player receives from
   * the groups of same type tiles in their bookshelf.
   * 
   */
  private Integer bookshelfPoints;

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
    extraPoint = 0;
    bookshelfPoints = 0;
    scoreBlockPoints = 0;
    privatePoints = 0;
  }

  /**
   * extraPoint setter. Value can be only set to 1.
   * 
   */
  // TODO: Check that extra points is given to only one player
  public void setExtraPoint() {
    this.extraPoint = 1;
  }

  /**
   * bookshelfPoints setter.
   * 
   * @param bookshelfPoints The bookshelf points value to be assigned.
   * @throws NullPointsException.
   * 
   */
  public void setBookshelfPoints(Integer bookshelfPoints) throws NullPointsException {
    if (bookshelfPoints == null) {
      throw new NullPointsException(
          "[class " + this.getClass() + ", method setBookshelfPoints]: Null bookshelf points value");
    }
    this.bookshelfPoints = bookshelfPoints;
  }

  /**
   * scoreBlockPoints setter.
   * 
   * @param scoreBlockPoints The scoreBlock points value to be assigned.
   * @throws NullPointsException.
   * 
   */
  public void setScoreBlockPoints(Integer scoreBlockPoints) throws NullPointsException {
    if (scoreBlockPoints == null) {
      throw new NullPointsException(
          "[class " + this.getClass() + ", method setScoreBlockPoints]: Null score block points value");
    }
    this.scoreBlockPoints = scoreBlockPoints;
  }

  /**
   * privatePoints setter.
   * 
   * @param privatePoints The private card points value to be assigned.
   * @throws NullPointsException.
   * 
   */
  public void setPrivatePoints(Integer privatePoints) throws NullPointsException {
    if (privatePoints == null) {
      throw new NullPointsException(
          "[class " + this.getClass() + ", method setPrivatePoints]: Null private points value");
    }
    this.privatePoints = privatePoints;
  }

  /**
   * extraPoints getter.
   * 
   * @return The extra points value.
   * 
   */
  public Integer getExtraPoint() {
    return extraPoint;
  }

  /**
   * bookshelfPoints getter.
   * 
   * @return The bookshelf points value.
   * 
   */
  public Integer getBookshelfPoints() {
    return bookshelfPoints;
  }

  /**
   * scoreBlockPoints getter.
   * 
   * @return The score block points value.
   * 
   */
  public Integer getScoreBlockPoints() {
    return scoreBlockPoints;
  }

  /**
   * privatePoints getter.
   * 
   * @return The private points value.
   * 
   */
  public Integer getPrivatePoints() {
    return privatePoints;
  }
}
