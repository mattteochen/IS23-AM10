package it.polimi.is23am10.utils;

import java.util.ArrayList;
import java.util.List;

import it.polimi.is23am10.server.model.items.bookshelf.Bookshelf;
import it.polimi.is23am10.server.model.items.bookshelf.exceptions.BookshelfGridColIndexOutOfBoundsException;
import it.polimi.is23am10.server.model.items.bookshelf.exceptions.BookshelfGridRowIndexOutOfBoundsException;
import it.polimi.is23am10.server.model.items.tile.Tile.TileType;
import it.polimi.is23am10.utils.exceptions.NullIndexValueException;
import it.polimi.is23am10.utils.exceptions.WrongBookShelfPicksException;

/**
 * Helper class to convert input move from index to coordinates.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public class MoveCommandHelper {

  /**
   * Private empty constructor for the class.
   */
  private MoveCommandHelper() {
  }

  /**
   * Method that takes the column input of the player bookshelf and returns the list of the 
   * first available empty coordinates.
   * 
   * @param idx column index.
   * @param bs player bookshelf.
   * @param nMoves number of moves made and of empty coords to check.
   *
   * @return list of first available empty coordinates of the bookshelf
   * @throws BookshelfGridColIndexOutOfBoundsException
   * @throws BookshelfGridRowIndexOutOfBoundsException
   * @throws NullIndexValueException
   * @throws WrongBookShelfPicksException
   */
  public static List<Coordinates> fromColIdxToCoord(String idx, Bookshelf bs , Integer nMoves) throws BookshelfGridColIndexOutOfBoundsException, BookshelfGridRowIndexOutOfBoundsException, NullIndexValueException, WrongBookShelfPicksException{
    List<Coordinates> bsCoord = new ArrayList<>();
    Integer columnIdx = idx.charAt(0) - 'A';
    Integer rowCount = 0;
    // If I got to the tile on top and there's no space left
    if(bs.getFreeRowsInCol(columnIdx) < nMoves){
      throw new WrongBookShelfPicksException(
        "Invalid bookshelf picks, selected a column with no sufficient space");
    }

    for(int row = bs.getBookshelfGrid()[0].length ; rowCount < nMoves && row >= 0 ; row--){
      if(bs.getBookshelfGridAt(row, columnIdx).getType() == TileType.EMPTY){
        bsCoord.add(new Coordinates(row, columnIdx));
        rowCount++;
      }
    }

    return bsCoord;
  }


}
