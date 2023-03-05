package it.polimi.is23am10.items.pattern;

import it.polimi.is23am10.items.library.Library;
import java.util.function.Function;


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
   * A {@link Function} instance applying the given rule.
   * 
   */
  private Function<T, Integer> rule;

  /**
   * A referecens on the rule's number of occurences.
   * 
   */
  private Integer numOfOccurences;

  /**
   * A string describeing the pattern.
   * 
   */
  private String patterDescription;

}
