package com.leif2k.movies;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.leif2k.movies.movie_pojo.Movie;
import com.leif2k.movies.review_pojo.Review;
import com.leif2k.movies.review_pojo.ReviewResponse;
import com.leif2k.movies.trailer_pogo.Trailer;
import com.leif2k.movies.trailer_pogo.TrailerResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MovieDetailViewModel extends AndroidViewModel {

    private static final String VIDEOS = "videos";
    private List<String> selectFields = new ArrayList<>(Arrays.asList("type", "review", "author"));

    private MutableLiveData<List<Trailer>> trailers = new MutableLiveData<>();
    private MutableLiveData<List<Review>> reviews = new MutableLiveData<>();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final MoviesDao moviesDao;


    public LiveData<List<Trailer>> getTrailers() {
        return trailers;
    }

    public LiveData<List<Review>> getReviews() {
        return reviews;
    }

    public LiveData<Movie> getFavouriteMovie(int movieID) {
        return moviesDao.getFavouriteMovie(movieID);
    }


    public MovieDetailViewModel(@NonNull Application application) {
        super(application);
        moviesDao = MovieDatabase.getInstance(application).moviesDao();
    }


    public void loadTrailers(int id) {
        Disposable disposable = ApiFactory.getApiService().loadTrailers(id,VIDEOS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<TrailerResponse, List<Trailer>>() {
                    @Override
                    public List<Trailer> apply(TrailerResponse trailerResponse) throws Throwable {
                        return trailerResponse.getObjectResponses().get(0).getTrailersList().getTrailers();
                    }
                })
                .subscribe(new Consumer<List<Trailer>>() {
                    @Override
                    public void accept(List<Trailer> listTrailers) throws Throwable {
                        if(!listTrailers.isEmpty()) {
                            trailers.setValue(listTrailers);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        Log.d("LogKey", "MovieDetailViewModel: Трейлеров нет");
                    }
                });
        compositeDisposable.add(disposable);
    }


    public void loadReviews(int id) {
        Disposable disposable = ApiFactory.getApiService().loadReviews(selectFields, id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<ReviewResponse, List<Review>>() {
                    @Override
                    public List<Review> apply(ReviewResponse reviewResponse) throws Throwable {
                        return reviewResponse.getReviews();
                    }
                })
                .subscribe(new Consumer<List<Review>>() {
                    @Override
                    public void accept(List<Review> listReviews) throws Throwable {
                        if(!listReviews.isEmpty()) {
                            reviews.setValue(listReviews);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        Log.d("LogKey", "MovieDetailViewModel: Отзывов нет");
                    }
                });
        compositeDisposable.add(disposable);
    }


    public void insertMovie(Movie movie) {
        Disposable disposable = moviesDao.insertMovie(movie)
                .subscribeOn(Schedulers.io())
                .subscribe();
        compositeDisposable.add(disposable);
    }

    public void removeMovie(int movieId) {
        Disposable disposable = moviesDao.removeMovie(movieId)
                .subscribeOn(Schedulers.io())
                .subscribe();
        compositeDisposable.add(disposable);
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
