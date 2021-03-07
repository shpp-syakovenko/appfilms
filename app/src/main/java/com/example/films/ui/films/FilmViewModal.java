package com.example.films.ui.films;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.films.data.ApiFactory;
import com.example.films.data.ApiService;
import com.example.films.data.entity.Film;
import com.example.films.data.entity.Response;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class FilmViewModal extends AndroidViewModel {
    private CompositeDisposable compositeDisposable;
    private MutableLiveData<List<Film>> films;

    private static final String API_KEY = "d71194ebc35d4c006794a0ccdd01b5e0";
    private static String lang = "en-US";
    private static String sortBy = "popularity.desc";
    private static final String VOTE_COUNT = "1000";

    public FilmViewModal(@NonNull Application application) {
        super(application);
        films = new MutableLiveData<>();
    }

    public LiveData<List<Film>> getFilms() {
        return films;
    }

    public void changeSort(boolean sort, int page){
        sortBy = sort ? "vote_average.desc" : "popularity.desc";
        loadFilms(page);
    }

    // Load films from network
    public void loadFilms(int page) {
        ApiFactory apiFactory = ApiFactory.getInstance();
        ApiService apiService = apiFactory.getApiService();

        compositeDisposable = new CompositeDisposable();

        Disposable disposable = apiService.getFilmsResponse(API_KEY, Integer.toString(page), lang, sortBy, VOTE_COUNT)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Response>() {
                    @Override
                    public void accept(Response response) throws Exception {
                        films.setValue(response.getListFilms());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(getApplication(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        compositeDisposable.add(disposable);
    }

    @Override
    protected void onCleared() {
        compositeDisposable.dispose();
        super.onCleared();
    }
}
