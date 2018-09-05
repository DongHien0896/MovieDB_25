package com.framgia.hien.moviedb.screen.detail;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.dong.moviedb.R;
import com.example.dong.moviedb.databinding.ActivityDetailBinding;
import com.framgia.hien.moviedb.data.repository.MovieRepository;
import com.framgia.hien.moviedb.data.source.remote.MovieRemoteDataSource;
import com.framgia.hien.moviedb.screen.BaseActivity;
import com.framgia.hien.moviedb.util.Constants;

public class DetailActivity extends BaseActivity implements DetailViewModel.ImageClick {

    private ActivityDetailBinding mBinding;
    private DetailViewModel mViewModel;
    private ProgressBar mProgressBar;
    private TextView mTextOverview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        MovieRepository repository = MovieRepository.getInstance(MovieRemoteDataSource.getInstance());
        Intent intent = getIntent();
        int movieId = intent.getIntExtra(Constants.ARGUMENT_MOVIE_ID, Constants.DEFAULT_VALUE);
        initView();
        mViewModel = new DetailViewModel(this, repository, movieId, this);
        mViewModel.setProgressBar(mProgressBar);
        mViewModel.setTextView(mTextOverview);
        mViewModel.getMovieDetail();
        mBinding.setViewModel(mViewModel);
    }

    private void initView() {
        mProgressBar = mBinding.progressDetailIndicator;
        mTextOverview = mBinding.textDetailOverview;
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
