package com.framgia.hien.moviedb.screen.detail;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.example.dong.moviedb.R;
import com.example.dong.moviedb.databinding.ActivityDetailBinding;
import com.framgia.hien.moviedb.screen.BaseActivity;

public class DetailActivity extends BaseActivity {

    private DetailViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDetailBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        mViewModel = new DetailViewModel(this);
        binding.setViewModel(mViewModel);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
