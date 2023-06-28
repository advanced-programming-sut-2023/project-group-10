package org.example.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import org.example.model.User;
import org.example.model.game.Trade;

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

    @FXML
    public void initialize() {
        initializeBackButton();
        initializeDetails();
    }

    private void initializeDetails() {
        initializeSenderInfo();
        initializeItemInfo();
    }

    private void initializeItemInfo() {

        Image item = new Image("src/main/resources/images/items/" + selectedTrade.getItem().getName() + ".png");
        itemImage.setFill(new ImagePattern(item));

        itemName.setText("Name: " + selectedTrade.getItem().getName());
        tradeId.setText(selectedTrade.getId());
        price.setText(Integer.toString(selectedTrade.getPrice()));
        message.setText(selectedTrade.getMessage(selectedTrade.getSenderId()));


    }

    private void initializeSenderInfo() {
        username.setText(selectedTrade.getSenderId());
        //Maybe it has some minor problems

        avatar.setFill(new ImagePattern(new Image(User.getUserByUsername(selectedTrade.getSenderId()).getAvatar())));

    }

    private void initializeBackButton() {
        back.setFill(new ImagePattern(new Image(Objects.requireNonNull(TradeDetailsController.class.getResource("/images/icons/back.png")).toString())));
    }

    public void accept(MouseEvent mouseEvent) {
        //No idea actually

    }

    public void reject(MouseEvent mouseEvent) throws Exception {
        goBack(mouseEvent);
    }
}
