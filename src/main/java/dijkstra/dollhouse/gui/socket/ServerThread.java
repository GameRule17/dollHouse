package dijkstra.dollhouse.gui.socket;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 *  .
 */
public class ServerThread implements Runnable {
  private JChatServer server;
  private Socket client;
  private BufferedReader input;
  private DataOutputStream output;
  private Thread thread;

  /**
   * .
   *
   * @param client .
   * @param server .
   * @throws IOException .
   */
  public ServerThread(final Socket client, final JChatServer server) throws IOException {
    this.client = client;
    this.server = server;
    input = new BufferedReader(new InputStreamReader(client.getInputStream()));
    output = new DataOutputStream(client.getOutputStream());
  }

  public Socket getSocket() {
    return client;
  }

  public void start() {
    thread = new Thread(this);
    thread.start();
  }

  public void stop() {
    thread.interrupt();
    thread = null;
  }

  public void sendMessage(final String message) throws IOException {
    output.writeBytes(message);
  }

  @Override
  public void run() {
    String message;
    while (thread != null) {
      try {
        message = input.readLine();
        server.sendBroadcastMessage(message + "\n");
      } catch (IOException e) {
        stop();
        server.removeServerThread(this);
      }
    }
  }
}
