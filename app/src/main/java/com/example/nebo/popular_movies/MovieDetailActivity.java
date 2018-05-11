package com.example.nebo.popular_movies;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class MovieDetailActivity extends AppCompatActivity {
    private ImageView mPosterImage = null;
    private TextView mTitleView = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();
        if (intent == null) {
            Toast.makeText(this, "null intent", 1).show();
            finish();
        }

        this.mPosterImage = (ImageView) findViewById(R.id.iv_moive_poster_detail);
        this.mTitleView = (TextView) findViewById(R.id.tv_movie_title);

        String posterURL = null, title = null;

        if (intent != null) {
            posterURL = intent.getStringExtra(getString(R.string.ik_movie_poster));
            title = intent.getStringExtra(getString(R.string.ik_movie_title));
        }

        Log.d("onCreate Details", posterURL + " " + title);

        Picasso.get().load(posterURL).error(R.drawable.image_placeholder).into(this.mPosterImage);
        if (title == null) {
            title = getString(R.string.default_title);
        }

        this.mTitleView.setText(title);
    }
}
