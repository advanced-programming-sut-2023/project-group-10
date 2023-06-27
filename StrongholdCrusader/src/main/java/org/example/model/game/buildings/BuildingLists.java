package org.example.model.game.buildings;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Popup;
import org.example.model.game.Item;
import org.example.model.game.buildings.buildingconstants.BuildingCategory;
import org.example.model.game.buildings.buildingconstants.BuildingType;
import org.example.model.game.buildings.buildingconstants.BuildingTypeName;
import org.example.model.utils.RandomGenerator;
import org.example.view.GameMenuGFXController;

import java.util.ArrayList;
import java.util.Map;

public class BuildingLists {
    public static final ListView<VBox> allBuildings = allBuildingsCreator();
    public static final ListView<VBox> castleBuildings = setBuildingBox(BuildingCategory.CASTLE);
    public static final ListView<VBox> farmBuildings = setBuildingBox(BuildingCategory.FARM);
    public static final ListView<VBox> weaponBuildings = setBuildingBox(BuildingCategory.WEAPON);
    public static final ListView<VBox> townBuildings = setBuildingBox(BuildingCategory.TOWN);
    public static final ListView<VBox> industryBuildings = setBuildingBox(BuildingCategory.INDUSTRY);
    public static final ListView<VBox> foodProcessingBuildings = setBuildingBox(BuildingCategory.FOOD_PROCESSING);
    public static final ListView<VBox> unknown = setBuildingBox(BuildingCategory.UNKNOWN);
    private static BuildingTypeName selectedBuilding;

    public static BuildingTypeName getSelectedBuilding() {
        return selectedBuilding;
    }

    private static ListView<VBox> allBuildingsCreator() {
        ListView<VBox> listView = new ListView<>();
        for (BuildingTypeName buildingTypeName : BuildingTypeName.values()) {
            VBox vBox = new VBox(2);
            vBox.setAlignment(Pos.CENTER);
            vBox.setBackground(new Background(RandomGenerator.setBackground("/images/backgrounds/lightBrown1.JPG")));
            vBox.setPadding(new Insets(0, 10, 0, 10));
            listView.getItems().add(vBox);
            Circle circle = new Circle(40, new ImagePattern(new Image(GameMenuGFXController.class.getResource("/images/buildings/" + buildingTypeName.toString().toLowerCase() + ".png").toString())));
            vBox.getChildren().add(circle);
            vBox.getChildren().add(new Label(BuildingType.getBuildingTypeByName(buildingTypeName).getName().toString().replaceAll("_", " ")));
            Popup popup = createPopup(BuildingType.getBuildingTypeByName(buildingTypeName));
            circle.setOnMouseEntered(mouseEvent -> {
                popup.setAnchorX(mouseEvent.getSceneX() + 2);
                popup.setAnchorY(mouseEvent.getSceneY() + 2);
                popup.show(GameMenuGFXController.stage);
            });
            circle.setOnDragDetected(mouseEvent -> {
                Dragboard db = circle.startDragAndDrop(TransferMode.ANY);
                ClipboardContent content = new ClipboardContent();
                content.putString("circle drag detected");
                db.setContent(content);
                ImagePattern imagePattern = (ImagePattern) circle.getFill();
                Image image = imagePattern.getImage();
                db.setDragView(new Image(image.getUrl(), 40, 40, false, false));
                selectedBuilding = buildingTypeName;
            });
            circle.setOnMouseDragged(mouseEvent -> mouseEvent.setDragDetect(true));
            circle.setOnMouseExited(mouseEvent -> popup.hide());
        }
        return listView;
    }

    private static ListView<VBox> setBuildingBox(BuildingCategory category) {
        ListView<VBox> listView = new ListView<>();
        ArrayList<BuildingType> buildings = new ArrayList<>();
        for (BuildingTypeName buildingTypeName : BuildingTypeName.values()) {
            if (BuildingType.getBuildingTypeByName(buildingTypeName).getCategory().equals(category))
                buildings.add(BuildingType.getBuildingTypeByName(buildingTypeName));
        }

        for (BuildingType buildingType : buildings) {
            VBox vBox = new VBox(2); //TODO set spacing
            vBox.setAlignment(Pos.CENTER);
            vBox.setBackground(new Background(RandomGenerator.setBackground("/images/backgrounds/lightBrown1.JPG")));
            vBox.setPadding(new Insets(0, 10, 0, 10));
            listView.getItems().add(vBox);
            Circle circle = new Circle(40, new ImagePattern(new Image(GameMenuGFXController.class.getResource("/images/buildings/" + buildingType.getName() + ".png").toString())));
            vBox.getChildren().add(circle);
            vBox.getChildren().add(new Label(buildingType.getName().toString().replaceAll("_", " ")));

            Popup popup = createPopup(buildingType);
            circle.setOnMouseEntered(mouseEvent -> {
                popup.setAnchorX(mouseEvent.getSceneX() + 2);
                popup.setAnchorY(mouseEvent.getSceneY() + 2);
                popup.show(GameMenuGFXController.stage);
            });
            circle.setOnMouseExited(mouseEvent -> popup.hide());
            circle.setOnDragDetected(mouseEvent -> {
                Dragboard db = circle.startDragAndDrop(TransferMode.ANY);
                ClipboardContent content = new ClipboardContent();
                content.putString("circle drag detected");
                db.setContent(content);
                ImagePattern imagePattern = (ImagePattern) circle.getFill();
                Image image = imagePattern.getImage();
                db.setDragView(new Image(image.getUrl(), 40, 40, false, false));
                selectedBuilding = buildingType.getName();
            });
            circle.setOnMouseDragged(mouseEvent -> mouseEvent.setDragDetect(true));
        }
        return listView;
    }

    private static Popup createPopup(BuildingType buildingType) {
        Popup popup = new Popup();
        Label label = new Label();
        String string = "hitpoint: " + buildingType.getMaxHitPoint();
        string += "\ncost: " + buildingType.getBuildingCost();
        if (buildingType.getResourcesNeeded() != null) {
            for (Map.Entry<Item, Integer> entry : buildingType.getResourcesNeeded().entrySet()) {
                string += ("\n" + entry.getKey().getName() + " needed: " + entry.getValue());
            }
        }
        string += ("\nemployee count: " + buildingType.getEmployeeCount());
        label.setText(string);
        VBox vBox = new VBox(label);
        vBox.setPadding(new Insets(5));
        label.setStyle("-fx-text-fill: rgba(211,234,216,0.78)");
        popup.getContent().add(vBox);
        vBox.setBackground(Background.fill(Color.BLACK));
        return popup;
    }


}
