package org.example.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.Border;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.example.model.Stronghold;
import org.example.model.game.Trade;

public class TradeMenuHistoryController {
    public VBox requestsFromUser;
    public VBox requestsToUser;

    @FXML
    public void initialize() {
        listOfSent();
        listOfReceived();

    }

    private void listOfReceived() {
        ListView<HBox> requests = new ListView<HBox>();
        requests.setPrefWidth(requestsToUser.getPrefWidth());
        for (int i = Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getTradeList().size()-1; i >= 0; i--) {
            if (Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getTradeList().get(i).getRecipientId().equals(
                    Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getOwner().getUsername())) {
                Trade trade = Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getTradeList().get(i);
                HBox tradeInfo = new HBox();
                tradeInfo.setSpacing(10);
                tradeInfo.setPrefHeight(35);
                Label id = new Label("No. " + trade.getId());
                Label sender = new Label("ID: " + trade.getSenderId());
                Label item = new Label("Item: " + trade.getItem().getName());
                Label price = new Label("Price: " + trade.getPrice());
                id.setFont(new Font("PT Mono", 10));
                sender.setFont(new Font("PT Mono", 10));
                item.setFont(new Font("PT Mono", 10));
                price.setFont(new Font("PT Mono", 10));

                tradeInfo.setBorder(Border.stroke(Color.CORNFLOWERBLUE));
                tradeInfo.getChildren().addAll(id, sender, item, price);
                if (trade.isViewed()) {
                    tradeInfo.setBackground(Background.fill(Color.GRAY));
                    Label viewed = new Label("\"viewed\"");
                    viewed.setFont(new Font("Helvetica", 10));
                    tradeInfo.getChildren().add(viewed);
                } else {
                    Button viewButton = new Button("view");
                    viewButton.setFont(new Font("Helvetica", 10));
                    viewButton.setBackground(Background.fill(Color.LIGHTBLUE));
                    viewButton.setOnMouseClicked(this::tradeDetails);
                    tradeInfo.getChildren().add(viewButton);
                }
                requests.getItems().add(tradeInfo);

            }
        }
        requestsToUser.getChildren().add(requests);


    }

    private void tradeDetails(MouseEvent mouseEvent) {
        String id = ((Label) ((Button) mouseEvent.getSource()).getParent().getChildrenUnmodifiable().get(0)).getText();
        id = id.replace("No. ", "");
        System.out.println(id);
        Trade selectedTrade = new Trade("", "", "", null, 0, 0);
        for (int i = 0; i < Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getTradeList().size(); i++) {
            if (Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getTradeList().get(i).getId().equals(id))
                selectedTrade = Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getTradeList().get(i);
        }
        TradeDetailsGFX tradeDetailsGFX = new TradeDetailsGFX();
        tradeDetailsGFX.setSelectedTrade(selectedTrade);

        try {
            tradeDetailsGFX.start(ShopMenuGFX.stage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        selectedTrade.setViewed(true);
    }


    private void listOfSent() {
        ListView<HBox> requests = new ListView<HBox>();
        requests.setPrefWidth(requestsFromUser.getPrefWidth());
        System.out.println(Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getTradeList().size());
        for (int i = Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getTradeList().size()-1; i >= 0; i--) {
            if (Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getTradeList().get(i).getSenderId().equals(
                    Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getOwner().getUsername())) {
                Trade trade = Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getTradeList().get(i);
                HBox tradeInfo = new HBox();
                tradeInfo.setSpacing(10);
                tradeInfo.setPrefHeight(35);
                Label id = new Label("No. " + trade.getId());
                Label receiver = new Label("ID: " + trade.getRecipientId());
                Label item = new Label("Item: " + trade.getItem().getName());
                Label price = new Label("Price: " + trade.getPrice());
                Label state = new Label(trade.isAcceptedStatus() ? "accepted" : "not accepted");
                id.setFont(new Font("PT Mono", 10));
                receiver.setFont(new Font("PT Mono", 10));
                item.setFont(new Font("PT Mono", 10));
                price.setFont(new Font("PT Mono", 10));
                state.setFont(new Font("PT Mono", 10));
                tradeInfo.setBorder(Border.stroke(Color.CORNFLOWERBLUE));
                tradeInfo.getChildren().addAll(id, receiver, item, price, state);
                requests.getItems().add(tradeInfo);

            }
        }
        requestsFromUser.getChildren().add(requests);

    }


    public void back(MouseEvent mouseEvent) throws Exception {
        new TradeMenuGFX().start(ShopMenuGFX.stage);
    }
}
