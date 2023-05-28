package org.example.view;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.model.SecurityQuestion;

import java.util.Map;

public class SecurityQuestionMenu extends Application {
    @FXML
    public RadioButton question1;
    @FXML
    public RadioButton question2;
    @FXML
    public RadioButton question3;
    @FXML
    public VBox pane;
    @FXML
    public TextField answer;
    @FXML
    public Button submit;
    private String question;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(SecurityQuestionMenu.class.getResource("/view/securityQuestion.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 300, 300);
        stage.setScene(scene);
        stage.setTitle("Security Question");
        stage.show();
    }

    @FXML
    public void initialize(){
        question1.setText(SecurityQuestion.getQuestionByNumber("1"));
        question2.setText(SecurityQuestion.getQuestionByNumber("2"));
        question3.setText(SecurityQuestion.getQuestionByNumber("3"));
    }

    public void question1(ActionEvent actionEvent) {
        answer.setVisible(true);
        submit.setVisible(true);
        question = question1.getText();
    }

    public void question2(ActionEvent actionEvent) {
        answer.setVisible(true);
        submit.setVisible(true);
        question = question2.getText();
    }

    public void question3(ActionEvent actionEvent) {
        answer.setVisible(true);
        submit.setVisible(true);
        question = question3.getText();
    }

    public void submit(MouseEvent mouseEvent) {
    }
}
