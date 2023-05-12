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


  public static List<Coordinates> fromColIdxToCoord(String idx, Bookshelf bs , Integer nMoves) throws BookshelfGridColIndexOutOfBoundsException, BookshelfGridRowIndexOutOfBoundsException, NullIndexValueException, WrongBookShelfPicksException{
    List<Coordinates> bsCoord = new ArrayList<>();
    Integer columnIdx = idx.charAt(0) - 'A';
    Integer rowCount = 0;

    for(int row = bs.getBookshelfGrid()[0].length ; rowCount < nMoves && row >= 0 ; row--){
      if(bs.getBookshelfGridAt(row, columnIdx).getType() == TileType.EMPTY){
        bsCoord.add(new Coordinates(row, columnIdx));
        rowCount++;
      }
      // If I got to the tile on top and there's no space left
      if(row == 0 && rowCount < nMoves){
        throw new WrongBookShelfPicksException(
          "Invalid bookshelf picks, selected a column with no sufficient space");
      }
    }

    return bsCoord;
  }


}
