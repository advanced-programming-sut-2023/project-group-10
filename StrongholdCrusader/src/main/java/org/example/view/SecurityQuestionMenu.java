package org.example.view;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.example.controller.SignupMenuController;
import org.example.model.SecurityQuestion;

public class SecurityQuestionMenu extends Application {

    public RadioButton question1;
    public RadioButton question2;
    public RadioButton question3;
    public TextField answer;
    public Button submit;
    private String question;
    private static Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        SecurityQuestionMenu.stage = stage;
        BorderPane borderPane = new FXMLLoader(SecurityQuestionMenu.class.getResource("/view/securityQuestion.fxml")).load();
        Background background = new Background(setBackground("/images/backgrounds/background2.png"));
        borderPane.setBackground(background);

        Scene scene = new Scene(borderPane, 1390, 850);
        stage.setScene(scene);
        stage.setTitle("Security Question");
        stage.setFullScreen(true);
        stage.show();
    }

    @FXML
    public void initialize(){
        question1.setText(SecurityQuestion.getQuestionByNumber("1"));
        question2.setText(SecurityQuestion.getQuestionByNumber("2"));
        question3.setText(SecurityQuestion.getQuestionByNumber("3"));
    }

    public void question1() {
        answer.setVisible(true);
        submit.setVisible(true);
        question = "1";
    }

    public void question2() {
        answer.setVisible(true);
        submit.setVisible(true);
        question = "2";
    }

    public void question3() {
        answer.setVisible(true);
        submit.setVisible(true);
        question = "3";
    }

    public void submit() throws Exception{
        SignupMenuController.createUser(question, answer.getText(), DataBank.getUsername(),
                DataBank.getPassword(), DataBank.getNickname(), DataBank.getSlogan(), DataBank.getEmail());
        new LoginMenu().start(stage);
    }

    private BackgroundImage setBackground(String url){
        Image image = new Image(GameMenu.class.getResource(url).toExternalForm(), 1440 ,900, false, false);
        BackgroundImage backgroundImage = new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        return backgroundImage;
    }
}
