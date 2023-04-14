package it.polimi.is23am10.server.controller.exceptions;

/**
 * Custom runtime exception to handle {@link Opcode} deserialization errors.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public abstract class CommandSerializationErrorException extends RuntimeException {
  protected CommandSerializationErrorException(String command, String type) {
    super(command + " command not processed. Failed to obtain "
        + type + " during deserialization process");
  }
}
