package it.polimi.is23am10.pattern;

import java.util.function.Predicate;

import it.polimi.is23am10.items.bookshelf.Bookshelf;

/**
 * Shared pattern object.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public class SharedPattern<T extends Bookshelf> extends AbstractPattern {

  /**
   * A {@link Predicate} instance applying the given rule.
   * 
   */
  private Predicate<T> rule;

  /**
   * A string describing the pattern.
   * 
   */
  private String patternDescription;

  /**
   * The constructor of the class SharedPattern.
   *
   */
  public SharedPattern(Predicate<T> rule, String description) {
    this.rule = rule;
    this.patternDescription = description;
  }

  /**
   * Rule getter.
   *
   * @return The rule function.
   */
  public Predicate<T> getRule() {
    return rule;
  }

  /**
   * Pattern description getter.
   *
   * @return The patter describing the assigned rule.
   */
  public String getPatternDescription() {
    return patternDescription;
  }

  /**
   * {@inheritDoc}
   *
   */
  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof SharedPattern )) {
      return false;
    }
    return this.rule == ((SharedPattern<Bookshelf>) obj).getRule()
        && this.patternDescription.equals(((SharedPattern<Bookshelf>) obj).getPatternDescription());
  }

  /**
   * {@inheritDoc}
   *
   */
  @Override
  public int hashCode() {
    return rule.hashCode() * patternDescription.hashCode();
  }
}
