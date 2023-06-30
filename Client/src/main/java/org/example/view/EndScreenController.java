package org.example.view;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.example.model.game.Government;

public class EndScreenController {
    public Label nameLabel;
    public ImageView avatarImage;

    private Government winner;

    public void setWinner(Government winner) {
        this.winner = winner;
    }

    public void prepare() {
        avatarImage.setImage(new Image(winner.getOwner().getAvatar()));
        nameLabel.setText(winner.getOwner().getUsername() + "\n~" + winner.getOwner().getNickname() + "~");
    }

    public void goBackToMainMenu() {
        try {
            new MainMenuGFX().start(SignupMenu.stage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
