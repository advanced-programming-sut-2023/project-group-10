package org.example.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.example.model.game.Item;

import java.net.URL;

public class ItemDetailsMenuGFX extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane itemDetails = FXMLLoader.load(
                new URL(ShopMenuGFX.class.getResource("/view/itemDetails.fxml").toExternalForm()));
        Image image = new Image(ItemDetailsMenuGFX.class.getResource("/images/backgrounds/brownPaper.jpeg").toExternalForm(), 1440 ,900, false, true);
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        itemDetails.setBackground(new Background(backgroundImage));
        Scene scene = new Scene(itemDetails);

        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }


    public static Item selectedItem;

    public void setSelectedItem(Item selectedItem) {
        this.selectedItem = selectedItem;
    }

    public static Item getSelectedItem() {
        return selectedItem;
    }
}
