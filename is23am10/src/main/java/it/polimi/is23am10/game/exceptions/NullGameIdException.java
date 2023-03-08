package it.polimi.is23am10.game.exceptions;

/**
 * Custom exception to handle null game id.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public class NullGameIdException extends Exception {
	public NullGameIdException() {
		super("Null game id");
	}
}
