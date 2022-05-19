package com.cvut.src.managers;

import com.cvut.src.view.components.MyShipMenu;
import javafx.fxml.Initializable;
import javafx.scene.media.*;
import javafx.util.Duration;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.File;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AudioManager  implements Runnable{
    private final static Logger logger = Logger.getLogger(AudioManager.class.getName());

    private MediaView mediaView;
    private String fileName;
    private String soundName;
    private AudioClip mediaPlayer2;

    public AudioManager(String name){
        this.soundName = name;
        try {
            init();
        }catch (URISyntaxException e) {
            e.printStackTrace();
            logger.log(Level.INFO, "While loading audio exception has been caught");
        }
    }

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

    public void playMusic() throws URISyntaxException {
        mediaPlayer2.play();
    }


    public void setInfiniteLoop(){
        mediaPlayer2.setCycleCount(AudioClip.INDEFINITE);
    }



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
