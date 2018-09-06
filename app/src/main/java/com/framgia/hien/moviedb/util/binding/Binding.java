package com.framgia.hien.moviedb.util.binding;

import android.databinding.BindingAdapter;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.dong.moviedb.R;
import com.framgia.hien.moviedb.screen.home.HomeSliderFragmentPagerAdapter;
import com.framgia.hien.moviedb.util.Constants;

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

    @BindingAdapter({"recyclerAdapter"})
    public static void setAdapterForRecyclerView(RecyclerView recyclerView,
                                                 RecyclerView.Adapter adapter) {
        recyclerView.setAdapter(adapter);
    }

    @BindingAdapter({"imageUrl"})
    public static void loadUrl(ImageView image, String url) {
        if (url == null) {
            url = Constants.EXAMPLE_URL;
        }
        String path = Constants.END_POINT_IMAGE_URL.concat(url);
        Glide.with(image.getContext())
                .load(path)
                .apply(new RequestOptions().placeholder(R.drawable.movie_detail_poster_sample))
                .into(image);
    }
}
