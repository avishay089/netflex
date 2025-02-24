package com.example.netflixconeapp;

import android.content.Intent;
import com.example.netflixconeapp.Movie ;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private List<Movie> movies;

    public MovieAdapter(List<Movie> movies) {
        this.movies = movies;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movies.get(position);
        holder.title.setText(movie.getTitle());
        holder.thumbnail.setImageResource(movie.getThumbnail());
        holder.itemView.setOnClickListener(v -> {
            // Open Movie Info screen
            Intent intent = new Intent(v.getContext(), MovieInfoActivity.class);
            intent.putExtra("MOVIE_TITLE", movie.getTitle());
            intent.putExtra("MOVIE_DESCRIPTION", movie.getDescription());
            intent.putExtra("MOVIE_THUMBNAIL", movie.getThumbnail());
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    static class MovieViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView thumbnail;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.movieTitle);
            thumbnail = itemView.findViewById(R.id.movieThumbnail);
        }
    }
}