package it.polimi.is23am10.items.card;

import it.polimi.is23am10.items.pattern.AbstractPattern;

/**
 * Abstract card object.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 *         Franesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 *         Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 *         Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 * 
 * @param T The type of the assigned
 *          {@link it.polimi.is23am10.server.items.AbstractPatter}.
 */
public class AbstractCard<T extends AbstractPattern> {

  /**
   * The pattern instance.
   * Its type must extends {@link it.polimi.is23am10.server.items.AbstractPatter}
   */
  private T pattern;

  /**
   * The instance id.
   */
  private int id;

}
