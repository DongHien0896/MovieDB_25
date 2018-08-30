package com.framgia.hien.moviedb.screen.home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class HomeSliderFragmentPagerAdapter extends FragmentPagerAdapter {

    public static final int HOME_SLIDER_TOTAL_PAGE = 5;

    public HomeSliderFragmentPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        return HomeSliderPagerFragment.getInstance(++position);
    }

    @Override
    public int getCount() {
        return HOME_SLIDER_TOTAL_PAGE;
    }
}
