package org.example.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import org.example.model.Stronghold;
import org.example.model.User;
import org.example.model.game.Government;

import java.io.File;

public class BriefingMenuController {
    public VBox golds;
    public HBox lords;
    public Pane miniMapPane;

    @FXML
    public void initialize() {
        golds.setStyle("-fx-background-color: rgba(225, 225, 225, 0.5); -fx-background-radius: 10;");
        miniMapPane.setStyle("-fx-background-color: rgba(225, 225, 225, 0.5); -fx-background-radius: 10;");
        lords.setStyle("-fx-background-color: rgba(225, 225, 225, 0.5); -fx-background-radius: 10;");
        initializeGolds();
        initializeLords();
        initializeMiniMap();


    }

    private void initializeMiniMap() {
        GridPane miniMap = new GridPane();
        int size = Stronghold.getCurrentBattle().getBattleMap().getSize();
        int q = 400 / size;
        for (int i = 0; i < 400; i++) {
            for (int j = 0; j < 400; j++) {
                Rectangle miniBlock = new Rectangle(miniMapPane.getPrefWidth() / 400, miniMapPane.getPrefHeight() / 400);
                if (Stronghold.getCurrentBattle().getBattleMap().getBlockByRowAndColumn(i / q, j / q).isKeep())
                    miniBlock.setFill(Stronghold.getCurrentBattle().getBattleMap().getBlockByRowAndColumn(i / q, j / q).getKeepGovernment().getColor());
                else
                    miniBlock.setFill(Stronghold.getCurrentBattle().getBattleMap().getBlockByRowAndColumn(i / q, j / q).getTexture().getColor());
                miniMap.getChildren().add(miniBlock);
                GridPane.setConstraints(miniBlock, j, i);
            }
        }
        miniMapPane.getChildren().add(miniMap);
    }

    private void initializeLords() {
        Government[] governments = Stronghold.getCurrentBattle().getGovernments();
        for (int i = 0; i < governments.length; i++) {
            VBox info = new VBox();
            info.setPrefWidth((lords.getPrefWidth() - lords.getSpacing() * 7) / 8);
            Rectangle avatar = new Rectangle(120, 120);
            avatar.setFill(new ImagePattern(new Image(User.getUserByUsername(governments[i].getOwner().getUsername()).getAvatar())));
            Label name = new Label(User.getUserByUsername(governments[i].getOwner().getUsername()).getNickname());
            name.setTextFill(governments[i].getColor());
            name.setFont(new Font("PT Mono", 18));
            info.getChildren().addAll(avatar, name);
            lords.getChildren().add(info);
        }
    }

    private void initializeGolds() {
        Government[] governments = Stronghold.getCurrentBattle().getGovernments();
        for (int i = 0; i < governments.length; i++) {
            HBox info = new HBox();
            info.setPrefHeight((golds.getPrefWidth() - lords.getSpacing() * 7) / 8);
            Rectangle goldsPic = new Rectangle(25, 25);
            goldsPic.setFill(new ImagePattern(new Image(new File("src/main/resources/images/icons/coins.png").toURI().toString())));
            Label coins = new Label(User.getUserByUsername(governments[i].getOwner().getUsername()).getNickname() + " : " + governments[i].getGold());
            coins.setTextFill(governments[i].getColor());
            coins.setFont(new Font("PT Mono", 18));
            info.getChildren().addAll(coins, goldsPic);
            golds.getChildren().add(info);
        }
    }

    public void back(MouseEvent mouseEvent) {
        BriefingGFX.stage.close();
    }
}
