package org.example.view;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.controller.BuildingMenuController;
import org.example.controller.GameMenuController;
import org.example.controller.MapMenuController;
import org.example.controller.UnitMenuController;
import org.example.model.Stronghold;
import org.example.model.User;
import org.example.model.game.buildings.Building;
import org.example.model.game.buildings.BuildingLists;
import org.example.model.game.buildings.ItemProducingBuilding;
import org.example.model.game.buildings.buildingconstants.BuildingTypeName;
import org.example.model.game.buildings.buildingconstants.ItemProducingBuildingType;
import org.example.model.game.envirnmont.Coordinate;
import org.example.model.game.envirnmont.ExtendedBlock;
import org.example.model.game.units.MilitaryUnit;
import org.example.model.game.units.unitconstants.MilitaryPersonRole;
import org.example.model.game.units.unitconstants.MilitaryUnitRole;
import org.example.model.game.units.unitconstants.Role;
import org.example.model.game.units.unitconstants.RoleName;
import org.example.view.enums.messages.BuildingMenuMessages;
import org.example.view.enums.messages.GameMenuMessages;
import org.example.view.enums.messages.UnitMenuMessages;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class GameMenuGFXController {
    public ScrollPane mapBox;
    public Pane controlButtonsBar;
    public VBox copiedBuilding;
    public Label unitMessageLabel;
    private VBox buttons = new VBox();
    private Group scrollPaneContent;
    public BorderPane turnPane;
    public Rectangle currentPlayerAvatar;
    public Label currentPlayerName;
    public Button nextPlayerButton;
    public VBox selectedTroopsInfoPane;
    public Rectangle faceImage;
    public Pane bookImage = new Pane();
    public Rectangle edge;
    public VBox buildingContainer;
    public HBox controlBox;
    public ListView<VBox> buildingBox;
    public Pane miniMapBox;
    public Pane infoBox;
    public static Stage stage;
    private Coordinate selectionStartCoordinate;
    private LinkedList<ExtendedBlock> selectedBlocks;
    private HashMap<RoleName, Integer> selectedRoleCountMap;
    private LinkedList<Label> selectedUnitsLabels;
    private Button unselectButton;
    private Coordinate selectedBuildingCoordinate;
    private ExtendedBlock[][] mapView;
    private boolean deleteMode = false;

    public void prepareGame(Stage stage) {
        GameMenuGFXController.stage = stage;
        scrollPaneContent = Stronghold.getMapGroupGFX();
        System.out.println(stage.getHeight());
        controlBox.setPrefHeight(200);

        mapBox.setPrefWidth(stage.getWidth() * 7 / 8);
        mapBox.setPrefHeight(stage.getHeight() - controlBox.getPrefHeight());
        initializeMapView();

        miniMapBox.setPrefWidth(200);
        miniMapBox.setPrefHeight(controlBox.getPrefHeight());
        miniMapBox.setStyle("-fx-background-color: DARKKHAKI");
        initializeMiniMap();

        controlButtonsBar.setPrefWidth(50);
        controlButtonsBar.setPrefHeight(controlBox.getPrefHeight());
        initializeControlButtons();

        turnPane.setPrefWidth(stage.getWidth() / 8);
        turnPane.setPrefHeight(mapBox.getPrefHeight());
        currentPlayerAvatar.setWidth(turnPane.getPrefWidth() / 2);
        currentPlayerAvatar.setHeight(turnPane.getPrefWidth() / 2);
        initializeTurnPane();

        infoBox.setPrefWidth(turnPane.getPrefWidth());
        infoBox.setStyle("-fx-background-color: #ee9a73");

        buildingBox.setPrefWidth(stage.getWidth() - controlButtonsBar.getPrefWidth() - miniMapBox.getPrefWidth() - infoBox.getPrefWidth());
        buildingBox.getItems().addAll(BuildingLists.allBuildings.getItems());

        unselectButton = new Button("unselect");
        unselectButton.setOnMouseClicked(this::unselectAllHandleMethod);

        selectedBlocks = new LinkedList<>();
        selectedRoleCountMap = new HashMap<>();
        selectedUnitsLabels = new LinkedList<>();
    }

    @FXML
    public void initialize() {
        Rectangle book = new Rectangle(90, 77);
        book.setFill(new ImagePattern(new Image(Objects.requireNonNull(GameMenuGFXController.class.getResource("/images/backgrounds/book.jpeg")).toString())));
        bookImage.getChildren().add(book);
        edge.setFill(new ImagePattern(new Image(Objects.requireNonNull(GameMenuGFXController.class.getResource("/images/backgrounds/edge.png")).toString())));
        scribeDetails();
        int popularity = Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getPopularity().get();
        faceImage.setFill(new ImagePattern(new Image(Objects.requireNonNull(GameMenuGFXController.class.getResource
                ("/images/faces/face" + popularity / 10 + ".png")).toString())));
        Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getPopularity().addListener((observable, oldValue, newValue) -> {
            faceImage.setFill(new ImagePattern(new Image(Objects.requireNonNull(GameMenuGFXController.class.getResource
                    ("/images/faces/face" + newValue.intValue() / 10 + ".png")).toString())));
            scribeDetails();
        });
        faceImage.setOnMouseClicked(mouseEvent -> popularityFactors());
        mapBox.setOnKeyPressed(keyEvent -> {
            if (keyEvent.isControlDown() && keyEvent.getCode().equals(KeyCode.C) && selectedBuildingCoordinate != null) {
                BuildingTypeName selectedBuilding = Stronghold.getCurrentBattle().getBattleMap().getBlockByRowAndColumn(selectedBuildingCoordinate).getBuilding().getBuildingType().getName();
                Circle circle = new Circle(40, new ImagePattern(new Image(Objects.requireNonNull(GameMenuGFXController.
                        class.getResource("/images/buildings/" + selectedBuilding.name() + ".png")).toString())));
                Label label = new Label(selectedBuilding.name().replaceAll("_", " "));
                if (copiedBuilding.getChildren().size() == 3) {
                    copiedBuilding.getChildren().remove(2);
                    copiedBuilding.getChildren().remove(1);
                }
                StringSelection stringSelection = new StringSelection(selectedBuilding.name());
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(stringSelection, null);
                copiedBuilding.getChildren().add(circle);
                copiedBuilding.getChildren().add(label);
                copiedBuilding.setVisible(true);
            } else if (keyEvent.isControlDown() && keyEvent.getCode().equals(KeyCode.V) && copiedBuilding.getChildren().size() == 3 && selectedBlocks.size() == 1) {
                Coordinate coordinate = Stronghold.getCurrentBattle().getBattleMap().getPolygonCoordinateMap().get(selectedBlocks.get(0).getBlockView());
                ExtendedBlock extendedBlock = mapView[coordinate.row][coordinate.column];
                GameMenuMessages message = extendedBlock.setBuilding(coordinate, BuildingLists.getSelectedBuilding());
                setDropBuildingMessage(message);
                if (extendedBlock.getBlock().getBuilding() != null)
                    setBuilding(extendedBlock, coordinate);
            } else if (keyEvent.isControlDown() && keyEvent.getCode().equals(KeyCode.R) && selectedBuildingCoordinate != null) {
                BuildingMenuController.setSelectedBuilding(Stronghold.getCurrentBattle().getBattleMap().getBlockByRowAndColumn(selectedBuildingCoordinate).getBuilding());
                BuildingMenuMessages message = BuildingMenuController.repair();
                setRepairMessage(message);
            } else if (keyEvent.isControlDown() && keyEvent.getCode().equals(KeyCode.U) && selectedBuildingCoordinate != null) {
                BuildingMenuController.setSelectedBuilding(Stronghold.getCurrentBattle().getBattleMap().getBlockByRowAndColumn(selectedBuildingCoordinate).getBuilding());
                BuildingTypeName name = BuildingMenuController.selectedBuilding.getBuildingType().getName();
                if (name.equals(BuildingTypeName.MERCENARY_POST) || name.equals(BuildingTypeName.TUNNELER_GUILD) || name.equals(BuildingTypeName.ENGINEER_GUILD) ||
                        name.equals(BuildingTypeName.BARRACKS) || name.equals(BuildingTypeName.CATHEDRAL))
                    createUnitPopup();
                else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Can Not Create Unit");
                    alert.setContentText("You can not create unit in this building");
                    alert.show();
                }
            } else if (keyEvent.isControlDown() && keyEvent.getCode().equals(KeyCode.O) && selectedBuildingCoordinate != null) {
                Building building = Stronghold.getCurrentBattle().getBattleMap().getBlockByRowAndColumn(selectedBuildingCoordinate).getBuilding();
                if (building.getBuildingType().getName().equals(BuildingTypeName.MARKET)) {
                    try {
                        ShopMenuGFX shopMenuGFX = new ShopMenuGFX();
                        FXMLLoader loader = new FXMLLoader(GameMenuGFX.class.getResource("/view/gameMenu.fxml"));
                        shopMenuGFX.setGameController(this);
                        shopMenuGFX.start(new Stage());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        mapBox.addEventFilter(ScrollEvent.ANY, scrollEvent -> {
            zoom(!(scrollEvent.getDeltaY() < 0));
            scrollEvent.consume();
        });
    }

    private void setDropBuildingMessage(GameMenuMessages message) {
        if (!message.equals(GameMenuMessages.SUCCESSFUL_DROP)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Drop Building Unsuccessful");
            alert.setContentText(message.name().replaceAll("_", " "));
            alert.show();
        }
    }

    private void setRepairMessage(BuildingMenuMessages message) {
        if (message.equals(BuildingMenuMessages.REPAIR_SUCCESSFUL)) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Repair Successful");
            alert.setContentText("Selected Building was repaired successfully");
            alert.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Repair Unsuccessful");
            alert.setContentText(message.name().replaceAll("_", " "));
            alert.show();
        }
    }

    private void createUnitPopup() {
        Popup popup = new Popup();
        VBox vBox = new VBox(5);
        vBox.setAlignment(Pos.CENTER);

        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.setPromptText("role name");
        for (RoleName roleName : RoleName.values()) {
            if (MilitaryPersonRole.getAllMilitaryRoles().contains(Role.getRoleByName(roleName)))
                comboBox.getItems().add(roleName.name().replaceAll("_", " ").toLowerCase());
        }

        Slider slider = new Slider(1, 10, 1);
        slider.setBlockIncrement(1);
        Text text = new Text("unit count: 1");
        text.setFill(Color.WHITE);
        slider.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            text.setText("unit count: " + newValue.intValue());
        });
        Button button = new Button("create");
        button.setOnMouseClicked(mouseEvent -> {
            popup.hide();
            BuildingMenuMessages message = BuildingMenuController.createUnit(comboBox.getValue(), (int) slider.getValue());
            if (!message.equals(BuildingMenuMessages.CREATE_UNIT_SUCCESSFUL)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Create Unit Failed");
                alert.setContentText(message.name().replaceAll("_", " "));
                alert.show();
            } else scribeDetails();
        });

        vBox.getChildren().addAll(comboBox, slider, text, button);
        vBox.setBackground(Background.fill(Color.BLACK));
        vBox.setPadding(new Insets(15));
        popup.getContent().add(vBox);
        popup.show(stage);
    }

    private void initializeControlButtons() {
        Stop[] stops = new Stop[]{new Stop(0, Color.BLACK), new Stop(1, Color.RED)};
        LinearGradient lg1 = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, stops);
        Rectangle back = new Rectangle(controlButtonsBar.getPrefWidth(), controlButtonsBar.getPrefHeight());
        back.setFill(lg1);
        controlButtonsBar.getChildren().clear();
        controlButtonsBar.getChildren().add(back);
        buttons.setPrefHeight(controlButtonsBar.getPrefHeight());
        buttons.setPrefWidth(controlButtonsBar.getPrefWidth());

        double size = controlButtonsBar.getPrefHeight() / 8;
        Rectangle options = new Rectangle();
        options.setFill(new ImagePattern(new Image(Objects.requireNonNull(GameMenuGFXController.class.getResource
                ("/images/controlbuttons/options0.png")).toString())));
        options.setWidth(size);
        options.setHeight(size);
        options.setOnMouseClicked(this::selectOptionsFromMenuBar);

        Rectangle briefing = new Rectangle();
        briefing.setFill(new ImagePattern(new Image(Objects.requireNonNull(GameMenuGFXController.class.getResource
                ("/images/controlbuttons/briefing0.png")).toString())));
        briefing.setWidth(size);
        briefing.setHeight(size);
        briefing.setOnMouseClicked(this::selectBriefingFromMenuBar);

        Rectangle delete = new Rectangle();
        delete.setFill(new ImagePattern(new Image(Objects.requireNonNull(GameMenuGFXController.class.getResource
                ("/images/controlbuttons/delete0.png")).toString())));
        delete.setWidth(size);
        delete.setHeight(size);
        delete.setOnMouseClicked(this::selectDeleteFromMenuBar);

        Rectangle undo = new Rectangle();
        undo.setFill(new ImagePattern(new Image(Objects.requireNonNull(GameMenuGFXController.class.getResource
                ("/images/controlbuttons/undo0.png")).toString())));
        undo.setWidth(size);
        undo.setHeight(size);
        undo.setOnMouseClicked(this::selectUndoFromMenuBar);

        buttons.setSpacing((controlButtonsBar.getPrefHeight() - size * 4) / 6);
        buttons.setAlignment(Pos.CENTER);
        buttons.getChildren().addAll(options, briefing, delete, undo);

        controlButtonsBar.getChildren().add(buttons);

    }

    private void selectUndoFromMenuBar(MouseEvent mouseEvent) {
        selectButtonFromBar(3);
    }

    private void selectDeleteFromMenuBar(MouseEvent mouseEvent) {
        selectButtonFromBar(2);
        deleteMode = true;

    }

    private void selectBriefingFromMenuBar(MouseEvent mouseEvent) {
        selectButtonFromBar(1);
        BriefingGFX briefingGFX = new BriefingGFX();
        briefingGFX.setGameMenuGFXController(this);
        try {
            briefingGFX.start(new Stage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        unselectAll();
    }

    private void selectOptionsFromMenuBar(MouseEvent mouseEvent) {
        selectButtonFromBar(0);
        OptionsMenuGFX optionsMenuGFX = new OptionsMenuGFX();
        optionsMenuGFX.setGameMenuGFXController(this);
        try {
            optionsMenuGFX.start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        unselectAll();
    }

    private void selectButtonFromBar(int index) {
        ImagePattern[] unselected = {
                (new ImagePattern(new Image(Objects.requireNonNull(GameMenuGFXController.class.getResource
                        ("/images/controlbuttons/options0.png")).toString()))),
                (new ImagePattern(new Image(Objects.requireNonNull(GameMenuGFXController.class.getResource
                        ("/images/controlbuttons/briefing0.png")).toString()))),
                (new ImagePattern(new Image(Objects.requireNonNull(GameMenuGFXController.class.getResource
                        ("/images/controlbuttons/delete0.png")).toString()))),
                (new ImagePattern(new Image(Objects.requireNonNull(GameMenuGFXController.class.getResource
                        ("/images/controlbuttons/undo0.png")).toString()))),
        };
        ImagePattern[] selected = {
                (new ImagePattern(new Image(Objects.requireNonNull(GameMenuGFXController.class.getResource
                        ("/images/controlbuttons/options1.png")).toString()))),
                (new ImagePattern(new Image(Objects.requireNonNull(GameMenuGFXController.class.getResource
                        ("/images/controlbuttons/briefing1.png")).toString()))),
                (new ImagePattern(new Image(Objects.requireNonNull(GameMenuGFXController.class.getResource
                        ("/images/controlbuttons/delete1.png")).toString()))),
                (new ImagePattern(new Image(Objects.requireNonNull(GameMenuGFXController.class.getResource
                        ("/images/controlbuttons/undo1.png")).toString()))),
        };
        for (int i = 0; i < buttons.getChildren().size(); i++) {
            ((Rectangle) buttons.getChildren().get(i)).setFill(unselected[i]);

        }
        if (index == -1)
            return;
        ((Rectangle) buttons.getChildren().get(index)).setFill(selected[index]);

    }

    private void unselectAll() {
        selectButtonFromBar(-1);
    }


    public void scribeDetails() {
        VBox fields = new VBox();
        fields.setSpacing(3);
        Text popularity = new Text("   " + Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getPopularity().getValue());
        popularity.setFont(new Font("PT Mono", 14));
        popularity.setTextAlignment(TextAlignment.CENTER);
        popularity.setRotate(15);
        Text golds = new Text("  " + ((int)Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getGold()));
        golds.setFont(new Font("PT Mono", 11));
        golds.setTextAlignment(TextAlignment.CENTER);
        golds.setRotate(15);
        Text population = new Text((Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getPeasants().size())+
                "/"+Stronghold.getCurrentBattle().getGovernmentAboutToPlay().maxPossiblePeasants());
        population.setFont(new Font("PT Mono", 10));
        population.setTextAlignment(TextAlignment.CENTER);
        population.setRotate(15);
        fields.getChildren().addAll(popularity, golds, population);
        System.out.println(popularity.getText() + "  " + population.getText() + " " + golds.getText());
        if (bookImage.getChildren().size() > 1)
            bookImage.getChildren().remove(bookImage.getChildren().size() - 1);
        bookImage.getChildren().add(fields);

    }

    private void initializeMapView() {
        CommonGFXActions.setMapScrollPaneProperties(mapBox);
        org.example.model.game.envirnmont.Map gameMap = Stronghold.getCurrentBattle().getBattleMap();
        mapView = gameMap.getBlocksGraphics();
        for (int i = 0; i < mapView.length; i++) {
            for (int j = 0; j < mapView.length; j++) {
                Polygon blockView = mapView[i][j].getBlockView();
                Coordinate coordinate = gameMap.getPolygonCoordinateMap().get(blockView);
                blockView.setOnMousePressed(mouseEvent -> {
                    if (mouseEvent.isSecondaryButtonDown()) selectionStartCoordinate = coordinate;
                });
                blockView.setOnMouseEntered(mouseEvent -> {
                    if (mouseEvent.isPrimaryButtonDown() || mouseEvent.isSecondaryButtonDown()) return;
                    if (selectionStartCoordinate == null)
                        Tooltip.install(blockView, new Tooltip(MapMenuController.showDetailsExtended(coordinate)));
                    else {
                        Coordinate coordinate1 = new Coordinate(Math.min(selectionStartCoordinate.row, coordinate.row), Math.min(selectionStartCoordinate.column, coordinate.column));
                        Coordinate coordinate2 = new Coordinate(Math.max(selectionStartCoordinate.row, coordinate.row), Math.max(selectionStartCoordinate.column, coordinate.column));
                        for (int k = coordinate1.row; k <= coordinate2.row; k++) {
                            for (int l = coordinate1.column; l <= coordinate2.column; l++)
                                switchSelectionState(new Coordinate(k, l));
                        }
                        selectionStartCoordinate = null;
                    }
                });
                blockView.setOnMouseClicked(mouseEvent -> {
                    if (selectionStartCoordinate != null) {
                        switchSelectionState(selectionStartCoordinate);
                        if (deleteMode) {
                            delete(selectionStartCoordinate);
                            deleteMode = false;
                            unselectAll();
                        }
                        selectionStartCoordinate = null;
                    }

                });
                blockView.setOnDragOver(dragEvent -> {
                    if (dragEvent.getGestureSource() != blockView && dragEvent.getDragboard().hasString())
                        dragEvent.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                    dragEvent.consume();
                });
                blockView.setOnDragDropped(dragEvent -> {
                    Dragboard db = dragEvent.getDragboard();
                    ;
                    if (db.hasString()) {
                        ExtendedBlock extendedBlock = mapView[coordinate.row][coordinate.column];
                        GameMenuMessages message = extendedBlock.setBuilding(coordinate, BuildingLists.getSelectedBuilding());
                        setDropBuildingMessage(message);
                        if (extendedBlock.getBlock().getBuilding() != null)
                            setBuilding(extendedBlock, coordinate);
                    } else dragEvent.setDropCompleted(false);
                    dragEvent.consume();
                });
            }
        }

        Group zoomHandlerGroup = new Group(scrollPaneContent);
        mapBox.setContent(zoomHandlerGroup);
        mapBox.setHvalue(0.5);
    }

    private void setBuilding(ExtendedBlock extendedBlock, Coordinate coordinate) {
        AtomicReference<Popup> popup = new AtomicReference<>();
        extendedBlock.getObject().setOnMouseEntered(mouseEvent1 -> {
            popup.set(buildingDetails(coordinate));
            popup.get().setAnchorX(mouseEvent1.getSceneX() + 5);
            popup.get().setAnchorY(mouseEvent1.getSceneY() + 5);
            popup.get().show(stage);
        });
        extendedBlock.getObject().setOnMouseExited(mouseEvent1 -> popup.get().hide());
        extendedBlock.getObject().setOnMouseClicked(mouseEvent -> {
            if (selectedBuildingCoordinate == null) {
                selectedBuildingCoordinate = coordinate;
                extendedBlock.getObject().setBlendMode(BlendMode.COLOR_DODGE);
            } else if (selectedBuildingCoordinate == coordinate) {
                selectedBuildingCoordinate = null;
                extendedBlock.getObject().setBlendMode(null);
            } else {
                Stronghold.getCurrentBattle().getBattleMap().getExtendedBlockByRowAndColumn(selectedBuildingCoordinate).getObject().setBlendMode(null);
                selectedBuildingCoordinate = null;
                extendedBlock.getObject().setBlendMode(BlendMode.COLOR_DODGE);
                selectedBuildingCoordinate = coordinate;
            }
        });
        if (!scrollPaneContent.getChildren().contains(extendedBlock.getObject()))
            scrollPaneContent.getChildren().add(extendedBlock.getObject());
        scribeDetails();
    }

    private void switchSelectionState(Coordinate coordinate) {
        ExtendedBlock extendedBlock = mapView[coordinate.row][coordinate.column];
        if (selectedBlocks.contains(extendedBlock)) {
            selectedBlocks.remove(extendedBlock);
            unselectBlockView(extendedBlock);
        } else selectBlock(extendedBlock);
    }

    private void delete(Coordinate coordinate) {
        ExtendedBlock extendedBlock = mapView[coordinate.row][coordinate.column];
        if (extendedBlock.getBlock().getBuilding() == null)
            return;
        extendedBlock.getBlock().removeBuilding();
        extendedBlock.removeBuilding();
    }

    private void unselectBlockView(ExtendedBlock extendedBlock) {
        extendedBlock.getBlockView().setStroke(null);
        updateSelectedBlocksPane();
    }

    private void selectBlock(ExtendedBlock extendedBlock) {
        selectedBlocks.add(extendedBlock);
        extendedBlock.getBlockView().setStroke(Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getColor());
        extendedBlock.getBlockView().setStrokeType(StrokeType.INSIDE);
        extendedBlock.getBlockView().setStrokeWidth(2);
        updateSelectedBlocksPane();
    }

    private void updateSelectedBlocksPane() {
        selectedTroopsInfoPane.getChildren().clear();
        selectedRoleCountMap.clear();
        selectedUnitsLabels.clear();
        int maxRate = 0;
        int minRate = 100;
        int rateSum = 0;
        int buildingCount = 0;
        for (ExtendedBlock selectedBlock : selectedBlocks) {
            ArrayList<MilitaryUnit> selectedUnits = selectedBlock.getBlock().getSelectableMilitaryUnitsByGovernment(Stronghold.getCurrentBattle().getGovernmentAboutToPlay());
            for (MilitaryUnit selectedUnit : selectedUnits)
                selectedRoleCountMap.put(selectedUnit.getRole().getName(), selectedRoleCountMap.getOrDefault(selectedUnit.getRole().getName(), 0) + 1);
            // TODO: complete
            Building building;
            if ((building = selectedBlock.getBlock().getBuilding()) instanceof ItemProducingBuilding) {
                ItemProducingBuildingType buildingType = (ItemProducingBuildingType) building.getBuildingType();
                if (buildingType.getRate() > maxRate) maxRate = buildingType.getRate();
                if (buildingType.getRate() < minRate) minRate = buildingType.getRate();
                rateSum += buildingType.getRate();
                buildingCount++;
            }
        }
        Label productionRate = new Label();
        productionRate.setWrapText(true);
        productionRate.setTextAlignment(TextAlignment.CENTER);
        if (buildingCount == 0) productionRate.setText("no production in this area");
        else
            productionRate.setText("max rate: " + maxRate + "\n" + "min rate: " + minRate + "\naverage rate: " + ((double) rateSum) / buildingCount);
        selectedTroopsInfoPane.getChildren().add(productionRate);
        if (selectedRoleCountMap.isEmpty()) return;
        for (Map.Entry<RoleName, Integer> roleCountEntry : selectedRoleCountMap.entrySet())
            selectedTroopsInfoPane.getChildren().add(generateTroopBox(roleCountEntry.getKey(), roleCountEntry.getValue()));
        Button moveButton = new Button("move");
        moveButton.setOnMouseClicked(mouseEvent -> moveSelectedUnits());
        Button attackButton = new Button("attack");
        attackButton.setOnMouseClicked(mouseEvent -> attackSelectedUnits());
        HBox actionsContainer = new HBox(moveButton, attackButton, unselectButton);
        actionsContainer.setAlignment(Pos.CENTER);
        selectedTroopsInfoPane.getChildren().add(actionsContainer);
    }

    private VBox generateTroopBox(RoleName type, int troopCount) {
        ImageView imageView = new ImageView(((MilitaryUnitRole) MilitaryUnitRole.getRoleByName(type)).getRoleDefaultImage());
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(50);
        Slider slider = new Slider(0, troopCount, troopCount);
        Label nameLabel = new Label(type + " | count = " + (int) slider.getValue());
        nameLabel.setWrapText(true);
        nameLabel.setTextAlignment(TextAlignment.CENTER);
        selectedUnitsLabels.add(nameLabel);
        HBox troopInfo = new HBox(imageView, nameLabel);
        troopInfo.setAlignment(Pos.CENTER);
        slider.valueProperty().addListener(observable -> nameLabel.setText(type + " | count = " + (int) slider.getValue()));
        VBox result = new VBox(troopInfo, slider);
        result.setAlignment(Pos.CENTER);
        result.setPadding(new Insets(10, 40, 5, 40));
        return result;
    }

    private void initializeTurnPane() {
        // TODO: add other initialization processes (state of troops, resources, ...)
        updateCurrentPlayerInfo();
    }

    private static Popup buildingDetails(Coordinate coordinate) {
        Popup popup = new Popup();
        VBox vBox = new VBox();
        Building building = Stronghold.getCurrentBattle().getBattleMap().getBlockByRowAndColumn(coordinate).getBuilding();
        String string = "coordinate: (" + coordinate.row + "," + coordinate.column + ")";
        string += "\nbuilding type: " + building.getBuildingType().getName().name();
        string += "\nhitpoint: " + building.getHitPoint();
        Text text = new Text(string);
        vBox.getChildren().add(text);
        popup.getContent().add(vBox);
        vBox.setBackground(Background.fill(Color.BROWN));
        text.setFill(Color.BEIGE);
        vBox.setPadding(new Insets(7));
        return popup;
    }

    public void castle() {
        buildingBox.getItems().clear();
        buildingBox.getItems().addAll(BuildingLists.castleBuildings.getItems());
    }

    public void farm() {
        buildingBox.getItems().clear();
        buildingBox.getItems().addAll(BuildingLists.farmBuildings.getItems());
    }

    public void foodProcessing() {
        buildingBox.getItems().clear();
        buildingBox.getItems().addAll(BuildingLists.foodProcessingBuildings.getItems());
    }

    public void industry() {
        buildingBox.getItems().clear();
        buildingBox.getItems().addAll(BuildingLists.industryBuildings.getItems());
    }

    public void town() {
        buildingBox.getItems().clear();
        buildingBox.getItems().addAll(BuildingLists.townBuildings.getItems());
    }

    public void weapon() {
        buildingBox.getItems().clear();
        buildingBox.getItems().addAll(BuildingLists.weaponBuildings.getItems());
    }

    public void unknown() {
        buildingBox.getItems().clear();
        buildingBox.getItems().addAll(BuildingLists.unknown.getItems());
    }

    private void popularityFactors() {
        HBox mainPane = new HBox(30);
        mainPane.setPrefHeight(stage.getHeight() / 5);
        mainPane.setPrefWidth(stage.getWidth() * 4 / 6);
        mainPane.setAlignment(Pos.CENTER);
        mainPane.setBackground(Background.fill(new ImagePattern(new Image(GameMenuGFXController.class.getResource("/images/backgrounds/menu.jpeg").toString()))));

        VBox first = new VBox(10);
        first.setTranslateX(-100);
        first.setAlignment(Pos.CENTER);
        Text popularity = new Text("Popularity");
        popularity.setFont(Font.font("Helvetica", FontPosture.ITALIC, 20));
        Button button = new Button("change rate");
        button.setOnMouseClicked(mouseEvent -> {
            Popup popup = changePopularityMenu();
            popup.show(stage);
        });
        ImagePattern backImage = new ImagePattern(new Image(GameMenuGFXController.class.getResource("/images/icons/back.png").toString()));
        Rectangle back = new Rectangle(30, 30, backImage);
        first.getChildren().addAll(popularity, button, back);
        back.setOnMouseClicked(mouseEvent -> {
            controlBox.getChildren().remove(0);
            controlBox.getChildren().add(0, buildingContainer);
            buildingBox.getItems().clear();
            buildingBox.getItems().addAll(BuildingLists.allBuildings.getItems());
        });

        int foodRate = Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getFoodRate();
        int taxRate = Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getTaxRate();
        int fearRate = Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getFearRate();
        int religionRate = Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getReligionCount();
        int total = foodRate + taxRate - fearRate + religionRate;
        HBox totalContainer = comingMonth(total);
        totalContainer.setTranslateX(-20);
        totalContainer.setTranslateY(50);

        VBox second = faces();
        VBox third = foodAndTax(foodRate, taxRate);
        VBox forth = fearAndReligion(fearRate, religionRate);
        mainPane.getChildren().addAll(first, second, third, forth, totalContainer);

        controlBox.getChildren().remove(0);
        controlBox.getChildren().add(0, mainPane);
    }

    private static VBox faces() {
        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);
        vbox.setTranslateX(-30);
        vbox.setTranslateY(20);
        Rectangle greenFace = new Rectangle(40, 60, new ImagePattern(new Image(GameMenuGFXController.class.getResource("/images/faces/greenFace.png").toString())));
        Rectangle redFace = new Rectangle(40, 60, new ImagePattern(new Image(GameMenuGFXController.class.getResource("/images/faces/redFace.png").toString())));
        redFace.setTranslateX(greenFace.getTranslateX() + 15);
        redFace.setTranslateY(greenFace.getTranslateY() - 30);
        vbox.getChildren().addAll(greenFace, redFace);
        return vbox;
    }

    private static VBox foodAndTax(int foodRateNum, int taxRateNum) {
        VBox vBox1 = new VBox(10);
        vBox1.setAlignment(Pos.CENTER);

        HBox hBox1 = new HBox(5);
        hBox1.setAlignment(Pos.CENTER_LEFT);
        Text rate = new Text(Integer.toString(foodRateNum));
        Rectangle foodFace = new Rectangle(30, 45);
        Text food = new Text("Food");
        rate.setFont(Font.font("Helvetica", 15));
        food.setFont(Font.font("Helvetica", 15));
        hBox1.getChildren().addAll(rate, foodFace, food);
        String color;
        if (foodRateNum > 0) color = "green";
        else if (foodRateNum == 0) color = "yellow";
        else color = "red";
        foodFace.setFill(new ImagePattern(new Image(GameMenuGFXController.class.getResource("/images/faces/" + color + "Face.png").toString())));

        HBox hBox2 = new HBox(5);
        hBox2.setAlignment(Pos.CENTER_LEFT);
        Text taxRate = new Text(Integer.toString(taxRateNum));
        Rectangle taxFace = new Rectangle(30, 45);
        Text tax = new Text("Tax");
        taxRate.setFont(Font.font("Helvetica", 15));
        tax.setFont(Font.font("Helvetica", 15));
        hBox2.getChildren().addAll(taxRate, taxFace, tax);
        if (taxRateNum > 0) color = "green";
        else if (taxRateNum == 0) color = "yellow";
        else color = "red";
        taxFace.setFill(new ImagePattern(new Image(GameMenuGFXController.class.getResource("/images/faces/" + color + "Face.png").toString())));

        vBox1.getChildren().addAll(hBox1, hBox2);
        return vBox1;
    }

    private static VBox fearAndReligion(int fearRateNum, int religionRateNum) {
        VBox vBox2 = new VBox(10);
        vBox2.setAlignment(Pos.CENTER);

        HBox fearContainer = new HBox(5);
        fearContainer.setAlignment(Pos.CENTER_LEFT);
        Text fearRate = new Text(Integer.toString(fearRateNum));
        Rectangle fearFace = new Rectangle(30, 45);
        Text fear = new Text("Fear Factor");
        fear.setFont(Font.font("Helvetica", 15));
        fearRate.setFont(Font.font("Helvetica", 15));
        fearContainer.getChildren().addAll(fearRate, fearFace, fear);
        String color;
        if (fearRateNum > 0) color = "red";
        else if (fearRateNum == 0) color = "yellow";
        else color = "green";
        fearFace.setFill(new ImagePattern(new Image(GameMenuGFXController.class.getResource("/images/faces/" + color + "Face.png").toExternalForm())));

        HBox religionContainer = new HBox(5);
        religionContainer.setAlignment(Pos.CENTER_LEFT);
        Text religionRate = new Text(Integer.toString(religionRateNum));
        Rectangle religionFace = new Rectangle(30, 45);
        Text religion = new Text("Religion");
        religionRate.setFont(Font.font("Helvetica", 15));
        religion.setFont(Font.font("Helvetica", 15));
        religionContainer.getChildren().addAll(religionRate, religionFace, religion);
        if (religionRateNum > 0) color = "green";
        else color = "yellow";
        religionFace.setFill(new ImagePattern(new Image(GameMenuGFXController.class.getResource("/images/faces/" + color + "Face.png").toExternalForm())));

        vBox2.getChildren().addAll(fearContainer, religionContainer);
        return vBox2;
    }

    private static HBox comingMonth(int total) {
        String totalCount;
        if (total > 0) totalCount = "+" + total;
        else totalCount = Integer.toString(total);
        HBox totalContainer = new HBox(10);
        totalContainer.setAlignment(Pos.CENTER);
        totalContainer.getChildren().add(new Text("In the coming month: " + totalCount));

        String color;
        if (total > 0) color = "green";
        else if (total == 0) color = "yellow";
        else color = "red";

        ImagePattern imagePattern = new ImagePattern(new Image(GameMenuGFXController.class.getResource("/images/faces/" + color + "Face.png").toString()));
        Rectangle rectangle = new Rectangle(20, 30, imagePattern);
        totalContainer.getChildren().add(rectangle);
        return totalContainer;
    }

    public void goToNextPlayer() {
        // TODO: handle animations and potential bugs
        unselectAllMethod();
        unitMessageLabel.setText("");
        GameMenuMessages result = GameMenuController.goToNextPlayer();
        if (result == GameMenuMessages.GAME_OVER) {
            try {
                new EndScreenGFX(Stronghold.getCurrentBattle().getAliveGovernment()).start(SignupMenu.stage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        scribeDetails();
        updateCurrentPlayerInfo();
    }

    private void updateCurrentPlayerInfo() {
        User currentPlayer = Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getOwner();
        currentPlayerAvatar.setFill(new ImagePattern(new Image(currentPlayer.getAvatar())));
        currentPlayerName.setText(currentPlayer.getUsername() + "\n~" + currentPlayer.getNickname() + "~");
    }

    private Popup changePopularityMenu() {
        Popup popup = new Popup();

        VBox sliders = new VBox(20);
        sliders.setAlignment(Pos.CENTER);

        Slider foodRateSlider = new Slider(-2, 2, Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getFoodRate());
        foodRateSlider.setBlockIncrement(1);
        Text foodRateText = new Text("food rate: " + (int) foodRateSlider.getValue());
        foodRateSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            foodRateText.setText("food rate: " + newValue.intValue());
            Stronghold.getCurrentBattle().getGovernmentAboutToPlay().setFoodRate(newValue.intValue());
        });
        VBox foodRateContainer = new VBox(5, foodRateSlider, foodRateText);
        foodRateContainer.setAlignment(Pos.CENTER);

        Slider taxRateSlider = new Slider(-3, 8, Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getTaxRate());
        taxRateSlider.setBlockIncrement(1);
        Text taxRateText = new Text("tax rate: " + (int) taxRateSlider.getValue());
        taxRateSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            taxRateText.setText("tax rate: " + newValue.intValue());
            Stronghold.getCurrentBattle().getGovernmentAboutToPlay().setTaxRate(newValue.intValue());
        });
        VBox taxRateContainer = new VBox(5, taxRateSlider, taxRateText);
        taxRateContainer.setAlignment(Pos.CENTER);

        Slider fearRateSlider = new Slider(-5, 5, Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getFearRate());
        Text fearRateText = new Text("fear rate: " + (int) fearRateSlider.getValue());
        fearRateSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            fearRateText.setText("fear rate: " + newValue.intValue());
            Stronghold.getCurrentBattle().getGovernmentAboutToPlay().setFearRate(newValue.intValue());
        });
        VBox fearRateContainer = new VBox(5, fearRateSlider, fearRateText);
        fearRateContainer.setAlignment(Pos.CENTER);

        Text religionCount = new Text("religion count: " + Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getReligionCount());
        ComboBox<String> religion = new ComboBox<>();
        religion.setPromptText("religion");
        religion.setMinHeight(25);
        religion.setMaxHeight(25);
        religion.getItems().add("have religion");
        religion.getItems().add("don't have religion");
        religion.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals("have religion")) {
                Stronghold.getCurrentBattle().getGovernmentAboutToPlay().addReligion();
                religionCount.setText("religion count: 1");
            } else {
                Stronghold.getCurrentBattle().getGovernmentAboutToPlay().removeReligion();
                religionCount.setText("religion count: 0");
            }
        });
        VBox religionContainer = new VBox(5, religion, religionCount);
        religionContainer.setAlignment(Pos.CENTER);

        Rectangle back = new Rectangle(50, 30, new ImagePattern(new Image(Objects.requireNonNull(GameMenuGFXController.class.getResource("/images/icons/backHand.png")).toString())));
        back.setOnMouseClicked(mouseEvent -> {
            popup.hide();
            popularityFactors();
        });

        sliders.getChildren().addAll(foodRateContainer, taxRateContainer, fearRateContainer, religionContainer, back);
        sliders.setPadding(new Insets(25));
        Image image = new Image(GameMenu.class.getResource("/images/backgrounds/greenSheet2.jpeg").toExternalForm(), 197, 316, false, false);
        BackgroundImage backgroundImage = new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        sliders.setBackground(new Background(backgroundImage));
        popup.getContent().add(sliders);

        return popup;
    }

    public void initializeMiniMap() {
        GridPane miniMap = new GridPane();
        int size = Stronghold.getCurrentBattle().getBattleMap().getSize();
        int q = 400 / size;
        for (int i = 0; i < 400; i++) {
            for (int j = 0; j < 400; j++) {
                Rectangle miniBlock = new Rectangle(miniMapBox.getPrefWidth() / 400, miniMapBox.getPrefHeight() / 400);
                miniBlock.setFill(Stronghold.getCurrentBattle().getBattleMap().getBlockByRowAndColumn(i / q, j / q).getTexture().getColor());
                miniMap.getChildren().add(miniBlock);
                GridPane.setConstraints(miniBlock, j, i);
            }
        }
        miniMapBox.getChildren().add(miniMap);
    }

    public void moveSelectedUnits() {
        ArrayList<MilitaryUnit> movingUnits = getSelectedUnits();
        prepareMoveAttackMenu(false, movingUnits);
    }

    public void attackSelectedUnits() {
        ArrayList<MilitaryUnit> attackingUnits = getSelectedUnits();
        prepareMoveAttackMenu(true, attackingUnits);
    }

    private ArrayList<MilitaryUnit> getSelectedUnits() {
        HashMap<RoleName, Integer> typeCountMap = new HashMap<>();
        for (Label selectedUnitsLabel : selectedUnitsLabels) {
            String[] words = selectedUnitsLabel.getText().split(" ");
            RoleName type = RoleName.getRoleNameByNameString(words[0]);
            int count = Integer.parseInt(words[words.length - 1]);
            if (count != 0) typeCountMap.put(type, count);
        }

        if (typeCountMap.size() == 0) return null;

        ArrayList<MilitaryUnit> selectedUnits = new ArrayList<>();
        for (ExtendedBlock selectedBlock : selectedBlocks)
            for (MilitaryUnit militaryUnit : selectedBlock.getBlock().getSelectableMilitaryUnitsByGovernment(Stronghold.getCurrentBattle().getGovernmentAboutToPlay())) {
                int temp;
                RoleName roleName = militaryUnit.getRole().getName();
                if ((temp = typeCountMap.getOrDefault(roleName, -1)) != -1) {
                    temp--;
                    selectedUnits.add(militaryUnit);
                    typeCountMap.put(roleName, temp);
                    if (temp == 0) typeCountMap.remove(roleName);
                }
            }
        return selectedUnits;
    }

    private void prepareMoveAttackMenu(boolean isAttack, ArrayList<MilitaryUnit> selectedUnits) {
        selectedTroopsInfoPane.getChildren().clear();
        Label xLabel = new Label("x = ");
        Spinner<Integer> xSpinner = new Spinner<>(0, mapView.length - 1, 0);
        HBox xContainer = new HBox(xLabel, xSpinner);
        xContainer.setAlignment(Pos.CENTER);
        Label yLabel = new Label("y = ");
        Spinner<Integer> ySpinner = new Spinner<>(0, mapView.length - 1, 0);
        HBox yContainer = new HBox(yLabel, ySpinner);
        yContainer.setAlignment(Pos.CENTER);
        Button moveButton = new Button(isAttack ? "attack" : "move");
        moveButton.setOnMouseClicked(mouseEvent -> {
            UnitMenuController.selectedMilitaryUnits = selectedUnits;
            if (isAttack) {
                UnitMenuMessages result = UnitMenuController.attackEnemy(new Coordinate(xSpinner.getValue(), ySpinner.getValue()));
                if (result == UnitMenuMessages.TARGET_OUT_OF_RANGE)
                    unitMessageLabel.setText("target is out of range, move closer and try again!");
                else if (result == UnitMenuMessages.NO_ENEMY_HERE) unitMessageLabel.setText("there's no enemy here!");
                else unitMessageLabel.setText("units are attacking target");
            } else {
                UnitMenuMessages result = UnitMenuController.moveUnit(new Coordinate(xSpinner.getValue(), ySpinner.getValue()));
                if (result == UnitMenuMessages.INVALID_DESTINATION) unitMessageLabel.setText("units can't go here!");
                else if (result == UnitMenuMessages.NO_WAY_THERE)
                    unitMessageLabel.setText("there is no way to get there!");
                else
                    unitMessageLabel.setText("destination set successfully, units will go there when your turn is over");
            }
            PauseTransition visiblePause = new PauseTransition(
                    Duration.seconds(3)
            );
            unselectAllMethod();
            updateSelectedBlocksPane();
            visiblePause.setOnFinished(event -> unitMessageLabel.setText(""));
            visiblePause.play();
        });
        Button cancelButton = new Button("back");
        cancelButton.setOnMouseClicked(mouseEvent -> updateSelectedBlocksPane());
        HBox buttonContainer = new HBox(moveButton, cancelButton);
        buttonContainer.setAlignment(Pos.CENTER);
        selectedTroopsInfoPane.getChildren().addAll(xContainer, yContainer, buttonContainer);
    }

    private void unselectAllHandleMethod(MouseEvent mouseEvent) {
        unselectAllMethod();
    }

    private void unselectAllMethod() {
        selectedTroopsInfoPane.getChildren().clear();
        selectedUnitsLabels.clear();
        for (ExtendedBlock selectedBlock : selectedBlocks)
            selectedBlock.getBlockView().setStroke(null);
        selectedRoleCountMap.clear();
        selectedBlocks.clear();
    }

    public void zoom(boolean zoomIn) {
        double vValue = mapBox.getVvalue();
        double hValue = mapBox.getHvalue();
        if (scrollPaneContent.getScaleX() > 1.5 && zoomIn || scrollPaneContent.getScaleX() < 0.8 && !zoomIn) return;
        double scaleFactor = zoomIn ? 1.1 : 1 / 1.1;
        scrollPaneContent.setScaleX(scrollPaneContent.getScaleX() * scaleFactor);
        scrollPaneContent.setScaleY(scrollPaneContent.getScaleY() * scaleFactor);
        mapBox.setVvalue(vValue);
        mapBox.setHvalue(hValue);
    }
}