package it.polimi.is23am10.server.sharedexceptions;

/**
 * Custom exception to handle index out of bounds values.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public class ColIndexOutOfBoundsException extends Exception {
  public ColIndexOutOfBoundsException(String item, Integer col, Integer maxValue) {
    super(col < 0 ? item + " grid column index can not be negative"
        : item + " grid column index can not be greater than " + maxValue);
  }

}
