package org.example.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.net.URL;

public class TradeMenuHistoryGFX extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Pane historyPane = FXMLLoader.load(
                new URL(TradeMenuHistoryGFX.class.getResource("/view/tradeHistoryMenu.fxml").toExternalForm()));
        javafx.scene.image.Image image = new javafx.scene.image.Image(ShopMenuGFX.class.getResource("/images/backgrounds/tradeMenuBackground.jpeg").toExternalForm());
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(100, 100, true, true, true, true));
        historyPane.setBackground(new Background(backgroundImage));
        stage.getScene().setRoot(historyPane);
        stage.setMaximized(true);
    }
}
