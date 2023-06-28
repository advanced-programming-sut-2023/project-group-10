package org.example.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;

public class GameMenuGFX extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        URL gameMenuFXML = GameMenuGFX.class.getResource("/view/gameMenu.fxml");
        FXMLLoader loader = new FXMLLoader(gameMenuFXML);
        Pane rootPane = loader.load();
        Scene gameScene = primaryStage.getScene();
        gameScene.setRoot(rootPane);
        primaryStage.setMaximized(true);
        ((GameMenuGFXController) loader.getController()).prepareGame(primaryStage);
        gameScene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            switch (event.getCode()) {
                case TAB:
                    try {
                        new ShopMenuGFX().start(SignupMenu.stage);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case M:
                    ((GameMenuGFXController) loader.getController()).moveSelectedUnits();
                    break;
                case A:
                    ((GameMenuGFXController)loader.getController()).attackSelectedUnits();
                    break;
                case I:
                    ((GameMenuGFXController)loader.getController()).zoom(true);
                    break;
                case O:
                    ((GameMenuGFXController)loader.getController()).zoom(false);
                    break;
            }
        });
    }
}
