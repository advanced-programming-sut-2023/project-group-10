package org.example.view;

import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import org.example.controller.GameMenuController;
import org.example.model.Stronghold;
import org.example.model.game.Government;
import org.example.model.game.envirnmont.Coordinate;
import org.example.model.game.envirnmont.Map;

import java.util.HashMap;

public class OptionsMenuController {
    public void resumeGame(MouseEvent mouseEvent) {
        OptionsMenuGFX.stage.close();
    }

    public void quitGame(MouseEvent mouseEvent) {
        Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getLord().killMe();
        OptionsMenuGFX.stage.close();
    }

    public void restartGame(MouseEvent mouseEvent) throws Exception {
        OptionsMenuGFX.stage.close();
        HashMap<String, Color> colors = new HashMap<>();
        HashMap<String, Coordinate> keeps = new HashMap<>();
        for (Government government : Stronghold.getCurrentBattle().getGovernments()) {
            colors.put(government.getOwner().getUsername(),government.getColor());
            keeps.put(government.getOwner().getUsername(),government.getKeep());
        }
        GameMenuController.initializeGame(colors, keeps, new Map(Stronghold.getCurrentBattle().getBattleMap().getSize()));
        new CustomizeMapMenuGFX().start(SignupMenu.stage);

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
