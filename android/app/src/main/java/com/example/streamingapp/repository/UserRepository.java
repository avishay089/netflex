package com.example.streamingapp.repository;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.streamingapp.data.local.AppDatabase;
import com.example.streamingapp.data.local.UserDao;
import com.example.streamingapp.data.remote.RetroInstance;
import com.example.streamingapp.data.remote.UserApiService;
import com.example.streamingapp.model.LoginCredentials;
import com.example.streamingapp.model.LoginResponse;
import com.example.streamingapp.model.User;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserRepository {
    private UserApiService apiService;
    private UserDao userDao;
    private final Context context;

    public UserRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        this.userDao = db.userDao();
        this.context = application;
        this.apiService = RetroInstance.getUserApi();
    }

    public Call<Void> registerUser(User user) {
        return apiService.register(user);
    }

    public Call<LoginResponse> loginUser(String username, String password) {

        LoginCredentials credentials = new LoginCredentials(username, password);
        return apiService.login(credentials);
    }
}
