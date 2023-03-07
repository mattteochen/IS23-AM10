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
        if (countColumns >= 3) {
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
  public static final Predicate<Library> checkDiagonalsSameType = l -> {
    Tile[][] grid = l.getLibraryGrid();

    for (int i = 0; i < grid[0].length - 1; i++) {
      if (!grid[i][i].equals(grid[i + 1][i + 1])) {
        break;
      } else if (i == grid[0].length - 1) {
        return true;
      }
    }

    for (int i = 0; i < grid[0].length - 1; i++) {
      if (!grid[i + 1][i].equals(grid[i + 2][i + 1])) {
        break;
      } else if (i == grid[0].length - 1) {
        return true;
      }
    }

    for (int i = 0; i < grid[0].length - 1; i++) {
      if (!grid[grid.length - 1 - i][i].equals(grid[grid.length - 2 - i][i + 1])) {
        break;
      } else if (i == grid[0].length - 1) {
        return true;
      }
    }

    for (int i = 0; i < grid[0].length - 1; i++) {
      if (!grid[grid.length - 2 - i][i].equals(grid[grid.length - 3 - i][i + 1])) {
        break;
      } else if (i == grid[0].length - 1) {
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
  public static final Predicate<Library> checkMaxThreeTypesInRow = l -> {
    int countRows = 0;
    Tile[][] grid = l.getLibraryGrid();
    for (int i = 0; i < grid.length; i++) {
      int countTypes = 0;
      Set<TileType> seenTypes = new HashSet<TileType>();
      for (int j = 0; j < grid[0].length; j++) {
        if (!seenTypes.contains(grid[j][i].getType())) {
          seenTypes.add(grid[j][i].getType());
          countTypes++;
        }
      }
      if (countTypes <= 4) {
        countRows++;
        if (countRows >= 4) {
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
  public static final Predicate<Library> checkTwoColumnAllDiff = l -> {
    int countColumns = 0;
    Tile[][] grid = l.getLibraryGrid();
    for (int i = 0; i < grid[i].length; i++) {
      int countTypes = 0;
      Set<TileType> seenTypes = new HashSet<TileType>();
      for (int j = 0; j < grid.length; j++) {
        if (!seenTypes.contains(grid[i][j].getType())) {
          seenTypes.add(grid[i][j].getType());
          countTypes++;
        } else {
          break;
        }
      }
      if (countTypes == 6) {
        countColumns++;
        if (countColumns >= 2) {
          return true;
        }
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
  public static final Predicate<Library> checkTwoRowsAllDiff = l -> {
    int countRows = 0;
    Tile[][] grid = l.getLibraryGrid();
    for (int i = 0; i < grid.length; i++) {
      int countTypes = 0;
      Set<TileType> seenTypes = new HashSet<TileType>();
      for (int j = 0; j < grid[j].length; j++) {
        if (!seenTypes.contains(grid[j][i].getType())) {
          seenTypes.add(grid[j][i].getType());
          countTypes++;
        } else {
          break;
        }
      }
      if (countTypes == 5) {
        countRows++;
        if (countRows >= 2) {
          return true;
        }
      }
    }
    return false;

  };

  /*
   * #11
   * Rule that checks if there are 5 tiles of the same type on a 'X' shape
   *
   */
  public static final Predicate<Library> checkTilesXShape = l -> {
    Tile[][] grid = l.getLibraryGrid();
    for (int i = 0; i < grid[0].length - 2; i++) {
      for (int j = 0; j < grid.length - 2; j++) {
        if (grid[i][j].equals(grid[i + 2][j]) &&
            grid[i][j].equals(grid[i + 1][j + 1]) &&
            grid[i][j].equals(grid[i + 2][j + 2]) &&
            grid[i][j].equals(grid[i][j + 2])) {
          return true;
        }
      }
    }
    return false;
  };

  /*
   * #12
   * Rule that checks if the columns in the library are ordered (asc o desc) and
   * the rest of the library is filled with null
   *
   */
  public static final Predicate<Library> checkOrderedLibraryColumns = l -> {
    Tile[][] grid = l.getLibraryGrid();

    Predicate<Tile[][]> checkDescOrder = g -> {
      for (int i = 1; i < grid[0].length; i++) {
        for (int j = i; j < grid.length; j++) {
          if (grid[i][j] != null) {
            return false;
          }
        }
      }
      return true;
    };

    Predicate<Tile[][]> checkAscOrder = g -> {
      for (int i = 0; i < grid.length; i++) {
        for (int j = 0; j < grid[0].length - i; j++) {
          if (grid[j][i] != null) {
            return false;
          }
        }
      }
      return true;
    };

    return (checkAscOrder.test(grid) && checkDescOrder.test(grid));

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