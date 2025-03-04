package com.example.streamingapp.model;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("token")
    private String token;
    private boolean isAdmin;
    private String userId;
    private String profilePicture;
    private String userName;

    public LoginResponse() {
    }

    public LoginResponse(String userId, String token, boolean isAdmin, String profilePicture, String userName) {
        this.userId = userId;
        this.token = token;
        this.isAdmin = isAdmin;
        this.profilePicture = profilePicture;
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
