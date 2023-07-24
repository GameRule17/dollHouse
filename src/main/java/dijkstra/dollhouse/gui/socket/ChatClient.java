package dijkstra.dollhouse.gui.socket;

import dijkstra.dollhouse.gui.GlobalChatPanel;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * An istance of this class represents a client of the global chat.
 */
public class ChatClient implements Runnable {
  private static final String ipAddress = "127.0.0.1";
  private static final int port = 7777;
  private DataOutputStream output;
  private BufferedReader input;
  private Socket socket;
  private Thread receivingThread;

  /**
   * Tries to connect to the server, initializes all needed data and
   * starts the corresponding thread.
   *
   * @throws IOException In case the connection to the server fails.
   */
  public void start() throws IOException {
    socket = new Socket(ipAddress, port);
    output = new DataOutputStream(socket.getOutputStream());
    input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    receivingThread = new Thread(this);
    receivingThread.start();
  }

  public void sendMessage(String strMessage) throws IOException {
    output.writeBytes(strMessage + '\n');
  }

  /**
   * Close the connection with the server and all other needed resources.
   *
   * @throws IOException In case the connection to the server fails.
   */
  public void close() throws IOException {
    System.out.println("Chiusura client");
    output.close();
    input.close();
    socket.close();
  }

  public void stop() {
    receivingThread.interrupt();
    receivingThread = null;
  }

  @Override
  public void run() {
    while (receivingThread != null) {
      try {
        GlobalChatPanel.outputGlobalChatArea.append(input.readLine() + "\n");
      } catch (IOException exc) {
        stop();
      }
    }
  }
}