package it.polimi.is23am10.utils;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import it.polimi.is23am10.config.ServerConfigContext;
import it.polimi.is23am10.config.ServerConfigDefault;
import it.polimi.is23am10.utils.exceptions.InvalidArgumentException;
import it.polimi.is23am10.utils.exceptions.MissingParameterException;
import org.junit.jupiter.api.Test;


/**
 * Argument parser tests.
 */
public class ArgParserTest {

  @Test
  void set_port_argument_should_set_server_port() 
      throws InvalidArgumentException, MissingParameterException {
    final String serverPort = "8888";
    final String[] mockCommand = {
        "--port", serverPort
    };
    ArgParser.parse(mockCommand);
    assertEquals(Integer.parseInt(serverPort), ServerConfigDefault.getServerPort());

    ServerConfigContext ctx = new ServerConfigContext();
    assertEquals(Integer.parseInt(serverPort), ctx.getServerPort());  
    assertEquals(ServerConfigDefault.getKeepAlive(), ctx.getKeepAlive());
    assertEquals(ServerConfigDefault.getMaxConnections(), ctx.getMaxConnections());
  }

  @Test
  void missing_port_argument_should_throw_missing_parameter() 
      throws InvalidArgumentException, MissingParameterException {
    final String[] mockCommand = {
        "--port"
    };

    assertThrows(MissingParameterException.class, () -> ArgParser.parse(mockCommand));
  }

  @Test
  void set_max_connections_argument_should_set_max_connections() 
      throws InvalidArgumentException, MissingParameterException {
    final String serverMaxConnections = "7";
    final String[] mockCommand = {
        "--max-connections", serverMaxConnections
    };
    ArgParser.parse(mockCommand);
    assertEquals(Integer.parseInt(serverMaxConnections), ServerConfigDefault.getMaxConnections());

    ServerConfigContext ctx = new ServerConfigContext();
    assertEquals(Integer.parseInt(serverMaxConnections), ctx.getMaxConnections());
    assertEquals(ServerConfigDefault.getKeepAlive(), ctx.getKeepAlive());
    assertEquals(ServerConfigDefault.getServerPort(), ctx.getServerPort());
  }

  @Test
  void missing_max_connections_argument_should_throw_missing_parameter() 
      throws InvalidArgumentException, MissingParameterException {
    final String[] mockCommand = {
        "--max-connections"
    };

    assertThrows(MissingParameterException.class, () -> ArgParser.parse(mockCommand));
  }

  @Test
  void set_keep_alive_should_set_keep_alive() 
      throws InvalidArgumentException, MissingParameterException {
    final String serverKeepAlive = "false";
    final String[] mockCommand = {
        "--keep-alive", serverKeepAlive
    };
    ArgParser.parse(mockCommand);
    assertEquals(Boolean.parseBoolean(serverKeepAlive), ServerConfigDefault.getKeepAlive());

    ServerConfigContext ctx = new ServerConfigContext();
    assertEquals(Boolean.parseBoolean(serverKeepAlive), ctx.getKeepAlive());
    assertEquals(ServerConfigDefault.getMaxConnections(), ctx.getMaxConnections());
    assertEquals(ServerConfigDefault.getServerPort(), ctx.getServerPort());
  }

  @Test
  void missing_keep_alive_argument_should_throw_missing_parameter() 
      throws InvalidArgumentException, MissingParameterException {
    final String[] mockCommand = {
        "--keep-alive"
    };

    assertThrows(MissingParameterException.class, () -> ArgParser.parse(mockCommand));
  }

}
