package com.framgia.hien.moviedb.screen.main;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.dong.moviedb.R;
import com.framgia.hien.moviedb.screen.BaseViewModel;
import com.framgia.hien.moviedb.screen.favorite.FavoriteFragment;
import com.framgia.hien.moviedb.screen.home.HomeFragment;

public class MainViewModel extends BaseViewModel implements BottomNavigationView.OnNavigationItemSelectedListener {

    private Fragment mFragment;
    private HomeFragment mHomeFragment;
    private FavoriteFragment mFavoriteFragment;
    private FragmentManager mFragmentManager;

    public MainViewModel(AppCompatActivity appCompatActivity) {
        mFragmentManager = appCompatActivity.getSupportFragmentManager();
        createFragment();
    }

    @Override
    protected void onStart() {

    }

    @Override
    protected void onStop() {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_home:
                hideShowFragment(mFragment, mHomeFragment);
                mFragment = mHomeFragment;
                return true;
            case R.id.navigation_favorite:
                hideShowFragment(mFragment, mFavoriteFragment);
                mFragment = mFavoriteFragment;
                return true;
            case R.id.navigation_setting:
                return true;
        }
        return false;
    }

    private void createFragment() {
        mHomeFragment = HomeFragment.getsInstance();
        mFavoriteFragment = FavoriteFragment.getsInstance();
        addHideFragment(mFavoriteFragment);
        mFragmentManager.beginTransaction().add(R.id.frame_container, mHomeFragment).commit();
        mFragment = mHomeFragment;
    }

    private void addHideFragment(Fragment fragment) {
        mFragmentManager.beginTransaction().add(R.id.frame_container, fragment).hide(fragment).commit();
    }

    private void hideShowFragment(Fragment hide, Fragment show) {
        mFragmentManager.beginTransaction().hide(hide).show(show).commit();
    }
}
