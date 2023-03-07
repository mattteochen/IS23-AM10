package it.polimi.is23am10.player.Exceptions;

/**
 * Null player name exception.
 * Can be used public methods of {@link Player}.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public class NullPlayerScoreException extends Exception {
	public NullPlayerScoreException(String msg) {
		super(msg);
	}
}
