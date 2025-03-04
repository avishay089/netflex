package com.example.streamingapp.data.remote;

import com.example.streamingapp.model.GetMoviesResponse;
import com.example.streamingapp.model.Movie;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface MovieApiService {

    @Headers("Authorization: Bearer {JWT}")
    @GET("/api/movies")
    Call<List<GetMoviesResponse>> getMovies(@Header("user_id") String userId);

    @POST("/api/movies")
    Call<Void> addMovie(@Header("user_id") String userId, @Body Movie movie);

    @GET("/api/movies/{id}")
    Call<Movie> getMovieDetails(@Header("user_id") String userId, @Path("id") String movieId);

    @PUT("/api/movies/{id}")
    Call<Void> updateMovie(@Header("user_id") String userId, @Path("id") String movieId, @Body Movie movie);

    @DELETE("/api/movies/{id}")
    Call<Void> deleteMovie(@Header("user_id") String userId, @Path("id") String movieId);

    @GET("/api/movies/search/{query}")
    Call<List<Movie>> searchMovies(@Header("user_id") String userId, @Path("query") String query);

    @GET("/api/movies/{id}/recommend")
    Call<Movie> getRecommend(@Header("user_id") String userId, @Path("id") String movieId);

    @POST("/api/movies/{id}/recommend")
    Call<Void> addRecommend(@Header("user_id") String userId, @Path("id") String movieId);
}
