package com.example.streamingapp.data.remote;

import com.example.streamingapp.BuildConfig;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroInstance {

    public static String BASE_URL = BuildConfig.BASE_URL;
    private static Retrofit retrofit;

    public static Retrofit getRetroClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static UserApiService getUserApi() {
        return getRetroClient().create(UserApiService.class);
    }

    public static MovieApiService getMovieApi() {
        return getRetroClient().create(MovieApiService.class);
    }

    public static CategoryApiService getCategoryApi() {
        return getRetroClient().create(CategoryApiService.class);
    }
}
