package it.polimi.is23am10.items.pattern;

import it.polimi.is23am10.items.library.Library;

import java.util.function.Predicate;

/**
 * Shared pattern object.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public class SharedPattern<T extends Library> extends AbstractPattern {

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
  public SharedPattern(Predicate rule, String description){
    this.rule = rule;
    this.patternDescription = description;
  };

}
