package it.polimi.is23am10.utils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import it.polimi.is23am10.server.config.ServerConfig;
import it.polimi.is23am10.server.config.ServerConfigContext;
import it.polimi.is23am10.server.config.exceptions.InvalidMaxConnectionsNumberException;
import it.polimi.is23am10.server.config.exceptions.InvalidPortNumberException;
import it.polimi.is23am10.utils.exceptions.InvalidArgumentException;
import it.polimi.is23am10.utils.exceptions.MissingParameterException;
import org.junit.jupiter.api.Test;

/**
 * Argument parser tests.
 */
public class ArgParserTest {

  @Test
  void set_socket_port_argument_should_set_server_port()
      throws InvalidArgumentException, MissingParameterException, NumberFormatException,
      InvalidPortNumberException, InvalidMaxConnectionsNumberException {
    final String serverPort = "8888";
    final String[] mockCommand = {
        "--socket-port", serverPort
    };
    ArgParser.parse(mockCommand);
    assertEquals(Integer.parseInt(serverPort), ServerConfig.getServerSocketPort());

    ServerConfigContext ctx = new ServerConfigContext();
    assertEquals(Integer.parseInt(serverPort), ctx.getServerSocketPort());
    assertEquals(ServerConfig.getKeepAlive(), ctx.getKeepAlive());
    assertEquals(ServerConfig.getMaxConnections(), ctx.getMaxConnections());
  }

  @Test
  void set_rmi_port_argument_should_set_server_port()
      throws InvalidArgumentException, MissingParameterException, NumberFormatException,
      InvalidPortNumberException, InvalidMaxConnectionsNumberException {
    final String serverPort = "8888";
    final String[] mockCommand = {
        "--rmi-port", serverPort
    };
    ArgParser.parse(mockCommand);
    assertEquals(Integer.parseInt(serverPort), ServerConfig.getServerRmiPort());

    ServerConfigContext ctx = new ServerConfigContext();
    assertEquals(Integer.parseInt(serverPort), ctx.getServerRmiPort());
    assertEquals(ServerConfig.getKeepAlive(), ctx.getKeepAlive());
    assertEquals(ServerConfig.getMaxConnections(), ctx.getMaxConnections());
  }

  @Test
  void missing_port_argument_should_throw_missing_parameter_exception()
      throws InvalidArgumentException, MissingParameterException {
    final String[] mockCommand = {
        "--socket-port"
    };

    assertThrows(MissingParameterException.class, () -> ArgParser.parse(mockCommand));
  }

  @Test
  void set_max_connections_argument_should_set_max_connections()
      throws InvalidArgumentException, MissingParameterException, NumberFormatException,
      InvalidPortNumberException, InvalidMaxConnectionsNumberException {
    final String serverMaxConnections = "7";
    final String[] mockCommand = {
        "--max-connections", serverMaxConnections
    };
    ArgParser.parse(mockCommand);
    assertEquals(Integer.parseInt(serverMaxConnections), ServerConfig.getMaxConnections());

    ServerConfigContext ctx = new ServerConfigContext();
    assertEquals(Integer.parseInt(serverMaxConnections), ctx.getMaxConnections());
    assertEquals(ServerConfig.getKeepAlive(), ctx.getKeepAlive());
    assertEquals(ServerConfig.getServerSocketPort(), ctx.getServerSocketPort());
  }

  @Test
  void missing_max_connections_argument_should_throw_missing_parameter_exception()
      throws InvalidArgumentException, MissingParameterException {
    final String[] mockCommand = {
        "--max-connections"
    };

    assertThrows(MissingParameterException.class, () -> ArgParser.parse(mockCommand));
  }

  @Test
  void set_keep_alive_should_set_keep_alive()
      throws InvalidArgumentException, MissingParameterException, NumberFormatException,
      InvalidPortNumberException, InvalidMaxConnectionsNumberException {
    final String serverKeepAlive = "false";
    final String[] mockCommand = {
        "--keep-alive", serverKeepAlive
    };
    ArgParser.parse(mockCommand);
    assertEquals(Boolean.parseBoolean(serverKeepAlive), ServerConfig.getKeepAlive());

    ServerConfigContext ctx = new ServerConfigContext();
    assertEquals(Boolean.parseBoolean(serverKeepAlive), ctx.getKeepAlive());
    assertEquals(ServerConfig.getMaxConnections(), ctx.getMaxConnections());
    assertEquals(ServerConfig.getServerSocketPort(), ctx.getServerSocketPort());
  }

