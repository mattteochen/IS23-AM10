package it.polimi.is23am10.pattern;

import java.util.List;
import java.util.Random;
import java.util.function.Function;
import it.polimi.is23am10.items.bookshelf.Bookshelf;
import it.polimi.is23am10.items.tile.Tile;
import it.polimi.is23am10.items.tile.Tile.TileType;

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
    public static final Function<Bookshelf, Integer> checkPattern1 = bs -> {
        Tile[][] grid = bs.getBookshelfGrid();
        int cardMatched = 0;
        
        if(grid[0][0].getType() == (TileType.PLANT))  cardMatched++;
        if(grid[0][2].getType() == (TileType.FRAME))  cardMatched++;
        if(grid[1][4].getType() == (TileType.CAT))   cardMatched++;
        if(grid[2][3].getType() == (TileType.BOOK))   cardMatched++;
        if(grid[3][1].getType() == (TileType.GAME))   cardMatched++;
        if(grid[5][2].getType() == (TileType.TROPHY)) cardMatched++;
        
        return cardMatched;
    };

    /**
     * PrivatePattern Rule #2.
     * 
     */
    public static final Function<Bookshelf, Integer> checkPattern2 = bs -> {
        Tile[][] grid = bs.getBookshelfGrid();
        int cardMatched = 0;
        
        if(grid[1][1].getType() == (TileType.PLANT))  cardMatched++;
        if(grid[2][0].getType() == (TileType.CAT))    cardMatched++;
        if(grid[2][2].getType() == (TileType.GAME))   cardMatched++;
        if(grid[3][4].getType() == (TileType.BOOK))   cardMatched++;
        if(grid[4][3].getType() == (TileType.TROPHY)) cardMatched++;
        if(grid[5][4].getType() == (TileType.FRAME))  cardMatched++;
        
        return cardMatched;
    };

    /**
     * PrivatePattern Rule #3.
     * 
     */
    public static final Function<Bookshelf, Integer> checkPattern3 = bs -> {
        Tile[][] grid = bs.getBookshelfGrid();
        int cardMatched = 0;
        
        if(grid[1][0].getType() == (TileType.FRAME))  cardMatched++;
        if(grid[1][3].getType() == (TileType.GAME))   cardMatched++;
        if(grid[2][2].getType() == (TileType.PLANT))  cardMatched++;
        if(grid[3][1].getType() == (TileType.CAT))    cardMatched++;
        if(grid[3][4].getType() == (TileType.TROPHY)) cardMatched++;
        if(grid[5][0].getType() == (TileType.BOOK))   cardMatched++;
        
        return cardMatched;
    };

    /**
     * PrivatePattern Rule #4.
     * 
     */
    public static final Function<Bookshelf, Integer> checkPattern4 = bs -> {
        Tile[][] grid = bs.getBookshelfGrid();
        int cardMatched = 0;
        
        if(grid[0][4].getType() == (TileType.GAME))   cardMatched++;
        if(grid[2][0].getType() == (TileType.TROPHY)) cardMatched++;
        if(grid[2][2].getType() == (TileType.FRAME))  cardMatched++;
        if(grid[3][3].getType() == (TileType.PLANT))  cardMatched++;
        if(grid[4][1].getType() == (TileType.BOOK))   cardMatched++;
        if(grid[4][2].getType() == (TileType.CAT))    cardMatched++;
        
        return cardMatched;
    };

    /**
     * PrivatePattern Rule #5.
     * 
     */
    public static final Function<Bookshelf, Integer> checkPattern5 = bs -> {
        Tile[][] grid = bs.getBookshelfGrid();
        int cardMatched = 0;
        
        if(grid[1][1].getType() == (TileType.TROPHY)) cardMatched++;
        if(grid[3][1].getType() == (TileType.FRAME))  cardMatched++;
        if(grid[3][2].getType() == (TileType.BOOK))   cardMatched++;
        if(grid[4][4].getType() == (TileType.PLANT))  cardMatched++;
        if(grid[5][0].getType() == (TileType.GAME))   cardMatched++;
        if(grid[5][3].getType() == (TileType.CAT))    cardMatched++;
        
        return cardMatched;
    };

    /**
     * PrivatePattern Rule #6.
     * 
     */
    public static final Function<Bookshelf, Integer> checkPattern6 = bs -> {
        Tile[][] grid = bs.getBookshelfGrid();
        int cardMatched = 0;
        
        if(grid[0][2].getType() == (TileType.TROPHY)) cardMatched++;
        if(grid[0][4].getType() == (TileType.CAT))    cardMatched++;
        if(grid[2][3].getType() == (TileType.BOOK))   cardMatched++;
        if(grid[4][1].getType() == (TileType.GAME))   cardMatched++;
        if(grid[4][3].getType() == (TileType.FRAME))  cardMatched++;
        if(grid[5][0].getType() == (TileType.PLANT))  cardMatched++;
        
        return cardMatched;
    };

    /**
     * PrivatePattern Rule #7.
     * 
     */
    public static final Function<Bookshelf, Integer> checkPattern7 = bs -> {
        Tile[][] grid = bs.getBookshelfGrid();
        int cardMatched = 0;
        
        if(grid[0][0].getType() == (TileType.CAT))    cardMatched++;
        if(grid[1][3].getType() == (TileType.FRAME))  cardMatched++;
        if(grid[2][1].getType() == (TileType.PLANT))  cardMatched++;
        if(grid[3][0].getType() == (TileType.TROPHY)) cardMatched++;
        if(grid[4][4].getType() == (TileType.GAME))   cardMatched++;
        if(grid[5][2].getType() == (TileType.BOOK))   cardMatched++;
        
        return cardMatched;
    };

    /**
     * PrivatePattern Rule #8.
     * 
     */
    public static final Function<Bookshelf, Integer> checkPattern8 = bs -> {
        Tile[][] grid = bs.getBookshelfGrid();
        int cardMatched = 0;
        
        if(grid[0][4].getType() == (TileType.FRAME))  cardMatched++;
        if(grid[1][1].getType() == (TileType.CAT))  cardMatched++;
        if(grid[2][2].getType() == (TileType.TROPHY))   cardMatched++;
        if(grid[3][0].getType() == (TileType.PLANT))   cardMatched++;
        if(grid[4][3].getType() == (TileType.BOOK))   cardMatched++;
        if(grid[5][3].getType() == (TileType.GAME)) cardMatched++;
        
        return cardMatched;
    };

    /**
     * PrivatePattern Rule #9.
     * 
     */
    public static final Function<Bookshelf, Integer> checkPattern9 = bs -> {
        Tile[][] grid = bs.getBookshelfGrid();
        int cardMatched = 0;
        
        if(grid[0][2].getType() == (TileType.GAME))  cardMatched++;
        if(grid[2][2].getType() == (TileType.CAT))    cardMatched++;
        if(grid[3][4].getType() == (TileType.BOOK)) cardMatched++;
        if(grid[4][1].getType() == (TileType.TROPHY))  cardMatched++;
        if(grid[4][4].getType() == (TileType.PLANT))   cardMatched++;
        if(grid[5][0].getType() == (TileType.FRAME))   cardMatched++;
        
        return cardMatched;
    };

    /**
     * PrivatePattern Rule #10.
     * 
     */
    public static final Function<Bookshelf, Integer> checkPattern10 = bs -> {
        Tile[][] grid = bs.getBookshelfGrid();
        int cardMatched = 0;
        
        if(grid[0][4].getType() == (TileType.TROPHY))   cardMatched++;
        if(grid[1][1].getType() == (TileType.GAME))    cardMatched++;
        if(grid[2][0].getType() == (TileType.BOOK))   cardMatched++;
        if(grid[3][3].getType() == (TileType.CAT)) cardMatched++;
        if(grid[4][1].getType() == (TileType.FRAME))  cardMatched++;
        if(grid[5][3].getType() == (TileType.PLANT))  cardMatched++;
        
        return cardMatched;
    };

    /**
     * PrivatePattern Rule #11.
     * 
     */
    public static final Function<Bookshelf, Integer> checkPattern11 = bs -> {
        Tile[][] grid = bs.getBookshelfGrid();
        int cardMatched = 0;
        
        if(grid[0][2].getType() == (TileType.PLANT))  cardMatched++;
        if(grid[1][1].getType() == (TileType.BOOK))   cardMatched++;
        if(grid[2][0].getType() == (TileType.GAME))   cardMatched++;
        if(grid[3][2].getType() == (TileType.FRAME))  cardMatched++;
        if(grid[4][4].getType() == (TileType.CAT))    cardMatched++;
        if(grid[5][3].getType() == (TileType.TROPHY)) cardMatched++;
        
        return cardMatched;
    };

    /**
     * PrivatePattern Rule #12.
     * 
     */
    public static final Function<Bookshelf, Integer> checkPattern12 = bs -> {
        Tile[][] grid = bs.getBookshelfGrid();
        int cardMatched = 0;
        
        if(grid[0][2].getType() == (TileType.BOOK))   cardMatched++;
        if(grid[1][1].getType() == (TileType.PLANT))  cardMatched++;
        if(grid[2][2].getType() == (TileType.FRAME))  cardMatched++;
        if(grid[3][3].getType() == (TileType.TROPHY)) cardMatched++;
        if(grid[4][4].getType() == (TileType.GAME))   cardMatched++;
        if(grid[5][0].getType() == (TileType.CAT))    cardMatched++;
        
        return cardMatched;
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
