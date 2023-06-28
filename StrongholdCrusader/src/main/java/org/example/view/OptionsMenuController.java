package org.example.view;

import javafx.scene.input.MouseEvent;

public class OptionsMenuController {
    public void resumeGame(MouseEvent mouseEvent) {
        OptionsMenuGFX.stage.close();
    }

    public void quitGame(MouseEvent mouseEvent) {
        //quit implementation
    }

    public void restartGame(MouseEvent mouseEvent) {
        //I don't know
    }

    public void help(MouseEvent mouseEvent) {
        //show table of contents
    }

    public void options(MouseEvent mouseEvent) {
        //Do we have any sound effects yet? or we could change some controls

    }
}
