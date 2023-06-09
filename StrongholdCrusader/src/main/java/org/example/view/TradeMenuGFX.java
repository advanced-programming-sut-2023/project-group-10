package org.example.view;

import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;

public class TradeMenuGFX extends Application {
    private Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane tradePane = FXMLLoader.load(
                new URL(TradeMenuGFX.class.getResource("/view/tradeMenu" +
                        ".fxml").toExternalForm()));
        this.stage = primaryStage;

        ImageView background = new ImageView(new Image(TradeMenuGFX.class.getResource("/images/backgrounds/oldMap.png").toString(), 1440 ,900, false, true));
        ImageView background2 = new ImageView(new Image(TradeMenuGFX.class.getResource("/images/backgrounds/oldMap.png").toString(), 1440 ,900, false, true));
        tradePane.getChildren().addAll(background, background2,TradeMenuController.buttonBox,TradeMenuController.back);
        Scene scene = new Scene(tradePane);
        stage.setTitle("Trade Menu");
        TranslateTransition trans1 = new TranslateTransition(Duration.seconds(10), background);
        trans1.setFromX(0);
        trans1.setToX(1440);
        trans1.setCycleCount(-1);
        trans1.setDuration(Duration.seconds(35));
        TranslateTransition trans2 = new TranslateTransition(Duration.seconds(10), background2);
        trans2.setFromX(-1440);
        trans2.setToX(0);
        trans2.setCycleCount(-1);
        trans2.setDuration(Duration.seconds(35));
        ParallelTransition parTrans = new ParallelTransition(trans1, trans2);
        parTrans.play();
        stage.setFullScreen(true);
        stage.setScene(scene);
        stage.show();



    }
}
