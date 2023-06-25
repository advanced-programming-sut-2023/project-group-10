package org.example.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.example.model.utils.RandomGenerator;

import java.net.URL;

public class TradeMenuHistoryGFX extends Application {
    private Object stage;

    @Override
    public void start(Stage stage) throws Exception {
        Pane historyPane = FXMLLoader.load(
                new URL(TradeMenuHistoryGFX.class.getResource("/view/tradeHistoryMenu.fxml").toExternalForm()));
        historyPane.setBackground(new Background(RandomGenerator.setBackground("/images/backgrounds/tradeMenuBackground.jpeg")));
        Scene scene = new Scene(historyPane, 1390, 850);
        stage.setScene(scene);
        this.stage = stage;
        stage.setFullScreen(true);
        stage.show();
    }
}
