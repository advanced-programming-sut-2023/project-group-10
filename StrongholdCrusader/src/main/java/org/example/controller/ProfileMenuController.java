package org.example.controller;

import org.example.model.Stronghold;
import org.example.model.utils.CheckFormat;
import org.example.view.enums.messages.ProfileMenuMessages;

import java.util.Scanner;

//TODO all functions needed to be checked with Mehrazin

public class ProfileMenuController {
    public static ProfileMenuMessages changeUsername(String username){
        if(username == null)
            return ProfileMenuMessages.NO_USERNAME_PROVIDED;

        if(!CheckFormat.checkUsernameFormat(username))
            return ProfileMenuMessages.INVALID_USERNAME;

        //TODO add username exists

        else{
            Stronghold.getCurrentUser().setUsername(username);
            return ProfileMenuMessages.CHANGE_USERNAME_SUCCESSFUL;
        }
    }

    public static ProfileMenuMessages changeNickname(String nickname){
        if(nickname == null)
            return ProfileMenuMessages.NO_NICKNAME_PROVIDED;

        else{
            Stronghold.getCurrentUser().setNickname(nickname);
            return ProfileMenuMessages.CHANGE_NICKNAME_SUCCESSFUL;
        }
    }

    public static ProfileMenuMessages changePassword(String oldPassword, String newPassword){
        if(oldPassword == null || newPassword == null)
            return ProfileMenuMessages.NO_PASSWORD_PROVIDED;

        if(!CheckFormat.checkPasswordFormat(newPassword))
            return ProfileMenuMessages.WEAK_PASSWORD;

        if(!Stronghold.getCurrentUser().getPassword().equals(oldPassword))
            return ProfileMenuMessages.INCORRECT_PASSWORD; //TODO check with mehrazin

        while(oldPassword.equals(newPassword)){
            System.out.println("Please enter a new password!");
            newPassword = new Scanner(System.in).nextLine();
        }

        Stronghold.getCurrentUser().setPassword(newPassword);
        return ProfileMenuMessages.CHANGE_PASSWORD_SUCCESSFUL;
    }

    public static ProfileMenuMessages changeEmail(String email){
        if(email == null)
            return ProfileMenuMessages.NO_EMAIL_PROVIDED;

        if(!CheckFormat.checkEmailFormat(email))
            return ProfileMenuMessages.INVALID_EMAIL;

        //TODO add email exists function

        else{
            Stronghold.getCurrentUser().setEmail(email);
            return ProfileMenuMessages.CHANGE_EMAIL_SUCCESSFUL;
        }
    }

    public static ProfileMenuMessages changeSlogan(String slogan){
        if(slogan == null)
            return ProfileMenuMessages.NO_SLOGAN_PROVIDED;

        else{
            Stronghold.getCurrentUser().setSlogan(slogan);
            return ProfileMenuMessages.CHANGE_SLOGAN_SUCCESSFUL;
        }
    }
}
