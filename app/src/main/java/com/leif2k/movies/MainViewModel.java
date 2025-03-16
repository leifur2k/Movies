package com.leif2k.movies;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.leif2k.movies.movie_pojo.Movie;
import com.leif2k.movies.movie_pojo.MovieResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainViewModel extends AndroidViewModel {

    private MutableLiveData<List<Movie>> movies = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private MutableLiveData<Boolean> isError = new MutableLiveData<>();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private int page = 1;
    private int limit = 30;
    private String rating = "8-10";
    private String votes = "100000-1000000";
    private List<String> selectFields = new ArrayList<>(Arrays.asList("id", "name", "description", "year", "rating", "poster"));


    public MainViewModel(@NonNull Application application) {
        super(application);
        loadMovies();
    }


    public LiveData<List<Movie>> getMovies() {
        return movies;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<Boolean> getIsError() {
        return isError;
    }


    public void loadMovies() {

        Boolean loading = isLoading.getValue();
        if (loading != null && loading)
            return;

        Disposable disposable = ApiFactory.getApiService().loadMovies(page, limit, rating, votes, selectFields)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Throwable {
                        isLoading.setValue(true);
                    }
                })
                .doAfterTerminate(new Action() {
                    @Override
                    public void run() throws Throwable {
                        isLoading.setValue(false);
                    }
                })
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        isError.setValue(false);
                    }
                })
                .subscribe(new Consumer<MovieResponse>() {
                    @Override
                    public void accept(MovieResponse movieResponse) throws Throwable {
                        List<Movie> loadedMovies = movies.getValue();
                        if (loadedMovies != null) {
                            loadedMovies.addAll(movieResponse.getMovies());
                            movies.setValue(loadedMovies);
                        } else {
                            movies.setValue(movieResponse.getMovies());
                        }
                        Log.d("LogKey", "MainViewModel: " + limit + " Фильмов загружено!");
                        page++;

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        isError.setValue(true);
                    }
                });
        compositeDisposable.add(disposable);
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
