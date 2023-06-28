package org.example.model.game.envirnmont;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.util.Pair;
import org.example.controller.CustomizeMapController;
import org.example.controller.GameMenuController;
import org.example.model.game.RockType;
import org.example.model.game.TreeType;
import org.example.model.game.buildings.buildingconstants.BuildingTypeName;
import org.example.view.GameMenuGFXController;
import org.example.view.enums.messages.CustomizeMapMessages;
import org.example.view.enums.messages.GameMenuMessages;

import java.util.HashMap;

public class ExtendedBlock {
    private static final double WIDTH = 200;
    private static final double HEIGHT = 100;
    private static double x0;
    private static final HashMap<BlockTexture, ImagePattern> textureImageMap;
    private static final HashMap<TreeType, ImagePattern> treeImageMap;
    private static final HashMap<RockType, ImagePattern> rockImageMap;
    private static final ImagePattern[] randomRocks;
    private static final HashMap<BuildingTypeName, ImagePattern> buildingImageMap;
    private final Block block;
    private final Polygon blockView;
    private Rectangle object;

    static {
        textureImageMap = new HashMap<>();
        for (BlockTexture value : BlockTexture.values())
            textureImageMap.put(value, new ImagePattern(new Image(BlockTexture.getTextureListAssetsFolderPath() + value.getListAssetFileName())));
        treeImageMap = new HashMap<>();
        for (TreeType value : TreeType.values())
            treeImageMap.put(value, new ImagePattern(new Image(TreeType.getTreeListAssetsFolderPath() + value.getListAssetFileName())));
        rockImageMap = new HashMap<>();
        for (RockType value : RockType.values())
            rockImageMap.put(value, new ImagePattern(new Image(RockType.getRockListAssetsFolderPath() + value.getListAssetFileName())));
        randomRocks = new ImagePattern[10];
        for (int i = 0; i < randomRocks.length; i++)
            randomRocks[i] = new ImagePattern(new Image(RockType.getRockListAssetsFolderPath() + "random/" + (i + 1) + ".png"));
        buildingImageMap = new HashMap<>();
        for (BuildingTypeName value : BuildingTypeName.values())
            buildingImageMap.put(value, new ImagePattern(new Image(GameMenuGFXController.class.getResource("/images/buildings/" + value.toString().toLowerCase() + ".png").toExternalForm())));
    }

    public ExtendedBlock(Block block, int row, int column) {
        this.block = block;
        blockView = new Polygon(-WIDTH / 2, HEIGHT / 2, 0.0, 0.0, WIDTH / 2, HEIGHT / 2, 0.0, HEIGHT);
        setTexture(block.getTexture(), new Coordinate(row, column));
        setPosition(row, column);
    }

    public static double getX0() {
        return x0;
    }

    public static void setX0(double x0) {
        ExtendedBlock.x0 = x0;
    }

    public static double getWidth() {
        return WIDTH;
    }

    public static double getHeight() {
        return HEIGHT;
    }

    public static Pair<Double, Double> getCenterOfBlockForUnits(int row, int column, double unitWidth, double unitHeight) {
        return new Pair<>(WIDTH / 2 * (column - row + 1) + x0 - unitWidth / 2, HEIGHT / 2 * (row + column + 2.5) - unitHeight);
    }

    public static Pair<Double, Double> getRandomPositioningForUnits(int row, int column, double unitWidth, double unitHeight) {
        Pair<Double, Double> result = getCenterOfBlockForUnits(row, column, unitWidth, unitHeight);
        double randomX = WIDTH / 2 * Math.random() - WIDTH / 4;
        double randomY = HEIGHT / 2 * Math.random() - HEIGHT / 4;
        return new Pair<>(result.getKey() + randomX, result.getValue() + randomY);
    }

    public Block getBlock() {
        return block;
    }

    public Polygon getBlockView() {
        return blockView;
    }

    public Rectangle getObject() {
        return object;
    }

    public CustomizeMapMessages setTexture(BlockTexture texture, Coordinate position) {
        CustomizeMapMessages result = CustomizeMapController.setTexture(texture, position);
        if (result == CustomizeMapMessages.SET_TEXTURE_OF_BLOCK_SUCCESSFUL)
            blockView.setFill(textureImageMap.get(texture));
        return result;
    }

    public CustomizeMapMessages setTree(Coordinate position, TreeType treeType) {
        CustomizeMapMessages result = CustomizeMapController.dropTree(position, treeType);
        if (result == CustomizeMapMessages.SUCCESSFUL_TREE_DROP) {
            object = new Rectangle();
            object.setFill(treeImageMap.get(treeType));
            setObjectProperties(position.row, position.column, true);
        }
        return result;
    }

    public CustomizeMapMessages setRock(Coordinate position, RockType rockType) {
        CustomizeMapMessages result = CustomizeMapController.dropRock(position, rockType);
        if (result == CustomizeMapMessages.DROP_ROCK_SUCCESSFUL) {
            object = new Rectangle();
            object.setFill(rockImageMap.get(rockType));
            setObjectProperties(position.row, position.column, true);
        }
        return result;
    }

    public GameMenuMessages setBuilding(Coordinate position, BuildingTypeName typeName) {
        GameMenuMessages result = GameMenuController.dropBuilding(position, typeName);
        if (result == GameMenuMessages.SUCCESSFUL_DROP) {
            object = new Rectangle();
            object.setFill(buildingImageMap.get(typeName));
            setObjectProperties(position.row, position.column, false);
        }
        return result;
    }

    public void setKeep(int row, int column) {
        // TODO: set keep boolean in block object to true if necessary
        object = new Rectangle();
        object.setFill(new ImagePattern(new Image(GameMenuGFXController.class.getResource("/images/buildings/keep").toExternalForm() + "keep" + (int) (Math.random() * 4 + 1) + ".png")));
        setObjectProperties(row, column, false);
    }

//    public GameMenuMessages dropUnit(Coordinate position, RoleName typeName, int count) {
//        return GameMenuController.dropUnit(position, typeName, count);
//    }

    private void setObjectProperties(int row, int column, boolean isNatural) {
        ImagePattern paint = (ImagePattern) object.getFill();
        double heightToWidthRatio = paint.getImage().getHeight() / paint.getImage().getWidth();
        object.setWidth(isNatural ? WIDTH / 6 : WIDTH / 2);
        object.setHeight(heightToWidthRatio * object.getWidth());
        if (isNatural) {
            Pair<Double, Double> center = getCenterOfBlockForUnits(row, column, object.getWidth(), object.getHeight());
            object.relocate(center.getKey(), center.getValue());
        } else
            object.relocate(WIDTH / 2 * (column - row) + x0 + object.getWidth() / 2, HEIGHT / 2 * (row + column + 2) - object.getHeight() + 5);
        object.setPickOnBounds(false);
    }

    private void setPosition(int row, int column) {
        blockView.relocate(WIDTH / 2 * (column - row) + x0, HEIGHT / 2 * (row + column + 1));
    }

    public CustomizeMapMessages erase(Coordinate position) {
        setTexture(BlockTexture.EARTH, position);
        object = null;
        return CustomizeMapController.clear(position);
    }

    public void removeBuilding() {
        ((Group) object.getParent()).getChildren().remove(object);
        object = null;
    }
}
