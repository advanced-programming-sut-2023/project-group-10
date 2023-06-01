package org.example.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.example.model.game.Item;

import java.net.URL;

public class ItemDetailsMenuGFX extends Application {



    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane itemDetails = FXMLLoader.load(
                new URL(ShopMenuGFX.class.getResource("/view/itemDetails.fxml").toExternalForm()));

        Scene scene = new Scene(itemDetails);
        primaryStage.setScene(scene);
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
