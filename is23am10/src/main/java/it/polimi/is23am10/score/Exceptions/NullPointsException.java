package it.polimi.is23am10.score.Exceptions;

/**
 * Custom exception to handle null points values.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public class NullPointsException extends Exception {
	public NullPointsException(String msg) {
		super(msg);
	}
}
