package edu.virginia.sde.reviews;

public class User {

    private static User instance;
    private int userId;
    private String username;


    private User() {}

    public static User getInstance() {
        if (instance == null) {
            instance = new User();
        }
        return instance;
    }

    public void setUser(int userId, String username) {
        this.userId = userId;
        this.username = username;
    }

    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public void clearSession() {
        userId = 0;
        username = null;
    }
}
