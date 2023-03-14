package it.polimi.is23am10.pattern;

import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

import it.polimi.is23am10.items.bookshelf.Bookshelf;
import it.polimi.is23am10.items.tile.Tile;

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
     * 
     */
    public static final Predicate<Bookshelf> checkPattern1 = bs -> {
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
     * 
     */
    public static final Predicate<Bookshelf> checkPattern2 = bs -> {
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
     * 
     */
    public static final Predicate<Bookshelf> checkPattern3 = bs -> {
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
     * 
     */
    public static final Predicate<Bookshelf> checkPattern4 = bs -> {
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
     * 
     */
    public static final Predicate<Bookshelf> checkPattern5 = bs -> {
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
     * 
     */
    public static final Predicate<Bookshelf> checkPattern6 = bs -> {
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
     * 
     */
    public static final Predicate<Bookshelf> checkPattern7 = bs -> {
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
     * 
     */
    public static final Predicate<Bookshelf> checkPattern8 = bs -> {
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
     * 
     */
    public static final Predicate<Bookshelf> checkPattern9 = bs -> {
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
     * 
     */
    public static final Predicate<Bookshelf> checkPattern10 = bs -> {
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
     * 
     */
    public static final Predicate<Bookshelf> checkPattern11 = bs -> {
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
     * 
     */
    public static final Predicate<Bookshelf> checkPattern12 = bs -> {
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
     * The list of {@link PrivatePattern} containing all the 12 different patterns.
     * 
     */
    public static final List<PrivatePattern> patternsArray = List.of(
        (new PrivatePattern(checkPattern1)),
        (new PrivatePattern(checkPattern2)),
        (new PrivatePattern(checkPattern3)),
        (new PrivatePattern(checkPattern4)),
        (new PrivatePattern(checkPattern5)),
        (new PrivatePattern(checkPattern6)),
        (new PrivatePattern(checkPattern7)),
        (new PrivatePattern(checkPattern8)),
        (new PrivatePattern(checkPattern9)),
        (new PrivatePattern(checkPattern10)),
        (new PrivatePattern(checkPattern11)),
        (new PrivatePattern(checkPattern12))
    );
    
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
