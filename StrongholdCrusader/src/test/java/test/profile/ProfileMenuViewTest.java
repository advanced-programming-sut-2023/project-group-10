package test.profile;

import org.example.model.Stronghold;
import org.example.model.User;
import org.example.view.ProfileMenu;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
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
    public void testChangeUsernameUserExists(){

        Stronghold.setCurrentUser(User.getUserByUsername("mehrazin001"));
        ProfileMenu profileMenu = Mockito.mock(ProfileMenu.class);
        String response = profileMenu.changeUsername("profile change -u rozhin001");
        Stronghold.setCurrentUser(null);
        Assertions.assertEquals("Username already exists!", response);
    }
// TODO: Successful/Later
    @Test
    public void testChangeNicknameInvalidOption(){

        ProfileMenu profileMenu = Mockito.mock(ProfileMenu.class);
        String response = profileMenu.changeNickname("profile change -a alias");
        Assertions.assertEquals("invalid option", response);
    }


    @Test
    public void testChangeNicknameNotProvided(){
        ProfileMenu profileMenu = Mockito.mock(ProfileMenu.class);
        String response = profileMenu.changeNickname("profile change -n  ");
        Assertions.assertEquals("No nickname provided!", response);
    }
    @Test
    public void testChangeNicknameInvalidFormat(){
        String response = ProfileMenu.changeNickname("profile change -n  nick****! ");
        Assertions.assertEquals("Nickname format is invalid!", response);
    }

    @Test
    public void testChangeNicknameSuccessful(){

        Stronghold.setCurrentUser(User.getUserByUsername("mehrazin001"));
        String response = ProfileMenu.changeNickname("profile change -n Mehrazin.M");
        Stronghold.setCurrentUser(null);
        Assertions.assertEquals("Mehrazin.M",User.getUserByUsername("mehrazin001").getNickname());
    }


}
