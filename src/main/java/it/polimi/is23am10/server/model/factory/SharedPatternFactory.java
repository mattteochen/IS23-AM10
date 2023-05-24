package it.polimi.is23am10.server.model.factory;

import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import it.polimi.is23am10.server.model.items.bookshelf.Bookshelf;
import it.polimi.is23am10.server.model.items.tile.Tile;
import it.polimi.is23am10.server.model.items.tile.Tile.TileType;
import it.polimi.is23am10.server.model.pattern.SharedPattern;

/**
 * Shared pattern factory object.
 * 
 * <p>
 * NOTE: if not specified, each iteration of the player's bookshelf inside the
 * functions is gonna be first over rows,then columns
 * </p>
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */

public final class SharedPatternFactory {

  /**
   * Private constructor.
   * 
   */
  private SharedPatternFactory() {

  }

  /**
   * Number of occurrences need to comply checkTwoAdjacent rule.
   */
  private static final int TWO_ADJACENT_OCC = 6;

  /**
   * Number of occurrences need to comply checkFourAdjacent rule.
   */
  private static final int FOUR_ADJACENT_OCC = 4;

  /**
   * Number of occurrences need to comply checkSquares rule.
   */
  private static final int SQUARES_OCC = 2;

  /**
   * Number of occurrences need to comply checkMaxThreeTypesCol rule.
   */
  private static final int MAX_THREE_TYPES_COL_OCC = 3;

  /**
   * Number of occurrences need to comply checkEightDiff rule.
   */
  private static final int EIGHT_DIFF_OCC = 8;

  /**
   * Number of occurrences need to comply checkMaxThreeTypesRow rule.
   */
  private static final int MAX_THREE_TYPES_ROW_OCC = 4;

  /**
   * Number of occurrences need to comply checkTwoColsAllDiff rule.
   */
  private static final int COL_ALL_DIFF_OCC = 2;

  /**
   * Number of occurrences need to comply checkTwoRowsAllDiff rule.
   */
  private static final int ROW_ALL_DIFF_OCC = 2;

  /**
   * The random generator instance.
   * 
   */
  private static final Random random = new Random();

