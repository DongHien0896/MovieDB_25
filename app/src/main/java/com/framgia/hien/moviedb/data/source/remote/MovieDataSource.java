package com.framgia.hien.moviedb.data.source.remote;

import com.framgia.hien.moviedb.data.model.Movie;

import java.util.List;

import io.reactivex.Maybe;

public interface MovieDataSource {

    Maybe<List<Movie>> getAllMovieByType(String key, int page, String type);
    Maybe<Movie> getDetailMovie(int movieId, String key);

    interface RemoteDataSource extends MovieDataSource {
    }
}
