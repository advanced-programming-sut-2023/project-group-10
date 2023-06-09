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
        buttons.setTranslateY(740);
        buttons.setTranslateX(630);
    }

    private void setTableOfItems() {

        File[] allFiles = new File[20];
        Item[] items = Item.values();
        int j = 0;
        for (int i = 0; i < items.length; i++) {
            if (!items[i].isSellable())
                continue;
            allFiles[j] = new File("src/main/resources/images/items/" + items[i].getName() + ".png");
            j++;
        }
        int columnCount = 5;
        for (int i = 0; i < allFiles.length; i++) {
            ImageView imageView = new ImageView();
            Image image = new Image(allFiles[i].toURI().toString(), 150, 150, false, false);
            imageView.setImage(image);
            imageView.setPreserveRatio(true);
            imageView.setFitWidth(150);
            imageView.setFitHeight(150);
            imageView.setOnMouseClicked(this::chooseItem);
            tableOfItems.getChildren().add(imageView);
            GridPane.setConstraints(imageView, i % columnCount, i / columnCount);
        }
        tableOfItems.setTranslateX(350);
        tableOfItems.setTranslateY(100);
    }

    private void chooseItem(MouseEvent mouseEvent)  {
        ItemDetailsMenuGFX itemDetailsMenuGFX=new ItemDetailsMenuGFX();
        String  path=((ImageView)mouseEvent.getSource()).getImage().getUrl().toString();
        itemDetailsMenuGFX.setSelectedItem(Item.getItemByPath(path));
        System.out.println(itemDetailsMenuGFX.getSelectedItem()== null);
        try {
            itemDetailsMenuGFX.start(ShopMenuGFX.stage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void back(MouseEvent mouseEvent) {
    }

    public void tradeMenu(MouseEvent mouseEvent) {
        try {
            new TradeMenuGFX().start(SignupMenu.stage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
