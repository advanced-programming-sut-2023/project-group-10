package test.signup;

import org.example.controller.SignupMenuController;
import org.example.model.DataBase;
import org.example.view.enums.messages.SignupMenuMessages;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)

public class SignUpMenuControllerTest {
    @Mock
    DataBase dataBase;
    @InjectMocks
    SignupMenuController signupMenuController;

    @Test
    public void testUsernameFormat() {
        signupMenuController = Mockito.mock(SignupMenuController.class);

        SignupMenuMessages signupMenuMessage = SignupMenuController.createUser("user%name", "password",
                "password", "email", "nickname");
        Assertions.assertEquals(SignupMenuMessages.INVALID_USERNAME_FORMAT, signupMenuMessage);
    }

    @Test
    public void testNicknameFormat() {
        signupMenuController = Mockito.mock(SignupMenuController.class);

        SignupMenuMessages signupMenuMessage = SignupMenuController.createUser("username", "password",
                "confirmation", "email", "NICK@name");
        Assertions.assertEquals(SignupMenuMessages.INVALID_NICKNAME_FORMAT, signupMenuMessage);

    }

    @Test
    public void testPasswordLength() {
        signupMenuController = Mockito.mock(SignupMenuController.class);

        SignupMenuMessages signupMenuMessage = SignupMenuController.createUser("username", "aaaa",
                "aaaa", "email.email@email.email", "nickname");
        Assertions.assertEquals(SignupMenuMessages.SHORT_PASSWORD, signupMenuMessage);
    }

    @Test
    public void testPasswordUpperCaseUse() {
        signupMenuController = Mockito.mock(SignupMenuController.class);

        SignupMenuMessages signupMenuMessage = SignupMenuController.createUser("username", "uppercase",
                "confirmation", "email", "nickname");
        Assertions.assertEquals(SignupMenuMessages.NO_UPPERCASE_LETTER, signupMenuMessage);
    }

    @Test
    public void testPasswordLowerCaseUse() {

        signupMenuController = Mockito.mock(SignupMenuController.class);

        SignupMenuMessages signupMenuMessage = SignupMenuController.createUser("username", "LOWERCASE",
                "confirmation", "email", "nickname");
        Assertions.assertEquals(SignupMenuMessages.NO_LOWERCASE_LETTER, signupMenuMessage);
    }

    @Test
    public void testPasswordContainsNumber() {
        signupMenuController = Mockito.mock(SignupMenuController.class);

        SignupMenuMessages signupMenuMessage = SignupMenuController.createUser("username", "lower&Upper",
                "confirmation", "email", "nickname");
        Assertions.assertEquals(SignupMenuMessages.NO_NUMBER, signupMenuMessage);
    }

    @Test
    public void testPasswordForSpecialCharacters() {
        signupMenuController = Mockito.mock(SignupMenuController.class);

        SignupMenuMessages signupMenuMessage = SignupMenuController.createUser("username1", "lowerUpper123",
                "confirmation", "email", "nickname");
        Assertions.assertEquals(SignupMenuMessages.NO_SPECIAL_CHARACTER, signupMenuMessage);
    }

    @Test
    public void testPasswordConfirmation() {
        signupMenuController = Mockito.mock(SignupMenuController.class);

        SignupMenuMessages signupMenuMessage = SignupMenuController.createUser("username", "lower&Upper123",
                "confirmation",
                "email", "nickname");
        Assertions.assertEquals(SignupMenuMessages.WRONG_PASSWORD_CONFIRMATION, signupMenuMessage);
    }

    @Test
    public void testForSuccessfulCreation() {
        signupMenuController = Mockito.mock(SignupMenuController.class);

        SignupMenuMessages signupMenuMessage = SignupMenuController.createUser("mehrazin", "mehrAZIN@123",
                "mehrAZIN@123", "mehrazin.m@gmail.com", "mehrazin");
        Assertions.assertEquals(SignupMenuMessages.SHOW_QUESTIONS, signupMenuMessage);

    }

    @Test
    public void testEmailFormat1() {
        signupMenuController = Mockito.mock(SignupMenuController.class);

        SignupMenuMessages signupMenuMessage = SignupMenuController.createUser("mehrazin", "mehrAZIN@123",
                "mehrAZIN@123", "mehrazin.m@gmail", "mehrazin");
        Assertions.assertEquals(SignupMenuMessages.INVALID_EMAIL_FORMAT, signupMenuMessage);
    }

    @Test
    public void testEmailFormat2() {
        signupMenuController = Mockito.mock(SignupMenuController.class);

        SignupMenuMessages signupMenuMessage = SignupMenuController.createUser("mehrazin", "mehrAZIN@123",
                "mehrAZIN@123", "@gmail.com", "mehrazin");
        Assertions.assertEquals(SignupMenuMessages.INVALID_EMAIL_FORMAT, signupMenuMessage);
    }

    @Test
    public void testBoundsForQuestion() {
        signupMenuController = Mockito.mock(SignupMenuController.class);

        SignupMenuMessages signupMenuMessage = SignupMenuController.pickSecurityQuestionAndCreateUser
                ("10", "answer", "confirm",
                        "username", "password", "nickname", "slogan", "email");
        Assertions.assertEquals(SignupMenuMessages.NUMBER_OUT_OF_BOUNDS, signupMenuMessage);
    }

    @Test
    public void testConfirmationForQuestion() throws Exception {
        signupMenuController = Mockito.mock(SignupMenuController.class);


        SignupMenuMessages signupMenuMessage = SignupMenuController.pickSecurityQuestionAndCreateUser
                ("1", "answer", "confirm",
                        "username", "password", "nickname", "slogan", "email");
        Assertions.assertEquals(SignupMenuMessages.REENTER_ANSWER, signupMenuMessage);
    }

    @Test
    public void testTakenEmail() throws Exception {
        signupMenuController = Mockito.mock(SignupMenuController.class);
        SignupMenuController.pickSecurityQuestionAndCreateUser
                ("1", "Father's name", "Father's name",
                        "rozhin001", "R0zhin001", "RozhTagh", "noSlogan", "rozhin@mail.com");
        SignupMenuMessages signupMenuMessage = SignupMenuController.createUser
                ("rozhin002", "valid@Passw0rd", "valid@Passw0rd", "rozhin@mail.com", "nickname");
        Assertions.assertEquals(SignupMenuMessages.EMAIL_EXISTS, signupMenuMessage);
    }

}
