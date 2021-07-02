package model;

/**
 * User sind Benutzer die sich in die Oberfläche einloggen können
 */
public class User {
    private long caregiverID;
    private String username;
    private String password;

    public User(String username, String password, long caregiverID) {
        this.username = username;
        this.password = password;
        this.caregiverID = caregiverID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getCaregiverID() {
        return caregiverID;
    }

    public void setCaregiverID(long caregiverID) {
        this.caregiverID = caregiverID;
    }
}
