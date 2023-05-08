package it.polimi.is23am10.client.interfaces;

/**
 * Custom functional interface definition for the application timer.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
@FunctionalInterface
public interface AlarmConsumer {
  void start();
}
