package org.example.view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;
import org.example.controller.MapMenuController;
import org.example.model.Stronghold;
import org.example.model.game.Item;
import org.example.model.game.RockType;
import org.example.model.game.TreeType;
import org.example.model.game.buildings.buildingconstants.BuildingCategory;
import org.example.model.game.buildings.buildingconstants.BuildingType;
import org.example.model.game.buildings.buildingconstants.BuildingTypeName;
import org.example.model.game.envirnmont.Block;
import org.example.model.game.envirnmont.BlockTexture;
import org.example.model.game.envirnmont.Coordinate;
import org.example.model.game.envirnmont.ExtendedBlock;
import org.example.model.utils.RandomGenerator;
import org.example.view.enums.messages.CustomizeMapMessages;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class GameMenuGFXController {
    public ScrollPane mapBox;
    public Rectangle faceImage;
    public Rectangle bookImage;
    public Rectangle edge;
    public VBox buildingContainer;
    private ExtendedBlock[][] mapView;
    public HBox controlBox;
    public ListView<VBox> buildingBox;
    public Pane miniMapBox;
    public Pane infoBox;
    private static Stage stage;
    private static Popup showingBlockInfoPopup;

    public void prepareGame(Stage stage) {
        GameMenuGFXController.stage = stage;
        System.out.println(stage.getHeight());
        controlBox.setPrefHeight(stage.getHeight() / 5);
        controlBox.setStyle("-fx-background-color: #171817");
        mapBox.setPrefHeight(stage.getHeight() - controlBox.getPrefHeight());
        initializeMapView();
        buildingBox.setPrefWidth(stage.getWidth()*4/6);
        miniMapBox.setPrefWidth(stage.getWidth() / 6);
        miniMapBox.setStyle("-fx-background-color: #6c6cb4");
        infoBox.setPrefWidth(stage.getWidth() / 6);
        infoBox.setStyle("-fx-background-color: #ee9a73");
        allBuildings();
    }

    @FXML
    public void initialize(){
        bookImage.setFill(new ImagePattern(new Image(Objects.requireNonNull(GameMenuGFXController.class.getResource("/images/backgrounds/book.jpeg")).toString())));
        edge.setFill(new ImagePattern(new Image(Objects.requireNonNull(GameMenuGFXController.class.getResource("/images/backgrounds/edge.png")).toString())));

        int popularity = Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getPopularity().get();
        faceImage.setFill(new ImagePattern(new Image(Objects.requireNonNull(GameMenuGFXController.class.getResource("/images/faces/face" + popularity / 10 + ".png")).toString())));
        Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getPopularity().addListener((observable, oldValue, newValue) -> {
            faceImage.setFill(new ImagePattern(new Image(Objects.requireNonNull(GameMenuGFXController.class.getResource("/images/faces/face" + newValue.intValue() / 10 + ".png")).toString())));
        });
        faceImage.setOnMouseClicked(mouseEvent -> popularityFactors());
    }

    private void initializeMapView() {
        CommonGFXActions.setMapScrollPaneProperties(mapBox);
        mapView=Stronghold.getCurrentMapGraphics();
        for (int i = 0; i < mapView.length; i++) {
            for (int j = 0; j < mapView.length; j++) {
                Polygon blockView = mapView[i][j].getBlockView();
                Coordinate coordinate = Stronghold.getPolygonCoordinateMap().get(blockView);
                blockView.setOnMousePressed(event -> {
                    if (event.isSecondaryButtonDown()) {
                        // TODO: add selection actions
                    }
                });
                blockView.setOnMouseEntered(mouseEvent -> {
                    if(mouseEvent.isPrimaryButtonDown() || mouseEvent.isSecondaryButtonDown()) return;
                    showingBlockInfoPopup=createBlockInfoPopup(coordinate);
                    showingBlockInfoPopup.setAnchorX(mouseEvent.getSceneX());
                    showingBlockInfoPopup.setAnchorY(mouseEvent.getSceneY());
                    showingBlockInfoPopup.show(stage);
                });
                blockView.setOnMouseExited(mouseEvent -> showingBlockInfoPopup.hide());
            }
        }
        mapBox.setContent(Stronghold.getMapGroupGFX());
        mapBox.setHvalue(0.5);
    }

    private void allBuildings(){
        for(BuildingTypeName buildingTypeName : BuildingTypeName.values()){
            VBox vBox = new VBox(2);
            vBox.setAlignment(Pos.CENTER);
            vBox.setBackground(new Background(RandomGenerator.setBackground("/images/backgrounds/lightBrown1.JPG")));
            vBox.setPadding(new Insets(0, 10, 0, 10));
            buildingBox.getItems().add(vBox);
            vBox.getChildren().add(new Circle(40, new ImagePattern(new Image(GameMenuGFXController.class.getResource("/images/buildings/" + buildingTypeName.toString().toLowerCase() + ".png").toString()))));
            vBox.getChildren().add(new Label(BuildingType.getBuildingTypeByName(buildingTypeName).getName().toString().replaceAll("_", " ")));
        }
    }

    public void castle() {
        setBuildingBox(BuildingCategory.CASTLE);
    }

    public void farm() {
        setBuildingBox(BuildingCategory.FARM);
    }

    public void foodProcessing() {
        setBuildingBox(BuildingCategory.FOOD_PROCESSING);
    }

    public void industry() {
        setBuildingBox(BuildingCategory.INDUSTRY);
    }

    public void town() {
        setBuildingBox(BuildingCategory.TOWN);
    }

    public void weapon() {
        setBuildingBox(BuildingCategory.WEAPON);
    }

    private void popularityFactors() {
        HBox mainPane = new HBox(30);
        mainPane.setPrefHeight(stage.getHeight()/5);
        mainPane.setPrefWidth(stage.getWidth() * 4/6);
        mainPane.setAlignment(Pos.CENTER);
        mainPane.setBackground(Background.fill(new ImagePattern(new Image(GameMenuGFXController.class.getResource("/images/backgrounds/menu.jpeg").toString()))));

        VBox first = new VBox(10);
        first.setAlignment(Pos.CENTER);
        Text popularity = new Text("Popularity");
        popularity.setFont(Font.font("Helvetica", FontPosture.ITALIC, 20));
        ImagePattern backImage = new ImagePattern(new Image(GameMenuGFXController.class.getResource("/images/icons/back.png").toString()));
        Rectangle back = new Rectangle(30, 30, backImage);
        first.getChildren().addAll(popularity, back);
        back.setOnMouseClicked(mouseEvent -> {
            controlBox.getChildren().remove(0);
            controlBox.getChildren().add(0, buildingContainer);
            allBuildings();
        });

        VBox second = faces();
        VBox third = foodAndTax();
        VBox forth = fearAndReligion();
        mainPane.getChildren().addAll(first, second, third, forth);

        controlBox.getChildren().remove(0);
        controlBox.getChildren().add(0, mainPane);
    }

    private static VBox faces(){
        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);
        Rectangle greenFace = new Rectangle(40, 60, new ImagePattern(new Image(GameMenuGFXController.class.getResource("/images/faces/greenFace.png").toString())));
        Rectangle redFace = new Rectangle(40, 60, new ImagePattern(new Image(GameMenuGFXController.class.getResource("/images/faces/redFace.png").toString())));
        redFace.setTranslateX(greenFace.getTranslateX() + 15);
        redFace.setTranslateY(greenFace.getTranslateY() - 30);
        vbox.getChildren().addAll(greenFace, redFace);
        return vbox;
    }

    private static VBox foodAndTax(){
        VBox vBox1 = new VBox(10);
        vBox1.setAlignment(Pos.CENTER);

        HBox hBox1 = new HBox(5);
        hBox1.setAlignment(Pos.CENTER_LEFT);
        Text foodRate = new Text(Integer.toString(Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getFoodRate()));
        Rectangle foodFace = new Rectangle(20, 30);
        Text food = new Text("Food");
        food.setFont(Font.font("Helvetica", 10));
        hBox1.getChildren().addAll(foodRate, foodFace, food);

        HBox hBox2 = new HBox(5);
        hBox2.setAlignment(Pos.CENTER_LEFT);
        Text taxRate = new Text(Integer.toString(Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getFoodRate()));
        Rectangle taxFace = new Rectangle(20, 30);
        Text tax = new Text("Tax");
        tax.setFont(Font.font("Helvetica", 10));
        hBox2.getChildren().addAll(taxRate, taxFace, tax);

        vBox1.getChildren().addAll(hBox1, hBox2);
        return vBox1;
    }

    private static VBox fearAndReligion(){
        VBox vBox2 = new VBox(10);
        vBox2.setAlignment(Pos.CENTER);

        HBox fearContainer = new HBox(5);
        fearContainer.setAlignment(Pos.CENTER_LEFT);
        Text fearRate = new Text(Integer.toString(Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getFearRate()));
        Rectangle fearFace = new Rectangle(20, 30);
        Text fear = new Text("Fear Factor");
        fear.setFont(Font.font("Helvetica", 10));
        fearContainer.getChildren().addAll(fearRate, fearFace, fear);

        HBox religionContainer = new HBox(5);
        religionContainer.setAlignment(Pos.CENTER_LEFT);
        Text religionRate = new Text(Integer.toString(Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getReligionCount()));
        Rectangle religionFace = new Rectangle(20, 30);
        Text religion = new Text("Religion");
        religion.setFont(Font.font("Helvetica", 10));
        religionContainer.getChildren().addAll(religionRate, religionFace, religion);

        vBox2.getChildren().addAll(fearContainer, religionContainer);
        return vBox2;
    }

    private void setBuildingBox(BuildingCategory category){
        buildingBox.getItems().clear();
        ArrayList<BuildingType> buildings = new ArrayList<>();
        for(BuildingTypeName buildingTypeName : BuildingTypeName.values()){
            if(BuildingType.getBuildingTypeByName(buildingTypeName) == null) continue; //TODO delete this line
            if(BuildingType.getBuildingTypeByName(buildingTypeName).getCategory().equals(category))
                buildings.add(BuildingType.getBuildingTypeByName(buildingTypeName));
        }

        for(BuildingType buildingType : buildings){
            VBox vBox = new VBox(2); //TODO set spacing
            vBox.setAlignment(Pos.CENTER);
            vBox.setBackground(new Background(RandomGenerator.setBackground("/images/backgrounds/lightBrown1.JPG")));
            vBox.setPadding(new Insets(0, 10, 0, 10));
            buildingBox.getItems().add(vBox);
            vBox.getChildren().add(new Circle(40, new ImagePattern(new Image(GameMenuGFXController.class.getResource("/images/buildings/" + buildingType.getName() + ".png").toString()))));
            vBox.getChildren().add(new Label(buildingType.getName().toString().replaceAll("_", " ")));

            Popup popup = createPopup(buildingType);
            vBox.hoverProperty().addListener((Observable, oldValue, newValue) -> {
                if(newValue) popup.show(stage);
                else popup.hide();
            });
        }
    }

    private static Popup createPopup(BuildingType buildingType){
        Popup popup = new Popup();
        Label label = new Label();
        String string = "hitpoint: " + buildingType.getMaxHitPoint();
        string += "\ncost: " + buildingType.getBuildingCost();
        if(buildingType.getResourcesNeeded() != null) {
            for (Map.Entry<Item, Integer> entry : buildingType.getResourcesNeeded().entrySet()) {
                string += ("\n" + entry.getKey().getName() + " needed: " + entry.getValue());
            }
        }

        string += ("\nemployee count: " + buildingType.getEmployeeCount());
        popup.getContent().add(label);
        label.setText(string);
        popup.setAnchorX(500);
        popup.setAnchorY(300);
        return popup;
    }

    private Popup createBlockInfoPopup(Coordinate blockPosition) {
        Popup popup=new Popup();
        Label label=new Label(MapMenuController.showDetailsExtended(blockPosition));
        label.setStyle("-fx-text-fill: rgba(211,234,216,0.78)");
        VBox container=new VBox(label);
        container.setBackground(Background.fill(new Color(0, 0, 0, 1)));
        container.setMouseTransparent(true);
        container.setPadding(new Insets(5));
        container.setAlignment(Pos.CENTER);
        popup.getContent().add(container);
        return popup;
    }
}