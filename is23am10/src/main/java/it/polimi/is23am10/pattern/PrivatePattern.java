package it.polimi.is23am10.pattern;

import it.polimi.is23am10.items.bookshelf.Bookshelf;
import it.polimi.is23am10.items.bookshelf.exceptions.WrongCharBookshelfStringException;
import it.polimi.is23am10.items.bookshelf.exceptions.WrongLengthBookshelfStringException;
import it.polimi.is23am10.items.tile.exceptions.WrongTileTypeException;

/**
 * Private pattern object.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public class PrivatePattern extends AbstractPattern {

  /**
   * The pattern play grid. An instance of {@link Bookshelf}
   * 
   */
  private Bookshelf grid;
  
  /**
   * The constructor of the class PrivatePattern.
   * 
   */
  public PrivatePattern(String s){
    try {
      this.grid = new Bookshelf(s);
    } catch (NullPointerException e) {
      e.printStackTrace();
    } catch (WrongLengthBookshelfStringException e) {
      e.printStackTrace();
    } catch (WrongCharBookshelfStringException e) {
      e.printStackTrace();
    } catch (WrongTileTypeException e) {
      e.printStackTrace();
    }
  };
}
