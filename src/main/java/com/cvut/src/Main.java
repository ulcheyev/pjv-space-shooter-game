package com.cvut.src;
import com.cvut.src.controller.GameController;
import com.cvut.src.managers.AudioManager;
import com.cvut.src.view.components.MainMenu;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws IOException {
        try{
            GameController gameController = new GameController(primaryStage);
            primaryStage.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
