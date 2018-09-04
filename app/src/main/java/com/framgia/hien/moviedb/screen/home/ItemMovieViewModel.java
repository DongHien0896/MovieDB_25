package com.framgia.hien.moviedb.screen.home;

import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.framgia.hien.moviedb.data.model.Movie;

import io.reactivex.annotations.NonNull;

public class ItemMovieViewModel extends BaseObservable {

    public ObservableField<Movie> movieObservableField = new ObservableField<>();
    private MovieAdapter.ItemClickListener mItemClickListener;

    public ItemMovieViewModel(MovieAdapter.ItemClickListener itemClick){
        this.mItemClickListener = itemClick;
    }

    public void setMovie(@NonNull Movie movie){
        movieObservableField.set(movie);
    }

    @BindingAdapter({"imageUrl"})
    public static void loadUrl(ImageView image, String url) {
        Glide.with(image.getContext())
                .load(url)
                .into(image);
    }

    public void onItemClicked(){
        if (mItemClickListener == null || movieObservableField.get() == null){
            return;
        }
        mItemClickListener.onItemClicked(movieObservableField.get().getId());
    }
}
