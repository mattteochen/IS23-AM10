package it.polimi.is23am10.utils.exceptions;

/**
 * Custom exception to handle index out of bounds values.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public class RowIndexOutOfBoundsException extends Exception {
  public RowIndexOutOfBoundsException(String item, Integer row, Integer maxValue) {
    super(
        row < 0
            ? item + " grid row index can not be negative"
            : item + " grid row index can not be greater than " + maxValue);
  }
}
