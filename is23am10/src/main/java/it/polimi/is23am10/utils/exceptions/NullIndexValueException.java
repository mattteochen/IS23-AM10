package it.polimi.is23am10.utils.exceptions;

public class NullIndexValueException extends Exception {
	public NullIndexValueException() {
		super("The provided index value is null");
	}
}
