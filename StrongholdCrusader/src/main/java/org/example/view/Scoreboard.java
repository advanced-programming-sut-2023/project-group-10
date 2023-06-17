package org.example.view;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.example.model.User;


public class Scoreboard extends Application {
    private static Stage stage;
    public Pane pane;

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
        TableView table = new TableView<>();
        TableColumn<User, Integer> rank = new TableColumn<>("rank");
        rank.setCellValueFactory(new PropertyValueFactory<>("rank"));
        TableColumn<User, String> username = new TableColumn<>("Username");
        username.setCellValueFactory(new PropertyValueFactory<>("username"));
        TableColumn<User, Integer> highScore = new TableColumn<>("HighScore");
        highScore.setCellValueFactory(new PropertyValueFactory<>("highScore"));
        table.getColumns().addAll(rank, username, highScore);
        table.getItems().addAll(User.sortUsers());
        pane.getChildren().add(table);
        table.setMaxHeight(50);
    }
}
