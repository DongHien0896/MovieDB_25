package com.framgia.hien.moviedb.data.repository;

import com.framgia.hien.moviedb.data.model.Movie;
import com.framgia.hien.moviedb.data.model.ResultMovie;
import com.framgia.hien.moviedb.data.source.remote.MovieDataSource;
import com.framgia.hien.moviedb.data.source.remote.MovieRemoteDataSource;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.annotations.NonNull;

public class MovieRepository implements MovieDataSource.RemoteDataSource {

    private MovieRemoteDataSource mRemoteDataSource;
    private static MovieRepository sInstance;

    public MovieRepository(@NonNull MovieRemoteDataSource remoteDataSource) {
        this.mRemoteDataSource = remoteDataSource;
    }

    public static synchronized MovieRepository getInstance(MovieRemoteDataSource movieRemoteDataSource) {
        if (sInstance == null) {
            sInstance = new MovieRepository(movieRemoteDataSource);
        }
        return sInstance;
    }

    @Override
    public Maybe<List<Movie>> getAllMovieByType(String key, int page, String type) {
        return mRemoteDataSource.getAllMovieByType(key, page, type);
    }

    @Override
    public Maybe<Movie> getDetailMovie(int movieId, String key) {
        return mRemoteDataSource.getDetailMovie(movieId, key);
    }

    @Override
    public Maybe<List<ResultMovie>> getAllMovieByPerson(String key, String language, String query) {
        return mRemoteDataSource.getAllMovieByPerson(key, language, query);
    }

    @Override
    public Maybe<List<Movie>> getAllMovieByCompany(int companyId, String key, String language) {
        return mRemoteDataSource.getAllMovieByCompany(companyId, key, language);
    }

    @Override
    public Maybe<List<Movie>> searchMovieByGenre(String key, int genreId, int page) {
        return mRemoteDataSource.searchMovieByGenre(key, genreId, page);
    }

    @Override
    public Maybe<List<Movie>> searchMovieByName(String key, String query, int page) {
        return mRemoteDataSource.searchMovieByName(key, query, page);
    }
}
