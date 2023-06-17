package org.example.view;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import org.example.model.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Scoreboard extends Application {
    private static Stage stage;
    public Pane pane;
    public ListView rank;
    public ListView username;
    public ListView avatar;
    public ListView highScore;


    @Override
    public void start(Stage stage) throws Exception {
        Scoreboard.stage = stage;
        Pane pane = new FXMLLoader(Scoreboard.class.getResource("/view/scoreboard.fxml")).load();
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.setTitle("Scoreboard");
        stage.show();
    }

    @FXML
    public void initialize(){
        List<User> sortedUsers = new ArrayList<>();
        Collections.copy(sortedUsers, User.sortUsers());

        for(int i = 1; i <= sortedUsers.size(); i++){
            rank.getItems().add(i);
            username.getItems().add(sortedUsers.get(i-1).getUsername());
            avatar.getItems().add(new Circle(30, new ImagePattern(new Image(sortedUsers.get(i-1).getAvatar()))));
            highScore.getItems().add(sortedUsers.get(i-1).getHighScore());
        }

        Node n1 = rank.lookup(".scroll-bar");
        if (n1 instanceof ScrollBar) {
            final ScrollBar bar1 = (ScrollBar) n1;
            Node n2 = username.lookup(".scroll-bar");
            if (n2 instanceof ScrollBar) {
                final ScrollBar bar2 = (ScrollBar) n2;
                bar1.valueProperty().bindBidirectional(bar2.valueProperty());
            }
            Node n3 = avatar.lookup(".scroll-bar");
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
}
