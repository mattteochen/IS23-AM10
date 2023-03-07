package it.polimi.is23am10.score.expcetions;

/**
 * Custom exception to handle invalid extra points values.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public class InvalidExtraPointsException extends Exception {
	public InvalidExtraPointsException() {
		super("Extra point must be 0 or 1");
	}
}
