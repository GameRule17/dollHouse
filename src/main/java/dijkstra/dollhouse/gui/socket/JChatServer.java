package dijkstra.dollhouse.gui.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * .
 */
public class JChatServer {
  private static final int clientPort = 7777;
  private ServerSocket serverSocket;
  private ArrayList<ServerThread> clients;

  public JChatServer() throws IOException {
    clients = new ArrayList<>();
    serverSocket = new ServerSocket(clientPort);
  }

  /**
   * .
   *
   * @throws IOException .
   */
  public void start() throws IOException {
    Socket client;
    ServerThread clientThread;
    System.out.println("Server in ascolto alla porta " + clientPort);

    while (true) {
      client = serverSocket.accept();
      System.out.println("Connesso: " + client.getInetAddress() + " porta: " + clientPort);
      clientThread = new ServerThread(client, this);
      clients.add(clientThread);
      clientThread.start();
    }
  }

  /**
   * .
   *
   * @param message .
   * @throws IOException .
   */
  public void sendBroadcastMessage(String message) throws IOException {
    for (ServerThread client : clients) {
      client.sendMessage(message);
    }
  }

  public void removeServerThread(final ServerThread thread) {
    clients.remove(thread);
    System.out.println("Disconnesso: " + thread.getSocket().getInetAddress());
  }

  /**
   * .
   *
   * @param args .
   */
  public static void main(String[] args) {
    try {
      JChatServer server = new JChatServer();
      server.start();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}