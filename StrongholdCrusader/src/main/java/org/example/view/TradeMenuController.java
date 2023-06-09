package org.example.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class TradeMenuController {
    public static Button back=new Button();
    public Button makeRequest;
    public static HBox buttonBox = new HBox();
    public Button history;

    @FXML
    private void initialize() {
        buttonBox.setSpacing(100);
        showMakeRequestButton();
        showHistoryButton();
        showBackButton();
        buttonBox.setTranslateY(400);
        buttonBox.setTranslateX(300);
        buttonBox.setPrefWidth(1000);

    }

    private void showBackButton() {
        back.setText("back");
        back.setBackground(Background.fill(Color.SNOW));
        back.setFont(new Font("Courier new",14));
        back.setTranslateX(10);
        back.setTranslateY(10);
        back.setOnMouseClicked(this::back);

    }

    private void back(MouseEvent mouseEvent)  {
        try {
            new ShopMenuGFX().start(SignupMenu.stage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void showHistoryButton() {
        history = new Button();
        history.setText("Show All Trades");
        history.setPrefWidth(500);
        history.setPrefHeight(100);
        history.setFont(new Font("PT Mono", 32));
        history.setBackground(Background.fill(Color.MOCCASIN));
        history.setOnMouseClicked(this::showHistory);
        buttonBox.getChildren().add(history);

    }

    private void showHistory(MouseEvent mouseEvent) {
    }

    private void showMakeRequestButton() {
        makeRequest = new Button();
        makeRequest.setText("Make New Trade");
        makeRequest.setPrefWidth(500);
        makeRequest.setPrefHeight(100);
        makeRequest.setFont(new Font("PT Mono", 32));
        makeRequest.setBackground(Background.fill(Color.MOCCASIN));
        makeRequest.setOnMouseClicked(this::makeRequest);
        buttonBox.getChildren().add(makeRequest);
    }

    private void makeRequest(MouseEvent mouseEvent) {
        try {
            new TradeRequestMenuGFX().start(SignupMenu.stage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
