package it.polimi.is23am10.utils;

import it.polimi.is23am10.utils.config.AppConfig;
import it.polimi.is23am10.utils.config.exceptions.InvalidMaxConnectionsNumberException;
import it.polimi.is23am10.utils.config.exceptions.InvalidPortNumberException;
import it.polimi.is23am10.utils.exceptions.InvalidArgumentException;
import it.polimi.is23am10.utils.exceptions.MissingParameterException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parser for argument from CLI.
 *
 * @author Alessandro Amandonico (alessandro.amandonico@mail.polimi.it)
 * @author Francesco Buccoliero (francesco.buccoliero@mail.polimi.it)
 * @author Kaixi Matteo Chen (kaiximatteo.chen@mail.polimi.it)
 * @author Lorenzo Cavallero (lorenzo1.cavallero@mail.polimi.it)
 */
public class ArgParser {

  /**
   * Private constructor of argument parser.
   */
  private ArgParser() {
  }

  /**
   * The socket port cli command.
   */
  public static final String SOCKET_PORT_CLI_COMMAND = "--socket-port";

  /**
   * The rmi port cli command.
   */
  public static final String RMI_PORT_CLI_COMMAND = "--rmi-port";

  /**
   * The max connections cli command.
   */
  public static final String MAX_CONNECTIONS_CLI_COMMAND = "--max-connections";

  /**
   * The keep alive cli command.
   */
  public static final String KEEP_ALIVE_CLI_COMMAND = "--keep-alive";  

  /**
   * The is server command.
   */
  public static final String IS_SERVER_CLI_COMMAND = "--is-server";  

  /**
   * The show gui cli command.
   */
  public static final String SHOW_GUI_CLI_COMMAND = "--show-gui";  

  /**
   * The use rmi cli command.
   */
  public static final String USE_RMI_CLI_COMMAND = "--use-rmi";  

  /**
   * The server address cli command.
   */
  public static final String SERVER_ADDRESS_CLI_COMMAND = "--address";  

  /**
   * The debug command. If enabled shows all messages in console.
   */
  public static final String DEBUG_CLI_COMMAND = "--debug";  

  /**
   * The max players inactivity time command.
   */
  public static final String MAX_CLIENT_INACTIVITY_TIME = "--max-player-inactive-ms";

  /**
   * Regex expression for validating ipv4 addresses
   */
  private static final String IPV4_REGEX = 
  "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
  "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
  "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
  "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";

  /**
   * Argument parser method that checks commands.
   *
   * @param args arguments passed through cli.
   * @throws InvalidArgumentException             for invalid command.
   * @throws MissingParameterException            for missing param.
   * @throws InvalidPortNumberException           if port not in bounds.
   * @throws NumberFormatException                if int not found.
   * @throws InvalidMaxConnectionsNumberException if the max number of connections is invalid.
   * 
   */
  public static void parse(String[] args)
      throws InvalidArgumentException, MissingParameterException, NumberFormatException,
      InvalidPortNumberException, InvalidMaxConnectionsNumberException {
    if (args != null) {
      for (int i = 0; i < args.length; i++) {
        switch (args[i]) {
          case SOCKET_PORT_CLI_COMMAND:
            if (i + 1 < args.length) {
              AppConfig.setServerSocketPort(Integer.parseInt(args[i + 1]));
              i++;
            } else {
              throw new MissingParameterException(args[i]);
            }
            break;
          case RMI_PORT_CLI_COMMAND:
            if (i + 1 < args.length) {
              AppConfig.setServerRmiPort(Integer.parseInt(args[i + 1]));
              i++;
            } else {
              throw new MissingParameterException(args[i]);
            }
            break;
          case MAX_CONNECTIONS_CLI_COMMAND:
            if (i + 1 < args.length) {
              AppConfig.setMaxConnections(Integer.parseInt(args[i + 1]));
              i++;
            } else {
              throw new MissingParameterException(args[i]);
            }
            break;
          case KEEP_ALIVE_CLI_COMMAND:
            if (i + 1 < args.length) {
              AppConfig.setKeepAlive(Boolean.parseBoolean(args[i + 1]));
              i++;
            } else {
              throw new MissingParameterException(args[i]);
            }
            break;
          case SERVER_ADDRESS_CLI_COMMAND:
            if (i + 1 < args.length) {
              if (Pattern.matches(IPV4_REGEX, args[i + 1])){
                AppConfig.setServerAddress(args[i + 1]);
                i++;
              } else {
                throw new InvalidArgumentException("Address is not a valid IPV4. For localhost omit flag.");
              }
            } else {
              throw new MissingParameterException(args[i]);
            }
            break;
          case IS_SERVER_CLI_COMMAND:
            AppConfig.setIsServer(true);
            break;
          case SHOW_GUI_CLI_COMMAND:
            AppConfig.setShowGUI(true);
            break;
          case USE_RMI_CLI_COMMAND:
            AppConfig.setUseRMI(true);
            break;
          case DEBUG_CLI_COMMAND:
            AppConfig.setShowDebug(true);
            break;
          case MAX_CLIENT_INACTIVITY_TIME:
            if (i + 1 < args.length) {
              AppConfig.setMaxInactivityTimeMs(Long.parseLong(args[i + 1]));
              i++;
            } else {
              throw new MissingParameterException(args[i]);
            }
            break;
          default:
            throw new InvalidArgumentException(args[i]);
        }
      }
    }
  }
}
