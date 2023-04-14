package it.polimi.is23am10.server.model.items.scoreblock;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import it.polimi.is23am10.server.model.items.scoreblock.exceptions.NotValidScoreBlockValueException;

/**
 * ScoreBlock class definition.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public final class ScoreBlock implements Serializable {

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

  /**
   * {@inheritDoc}
   * 
   */
  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof ScoreBlock)) {
      return false;
    }
    ScoreBlock sb = (ScoreBlock) obj;
    return (score == sb.score);
  }

  /**
   * {@inheritDoc}
   * 
   */
  @Override
  public int hashCode() {
    return score.hashCode();
  }
}
