package org.example.view;

import javafx.scene.input.MouseEvent;
import org.example.model.Stronghold;

public class OptionsMenuController {
    public void resumeGame(MouseEvent mouseEvent) {
        OptionsMenuGFX.stage.close();
    }

    public void quitGame(MouseEvent mouseEvent) {
        Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getLord().killMe();
        OptionsMenuGFX.stage.close();
    }

    public void restartGame(MouseEvent mouseEvent) {
        //I don't know
    }

    public void help(MouseEvent mouseEvent) {
      Help help=new Help();
        try {
            help.start(OptionsMenuGFX.stage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void options(MouseEvent mouseEvent) {
        //Do we have any sound effects yet? or we could change some controls

    }
}
