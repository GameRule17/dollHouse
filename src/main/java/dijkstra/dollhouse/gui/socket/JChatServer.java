package dijkstra.dollhouse.gui.socket;

import java.net.*;
import java.io.*;
import java.util.*;

public class JChatServer {
    private ServerSocket serverSocket;
    private static final int clientPort = 7777;
    private ArrayList<Thread> clients;

    public JChatServer() throws IOException {
        // clients = new ArrayList();
        serverSocket = new ServerSocket(clientPort);
    }
    
    
    public void start() throws IOException {
        Socket client;
        System.out.println("Server in ascolto alla porta " + clientPort);
        
        // Ciclo infinito in ascolto di eventuali Client
        while (true) {
            client = serverSocket.accept();
            System.out.println("Connesso Client: " + client.getInetAddress() + " porta: "
                + clientPort);
            startListeningSingleClient(client);
        }
    }
    
    
    private void startListeningSingleClient(Socket client) {
        Thread t = new Thread (new ParallelServer(client));
        t.start();
    }
    
    
    public class ParallelServer implements Runnable {
        private Socket client;
        private BufferedReader is;
        private DataOutputStream os;
        
        public ParallelServer(Socket client) {
            this.client = client;
            try {
                is = new BufferedReader(new InputStreamReader(client.getInputStream()));
                os = new DataOutputStream(client.getOutputStream());
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        
        @Override
        public void run() {
            try {
                while (true) {
                    sendMessage(receiveMessage(is), is, os, client);
                }
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    
    public String receiveMessage(BufferedReader is) throws IOException {
        String strReceived = is.readLine();
        
        System.out.println("Il Client ha inviato: " + strReceived);
        return strReceived ;
    }
    
    public void sendMessage(String recMsg, BufferedReader is, DataOutputStream os,
            Socket client ) throws IOException {
        for(Iterator all = clients.iterator(); all.hasNext();) {
            Socket cl = (Socket)all.next();
            broadcastMessage(recMsg, cl);
        }
    }
    
    private void broadcastMessage(String recMsg, Socket cl) throws IOException {
        new DataOutputStream(cl.getOutputStream()).writeBytes(recMsg + "\n");
    }
    
    /*
    public void close(InputStream is, OutputStream os, Socket client)
    throws IOException {
    try (client) {
    try (is; os) {
    System.out.println("Chiamata close");
    // chiusura della comunicazione con il Client
    }
    System.out.println("Chiusura chiamata Client: " +
    client.getInetAddress().getHostName() + "su porta: " + clientPort);
    }
    } */
}
