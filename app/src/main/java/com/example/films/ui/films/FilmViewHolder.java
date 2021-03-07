package com.example.films.ui.films;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.films.R;
import com.example.films.data.entity.Film;
import com.squareup.picasso.Picasso;

public class FilmViewHolder extends RecyclerView.ViewHolder {

    private ImageView imageView;

    public FilmViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.imageViewSmallPoster);
    }

    public void bind(Film film, FilmsAdapter.OnClickPosterListener onClickPosterListener){
        Picasso.get().load("https://image.tmdb.org/t/p/w185" + film.getPosterPath()).into(imageView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onClickPosterListener != null){
                    onClickPosterListener.onClickPoster(getAdapterPosition());
                }
            }
        });
    }
}
