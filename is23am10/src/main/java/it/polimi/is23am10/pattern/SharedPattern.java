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
   * The constructor of the class SharedPattern
   *
   */
  public SharedPattern(Predicate<T> rule, String description){
    this.rule = rule;
    this.patternDescription = description;
  };

}
