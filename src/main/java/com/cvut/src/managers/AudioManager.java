package com.cvut.src.managers;
import javafx.scene.media.*;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The class to play sounds and music
 * @author ulcheyev
 **/
public class AudioManager  implements Runnable{
    private final static Logger logger = Logger.getLogger(AudioManager.class.getName());

    private MediaView mediaView;
    private String fileName;
    private String soundName;
    private AudioClip mediaPlayer2;

    /**
     * Initialize AudioManager class and play sound
     * @param name sound name
     **/
    public AudioManager(String name){
        this.soundName = name;
        try {
            init();
        }catch (URISyntaxException e) {
            e.printStackTrace();
            logger.log(Level.INFO, "While loading audio exception has been caught");
        }
    }

    //Method load sound and play it
    private void init() throws URISyntaxException {
        mediaView = new MediaView();
        switch (soundName.toUpperCase()){
            case "THEME" : fileName = getClass().getResource("/sounds/theme.wav").toURI().toString();
                break;
            case "WIN" : fileName = getClass().getResource("/sounds/win_sound.wav").toURI().toString();
                break;
            case "LOOSE" : fileName = getClass().getResource("/sounds/loose_sound.wav").toURI().toString();
                break;
            case "HIT" : fileName = getClass().getResource("/sounds/hit_explosion.wav").toURI().toString();
                break;
            case "DESTROY" : fileName = getClass().getResource("/sounds/destroy_explosion_2.mp3").toURI().toString();
                break;
            case "BOSS_DESTROY" : fileName = getClass().getResource("/sounds/boss_destroy.mp3").toURI().toString();
                break;
            case "SHOT" : fileName = getClass().getResource("/sounds/shot.wav").toURI().toString();
                break;
            case "MULTI_SHOT" : fileName = getClass().getResource("/sounds/multi_shot.mp3").toURI().toString();
                break;
        }
        Media media = new Media(fileName);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaView.setMediaPlayer(mediaPlayer);
        mediaPlayer2 = new AudioClip(media.getSource());
        mediaPlayer2.setVolume(60);
    }

    //Method which is called by thread. Plays selected sound
    private void playMusic() throws URISyntaxException {
        mediaPlayer2.play();
    }

    /**Plays select sound infinite times**/
    public void setInfiniteLoop(){
        mediaPlayer2.setCycleCount(AudioClip.INDEFINITE);
    }


    /**Run thread**/
    @Override
    public void run() {
        try {
            playMusic();
            return;
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
