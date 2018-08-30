package com.framgia.hien.moviedb.screen.home;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dong.moviedb.R;
import com.example.dong.moviedb.databinding.FragmentHomeBinding;
import com.framgia.hien.moviedb.screen.BaseFragment;

public class HomeFragment extends BaseFragment {

    private FragmentHomeBinding mBinding;
    private static HomeFragment sInstance;

    public HomeFragment() {
    }

    public static HomeFragment getsInstance(){
        if (sInstance == null){
            sInstance = new HomeFragment();
        }
        return sInstance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        mBinding.setBinding(this);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupSlider();
    }

    private void setupSlider() {

    }
}
