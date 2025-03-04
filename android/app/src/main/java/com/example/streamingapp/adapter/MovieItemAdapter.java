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

import java.util.List;

public class MovieItemAdapter extends RecyclerView.Adapter<MovieItemAdapter.MovieItemViewHolder> {

    private List<Movie> movies;
    private OnMovieItemClickListener clickListener;

    public MovieItemAdapter(List<Movie> movies, OnMovieItemClickListener clickListener) {
        this.movies = movies;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public MovieItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_search_item, parent, false);
        return new MovieItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieItemViewHolder holder, int position) {
        holder.bind(movies.get(position), clickListener);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }


    public interface OnMovieItemClickListener {
        void onMovieItemClick(Movie movie);
    }

    static class MovieItemViewHolder extends RecyclerView.ViewHolder {
        TextView tvMovieName, tvMovieCategory, tvDescription;
        LinearLayout itemMovieView;
        ImageView ivMovieBackground;
        public MovieItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMovieName = itemView.findViewById(R.id.tvMovieItemName);
            tvMovieCategory = itemView.findViewById(R.id.tvMovieItemCategory);
            tvDescription = itemView.findViewById(R.id.tvMovieItemDescription);
            itemMovieView = itemView.findViewById(R.id.itemMovieView);
            ivMovieBackground = itemView.findViewById(R.id.ivMovieBackground);
        }

        public void bind(Movie movie, OnMovieItemClickListener listener) {
            tvMovieCategory.setText(movie.getName());
            tvMovieCategory.setText(movie.getCategory());
            tvDescription.setText(movie.getDescription());

            String fullImageUrl = BuildConfig.BASE_URL + movie.getImageUrl() + ".png";

            Glide.with(itemView.getContext())
                    .load(fullImageUrl)
                    .apply(new RequestOptions()
                            .centerCrop()
                            .placeholder(R.drawable.background)
                            .error(R.drawable.background))
                    .into(ivMovieBackground);
            itemView.setOnClickListener(v -> {
                if(listener != null) {
                    listener.onMovieItemClick(movie);
                }
            });
        }
    }
}
