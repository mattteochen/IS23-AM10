package it.polimi.is23am10.controller.exceptions;

/**
 * Custom runtime exception to handle {@link Opcode#START} deserialization errors.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public class MoveTileCommandSerializationErrorException extends
    CommandSerializationErrorException {
  public MoveTileCommandSerializationErrorException(String type) {
    super("ADD_PLAYER", type);
  }
}