package com.example.netflixconeapp.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import okhttp3.OkHttpClient;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class ApiProvider {

    // Base URL for your API
    private static final String BASE_URL = "http://localhost:5000/";

    // Retrofit instance
    private static Retrofit retrofit;

    // Centralized method to get the Retrofit instance
    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            // Set up OkHttpClient to handle additional configurations if needed
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            // Custom request processing (e.g., adding headers)
                            Request originalRequest = chain.request();
                            Request newRequest = originalRequest.newBuilder()
                                    .header("Content-Type", "application/json") // Default content type
                                    .build();
                            return chain.proceed(newRequest);
                        }
                    })
                    .build();

            // Initialize Retrofit with base URL and Gson converter
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client) // Attach OkHttpClient
                    .addConverterFactory(GsonConverterFactory.create()) // JSON (GSON) parser
                    .build();
        }
        return retrofit;
    }

    // Generic API Service Getter (Creates API service for any API interface)
    public static <T> T createService(Class<T> serviceClass) {
        return getRetrofitInstance().create(serviceClass);
    }
}
