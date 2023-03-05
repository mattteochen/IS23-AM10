package it.polimi.is23am10.items.staples;

import it.polimi.is23am10.items.card.SharedCard;
import it.polimi.is23am10.items.card.PrivateCard;
import it.polimi.is23am10.items.tile.Tile;

import java.util.List;

/**
 * The staples object.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public class Staples {

    /**
     * List of {@SharedCard} type.
     * 
     */
    private List<SharedCard> sharedDeck;

    /**
     * List of {@PrivateCard} type.
     * 
     */
    private List<PrivateCard> privateDeck;

    /**
     * List of {@Tile} type.
     * 
     */
    private List<Tile> tileSack;
}
