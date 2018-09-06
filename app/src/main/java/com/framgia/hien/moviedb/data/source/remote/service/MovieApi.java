package com.framgia.hien.moviedb.data.source.remote.service;

import com.framgia.hien.moviedb.data.model.CastResponse;
import com.framgia.hien.moviedb.data.model.Genre;
import com.framgia.hien.moviedb.data.model.GenresResponse;
import com.framgia.hien.moviedb.data.model.Movie;
import com.framgia.hien.moviedb.data.model.MovieResponse;
import com.framgia.hien.moviedb.data.model.TrailerResponse;

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

    @GET("genre/movie/list")
    Single<GenresResponse>  getGenres(@Query("api_key") String key);

    @GET("movie/{movie_id}")
    Single<Movie> getDetailMovie(@Path("movie_id") int id, @Query("api_key") String key);

    @GET("movie/{movie_id}/credits")
    Single<CastResponse> getCastOfMovie(@Path("movie_id") int id, @Query("api_key") String key);

    @GET("movie/{movie_id}/videos")
    Single<TrailerResponse> getTrailer(@Path("movie_id") int id, @Query("api_key") String key);
}

