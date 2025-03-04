package com.example.streamingapp.model;

import java.util.List;

public class GetMoviesResponse {
    private String category_name;
    private List<Movie> movies;

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}
