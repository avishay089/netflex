package com.example.netflixconeapp;

import java.util.ArrayList;
import java.util.List;

public class MovieRepository {

    // Simulate a local data source (e.g., Room database)
    private static List<Movie> localMovies;

    // Constructor
    public MovieRepository() {
        // Initialize a static list of movies to simulate a local database
        localMovies = new ArrayList<>();
        localMovies.add(new Movie("1", "The Surface Crack", "A story of cracks and challenges.", R.drawable.sample_movie_thumbnail));
        localMovies.add(new Movie("2", "Mortar Gone Missing", "An exciting mystery-solving thriller.", R.drawable.sample_movie_thumbnail));
        localMovies.add(new Movie("3", "Cracks Unveiled", "Revealing the hidden truths of cracks.", R.drawable.sample_movie_thumbnail));
        localMovies.add(new Movie("4", "The Missing Mortar Chronicles", "A journey to find the lost mortars.", R.drawable.sample_movie_thumbnail));
    }

    /**
     * Method to fetch movies (Can be extended to fetch remote data in real implementation)
     */
    public static List<Movie> getMovies() {
        return localMovies;
    }

    /**
     * Simulates fetching remote data (e.g., from an API server).
     * Replace this method with an actual network call in a real app.
     */
    public List<Movie> fetchMoviesFromRemote() {
        // Simulating a possible remote server fetch delay
        List<Movie> remoteMovies = new ArrayList<>();
        remoteMovies.add(new Movie("5", "Concrete Adventures", "A heroic journey amidst a world of concrete.", R.drawable.sample_movie_thumbnail));
        remoteMovies.add(new Movie("6", "The Great Crack Escape", "An adventure to escape from imminent surface failure.", R.drawable.sample_movie_thumbnail));
        return remoteMovies;
    }

}
