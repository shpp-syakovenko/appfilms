package com.example.films.ui.films;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.example.films.R;
import com.example.films.data.entity.Film;
import com.example.films.ui.detailFilm.DetailFilmActivity;

import java.util.List;

public class FilmListActivity extends AppCompatActivity {

    private TextView textViewPopularity;
    private TextView textViewRated;
    private Switch switchSort;
    private RecyclerView recyclerViewFilms;

    private FilmViewModal viewModal;
    private FilmsAdapter adapter;

    private int page = 1;

    private boolean loading = true;
    GridLayoutManager gridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_list);

        textViewPopularity = findViewById(R.id.textViewPopularity);
        textViewRated = findViewById(R.id.textViewRated);
        switchSort = findViewById(R.id.switchSort);


        gridLayoutManager = new GridLayoutManager(this,2);
        recyclerViewFilms = findViewById(R.id.recyclerViewFilms);
        recyclerViewFilms.setLayoutManager(gridLayoutManager);

        viewModal = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(FilmViewModal.class);
        adapter = new FilmsAdapter();
        recyclerViewFilms.setAdapter(adapter);


        switchSort.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                page = 1;
                viewModal.changeSort(isChecked, page);
                recyclerViewFilms.smoothScrollToPosition(0);
                if(isChecked){
                    textViewRated.setTextColor(getResources().getColor(R.color.accent));
                    textViewPopularity.setTextColor(getResources().getColor(R.color.white));
                }else{
                    textViewRated.setTextColor(getResources().getColor(R.color.white));
                    textViewPopularity.setTextColor(getResources().getColor(R.color.accent));
                }
            }
        });


        viewModal.getFilms().observe(this, new Observer<List<Film>>() {
            @Override
            public void onChanged(List<Film> films) {
                    if(loading) {
                        adapter.setFilms(films);
                    }else{
                        adapter.addFilms(films);
                        loading = true;
                    }
            }
        });

        recyclerViewFilms.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) { //check for scroll down
                    int visibleItemCount = gridLayoutManager.getChildCount(); // How much items on the screen
                    int firstItemOnScreen = gridLayoutManager.findFirstVisibleItemPosition(); // Position first item on the screen
                    int totalItemCount = gridLayoutManager.getItemCount();    // Hom many items.

                    if (loading) {
                        if ((visibleItemCount + firstItemOnScreen) >= totalItemCount) {
                            loading = false;
                            page++;
                            viewModal.loadFilms(page);
                        }
                    }
                }
            }
        });

        adapter.setOnClickPosterListener(new FilmsAdapter.OnClickPosterListener() {
            @Override
            public void onClickPoster(int position) {
                Film film = adapter.getFilms().get(position);
                Intent intent = new Intent(getApplicationContext(), DetailFilmActivity.class);
                intent.putExtra("title", film.getTitle());
                intent.putExtra("poster", film.getPosterPath());
                intent.putExtra("overview", film.getOverview());
                startActivity(intent);
            }
        });

        viewModal.loadFilms(page);

    }
}