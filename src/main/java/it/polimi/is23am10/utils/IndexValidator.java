package it.polimi.is23am10.utils;

import it.polimi.is23am10.utils.exceptions.NullIndexValueException;

/**
 * Index validator class definition.
 * 
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 * 
 */
public final class IndexValidator {

  /**
   * Private constructor.
   * 
   */
  private IndexValidator() {

  }

  /**
   * Validate the row index.
   * 
   * @param row The index value to be evaluated.
   * @return The validation result.
   * @throws NullIndexValueException If the index provided is null.
   * 
   */
  public static boolean validRowIndex(Integer row, Integer maxValue) throws NullIndexValueException {
    if (row == null) {
      throw new NullIndexValueException();
    }
    return row >= 0 && row < maxValue;
  }

  /**
   * Validate the column index.
   * 
   * @param col The index value to be evaluated.
   * @return The validation result.
   * @throws NullIndexValueException If the index provided is null.
   * 
   */
  public static boolean validColIndex(Integer col, Integer maxValue) throws NullIndexValueException {
    if (col == null) {
      throw new NullIndexValueException();
    }
    return col >= 0 && col < maxValue;
  }

}
