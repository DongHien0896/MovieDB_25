package com.framgia.hien.moviedb.data.source.remote;

import com.framgia.hien.moviedb.data.model.Movie;
import com.framgia.hien.moviedb.data.model.MovieResponse;
import com.framgia.hien.moviedb.data.source.remote.service.MovieApi;
import com.framgia.hien.moviedb.data.source.remote.service.MovieServiceClient;
import com.framgia.hien.moviedb.util.Constants;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;

public class MovieRemoteDataSource implements MovieDataSource.RemoteDataSource {

    private static MovieRemoteDataSource sMovieRemote;
    private MovieApi mMovieApi;

    public MovieRemoteDataSource(MovieApi movieApi) {
        this.mMovieApi = movieApi;
    }

    public static synchronized MovieRemoteDataSource getInstance() {
        if (sMovieRemote == null) {
            sMovieRemote = new MovieRemoteDataSource(MovieServiceClient.getInstance());
        }
        return sMovieRemote;
    }

    @Override
    public Maybe<List<Movie>> getAllMovieByType(String key, int page, String type) {
        switch (type) {
            case Constants.TYPE_POPULAR:
                return mMovieApi.getMoviePopular(key, page)
                        .flatMap(new Function<MovieResponse, SingleSource<? extends List<Movie>>>() {
                            @Override
                            public SingleSource<? extends List<Movie>> apply(MovieResponse movieResponse) {
                                return Single.just(movieResponse.getItems());
                            }
                        })
                        .toMaybe();
            case Constants.TYPE_NOW_PLAYING:
                return mMovieApi.getMovieNowPlaying(key, page)
                        .flatMap(new Function<MovieResponse, SingleSource<? extends List<Movie>>>() {
                            @Override
                            public SingleSource<? extends List<Movie>> apply(MovieResponse movieResponse) {
                                return Single.just(movieResponse.getItems());
                            }
                        })
                        .toMaybe();
            case Constants.TYPE_UPCOMING:
                return mMovieApi.getMovieUpcoming(key, page)
                        .flatMap(new Function<MovieResponse, SingleSource<? extends List<Movie>>>() {
                            @Override
                            public SingleSource<? extends List<Movie>> apply(MovieResponse movieResponse) {
                                return Single.just(movieResponse.getItems());
                            }
                        })
                        .toMaybe();
            case Constants.TYPE_TOP_RATED:
                return mMovieApi.getMovieTopRated(key, page)
                        .flatMap(new Function<MovieResponse, SingleSource<? extends List<Movie>>>() {
                            @Override
                            public SingleSource<? extends List<Movie>> apply(MovieResponse movieResponse) {
                                return Single.just(movieResponse.getItems());
                            }
                        })
                        .toMaybe();
            default:
                break;
        }
        return null;
    }
}
