package dijkstra.dollhouse.gui.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * An istance of this class represents a server of the global chat.
 */
public class ChatServer {
  private static final int clientPort = 7777;
  private ServerSocket serverSocket;
  private ArrayList<ServerThread> clients;

  public ChatServer() throws IOException {
    clients = new ArrayList<>();
    serverSocket = new ServerSocket(clientPort);
  }

  /**
   * It starts the listening thread and every time a client
   * tries to connect to this server, it creates a ServerThread and
   * runs it in order to satisfy all its client requests.
   *
   * @throws IOException if an I/O error occurs when waiting for a connection.
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
   * Sends to all clients connected the corresponding message.
   *
   * @param message - the message to send.
   * @throws IOException if an I/O error occurs during the operation.
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
   * Starts the server.
   *
   * @param args .
   */
  public static void main(String[] args) {
    try {
      ChatServer server = new ChatServer();
      server.start();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}