package dijkstra.dollhouse;

import java.io.File;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class MusicPlayer {
    private MediaPlayer mediaPlayer;
    
    public void playMusic(String filePath) {
        // Inizializza JavaFX solo se non è già stato inizializzato
        if (!Platform.isFxApplicationThread()) {
            new JFXPanel(); // Questo avvia l'ambiente JavaFX
        }
        
        Media media = new Media(new File(filePath).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        
        // Aggiungi un gestore di eventi per rilasciare le risorse del media player dopo la riproduzione
        mediaPlayer.setOnEndOfMedia(() -> {
            mediaPlayer.stop();
            mediaPlayer.dispose();
            System.out.println("Riproduzione terminata.");
        });
        
        mediaPlayer.play();
        System.out.println("Riproduzione iniziata.");
    }
    
    public void stopMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.dispose();
            System.out.println("Riproduzione interrotta.");
        }
    }
    
    public void setVolume(double volume) {
        if (mediaPlayer != null) {
            mediaPlayer.setVolume(volume);
            System.out.println("Volume impostato a: " + (volume * 100) + "%");
        }
    }
}
