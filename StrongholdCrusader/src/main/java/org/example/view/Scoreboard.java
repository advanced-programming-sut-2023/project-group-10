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
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.model.Stronghold;
import org.example.model.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Scoreboard extends Application {
    private static Stage stage;
    public Circle currentPlayerAvatar;
    public Text currentPlayerUsername;
    public Text currentPlayerScore;


    @Override
    public void start(Stage stage) throws Exception {
        Scoreboard.stage = stage;

        ListView<Integer> rank = new ListView<>();
        rank.setMaxWidth(70);
        ListView<Circle> avatar = new ListView<>();
        avatar.setMaxWidth(150);
        ListView<String> username = new ListView<>();
        username.setMaxWidth(150);
        ListView<Integer> highScore = new ListView<>();
        highScore.setMaxWidth(100);

        List<User> sortedUsers = new ArrayList<>();
        Collections.copy(sortedUsers, User.sortUsers());

        for(int i = 1; i <= sortedUsers.size(); i++){
            rank.getItems().add(i);
            username.getItems().add(sortedUsers.get(i-1).getUsername());
            avatar.getItems().add(new Circle(30, new ImagePattern(new Image(sortedUsers.get(i-1).getAvatar()))));
            highScore.getItems().add(sortedUsers.get(i-1).getHighScore());
        }

        /*for(int i = 1; i <= 50; i++){
            rank.getItems().add(i-1);
            username.getItems().add(Integer.toString(i));
            highScore.getItems().add(i+1);
        }*/

        HBox hBox = new HBox(rank, avatar, username, highScore);
        hBox.setAlignment(Pos.CENTER);
        hBox.setMaxHeight(300);

        HBox main = new FXMLLoader(Scoreboard.class.getResource("/view/scoreboard.fxml")).load();
        main.getChildren().add(hBox);
        Scene scene = new Scene(main);
        stage.setScene(scene);
        stage.setTitle("Scoreboard");
        stage.show();

        bind(rank, username, highScore);
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

    public static void bind(ListView<Integer> rank, ListView<String> username, ListView<Integer> highScore){
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
        }
    }
}
