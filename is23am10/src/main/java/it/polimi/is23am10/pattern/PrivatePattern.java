package it.polimi.is23am10.pattern;

import it.polimi.is23am10.items.bookshelf.Bookshelf;
import java.util.function.Function;

/**
 * Private pattern object.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public class PrivatePattern extends AbstractPattern {

  /**
   * A {@link Function} that implements a specific rule,
   * which retrieves the number of bookshelf tiles belonging to a player
   * that match the private card assigned to that player.
   */
  private Function<Bookshelf, Integer> rule;

  /**
   * The constructor of the class PrivatePattern.
   *
   * @param rule a function that takes a Bookshelf object and returns an Integer.
   */
  public PrivatePattern(Function<Bookshelf, Integer> rule) {
    this.rule = rule;
  }

  /**
   * Rule getter.
   *
   * @return The rule function.
   */
  public Function<Bookshelf, Integer> getRule() {
    return rule;
  }

  /**
   * {@inheritDoc}
   *
   */
  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof PrivatePattern)) {
      return false;
    }
    return this.rule == ((PrivatePattern) obj).getRule();
  }

  /**
   * {@inheritDoc}
   *
   */
  @Override
  public int hashCode() {
    return rule.hashCode();
  }
}
