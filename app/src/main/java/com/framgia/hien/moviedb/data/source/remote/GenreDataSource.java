package com.framgia.hien.moviedb.data.source.remote;

import com.framgia.hien.moviedb.data.model.Genre;

import java.util.List;

import io.reactivex.Maybe;

public interface GenreDataSource {
    Maybe<List<Genre>> getGenres(String key);

    interface RemoteDataSource extends GenreDataSource {
    }
}
