package com.example.netflixconeapp.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofit;
    private static final String BASE_URL = "http://localhost:5000/api/";

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)  // Base API URL
                    .addConverterFactory(GsonConverterFactory.create())  // JSON to Java Object conversion
                    .build();
        }
        return retrofit;
    }
}
