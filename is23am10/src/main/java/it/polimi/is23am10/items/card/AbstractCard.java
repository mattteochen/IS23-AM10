package it.polimi.is23am10.items.card;

import it.polimi.is23am10.items.pattern.AbstractPattern;

/**
 * Abstract card object.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 * 
 * @param T The type of the assigned
 *          {@link AbstractPattern}.
 */
public abstract class AbstractCard<T extends AbstractPattern> {

  /**
   * The pattern instance.
   * Its type must extends {@link AbstractPattern}
   */
  private T pattern;

  /**
   * The instance id.
   */
  private int id;

}
