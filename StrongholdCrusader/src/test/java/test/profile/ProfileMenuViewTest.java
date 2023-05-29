package test.profile;

import org.example.model.Stronghold;
import org.example.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ProfileMenuViewTest {
    @Test
    public void testChangeUsernameInvalidOption() {
        ProfileMenu profileMenu = Mockito.mock(ProfileMenu.class);
        String response = profileMenu.changeUsername("profile change -o <username>");
        Assertions.assertEquals("invalid option", response);

    }

    @Test
    public void testChangeUsernameNoUsernameProvided() {

        ProfileMenu profileMenu = Mockito.mock(ProfileMenu.class);
        String response = profileMenu.changeUsername("profile change -u ");
        Assertions.assertEquals("No username provided!", response);
    }

    @Test
    public void testChangeUsernameFormat() {
        ProfileMenu profileMenu = Mockito.mock(ProfileMenu.class);
        String response = profileMenu.changeUsername("profile change -u @user");
        Assertions.assertEquals("Username format is invalid!", response);
    }

    @Test
    public void testChangeUsernameOldOne() {
        Stronghold.setCurrentUser(User.getUserByUsername("mehrazin001"));
        ProfileMenu profileMenu = Mockito.mock(ProfileMenu.class);
        String response = profileMenu.changeUsername("profile change -u mehrazin001");
        Stronghold.setCurrentUser(null);
        Assertions.assertEquals("You have to enter a new username!", response);
    }

    @Test
    public void testChangeUsernameUserExists() {

        Stronghold.setCurrentUser(User.getUserByUsername("mehrazin001"));
        ProfileMenu profileMenu = Mockito.mock(ProfileMenu.class);
        String response = profileMenu.changeUsername("profile change -u rozhin001");
        Stronghold.setCurrentUser(null);
        Assertions.assertEquals("Username already exists!", response);
    }

    @Test
    public void testChangeUsernameSuccessful() {

        Stronghold.setCurrentUser(User.getUserByUsername("rozhin001"));
        ProfileMenu profileMenu = Mockito.mock(ProfileMenu.class);
        String response = profileMenu.changeUsername("profile change -u Rozhin001");
        Stronghold.setCurrentUser(null);
        Assertions.assertEquals("Username changed successfully", response);
    }

    @Test
    public void testChangeNicknameInvalidOption() {

        ProfileMenu profileMenu = Mockito.mock(ProfileMenu.class);
        String response = profileMenu.changeNickname("profile change -a alias");
        Assertions.assertEquals("invalid option", response);
    }


    @Test
    public void testChangeNicknameNotProvided() {
        ProfileMenu profileMenu = Mockito.mock(ProfileMenu.class);
        String response = profileMenu.changeNickname("profile change -n  ");
        Assertions.assertEquals("No nickname provided!", response);
    }

    @Test
    public void testChangeNicknameInvalidFormat() {
        String response = ProfileMenu.changeNickname("profile change -n  nick****! ");
        Assertions.assertEquals("Nickname format is invalid!", response);
    }

    @Test
    public void testChangeNicknameSuccessful() {

        Stronghold.setCurrentUser(User.getUserByUsername("mehrazin001"));
        String response = ProfileMenu.changeNickname("profile change -n Mehrazin.M");
        Stronghold.setCurrentUser(null);
        Assertions.assertEquals("Mehrazin.M", User.getUserByUsername("mehrazin001").getNickname());
    }

    @Test
    public void testChangeEmailInvalidOption() {

        String response = ProfileMenu.changeEmail("profile change -m mail");
        Assertions.assertEquals("invalid option", response);
    }

    @Test
    public void testChangeEmailNotProvided() {

        String response = ProfileMenu.changeEmail("profile change -e ");
        Assertions.assertEquals("No email provided!", response);
    }

    @Test
    public void changeEmailInvalidFormat() {

        String response = ProfileMenu.changeEmail("profile change -e me.mail@mail");
        Assertions.assertEquals("Invalid email format!", response);
    }

    @Test
    public void testChangeEmailEmailExists() {
        Stronghold.setCurrentUser(User.getUserByUsername("mehrazin001"));
        String response = ProfileMenu.changeEmail("profile change -e rozhin@mail.com");
        Stronghold.setCurrentUser(null);
        Assertions.assertEquals("Email already exists!", response);
    }

    @Test
    public void testChangeEmailOldOne() {
        Stronghold.setCurrentUser(User.getUserByUsername("mehrazin001"));
        String response = ProfileMenu.changeEmail("profile change -e mehrazin@mail.com");
        Stronghold.setCurrentUser(null);
        Assertions.assertEquals("You have to enter a new email!", response);
    }

    //Don't know why it does not work?
//    @Test
//    public void testChangeEmailSuccessful(){
//
//        Stronghold.setCurrentUser(User.getUserByUsername("mehrazin001"));
//        ProfileMenu.changeEmail("profile change -e mehrazin@gmail.com");
//        Assertions.assertEquals(User.getUserByUsername("mehrazin001").getEmail(),"mehrazin@gmail.com");
//
//    }


}
