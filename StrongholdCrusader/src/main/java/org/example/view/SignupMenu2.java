package org.example.view;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.model.utils.RandomGenerator;

public class SignupMenu2 extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        BorderPane borderPane = new FXMLLoader(SignupMenu2.class.getResource("/view/signupMenu.fxml")).load();

        VBox vBox = new VBox(20);
        vBox.setAlignment(Pos.CENTER);
        borderPane.setCenter(vBox);

        TextField username = new TextField();
        username.setMaxWidth(240);
        username.setPromptText("username");
        vBox.getChildren().add(username);

        PasswordField passwordField = new PasswordField();
        passwordField.setMaxWidth(240);
        passwordField.setPromptText("password");
        Button randomPassword = new Button("random");

        HBox passwordContainer = new HBox(15);
        passwordContainer.setAlignment(Pos.CENTER);
        passwordContainer.getChildren().addAll(passwordField, randomPassword);
        vBox.getChildren().add(passwordContainer);

        TextField nickname = new TextField();
        nickname.setMaxWidth(240);
        nickname.setPromptText("nickname");
        vBox.getChildren().add(nickname);

        TextField email = new TextField();
        email.setMaxWidth(240);
        email.setPromptText("email");
        vBox.getChildren().add(email);

        CheckBox hasSlogan = new CheckBox("create slogan");
        vBox.getChildren().add(hasSlogan);

        Button submit = new Button("submit");
        Button reset = new Button("reset");
        HBox submitContainer = new HBox(10, submit, reset);
        submitContainer.setAlignment(Pos.CENTER);
        vBox.getChildren().add(submitContainer);

        TextField slogan = new TextField();
        slogan.setMaxWidth(300);
        slogan.setPromptText("slogan");

        Button button = new Button("random");
        ComboBox<String> defaultSlogan = new ComboBox<>();
        defaultSlogan.setMaxWidth(240);
        defaultSlogan.setPromptText("slogan");
        for(String string : RandomGenerator.getSlogans()){
            defaultSlogan.getItems().add(string);
        }
        hasSlogan.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                HBox sloganContainer = new HBox(15);
                sloganContainer.setAlignment(Pos.CENTER);
                sloganContainer.getChildren().addAll(slogan, button);
                vBox.getChildren().remove(submitContainer);
                vBox.getChildren().addAll(sloganContainer, defaultSlogan);
                vBox.getChildren().add(submitContainer);
            }
        });

        Scene scene = new Scene(borderPane, 400, 600);
        stage.setScene(scene);
        stage.setTitle("signup menu");
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(SignupMenu2.class, args);
    }
}
