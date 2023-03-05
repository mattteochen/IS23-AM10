package it.polimi.is23am10.game;

import it.polimi.is23am10.player.Player;
import it.polimi.is23am10.items.staples.Staples;
import it.polimi.is23am10.items.board.Board;
import it.polimi.is23am10.items.card.SharedCard;

import java.util.UUID;
import java.util.List;

/**
 * The Game object.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public class Game {
    
    /**
     * A randomly generated {@link UUID} id.
     * 
     */
    private UUID gameId;

    /**
     * The max number of players.
     * 
     */
    private int maxPlayers;

    /**
     * List of {@Player} type.
     * 
     */
    private List<Player> players;

    /**
     * A randomly choosen first player of the game.
     * 
     */
    private Player firstPlayer;

    /**
     * The instance {@Board} type.
     * 
     */
    private Board gameBoard;

    /**
     * List of {@SharedCard} type containing two randomly selected
     * shared cards for this game.     * 
     */
    private List<SharedCard> sharedCards;

    /**
     * The instance {@Staples} type containing all the needed cards and tiles
     * for this game.     * 
     */
    private Staples staples;

    /**
     * A boolean signaling the game is ended.
     * 
     */
    private boolean ended;
}

