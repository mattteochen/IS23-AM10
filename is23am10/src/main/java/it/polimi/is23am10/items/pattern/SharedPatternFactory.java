package it.polimi.is23am10.items.pattern;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import it.polimi.is23am10.items.library.Library;
import it.polimi.is23am10.items.pattern.SharedPattern;
import it.polimi.is23am10.items.tile.Tile;
import it.polimi.is23am10.items.tile.Tile.TileType;

/**
 * Shared pattern factory object.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public final class SharedPatternFactory {
  private SharedPatternFactory() {
  }

  /*
   * #1
   * Rule that checks if there are at least six couples of the same tile type in
   * adjacent positions (row or column)
   *
   */
  public static final Predicate<Library> checkTwoAdjacents = l -> {
    int count = 0;
    Tile[][] grid = l.getLibraryGrid();
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[i].length; j++) {
        if (i < grid.length - 1 && grid[i][j].equals(grid[i + 1][j])) {
          count++;
          if (count >= 6) {
            return true;
          }
        }
        if (j < grid[i].length - 1 && grid[i][j].equals(grid[i][j + 1])) {
          count++;
          if (count >= 6) {
            return true;
          }
        }
      }
    }
    return false;
  };

  /*
   * #2
   * Rule that checks if the elements on all the corners match
   *
   */
  public static final Predicate<Library> checkCornersMatch = l -> {
    Tile[][] grid = l.getLibraryGrid();
    return (grid[0][0].equals(grid[0][4]) &&
        grid[0][0].equals(grid[5][0]) &&
        grid[0][0].equals(grid[5][4]));
  };

  /*
   * #3
   * Rule that checks if there are at least 4 different groups of 4 elements of
   * the same type in adjacent positions(row or column)
   *
   */
  public static final Predicate<Library> checkFourAdjacents = (l) -> {
    int count = 0;
    Tile[][] grid = l.getLibraryGrid();
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[i].length; j++) {
        if (i <= grid.length - 3 && grid[i][j].equals(grid[i + 1][j]) && grid[i][j].equals(grid[i + 2][j])
            && grid[i][j].equals(grid[i + 3][j])) {
          count++;
          if (count >= 4) {
            return true;
          }
        }
        if (j <= grid[i].length - 3 && grid[i][j].equals(grid[i][j + 1]) && grid[i][j].equals(grid[i][j + 2])
            && grid[i][j].equals(grid[i][j + 3])) {
          count++;
          if (count >= 4) {
            return true;
          }
        }
      }
    }
    return false;
  };

  /*
   * #4
   * Rule that checks if there are at least two 2x2 squares of tiles of the same
   * type (the type has to be the same for both the squares)
   * TODO: how do I check whether they are all of the same type
   */
  public static final Predicate<Library> checkSquares = (l) -> {
    int count = 0;
    Tile[][] grid = l.getLibraryGrid();
    for (int i = 0; i < grid.length - 1; i++) {
      for (int j = 0; j < grid[i].length - 1; j++) {
        if (grid[i][j].equals(grid[i + 1][j]) && grid[i][j].equals(grid[i][j + 1])
            && grid[i][j].equals(grid[i + 1][j + 1])) {
          count++;
          if (count >= 2) {
            return true;
          }
        }
      }
    }
    return false;
  };

  /*
   * #5
   * Rule that checks if there are at least three columns containing maximum 3
   * different types of tiles
   *
   */
  public static final Predicate<Library> checkMaxThreeTypesInColumn = (l) -> {
    int countColumns = 0;
    Tile[][] grid = l.getLibraryGrid();
    for (int i = 0; i < grid[i].length; i++) {
      int countTypes = 0;
      Set<TileType> seenTypes = new HashSet<TileType>();
      for (int j = 0; j < grid.length; j++) {
        if (!seenTypes.contains(grid[i][j].getType())) {
          seenTypes.add(grid[i][j].getType());
          countTypes++;
        }
      }
      if (countTypes <= 3) {
        countColumns++;
        if (countColumns > 3) {
          return true;
        }
      }
    }
    return false;
  };

  /*
   * #6
   * Rule that checks if there are at least eight different tiles of the same type
   *
   */
  public static final Predicate<Library> checkEightOfSameType = (l) -> {
    Tile[][] grid = l.getLibraryGrid();
    Map<TileType, Integer> checkCount = new HashMap<TileType, Integer>();
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[i].length; j++) {
        if (!checkCount.containsKey(grid[i][j].getType())) {
          checkCount.put(grid[i][j].getType(), 1);
        } else {
          int oldCount = checkCount.get(grid[i][j].getType());
          checkCount.put(grid[i][j].getType(), oldCount + 1);
        }
        if (checkCount.get(grid[i][j].getType()) >= 8) {
          return true;
        }
      }
    }
    return false;
  };

  /*
   * #7
   * Rule that checks if the diagonals are filled with tiles of the same type
   *
   */
  public static final Predicate<Library> checkDiagonalsSameType = l ->{
    Tile[][] grid = l.getLibraryGrid();
    for(int i = 0; i < grid.length; i++){
      
    }
  };
  
  /*
   * #8
   * Rule that checks if
   *
   */
  public static final Predicate<Library> = l ->{

  };

  /*
   * #9
   * Rule that checks if
   *
   */
  public static final Predicate<Library> = l ->{

  };

  /*
   * #10
   * Rule that checks if
   *
   */
  public static final Predicate<Library> = l ->{

  };

  /*
   * #11
   * Rule that checks if
   *
   */
  public static final Predicate<Library> = l ->{

  };

  /*
   * #12
   * Rule that checks if
   *
   */
  public static final Predicate<Library> = l ->{

  };

  /*
   * The list of {@link SharedPattern} containing all the 12 different pattern
   * rules with their lambda functions
   *
   */
  /*
   * public static final List<SharedPattern> patterns = new
   * LinkedList<SharedPattern>(
   * 
   * )
   */
}