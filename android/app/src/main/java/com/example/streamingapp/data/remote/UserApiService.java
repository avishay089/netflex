package com.example.streamingapp.data.remote;

import com.example.streamingapp.model.LoginCredentials;
import com.example.streamingapp.model.LoginResponse;
import com.example.streamingapp.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserApiService {
    @POST("/api/tokens")
    Call<LoginResponse> login(@Body LoginCredentials credentials);

    @POST("/api/users")
    Call<Void> register(@Body User user);
}
