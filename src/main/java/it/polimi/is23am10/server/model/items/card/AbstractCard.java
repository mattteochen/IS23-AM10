package it.polimi.is23am10.server.model.items.card;

import java.io.Serializable;

import it.polimi.is23am10.server.model.items.card.exceptions.AlreadyInitiatedPatternException;
import it.polimi.is23am10.server.model.pattern.AbstractPattern;

/**
 * Abstract card object.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 * 
 */
public abstract class AbstractCard<R, T extends AbstractPattern<R>> implements Serializable {

  /**
   * The pattern instance.
   * Its type must extends {@link AbstractPattern}
   */
  private transient T pattern;

  /**
   * Pattern setter.
   * Pattern can not be final as it must be randomly chosen and it has to be
   * unique across all instantiated cards.
   * Only the derived class can set the pattern.
   *
   * @param pattern The pattern to assign.
   * @throws AlreadyInitiatedPatternException If assigning a pattern to a card that already has one. If the pattern is already initiated.
   */
  protected void setPattern(T pattern) throws AlreadyInitiatedPatternException {
    if (this.pattern != null) {
      throw new AlreadyInitiatedPatternException(
          "[Class " + this.getClass() + ", method setPattern]: The pattern has already been instantiated");
    }
    this.pattern = pattern;
  }

  /**
   * Pattern getter.
   *
   * @return The instance pattern.
   */
  public T getPattern() {
    return pattern;
  }
}
