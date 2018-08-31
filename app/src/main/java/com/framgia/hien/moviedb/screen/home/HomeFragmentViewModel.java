package com.framgia.hien.moviedb.screen.home;

import android.databinding.ObservableField;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import com.framgia.hien.moviedb.screen.BaseViewModel;
import com.framgia.hien.moviedb.util.Constants;

public class HomeFragmentViewModel extends BaseViewModel {

    public ObservableField<HomeSliderFragmentPagerAdapter> homeSlideAdapter = new ObservableField<>();
    private static final int SLIDER_INTERVAL_TIMEOUT = 5500;
    private static final int DECREASE = -1;
    private final Handler mSliderHandler = new Handler();
    private Runnable mSliderRunnable;

    public HomeFragmentViewModel(FragmentManager fragmentManager) {
        homeSlideAdapter.set(new HomeSliderFragmentPagerAdapter(fragmentManager));
    }

    public void startSliderInterval(final ViewPager pager) {
        mSliderRunnable = new Runnable() {
            @Override
            public void run() {
                int count = pager.getCurrentItem();
                if (count == Constants.HOME_SLIDER_TOTAL_PAGE - 1) {
                    count = DECREASE;
                }
                pager.setCurrentItem(++count, true);
                mSliderHandler.postDelayed(this, SLIDER_INTERVAL_TIMEOUT);
            }
        };

        mSliderHandler.postDelayed(mSliderRunnable, SLIDER_INTERVAL_TIMEOUT);
    }

    public void stopSliderInterval() {
        mSliderHandler.removeCallbacks(mSliderRunnable);
    }

    @Override
    protected void onStart() {
    }

    @Override
    protected void onStop() {
    }
}
