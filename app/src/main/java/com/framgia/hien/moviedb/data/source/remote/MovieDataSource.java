package com.framgia.hien.moviedb.data.source.remote;

import com.framgia.hien.moviedb.data.model.Movie;
import com.framgia.hien.moviedb.data.model.ResultMovie;

import java.util.List;

import io.reactivex.Maybe;

public interface MovieDataSource {

    Maybe<List<Movie>> getAllMovieByType(String key, int page, String type);

    Maybe<Movie> getDetailMovie(int movieId, String key);

    Maybe<List<ResultMovie>> getAllMovieByPerson(String key, String language, String query);

    Maybe<List<Movie>> getAllMovieByCompany(int companyId, String key, String language);

    interface RemoteDataSource extends MovieDataSource {
    }
}
