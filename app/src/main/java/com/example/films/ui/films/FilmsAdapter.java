package com.example.films.ui.films;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.films.R;
import com.example.films.data.entity.Film;

import java.util.ArrayList;
import java.util.List;

public class FilmsAdapter extends RecyclerView.Adapter<FilmViewHolder> {

    private List<Film> films;

    private OnClickPosterListener onClickPosterListener;

    public interface OnClickPosterListener{
        void onClickPoster(int position);
    }

    public FilmsAdapter() {
        this.films = new ArrayList<>();
    }

    @NonNull
    @Override
    public FilmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_film, parent, false);
        return new FilmViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FilmViewHolder holder, int position) {
        holder.bind(films.get(position), onClickPosterListener);
    }

    @Override
    public int getItemCount() {
        return films.size();
    }

    public List<Film> getFilms() {
        return films;
    }

    public void setOnClickPosterListener(OnClickPosterListener onClickPosterListener) {
        this.onClickPosterListener = onClickPosterListener;
    }

    public void addFilms(List<Film> films){
        this.films.addAll(films);
        notifyDataSetChanged();
    }

    public void setFilms(List<Film> films) {
        this.films.clear();
        this.films.addAll(films);
        notifyDataSetChanged();
    }
}
