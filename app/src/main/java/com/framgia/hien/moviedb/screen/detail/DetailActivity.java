package com.framgia.hien.moviedb.screen.detail;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.dong.moviedb.R;
import com.example.dong.moviedb.databinding.ActivityDetailBinding;
import com.framgia.hien.moviedb.data.repository.MovieRepository;
import com.framgia.hien.moviedb.data.repository.CastRepository;
import com.framgia.hien.moviedb.data.repository.MovieRepository;
import com.framgia.hien.moviedb.data.source.remote.CastRemoteDataSource;
import com.framgia.hien.moviedb.data.source.remote.MovieRemoteDataSource;
import com.framgia.hien.moviedb.screen.BaseActivity;
import com.framgia.hien.moviedb.util.Constants;

public class DetailActivity extends BaseActivity implements DetailViewModel.BackPressListener {

    private ActivityDetailBinding mBinding;
    private DetailViewModel mViewModel;
    private ProgressBar mProgressBar;
    private TextView mTextOverview;
    private ImageView mImageDrop;
    private ImageView mImageTrailer;
    private ImageView mImageFavorite;
    private ImageView mImageUnfavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        setComponent();
    }

    private void setComponent() {
        MovieRepository movieRepository = MovieRepository.getInstance(MovieRemoteDataSource.getInstance());
        CastRepository castRepository = CastRepository.getsInstance(CastRemoteDataSource.getInstance());
        Intent intent = getIntent();
        int movieId = intent.getIntExtra(Constants.ARGUMENT_MOVIE_ID, Constants.DEFAULT_VALUE);
        initView();
        setViewModel(movieRepository, castRepository, movieId);
        mBinding.setViewModel(mViewModel);
    }

    private void setViewModel(MovieRepository movieRepository, CastRepository castRepository, int movieId) {
        mViewModel = new DetailViewModel(this, movieId, this);
        mViewModel.setRepository(movieRepository, castRepository);
        mViewModel.setProgressBar(mProgressBar);
        mViewModel.setTextView(mTextOverview);
        mViewModel.setImagView(mImageDrop, mImageTrailer);
        mViewModel.setFavoriteImage(mImageFavorite, mImageUnfavorite);
    }

    private void initView() {
        mProgressBar = mBinding.progressDetailIndicator;
        mTextOverview = mBinding.textDetailOverview;
        mImageDrop = mBinding.imageDetailBackdrop;
        mImageTrailer = mBinding.imageTrailer;
        mImageFavorite = mBinding.imageFavorite;
        mImageUnfavorite = mBinding.imageUnFavorite;
    }

    @Override
    protected void onStart() {
        super.onStart();
        mViewModel.onStart();
    }

    @Override
    protected void onStop() {
        mViewModel.onStop();
        super.onStop();
    }

    @Override
    public void backPress() {
        onBackPressed();
    }
}
