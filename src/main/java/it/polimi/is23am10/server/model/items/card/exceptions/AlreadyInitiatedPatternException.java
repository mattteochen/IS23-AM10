package it.polimi.is23am10.server.model.items.card.exceptions;

import it.polimi.is23am10.server.model.items.card.AbstractCard;
import it.polimi.is23am10.server.model.pattern.AbstractPattern;

/**
 * Custom exception for duplicate {@link AbstractCard} {@link AbstractPattern}
 * initialization.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public class AlreadyInitiatedPatternException extends Exception {
  public AlreadyInitiatedPatternException(String msg) {
    super(msg);
  }
}
