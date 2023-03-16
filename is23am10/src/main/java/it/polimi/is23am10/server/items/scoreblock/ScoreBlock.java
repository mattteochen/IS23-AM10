package it.polimi.is23am10.server.items.scoreblock;

import java.util.Arrays;
import java.util.List;

import it.polimi.is23am10.server.items.scoreblock.exceptions.NotValidScoreBlockValueException;

/**
 * ScoreBlock class definition.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public final class ScoreBlock {

  protected static final List<Integer> allowedScoreValues = Arrays.asList(2, 4, 6, 8);

  /**
   * The value assigned to the current instance of the score block.
   * 
   */
  private Integer score;

  /**
   * Constructor.
   * 
   * @throws NotValidScoreBlockValueException.
   * 
   */
  public ScoreBlock(Integer score) throws NotValidScoreBlockValueException {
    if (!allowedScoreValues.contains(score)) {
      throw new NotValidScoreBlockValueException(
          "[Class " + this.getClass() + ", constructor]: Assigned score value is not compliant with the rules");
    }
    this.score = score;
  }

  /**
   * score getter.
   * 
   * @return The score value associated with this instance.
   * 
   */
  public Integer getScore() {
    return score;
  }
}
