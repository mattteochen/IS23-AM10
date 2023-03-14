package it.polimi.is23am10.pattern;

import java.util.List;
import java.util.Random;
import it.polimi.is23am10.items.bookshelf.Bookshelf;
import it.polimi.is23am10.items.bookshelf.exceptions.WrongCharBookshelfStringException;
import it.polimi.is23am10.items.bookshelf.exceptions.WrongLengthBookshelfStringException;
import it.polimi.is23am10.items.tile.Tile;
import it.polimi.is23am10.items.tile.exceptions.WrongTileTypeException;

/**
 * Private pattern factory object.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public final class PrivatePatternFactory  {

    /**
     * PrivatePattern Rule #1.
     * @param bs
     * @return
     * @throws NullPointerException
     * @throws WrongLengthBookshelfStringException
     * @throws WrongCharBookshelfStringException
     * @throws WrongTileTypeException
     */
    public static final boolean checkPattern1(Bookshelf bs) throws NullPointerException, WrongLengthBookshelfStringException, WrongCharBookshelfStringException, WrongTileTypeException{
        Bookshelf pattern = new Bookshelf(
                "PXFXX" +
                "XXXXG" +
                "XXXBX" +
                "XGXXX" +
                "XXXXX" +
                "XXTXX" );

        Tile[][] grid = bs.getBookshelfGrid();

        for (int i = 0; i < grid.length; i++){
            for (int j = 0; j < grid[i].length; j++){
                if (!pattern.getBookshelfGrid()[i][j].isEmpty())
                    if (!grid[i][j].equals(pattern.getBookshelfGrid()[i][j]))
                        return false;
            }
        }
        return true;
    };

    /**
     * PrivatePattern Rule #2.
     * @param bs
     * @return
     * @throws NullPointerException
     * @throws WrongLengthBookshelfStringException
     * @throws WrongCharBookshelfStringException
     * @throws WrongTileTypeException
     */
    public static final boolean checkPattern2(Bookshelf bs) throws NullPointerException, WrongLengthBookshelfStringException, WrongCharBookshelfStringException, WrongTileTypeException{
        Bookshelf pattern = new Bookshelf(
                "XXXXX" +
                "XPXXX" +
                "CXGXX" +
                "XXXXB" +
                "XXXTX" +
                "XXXXF" );

        Tile[][] grid = bs.getBookshelfGrid();

        for (int i = 0; i < grid.length; i++){
            for (int j = 0; j < grid[i].length; j++){
                if (!pattern.getBookshelfGrid()[i][j].isEmpty())
                    if (!grid[i][j].equals(pattern.getBookshelfGrid()[i][j]))
                        return false;
            }
        }
        return true;
    };

    /**
     * PrivatePattern Rule #3.
     * @param bs
     * @return
     * @throws NullPointerException
     * @throws WrongLengthBookshelfStringException
     * @throws WrongCharBookshelfStringException
     * @throws WrongTileTypeException
     */
    public static final boolean checkPattern3(Bookshelf bs) throws NullPointerException, WrongLengthBookshelfStringException, WrongCharBookshelfStringException, WrongTileTypeException{
        Bookshelf pattern = new Bookshelf(
                "XXXXX" +
                "FXXGX" +
                "XXPXX" +
                "XCXXT" +
                "XXXXX" +
                "BXXXX" );

        Tile[][] grid = bs.getBookshelfGrid();

        for (int i = 0; i < grid.length; i++){
            for (int j = 0; j < grid[i].length; j++){
                if (!pattern.getBookshelfGrid()[i][j].isEmpty())
                    if (!grid[i][j].equals(pattern.getBookshelfGrid()[i][j]))
                        return false;
            }
        }
        return true;
    };

    /**
     * PrivatePattern Rule #4.
     * @param bs
     * @return
     * @throws NullPointerException
     * @throws WrongLengthBookshelfStringException
     * @throws WrongCharBookshelfStringException
     * @throws WrongTileTypeException
     */
    public static final boolean checkPattern4(Bookshelf bs) throws NullPointerException, WrongLengthBookshelfStringException, WrongCharBookshelfStringException, WrongTileTypeException{
        Bookshelf pattern = new Bookshelf(
                "XXXXG" +
                "XXXXX" +
                "TXFXX" +
                "XXXPX" +
                "XBCXX" +
                "XXXXX" );

        Tile[][] grid = bs.getBookshelfGrid();

        for (int i = 0; i < grid.length; i++){
            for (int j = 0; j < grid[i].length; j++){
                if (!pattern.getBookshelfGrid()[i][j].isEmpty())
                    if (!grid[i][j].equals(pattern.getBookshelfGrid()[i][j]))
                        return false;
            }
        }
        return true;
    };

    /**
     * PrivatePattern Rule #5.
     * @param bs
     * @return
     * @throws NullPointerException
     * @throws WrongLengthBookshelfStringException
     * @throws WrongCharBookshelfStringException
     * @throws WrongTileTypeException
     */
    public static final boolean checkPattern5(Bookshelf bs) throws NullPointerException, WrongLengthBookshelfStringException, WrongCharBookshelfStringException, WrongTileTypeException{
        Bookshelf pattern = new Bookshelf(
                "XXXXX" +
                "XTXXX" +
                "XXXXX" +
                "XFBXX" +
                "XXXXP" +
                "GXXCX" );

        Tile[][] grid = bs.getBookshelfGrid();

        for (int i = 0; i < grid.length; i++){
            for (int j = 0; j < grid[i].length; j++){
                if (!pattern.getBookshelfGrid()[i][j].isEmpty())
                    if (!grid[i][j].equals(pattern.getBookshelfGrid()[i][j]))
                        return false;
            }
        }
        return true;
    };

    /**
     * PrivatePattern Rule #6.
     * @param bs
     * @return
     * @throws NullPointerException
     * @throws WrongLengthBookshelfStringException
     * @throws WrongCharBookshelfStringException
     * @throws WrongTileTypeException
     */
    public static final boolean checkPattern6(Bookshelf bs) throws NullPointerException, WrongLengthBookshelfStringException, WrongCharBookshelfStringException, WrongTileTypeException{
        Bookshelf pattern = new Bookshelf(
                "XXTXX" +
                "XXXXX" +
                "XXXBX" +
                "XXXXX" +
                "XGXFX" +
                "PXXXX" );

        Tile[][] grid = bs.getBookshelfGrid();

        for (int i = 0; i < grid.length; i++){
            for (int j = 0; j < grid[i].length; j++){
                if (!pattern.getBookshelfGrid()[i][j].isEmpty())
                    if (!grid[i][j].equals(pattern.getBookshelfGrid()[i][j]))
                        return false;
            }
        }
        return true;
    };

    /**
     * PrivatePattern Rule #7.
     * @param bs
     * @return
     * @throws NullPointerException
     * @throws WrongLengthBookshelfStringException
     * @throws WrongCharBookshelfStringException
     * @throws WrongTileTypeException
     */
    public static final boolean checkPattern7(Bookshelf bs) throws NullPointerException, WrongLengthBookshelfStringException, WrongCharBookshelfStringException, WrongTileTypeException{
        Bookshelf pattern = new Bookshelf(
                "CXXXX" +
                "XXXFX" +
                "XPXXX" +
                "TXXXX" +
                "XXXXG" +
                "XXBXX" );

        Tile[][] grid = bs.getBookshelfGrid();

        for (int i = 0; i < grid.length; i++){
            for (int j = 0; j < grid[i].length; j++){
                if (!pattern.getBookshelfGrid()[i][j].isEmpty())
                    if (!grid[i][j].equals(pattern.getBookshelfGrid()[i][j]))
                        return false;
            }
        }
        return true;
    };

    /**
     * PrivatePattern Rule #8.
     * @param bs
     * @return
     * @throws NullPointerException
     * @throws WrongLengthBookshelfStringException
     * @throws WrongCharBookshelfStringException
     * @throws WrongTileTypeException
     */
    public static final boolean checkPattern8(Bookshelf bs) throws NullPointerException, WrongLengthBookshelfStringException, WrongCharBookshelfStringException, WrongTileTypeException{
        Bookshelf pattern = new Bookshelf(
                "PXFXX" +
                "XXXXG" +
                "XXXBX" +
                "XGXXX" +
                "XXXXX" +
                "XXTXX" );

        Tile[][] grid = bs.getBookshelfGrid();

        for (int i = 0; i < grid.length; i++){
            for (int j = 0; j < grid[i].length; j++){
                if (!pattern.getBookshelfGrid()[i][j].isEmpty())
                    if (!grid[i][j].equals(pattern.getBookshelfGrid()[i][j]))
                        return false;
            }
        }
        return true;
    };

    /**
     * PrivatePattern Rule #9.
     * @param bs
     * @return
     * @throws NullPointerException
     * @throws WrongLengthBookshelfStringException
     * @throws WrongCharBookshelfStringException
     * @throws WrongTileTypeException
     */
    public static final boolean checkPattern9(Bookshelf bs) throws NullPointerException, WrongLengthBookshelfStringException, WrongCharBookshelfStringException, WrongTileTypeException{
        Bookshelf pattern = new Bookshelf(
                "XXXXF" +
                "XCXXX" +
                "XXTXX" +
                "PXXXX" +
                "XXXBX" +
                "XXXGX" );

        Tile[][] grid = bs.getBookshelfGrid();

        for (int i = 0; i < grid.length; i++){
            for (int j = 0; j < grid[i].length; j++){
                if (!pattern.getBookshelfGrid()[i][j].isEmpty())
                    if (!grid[i][j].equals(pattern.getBookshelfGrid()[i][j]))
                        return false;
            }
        }
        return true;
    };

    /**
     * PrivatePattern Rule #10.
     * @param bs
     * @return
     * @throws NullPointerException
     * @throws WrongLengthBookshelfStringException
     * @throws WrongCharBookshelfStringException
     * @throws WrongTileTypeException
     */
    public static final boolean checkPattern10(Bookshelf bs) throws NullPointerException, WrongLengthBookshelfStringException, WrongCharBookshelfStringException, WrongTileTypeException{
        Bookshelf pattern = new Bookshelf(
                "XXGXX" +
                "XXXXX" +
                "XXCXX" +
                "XXXXB" +
                "XTXXP" +
                "FXXXX" );

        Tile[][] grid = bs.getBookshelfGrid();

        for (int i = 0; i < grid.length; i++){
            for (int j = 0; j < grid[i].length; j++){
                if (!pattern.getBookshelfGrid()[i][j].isEmpty())
                    if (!grid[i][j].equals(pattern.getBookshelfGrid()[i][j]))
                        return false;
            }
        }
        return true;
    };

    /**
     * PrivatePattern Rule #11.
     * @param bs
     * @return
     * @throws NullPointerException
     * @throws WrongLengthBookshelfStringException
     * @throws WrongCharBookshelfStringException
     * @throws WrongTileTypeException
     */
    public static final boolean checkPattern11(Bookshelf bs) throws NullPointerException, WrongLengthBookshelfStringException, WrongCharBookshelfStringException, WrongTileTypeException{
        Bookshelf pattern = new Bookshelf(
                "XXXXT" +
                "XGXXX" +
                "BXXBX" +
                "XXCXX" +
                "XFXXX" +
                "XXXPX" );

        Tile[][] grid = bs.getBookshelfGrid();

        for (int i = 0; i < grid.length; i++){
            for (int j = 0; j < grid[i].length; j++){
                if (!pattern.getBookshelfGrid()[i][j].isEmpty())
                    if (!grid[i][j].equals(pattern.getBookshelfGrid()[i][j]))
                        return false;
            }
        }
        return true;
    };

    /**
     * PrivatePattern Rule #12.
     * @param bs
     * @return
     * @throws NullPointerException
     * @throws WrongLengthBookshelfStringException
     * @throws WrongCharBookshelfStringException
     * @throws WrongTileTypeException
     */
    public static final boolean checkPattern12(Bookshelf bs) throws NullPointerException, WrongLengthBookshelfStringException, WrongCharBookshelfStringException, WrongTileTypeException{
        Bookshelf pattern = new Bookshelf(
                "XXPXX" +
                "XBXXX" +
                "GXXXX" +
                "XXFXX" +
                "XXXXC" +
                "XXXTX" );

        Tile[][] grid = bs.getBookshelfGrid();

        for (int i = 0; i < grid.length; i++){
            for (int j = 0; j < grid[i].length; j++){
                if (!pattern.getBookshelfGrid()[i][j].isEmpty())
                    if (!grid[i][j].equals(pattern.getBookshelfGrid()[i][j]))
                        return false;
            }
        }
        return true;
    };


    /**
     * The list og {@link PrivatePattern} containing all the 12 different patterns.
     * 
     */
    public static final List<PrivatePattern> patternsArray = List.of();
    

    /**
     * Method used to get random PrivatePattern between the 12 possible.
     * 
     * @return a random {@link PrivatePattern}.
     * 
     */
    public static final PrivatePattern getRandomPattern(){
        Random random = new Random();
        return patternsArray.get(random.nextInt(patternsArray.size()));
    };

}
