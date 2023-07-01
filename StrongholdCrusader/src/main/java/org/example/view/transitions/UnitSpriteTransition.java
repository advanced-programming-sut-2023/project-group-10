package org.example.view.transitions;

import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Shape;
import javafx.util.Duration;
import org.example.model.game.units.unitconstants.MilitaryUnitRole;
import org.example.model.game.units.unitconstants.RoleName;

import java.io.File;
import java.util.HashMap;
import java.util.Objects;

public class UnitSpriteTransition extends Transition {
    private static HashMap<String, ImagePattern[]> pathSpritesMap;
    private final Shape target;
    private final ImagePattern[] sprites;

    public UnitSpriteTransition(MilitaryUnitRole militaryUnitRole, String assetFolderAddress, Duration duration, Shape target) {
        this.target = target;
        sprites = pathSpritesMap.get(assetFolderAddress);
        setCycleDuration(duration);
        setInterpolator(Interpolator.LINEAR);
        setCycleCount(1);
        setOnFinished(actionEvent -> target.setFill(new ImagePattern(militaryUnitRole.getRoleDefaultImage())));
    }

    public static void initializeImages() {
        pathSpritesMap = new HashMap<>();
        String[] actions = {"moving", "attacking"};
        String[] directions = {"up", "down", "left", "right"};
        for (RoleName value : RoleName.values()) {
            for (String action : actions)
                for (String direction : directions) {
                    String assetFolderAddress = "src/main/resources/images/units/" + value.name() + "/" + action + "/" + direction;
                    File directory = new File(assetFolderAddress);
                    if (directory.listFiles() == null) continue;
                    ImagePattern[] sprites = new ImagePattern[directory.listFiles().length];
                    int i = 0;
                    for (File file : Objects.requireNonNull(directory.listFiles())) {
                        sprites[i] = new ImagePattern(new Image(file.toURI().toString()));
                        i++;
                    }
                    pathSpritesMap.put(assetFolderAddress, sprites);
                }
            String assetFolderAddress = "src/main/resources/images/units/" + value.name() + "/dying";
            File directory = new File(assetFolderAddress);
            if (directory.listFiles() == null) continue;
            ImagePattern[] sprites = new ImagePattern[directory.listFiles().length];
            int i = 0;
            for (File file : Objects.requireNonNull(directory.listFiles())) {
                sprites[i] = new ImagePattern(new Image(file.toURI().toString()));
                i++;
            }
            pathSpritesMap.put(assetFolderAddress, sprites);
        }
    }

    @Override
    protected void interpolate(double v) {
        target.setFill(sprites[(int) (v * sprites.length) % sprites.length]);
    }
}
