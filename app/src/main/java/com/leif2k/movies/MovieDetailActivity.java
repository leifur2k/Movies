package com.leif2k.movies;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.leif2k.movies.movie_pojo.Movie;
import com.leif2k.movies.review_pojo.Review;
import com.leif2k.movies.trailer_pogo.Trailer;
import com.leif2k.movies.trailer_pogo.TrailerResponse;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MovieDetailActivity extends AppCompatActivity {

    private static final String EXTRA_MOVIE = "movie";
    private static final String VIDEOS = "videos";

    private ImageView imageViewPoster;
    private ImageView imageViewStar;
    private TextView textViewTitle;
    private TextView textViewYear;
    private TextView textViewDescription;
    private RecyclerView recyclerViewTrailers;
    private RecyclerView recyclerViewReviews;

    private MovieDetailViewModel viewModel;
    private TrailersAdapter trailersAdapter;
    private ReviewsAdapter reviewsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_movie_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews();

        trailersAdapter = new TrailersAdapter();
        recyclerViewTrailers.setAdapter(trailersAdapter);
        recyclerViewTrailers.setLayoutManager(new LinearLayoutManager(this));

        reviewsAdapter = new ReviewsAdapter();
        recyclerViewReviews.setAdapter(reviewsAdapter);
        recyclerViewReviews.setLayoutManager(new LinearLayoutManager(this));

        viewModel = new ViewModelProvider(this).get(MovieDetailViewModel.class);

        Movie movie = (Movie) getIntent().getSerializableExtra(EXTRA_MOVIE);

        Glide.with(this)
                .load(movie.getPoster().getUrl())
                .into(imageViewPoster);

        textViewTitle.setText(movie.getName());
        textViewYear.setText(String.valueOf(movie.getYear()));
        textViewDescription.setText(movie.getDescription());

        viewModel.loadTrailers(movie.getId());

        viewModel.getTrailers().observe(this, new Observer<List<Trailer>>() {
            @Override
            public void onChanged(List<Trailer> trailers) {
                trailersAdapter.setTrailers(trailers);
                Log.d("LogKey", "MovieDetailActivity: Получено " + trailers.size() + " трейлеров.");
            }
        });

        trailersAdapter.setOnTrailerClickListener(new TrailersAdapter.OnTrailerClickListener() {
            @Override
            public void onTrailerClick(Trailer trailer) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(trailer.getUrl()));
                startActivity(intent);
            }
        });


        viewModel.loadReviews(movie.getId());

        viewModel.getReviews().observe(this, new Observer<List<Review>>() {
            @Override
            public void onChanged(List<Review> reviews) {
                reviewsAdapter.setReviews(reviews);
                Log.d("LogKey", "MovieDetailActivity: Получено " + reviews.size() + " отзывов.");
            }
        });

        viewModel.getFavouriteMovie(movie.getId()).observe(this, new Observer<Movie>() {
            @Override
            public void onChanged(Movie movieFromDb) {
                if(movieFromDb == null) {
                    imageViewStar.setImageResource(android.R.drawable.btn_star_big_off);
                    imageViewStar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            viewModel.insertMovie(movie);
                        }
                    });
                }
                else {
                    imageViewStar.setImageResource(android.R.drawable.btn_star_big_on);
                    imageViewStar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            viewModel.removeMovie(movie.getId());
                        }
                    });
                }
            }
        });

        textViewYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = FavouriteActivity.newIntent(MovieDetailActivity.this);
                startActivity(intent);
            }
        });

    }


    public static Intent newIntent(Context context, Movie movie) {
        Intent intent = new Intent(context, MovieDetailActivity.class);
        intent.putExtra(EXTRA_MOVIE, movie);
        return intent;
    }

    private void initViews() {
        imageViewPoster = findViewById(R.id.imageViewPoster);
        imageViewStar = findViewById(R.id.imageViewStar);
        textViewTitle = findViewById(R.id.textViewTitle);
        textViewYear = findViewById(R.id.textViewYear);
        textViewDescription = findViewById(R.id.textViewDescription);
        recyclerViewTrailers = findViewById(R.id.recyclerViewTrailers);
        recyclerViewReviews = findViewById(R.id.recyclerViewReviews);
    }

}