  @Test
  void missing_keep_alive_argument_should_throw_missing_parameter_exception()
      throws InvalidArgumentException, MissingParameterException {
    final String[] mockCommand = {
        "--keep-alive"
    };

    assertThrows(MissingParameterException.class, () -> ArgParser.parse(mockCommand));
  }

  @Test
  void isServer_argument_should_set_isServer() throws NumberFormatException, InvalidArgumentException, MissingParameterException, InvalidPortNumberException, InvalidMaxConnectionsNumberException{
    final String[] mockCommand = {
      "--is-server"
    };
    assertFalse(ServerConfig.getIsServer());
    ArgParser.parse(mockCommand);

    ServerConfigContext ctx = new ServerConfigContext();
    assertTrue(ctx.getIsServer());
  }

  @Test
  void showGUI_argument_should_set_showGUI() throws NumberFormatException, InvalidArgumentException, MissingParameterException, InvalidPortNumberException, InvalidMaxConnectionsNumberException{
    final String[] mockCommand = {
      "--show-gui"
    };
    assertFalse(ServerConfig.getShowGUI());
    ArgParser.parse(mockCommand);

    ServerConfigContext ctx = new ServerConfigContext();
    assertTrue(ctx.getShowGUI());
  }

  @Test
  void useRMI_argument_should_set_useRMI() throws NumberFormatException, InvalidArgumentException, MissingParameterException, InvalidPortNumberException, InvalidMaxConnectionsNumberException{
    final String[] mockCommand = {
      "--use-rmi"
    };
    assertFalse(ServerConfig.getUseRMI());
    ArgParser.parse(mockCommand);

    ServerConfigContext ctx = new ServerConfigContext();
    assertTrue(ctx.getUseRMI());
  }

  @Test
  void address_argument_should_set_address() throws NumberFormatException, InvalidArgumentException, MissingParameterException, InvalidPortNumberException, InvalidMaxConnectionsNumberException {
    final String ipAddress = "192.168.1.100";
    final String[] mockCommand = {
      "--address", ipAddress
    };

    assertEquals("localhost", ServerConfig.getServerAddress());
    ArgParser.parse(mockCommand);

    ServerConfigContext ctx = new ServerConfigContext();
    assertEquals(ipAddress, ctx.getServerAddress());
  }

  @Test
  void invalid_address_argument_should_throw_invalid_argument_exception() {
    final String ipAddress = "3000.22.1.33";
    final String[] mockCommand = {
      "--address", ipAddress
    };

    assertThrows(InvalidArgumentException.class, () -> ArgParser.parse(mockCommand));
  }

  @Test
  void missing_address_argument_should_throw_missing_parameter_exception()
      throws InvalidArgumentException, MissingParameterException {
    final String[] mockCommand = {
        "--address"
    };

    assertThrows(MissingParameterException.class, () -> ArgParser.parse(mockCommand));
  }

  @Test
  void invalid_argument_should_throw_invalid_argument_exception()
      throws InvalidArgumentException, MissingParameterException {
    final String[] mockCommand = {
        "--optimus-prime"
    };

    assertThrows(InvalidArgumentException.class, () -> ArgParser.parse(mockCommand));
  }

  @Test
  void low_port_number_should_throw_invalid_port_number_exception()
      throws InvalidArgumentException, MissingParameterException {
    final String serverPort = "700";
    final String[] mockCommand = {
        "--socket-port", serverPort
    };

    assertThrows(InvalidPortNumberException.class, () -> ArgParser.parse(mockCommand));
  }

  @Test
  void high_port_number_should_throw_invalid_port_number_exception()
      throws InvalidArgumentException, MissingParameterException {
    final String serverPort = "70000";
    final String[] mockCommand = {
        "--socket-port", serverPort
    };

    assertThrows(InvalidPortNumberException.class, () -> ArgParser.parse(mockCommand));
  }

  @Test
  void negative_max_connections_should_throw_invalid_max_connections_exception()
      throws InvalidArgumentException, MissingParameterException {
    final String serverMaxConnections = "-1";
    final String[] mockCommand = {
        "--max-connections", serverMaxConnections
    };

    assertThrows(InvalidMaxConnectionsNumberException.class, () -> ArgParser.parse(mockCommand));
  }

  @Test
  void high_max_connections_should_throw_invalid_max_connections_exception()
      throws InvalidArgumentException, MissingParameterException {
    final String serverMaxConnections = "100";
    final String[] mockCommand = {
        "--max-connections", serverMaxConnections
    };

    assertThrows(InvalidMaxConnectionsNumberException.class, () -> ArgParser.parse(mockCommand));
  }

}
