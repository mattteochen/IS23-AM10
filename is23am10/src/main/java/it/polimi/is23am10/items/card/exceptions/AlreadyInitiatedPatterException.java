package it.polimi.is23am10.items.card.exceptions;

/**
 * Custom exception for duplicate {@link AbstractCard} {@link AbstractPattern}
 * initialization.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public class AlreadyInitiatedPatterException extends Exception {
	public AlreadyInitiatedPatterException(String msg) {
		super(msg);
	}
}
