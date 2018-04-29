package com.example.nebo.popular_movies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    private MovieAdapter mMovieAdapter = null;
    private RecyclerView mRecyclerView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMovieAdapter = new MovieAdapter();

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_recycler_view);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);

        mRecyclerView.setAdapter(mMovieAdapter);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setHasFixedSize(true);
    }
}
