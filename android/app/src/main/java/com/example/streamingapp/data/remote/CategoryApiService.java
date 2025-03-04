package com.example.streamingapp.data.remote;

import com.example.streamingapp.model.Category;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface CategoryApiService {

    @Headers("Authorization: Bearer {JWT}")
    @POST("/api/categories")
    Call<Void> addCategory(@Header("user_id") String userId, @Body Category category);

    @GET("/api/categories")
    Call<List<Category>> getCategories(@Header("user_id") String userId);

    @GET("/api/categories/{id}")
    Call<Category> getCategoryDetails(@Header("user_id") String userId, @Path("id") String categoryId);

    @PATCH("/api/categories/{id}")
    Call<Void> updateCategory(@Header("user_id") String userId, @Path("id") String categoryId, @Body Category category);

    @DELETE("/api/categories/{id}")
    Call<Void> deleteCategory(@Header("user_id") String userId, @Path("id") String categoryId);
}
