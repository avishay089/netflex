package com.example.netflixconeapp.network;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;



// Retrofit interface for User API
public interface UserApiService {

    // Signup endpoint for users
    @POST("api/users")
    Call<SignupResponse> signupUser(@Body UserNetworkModel userNetworkModel);
}



