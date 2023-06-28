package org.example.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.model.game.Trade;

import java.net.URL;

public class TradeDetailsGFX extends Application {
    private Trade selectedTrade;

    public void setSelectedTrade(Trade selectedTrade) {
        this.selectedTrade = selectedTrade;
    }

    @Override
    public void start(Stage stage) throws Exception {
        URL tradeDetailsFXML = TradeDetailsGFX.class.getResource("/view/tradeDetailsMenu.fxml");
        FXMLLoader loader = new FXMLLoader(tradeDetailsFXML);
        Pane tradeDetailsPane= loader.load();
        ((TradeDetailsController) loader.getController()).setSelectedTrade(this.selectedTrade);
        Scene scene = new Scene(tradeDetailsPane);
        stage.setScene(scene);
        stage.setAlwaysOnTop(true);
        stage.setWidth(tradeDetailsPane.getPrefWidth());
        stage.setHeight(tradeDetailsPane.getPrefHeight());
        stage.setResizable(false);
        if (!stage.isShowing()) {
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        }
        stage.centerOnScreen();




    }
}
