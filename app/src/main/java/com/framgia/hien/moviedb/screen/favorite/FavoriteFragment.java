package com.framgia.hien.moviedb.screen.favorite;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dong.moviedb.R;
import com.example.dong.moviedb.databinding.FragmentFavoriteBinding;
import com.framgia.hien.moviedb.screen.BaseFragment;

public class FavoriteFragment extends BaseFragment {

    private FragmentFavoriteBinding mBinding;
    private static FavoriteFragment sInstance;

    public FavoriteFragment() {

    }

    public static FavoriteFragment getsInstance(){
        if (sInstance == null){
            sInstance = new FavoriteFragment();
        }
        return sInstance;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_favorite, container, false);
        mBinding.setBinding(this);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
