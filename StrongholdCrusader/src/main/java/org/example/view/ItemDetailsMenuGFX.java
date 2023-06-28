package org.example.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.example.model.game.Item;

import java.net.URL;

public class ItemDetailsMenuGFX extends Application {
    public static Item selectedItem;
    public static Stage stage;
    public GameMenuGFXController gameMenuGFXController;

    @Override
    public void start(Stage primaryStage) throws Exception {
        URL itemDetailsFXML = ItemDetailsMenuGFX.class.getResource("/view/itemDetails.fxml");
        FXMLLoader loader = new FXMLLoader(itemDetailsFXML);
        Pane itemDetails= loader.load();
        ((ItemDetailMenuController) loader.getController()).setGameMenuGFXController(this.gameMenuGFXController);
        Image image = new Image(ItemDetailsMenuGFX.class.getResource("/images/backgrounds/brownPaper.jpeg").toExternalForm());
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(100, 100, true, true, true, true));
        itemDetails.setBackground(new Background(backgroundImage));
        stage=primaryStage;
        primaryStage.getScene().setRoot(itemDetails);
        primaryStage.centerOnScreen();
    }

    public void setSelectedItem(Item selectedItem) {
        ItemDetailsMenuGFX.selectedItem = selectedItem;
    }

    public static Item getSelectedItem() {
        return selectedItem;
    }

    public void setGameMenuGFXController(GameMenuGFXController gameMenuGFXController) {
        this.gameMenuGFXController = gameMenuGFXController;
    }
}
