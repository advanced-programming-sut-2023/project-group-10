package org.example.view.unittransitions;

import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Shape;
import javafx.util.Duration;
import org.example.model.game.units.unitconstants.MilitaryUnitRole;

import java.io.File;
import java.util.Objects;

public class UnitSpriteTransition extends Transition {
    private final ImagePattern[] sprites;
    private final Shape target;

    public UnitSpriteTransition(MilitaryUnitRole militaryUnitRole, String assetFolderAddress, Duration duration, Shape target) {
        this.target = target;
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
        target.setFill(sprites[(int) (v * sprites.length) % sprites.length]);
    }
}
