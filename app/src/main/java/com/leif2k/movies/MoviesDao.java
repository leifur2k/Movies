package com.leif2k.movies;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.leif2k.movies.movie_pojo.Movie;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface MoviesDao {

    @Query("SELECT * FROM favourite_movies")
    LiveData<List<Movie>> getAllFavouriteMovies();

    @Query("SELECT * FROM favourite_movies WHERE id = :movieId")
    LiveData<Movie> getFavouriteMovie(int movieId);

    @Insert
    Completable insertMovie(Movie movie);

    @Query("DELETE FROM favourite_movies WHERE id = :movieId")
    Completable removeMovie(int movieId);

}
