package dijkstra.dollhouse;

import javafx.application.Application;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class MusicPlayer extends Application {
    private MediaPlayer mediaPlayer;

    public void playMusic(String filePath) {
        Media media = new Media(filePath);
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
        System.out.println("Riproduzione iniziata.");
    }

    public void stopMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            System.out.println("Riproduzione interrotta.");
        }
    }

    @Override
    public void start(Stage primaryStage) {
        // Metodo richiesto dall'estensione di Application
    }

    public static void main(String[] args) {
        launch(args);
    }
}


/*
OPERAZIONI IN MAIN:

public class Main {
    public static void main(String[] args) {
        MusicPlayer player = new MusicPlayer();
        String filePath = "path_to_your_mp3_file.mp3";
        player.playMusic(filePath);

        // Per interrompere la riproduzione
        player.stopMusic();
    }
}


 */