  /**
   * #1 in rulebook, #4 in images
   * Rule that checks if there are at least six couples of the same tile type in
   * adjacent positions (row or column).
   *
   */
  public static final Predicate<Bookshelf> checkTwoAdjacent = bs -> {
    int count = 0;
    Tile[][] grid = bs.getBookshelfGrid();
    Set<String> coordsAdjAlreadyCounted = new HashSet<>();

    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[i].length; j++) {
        if (i < grid.length - 1 && grid[i][j].equals(grid[i + 1][j]) && !grid[i][j].isEmpty()
            && (!coordsAdjAlreadyCounted.contains(String.valueOf(i) + String.valueOf(j))
                && !coordsAdjAlreadyCounted.contains(String.valueOf(i + 1) + String.valueOf(j)))) {
          coordsAdjAlreadyCounted.add(String.valueOf(i) + String.valueOf(j));
          coordsAdjAlreadyCounted.add(String.valueOf(i + 1) + String.valueOf(j));
          count++;
          if (count >= TWO_ADJACENT_OCC) {
            return true;
          }
        }

        if (j < grid[i].length - 1 && grid[i][j].equals(grid[i][j + 1]) && !grid[i][j + 1].isEmpty()
            && (!coordsAdjAlreadyCounted.contains(String.valueOf(i) + String.valueOf(j))
                && !coordsAdjAlreadyCounted.contains(String.valueOf(i) + String.valueOf(j + 1)))) {
          coordsAdjAlreadyCounted.add(String.valueOf(i) + String.valueOf(j));
          coordsAdjAlreadyCounted.add(String.valueOf(i) + String.valueOf(j + 1));
          count++;
          if (count >= TWO_ADJACENT_OCC) {
            return true;
          }
        }
      }
    }
    return false;
  };

  /*
   * #2 in rulebook, #3 in images
   * Rule that checks if the elements on all the corners match.
   *
   */
  public static final Predicate<Bookshelf> checkCornersMatch = bs -> {
    Tile[][] grid = bs.getBookshelfGrid();
    if (grid[0][0].isEmpty()) {
      return false;
    }
    return (grid[0][0].equals(grid[0][grid[0].length - 1]) &&
        grid[0][0].equals(grid[grid.length - 1][0]) &&
        grid[0][0].equals(grid[grid.length - 1][grid[0].length - 1]));
  };

  /**
   * #3 in rulebook, #8 in images
   * Rule that checks if there are at least 4 separate groups of 4 elements of
   * the same type in adjacent positions(row or column).
   *
   */
  public static final Predicate<Bookshelf> checkFourAdjacent = bs -> {
    int count = 0;
    Tile[][] grid = bs.getBookshelfGrid();
    Set<String> coordsAdjAlreadyCounted = new HashSet<>();

    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[i].length; j++) {
        if (i < grid.length - 3 && grid[i][j].equals(grid[i + 1][j]) && grid[i][j].equals(grid[i + 2][j])
            && grid[i][j].equals(grid[i + 3][j]) && !grid[i][j].isEmpty()
            && (!coordsAdjAlreadyCounted.contains(String.valueOf(i) + String.valueOf(j))
                && !coordsAdjAlreadyCounted.contains(String.valueOf(i + 1) + String.valueOf(j))
                && !coordsAdjAlreadyCounted.contains(String.valueOf(i + 2) + String.valueOf(j))
                && !coordsAdjAlreadyCounted.contains(String.valueOf(i + 3) + String.valueOf(j)))) {
          coordsAdjAlreadyCounted.add(String.valueOf(i) + String.valueOf(j));
          coordsAdjAlreadyCounted.add(String.valueOf(i + 1) + String.valueOf(j));
          coordsAdjAlreadyCounted.add(String.valueOf(i + 2) + String.valueOf(j));
          coordsAdjAlreadyCounted.add(String.valueOf(i + 3) + String.valueOf(j));
          count++;
          if (count >= FOUR_ADJACENT_OCC) {
            return true;
          }
        }

        if (j < grid[i].length - 3 && grid[i][j].equals(grid[i][j + 1]) && grid[i][j].equals(grid[i][j + 2])
            && grid[i][j].equals(grid[i][j + 3]) && !grid[i][j].isEmpty()
            && (!coordsAdjAlreadyCounted.contains(String.valueOf(i) + String.valueOf(j))
                && !coordsAdjAlreadyCounted.contains(String.valueOf(i) + String.valueOf(j + 1))
                && !coordsAdjAlreadyCounted.contains(String.valueOf(i) + String.valueOf(j + 2))
                && !coordsAdjAlreadyCounted.contains(String.valueOf(i) + String.valueOf(j + 3)))) {
          coordsAdjAlreadyCounted.add(String.valueOf(i) + String.valueOf(j));
          coordsAdjAlreadyCounted.add(String.valueOf(i) + String.valueOf(j + 1));
          coordsAdjAlreadyCounted.add(String.valueOf(i) + String.valueOf(j + 2));
          coordsAdjAlreadyCounted.add(String.valueOf(i) + String.valueOf(j + 3));
          count++;
          if (count >= FOUR_ADJACENT_OCC) {
            return true;
          }
        }
      }
    }
    return false;
  };

  /**
   * Support function that checks if there's a 2x2 square of tiles with the same
   * type.
   * 
   * @param startRow Starting row from where I want to check if it's a valid
   *                 square.
   * @param startCol Starting col from where I want to check if it's a valid
   *                 square.
   * @param grid     bookshelf grid.
   * @return True if the square is present.
   */
  private static final boolean isValidSquare(int startRow, int startCol, Tile[][] grid) {
    if (startRow >= grid.length || startCol >= grid[0].length || startRow < 0 || startCol < 0) {
      return false;
    }
    TileType currType = grid[startRow][startCol].getType();
    return (grid[startRow][startCol].equals(grid[startRow + 1][startCol])
        && grid[startRow][startCol].equals(grid[startRow][startCol + 1])
        && grid[startRow][startCol].equals(grid[startRow + 1][startCol + 1]) && currType != TileType.EMPTY);
  }

  /*
   * #4 in rulebook, #1 in images
   * Rule that checks if there are at least two 2x2 squares of tiles of the same
   * type (the type has to be the same for both the squares).
   * 
   */
  public static final Predicate<Bookshelf> checkSquares = bs -> {
    Tile[][] grid = bs.getBookshelfGrid();
    EnumMap<TileType, Integer> checkCount = new EnumMap<>(TileType.class);

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

  /**
   * #5 both in rulebook and images
   * Rule that checks if there are at least three columns containing maximum 3
   * different types of tiles.
   *
   */
  public static final Predicate<Bookshelf> checkMaxThreeTypesInColumn = bs -> {
    int countedColumns = 0;
    Tile[][] grid = bs.getBookshelfGrid();

    // here we want to check every column so we are iterating first over columns
    for (int i = 0; i < grid[0].length; i++) {
      Set<TileType> seenTypes = new HashSet<>();
      boolean isFull = true;
      for (int j = 0; j < grid.length; j++) {
        if (grid[j][i].isEmpty()){
          isFull = false;
        }
        if (!grid[j][i].isEmpty() && !seenTypes.contains(grid[j][i].getType())) {
          seenTypes.add(grid[j][i].getType());
        }
      }
      if (seenTypes.size() <= 3 && isFull) {
        countedColumns++;
        if (countedColumns >= MAX_THREE_TYPES_COL_OCC) {
          return true;
        }
      }
    }
    return false;
  };

  /*
   * #6 in rulebook, #9 in images 
   * Rule that checks if there are at least eight different tiles of the same
   * type.
   *
   */
  public static final Predicate<Bookshelf> checkEightOfSameType = bs -> {
    Tile[][] grid = bs.getBookshelfGrid();
    EnumMap<TileType, Integer> checkCount = new EnumMap<>(TileType.class);
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

  /**
   * Supporting method checking ascendent diagonal in Bookshelf.
   * 
   * @param startingRowOffset tells us if the diagonal is shifted vertically.
   * @param grid              bookshelf grid
   * @return True if diagonal is present.
   */
  private static final boolean checkAscDiagonal(int startingRowOffset, Tile[][] grid) {
    if (startingRowOffset >= grid.length || startingRowOffset < 0) {
      return false;
    }
    for (int i = 0; i < grid[0].length - 1; i++) {
      if (!grid[i + startingRowOffset][i].equals(grid[i + 1 + startingRowOffset][i + 1])
          || grid[i + startingRowOffset][i].isEmpty()) {
        break;
      }
      if (i == grid[0].length - 2) {
        return true;
      }
    }
    return false;
  }

  /**
   * Supporting method checking descendent diagonal in Bookshelf.
   * 
   * @param startingRowOffset tells us if the diagonal is shifted vertically.
   * @param grid              bookshelf grid.
   * @return True if diagonal is present.
   */
  private static final boolean checkDescDiagonal(int startingRowOffset, Tile[][] grid) {
    if (startingRowOffset >= grid.length) {
      return false;
    }
    for (int i = 0; i < grid[0].length - 1; i++) {
      if (!grid[grid.length - 1 - i - startingRowOffset][i]
          .equals(grid[grid.length - 2 - i - startingRowOffset][i + 1])
          || grid[grid.length - 1 - i - startingRowOffset][i].isEmpty()) {
        break;
      }
      if (i == grid[0].length - 2) {
        return true;
      }
    }
    return false;
  }

  /**
   * #7 in rulebook, #11 in images
   * Rule that checks if the diagonals are filled with tiles of the same type.
   *
   */
  public static final Predicate<Bookshelf> checkDiagonalsSameType = bs -> {
    Tile[][] grid = bs.getBookshelfGrid();

    // I'm cycling with i=0,1 meaning that I'm checking both the diagonals if the
    // starting row is the first one(i=0) or the second one (i=1)
    for (int i = 0; i <= 1; i++) {
      if (checkAscDiagonal(i, grid) || checkDescDiagonal(i, grid)) {
        return true;
      }
    }

    return false;
  };

  /**
   * #8 in rulebook, #7 in images
   * Rule that checks if there are maximum three different types in at least 4
   * rows.
   *
   */
  public static final Predicate<Bookshelf> checkMaxThreeTypesInRow = bs -> {
    int countRows = 0;
    Tile[][] grid = bs.getBookshelfGrid();
    for (int i = 0; i < grid.length; i++) {
      Set<TileType> seenTypes = new HashSet<>();
      boolean isFull = true;
      for (int j = 0; j < grid[0].length; j++) {
        if (grid[i][j].isEmpty()) {
          isFull = false;
        }
        if (!grid[i][j].isEmpty() && !seenTypes.contains(grid[i][j].getType())) {
          seenTypes.add(grid[i][j].getType());
        }
      }

      if (seenTypes.size() <= 3 && isFull) {
        countRows++;
        if (countRows >= MAX_THREE_TYPES_ROW_OCC) {
          return true;
        }
      }
    }
    return false;
  };

  /**
   * #9 in rulebook, #2 in images
   * Rule that checks if there are at least two columns with all the elements of
   * different type.
   *
   */
  public static final Predicate<Bookshelf> checkTwoColumnAllDiff = bs -> {
    int countColumns = 0;
    Tile[][] grid = bs.getBookshelfGrid();
    /*
     * here I am iterating over columns first since I want to check if in a column
     * there are all different types
     * 
     */
    for (int i = 0; i < grid[0].length; i++) {
      Set<TileType> seenTypes = new HashSet<>();
      for (int j = 0; j < grid.length; j++) {
        if (grid[j][i].isEmpty() || seenTypes.contains(grid[j][i].getType())) {
          break;
        }
        seenTypes.add(grid[j][i].getType());
        if (j == grid.length - 1) {
          countColumns++;
        }
      }
      if (countColumns >= COL_ALL_DIFF_OCC) {
        return true;
      }
    }
    return false;

  };

  /**
   * #10 in rulebook, #6 in images
   * Rule that checks if there are at least two rows full of different types of
   * tiles.
   *
   */
  public static final Predicate<Bookshelf> checkTwoRowsAllDiff = bs -> {
    int countRows = 0;
    Tile[][] grid = bs.getBookshelfGrid();
    for (int i = 0; i < grid.length; i++) {
      Set<TileType> seenTypes = new HashSet<>();
      for (int j = 0; j < grid[0].length; j++) {
        if (grid[i][j].isEmpty() || seenTypes.contains(grid[i][j].getType())) {
          break;
        }
        seenTypes.add(grid[i][j].getType());
        if (j == grid[0].length - 1) {
          countRows++;
        }
      }
      if (countRows >= ROW_ALL_DIFF_OCC) {
        return true;
      }
    }
    return false;

  };

  /**
   * Support function that checks, starting from a tile in the grid, if it's part
   * of an X shape of tiles with the same type.
   * 
   * @param row  Starting row index from where I start to check.
   * @param col  Starting col index from where I start to check.
   * @param grid Bookshelf grid.
   * @return True if X shape is present.
   * 
   */
  private static final boolean checkXShape(int row, int col, Tile[][] grid) {
    if (row >= grid.length || col >= grid[0].length || row < 0 || col < 0) {
      return false;
    }
    return (grid[row][col].equals(grid[row + 2][col]) &&
        grid[row][col].equals(grid[row + 1][col + 1]) &&
        grid[row][col].equals(grid[row + 2][col + 2]) &&
        grid[row][col].equals(grid[row][col + 2]) && !grid[row][col].isEmpty());
  }

  /**
   * #11 in rulebook, #10 in images
   * Rule that checks if there are 5 tiles of the same type on a 'X' shape.
   *
   */
  public static final Predicate<Bookshelf> checkTilesXShape = bs -> {
    Tile[][] grid = bs.getBookshelfGrid();

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
   * #12 both in rulebook and images
   * Rule that checks if the columns in the bookshelf are ordered (asc o desc) and
   * the rest of the bookshelf is filled with {@link TileType#EMPTY}.
   *
   */
  public static final Predicate<Bookshelf> checkOrderedBookshelfColumns = bs -> {
    Tile[][] grid = bs.getBookshelfGrid();

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

  /**
   * The list of {@link SharedPattern} containing all the 12 different
   * patterns.
   * rules with their lambda functions
   *
   */
  private static final List<SharedPattern<Predicate<Bookshelf>>> patternsArray = List.of(
      (new SharedPattern<Predicate<Bookshelf>>(checkTwoAdjacent, 4)),
      (new SharedPattern<Predicate<Bookshelf>>(checkCornersMatch, 8)),
      (new SharedPattern<Predicate<Bookshelf>>(checkFourAdjacent, 3)),
      (new SharedPattern<Predicate<Bookshelf>>(checkSquares, 1)),
      (new SharedPattern<Predicate<Bookshelf>>(checkMaxThreeTypesInColumn,5)),
      (new SharedPattern<Predicate<Bookshelf>>(checkEightOfSameType,9)),
      (new SharedPattern<Predicate<Bookshelf>>(checkDiagonalsSameType,11)),
      (new SharedPattern<Predicate<Bookshelf>>(checkMaxThreeTypesInRow, 7)),
      (new SharedPattern<Predicate<Bookshelf>>(checkTwoColumnAllDiff,2)),
      (new SharedPattern<Predicate<Bookshelf>>(checkTwoRowsAllDiff, 6)),
      (new SharedPattern<Predicate<Bookshelf>>(checkTilesXShape, 10)),
      (new SharedPattern<Predicate<Bookshelf>>(checkOrderedBookshelfColumns, 12)));

  /**
   * Method used to get random SharedPattern between the 12 possible.
   *
   * @param usedPatterns a List of {@link SharedPattern} storing the already used
   *                     patterns.
   * @return a random pattern between the 12 possible.
   */
  public static SharedPattern<Predicate<Bookshelf>> getNotUsedPattern(
      List<SharedPattern<Predicate<Bookshelf>>> usedPatterns) {
    if (usedPatterns.isEmpty()) {
      return patternsArray.get(random.nextInt(patternsArray.size()));
    } else {
      List<SharedPattern<Predicate<Bookshelf>>> unusedPatterns = patternsArray.stream()
          .filter(pattern -> !usedPatterns.contains(pattern))
          .collect(Collectors.toList());
      return unusedPatterns.get(random.nextInt(unusedPatterns.size()));
    }
  }
}
