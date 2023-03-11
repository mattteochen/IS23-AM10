package it.polimi.is23am10.items.pattern;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

import javax.swing.text.StyledEditorKit.BoldAction;

import it.polimi.is23am10.items.library.Library;
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

  /**
   * The private constructor for the {@link SharedPatternFactory}.
   * 
   */
  private SharedPatternFactory() {
  }

  /**
   * All the number of occurrencies that each rule has to meet.
   * 
   */
  private static final int TWO_ADJACENTS_OCC = 6;
  private static final int FOUR_ADJACENTS_OCC = 4;
  private static final int SQUARES_OCC = 2;
  private static final int MAX_THREE_TYPES_COL_OCC = 3;
  private static final int EIGHT_DIFF_OCC = 8;
  private static final int MAX_THREE_TYPES_ROW_OCC = 4;
  private static final int COL_ALL_DIFF_OCC = 2;
  private static final int ROW_ALL_DIFF_OCC = 2;

  /*
   * NOTE: if not specified, each iteration of the player's library inside the
   * functions is gonna be first over rows,then columns
   */

  /*
   * #1
   * Rule that checks if there are at least six couples of the same tile type in
   * adjacent positions (row or column).
   *
   */
  public static final Predicate<Library> checkTwoAdjacents = lib -> {
    int count = 0;
    Tile[][] grid = lib.getLibraryGrid();
    Map<Integer,Map<Integer,Boolean>> coordsAdjAlreadyCounted = new <Integer,Map<Integer,Boolean>> HashMap();

    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[i].length; j++) {
        if(!coordsAdjAlreadyCounted.get(i).get(j)){
          if (i < grid.length - 1 && grid[i][j].equals(grid[i + 1][j])) {
            if(coordsAdjAlreadyCounted.containsKey(i)){
              coordsAdjAlreadyCounted.get(i).put(j,true);
            }else{
              coordsAdjAlreadyCounted.put(i, Map.of(j,true));  
            }

            if(coordsAdjAlreadyCounted.containsKey(i+1)){
              coordsAdjAlreadyCounted.get(i+1).put(j,true);
            }else{
              coordsAdjAlreadyCounted.put(i+1, Map.of(j,true));
            }

            count++;
            if (count >= TWO_ADJACENTS_OCC) {
              return true;
            }
          }
          if (j < grid[i].length - 1 && grid[i][j].equals(grid[i][j + 1])) {
            if(coordsAdjAlreadyCounted.containsKey(i)){
              coordsAdjAlreadyCounted.get(i).put(j,true);
            }else{
              coordsAdjAlreadyCounted.put(i, Map.of(j,true));  
              coordsAdjAlreadyCounted.put(i, Map.of(j+1,true));  
            }

            if(coordsAdjAlreadyCounted.containsKey(i+1)){
              coordsAdjAlreadyCounted.get(i+1).put(j,true);
            }else{
              coordsAdjAlreadyCounted.put(i+1, Map.of(j,true));
            }
            count++;
            if (count >= TWO_ADJACENTS_OCC) {
              return true;
            }
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
  public static final Predicate<Library> checkCornersMatch = lib -> {
    Tile[][] grid = lib.getLibraryGrid();
    return (grid[0][0].equals(grid[0][grid[0].length - 1]) &&
        grid[0][0].equals(grid[grid.length - 1][0]) &&
        grid[0][0].equals(grid[grid.length - 1][grid[0].length - 1]));
  };

  /*
   * #3
   * Rule that checks if there are at least 4 separate groups of 4 elements of
   * the same type in adjacent positions(row or column)
   *
   */
  public static final Predicate<Library> checkFourAdjacents = lib -> {
    int count = 0;
    Tile[][] grid = lib.getLibraryGrid();
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[i].length; j++) {
        if (i < grid.length - 3 && grid[i][j].equals(grid[i + 1][j]) && grid[i][j].equals(grid[i + 2][j])
            && grid[i][j].equals(grid[i + 3][j])) {
          count++;
          if (count >= FOUR_ADJACENTS_OCC) {
            return true;
          }
        }

        if (j < grid[i].length - 3 && grid[i][j].equals(grid[i][j + 1]) && grid[i][j].equals(grid[i][j + 2])
            && grid[i][j].equals(grid[i][j + 3])) {
          count++;
          if (count >= FOUR_ADJACENTS_OCC) {
            return true;
          }
        }
      }
    }
    return false;
  };

  /*
   * Support function that checks if there's a 2x2 square of tiles with the same
   * type
   * 
   */
  private static final boolean isValidSquare(int startRow, int startCol, Tile[][] grid) {
    TileType currType = grid[startRow][startCol].getType();
    if (grid[startRow][startCol].equals(grid[startRow + 1][startCol])
        && grid[startRow][startCol].equals(grid[startRow][startCol + 1])
        && grid[startRow][startCol].equals(grid[startRow + 1][startCol + 1]) && currType != TileType.EMPTY) {
      return true;
    }
    return false;
  }

  /*
   * #4
   * Rule that checks if there are at least two 2x2 squares of tiles of the same
   * type (the type has to be the same for both the squares)
   * 
   */
  public static final Predicate<Library> checkSquares = lib -> {
    Tile[][] grid = lib.getLibraryGrid();
    Map<TileType, Integer> checkCount = new HashMap<TileType, Integer>();

    for (int i = 0; i < grid.length - 1; i++) {
      for (int j = 0; j < grid[i].length - 1; j++) {
        TileType currType = grid[i][j].getType();
        if (isValidSquare(i, j, grid)) {
          if (!checkCount.containsKey(currType)) {
            checkCount.put(currType, 1);
          } else {
            int oldCount = checkCount.get(currType);
            checkCount.put(currType, oldCount + 1);
            if (checkCount.get(currType) >= SQUARES_OCC) {
              return true;
            }
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
  public static final Predicate<Library> checkMaxThreeTypesInColumn = lib -> {
    int countedColumns = 0;
    Tile[][] grid = lib.getLibraryGrid();

    // here we want to check every column so we are iterating first over columns
    for (int i = 0; i < grid[i].length; i++) {
      Set<TileType> seenTypes = new HashSet<TileType>();
      for (int j = 0; j < grid.length; j++) {
        if (grid[j][i].isEmpty()) {
          break;
        }
        if (!seenTypes.contains(grid[j][i].getType())) {
          seenTypes.add(grid[j][i].getType());
        }
      }
      if (seenTypes.size() <= 3) {
        countedColumns++;
        if (countedColumns >= MAX_THREE_TYPES_COL_OCC) {
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
  public static final Predicate<Library> checkEightOfSameType = lib -> {
    Tile[][] grid = lib.getLibraryGrid();
    Map<TileType, Integer> checkCount = new HashMap<TileType, Integer>();
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[i].length; j++) {
        if (!grid[i][j].isEmpty()) {
          if (!checkCount.containsKey(grid[i][j].getType())) {
            checkCount.put(grid[i][j].getType(), 1);
          } else {
            int oldCount = checkCount.get(grid[i][j].getType());
            checkCount.put(grid[i][j].getType(), oldCount + 1);
          }
          if (checkCount.get(grid[i][j].getType()) >= EIGHT_DIFF_OCC) {
            return true;
          }
        }
      }
    }
    return false;
  };

  /*
   * Supporting methods for the rule #7 that checks the diagonals of the same type
   * in the library
   * 
   */
  private static final boolean checkAscDiagonal(int startingRowOffset, Tile[][] grid) {
    for (int i = 0; i < grid[0].length - 1; i++) {
      if (!grid[i + startingRowOffset][i].equals(grid[i + 1 + startingRowOffset][i + 1])) {
        break;
      }
      if (i == grid[0].length - 2) {
        return true;
      }
    }
    return false;
  }

  private static final boolean checkDescDiagonal(int startingRowOffset, Tile[][] grid) {
    for (int i = 0; i < grid[0].length - 1; i++) {
      if (!grid[grid.length - 1 - i - startingRowOffset][i]
          .equals(grid[grid.length - 2 - i - startingRowOffset][i + 1])) {
        break;
      }
      if (i == grid[0].length - 2) {
        return true;
      }
    }
    return false;
  }

  /*
   * #7
   * Rule that checks if the diagonals are filled with tiles of the same type
   *
   */
  public static final Predicate<Library> checkDiagonalsSameType = lib -> {
    Tile[][] grid = lib.getLibraryGrid();

    // I'm cycling with i=0,1 meaning that I'm checking both the diagonals if the
    // starting row is the first one(i=0) or the second one (i=1)
    for (int i = 0; i <= 1; i++) {
      boolean res = checkAscDiagonal(i, grid);
      if (res) {
        return true;
      }
    }

    for (int i = 0; i <= 1; i++) {
      boolean res = checkDescDiagonal(i, grid);
      if (res) {
        return true;
      }
    }

    return false;
  };

  /*
   * #8
   * Rule that checks if there are maximum three different types in at least 4
   * rows
   *
   */
  public static final Predicate<Library> checkMaxThreeTypesInRow = lib -> {
    int countRows = 0;
    Tile[][] grid = lib.getLibraryGrid();
    for (int i = 0; i < grid.length; i++) {
      int countTypes = 0;
      Set<TileType> seenTypes = new HashSet<TileType>();
      for (int j = 0; j < grid[0].length; j++) {
        if (grid[i][j].isEmpty()) {
          break;
        }
        if (!seenTypes.contains(grid[i][j].getType())) {
          seenTypes.add(grid[i][j].getType());
          countTypes++;
        }
      }

      if (countTypes <= 3) {
        countRows++;
        if (countRows >= MAX_THREE_TYPES_ROW_OCC) {
          return true;
        }
      }
    }
    return false;
  };

  /*
   * #9
   * Rule that checks if there are at least two columns with all the elements of
   * different type
   *
   */
  public static final Predicate<Library> checkTwoColumnAllDiff = lib -> {
    int countColumns = 0;
    Tile[][] grid = lib.getLibraryGrid();

    /*
     * here I am iterating over columns first since I want to check if in a column
     * there are all different types
     * 
     */
    for (int i = 0; i < grid[0].length; i++) {
      for (int j = 0; j < grid.length - 1; j++) {
        if (grid[j][i].isEmpty() || grid[j][i].equals(grid[j + 1][i])) {
          break;
        }
        if (j == grid.length - 2) {
          countColumns++;
        }
      }
      if (countColumns >= COL_ALL_DIFF_OCC) {
        return true;
      }
    }
    return false;

  };

  /*
   * #10
   * Rule that checks if there are at least two rows full of different types of
   * tiles
   *
   */
  public static final Predicate<Library> checkTwoRowsAllDiff = lib -> {
    int countRows = 0;
    Tile[][] grid = lib.getLibraryGrid();
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[0].length - 1; j++) {
        if (grid[i][j].isEmpty() || grid[i][j].equals(grid[i][j + 1])) {
          break;
        }
        if (j == grid[0].length - 2) {
          countRows++;
        }
      }
      if (countRows >= ROW_ALL_DIFF_OCC) {
        return true;
      }
    }
    return false;

  };

  /*
   * Support function that checks, starting from a tile in the grid, if it's part
   * of an X shape of tiles with the same type.
   * 
   * 
   */

  private static final boolean checkXShape(int row, int col, Tile[][] grid) {
    if (grid[row][col].equals(grid[row + 2][col]) &&
        grid[row][col].equals(grid[row + 1][col + 1]) &&
        grid[row][col].equals(grid[row + 2][col + 2]) &&
        grid[row][col].equals(grid[row][col + 2])) {
      return true;
    } else {
      return false;
    }
  }

  /*
   * #11
   * Rule that checks if there are 5 tiles of the same type on a 'X' shape
   *
   */
  public static final Predicate<Library> checkTilesXShape = lib -> {
    Tile[][] grid = lib.getLibraryGrid();

    for (int i = 0; i < grid.length - 2; i++) {
      for (int j = 0; j < grid[0].length - 2; j++) {
        if (checkXShape(i, j, grid)) {
          return true;
        }
      }
    }
    return false;
  };

  /**
   * A support function to the rule #12 that checks if the library columns are
   * ordered, given the grid and indexes
   * 
   */
  private static final boolean checkOrder(int startRow, int startCol, Tile[][] grid, boolean shifted) {
    for (int i = 0; i < grid[0].length; i++) {
      /*
       * shifted is the case in which the last row is full of non empty tile
       */
      if (shifted && grid[grid.length - 1][i].isEmpty()) {
        return false;
      }
      if (!shifted && !grid[0][i].isEmpty()) {
        return false;
      }
    }

    for (int i = startRow; i < grid.length; i++) {
      for (int j = startCol; j < grid[0].length; j++) {

      }
    }

    return false;
  }

  /*
   * #12
   * Rule that checks if the columns in the library are ordered (asc o desc) and
   * the rest of the library is filled with {@link TileType} EMPTY
   *
   */
  public static final Predicate<Library> checkOrderedLibraryColumns = lib -> {
    Tile[][] grid = lib.getLibraryGrid();

    Predicate<Tile[][]> checkDescOrder = g -> {
      for (int i = 0; i < g.length; i++) {
        for (int j = 0; j < g[0].length; j++) {
          if (j >= i && (!g[i][j].isEmpty())) {
            return false;
          }
          if (j < i && (g[i][j].isEmpty())) {
            return false;
          }
        }
      }
      return true;
    };

    Predicate<Tile[][]> checkDescOrderShifted = g -> {
      for (int i = 0; i < g.length; i++) {
        for (int j = 0; j < g[0].length; j++) {
          if (j > i && (g[i][j].getType() != TileType.EMPTY)) {
            return false;
          }
          if (j <= i && (g[i][j].getType() == TileType.EMPTY)) {
            return false;
          }
        }
      }
      return true;
    };

    Predicate<Tile[][]> checkAscOrder = g -> {
      for (int i = 0; i < g.length; i++) {
        for (int j = 0; j < g[0].length; j++) {
          if (j < g[0].length - i && (g[i][j].getType() != TileType.EMPTY)) {
            return false;
          }
          if (j >= g[0].length - i && (g[i][j].getType() == TileType.EMPTY)) {
            return false;
          }
        }
      }
      return true;
    };

    Predicate<Tile[][]> checkAscOrderShifted = g -> {
      for (int i = 0; i < g.length; i++) {
        for (int j = 0; j < g[0].length; j++) {
          if (j < g[0].length - 1 - i && (g[i][j].getType() != TileType.EMPTY)) {
            return false;
          }
          if (j >= g[0].length - 1 - i && (g[i][j].getType() == TileType.EMPTY)) {
            return false;
          }
        }
      }
      return true;
    };

    return (checkAscOrder.test(grid) || checkDescOrder.test(grid) || checkAscOrderShifted.test(grid)
        || checkDescOrderShifted.test(grid));

  };

  /*
   * * The list of {@link SharedPattern} containin all the 12 different
   * pattern
   * rules with their lambda functions
   *
   */
  public static final List<SharedPattern> patterns = new LinkedList<SharedPattern>();

}