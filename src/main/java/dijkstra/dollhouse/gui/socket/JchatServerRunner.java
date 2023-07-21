package dijkstra.dollhouse.gui.socket;

import java.io.IOException;

public class JchatServerRunner {
    
    public static void main(String[] args) {
        try {
            JChatServer serverChat = new JChatServer();
            serverChat.start();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
    }
}
