package org.example.view.transitions;

import javafx.animation.Transition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.File;
import java.util.Objects;

public class FireTransition extends Transition {
    private static Image[] fireAssets = new Image[18];
    private final ImageView fireNode;

    public FireTransition(ImageView fireNode) {
        this.fireNode = fireNode;
        setCycleCount(INDEFINITE);
        setCycleDuration(Duration.millis(2000));
    }

    public static void initializeImages() {
        for(int j = 1; j <= 18; j++){
            fireAssets[j-1] = new Image(FireTransition.class.getResource("/images/fire/" + j + ".png").toExternalForm());
        }
    }

    @Override
    protected void interpolate(double v) {
        if (v == 1) v -= 0.01;
        fireNode.setImage(fireAssets[(int) (v * fireAssets.length)]);
    }
}
