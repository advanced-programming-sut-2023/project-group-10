package org.example.connection.Handlers;

import com.google.gson.Gson;
import org.example.connection.Connection;
import org.example.connection.Packet;
import org.example.connection.ServerToClientCommands;
import org.example.controller.LoginMenuController;
import org.example.model.SecurityQuestion;
import org.example.model.User;
import org.example.view.enums.messages.LoginMenuMessages;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LoginMenuHandler {
    private final Connection connection;
    private Packet receivedPacket;

    public LoginMenuHandler(Connection connection) {
        this.connection = connection;
    }

    public void setReceivedPacket(Packet receivedPacket) {
        this.receivedPacket = receivedPacket;
    }

    public void getSecurityQuestion() throws IOException {
        String username = receivedPacket.getAttribute().get("username");
        boolean isUsernameValid;
        String messageValue;
        if (User.getUserByUsername(username) != null) {
            isUsernameValid = true;
            String questionNumber = User.getUserByUsername(username).getQuestionNumber();
            messageValue = SecurityQuestion.getQuestionByNumber(questionNumber);
        } else {
            isUsernameValid = false;
            messageValue = "";
        }
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("is username valid", String.valueOf(isUsernameValid));
        hashMap.put("message", messageValue);
        Packet toBeSent = new Packet(ServerToClientCommands.GET_SECURITY_QUESTION.getCommand(), hashMap);
        connection.sendPacket(toBeSent);
    }

    public void tryToChangePassword() throws IOException {
        // password format has been checked before
        String username = receivedPacket.getAttribute().get("username");
        String answer = receivedPacket.getAttribute().get("answer");
        String newPassword = receivedPacket.getAttribute().get("new password");
        boolean isSuccessful = User.checkSecurityAnswer(username, answer);
        if (isSuccessful) User.getUserByUsername(username).setPassword(newPassword);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("is successful", String.valueOf(isSuccessful));
        Packet toBeSent = new Packet(ServerToClientCommands.TRY_TO_CHANGE_PASSWORD.getCommand(), hashMap);
        connection.sendPacket(toBeSent);
    }

    public void login() throws IOException {
        // stay logged in is handled on the client side
        // TODO: add online and offline
        String username = receivedPacket.getAttribute().get("username");
        String password = receivedPacket.getAttribute().get("password");
        User userObject;
        String messageValue;
        LoginMenuMessages message = LoginMenuController.login(username, password);
        if (message == LoginMenuMessages.USERNAME_DOESNT_EXIST) {
            userObject = null;
            messageValue = "username does not exist";
        } else if (message == LoginMenuMessages.WRONG_PASSWORD) {
            userObject = null;
            messageValue = "wrong password";
        } else {
            userObject = User.getUserByUsername(username);
            messageValue = "";
        }
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("user object", userObjectToJson(userObject));
        hashMap.put("message", messageValue);
        Packet toBeSent = new Packet(ServerToClientCommands.LOGIN.getCommand(), hashMap);
        connection.sendPacket(toBeSent);
    }

    private String userObjectToJson(User user) {
        // null object serialized as empty string
        if (user == null) return "";
        return new Gson().toJson(user, User.class);
    }
}
