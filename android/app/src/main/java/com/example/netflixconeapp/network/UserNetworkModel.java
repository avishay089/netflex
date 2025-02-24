package com.example.netflixconeapp.network;

public class UserNetworkModel {
    private String email;
    private String name;
    private String username;
    private String tz; // ID Number
    private String password;
    private boolean isAdmin;
    private String profilePicture;

    public UserNetworkModel(String email, String name, String username, String tz, String password,
                            boolean isAdmin, String profilePicture) {
        this.email = email;
        this.name = name;
        this.username = username;
        this.tz = tz;
        this.password = password;
        this.isAdmin = isAdmin;
        this.profilePicture = profilePicture;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTz() {
        return tz;
    }

    public void setTz(String tz) {
        this.tz = tz;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    // Constructor, Getters and Setters...
}
