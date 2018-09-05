package com.framgia.hien.moviedb.screen.main;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.dong.moviedb.R;
import com.framgia.hien.moviedb.screen.BaseViewModel;
import com.framgia.hien.moviedb.screen.favorite.FavoriteFragment;
import com.framgia.hien.moviedb.screen.home.HomeFragment;
import com.framgia.hien.moviedb.util.network.NetworkReceiver;

public class MainViewModel extends BaseViewModel implements BottomNavigationView.OnNavigationItemSelectedListener {

    private Fragment mFragment;
    private HomeFragment mHomeFragment;
    private FavoriteFragment mFavoriteFragment;
    private FragmentManager mFragmentManager;
    private NetworkReceiver mNetworkReceiver;
    private AppCompatActivity mAppCompatActivity;

    public MainViewModel(AppCompatActivity appCompatActivity) {
        this.mAppCompatActivity = appCompatActivity;
        mFragmentManager = appCompatActivity.getSupportFragmentManager();
        createComponent();
        checkInternet();
    }

    @Override
    protected void onStart() {
        if (mNetworkReceiver == null){
            return;
        }
        mAppCompatActivity.registerReceiver(mNetworkReceiver, new IntentFilter(ConnectivityManager
                .CONNECTIVITY_ACTION));
    }

    @Override
    protected void onStop() {
        if (mNetworkReceiver == null){
            return;
        }
        mAppCompatActivity.unregisterReceiver(mNetworkReceiver);
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

    private void createComponent() {
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

    public void initNetworkBroadcastReceiver(NetworkReceiver.NetworkStateListener listener) {
        mNetworkReceiver = new NetworkReceiver(listener);
    }

    protected void showDialogNoInternet() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mAppCompatActivity.getApplicationContext());
        builder.setTitle(mAppCompatActivity.getString(R.string.app_name));
        builder.setMessage(mAppCompatActivity.getString(R.string.message_dialog_no_internet));
        AlertDialog dialog = builder.create();
        if (dialog.isShowing()) {
            return;
        }
        dialog.show();
    }

    private void checkInternet() {
        initNetworkBroadcastReceiver(new NetworkReceiver.NetworkStateListener() {
            @Override
            public void onNetworkConnected() {
                createComponent();
            }

            @Override
            public void onNetworkDisconnected() {
                showDialogNoInternet();
            }
        });
    }
}
