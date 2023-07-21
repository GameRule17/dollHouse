package dijkstra.dollhouse.gui.socket;

import java.net.*;
import java.io.*;

public class JChatClient {
    private DataOutputStream os;
    private BufferedReader is;
    private Socket socket;
    
    public void start() throws IOException {
        //Connessione della Socket con il Server
        socket = new Socket("0.0.0.0", 7777);
        
        //Stream di byte da passare al Socket
        os = new DataOutputStream(socket.getOutputStream());
        is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }
    
    public void sendMessage(String strMessage)throws IOException {
        os.writeBytes(strMessage + '\n');
    }
    
    
    public String receiveMessage() throws IOException {
        String strReceived = is.readLine();
        return strReceived;
    }
    
    public void close() throws IOException {
        System.out.println("Chiusura client");
        os.close();
        is.close();
        socket.close();
    }
}
