package org.example.view;

import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;

public class TradeMenuGFX extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane tradePane = FXMLLoader.load(
                new URL(TradeMenuGFX.class.getResource("/view/tradeMenu.fxml").toExternalForm()));
        tradePane.getChildren().clear();

        ImageView background = new ImageView(new Image(TradeMenuGFX.class.getResource("/images/backgrounds/trade.jpg").toString()));
        background.setPreserveRatio(true);
        background.setFitHeight(primaryStage.getHeight());
        background.setOpacity(0.3);
        ImageView background2 = new ImageView(background.getImage());
        background2.setPreserveRatio(true);
        background2.setFitHeight(primaryStage.getHeight());
        background2.setOpacity(0.3);
        tradePane.getChildren().addAll(background, background2, TradeMenuController.buttonBox, TradeMenuController.back);
        primaryStage.setTitle("Trade Menu");
        TranslateTransition trans1 = new TranslateTransition(Duration.seconds(25), background);
        trans1.setInterpolator(Interpolator.LINEAR);
        trans1.setFromX(0);
        trans1.setToX(background.getImage().getWidth() / background.getImage().getHeight() * background.getFitHeight());
        trans1.setCycleCount(-1);
        TranslateTransition trans2 = new TranslateTransition(Duration.seconds(25), background2);
        trans2.setInterpolator(Interpolator.LINEAR);
        trans2.setFromX(-trans1.getToX());
        trans2.setToX(0);
        trans2.setCycleCount(-1);
        ParallelTransition parTrans = new ParallelTransition(trans1, trans2);
        parTrans.play();
        primaryStage.getScene().setRoot(tradePane);
        primaryStage.setWidth(tradePane.getPrefWidth());
        primaryStage.setHeight(tradePane.getPrefHeight());
        primaryStage.centerOnScreen();
    }
}
