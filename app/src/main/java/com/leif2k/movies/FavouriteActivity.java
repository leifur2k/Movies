package com.leif2k.movies;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.leif2k.movies.movie_pojo.Movie;

import java.util.List;

public class FavouriteActivity extends AppCompatActivity {

    private FavouriteViewModel viewModel;
    private RecyclerView recyclerViewFavourites;
    private FavouriteAdapter favouriteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_favourite);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.recyclerViewFavourites), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerViewFavourites = findViewById(R.id.recyclerViewFavourites);
        favouriteAdapter = new FavouriteAdapter();
        recyclerViewFavourites.setAdapter(favouriteAdapter);
        recyclerViewFavourites.setLayoutManager(new GridLayoutManager(this, 2));
        viewModel = new ViewModelProvider(this).get(FavouriteViewModel.class);

        viewModel.getAllFavouriteMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                favouriteAdapter.setFavouriteMovies(movies);
            }
        });

        favouriteAdapter.setOnMovieClickListener(new FavouriteAdapter.OnMovieClickListener() {
            @Override
            public void onMovieClick(Movie movie) {
                Intent intent = MovieDetailActivity.newIntent(FavouriteActivity.this, movie);
                startActivity(intent);
            }
        });

    }

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, FavouriteActivity.class);
        return intent;
    }

}