package com.leif2k.movies;

import android.app.Application;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.leif2k.movies.movie_pojo.Movie;

@Database(entities = {Movie.class}, version = 1, exportSchema = false)
public abstract class MovieDatabase extends RoomDatabase {

    private static MovieDatabase instance = null;

    public static MovieDatabase getInstance(Application application) {
        if (instance == null) {
            instance = Room.databaseBuilder(application, MovieDatabase.class, "movie.db").build();
        }
        return instance;
    }

    public abstract MoviesDao moviesDao();
}

