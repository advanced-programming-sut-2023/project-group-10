package test.signup;

import org.example.view.SignupMenu;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Scanner;

@ExtendWith(MockitoExtension.class)
public class SignUpViewTest {
    @Test
    public void testInvalidOptionRegisterOption() {
        Scanner scanner = Mockito.mock(Scanner.class);
        SignupMenu signupMenu = Mockito.mock(SignupMenu.class);
        String response = signupMenu.register(scanner, "user create -u username -p password -c passwordConfirmation –e email -n nickname -s slogan -m moto");
        Assertions.assertEquals("invalid option", response);
    }

    @Test
    public void testMissingRegisterOption() {
        Scanner scanner = Mockito.mock(Scanner.class);
        SignupMenu signupMenu = Mockito.mock(SignupMenu.class);
        String response = signupMenu.register(scanner, "user create -u username -p password -c passwordConfirmation  -n nickname -s slogan");
        Assertions.assertEquals("missing option", response);

    }

    @Test
    public void testNoSlogan() {
        Scanner scanner = Mockito.mock(Scanner.class);
        SignupMenu signupMenu = Mockito.mock(SignupMenu.class);
        String response = signupMenu.register(scanner, "user create -u username@@ -p password -c passwordConfirmation -e email -n nickname ");
        Assertions.assertEquals("Invalid format for username", response);

    }

    @Test
    public void testEmptyFieldRegisterOption() {
        Scanner scanner = Mockito.mock(Scanner.class);
        SignupMenu signupMenu = Mockito.mock(SignupMenu.class);
        String response = signupMenu.register(scanner, "user create -u  -p password -c passwordConfirmation –e email -n nickname -s slogan");
        Assertions.assertEquals("empty field", response);

    }

    @Test
    public void testPasswordLength() {
        Scanner scanner = Mockito.mock(Scanner.class);
        SignupMenu signupMenu = Mockito.mock(SignupMenu.class);
        String response = signupMenu.register(scanner, "user create -u username -p pass -c passwordConfirmation -e email -n nickname");
        Assertions.assertEquals("Short password!,You must provide at least 6 characters!", response);
    }

    @Test
    public void testPasswordUpperCase() {
        Scanner scanner = Mockito.mock(Scanner.class);
        SignupMenu signupMenu = Mockito.mock(SignupMenu.class);
        String response = signupMenu.register(scanner, "user create -u username -p password -c passwordConfirmation -e email -n nickname");
        Assertions.assertEquals("Your password must contain an uppercase letter!", response);
    }

    @Test
    public void testPasswordLowerCase() {
        Scanner scanner = Mockito.mock(Scanner.class);
        SignupMenu signupMenu = Mockito.mock(SignupMenu.class);
        String response = signupMenu.register(scanner, "user create -u username -p PASSWORD -c passwordConfirmation -e email -n nickname");
        Assertions.assertEquals("Your password must contain a lowercase letter!", response);
    }

    @Test
    public void testPasswordDigit() {
        Scanner scanner = Mockito.mock(Scanner.class);
        SignupMenu signupMenu = Mockito.mock(SignupMenu.class);
        String response = signupMenu.register(scanner, "user create -u username -p PASSword -c passwordConfirmation -e email -n nickname");
        Assertions.assertEquals("Your password must contain at least one digit!", response);
    }

    @Test
    public void testPasswordSpecialCharacter() {
        Scanner scanner = Mockito.mock(Scanner.class);
        SignupMenu signupMenu = Mockito.mock(SignupMenu.class);
        String response = signupMenu.register(scanner, "user create -u username -p PASSword123 -c passwordConfirmation -e email -n nickname");
        Assertions.assertEquals("Your password must contain at least one special character!", response);
    }

    @Test
    public void testEmailFormat() {

        SignupMenu signupMenu = Mockito.mock(SignupMenu.class);
        Scanner scanner = Mockito.mock(Scanner.class);
        String response = SignupMenu.register(scanner, "user create -u username -p P@SSword123 -c P@SSword123 -e email -n nickname");

        Assertions.assertEquals("Invalid format for email", response);
    }

    @Test
    public void testTakenEmail() {

        SignupMenu signupMenu = Mockito.mock(SignupMenu.class);
        Scanner scanner = Mockito.mock(Scanner.class);
        String response = SignupMenu.register(scanner, "user create -u username -p P@SSword123 -c P@SSword123 -e rozhin@mail.com -n nickname");
        Assertions.assertEquals("There is a user who is registered with this email address!", response);
    }

    @Test
    public void testPasswordConfirmation() {

        SignupMenu signupMenu = Mockito.mock(SignupMenu.class);
        Scanner scanner = Mockito.mock(Scanner.class);
        String response = SignupMenu.register(scanner, "user create -u username -p P@SSword123 -c confirmation -e rozhin@mail.com -n nickname");
        Assertions.assertEquals("passwords doesn't match, try to signup again", response);
    }

}
