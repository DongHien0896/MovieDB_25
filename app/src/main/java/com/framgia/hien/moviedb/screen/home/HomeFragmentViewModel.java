package com.framgia.hien.moviedb.screen.home;

import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ProgressBar;

import com.example.dong.moviedb.BuildConfig;
import com.framgia.hien.moviedb.data.model.Movie;
import com.framgia.hien.moviedb.data.repository.MovieRepository;
import com.framgia.hien.moviedb.screen.BaseViewModel;
import com.framgia.hien.moviedb.screen.detail.DetailActivity;
import com.framgia.hien.moviedb.util.Constants;
import com.framgia.hien.moviedb.util.rx.BaseScheduleProvider;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class HomeFragmentViewModel extends BaseViewModel implements MovieAdapter.ItemClickListener {

    public ObservableField<HomeSliderFragmentPagerAdapter> homeSlideAdapter = new ObservableField<>();
    private static final int SLIDER_INTERVAL_TIMEOUT = 5500;
    private static final int DECREASE = -1;
    private final Handler mSliderHandler = new Handler();
    private Runnable mSliderRunnable;

    private MovieRepository mMovieRepository;
    private ProgressBar mProgressBar;
    private BaseScheduleProvider mBaseScheduleProvider;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    private MovieAdapter mMoviePopularAdapter;
    private MovieAdapter mMovieNowPlayingAdaper;
    private MovieAdapter mMovieUpcomingAdapter;
    private MovieAdapter mMovieTopRatedAdapter;
    private Context mContext;

    public HomeFragmentViewModel(FragmentManager fragmentManager, MovieRepository movieRepository, Context context) {
        homeSlideAdapter.set(new HomeSliderFragmentPagerAdapter(fragmentManager));
        this.mMovieRepository = movieRepository;
        this.mContext = context;
        mMoviePopularAdapter = new MovieAdapter();
        mMovieNowPlayingAdaper = new MovieAdapter();
        mMovieUpcomingAdapter = new MovieAdapter();
        mMovieTopRatedAdapter = new MovieAdapter();
        mMoviePopularAdapter.setItemClickListener(this);
        mMovieNowPlayingAdaper.setItemClickListener(this);
        mMovieUpcomingAdapter.setItemClickListener(this);
        mMovieTopRatedAdapter.setItemClickListener(this);
    }

    public void setProgressBar(ProgressBar progressBar) {
        this.mProgressBar = progressBar;
    }

    public MovieAdapter getMoviePopularAdapter() {
        return mMoviePopularAdapter;
    }

    public MovieAdapter getMovieNowPlayingAdapter() {
        return mMovieNowPlayingAdaper;
    }

    public MovieAdapter getMovieUpcomingAdapter() {
        return mMovieUpcomingAdapter;
    }

    public MovieAdapter getMovieTopRatedAdapter() {
        return mMovieTopRatedAdapter;
    }

    public void setSchedulerProvider(BaseScheduleProvider baseScheduleProvider) {
        this.mBaseScheduleProvider = baseScheduleProvider;
    }

    private void requestGetMovies() {
        mProgressBar.setVisibility(View.VISIBLE);
        getMovies(Constants.TYPE_POPULAR, mMoviePopularAdapter);
        getMovies(Constants.TYPE_NOW_PLAYING, mMovieNowPlayingAdaper);
        getMovies(Constants.TYPE_UPCOMING, mMovieUpcomingAdapter);
        getMovies(Constants.TYPE_TOP_RATED, mMovieTopRatedAdapter);
    }

    private void getMovies(String type, final MovieAdapter adapter) {
        Disposable disposable = mMovieRepository.getAllMovieByType(BuildConfig.API_KEY.toString()
                , Constants.PAGE_REQUEST, type)
                .subscribeOn(mBaseScheduleProvider.io())
                .observeOn(mBaseScheduleProvider.ui())
                .subscribe(new Consumer<List<Movie>>() {
                    @Override
                    public void accept(List<Movie> movies) throws Exception {
                        mProgressBar.setVisibility(View.GONE);
                        // set adapter for recyclerView.
                        adapter.setMovies(movies);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        // show error
                        mProgressBar.setVisibility(View.GONE);
                    }
                });
        mCompositeDisposable.add(disposable);
    }

    public void startSliderInterval(final ViewPager pager) {
        mSliderRunnable = new Runnable() {
            @Override
            public void run() {
                int count = pager.getCurrentItem();
                if (count == Constants.HOME_SLIDER_TOTAL_PAGE - 1) {
                    count = DECREASE;
                }
                pager.setCurrentItem(++count, true);
                mSliderHandler.postDelayed(this, SLIDER_INTERVAL_TIMEOUT);
            }
        };

        mSliderHandler.postDelayed(mSliderRunnable, SLIDER_INTERVAL_TIMEOUT);
    }

    public void stopSliderInterval() {
        mSliderHandler.removeCallbacks(mSliderRunnable);
    }

    @Override
    protected void onStart() {
        requestGetMovies();
    }

    @Override
    protected void onStop() {
        mCompositeDisposable.clear();
    }

    @Override
    public void onItemClicked(Integer movieId) {
        if (movieId == null) {
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.ARGUMENT_MOVIE_ID, movieId);
        Intent intent = new Intent(mContext, DetailActivity.class);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }
}
