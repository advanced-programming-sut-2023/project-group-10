package org.example.view;

import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.model.game.Rock;
import org.example.model.game.TreeType;
import org.example.model.game.envirnmont.BlockTexture;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;


public class CustomizeMapMenuController {
    public Pane mapBox;
    public VBox itemsBox;
    public ListView<HBox> texturesBox;
    public ListView<HBox> treeTypesBox;
    public ListView<HBox> rockDirectionsBox;

    public void prepareGame(Stage primaryStage) {
        texturesBox.setPrefHeight(primaryStage.getHeight() /2);
        texturesBox.getItems().addAll(generateOptions(BlockTexture.getTextureListAssetsFolderPath(), BlockTexture.getItemNameFileNameMap()));
        treeTypesBox.setPrefHeight(primaryStage.getHeight() / 4);
        treeTypesBox.getItems().addAll(generateOptions(TreeType.getTreeListAssetsFolderPath(), TreeType.getItemNameFileNameMap()));
        rockDirectionsBox.setPrefHeight(primaryStage.getHeight() / 4);
        rockDirectionsBox.getItems().addAll(generateOptions(Rock.getRockListAssetsFolderPath(), Rock.getItemNameFileNameMap()));
        mapBox.setPrefWidth(primaryStage.getWidth()-itemsBox.getWidth());
    }

    private ArrayList<HBox> generateOptions(String assetsFolderName, LinkedHashMap<String, String> itemNameFileNameMap) {
        ArrayList<HBox> result = new ArrayList<>();
        ImageView imageView;
        Label label;
        for (Map.Entry<String, String> entry : itemNameFileNameMap.entrySet()) {
            label = new Label(entry.getKey());
            label.getStyleClass().add("item-name");
            imageView = new ImageView(assetsFolderName + entry.getValue());
            imageView.setPreserveRatio(true);
            imageView.setFitWidth(50);
            result.add(new HBox(imageView, label));
        }
        return result;
    }
}
