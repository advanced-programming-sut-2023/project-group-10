package model;

public class Stronghold {
    private static User currentUser;
    private static Battle currentBattle;

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User newUser) {
        currentUser = newUser;
    }

    public static Battle getCurrentBattle() {
        return currentBattle;
    }

    public static void setCurrentBattle(Battle newBattle) {
        currentBattle = newBattle;
    }
}