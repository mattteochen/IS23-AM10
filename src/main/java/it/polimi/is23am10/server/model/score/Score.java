package it.polimi.is23am10.server.model.score;

import it.polimi.is23am10.server.model.items.bookshelf.Bookshelf;
import it.polimi.is23am10.server.model.items.bookshelf.exceptions.BookshelfGridColIndexOutOfBoundsException;
import it.polimi.is23am10.server.model.items.bookshelf.exceptions.BookshelfGridRowIndexOutOfBoundsException;
import it.polimi.is23am10.server.model.items.card.PrivateCard;
import it.polimi.is23am10.server.model.items.card.exceptions.NegativeMatchedBlockCountException;
import it.polimi.is23am10.server.model.items.card.exceptions.NullMatchedBlockCountException;
import it.polimi.is23am10.server.model.items.card.exceptions.NullScoreBlockListException;
import it.polimi.is23am10.server.model.items.scoreblock.ScoreBlock;
import it.polimi.is23am10.server.model.items.tile.Tile;
import it.polimi.is23am10.server.model.items.tile.Tile.TileType;
import it.polimi.is23am10.server.model.player.exceptions.NullPlayerBookshelfException;
import it.polimi.is23am10.utils.exceptions.NullIndexValueException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * The Score class definition.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */

public final class Score implements Serializable {
  /**
   * Integer referencing the extra point given to the first player
   * to complete their bookshelf.
   * 
   */
  private Integer extraPoint;

  /**
   * Integer referencing the points the player receives from
   * the groups of same type tiles in their bookshelf.
   * 
   */
  private Integer bookshelfPoints;

  /**
   * Integer referencing the points the player receives from
   * completing shared goals.
   * 
   */
  private Integer scoreBlockPoints;

  /**
   * Integer referencing the points the player receives from
   * completing their private (secret) goal.
   * 
   */
  private Integer privatePoints;

  /**
   * Map that allows the conversion from number of matches 
   * in private cards to points received.
   */
  public static final Map<Integer, Integer> privateCardPointsMap = Map.of(
      0, 0,
      1, 1,
      2, 2,
      3, 4,
      4, 6,
      5, 9,
      6, 12
      );

  /**
   * Map that allows the conversion from number of groups
   * in player's bookshelf to points received.
   */
  private static final Map<Integer, Integer> bookshelfPointsMap = Map.of(
      3, 2,
      4, 3,
      5, 5,
      6, 8
      );

  /**
   * Integer representing the minimum group size
   * for counting bookshelf points.
   */
  private static final Integer MIN_GROUP_SIZE = 3;

  /**
   * Integer representing the maximum "useful" group
   * size for counting bookshelf points. Bigger groups
   * count as groups of MAX_GROUP_SIZE
   */
  private static final Integer MAX_GROUP_SIZE = 6;

  /**
   * Constructor.
   * Set all the default values.
   * 
   */
  public Score() {
    extraPoint = 0;
    bookshelfPoints = 0;
    scoreBlockPoints = 0;
    privatePoints = 0;
  }

  /**
   * Copy constructor to generate another copy of same Score.
   * 
   * @param toCopy score to copy
   */
  public Score(Score toCopy) {
    bookshelfPoints = toCopy.getBookshelfPoints();
    extraPoint = toCopy.getExtraPoint();
    scoreBlockPoints = toCopy.getScoreBlockPoints();
    privatePoints = toCopy.getPrivatePoints();
  }

  /**
   * extraPoint setter. Value can be only set to 1.
   * 
   */
  public void setExtraPoint() {
    this.extraPoint = 1;
  }

  /**
   * bookshelfPoints setter.
   * 
   * @param bs The bookshelf object to check for groups in.
   * @throws NullIndexValueException If the index provided is null.
   * @throws BookshelfGridRowIndexOutOfBoundsException If the bookshelf row index is out of bounds.
   * @throws BookshelfGridColIndexOutOfBoundsException If the bookshelf column index is out of bounds.
   * @throws NullPlayerBookshelfException If bookshelf is null.
   * @throws NullPointerException Generic NPE.
   * 
   */
  public void setBookshelfPoints(Bookshelf bs) 
      throws NullPointerException, BookshelfGridColIndexOutOfBoundsException,
      BookshelfGridRowIndexOutOfBoundsException, NullIndexValueException, NullPlayerBookshelfException {
    if (bs == null) {
      throw new NullPlayerBookshelfException("[Score:setBookshelfPoints]");
    }

    List<Integer> groupsSizes = new ArrayList<>();
    Integer[][] visitedMap = new Integer[Bookshelf.BOOKSHELF_ROWS][Bookshelf.BOOKSHELF_COLS];

    // Traverse each tile in the grid
    for (int i = 0; i < Bookshelf.BOOKSHELF_ROWS; i++) {
      for (int j = 0; j < Bookshelf.BOOKSHELF_COLS; j++) {
        // Check if the tile has already been visited
        if (visitedMap[i][j] == null) {
          Tile tile = bs.getBookshelfGridAt(i, j);
          if (!tile.isEmpty()) {
            int groupSize = countAdjacentTilesRecursive(bs, i, j, visitedMap, tile.getType());

            // I only consider valid groups.
            if (groupSize >= MIN_GROUP_SIZE && groupSize <= MAX_GROUP_SIZE) {
              groupsSizes.add(groupSize);
            }
            if (groupSize > MAX_GROUP_SIZE) {
              groupsSizes.add(MAX_GROUP_SIZE);
            }
          }
        }
      }
    }

    bookshelfPoints = groupsSizes.stream().mapToInt(bookshelfPointsMap::get).sum();
  }

