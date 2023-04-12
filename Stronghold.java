package main.java.model;

import java.util.ArrayList;

public class Stronghold {
    private User currentUser;
    // TODO: complete this, read from file!
    private final ArrayList<Trade> allTradeRequests=new ArrayList<>();

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }


}
