package com.framgia.hien.moviedb.screen.search;

import com.framgia.hien.moviedb.data.model.Genre;
import com.framgia.hien.moviedb.screen.BaseViewModel;

public class SearchFragmentViewModel extends BaseViewModel{

    private Genre mGenre;

    public void SearchFramgmentViewModel(){

    }

    public void setGenre(Genre genre){
        this.mGenre = genre;
    }

    @Override
    protected void onStart() {

    }

    @Override
    protected void onStop() {

    }
}
