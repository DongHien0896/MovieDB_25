package com.framgia.hien.moviedb.util.binding;

import android.databinding.BindingAdapter;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.framgia.hien.moviedb.screen.home.HomeSliderFragmentPagerAdapter;

public class Binding {
    @BindingAdapter({"onNavigationItemSelected"})
    public static void setBottomNavigationClick(BottomNavigationView bottomNavigationView,
            BottomNavigationView.OnNavigationItemSelectedListener listener) {
        bottomNavigationView.setOnNavigationItemSelectedListener(listener);
    }

    @BindingAdapter({"setPagerAdapter"})
    public static void setViewPager(ViewPager viewPager, HomeSliderFragmentPagerAdapter adapter) {
        viewPager.setAdapter(adapter);
    }

}
