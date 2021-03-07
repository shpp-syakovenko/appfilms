package com.example.films.ui.detailFilm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.films.R;
import com.squareup.picasso.Picasso;

public class DetailFilmActivity extends AppCompatActivity {

    private TextView textViewTitle;
    private TextView textViewOverview;
    private ImageView imageViewBigPoster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_film);

        textViewTitle = findViewById(R.id.textViewTitle);
        textViewOverview = findViewById(R.id.textViewOverview);
        imageViewBigPoster = findViewById(R.id.imageViewBigPoster);

        Intent intent = getIntent();
        textViewTitle.setText(intent.getStringExtra("title"));
        textViewOverview.setText(intent.getStringExtra("overview"));
        Picasso.get().load("https://image.tmdb.org/t/p/w500" + intent.getStringExtra("poster")).into(imageViewBigPoster);
    }
}