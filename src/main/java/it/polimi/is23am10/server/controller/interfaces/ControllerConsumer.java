package it.polimi.is23am10.server.controller.interfaces;

import it.polimi.is23am10.server.network.playerconnector.AbstractPlayerConnector;
import org.apache.logging.log4j.Logger;

/**
 * Custom functional interface definition for the controller consumer.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
@FunctionalInterface
public interface ControllerConsumer<R, C> {
  R accept(Logger logger, AbstractPlayerConnector connector, C command);
}
