package org.example.view;

import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class PopularityFactorsMenuController {
    public void prepare(Stage primaryStage) {
        // TODO: update view based on government's popularity factors
    }

    public void goToChangeRateMenu(MouseEvent mouseEvent) throws Exception {
        new ChangeRateMenuGFX().start(SignupMenu.stage);
    }
}
