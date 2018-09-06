package com.framgia.hien.moviedb.screen.person;

import android.databinding.ObservableField;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.dong.moviedb.BuildConfig;
import com.framgia.hien.moviedb.data.model.Person;
import com.framgia.hien.moviedb.data.repository.MovieRepository;
import com.framgia.hien.moviedb.data.repository.PersonRepository;
import com.framgia.hien.moviedb.screen.BaseViewModel;
import com.framgia.hien.moviedb.screen.home.MovieAdapter;
import com.framgia.hien.moviedb.util.rx.BaseScheduleProvider;
import com.framgia.hien.moviedb.util.rx.ScheduleProvider;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class PersonViewModel extends BaseViewModel implements MovieAdapter.ItemClickListener {

    private static final int OVEVERVIEW_MAXLINE = 30;
    private static final int OVEVERVIEW_MINLINE = 3;
    private AppCompatActivity mActivity;
    private PersonRepository mPersonRepository;
    public ObservableField<Person> personObservableField = new ObservableField<>();
    private BaseScheduleProvider mBaseScheduleProvider;
    private CompositeDisposable mCompositeDisposable;
    private int mIdPerson;
    private ProgressBar mProgressBar;
    private boolean mIsTextOverviewExpanded;
    private TextView mTextOverview;
    private BackPressListener mBackPress;

    public PersonViewModel(AppCompatActivity activity, int idPerson, PersonRepository repository) {
        this.mActivity = activity;
        this.mIdPerson = idPerson;
        this.mPersonRepository = repository;
        setComponent();
    }

    private void setComponent() {
        this.mBaseScheduleProvider = ScheduleProvider.getInstance();
        mCompositeDisposable = new CompositeDisposable();
    }


    public void setBackPress(BackPressListener listener) {
        mBackPress = listener;
    }

    public void onBackClicked(View view) {
        mBackPress.backPress();
    }

    public void setTextView(TextView textView) {
        this.mTextOverview = textView;
    }

    public void setProgressBar(ProgressBar progressBar) {
        this.mProgressBar = progressBar;
    }

    public void onTextClicked(View view) {
        if (mIsTextOverviewExpanded) {
            mTextOverview.setMaxLines(OVEVERVIEW_MINLINE);
        } else {
            mTextOverview.setMaxLines(OVEVERVIEW_MAXLINE);
        }
        mIsTextOverviewExpanded = !mIsTextOverviewExpanded;
    }

    public void getPerson() {
        mProgressBar.setVisibility(View.VISIBLE);
        Disposable disposable = mPersonRepository.getPerson(mIdPerson, BuildConfig.API_KEY.toString())
                .subscribeOn(mBaseScheduleProvider.io())
                .observeOn(mBaseScheduleProvider.ui())
                .subscribe(new Consumer<Person>() {
                    @Override
                    public void accept(Person person) throws Exception {
                        personObservableField.set(person);
                        mProgressBar.setVisibility(View.GONE);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mProgressBar.setVisibility(View.GONE);
                    }
                });
        mCompositeDisposable.add(disposable);
    }

    @Override
    protected void onStart() {
        getPerson();
    }

    @Override
    protected void onStop() {

    }

    @Override
    public void onItemClicked(Integer movieId) {

    }

    interface BackPressListener {
        void backPress();
    }
}
