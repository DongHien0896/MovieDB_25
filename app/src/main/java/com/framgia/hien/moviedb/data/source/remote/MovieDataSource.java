package com.framgia.hien.moviedb.data.source.remote;

import com.framgia.hien.moviedb.data.model.Movie;

import java.util.List;

import io.reactivex.Maybe;

public interface MovieDataSource {

    Maybe<List<Movie>> getAllMovie(String key, int page);

    interface RemoteDataSource extends MovieDataSource {
    }
}
