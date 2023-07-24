package dijkstra.dollhouse;

import java.io.File;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * Static class to play and stop music.
 */
public class MusicPlayer {
  private static MediaPlayer mediaPlayer;

  /**
   * Play the mp3 file in the file path.
   *
   * @param filePath - the file path of the mp3.
   */
  public static void playMusic(String filePath) {
    // Inizializza JavaFX solo se non è già stato inizializzato
    if (!Platform.isFxApplicationThread()) {
      new JFXPanel(); // Questo avvia l'ambiente JavaFX
    }

    Media media = new Media(new File(filePath).toURI().toString());
    mediaPlayer = new MediaPlayer(media);

    setVolume(0.03);

    // Aggiungi un gestore di eventi per rilasciare le risorse del media player dopo la riproduzione
    mediaPlayer.setOnEndOfMedia(() -> {
      playMusic(filePath);
      setVolume(0.03);
    });

    mediaPlayer.play();
    // System.out.println("Riproduzione iniziata.");
  }

  /**
   * Stops the music.
   */
  public static void stopMusic() {
    if (mediaPlayer != null) {
      mediaPlayer.stop();
      mediaPlayer.dispose();
      System.out.println("Riproduzione interrotta.");
    }
  }

  /**
   * Sets the volume.
   *
   * @param volume - the value of the volume.
   */
  public static void setVolume(double volume) {
    if (mediaPlayer != null) {
      mediaPlayer.setVolume(volume);
      // System.out.println("Volume impostato a: " + (volume * 100) + "%");
    }
  }
}