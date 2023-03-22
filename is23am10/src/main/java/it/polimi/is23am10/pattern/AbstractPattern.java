package it.polimi.is23am10.pattern;

/**
 * Abstract pattern object.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public abstract class AbstractPattern<T> {

  /**
   * The assigned rule to this pattern.
   * 
   */
  protected T rule;

  /**
   * Constructor.
   * 
   * @param rule The rule assigned to the current pattern..
   * 
   */
  protected AbstractPattern(T rule) {
    this.rule = rule;
  }

  /**
   * Rule getter.
   *
   * @return The rule function.
   */
  public T getRule() {
    return rule;
  }
}
