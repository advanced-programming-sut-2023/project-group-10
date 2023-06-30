package org.example.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.example.model.game.Government;

public class EndScreenGFX extends Application {
    private final Government winner;

    public EndScreenGFX(Government winner) {
        this.winner = winner;
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(EndScreenGFX.class.getResource("/view/endScreen.fxml"));
        VBox pane = loader.load();
        Image image = new Image(EndScreenGFX.class.getResource("/images/backgrounds/background12.jpeg").toExternalForm());
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER, new BackgroundSize(100, 100, true, true, true, true));
        pane.setBackground(new Background(backgroundImage));
        stage.getScene().setRoot(pane);
        stage.setMaximized(true);
        EndScreenController controller = loader.getController();
        controller.setWinner(winner);
        controller.prepare();
    }
}
