package org.example.view;

import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import org.example.controller.ShopMenuController;
import org.example.view.enums.messages.ShopMenuMessages;

import java.io.File;

import static org.example.view.ItemDetailsMenuGFX.selectedItem;


public class ItemDetailMenuController {

    public ImageView imageOfItem;
    public VBox amountBox;
    public Label itemAmount;
    public HBox buyBox;
    public HBox sellBox;
    private int amount;


    @FXML
    public void initialize() {
        showPhoto();
        initializeBuyLabel();
        initializeSellLabel();
        showAmount();
    }

    private void showPhoto() {
        imageOfItem.setImage(new Image(new File("src/main/resources/images/items/"+selectedItem.getName()+".png").toURI().toString()));

        imageOfItem.setFitHeight(100);
        imageOfItem.setFitWidth(100);
//        imageOfItem.setTranslateX(20);
//        imageOfItem.setTranslateY(100);
        System.out.println(selectedItem.getName());
        System.out.println(imageOfItem.getX());
    }

    private void initializeSellLabel() {
        sellBox.setPrefWidth(250);
        Label sellPrice = new Label();
        sellPrice.setText("sell price : " + selectedItem.getSellPrice());
        sellPrice.setFont(new Font("PT Mono", 14));
        sellPrice.setMinWidth(160);
        Button sell = new Button("sell");

        sell.setOnMouseClicked(this::sell);
        sell.setPrefWidth(60);
        System.out.println(sell.getWidth());
        sellBox.getChildren().addAll(sellPrice, sell);

    }

    private void initializeBuyLabel() {
        buyBox.setPrefWidth(250);
        Label buyPrice = new Label();
        buyPrice.setText("buy price : " + selectedItem.getBuyPrice());
        buyPrice.setFont(new Font("PT Mono", 14));
        buyPrice.setMinWidth(160);
        Button buy = new Button("buy");
        buy.setOnMouseClicked(this::sell);
        buy.setPrefWidth(60);
        buyBox.getChildren().addAll(buyPrice, buy);

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
        // how did Rozhin handle this?
        new ShopMenuGFX().start(SignupMenu2.stage);
    }

    public void sell(MouseEvent mouseEvent) {
        ShopMenuMessages message = ShopMenuController.sell(selectedItem.getName(), amount);
        switch (message) {
            case INVALID_ITEM:
                System.out.println("There is no such item!");
                break;
            case INVALID_AMOUNT:
                System.out.println("You must enter a number greater than 0!");
                break;
            case INSUFFICIENT_AMOUNT:
                System.out.println("You don't have enough storage of this item!");
                break;
            case SUCCESS:
                System.out.println("you've successfully sold the item!");
        }

    }

    public void buy(MouseEvent mouseEvent) {
        ShopMenuMessages message = ShopMenuController.buy(selectedItem.getName(), amount);
        switch (message) {
            //convert these to popups
            case INVALID_ITEM:
                System.out.println("There is no such item in the shop!");
                break;
            case INVALID_AMOUNT:
                System.out.println("You must enter a number greater than 0!");
                break;
            case INSUFFICIENT_GOLD:
                System.out.println("You don't have enough gold to buy this item!");
                break;
            case SUCCESS:
                System.out.println("you've successfully purchased the item!");
        }
    }

}
