package it.polimi.is23am10.server.model.pattern;

/**
 * Shared pattern object.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public class SharedPattern<T> extends AbstractPattern<T> {

  /**
   * The constructor of the class SharedPattern.
   *
   */
  public SharedPattern(T rule, Integer cardIndex) {
    super(rule, cardIndex);
  }

  /**
   * {@inheritDoc}
   *
   */
  @Override
  @SuppressWarnings("unchecked")
  public boolean equals(Object obj) {
    if (!(obj instanceof SharedPattern)) {
      return false;
    }
    return this.rule == ((SharedPattern<T>) obj).getRule();
  }

  /**
   * {@inheritDoc}
   *
   */
  @Override
  public int hashCode() {
    return this.getIndex().hashCode();
  }
}
