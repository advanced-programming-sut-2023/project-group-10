package org.example.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.stage.Stage;

import java.io.File;

public class SoundController {
    GameMenuGFXController gameMenuGFXController;

    public void setGameMenuGFXController(GameMenuGFXController gameMenuGFXController) {
        this.gameMenuGFXController = gameMenuGFXController;
    }

    public void chooseSong(MouseEvent mouseEvent) {
        String themePath="src/main/resources/media/"+((Label)mouseEvent.getSource()).getText()+".mp3";
        Media media=new Media(new File(themePath).toURI().toString());
        gameMenuGFXController.setThemeSong(media);
    }

    public void mute(MouseEvent mouseEvent) {
        gameMenuGFXController.mute();
    }

    public void submit(MouseEvent mouseEvent) {
        SoundGFX.stage.close();
    }
}
