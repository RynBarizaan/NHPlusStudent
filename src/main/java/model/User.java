package model;

/**
 * Only a User is able to log into the program
 */
public class User {
    private long caregiverID;
    private String username;
    private String password;

    /**
     *
     * @param username
     * @param password
     * @param caregiverID
     */
    public User(String username, String password, long caregiverID) {
        this.username = username;
        this.password = password;
        this.caregiverID = caregiverID;
    }

    /**
     *
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     *
     * @param username set new username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     *
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     *
     * @param password set new password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     *
     * @return caregiverID
     */
    public long getCaregiverID() {
        return caregiverID;
    }

    /**
     *
     * @param caregiverID set new caregiverID
     */
    public void setCaregiverID(long caregiverID) {
        this.caregiverID = caregiverID;
    }
}
