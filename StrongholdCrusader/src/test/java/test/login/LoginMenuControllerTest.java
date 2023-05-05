package test.login;

import org.example.controller.LoginMenuController;
import org.example.view.enums.messages.LoginMenuMessages;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)

public class LoginMenuControllerTest {

    @Test
    public void testForUserExists() {

        LoginMenuController loginMenuController = Mockito.mock(LoginMenuController.class);
        LoginMenuMessages response= loginMenuController.login("mehrazin002","password",false);
        Assertions.assertEquals(LoginMenuMessages.USERNAME_DOESNT_EXIST,response);

    }
    @Test
    public void testForWrongPassword() {
        // TODO: how to test it here,Ask @Rozhin
    }

    @Test
    public void testForForgetPasswordWrongSecurityAnswer(){

        LoginMenuController loginMenuController = Mockito.mock(LoginMenuController.class);
        LoginMenuMessages response= loginMenuController.forgetPassword("mehrazin001","answer","newPassword");
        Assertions.assertEquals(LoginMenuMessages.SECURITY_ANSWER_WRONG,response);
    }
    @Test
    public void testForForgetPasswordLength(){

        LoginMenuController loginMenuController = Mockito.mock(LoginMenuController.class);

        LoginMenuMessages response = loginMenuController.forgetPassword
                ("mehrazin001","Father's name","pass");
        Assertions.assertEquals(LoginMenuMessages.SHORT_PASSWORD,response);
    }
    @Test
    public void testForgetPasswordLowerCaseUse() {
        LoginMenuController loginMenuController = Mockito.mock(LoginMenuController.class);

        LoginMenuMessages response = loginMenuController.forgetPassword("mehrazin001","Father's name","PASSWORD");
        Assertions.assertEquals(LoginMenuMessages.NO_LOWERCASE_LETTER, response);
    }
    @Test
    public void testForgetPasswordUpperCaseUse() {
        LoginMenuController loginMenuController = Mockito.mock(LoginMenuController.class);

        LoginMenuMessages response = loginMenuController.forgetPassword("mehrazin001","Father's name","password");
        Assertions.assertEquals(LoginMenuMessages.NO_UPPERCASE_LETTER, response);
    }


    @Test
    public void testForgetPasswordContainsNumber() {
        LoginMenuController loginMenuController = Mockito.mock(LoginMenuController.class);

        LoginMenuMessages response= loginMenuController.forgetPassword("mehrazin001","Father's name","PASSword");
        Assertions.assertEquals(LoginMenuMessages.NO_NUMBER, response);
    }

    @Test
    public void testForgetPasswordForSpecialCharacters() {
        LoginMenuController loginMenuController = Mockito.mock(LoginMenuController.class);

        LoginMenuMessages response= LoginMenuController.forgetPassword("mehrazin001","Father's name","PASSword123");
        Assertions.assertEquals(LoginMenuMessages
                .NO_SPECIAL_CHARACTER, response);
    }


}
