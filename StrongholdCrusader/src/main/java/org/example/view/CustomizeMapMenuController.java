package org.example.view;

import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import org.example.controller.CustomizeMapController;
import org.example.model.game.RockType;
import org.example.model.game.TreeType;
import org.example.model.game.envirnmont.BlockTexture;
import org.example.model.game.envirnmont.Coordinate;
import org.example.model.game.envirnmont.ExtendedBlock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


public class CustomizeMapMenuController {
    public ScrollPane mapBox;
    public Group mapPane;
    public VBox itemsBox;
    public ListView<HBox> itemList;
    public HBox selectListBox;
    private ExtendedBlock[][] mapView;
    private CustomizationMode customizationMode;

    public void prepareGame(Stage primaryStage) {
        // TODO: link with start game
//        CustomizeMapController.initializeMap();
        selectListBox.setPrefWidth(primaryStage.getWidth() / 5);
        initializeSelectListBox();
        itemList.setPrefWidth(primaryStage.getWidth() / 5);
        itemList.setPrefHeight(primaryStage.getHeight() - selectListBox.getHeight());
        mapBox.setPrefWidth(primaryStage.getWidth() - itemsBox.getWidth());
        initializeMapView();
    }

    private void initializeSelectListBox() {
        ToggleGroup selectListToggle = new ToggleGroup();
        ToggleButton texture = new ToggleButton("texture");
        texture.setToggleGroup(selectListToggle);
        ToggleButton tree = new ToggleButton("tree");
        tree.setToggleGroup(selectListToggle);
        ToggleButton rock = new ToggleButton("rock");
        rock.setToggleGroup(selectListToggle);
        selectListBox.getChildren().addAll(texture, tree, rock);
        selectListToggle.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) oldValue.setSelected(true);
            else {
                itemList.getItems().clear();
                switch (((ToggleButton) newValue).getText()) {
                    case "texture":
                        itemList.getItems().addAll(generateOptions(BlockTexture.getTextureListAssetsFolderPath(), BlockTexture.getItemNameFileNameMap()));
                        customizationMode = CustomizationMode.TEXTURE;
                        break;
                    case "tree":
                        itemList.getItems().addAll(generateOptions(TreeType.getTreeListAssetsFolderPath(), TreeType.getItemNameFileNameMap()));
                        customizationMode = CustomizationMode.TREE;
                        break;
                    case "rock":
                        itemList.getItems().addAll(generateOptions(RockType.getRockListAssetsFolderPath(), RockType.getItemNameFileNameMap()));
                        customizationMode = CustomizationMode.ROCK;
                }
                itemList.getSelectionModel().select(0);
            }
        });
        selectListToggle.selectToggle(texture);
    }

    private ArrayList<HBox> generateOptions(String assetsFolderName, LinkedHashMap<String, String> itemNameFileNameMap) {
        ArrayList<HBox> result = new ArrayList<>();
        HBox container;
        ImageView imageView;
        Label label;
        for (Map.Entry<String, String> entry : itemNameFileNameMap.entrySet()) {
            label = new Label(entry.getKey());
            label.getStyleClass().add("item-name");
            imageView = new ImageView(assetsFolderName + entry.getValue());
            imageView.setPreserveRatio(true);
            imageView.setFitWidth(50);
            container = new HBox(imageView, label);
            container.setId(entry.getKey());
            result.add(container);
        }
        return result;
    }

    private void initializeMapView() {
        mapBox.setPannable(true);
        mapBox.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        mapBox.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        mapBox.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            if (event.getButton() != MouseButton.SECONDARY) mapBox.setPannable(false);
        });
        mapBox.addEventHandler(MouseEvent.MOUSE_RELEASED, event -> {
            mapBox.setPannable(true);
        });
        // TODO: link with start game
//        org.example.model.game.envirnmont.Map map = CustomizeMapController.getMap();
        org.example.model.game.envirnmont.Map map = new org.example.model.game.envirnmont.Map(200);
        CustomizeMapController.setMap(map);
        HashMap<Polygon, Coordinate> polygonCoordinateMap = new HashMap<>();
        int size = map.getSize();
        double x0 = size * ExtendedBlock.getWidth() / 2;
        mapView = new ExtendedBlock[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                mapView[i][j] = new ExtendedBlock(map.getBlockByRowAndColumn(i, j), i, j, x0);
                Polygon blockView = mapView[i][j].getBlockView();
                blockView.setVisible(false);
                polygonCoordinateMap.put(blockView, new Coordinate(i, j));
                mapPane.getChildren().add(blockView);
                blockView.setOnMousePressed(event -> {
                    if (event.isPrimaryButtonDown()) {
                        Coordinate coordinate = polygonCoordinateMap.get(blockView);
                        if (customizationMode == CustomizationMode.TEXTURE)
                            mapView[coordinate.row][coordinate.column].setTexture(BlockTexture.getTypeByName(itemList.getSelectionModel().getSelectedItem().getId()), coordinate);
                        else {
                            Shape object = null;
                            if (customizationMode == CustomizationMode.TREE)
                                object = mapView[coordinate.row][coordinate.column].setTree(coordinate, TreeType.getTreeTypeByName(itemList.getSelectionModel().getSelectedItem().getId()));
                            else if (customizationMode == CustomizationMode.ROCK)
                                object = mapView[coordinate.row][coordinate.column].setRock(coordinate, RockType.getRockTypeByName(itemList.getSelectionModel().getSelectedItem().getId()));
                            if (object != null) {
                                object.setMouseTransparent(true);
                                mapPane.getChildren().add(object);
                                Rectangle frontTree;
                                for (int k = coordinate.row + 1, l = coordinate.column + 1; k < size && l < size; k++, l++) {
                                    if ((frontTree = mapView[k][l].getObject()) != null) {
                                        mapPane.getChildren().remove(frontTree);
                                        mapPane.getChildren().add(frontTree);
                                    }
                                }
                            }
                        }
                    }
                });
            }
        }

        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                mapView[i][j].getBlockView().setVisible(true);

        mapBox.setHvalue(0.5);
    }
}