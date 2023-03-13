package it.polimi.is23am10.pattern;

import java.util.List;
import java.util.Random;

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
     * The list of {@link PrivatePattern} containing all the 12 patterns 
     * sorted by the game rulebook order.
     * 
     */
    private static final List<PrivatePattern> patternsArray = List.of(
        (new PrivatePattern(
            "PXFXX" +
            "XXXXG" +
            "XXXBX" +
            "XGXXX" +
            "XXXXX" +
            "XXTXX" 
        )),
        (new PrivatePattern(
            "XXXXX" +
            "XPXXX" +
            "CXGXX" +
            "XXXXB" +
            "XXXTX" +
            "XXXXF"
        )),
        (new PrivatePattern(
            "XXXXX" +
            "FXXGX" +
            "XXPXX" +
            "XCXXT" +
            "XXXXX" +
            "BXXXX"
        )),
        (new PrivatePattern(
            "XXXXG" +
            "XXXXX" +
            "TXFXX" +
            "XXPX" +
            "XBCXX" +
            "XXXXX"
        )),
        (new PrivatePattern(
            "XXXXX" +
            "XTXXX" +
            "XXXXX" +
            "XFBXX" +
            "XXXXP" +
            "GXXCX"
        )),
        (new PrivatePattern(
            "XXTXC" +
            "XXXXX" +
            "XXXBX" +
            "XXXXX" +
            "XGXFX" +
            "PXXXX"
        )),
        (new PrivatePattern(
            "CXXXX" +
            "XXXFX" +
            "XPXXX" +
            "TXXXX" +
            "XXXXG" +
            "XXBXX"
        )),
        (new PrivatePattern(
            "XXXXF" +
            "XGXXX" +
            "XXTXX" +
            "PXXXX" +
            "XXXBX" +
            "XXXGX"
        )),
        (new PrivatePattern(
            "XXGXX" +
            "XXXXX" +
            "XXCXX" +
            "XXXXB" +
            "XTXXP" +
            "FXXXX"
        )),
        (new PrivatePattern(
            "XXXXT" +
            "XGXXX" +
            "BXXXX" +
            "XXXCX" +
            "XFXXX" +
            "XXXPX"
        )),
        (new PrivatePattern(
            "XXPXX" +
            "XBXXX" +
            "GXXXX" +
            "XXFXX" +
            "XXXXC" +
            "XXXTX"
        )),
        (new PrivatePattern(
            "XXBXX" +
            "XPXXX" +
            "XXFXX" +
            "XXXTX" +
            "XXXXG" +
            "CXXXX"
        ))
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
