package org.example.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import org.example.controller.TradeMenuController;
import org.example.model.User;
import org.example.model.game.Trade;
import org.example.view.enums.messages.TradeMenuMessages;

import java.io.File;
import java.util.Objects;

public class TradeDetailsController {
    public Rectangle avatar;
    public Label username;
    public Rectangle itemImage;
    public Label tradeId;
    public Label price;
    public Label itemName;
    public Rectangle back;
    public Text message;
    private Trade selectedTrade;

    public void setSelectedTrade(Trade selectedTrade) {
        this.selectedTrade = selectedTrade;
    }

    public void goBack(MouseEvent mouseEvent) throws Exception {
        new TradeMenuHistoryGFX().start(ShopMenuGFX.stage);
    }

    public void prepare() {
        initializeBackButton();
        initializeDetails();
    }

    private void initializeDetails() {
        initializeSenderInfo();
        initializeItemInfo();
    }

    private void initializeItemInfo() {
        System.out.println(selectedTrade.getItem().getName());
        Image item = new Image(new File("src/main/resources/images/items/" + selectedTrade.getItem().getName() + ".png").toURI().toString());

        itemImage.setFill(new ImagePattern(item));

        itemName.setText("Name: " + selectedTrade.getItem().getName());
        tradeId.setText(selectedTrade.getId());
        price.setText(Integer.toString(selectedTrade.getPrice()));
        message.setText(selectedTrade.getMessage(selectedTrade.getSenderId()));


    }

    private void initializeSenderInfo() {
        username.setText(selectedTrade.getSenderId());
        avatar.setFill(new ImagePattern(new Image(User.getUserByUsername(selectedTrade.getSenderId()).getAvatar())));

    }

    private void initializeBackButton() {
        back.setFill(new ImagePattern(new Image(Objects.requireNonNull(TradeDetailsController.class.getResource("/images/icons/back.png")).toString())));
    }

    public void accept(MouseEvent mouseEvent) {
        TradeMenuMessages tradeMenuMessage= TradeMenuController.acceptRequest(selectedTrade.getId());
        Alert alert =new Alert(Alert.AlertType.CONFIRMATION);
        switch (tradeMenuMessage){
            case NOT_ENOUGH_SPACE:
                alert=new Alert(Alert.AlertType.ERROR);
                alert.setTitle("error in accepting the trade");
                alert.setContentText("you don't have enough space");
                break;
            case NOT_SUFFICIENT_GOLD:
                alert=new Alert(Alert.AlertType.ERROR);
                alert.setTitle("error in accepting the trade");
                alert.setContentText("you don't have enough gold");
                break;
            case TRADE_SUCCESSFULLY_ACCEPTED:
                alert=new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("successful accept trade");
                alert.setContentText("you accepted the trade successfully");
                break;
        }
        alert.showAndWait();
    }

    public void reject(MouseEvent mouseEvent) throws Exception {
        goBack(mouseEvent);
    }
}
