package org.example.model.game.envirnmont;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;

import java.util.HashMap;

public class ExtendedBlock {
    private static final double WIDTH = 32;
    private static final double HEIGHT = 16;
    private final Block block;
    private final Polygon blockView;
    private static final HashMap<BlockTexture, ImagePattern> textureImageMap;

    static {
        textureImageMap = new HashMap<>();
        for (BlockTexture value : BlockTexture.values())
            textureImageMap.put(value, new ImagePattern(new Image(BlockTexture.getTextureListAssetsFolderPath() + value.getListAssetFileName())));
    }

    public ExtendedBlock(Block block, int row, int column, double x0) {
        this.block = block;
        blockView = new Polygon(-WIDTH / 2, HEIGHT / 2, 0.0, 0.0, WIDTH / 2, HEIGHT / 2, 0.0, HEIGHT);
        setTexture(block.getTexture());
        setPosition(row, column, x0);
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

    public void setTexture(BlockTexture texture) {
        block.setTexture(texture);
        blockView.setFill(textureImageMap.get(texture));
    }

    private void setPosition(int row, int column, double x0) {
        blockView.relocate(WIDTH / 2 * (column - row) + x0, HEIGHT / 2 * (row + column + 1));
    }
}
