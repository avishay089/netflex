package com.example.streamingapp.data.local;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.streamingapp.model.LoginResponse;

public class TokenManager {
    private static final String PREF_NAME = "AuthPrefs";
    private static final String KEY_JWT_TOKEN = "jwt_token";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_USER_NAME = "user_name";
    private static final String KEY_IS_ADMIN = "is_admin";
    private static final String KEY_AVATAR = "profile_avatar";
    private final SharedPreferences prefs;

    public TokenManager(Context context) {
        this.prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void saveToken(String token) {
        prefs.edit().putString(KEY_JWT_TOKEN, token).apply();
    }
    public String getToken() {
        return prefs.getString(KEY_JWT_TOKEN, null);
    }

    public void deleteToken() {
        prefs.edit().remove(KEY_JWT_TOKEN).apply();
    }

    public boolean hasToken() {
        return getToken() != null;
    }

    public void saveUserInfo(LoginResponse response) {
        prefs.edit().putString(KEY_USER_ID, response.getUserId()).apply();
        prefs.edit().putString(KEY_USER_NAME, response.getUserName()).apply();
        prefs.edit().putBoolean(KEY_IS_ADMIN, response.isAdmin()).apply();
        prefs.edit().putString(KEY_AVATAR, response.getProfilePicture()).apply();
    }

    public LoginResponse getUserInfo() {
        return new LoginResponse(
                prefs.getString(KEY_USER_ID, null),
                prefs.getString(KEY_JWT_TOKEN, null),
                prefs.getBoolean(KEY_IS_ADMIN, false),
                prefs.getString(KEY_AVATAR, null),
                prefs.getString(KEY_USER_NAME, null)
        );
    }

    public void deleteUserInfo() {
        prefs.edit().remove(KEY_USER_ID).apply();
        prefs.edit().remove(KEY_IS_ADMIN).apply();
        prefs.edit().remove(KEY_AVATAR).apply();
        prefs.edit().remove(KEY_USER_NAME).apply();
    }
}
