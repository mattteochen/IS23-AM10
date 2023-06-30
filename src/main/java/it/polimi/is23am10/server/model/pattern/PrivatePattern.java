package it.polimi.is23am10.server.model.pattern;

/**
 * Private pattern object.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public class PrivatePattern<T> extends AbstractPattern<T> {

  /**
   * The constructor of the class PrivatePattern.
   *
   * @param rule a function that takes a Bookshelf object and returns an Integer.
   * @param cardIndex The card index associated
   */
  public PrivatePattern(T rule, Integer cardIndex) {
    super(rule, cardIndex);
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings("unchecked")
  public boolean equals(Object obj) {
    if (!(obj instanceof PrivatePattern)) {
      return false;
    }
    return this.rule == ((PrivatePattern<T>) obj).getRule();
  }

  /** {@inheritDoc} */
  @Override
  public int hashCode() {
    return rule.hashCode();
  }
}
