package it.polimi.is23am10.server.items.card;

import it.polimi.is23am10.server.items.card.exceptions.AlreadyInitiatedPatternException;
import it.polimi.is23am10.server.pattern.AbstractPattern;

/**
 * Abstract card object.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 * 
 * @param T The type of the assigned
 *          {@link AbstractPattern}.
 */
public abstract class AbstractCard<T extends AbstractPattern> {

  /**
   * The pattern instance.
   * Its type must extends {@link AbstractPattern}
   */
  private T pattern;

  /**
   * The instance id.
   * TODO: maybe unused
   */
  private int id;

  /**
   * Pattern setter.
   * Pattern can not be final as it must be randomly chosen and it has to be
   * unique across all instantiated cards.
   * Only the derived class can set the pattern.
   *
   * @param pattern The pattern to assign.
   * @throws AlreadyInitiatedPatternException.
   */
  protected void setPattern(T pattern) throws AlreadyInitiatedPatternException {
    if (this.pattern != null) {
      throw new AlreadyInitiatedPatternException(
          "[Class " + this.getClass() + ", method setPattern]: The pattern has already been intantiated");
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
