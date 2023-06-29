package org.example.view.transitions;

import javafx.animation.Transition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.File;
import java.nio.file.Files;
import java.util.Objects;

public class FireTransition extends Transition {
    private static Image[] fireAssets;
    private final ImageView fireNode;

    public FireTransition(ImageView fireNode) {
        this.fireNode = fireNode;
        setCycleCount(INDEFINITE);
        setCycleDuration(Duration.millis(2000));
    }

    public static void initializeImages() {
        String assetFolderAddress = "./StrongholdCrusader/src/main/resources/images/fire";
        File directory = new File(assetFolderAddress);
        fireAssets = new Image[Objects.requireNonNull(directory.listFiles()).length];
        int i = 0;
        for (File file : Objects.requireNonNull(directory.listFiles())) {
            fireAssets[i] = new Image(file.toURI().toString());
            i++;
        }
    }

    @Override
    protected void interpolate(double v) {
        if (v == 1) v -= 0.01;
        fireNode.setImage(fireAssets[(int) (v * fireAssets.length)]);
    }
}
