package it.polimi.is23am10.client.examples;

import com.google.gson.Gson;

import it.polimi.is23am10.server.command.AddPlayerCommand;
import it.polimi.is23am10.utils.config.AppConfig;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This an example class.
 *
 */
public class SocketClientAddPlayerExample {

  /**
   * Main method.
   *
   * @throws InterruptedException Thrown by timer.
   * @throws IOException On readline failure.
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
    final Logger logger = LogManager.getLogger(SocketClientAddPlayerExample.class);
    AddPlayerCommand command = new AddPlayerCommand("Alice", UUID.fromString(""));

    // create the json string
    String message = gson.toJson(command);

    // establish socket connection to server
    socket = new Socket(host.getHostName(), AppConfig.getServerSocketPort());

    // write to socket using PrintWriter.
    printer = new PrintWriter(socket.getOutputStream(), true, StandardCharsets.UTF_8);
    logger.info("Sending request to Socket Server {}", message);
    printer.println(message);

    DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
    while (dataInputStream.available() == 0) {
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
