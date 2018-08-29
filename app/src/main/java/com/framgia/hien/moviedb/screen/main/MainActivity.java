package com.framgia.hien.moviedb.screen.main;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.MenuItem;

import com.example.dong.moviedb.R;
import com.example.dong.moviedb.databinding.ActivityMainBinding;
import com.framgia.hien.moviedb.screen.BaseActivity;
import com.framgia.hien.moviedb.screen.favorite.FavoriteFragment;
import com.framgia.hien.moviedb.screen.home.HomeFragment;

public class MainActivity extends BaseActivity {

    private MainViewModel mViewModel;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            mViewModel.onNavigationItemSelected(item);
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mViewModel = new MainViewModel(this);
        binding.setMainViewModel(mViewModel);
        BottomNavigationView navigation = findViewById(R.id.navigation_bottom);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
}
