package it.polimi.is23am10.items.bookshelf.exceptions;

/**
 * bookshelfString given to the constructor has wrong length
 * Can be used public methods of {@link Bookshelf}.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public class WrongLengthBookshelfStringException extends Exception {
  public WrongLengthBookshelfStringException(String msg) {
    super(msg);
  }
}