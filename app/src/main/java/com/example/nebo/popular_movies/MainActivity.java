package com.example.nebo.popular_movies;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.CursorLoader;
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

import com.example.nebo.popular_movies.util.NetworkUtils;

import java.net.URL;

public class MainActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<String>,
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

        // Loader Manager for async tasks
        LoaderManager loaderManager = getSupportLoaderManager();
        Loader<Cursor> movieLoader = loaderManager.getLoader(MainActivity.REFRESH_LOADER_ID);

        if (movieLoader == null) {
            loaderManager.initLoader(MainActivity.REFRESH_LOADER_ID, null, this).forceLoad();
        }
        else {
            loaderManager.restartLoader(MainActivity.REFRESH_LOADER_ID, null, this).forceLoad();
        }
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

    //**********************************************************************************************
    // START ANDROID LIFE-CYCLE METHODS
    //**********************************************************************************************
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Do something here
    }

    @Override
    protected void onStop() {
        super.onStop();

    }
    //**********************************************************************************************
    // END ANDROID LIFE-CYCLE METHODS
    //**********************************************************************************************

    //**********************************************************************************************
    // START LOADER METHODS FOR ASYNC TASKS
    //
    // Loader is to be used if tied to the activity lifecycle
    // allow for user interface changes and commuinicate with Activity
    // For this Loader is used due to population of a recycler view adapter
    //**********************************************************************************************
    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, final @Nullable Bundle args) {
        Log.d("onCreateLoader", "In on CreateLoader");
        switch(id) {
            case MainActivity.REFRESH_LOADER_ID:
                // Need to go to the refresh activity / process.
                Log.d("onCreateLoader", "Received ID of " + id);
                return new AsyncTaskLoader<String>(this) {
                    @Nullable
                    @Override
                    public String loadInBackground() {
                        Log.d("LoadInBackground", "in background task");
                        URL url = NetworkUtils.buildUrl("xxx");
                        try {
                            String string = NetworkUtils.getUrlHttpResponse(url);
                            Log.d("Network Result", string);
                        }
                        catch (java.io.IOException e) {
                            e.printStackTrace();
                        }
                        // work from async tasks for getting data i.e. url, getResponse function.
                        return null;
                    }

                    @Override
                    protected void onStartLoading() {
                        super.onStartLoading();
                        Log.d("Starting the loading", "Loading");
                        // check for null args if necessary.
                        // Likely should set visibility here
                    }

                    @Override
                    protected void onStopLoading() {
                        super.onStopLoading();
                        Log.d("Stoped loading", "not loading.");
                    }

                };
                // break;
            default:
                Log.d("onCreateLoader Error", "Illegal ID of " + id);

                throw new java.lang.IllegalArgumentException("Unsupported ID value.");
        }

        // return null;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        // loader is complete but might be some errors w.r.t the data
        // should likely need to set visibility of the views here.
        Log.d("onLoadFinished", "Completed the task");
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }
    //**********************************************************************************************
    // END LOADER METHODS FOR ASYNC TASKS
    //**********************************************************************************************
}
