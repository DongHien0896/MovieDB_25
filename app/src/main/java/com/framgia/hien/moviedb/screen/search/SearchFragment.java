package com.framgia.hien.moviedb.screen.search;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.dong.moviedb.R;
import com.example.dong.moviedb.databinding.FragmentSearchBinding;
import com.framgia.hien.moviedb.data.model.Genre;
import com.framgia.hien.moviedb.screen.BaseFragment;

public class SearchFragment extends BaseFragment {

    private FragmentSearchBinding mBinding;
    private SearchFragmentViewModel mViewModel;
    private ProgressBar mProgressBar;
    private static SearchFragment sFragment;

    public static SearchFragment getInstance() {
        if (sFragment == null) {
            sFragment = new SearchFragment();
        }
        return sFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container
            , @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false);

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new SearchFragmentViewModel();
    }

    public void searchForResult(String query) {
        //show result search
    }

    public void setGenre(Genre genre) {
        mViewModel.setGenre(genre);
    }

    @Override
    public void onStart() {
        super.onStart();
        mViewModel.onStart();
    }

    @Override
    public void onStop() {
        mViewModel.onStop();
        super.onStop();
    }
}
