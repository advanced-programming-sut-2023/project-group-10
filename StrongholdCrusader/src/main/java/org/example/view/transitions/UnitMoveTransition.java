package org.example.view.transitions;

import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Shape;
import javafx.util.Duration;
import org.example.model.game.units.unitconstants.MilitaryUnitRole;

import java.io.File;
import java.util.Objects;

public class UnitMoveTransition extends Transition {
    private static final int SPRITE_CHANGE_RATE = 2;
    private final ImagePattern[] sprites;
    private final Shape target;
    private final double xDiff;
    private final double yDiff;
    private final double originalX;
    private final double originalY;

    public UnitMoveTransition(MilitaryUnitRole militaryUnitRole, String assetFolderAddress, double xDiff, double yDiff, double originalX, double originalY, Duration duration, Shape target) {
        this.target = target;
        this.xDiff = xDiff;
        this.yDiff = yDiff;
        this.originalX = originalX;
        this.originalY = originalY;
        File directory = new File(assetFolderAddress);
        sprites = new ImagePattern[Objects.requireNonNull(directory.listFiles()).length];
        int i = 0;
        for (File file : Objects.requireNonNull(directory.listFiles())) {
            sprites[i] = new ImagePattern(new Image(file.getAbsolutePath()));
            i++;
        }
        setCycleDuration(duration);
        setInterpolator(Interpolator.LINEAR);
        setCycleCount(1);
        setOnFinished(actionEvent -> target.setFill(new ImagePattern(militaryUnitRole.getRoleDefaultImage())));
    }

    @Override
    protected void interpolate(double v) {
        target.relocate(originalX + xDiff * v, originalY + yDiff * v);
        target.setFill(sprites[(int) (v * sprites.length * SPRITE_CHANGE_RATE) % sprites.length]);
    }
}