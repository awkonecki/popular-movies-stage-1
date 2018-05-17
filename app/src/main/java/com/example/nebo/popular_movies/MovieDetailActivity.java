package com.example.nebo.popular_movies;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nebo.popular_movies.data.Movie;
import com.squareup.picasso.Picasso;

public class MovieDetailActivity extends AppCompatActivity {
    private ImageView mPosterImage = null;
    private ImageView mBackground = null;
    private TextView mSynopsis = null;
    private TextView mTitle = null;
    private TextView mRating = null;
    private TextView mReleaseDate = null;

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
            finish();
        }

        this.mPosterImage = (ImageView) findViewById(R.id.iv_moive_poster_detail);
        this.mSynopsis = (TextView) findViewById(R.id.tv_movie_description);
        this.mBackground = (ImageView) findViewById(R.id.iv_background_detail);
        this.mRating = (TextView) findViewById(R.id.tv_movie_detail_rating);
        this.mTitle = (TextView) findViewById(R.id.tv_movie_detail_title);
        this.mReleaseDate = (TextView) findViewById(R.id.tv_movie_detail_release_date);

        Movie movie = null;

        if (intent != null) {
            movie = new Movie(intent.getStringExtra(getString(R.string.ik_movie_title)),
                    intent.getIntExtra(getString(R.string.ik_movie_id), -1),
                    intent.getDoubleExtra(getString(R.string.ik_user_rating), -1),
                    0.0,
                    intent.getStringExtra(getString(R.string.ik_movie_poster)),
                    intent.getStringExtra(getString(R.string.ik_movie_backdrop)),
                    intent.getStringExtra(getString(R.string.ik_movie_synopsis)),
                    intent.getStringExtra(getString(R.string.ik_release_date)));
        }

        this.populateUI(movie);
    }

    private void populateUI(Movie movie) {
        String title = null;

        if (movie != null) {
            Picasso.get().load(movie.getPosterPath()).error(R.drawable.image_placeholder).into(this.mPosterImage);
            Picasso.get().load(movie.getBackdropPath()).error(R.drawable.image_placeholder).into(this.mBackground);

            if (movie.getTitle() == null) {
                title = getString(R.string.default_title);
            }

            this.setTitle(title);
            this.mSynopsis.setText(movie.getOverview());
            this.mReleaseDate.setText(movie.getReleaseDate());
            this.mRating.setText(Double.toString(movie.getVote()));
            this.mTitle.setText(movie.getTitle());
        }
    }
}
