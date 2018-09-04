package com.framgia.hien.moviedb.data.source.remote.service;

import com.framgia.hien.moviedb.data.model.MovieResponse;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieApi {

    @GET("movie/popular")
    Single<MovieResponse> getMoviePopular(@Query("api_key") String key, @Query("page") int pageNumber);

    @GET("movie/now_playing")
    Single<MovieResponse> getMovieNowPlaying(@Query("api_key") String key, @Query("page") int pageNumber);

    @GET("movie/upcoming")
    Single<MovieResponse> getMovieUpcoming(@Query("api_key") String key, @Query("page") int pageNumber);

    @GET("movie/top_rated")
    Single<MovieResponse> getMovieTopRated(@Query("api_key") String key, @Query("page") int pageNumber);
}

