package org.example.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Border;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.example.model.Stronghold;
import org.example.model.game.Battle;
import org.example.model.game.Government;
import org.example.model.game.Trade;
import org.example.model.game.envirnmont.Map;

public class TradeMenuHistoryController {
    public VBox requestsFromUser;
    public VBox requestsToUser;

    @FXML
    public void initialize() {
        showRequestsByUser();
        showRequestsToUser();

    }

    private void showRequestsToUser() {
        requestsToUser.setPrefWidth(450);
        requestsToUser.setPrefHeight(850);
        requestsToUser.setTranslateX(200);
        Label title = new Label("Requests to you");
        title.setFont(new Font("PT Mono", 28));
        requestsToUser.getChildren().add(title);
        listOfReceived();
    }

    private void listOfReceived() {
    }

    private void showRequestsByUser() {
        requestsFromUser.setPrefWidth(650);
        requestsFromUser.setPrefHeight(850);
        requestsFromUser.setTranslateX(200);
        Label title = new Label("Requests from you");
        title.setFont(new Font("PT Mono", 28));
        requestsFromUser.getChildren().add(title);
        listOfSent();

    }

    private void listOfSent() {
        ListView<HBox> requests = new ListView<HBox>();
        requests.setPrefWidth(600);
        for (int i = 0; i < Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getTradeList().size(); i++) {
            if (Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getTradeList().get(i).getSenderId().equals(
                    Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getOwner().getUsername())) {
                Trade trade = Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getTradeList().get(i);
                HBox tradeInfo = new HBox();
                tradeInfo.setSpacing(10);
                Label id = new Label(trade.getId());
                Label sender = new Label(trade.getRecipientId());
                Label item = new Label(trade.getItem().getName());
                Label price = new Label(Integer.toString(trade.getPrice()));
                id.setFont(new Font("PT Mono", 20));
                sender.setFont(new Font("PT Mono", 20));
                item.setFont(new Font("PT Mono", 20));
                price.setFont(new Font("PT Mono", 20));
                tradeInfo.setBorder(Border.stroke(Color.CORNFLOWERBLUE));
                tradeInfo.getChildren().addAll(id, sender, item, price);
                requests.getItems().add(tradeInfo);
            }
        }
        requestsFromUser.getChildren().add(requests);


    }
}
