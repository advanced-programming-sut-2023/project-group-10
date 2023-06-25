package org.example.model.game.envirnmont;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import org.example.controller.CustomizeMapController;
import org.example.controller.GameMenuController;
import org.example.model.game.RockType;
import org.example.model.game.TreeType;
import org.example.model.game.buildings.buildingconstants.BuildingTypeName;
import org.example.model.game.units.unitconstants.RoleName;
import org.example.view.GameMenuGFXController;
import org.example.view.enums.messages.CustomizeMapMessages;
import org.example.view.enums.messages.GameMenuMessages;

import java.util.ArrayList;
import java.util.HashMap;

public class ExtendedBlock {
    private static final double WIDTH = 64;
    private static final double HEIGHT = 32;
    private static final HashMap<BlockTexture, ImagePattern> textureImageMap;
    private static final HashMap<TreeType, ImagePattern> treeImageMap;
    private static final HashMap<RockType, ImagePattern> rockImageMap;
    private static final HashMap<BuildingTypeName, ImagePattern> buildingImageMap;
    private static final HashMap<RoleName, ImagePattern> unitImageMap;
    private final Block block;
    private final Polygon blockView;
    private Rectangle object;
    private final double x0;
    private final ArrayList<Rectangle> unitsView;

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
        buildingImageMap = new HashMap<>();
        for (BuildingTypeName value : BuildingTypeName.values())
            buildingImageMap.put(value, new ImagePattern(new Image(GameMenuGFXController.class.getResource("/images/buildings/" + value.toString().toLowerCase() + ".png").toExternalForm())));
        unitImageMap = new HashMap<>();
//        for (RoleName value : RoleName.values()) {
//
//        }
    }

    public ExtendedBlock(Block block, int row, int column, double x0) {
        this.block = block;
        blockView = new Polygon(-WIDTH / 2, HEIGHT / 2, 0.0, 0.0, WIDTH / 2, HEIGHT / 2, 0.0, HEIGHT);
        setTexture(block.getTexture(), new Coordinate(row, column));
        this.x0 = x0;
        unitsView = new ArrayList<>();
        setPosition(row, column);
    }

    public static double getWidth() {
        return WIDTH;
    }

    public static double getHeight() {
        return HEIGHT;
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
            setObjectProperties(position.row, position.column);
        }
        return result;
    }

    public CustomizeMapMessages setRock(Coordinate position, RockType rockType) {
        CustomizeMapMessages result = CustomizeMapController.dropRock(position, rockType);
        if (result == CustomizeMapMessages.DROP_ROCK_SUCCESSFUL) {
            object = new Rectangle();
            object.setFill(rockImageMap.get(rockType));
            setObjectProperties(position.row, position.column);
        }
        return result;
    }

    public GameMenuMessages setBuilding(Coordinate position, BuildingTypeName typeName) {
        GameMenuMessages result = GameMenuController.dropBuilding(position, typeName);
        if (result == GameMenuMessages.SUCCESSFUL_DROP) {
            object = new Rectangle();
            object.setFill(buildingImageMap.get(typeName));
            setObjectProperties(position.row, position.column);
        }
        return result;
    }

    public void setKeep(int row, int column) {
        object = new Rectangle();
        object.setFill(new ImagePattern(new Image(GameMenuGFXController.class.getResource("/images/buildings/keep").toExternalForm() + "keep" + (int) (Math.random() * 4 + 1) + ".png")));
        setObjectProperties(row, column);
    }

//    public GameMenuMessages dropUnit(Coordinate position, RoleName typeName, int count) {
//        GameMenuMessages result = GameMenuController.dropUnit(position, typeName, count);
//        if (result == GameMenuMessages.SUCCESSFUL_DROP) {
//            for(int i=0; i<count;i++) {
//                Rectangle unitView=new Rectangle();
//
//            }
//        }
//    }

    private void setObjectProperties(int row, int column) {
        ImagePattern paint = (ImagePattern) object.getFill();
        double heightToWidthRatio = paint.getImage().getHeight() / paint.getImage().getWidth();
        object.setWidth(WIDTH / 2);
        object.setHeight(heightToWidthRatio * WIDTH / 2);
        object.relocate(WIDTH / 2 * (column - row) + x0 + object.getWidth() / 2, HEIGHT / 2 * (row + column + 2) - object.getHeight()+5);
    }

    private void setPosition(int row, int column) {
        blockView.relocate(WIDTH / 2 * (column - row) + x0, HEIGHT / 2 * (row + column + 1));
    }

    public CustomizeMapMessages erase(Coordinate position) {
        setTexture(BlockTexture.EARTH, position);
        object = null;
        return CustomizeMapController.clear(position);
    }
}
