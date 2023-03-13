package it.polimi.is23am10.pattern;

import java.util.function.Predicate;

import it.polimi.is23am10.items.bookshelf.Bookshelf;

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
   * A {@link Predicate} instance applying the given rule.
   * 
   */
  private Predicate<Bookshelf> rule;
  
  /**
   * The constructor of the class PrivatePattern.
   * 
   */
  public PrivatePattern(Predicate rule){
      this.rule = rule;
  };
}
