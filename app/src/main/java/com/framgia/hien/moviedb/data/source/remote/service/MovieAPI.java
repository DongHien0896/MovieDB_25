package com.framgia.hien.moviedb.data.source.remote.service;

import com.framgia.hien.moviedb.data.model.MovieResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MovieAPI {

    @GET("/movie/popular?api_key={key}&page={page}")
    Observable<MovieResponse> getMoviePopular(@Path("key") String key, @Path("page") int pageNUmber);
}
