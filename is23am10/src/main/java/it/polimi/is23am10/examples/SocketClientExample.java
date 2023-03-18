package it.polimi.is23am10.examples;

import com.google.gson.Gson;
import it.polimi.is23am10.command.StartGameCommand;
import it.polimi.is23am10.config.ServerConfigDefault;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This class implements java socket client.
 *
 */
public class SocketClientExample {

  /**
   * Main method.
   *
   */
  public static void main(String[] args)
      throws IOException {
    // get the localhost IP address, if server is running on some other IP, you need
    // to use that
    InetAddress host = InetAddress.getLocalHost();
    Socket socket = null;
    PrintWriter printer;
    Gson gson = new Gson();
    final Logger logger = LogManager.getLogger(SocketClientExample.class);
    StartGameCommand command = new StartGameCommand("Client", 4);

    //create the json string
    String message = gson.toJson(command);

    // establish socket connection to server
    socket = new Socket(host.getHostName(), ServerConfigDefault.SERVER_PORT);

    // write to socket using PrintWriter.
    printer = new PrintWriter(socket.getOutputStream(), true, StandardCharsets.UTF_8);
    logger.info("Sending request to Socket Server {}", message);
    printer.println(message);

    printer.close();
    socket.close();
  }
}
