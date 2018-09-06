package com.framgia.hien.moviedb.screen.play;

import android.databinding.BaseObservable;
import android.databinding.ObservableField;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.dong.moviedb.BuildConfig;
import com.framgia.hien.moviedb.data.model.Movie;
import com.framgia.hien.moviedb.data.model.Trailer;
import com.framgia.hien.moviedb.data.repository.TrailerRepository;
import com.framgia.hien.moviedb.screen.BaseViewModel;
import com.framgia.hien.moviedb.screen.main.MainActivity;
import com.framgia.hien.moviedb.util.rx.BaseScheduleProvider;
import com.framgia.hien.moviedb.util.rx.ScheduleProvider;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class PlayViewModel extends BaseViewModel implements TrailerAdapter.ItemTrailerClickListener {

    private BaseScheduleProvider mBaseScheduleProvider;
    private CompositeDisposable mCompositeDisposable;
    private TrailerRepository mTrailerRepository;
    public ObservableField<Movie> movieObservableField = new ObservableField<>();
    private InterfaceBackClickListener mListener;
    private TrailerAdapter mTrailerAdapter;
    private ProgressBar mProgressBar;

    private YouTubePlayerView mYouTubePlayerView;
    private YouTubePlayer mYouTubePlayer;
    private PlayerStateChangeListener mPlayerStateChangeListener;
    private List<String> mKeyVideos = new ArrayList<>();

    public PlayViewModel(Movie movie, InterfaceBackClickListener listener) {
        movieObservableField.set(movie);
        this.mListener = listener;
        setComponent();
    }

    private void setComponent() {
        this.mBaseScheduleProvider = ScheduleProvider.getInstance();
        mCompositeDisposable = new CompositeDisposable();
        mTrailerAdapter = new TrailerAdapter();
        mTrailerAdapter.setItemTrailerClick(this);
    }

    public void setTrailerRepository(TrailerRepository repository) {
        this.mTrailerRepository = repository;
    }

    public TrailerAdapter getTrailerAdapter() {
        return this.mTrailerAdapter;
    }

    public void setProgressBar(ProgressBar progressBar) {
        this.mProgressBar = progressBar;
    }

    public void getTrailer() {
        mProgressBar.setVisibility(View.VISIBLE);
        Disposable disposable = mTrailerRepository
                .getTrailer(movieObservableField.get().getId(), BuildConfig.API_KEY.toString())
                .subscribeOn(mBaseScheduleProvider.io())
                .observeOn(mBaseScheduleProvider.ui())
                .subscribe(new Consumer<List<Trailer>>() {
                               @Override
                               public void accept(List<Trailer> trailers) throws Exception {
                                   mTrailerAdapter.setTrailers(trailers);
                                   setKeyVideos(trailers);
                                   mProgressBar.setVisibility(View.GONE);
                               }
                           }
                        , new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                mProgressBar.setVisibility(View.GONE);
                            }
                        });
        mCompositeDisposable.add(disposable);
    }

    public void setKeyVideos(List<Trailer> trailers){
        for (Trailer trailer : trailers){
            mKeyVideos.add(trailer.getKey());
        }
    }

    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return mYouTubePlayerView;
    }

    public void onBackClicked(View view) {
        mListener.onBackImageClick();
    }

    @Override
    public void onItemTrailerClick(String trailerKey) {

    }

    @Override
    protected void onStart() {
        getTrailer();
    }

    @Override
    protected void onStop() {

    }

    interface InterfaceBackClickListener {
        void onBackImageClick();
    }

    private final class PlayerStateChangeListener implements YouTubePlayer.PlayerStateChangeListener {
        @Override
        public void onLoading() {
        }

        @Override
        public void onLoaded(String s) {
            mYouTubePlayer.play();
        }

        @Override
        public void onAdStarted() {
        }

        @Override
        public void onVideoStarted() {
        }

        @Override
        public void onVideoEnded() {
        }

        @Override
        public void onError(YouTubePlayer.ErrorReason errorReason) {
        }
    }
}
