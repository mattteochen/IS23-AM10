package it.polimi.is23am10.items.pattern;

import java.util.function.Function;

import it.polimi.is23am10.items.library.Library;

/**
 * Shared pattern object.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 *         Franesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 *         Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 *         Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
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

}
