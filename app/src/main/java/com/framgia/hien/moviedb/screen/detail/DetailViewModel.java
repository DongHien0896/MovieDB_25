package com.framgia.hien.moviedb.screen.detail;

import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.dong.moviedb.BuildConfig;
import com.example.dong.moviedb.R;
import com.framgia.hien.moviedb.data.model.Movie;
import com.framgia.hien.moviedb.data.repository.MovieRepository;
import com.framgia.hien.moviedb.screen.BaseViewModel;
import com.framgia.hien.moviedb.util.Constants;
import com.framgia.hien.moviedb.util.rx.BaseScheduleProvider;
import com.framgia.hien.moviedb.util.rx.ScheduleProvider;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class DetailViewModel extends BaseViewModel implements CompanyAdapter.ItemClickListener {

    private AppCompatActivity mActivity;
    private MovieRepository mMovieRepository;
    public ObservableField<Movie> movieObservableField = new ObservableField<>();
    private BaseScheduleProvider mBaseScheduleProvider;
    private CompositeDisposable mCompositeDisposable;
    private int mMovieId;
    private ProgressBar mProgressBar;
    private ImageClick mImageClick;
    private boolean mIsTextOverviewExpanded;
    private TextView mTextOverview;
    private CompanyAdapter mCompanyAdapter;

    public static final int OVEVERVIEW_MINLINE = 3;
    public static final int OVEVERVIEW_MAXLINE = 30;

    public DetailViewModel(AppCompatActivity appCompatActivity, MovieRepository repository, int movieId
            , ImageClick imageClick) {
        this.mActivity = appCompatActivity;
        this.mMovieRepository = repository;
        this.mMovieId = movieId;
        this.mBaseScheduleProvider = ScheduleProvider.getInstance();
        mCompositeDisposable = new CompositeDisposable();
        this.mImageClick = imageClick;
        mCompanyAdapter = new CompanyAdapter();
        mCompanyAdapter.setItemClickListener(this);
    }

    public CompanyAdapter getCompanyAdapter(){
        return mCompanyAdapter;
    }

    public void setProgressBar(ProgressBar progressBar) {
        this.mProgressBar = progressBar;
    }

    public void setTextView(TextView textView){
        this.mTextOverview = textView;
    }

    public void getMovieDetail() {
        mProgressBar.setVisibility(View.VISIBLE);
        Disposable disposable = mMovieRepository.getDetailMovie(mMovieId, BuildConfig.API_KEY.toString())
                .subscribeOn(mBaseScheduleProvider.io())
                .observeOn(mBaseScheduleProvider.ui())
                .subscribe(new Consumer<Movie>() {
                    @Override
                    public void accept(Movie movie) throws Exception {
                        mProgressBar.setVisibility(View.GONE);
                        movieObservableField.set(movie);
                        mCompanyAdapter.setCompanies(movie.getProductionCompanies());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mProgressBar.setVisibility(View.GONE);
                    }
                });
        mCompositeDisposable.add(disposable);
    }

    @Override
    protected void onStart() {
    }

    @Override
    protected void onStop() {

    }

    public void onBackClicked(View view){
        mImageClick.backPress();
    }

    public void onTextClicked(View view){
        if (mIsTextOverviewExpanded) {
            mTextOverview.setMaxLines(OVEVERVIEW_MINLINE);
        } else {
            mTextOverview.setMaxLines(OVEVERVIEW_MAXLINE);
        }
        mIsTextOverviewExpanded = !mIsTextOverviewExpanded;
    }

    @Override
    public void onItemClick(int CompanyId) {

    }

    interface ImageClick {
        void backPress();
    }
}
