package com.example.streamingapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.streamingapp.BuildConfig;
import com.example.streamingapp.R;
import com.example.streamingapp.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieSearchAdapter extends RecyclerView.Adapter<MovieSearchAdapter.MovieViewHolder>{

    private List<Movie> movies = new ArrayList<>();
    private final OnMovieTapListener listener;

    public MovieSearchAdapter(OnMovieTapListener listener) {
        this.listener = listener;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_search_item, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movies.get(position);
        holder.tvMovieName.setText(movie.getName());
        holder.tvMovieCategory.setText(movie.getCategory());
        holder.tvDescription.setText(movie.getDescription());
        holder.itemMovieView.setOnClickListener(v -> listener.onMovieItemTapListener(movie));
        String fullImageUrl = BuildConfig.BASE_URL + movie.getImageUrl() + ".png";

        Glide.with(holder.itemView.getContext())
                .load(fullImageUrl)
                .apply(new RequestOptions()
                        .centerCrop()
                        .placeholder(R.drawable.background)
                        .error(R.drawable.background))
                .into(holder.ivMovieBackground);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public interface OnMovieTapListener {
        void onMovieItemTapListener(Movie movie);
    }

    static class MovieViewHolder extends RecyclerView.ViewHolder {
        TextView tvMovieName, tvMovieCategory, tvDescription;
        LinearLayout itemMovieView;
        ImageView ivMovieBackground;
        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMovieName = itemView.findViewById(R.id.tvMovieItemName);
            tvMovieCategory = itemView.findViewById(R.id.tvMovieItemCategory);
            tvDescription = itemView.findViewById(R.id.tvMovieItemDescription);
            itemMovieView = itemView.findViewById(R.id.itemMovieView);
            ivMovieBackground = itemView.findViewById(R.id.ivMovieBackground);
        }
    }
}
