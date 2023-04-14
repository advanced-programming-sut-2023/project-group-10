package controller;

import main.java.model.SecurityQuestion;
import main.java.view.enums.messages.SignUpMenuMessages;

import java.util.ArrayList;

public class SignupMenuController {
    public static void run() {

    }

    public static SignUpMenuMessages createUser(String username, String password, String passwordConfirmation, String email,
                                                String nickname, String slogan) {
        return null;
    }


    //TODO: decide abt the type
    public boolean checkEmailFormat(String emailAddress) {
        return true;
    }

    public String suggestNewUsername(String username) {
        return null;
    }

    public ArrayList<SecurityQuestion> showQuestions() {
        return null;
    }

    public static SignUpMenuMessages pickSecurityQuestion(String questionNumber, String answer, String answerConfirmation) {
        return null;
    }


}
