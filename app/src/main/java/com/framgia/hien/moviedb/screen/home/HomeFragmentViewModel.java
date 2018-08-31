package com.framgia.hien.moviedb.screen.home;

import android.databinding.ObservableField;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ProgressBar;

import com.example.dong.moviedb.BuildConfig;
import com.framgia.hien.moviedb.data.model.Movie;
import com.framgia.hien.moviedb.data.repository.MovieRepository;
import com.framgia.hien.moviedb.screen.BaseViewModel;
import com.framgia.hien.moviedb.util.Constants;
import com.framgia.hien.moviedb.util.rx.BaseScheduleProvider;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class HomeFragmentViewModel extends BaseViewModel {

    public ObservableField<HomeSliderFragmentPagerAdapter> homeSlideAdapter = new ObservableField<>();
    private static final int SLIDER_INTERVAL_TIMEOUT = 5500;
    private static final int DECREASE = -1;
    private final Handler mSliderHandler = new Handler();
    private Runnable mSliderRunnable;

    private MovieRepository mMovieRepository;
    private ProgressBar mProgressBar;
    private BaseScheduleProvider mBaseScheduleProvider;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    private MovieAdapter mMovieAdapter;

    public HomeFragmentViewModel(FragmentManager fragmentManager, MovieRepository movieRepository) {
        homeSlideAdapter.set(new HomeSliderFragmentPagerAdapter(fragmentManager));
        this.mMovieRepository = movieRepository;
    }

    public void setProgressBar(ProgressBar progressBar) {
        this.mProgressBar = progressBar;
    }

    public MovieAdapter getMovieAdapter(){
        return mMovieAdapter;
    }

    public void setSchedulerProvider(BaseScheduleProvider baseScheduleProvider) {
        this.mBaseScheduleProvider = baseScheduleProvider;
    }

    private void requestGetMovies() {
        mProgressBar.setVisibility(View.VISIBLE);
        Disposable disposable = mMovieRepository.getAllMovie(BuildConfig.API_KEY.toString(), Constants.PAGE_REQUEST)
                .subscribeOn(mBaseScheduleProvider.io())
                .observeOn(mBaseScheduleProvider.ui())
                .subscribe(new Consumer<List<Movie>>() {
                    @Override
                    public void accept(List<Movie> movies) throws Exception {
                        mProgressBar.setVisibility(View.GONE);
                        // set adapter for recyclerView.
                        mMovieAdapter = new MovieAdapter(movies);
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
}
