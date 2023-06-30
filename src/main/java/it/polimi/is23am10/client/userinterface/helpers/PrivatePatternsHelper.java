package it.polimi.is23am10.client.userinterface.helpers;

import it.polimi.is23am10.server.model.items.bookshelf.Bookshelf;
import it.polimi.is23am10.server.model.items.bookshelf.exceptions.WrongCharBookshelfStringException;
import it.polimi.is23am10.server.model.items.bookshelf.exceptions.WrongLengthBookshelfStringException;
import java.util.Map;

/**
 * Map containing the private bookshelf patterns associated with an index.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public final class PrivatePatternsHelper {
  private static final Map<Integer, String> privatePatternBS =
      Map.ofEntries(
          Map.entry(1, "PXFXXXXXXCXXXBXXGXXXXXXXXXXTXX"),
          Map.entry(2, "XXXXXXPXXXCXGXXXXXXBXXXTXXXXXF"),
          Map.entry(3, "XXXXXFXXGXXXPXXXCXXTXXXXXBXXXX"),
          Map.entry(4, "XXXXGXXXXXTXFXXXXXPXXBCXXXXXXX"),
          Map.entry(5, "XXXXXXTXXXXXXXXXFBXXXXXXPGXXCX"),
          Map.entry(6, "XXTXCXXXXXXXXBXXXXXXXGXFXPXXXX"),
          Map.entry(7, "CXXXXXXXFXXPXXXTXXXXXXXXGXXBXX"),
          Map.entry(8, "XXXXFXCXXXXXTXXPXXXXXXXBXXXXGX"),
          Map.entry(9, "XXGXXXXXXXXXCXXXXXXBXTXXPFXXXX"),
          Map.entry(10, "XXXXTXGXXXBXXXXXXXCXXFXXXXXXPX"),
          Map.entry(11, "XXPXXXBXXXGXXXXXXFXXXXXXCXXXTX"),
          Map.entry(12, "XXBXXXPXXXXXFXXXXXTXXXXXGCXXXX"));

  /**
   * Public function used to retrieve a bookshelf from its private card.
   *
   * @param idx index of bookshelf.
   * @return relative bookshelf.
   */
  public static Bookshelf getBookshelf(Integer idx) {
    try {
      return new Bookshelf(privatePatternBS.get(idx));
    } catch (NullPointerException
        | WrongLengthBookshelfStringException
        | WrongCharBookshelfStringException e) {
      return new Bookshelf();
    }
  }
}
