package com.leif2k.movies;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.leif2k.movies.movie_pojo.Movie;

import java.util.ArrayList;
import java.util.List;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.FavouriteViewHolder> {

    private List<Movie> favouriteMovies = new ArrayList<>();
    private OnMovieClickListener onMovieClickListener;


    public void setFavouriteMovies(List<Movie> favouriteMovies) {
        this.favouriteMovies = favouriteMovies;
        notifyDataSetChanged();
    }

    public void setOnMovieClickListener(OnMovieClickListener onMovieClickListener) {
        this.onMovieClickListener = onMovieClickListener;
    }


    @NonNull
    @Override
    public FavouriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
        return new FavouriteAdapter.FavouriteViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull FavouriteViewHolder holder, int position) {

        Movie movie = favouriteMovies.get(position);

        Glide.with(holder.itemView)
                .load(movie.getPoster().getUrl())
                .into(holder.imageViewPoster);

        double kp = movie.getRating().getKp();
        double roundedKp = Math.floor(kp * 10) / 10;
        holder.textViewRating.setText(String.valueOf(roundedKp));

        if(roundedKp >= 8)
            holder.textViewRating.setBackgroundResource(R.drawable.circle_green);
        else if (roundedKp >= 6)
            holder.textViewRating.setBackgroundResource(R.drawable.circle_orange);
        else
            holder.textViewRating.setBackgroundResource(R.drawable.circle_red);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onMovieClickListener != null)
                    onMovieClickListener.onMovieClick(movie);
            }
        });
    }


    @Override
    public int getItemCount() {
        return favouriteMovies.size();
    }


    interface OnMovieClickListener {
        void onMovieClick(Movie movie);
    }


    public static class FavouriteViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageViewPoster;
        private final TextView textViewRating;

        public FavouriteViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewPoster = itemView.findViewById(R.id.imageViewPoster);
            textViewRating = itemView.findViewById(R.id.textViewRating);
        }
    }
}
