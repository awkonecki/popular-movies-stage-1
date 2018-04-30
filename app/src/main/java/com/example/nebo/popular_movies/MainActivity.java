package com.example.nebo.popular_movies;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor>,
        MovieAdapter.MovieAdatperOnClickListener {

    private static final int REFRESH_LOADER_ID = 13;

    private MovieAdapter mMovieAdapter = null;
    private RecyclerView mRecyclerView = null;
    private ProgressBar mProgressBar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Save the instance of the progress bar.
        mProgressBar = findViewById(R.id.pb_main_progress_bar);



        mMovieAdapter = new MovieAdapter(this);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_recycler_view);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);

        mRecyclerView.setAdapter(mMovieAdapter);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setHasFixedSize(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int selectedItemId = item.getItemId();

        switch (selectedItemId) {
            case R.id.mi_refresh:
                break;
            case R.id.mi_sort:
                break;
            case R.id.mi_settings:
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void onLoading() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void noLoading() {
        mRecyclerView.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void OnClick(int position) {

    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        switch(id) {
            case MainActivity.REFRESH_LOADER_ID:
                // Need to go to the refresh activity / process.
                Log.d("onCreateLoader", "Received ID of " + id);
                break;
            default:
                Log.d("onCreateLoader Error", "Illegal ID of " + id);

                throw new java.lang.IllegalArgumentException("Unsupported ID value.");
        }

        return null;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }

    /*
    private class MovieListener implements MovieAdapter.MovieAdatperOnClickListener {
        @Override
        public void OnClick(int position) {

        }
    }
    */
}