  /**
   * scoreBlockPoints setter.
   * 
   * @param scoreBlocks The scoreblock list to get points from.
   * @throws NullScoreBlockListException If the list of scoreblocks is null.
   * 
   */
  public void setScoreBlockPoints(List<ScoreBlock> scoreBlocks) throws NullScoreBlockListException {
    if (scoreBlocks == null) {
      throw new NullScoreBlockListException("[Score:setScoreBlockPoints]");
    }
    scoreBlockPoints = scoreBlocks.stream().mapToInt(sb -> sb.getScore()).sum();
  }

  /**
   * privatePoints setter.
   * 
   * @param pc The private card to get points from.
   * @throws NegativeMatchedBlockCountException If the number of matched blocks to set is negative.
   * @throws NullMatchedBlockCountException If the number of matched blocks to set is null.
   * @throws NullPointerException Generic NPE.
   * 
   */
  public void setPrivatePoints(Bookshelf bs, PrivateCard pc) throws NullPointerException, NullMatchedBlockCountException, NegativeMatchedBlockCountException {
    if (pc == null) {
      throw new NullPointerException("[Score:setPrivatePoints]");
    }
    pc.setMatchedBlocksCount(pc.getPattern().getRule().apply(bs));
    privatePoints = privateCardPointsMap.get(pc.getMatchedBlocksCount());
  }

  public void obfuscatePrivatePoints() {
    privatePoints = -1;
  }

  /**
   * extraPoints getter.
   * 
   * @return The extra points value.
   * 
   */
  public Integer getExtraPoint() {
    return extraPoint;
  }

  /**
   * bookshelfPoints getter.
   * 
   * @return The bookshelf points value.
   * 
   */
  public Integer getBookshelfPoints() {
    return bookshelfPoints;
  }

  /**
   * scoreBlockPoints getter.
   * 
   * @return The score block points value.
   * 
   */
  public Integer getScoreBlockPoints() {
    return scoreBlockPoints;
  }

  /**
   * privatePoints getter.
   * 
   * @return The private points value.
   * 
   */
  public Integer getPrivatePoints() {
    return privatePoints;
  }

  /**
   * Helper function to help with BFS in bookshelf to find groups of same tile type.
   * NOTE: this function should be called from setBookshelfPoints()
   * 
   * @param bs Bookshelf to check for groups in
   * @param row Row index 
   * @param col Column index
   * @param visitedMap map to prevent repeatedly checking same spots
   * @param type type of tile to check for
   * @return integer representing the count of items in that group
   * @throws BookshelfGridColIndexOutOfBoundsException If the bookshelf column index is out of bounds.
   * @throws BookshelfGridRowIndexOutOfBoundsException If the bookshelf row index is out of bounds.
   * @throws NullIndexValueException If the index provided is null.
   */
  private int countAdjacentTilesRecursive(Bookshelf bs, int row, int col, Integer[][] visitedMap, TileType type) 
      throws BookshelfGridColIndexOutOfBoundsException, BookshelfGridRowIndexOutOfBoundsException, NullIndexValueException {
    // Check if the current tile is within the grid bounds and has the same type as the original tile, is not empty not visited
    if (
        row < 0 || row >= Bookshelf.BOOKSHELF_ROWS 
        || col < 0 || col >= Bookshelf.BOOKSHELF_COLS 
        || bs.getBookshelfGridAt(row, col).getType() != type 
        || bs.getBookshelfGridAt(row, col).getType() == TileType.EMPTY 
        || visitedMap[row][col] != null
    ) {
      return 0;
    }
    
    // Mark the current tile as visited by setting it to 1
    visitedMap[row][col] = 1;
    
    // Recursively count the adjacent tiles with the same type
    int count = 1;
    count += countAdjacentTilesRecursive(bs, row + 1, col, visitedMap, type);
    count += countAdjacentTilesRecursive(bs, row - 1, col, visitedMap, type);
    count += countAdjacentTilesRecursive(bs, row, col + 1, visitedMap, type);
    count += countAdjacentTilesRecursive(bs, row, col - 1, visitedMap, type);
    
    return count;
  }

  /**
   * Getter method that returns the total score
   * computing it from all the available scores.
   * 
   * @return total score.
   */
  public Integer getTotalScore() {
    return extraPoint + scoreBlockPoints + privatePoints + bookshelfPoints;
  }

  /**
   * Method used to retrieve the total score from a
   * possibly obfuscated score. It ignores the invalidated privatescore.
   * Used for sorting purposes in CLI.
   * 
   * @return visible score.
   */
  public Integer getVisibleScore() {
    return (privatePoints != -1) ? getTotalScore() : extraPoint + scoreBlockPoints + bookshelfPoints;
  }

  /**
   * Method used to retrieve a string representing the
   * total score. If score is obfuscated (private card points)
   * it returns a partial score and a graphic indication of possibly missing points.
   * 
   * @return String representing total score.
   */
  public String getStringTotalScore() {
    return (privatePoints != -1) ? getTotalScore().toString() : String.format("%d (+?)", getVisibleScore());
  }

  /**
   * {@inheritDoc}
   * 
   */
  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Score)) {
      return false;
    }
    Score sc = (Score) obj;
    return (
      extraPoint == sc.getExtraPoint() 
      && scoreBlockPoints == sc.getScoreBlockPoints() 
      && privatePoints == sc.getPrivatePoints()
      && bookshelfPoints == sc.getBookshelfPoints());
  }

  /**
   * {@inheritDoc}
   * 
   */
  @Override
  public int hashCode() {
    return extraPoint.hashCode() * scoreBlockPoints.hashCode() * privatePoints.hashCode() * bookshelfPoints.hashCode();
  }
}
