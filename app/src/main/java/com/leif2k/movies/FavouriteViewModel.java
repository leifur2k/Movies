package com.leif2k.movies;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.leif2k.movies.movie_pojo.Movie;

import java.util.List;

public class FavouriteViewModel extends AndroidViewModel {

    private final MoviesDao moviesDao;


    public FavouriteViewModel(@NonNull Application application) {
        super(application);
        moviesDao = MovieDatabase.getInstance(application).moviesDao();
    }


    public LiveData<List<Movie>> getAllFavouriteMovies() {
        return moviesDao.getAllFavouriteMovies();
    }


}
