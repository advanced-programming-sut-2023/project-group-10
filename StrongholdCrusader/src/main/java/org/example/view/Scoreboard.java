package org.example.view;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollBar;
import javafx.scene.image.Image;
import javafx.scene.input.*;
import javafx.scene.layout.HBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.controller.ProfileMenuController;
import org.example.model.Stronghold;
import org.example.model.User;

import java.util.ArrayList;
import java.util.List;


public class Scoreboard extends Application {
    private static Stage stage;
    public Circle currentPlayerAvatar;
    public Text currentPlayerUsername;
    public Text currentPlayerScore;
    private String path;


    @Override
    public void start(Stage stage) throws Exception {
        Scoreboard.stage = stage;

        ListView<Integer> rank = new ListView<>();
        rank.setMaxWidth(70);
        rank.setFixedCellSize(70);
        ListView<Circle> avatar = new ListView<>();
        avatar.setMaxWidth(150);
        avatar.setFixedCellSize(70);
        ListView<String> username = new ListView<>();
        username.setMaxWidth(150);
        username.setFixedCellSize(70);
        ListView<Integer> highScore = new ListView<>();
        highScore.setMaxWidth(100);
        highScore.setFixedCellSize(70);

        List<User> sortedUsers = new ArrayList<>(User.sortUsers());

        for(int i = 0; i < sortedUsers.size(); i++){
            rank.getItems().add(i+1);
            username.getItems().add(sortedUsers.get(i).getUsername());
            Circle circle = new Circle(30, new ImagePattern(new Image(sortedUsers.get(i).getAvatar())));
            circle.setOnDragDetected(mouseEvent -> {
                ImagePattern imagePattern = (ImagePattern) circle.getFill();
                path = imagePattern.getImage().getUrl();
                System.out.println(path);
            });
            avatar.getItems().add(circle);
            highScore.getItems().add(sortedUsers.get(i).getHighScore());
        }

        HBox hBox = new HBox(rank, avatar, username, highScore);
        hBox.setAlignment(Pos.CENTER);
        hBox.setMaxHeight(300);

        HBox main = new FXMLLoader(Scoreboard.class.getResource("/view/scoreboard.fxml")).load();
        main.getChildren().add(hBox);
        Scene scene = new Scene(main);
        stage.setScene(scene);
        stage.setTitle("Scoreboard");
        stage.show();

        bind(rank, avatar, username, highScore);
    }

    @FXML
    public void initialize(){
        currentPlayerAvatar.setFill(new ImagePattern(new Image(Stronghold.getCurrentUser().getAvatar())));
        currentPlayerUsername.setText(Stronghold.getCurrentUser().getUsername());
        currentPlayerScore.setText(Integer.toString(Stronghold.getCurrentUser().getHighScore()));
    }

    public void profileMenu() throws Exception{
        new ProfileMenu().start(stage);
    }

    public static void bind(ListView<Integer> rank, ListView<Circle> avatar, ListView<String> username, ListView<Integer> highScore){
        Node n1 = rank.lookup(".scroll-bar");
        if (n1 instanceof ScrollBar) {
            final ScrollBar bar1 = (ScrollBar) n1;
            Node n2 = highScore.lookup(".scroll-bar");
            if (n2 instanceof ScrollBar) {
                final ScrollBar bar2 = (ScrollBar) n2;
                bar1.valueProperty().bindBidirectional(bar2.valueProperty());
            }
            Node n3 = username.lookup(".scroll-bar");
            if(n3 instanceof ScrollBar){
                final ScrollBar bar3 = (ScrollBar) n3;
                bar1.valueProperty().bindBidirectional(bar3.valueProperty());
            }
            Node n4 = avatar.lookup(".scroll-bar");
            if(n4 instanceof ScrollBar){
                final ScrollBar bar4 = (ScrollBar) n4;
                bar1.valueProperty().bindBidirectional(bar4.valueProperty());
            }
        }
    }

    public void changeAvatar() {
        System.out.println("yes");
        ProfileMenuController.changeAvatar(path);
        currentPlayerAvatar.setFill(new ImagePattern(new Image(path)));
    }
}