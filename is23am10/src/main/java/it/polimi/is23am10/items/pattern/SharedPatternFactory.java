package it.polimi.is23am10.items.pattern;

import java.util.function.Predicate;
import it.polimi.is23am10.items.library.Library;
import it.polimi.is23am10.items.pattern.SharedPattern;
import it.polimi.is23am10.items.tile.Tile;

/**
 * Shared pattern factory object.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public static final class SharedPatternFactory {

  /* #1
  * Rule that checks if there are at least six couples of the same tile type in adjacent positions (row or column)
  *
  */
  private final  Predicate<Library> checkTwoAdjacents = (l) -> {
    int count = 0;
    for (int i = 0; i < l.length; i++) {
      for (int j = 0; j < l[i].length; j++) {
        if (i < l.length - 1 && l[i][j].equals(l[i + 1][j])) {
          count++;
          if (count >= 6) {
            return true;
          }
        }
        if (j < l[i].length - 1 && l[i][j].equals(l[i][j + 1])) {
          count++;
          if (count >= 6) {
            return true;
          }
        }
      }
    }
    return false;
  };

  /* #2
  * Rule that checks if the elements on all the corners match 
  *
  */
  private final Predicate<Library> checkCornersMatch = l -> 
    l[0][0].equals(l[0][l[0].length - 1]) &&
    l[0][0].equals(l[l.length - 1][0]) &&
    l[0][0].equals(l[l.length - 1][l[0].length - 1]);


  /* #3
  * Rule that checks if there are at least 4 different groups of 4 elements of the same type in adjacent positions(row or column)
  *
  */
  private final Predicate<Library> checkFourAdjacents = (l) -> {
    int count = 0;
    for (int i = 0; i < l.length; i++) {
      for (int j = 0; j < l[i].length; j++) {
        if (i <= l.length - 3 && l[i][j].equals(l[i + 1][j]) && l[i][j].equals(l[i + 2][j]) && l[i][j].equals(l[i + 3][j])) {
          count++;
          if (count >= 4) {
            return true;
          }
        }
        if (j <= l[i].length - 3 && l[i][j].equals(l[i][j + 1]) && l[i][j].equals(l[i][j + 2]) && l[i][j].equals(l[i][j + 3])) {
          count++;
          if (count >= 4) {
            return true;
          }
        }
      }
    }
    return false;
  }
  
  /* #4
  * Rule that checks if there are at least two 2x2 squares of tiles of the same type (the type has to be the same for both the squares)
  * TODO: how do I check whether they are all of the same type
  */
  private final Predicate<Library> checkSquares = (l) -> {
    int count = 0;
    for (int i = 0; i < l.length-1; i++) {
      for (int j = 0; j < l[i].length-1; j++) {
        if (l[i][j].equals(l[i + 1][j]) && l[i][j].equals(l[i + 1][j]) && l[i][j].equals(l[i + 1][j + 1])) {
          count++;
          if (count >= 2) {
            return true;
          }
        }
      }
    }
    return false;
  }
  
  /* #5
  * Rule that checks if there are at least three columns containing maximum 3 different types of tiles
  *
  */
  private final Predicate<Library> checkMaxThreeTypesInColumn = (l) -> {
    int countColumns = 0;
    for (int i = 0; i < l[i].length; i++) {
      int countTypes = 0;
      Set<TileType> seenTypes = new HashSet<TileType> ();
      for (int j = 0; j < l.length; j++) {
        if(!seenTypes.contains(l[i][j].type)){
          seenTypes.add(l[i][j].type);
          countTypes++;
        }
      }
      if(countTypes <= 3){
        countColumns++;
        if(countColumns > 3){
          return true
        }
      }
    }
    return false;
  }
  
  /* #6
  * Rule that checks if there are at least eight different tiles of the same type
  *
  */
  private final Predicate<Library> checkEightOfSameType = (l) -> {
    Map<TileType,Integer> checkCount = new HashMap<TileType,Integer> ();
    for (int i = 0; i < l.length; i++) {
      for (int j = 0; j < l[i].length; j++) {
        if(!checkCount.containsKey(l[i][j].type)){
          checkCount.put(l[i][j].type,1)
        } 
        else {
          int oldCount = checkCount.get(l[i][j].type);
          checkCount.put(l[i][j].type, oldCount+1);
          if(checkCount.get(l[i][j].type) >= 8){
            return true
          }
        }
      }
    }
    return false;
  }
  
  /*
  * Rule that checks if
  *
  */
  private final Predicate<Library> = (l) -> {

  }
  
  /*
  * Rule that checks if
  *
  */
  private final Predicate<Library> = (l) -> {

  }
  
  /*
  * Rule that checks if
  *
  */
  private final Predicate<Library> = (l) -> {

  }
  
  /*
  * Rule that checks if
  *
  */
  private final Predicate<Library> = (l) -> {

  }
  
  /*
  * Rule that checks if
  *
  */
  private final Predicate<Library> = (l) -> {

  }
  
  /*
  * Rule that checks if
  *
  */
  private final Predicate<Library> = (l) -> {

  }
  
  /*
  * The list of {@link SharedPattern} containing all the 12 different pattern rules with their lambda functions
  *
  */
  public static final List<SharedPattern> patterns = new LinkedList<SharedPattern>(
  )

}