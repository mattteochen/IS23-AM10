package it.polimi.is23am10.examples;

import com.google.gson.Gson;
import it.polimi.is23am10.command.StartGameCommand;
import it.polimi.is23am10.config.ServerConfigDefault;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This class implements an example of java socket client.
 * 
 * <p>
 *  How to use:
 *  - Start the socket server (ServerMain.java)
 *  - Start this main method
 * </p>
 *
 */
public class SocketClientExample {

  /**
   * Main method.
   * @throws InterruptedException
   *
   */
  public static void main(String[] args)
      throws IOException, InterruptedException {
    // get the localhost IP address, if server is running on some other IP, you need
    // to use that
    InetAddress host = InetAddress.getLocalHost();
    Socket socket = null;
    PrintWriter printer;
    BufferedReader reader = null;
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

    DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
    while(dataInputStream.available() == 0) {
      Thread.sleep(10);
    }

    reader = new BufferedReader(
          new InputStreamReader(dataInputStream));
    String payload = null;
    if (reader.ready()) {
      payload = reader.readLine();
    }
    if (payload != null) {
      logger.info("Socket buffer reader received {}", payload);
    }

    printer.close();
    socket.close();
  }
}
