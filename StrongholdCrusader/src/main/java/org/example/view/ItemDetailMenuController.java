package org.example.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.Border;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import org.example.controller.ShopMenuController;
import org.example.model.Stronghold;
import org.example.view.enums.messages.ShopMenuMessages;

import java.io.File;

import static org.example.view.ItemDetailsMenuGFX.selectedItem;


public class ItemDetailMenuController {

    public ImageView imageOfItem;
    public VBox amountBox;
    public Label itemAmount;
    public HBox buyBox;
    public HBox sellBox;
    public Label name;
    public HBox storageInfo;
    public HBox infos;
    public VBox rightBox;
    public Button buyButton;
    public Button sellButton;
    private int amount;
    private Label storage;


    @FXML
    public void initialize() {
        initializeStorageInfo();
        infos.setTranslateX(400);
        infos.setTranslateY(200);
        rightBox.setTranslateY(100);
        initializeBuyLabel();
        initializeSellLabel();
        initializeNameLabel();
        showPhoto();
        showAmount();
    }

    private void initializeNameLabel() {
        name.setText(enhanceName(selectedItem.getName()));
        name.setFont(new Font("Didot", 48));
        name.setAlignment(Pos.CENTER);
    }

    private String enhanceName(String name) {
        String enhanced = "";
        for (int i = 0; i < name.length(); i++) {
            if (i == 0)
                enhanced = enhanced.concat(String.valueOf(name.charAt(0)).toUpperCase());
            else
                enhanced = enhanced.concat(String.valueOf(name.charAt(i)));
        }
        return enhanced;
    }

    private void initializeStorageInfo() {
        double count = Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getItemCount(selectedItem);
        storage = new Label("current storage : " + count);
        storage.setFont(new Font("PT Mono", 16));
        storageInfo.getChildren().add(storage);
    }

    private void updateStorageLabel() {
        double count = Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getItemCount(selectedItem);
        storage.setText("current storage : " + count);
        storage.setFont(new Font("PT Mono", 16));

    }

    private void showPhoto() {
        imageOfItem.setImage(new Image(new File("src/main/resources/images/items/" + selectedItem.getName() + ".png").toURI().toString()));

        imageOfItem.setFitHeight(350);
        imageOfItem.setFitWidth(350);

    }

    private void initializeSellLabel() {
        sellBox.setPrefWidth(250);
        sellButton = new Button("Sell  " + selectedItem.getSellPrice());
        sellButton.setFont(new Font("PT Mono", 16));
        sellButton.setBackground(Background.fill(Color.MOCCASIN));
        sellButton.setBorder(Border.stroke(Color.FIREBRICK));
        sellButton.setOnMouseClicked(this::sell);
        sellButton.setPrefWidth(200);
        sellButton.setPrefHeight(40);
        sellBox.getChildren().add(sellButton);
    }

    private void initializeBuyLabel() {
        buyBox.setPrefWidth(250);
        buyButton = new Button("Buy  " + selectedItem.getBuyPrice());
        buyButton.setFont(new Font("PT Mono", 16));
        buyButton.setBackground(Background.fill(Color.MOCCASIN));
        buyButton.setBorder(Border.stroke(Color.FIREBRICK));
        buyButton.setOnMouseClicked(this::buy);
        buyButton.setPrefWidth(200);
        buyButton.setPrefHeight(40);
        buyBox.getChildren().add(buyButton);

    }

    private void showAmount() {
        //change the values
        Slider amountSlider = new Slider(1, 100, 1);
        amountSlider.setOrientation(Orientation.HORIZONTAL);
        amount = (int) amountSlider.getMin();
        itemAmount.setText("item amount : " + String.format("%02d", amount));
        amountSlider.setBlockIncrement(1);
        amountBox.getChildren().add(amountSlider);
        itemAmount.setLabelFor(amountSlider);
        amountSlider.valueProperty().addListener(observable -> {
            amount = (int) amountSlider.getValue();
            itemAmount.setText("item amount = " + String.format("%02d", amount));
        });

    }

    public void cancel(MouseEvent mouseEvent) throws Exception {

        new ShopMenuGFX().start(ShopMenuGFX.stage);
    }

    public void sell(MouseEvent mouseEvent) {
        ShopMenuMessages message = ShopMenuController.sell(selectedItem.getName(), amount);
        Text text = new Text();
        updateStorageLabel();
        switch (message) {
            case INVALID_ITEM:
                text.setText("There is no such item!");
                text.setFill(Color.ORANGERED);
                break;
            case INVALID_AMOUNT:
                text.setText("You must enter a number greater than 0!");
                text.setFill(Color.ORANGERED);
                break;
            case INSUFFICIENT_AMOUNT:
                text.setText("You don't have enough storage of this item!");
                text.setFill(Color.ORANGERED);
                break;
            case SUCCESS:
                text.setText("you've successfully sold the item!");
                text.setFill(Color.LIMEGREEN);
                break;
        }
        text.setFont(new Font("Courier new", 15));
        Popup popup = new Popup();
        popup.getContent().add(text);
        popup.setAutoHide(true);
        EventHandler<ActionEvent> event =
                new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent e) {
                        if (!popup.isShowing())
                            popup.show(SignupMenu.stage);
                    }
                };
        sellButton.setOnAction(event);


    }

    public void buy(MouseEvent mouseEvent) {
        ShopMenuMessages message = ShopMenuController.buy(selectedItem.getName(), amount);
        Text text = new Text();
        updateStorageLabel();

        switch (message) {
            //convert these to popups
            case INVALID_ITEM:
                text.setText("There is no such item in the shop!");
                text.setFill(Color.ORANGERED);
                break;
            case INVALID_AMOUNT:
                text.setText("You must enter a number greater than 0!");
                text.setFill(Color.ORANGERED);
                break;
            case INSUFFICIENT_GOLD:
                text.setText("You don't have enough gold to buy this item!");
                text.setFill(Color.ORANGERED);
                break;
            case SUCCESS:
                text.setText("you've successfully purchased the item!");
                text.setFill(Color.LIMEGREEN);
                break;
        }
        text.setFont(new Font("Courier new", 15));
        Popup popup = new Popup();
        popup.getContent().add(text);
        popup.setAutoHide(true);
        EventHandler<ActionEvent> event =
                new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent e) {
                        if (!popup.isShowing())
                            popup.show(SignupMenu.stage);
                    }
                };
        buyButton.setOnAction(event);

    }

}
