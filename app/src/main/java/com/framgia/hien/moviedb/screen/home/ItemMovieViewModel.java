package com.framgia.hien.moviedb.screen.home;

import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.dong.moviedb.R;
import com.framgia.hien.moviedb.data.model.Movie;
import com.framgia.hien.moviedb.util.Constants;

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
        String path = Constants.END_POINT_IMAGE_URL.concat(url);
        Glide.with(image.getContext())
                .load(path)
                .apply(new RequestOptions().placeholder(R.drawable.movie_detail_poster_sample))
                .into(image);
    }

    public void onItemClicked(View view){
        if (mItemClickListener == null || movieObservableField.get() == null){
            return;
        }
        mItemClickListener.onItemClicked(movieObservableField.get().getId());
    }
}
