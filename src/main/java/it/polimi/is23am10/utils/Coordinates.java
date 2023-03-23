package it.polimi.is23am10.utils;

/**
 * The Coordinate helper class definition.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 * 
 */
public class Coordinates {

  /**
   * The row index of the coordinates.
   * 
   */
  private Integer row;

  /**
   * The column index of the coordinates.
   * 
   */
  private Integer col;

  /**
   * Constructor of Coordinates given the two indexes.
   * 
   * @param r row index.
   * @param c column index.
   */
  public Coordinates(Integer r, Integer c) {
    this.row = r;
    this.col = c;
  }

  /**
   * Empty constructor that sets the coordinates in the origin.
   * 
   */
  public Coordinates() {
    this.row = 0;
    this.col = 0;
  }

  /**
   * Row getter.
   * 
   * @return row index.
   */
  public Integer getRow() {
    return row;
  }

  /**
   * Column getter.
   * 
   * @return column index.
   */
  public Integer getCol() {
    return col;
  }

  /**
   * Row setter.
   * 
   * @param r row index.
   */
  public void setRow(Integer r) {
    this.row = r;
  }

  /**
   * Column setter
   * 
   * @param c column index.
   */
  public void setCol(Integer c) {
    this.col = c;
  }

  /**
   * {@inheritDoc}
   *
   */
  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Coordinates)) {
      return false;
    }

    Coordinates coordinates = (Coordinates) obj;
    return (coordinates.getCol() == col && coordinates.getRow() == row);
  }

  /**
   * {@inheritDoc}
   *
   */
  @Override
  public int hashCode() {
    /*
     * A simple hashing function that gives me a unique number given two integers.
     * https://stackoverflow.com/questions/22826326/good-hashcode-function-for-2d-
     * coordinates
     */

    int tmp = (this.col + ((this.row + 1) / 2));
    return this.row + (tmp * tmp);
  }
}
