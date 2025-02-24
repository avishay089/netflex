package com.example.netflixconeapp;

import android.os.Handler;
import android.os.Looper;

public class LoginRepository {
    public interface LoginCallback {
        void onResponse(String token);
    }

    public void login(String username, String password, LoginCallback callback) {
        // Mock API call to authenticate user
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            if ("user".equals(username) && "password".equals(password)) {
                callback.onResponse("mock_jwt_token");
            } else {
                callback.onResponse(null);
            }
        }, 2000);
    }
}
