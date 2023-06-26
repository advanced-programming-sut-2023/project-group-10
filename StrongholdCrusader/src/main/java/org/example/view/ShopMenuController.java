package org.example.view;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import org.example.model.game.Item;

import java.io.File;

public class ShopMenuController {
    public GridPane tableOfItems;
    public HBox buttons;

    @FXML
    private void initialize() {
        setTableOfItems();
        buttons.setTranslateY(250);
        buttons.setTranslateX(200);
    }

    private void setTableOfItems() {

        File[] allFiles = new File[20];
        Item[] items = Item.values();
        int j = 0;
        for (int i = 0; i < items.length; i++) {
            if (!items[i].isSellable()) continue;
            allFiles[j] = new File("src/main/resources/images/items/" + items[i].getName() + ".png");
            j++;
        }
        int columnCount = 10;
        for (int i = 0; i < allFiles.length; i++) {
            ImageView imageView = new ImageView();
            Image image = new Image(allFiles[i].toURI().toString(), 80, 80, false, false);
            imageView.setImage(image);
            imageView.setPreserveRatio(true);
            imageView.setFitWidth(50);
            imageView.setFitHeight(50);

            imageView.setOnMouseClicked(this::chooseItem);
            tableOfItems.getChildren().add(imageView);
            GridPane.setConstraints(imageView, i % columnCount, i / columnCount);
        }
        tableOfItems.setTranslateX(100);
        tableOfItems.setTranslateY(100);
    }

    private void chooseItem(MouseEvent mouseEvent) {
        ItemDetailsMenuGFX itemDetailsMenuGFX = new ItemDetailsMenuGFX();
        String path = ((ImageView) mouseEvent.getSource()).getImage().getUrl();
        itemDetailsMenuGFX.setSelectedItem(Item.getItemByPath(path));
        System.out.println(ItemDetailsMenuGFX.getSelectedItem() == null);
        try {
            itemDetailsMenuGFX.start(ShopMenuGFX.stage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void back() {
        ShopMenuGFX.stage.close();
    }

    public void tradeMenu() {
        try {
            new TradeMenuGFX().start(ShopMenuGFX.stage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
