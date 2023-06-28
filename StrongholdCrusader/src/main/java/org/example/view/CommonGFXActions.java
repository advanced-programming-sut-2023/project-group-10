package org.example.view;

import javafx.animation.SequentialTransition;
import javafx.animation.Transition;
import javafx.scene.Group;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import javafx.util.Pair;
import org.example.model.game.envirnmont.Coordinate;
import org.example.model.game.envirnmont.ExtendedBlock;
import org.example.model.game.units.unitconstants.MilitaryUnitRole;
import org.example.model.game.units.unitconstants.Role;
import org.example.view.unittransitions.UnitMoveTransition;
import org.example.view.unittransitions.UnitSpriteTransition;

import java.util.List;

public class CommonGFXActions {
    public static void setMapScrollPaneProperties(ScrollPane mapBox) {
        mapBox.setPannable(true);
        mapBox.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        mapBox.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        mapBox.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            if (event.getButton() != MouseButton.PRIMARY) mapBox.setPannable(false);
        });
        mapBox.addEventHandler(MouseEvent.MOUSE_RELEASED, event -> mapBox.setPannable(true));
    }

    public static Transition getMoveAnimation(Role unitType, List<Coordinate> path, Coordinate currentLocation, Rectangle target) {
        String assetFolder = "src/main/resources/images/units/" + unitType.getName().name() + "/moving/";
        if (path.size() == 0) return null;
        String[] directions = new String[path.size()];
        directions[0] = getMoveDirection(currentLocation, path.get(0));
        for (int i = 1; i < directions.length; i++)
            directions[i] = getMoveDirection(path.get(i - 1), path.get(i));
        SequentialTransition moveTransition = new SequentialTransition();
        Pair<Double, Double> originalPosition = new Pair<>(target.getLayoutX(), target.getLayoutY());
        Pair<Double, Double> targetPosition;
        for (int i = 0; i < directions.length; i++) {
            targetPosition = ExtendedBlock.getRandomPositioningForUnits(path.get(i).row, path.get(i).column, target.getWidth(), target.getHeight());
            UnitMoveTransition unitMoveTransition = new UnitMoveTransition((MilitaryUnitRole) unitType, assetFolder + directions[i], targetPosition.getKey() - originalPosition.getKey(), targetPosition.getValue() - originalPosition.getValue(), originalPosition.getKey(), originalPosition.getValue(), Duration.millis(3000), target);
            originalPosition = targetPosition;
            moveTransition.getChildren().add(unitMoveTransition);
        }
        return moveTransition;
    }

    public static Transition getAttackAnimation(Role unitType, Rectangle targetView, Coordinate currentLocation, Coordinate targetLocation) {
        String assetFolder = "src/main/resources/images/units/" + unitType.getName().name() + "/attacking/" + getClosestAttackDirection(currentLocation, targetLocation);
        return new UnitSpriteTransition((MilitaryUnitRole) unitType, assetFolder, Duration.millis(1000), targetView);
    }

    public static Transition getDeathAnimation(Role unitType, Rectangle targetView) {
        if (targetView == null) return null;
        String assetFolder = "src/main/resources/images/units/" + unitType.getName().name() + "/dying";
        Transition deathTransition = new UnitSpriteTransition((MilitaryUnitRole) unitType, assetFolder, Duration.millis(2000), targetView);
        deathTransition.setOnFinished(event -> ((Group) targetView.getParent()).getChildren().remove(targetView));
        return deathTransition;
    }

    private static String getClosestAttackDirection(Coordinate currentLocation, Coordinate targetLocation) {
        int colDiff = targetLocation.column - currentLocation.column;
        int rowDiff = targetLocation.row - currentLocation.row;
        if (Math.abs(colDiff) > Math.abs(rowDiff)) {
            if (colDiff < 0) return "left";
            else return "right";
        } else if (rowDiff < 0) return "up";
        else return "down";
    }

    private static String getMoveDirection(Coordinate start, Coordinate end) {
        int colDiff = end.column - start.column;
        int rowDiff = end.row - start.row;
        if (colDiff == -1) return "left";
        else if (colDiff == 1) return "right";
        else if (rowDiff == -1) return "up";
        else return "down";
    }
}
