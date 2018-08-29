package com.framgia.hien.moviedb.util.binding;

import android.databinding.BindingAdapter;
import android.support.design.widget.BottomNavigationView;

public class Binding {
    @BindingAdapter({"onNavigationItemSelected"})
    public static void setBottomNavigationClick(BottomNavigationView bottomNavigationView,
            BottomNavigationView.OnNavigationItemSelectedListener listener) {
        bottomNavigationView.setOnNavigationItemSelectedListener(listener);
    }
}
