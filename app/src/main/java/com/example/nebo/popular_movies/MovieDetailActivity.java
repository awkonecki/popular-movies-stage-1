package com.example.nebo.popular_movies;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class MovieDetailActivity extends AppCompatActivity {
    private ImageView mPosterImage = null;
    private ImageView mBackground = null;
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
        this.mBackground = (ImageView) findViewById(R.id.iv_background_detail);

        String posterURL = null, title = null, backdropURL = null;

        if (intent != null) {
            posterURL = intent.getStringExtra(getString(R.string.ik_movie_poster));
            backdropURL = intent.getStringExtra(getString(R.string.ik_movie_backdrop));
            title = intent.getStringExtra(getString(R.string.ik_movie_title));
        }

        Log.d("onCreate Details", posterURL + " " + title);

        Picasso.get().load(posterURL).error(R.drawable.image_placeholder).into(this.mPosterImage);
        Picasso.get().load(backdropURL).error(R.drawable.image_placeholder).into(this.mBackground);


        if (title == null) {
            title = getString(R.string.default_title);
        }

        this.mTitleView.setText(title);
        this.setTitle(title);

    }
}
