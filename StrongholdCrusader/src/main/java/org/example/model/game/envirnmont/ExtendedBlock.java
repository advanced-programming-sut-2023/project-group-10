package org.example.model.game.envirnmont;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import org.example.controller.CustomizeMapController;
import org.example.model.game.RockType;
import org.example.model.game.TreeType;
import org.example.view.enums.messages.CustomizeMapMessages;

import java.util.HashMap;

public class ExtendedBlock {
    private static final double WIDTH = 64;
    private static final double HEIGHT = 32;
    private static final HashMap<BlockTexture, ImagePattern> textureImageMap;
    private static final HashMap<TreeType, ImagePattern> treeImageMap;
    private static final HashMap<RockType, ImagePattern> rockImageMap;
    private final Block block;
    private final Polygon blockView;
    private Rectangle object;
    private final double x0;

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
    }

    public ExtendedBlock(Block block, int row, int column, double x0) {
        this.block = block;
        blockView = new Polygon(-WIDTH / 2, HEIGHT / 2, 0.0, 0.0, WIDTH / 2, HEIGHT / 2, 0.0, HEIGHT);
        setTexture(block.getTexture(), new Coordinate(row, column));
        this.x0 = x0;
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

    public void setTexture(BlockTexture texture, Coordinate position) {
        if (CustomizeMapController.setTexture(texture, position) != CustomizeMapMessages.SET_TEXTURE_OF_BLOCK_SUCCESSFUL)
            return;
        blockView.setFill(textureImageMap.get(texture));
    }

    public Shape setTree(Coordinate position, TreeType treeType) {
        if (CustomizeMapController.dropTree(position, treeType) != CustomizeMapMessages.SUCCESSFUL_TREE_DROP)
            return null;
        object = new Rectangle();
        object.setFill(treeImageMap.get(treeType));
        setObjectProperties(position.row, position.column);
        return object;
    }

    public Shape setRock(Coordinate position, RockType rockType) {
        if (CustomizeMapController.dropRock(position, rockType) != CustomizeMapMessages.DROP_ROCK_SUCCESSFUL)
            return null;
        object = new Rectangle();
        object.setFill(rockImageMap.get(rockType));
        setObjectProperties(position.row, position.column);
        return object;
    }

    private void setObjectProperties(int row, int column) {
        object.setWidth(WIDTH / 2);
        object.setHeight(HEIGHT);
        object.relocate(WIDTH / 2 * (column - row + 0.5) + x0, HEIGHT / 2 * (row + column + 0.5));
    }

    private void setPosition(int row, int column) {
        blockView.relocate(WIDTH / 2 * (column - row) + x0, HEIGHT / 2 * (row + column + 1));
    }
}
