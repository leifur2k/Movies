package com.leif2k.movies;

import com.leif2k.movies.movie_pojo.MovieResponse;
import com.leif2k.movies.review_pojo.ReviewResponse;
import com.leif2k.movies.trailer_pogo.TrailerResponse;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface ApiService {

    String TOKEN = "CPA473E-NET4W6D-G7P21R2-S6Y4NYP";

    @GET("movie")
    @Headers("X-API-KEY:" + TOKEN)
    Single<MovieResponse> loadMovies(
            @Query("page") int page,
            @Query("limit") int limit,
            @Query("rating.kp") String rating,
            @Query("votes.kp") String votes,
            @Query("selectFields") List<String> selectFields
    );

    @GET("movie")
    @Headers("X-API-KEY:" + TOKEN)
    Single<TrailerResponse> loadTrailers(
            @Query("id") int id,
            @Query("selectFields") String selectField
    );

    @GET("review")
    @Headers("X-API-KEY:" + TOKEN)
    Single<ReviewResponse> loadReviews(
            @Query("selectFields") List<String> selectFields,
            @Query("movieId") int movieId
    );

}
