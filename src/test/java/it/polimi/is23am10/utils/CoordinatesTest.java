package it.polimi.is23am10.utils;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

/** Test class to check all Coordinates getter,setters and constructors. */
public class CoordinatesTest {

  @Test
  public void constructor_should_create_Coordinates() {
    Integer row = 4;
    Integer col = 2;
    Coordinates coord = new Coordinates(row, col);
    assertEquals(row, coord.getRow());
    assertEquals(col, coord.getCol());
  }

  @Test
  public void row_setter_should_set_Coordinates_row() {
    Coordinates coord = new Coordinates();
    Integer row = 4;
    coord.setRow(row);
    assertEquals(row, coord.getRow());
  }

  @Test
  public void col_setter_should_set_Coordinates_col() {
    Coordinates coord = new Coordinates();
    Integer col = 5;
    coord.setCol(col);
    assertEquals(col, coord.getCol());
  }

  @Test
  public void hash_code_should_give_custom_hash_code() {
    Integer row = 4;
    Integer col = 2;
    Coordinates coord = new Coordinates(row, col);

    assertEquals(coord.hashCode(), row + (col + ((row + 1) / 2)) * (col + ((row + 1) / 2)));
  }
}
