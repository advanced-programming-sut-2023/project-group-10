package test;

import org.example.controller.SignupMenuController;
import org.example.view.enums.messages.SignupMenuMessages;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)

public class SignUpMenuTest {

    @Test
    public void testUsernameFormat() {
        SignupMenuController signupMenuController = Mockito.mock(SignupMenuController.class);

        SignupMenuMessages signupMenuMessage = signupMenuController.createUser("user%name", "password",
                "password", "email", "nickname");
        Assertions.assertEquals(SignupMenuMessages.INVALID_USERNAME_FORMAT, signupMenuMessage);
    }

    @Test
    public void testNicknameFormat() {
        SignupMenuController signupMenuController = Mockito.mock(SignupMenuController.class);

        SignupMenuMessages signupMenuMessage = signupMenuController.createUser("username", "password",
                "confirmation", "email", "NICK@name");
        Assertions.assertEquals(SignupMenuMessages.INVALID_NICKNAME_FORMAT, signupMenuMessage);

    }

    @Test
    public void testPasswordLength() {
        SignupMenuController signupMenuController = Mockito.mock(SignupMenuController.class);

        SignupMenuMessages signupMenuMessage = signupMenuController.createUser("username", "aaaa",
                "aaaa", "email.email@email.email", "nickname");
        Assertions.assertEquals(SignupMenuMessages.SHORT_PASSWORD, signupMenuMessage);
    }

    @Test
    public void testPasswordUpperCaseUse() {
        SignupMenuController signupMenuController = Mockito.mock(SignupMenuController.class);

        SignupMenuMessages signupMenuMessage = signupMenuController.createUser("username", "uppercase",
                "confirmation", "email", "nickname");
        Assertions.assertEquals(SignupMenuMessages.NO_UPPERCASE_LETTER, signupMenuMessage);
    }

    @Test
    public void testPasswordLowerCaseUse() {
        SignupMenuController signupMenuController = Mockito.mock(SignupMenuController.class);

        SignupMenuMessages signupMenuMessage = signupMenuController.createUser("username", "LOWERCASE",
                "confirmation", "email", "nickname");
        Assertions.assertEquals(SignupMenuMessages.NO_LOWERCASE_LETTER, signupMenuMessage);
    }

    @Test
    public void testPasswordContainsNumber() {
        SignupMenuController signupMenuController = Mockito.mock(SignupMenuController.class);

        SignupMenuMessages signupMenuMessage = signupMenuController.createUser("username", "lower&Upper",
                "confirmation", "email", "nickname");
        Assertions.assertEquals(SignupMenuMessages.NO_NUMBER, signupMenuMessage);
    }

    @Test
    public void testPasswordForSpecialCharacters() {
        SignupMenuController signupMenuController = Mockito.mock(SignupMenuController.class);

        SignupMenuMessages signupMenuMessage = signupMenuController.createUser("username1", "lowerUpper123",
                "confirmation", "email", "nickname");
        Assertions.assertEquals(SignupMenuMessages.NO_SPECIAL_CHARACTER, signupMenuMessage);
    }

    @Test
    public void testPasswordConfirmation() {
        SignupMenuController signupMenuController = Mockito.mock(SignupMenuController.class);

        SignupMenuMessages signupMenuMessage = signupMenuController.createUser("username", "lower&Upper123",
                "confirmation",
                "email", "nickname");
        Assertions.assertEquals(SignupMenuMessages.WRONG_PASSWORD_CONFIRMATION, signupMenuMessage);
    }

    @Test
    public void testForSuccessfulCreation() {
        SignupMenuController signupMenuController = Mockito.mock(SignupMenuController.class);

        SignupMenuMessages signupMenuMessage = signupMenuController.createUser("mehrazin", "mehrAZIN@123",
                "mehrAZIN@123", "mehrazin.m@gmail.com", "mehrazin");
        Assertions.assertEquals(SignupMenuMessages.SHOW_QUESTIONS, signupMenuMessage);

    }
    //TODO: find a way to check taken username and taken email

    @Test
    public void testEmailFormat1() {
        SignupMenuController signupMenuController = Mockito.mock(SignupMenuController.class);

        SignupMenuMessages signupMenuMessage = signupMenuController.createUser("mehrazin", "mehrAZIN@123",
                "mehrAZIN@123", "mehrazin.m@gmail", "mehrazin");
        Assertions.assertEquals(SignupMenuMessages.INVALID_EMAIL_FORMAT, signupMenuMessage);
    }

    @Test
    public void testEmailFormat2() {
        SignupMenuController signupMenuController = Mockito.mock(SignupMenuController.class);

        SignupMenuMessages signupMenuMessage = signupMenuController.createUser("mehrazin", "mehrAZIN@123",
                "mehrAZIN@123", "@gmail.com", "mehrazin");
        Assertions.assertEquals(SignupMenuMessages.INVALID_EMAIL_FORMAT, signupMenuMessage);
    }

    @Test
    public void testBoundsForQuestion() {
        SignupMenuController signupMenuController = Mockito.mock(SignupMenuController.class);

        SignupMenuMessages signupMenuMessage = signupMenuController.pickSecurityQuestionAndCreateUser
                ("10", "answer", "confirm",
                        "username", "password", "nickname", "slogan", "email");
        Assertions.assertEquals(SignupMenuMessages.NUMBER_OUT_OF_BOUNDS, signupMenuMessage);
    }

    @Test
    public void testConfirmationForQuestion() throws Exception {
        SignupMenuController signupMenuController = Mockito.mock(SignupMenuController.class);


        SignupMenuMessages signupMenuMessage = signupMenuController.pickSecurityQuestionAndCreateUser
                ("1", "answer", "confirm",
                        "username", "password", "nickname", "slogan", "email");
        Assertions.assertEquals(SignupMenuMessages.REENTER_ANSWER, signupMenuMessage);
    }




}
