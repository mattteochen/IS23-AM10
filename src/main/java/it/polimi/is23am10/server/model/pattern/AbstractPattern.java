package it.polimi.is23am10.server.model.pattern;

/**
 * Abstract pattern object.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public abstract class AbstractPattern<T> {

  /** The assigned rule to this pattern. */
  protected T rule;

  /** A 1-12 number to identify the card sprite in view */
  private Integer cardIndex;

  /**
   * Constructor.
   *
   * @param rule The rule assigned to the current pattern..
   * @param cardIndex The card index associated
   */
  protected AbstractPattern(T rule, Integer cardIndex) {
    this.rule = rule;
    this.cardIndex = cardIndex;
  }

  /**
   * Rule getter.
   *
   * @return The rule function.
   */
  public T getRule() {
    return rule;
  }

  /**
   * CardIndex getter
   *
   * @return The index
   */
  public Integer getIndex() {
    return cardIndex;
  }
}
