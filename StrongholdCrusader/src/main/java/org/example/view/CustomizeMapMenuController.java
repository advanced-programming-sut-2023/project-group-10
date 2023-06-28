package org.example.view;

import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import org.example.controller.CustomizeMapController;
import org.example.model.Stronghold;
import org.example.model.game.Government;
import org.example.model.game.RockType;
import org.example.model.game.TreeType;
import org.example.model.game.envirnmont.BlockTexture;
import org.example.model.game.envirnmont.Coordinate;
import org.example.model.game.envirnmont.ExtendedBlock;
import org.example.model.game.units.MilitaryPerson;
import org.example.model.game.units.Unit;
import org.example.model.game.units.unitconstants.RoleName;
import org.example.view.enums.messages.CustomizeMapMessages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


public class CustomizeMapMenuController {
    public ScrollPane mapBox;
    public Group mapPane;
    public VBox actionsPane;
    public ListView<HBox> itemList;
    public HBox selectListBox;
    public Label errorMessage;
    public VBox controlsBox;
    private ExtendedBlock[][] mapView;
    private CustomizationMode customizationMode;

    public void prepareGame(Stage primaryStage) {
        CustomizeMapController.initializeMap();
        selectListBox.setPrefWidth(primaryStage.getWidth() / 5);
        initializeSelectListBox();
        itemList.setPrefWidth(primaryStage.getWidth() / 5);
        itemList.setPrefHeight(primaryStage.getHeight() - selectListBox.getHeight() - controlsBox.getHeight());
        mapBox.setPrefWidth(primaryStage.getWidth() - actionsPane.getWidth());
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
            label.getStyleClass().add("narrow-padding");
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
        CommonGFXActions.setMapScrollPaneProperties(mapBox);
        org.example.model.game.envirnmont.Map map = CustomizeMapController.getMap();
        int size = map.getSize();
        ExtendedBlock.setX0(size * ExtendedBlock.getWidth() / 2);
        mapView = new ExtendedBlock[size][size];
        Stronghold.getCurrentBattle().getBattleMap().setBlocksGraphics(mapView);
        Stronghold.setMapGroupGFX(mapPane);
        HashMap<Polygon, Coordinate> polygonCoordinateMap = Stronghold.getCurrentBattle().getBattleMap().getPolygonCoordinateMap();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                mapView[i][j] = new ExtendedBlock(map.getBlockByRowAndColumn(i, j), i, j);
                Polygon blockView = mapView[i][j].getBlockView();
                polygonCoordinateMap.put(blockView, new Coordinate(i, j));
                mapPane.getChildren().add(blockView);
                blockView.setOnMousePressed(event -> {
                    if (event.isSecondaryButtonDown()) {
                        Coordinate coordinate = polygonCoordinateMap.get(blockView);
                        CustomizeMapMessages result = null;
                        if (customizationMode == CustomizationMode.TEXTURE)
                            result = mapView[coordinate.row][coordinate.column].setTexture(BlockTexture.getTypeByName(itemList.getSelectionModel().getSelectedItem().getId()), coordinate);
                        else if (customizationMode == CustomizationMode.ERASER) {
                            mapPane.getChildren().remove(mapView[coordinate.row][coordinate.column].getObject());
                            result = mapView[coordinate.row][coordinate.column].erase(coordinate);
                        } else {
                            if (customizationMode == CustomizationMode.TREE)
                                result = mapView[coordinate.row][coordinate.column].setTree(coordinate, TreeType.getTreeTypeByName(itemList.getSelectionModel().getSelectedItem().getId()));
                            else if (customizationMode == CustomizationMode.ROCK)
                            {
                                result = mapView[coordinate.row][coordinate.column].setRock(coordinate, RockType.getRockTypeByName(itemList.getSelectionModel().getSelectedItem().getId()));
                            }
                            Shape object = mapView[coordinate.row][coordinate.column].getObject();
                            if (object != null && !mapPane.getChildren().contains(object)) {
                                object.setMouseTransparent(true);
                                mapPane.getChildren().add(object);
                                Rectangle frontTree;
                                for (int k = coordinate.row + 1, l = coordinate.column + 1; k < size && l < size; k++, l++)
                                    if ((frontTree = mapView[k][l].getObject()) != null) {
                                        mapPane.getChildren().remove(frontTree);
                                        mapPane.getChildren().add(frontTree);
                                    }
                            }
                        }
                        assert result != null;
                        updateMessage(result.getMessage());
                    }
                });
                if (map.getBlockByRowAndColumn(i, j).isKeep()) {
                    mapView[i][j].setKeep(i, j);
                    mapPane.getChildren().add(mapView[i][j].getObject());
                }
            }
        }
        for (Government government : Stronghold.getCurrentBattle().getGovernments()) {
            new MilitaryPerson(government.getKeep(), RoleName.LORD, government).addToGovernmentAndBlockAndView();
            for (int i = 0; i < 10; i++)
                new Unit(government.getKeep(), RoleName.PEASANT, government).addToGovernmentAndBlockAndView();
        }
        mapBox.setHvalue(0.5);
    }

    private void updateMessage(String message) {
        errorMessage.setText(message);
    }

    public void goToGame() throws Exception {
        new GameMenuGFX().start(SignupMenu.stage);
    }

    public void goToEraserMode() {
        customizationMode = CustomizationMode.ERASER;
    }

}