package com.framgia.hien.moviedb.screen.main;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.MenuItem;
import android.view.View;

import com.example.dong.moviedb.R;
import com.framgia.hien.moviedb.data.model.Genre;
import com.framgia.hien.moviedb.screen.BaseViewModel;
import com.framgia.hien.moviedb.screen.favorite.FavoriteFragment;
import com.framgia.hien.moviedb.screen.home.HomeFragment;
import com.framgia.hien.moviedb.screen.home.HomeFragmentViewModel;
import com.framgia.hien.moviedb.screen.search.SearchFragment;
import com.framgia.hien.moviedb.util.network.NetworkReceiver;

public class MainViewModel extends BaseViewModel implements BottomNavigationView.OnNavigationItemSelectedListener,
        SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener,
        HomeFragmentViewModel.OnClickSearchMoviesByGenre {

    private Fragment mFragment;
    private HomeFragment mHomeFragment;
    private FavoriteFragment mFavoriteFragment;
    private FragmentManager mFragmentManager;
    private NetworkReceiver mNetworkReceiver;
    private AppCompatActivity mAppCompatActivity;
    private SearchView mSearchView;
    private MenuItem mSearchMenu;
    private SearchFragment mSearchFragment;
    private BottomNavigationView mBottomNavigationView;

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
                mSearchMenu.setVisible(true);
                hideShowFragment(mFragment, mHomeFragment);
                setHomeFragment();
                mFragment = mHomeFragment;
                return true;
            case R.id.navigation_favorite:
                hideShowFragment(mFragment, mFavoriteFragment);
                mFragment = mFavoriteFragment;
                mSearchMenu.setVisible(false);
                return true;
            case R.id.navigation_setting:
                mSearchMenu.setVisible(false);
                return true;
        }
        return false;
    }

    private void setHomeFragment() {
        mHomeFragment.setClickGenreItem(this);
    }

    private void createComponent() {
        mHomeFragment = HomeFragment.getsInstance();
        mFavoriteFragment = FavoriteFragment.getsInstance();
        mSearchFragment = SearchFragment.getInstance();
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

    public void setMenuSearch(SearchView search, MenuItem menuItem){
        this.mSearchView = search;
        this.mSearchMenu = menuItem;
        mSearchView.setFocusableInTouchMode(true);
        mSearchView.setOnQueryTextListener(this);
        mSearchMenu.setOnActionExpandListener(this);
        mHomeFragment.setClickGenreItem(this);
    }

    public void setBottomNavigation(BottomNavigationView bottomNavigation){
        this.mBottomNavigationView = bottomNavigation;
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

    @Override
    public boolean onQueryTextSubmit(String query) {
        mSearchView.setQuery(query, false);
        mSearchFragment.searchForResult(query);
        mSearchView.clearFocus();
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public boolean onMenuItemActionExpand(MenuItem menuItem) {
        mBottomNavigationView.setVisibility(View.GONE);
        mFragmentManager.beginTransaction()
                .add(R.id.frame_container, mSearchFragment)
                .hide(mFragment)
                .addToBackStack(null)
                .commit();
        return true;
    }

    @Override
    public boolean onMenuItemActionCollapse(MenuItem menuItem) {
        mBottomNavigationView.setVisibility(View.VISIBLE);
        mFragmentManager.popBackStack();
        return true;
    }

    @Override
    public void searchMoviesByGenre(Genre genre) {
        mSearchView.setFocusableInTouchMode(false);
        mSearchMenu.expandActionView();
        mSearchView.setQuery(genre.getName(), false);
        mSearchView.clearFocus();
        mSearchFragment.setGenre(genre);
    }
}
