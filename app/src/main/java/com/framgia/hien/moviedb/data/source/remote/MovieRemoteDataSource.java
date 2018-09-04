package com.framgia.hien.moviedb.data.source.remote;

import com.framgia.hien.moviedb.data.model.Movie;
import com.framgia.hien.moviedb.data.model.MovieResponse;
import com.framgia.hien.moviedb.data.source.remote.service.MovieApi;
import com.framgia.hien.moviedb.data.source.remote.service.MovieServiceClient;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
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
    public Maybe<List<Movie>> getAllMovie(String key, int page) {
        return mMovieApi.getMoviePopular(key, page)
                .flatMap(new Function<MovieResponse, SingleSource<? extends List<Movie>>>() {
                    @Override
                    public SingleSource<? extends List<Movie>> apply(MovieResponse movieResponse) throws Exception {
                        return Single.just(movieResponse.getItems());
                    }
                })
                .toMaybe();
    }
}
