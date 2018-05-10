package com.example.nebo.popular_movies;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
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
import android.widget.Toast;

import com.example.nebo.popular_movies.async.MovieAsyncTaskLoader;
import com.example.nebo.popular_movies.data.Movie;
import com.example.nebo.popular_movies.util.JsonUtils;
import com.example.nebo.popular_movies.util.MovieURLUtils;
import com.example.nebo.popular_movies.util.NetworkUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<String>,
        MovieAdapter.MovieAdatperOnClickListener {

    private static boolean mLoading = false;

    private static final int REFRESH_LOADER_ID = 13;
    private static final int FETCH_DATA_ID = 14;
    private static int mPageNumber = 1;

    private MovieAdapter mMovieAdapter = null;
    private RecyclerView mRecyclerView = null;
    private ProgressBar mProgressBar = null;

    private List<Movie> mMovies = new ArrayList<Movie>();

    /**
     * @brief Scroll listener class that when no more vertical in the downward direction can occur
     * will perform a the fetching of a new page of movies.
     * @note Although the listener itself is okay, I believe that this is not really a clean way to
     * implement this functionality.
     * @reference https://stackoverflow.com/questions/36127734/detect-when-recyclerview-reaches-the-bottom-most-position-while-scrolling
     */
    private class MovieScrollListener extends RecyclerView.OnScrollListener {
        /**
         * @brief If no more vertical scrolling down can occur then will attempt to fetch more data.
         * @param recyclerView The recycler view that is responsible for displaying the movies.
         * @param dx current horizontal position
         * @param dy current vertical position
         */
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            GridLayoutManager layoutManager = (GridLayoutManager) recyclerView.getLayoutManager();

            // int visibleItems = layoutManager.getChildCount();
            int totalItems = layoutManager.getItemCount();
            // int firstPosition = layoutManager.findFirstVisibleItemPosition();
            int lastPosition = layoutManager.findLastVisibleItemPosition();

            if (lastPosition > (totalItems * 9 / 10)) {
                MainActivity.this.fetchData();
            }

            // This method is a member of the layout manager.
            if (!recyclerView.canScrollVertically(1)) {
                //MainActivity.this.fetchData();
                Toast.makeText(MainActivity.this, "scrolled", 1).show();
            }

            // Log.d("Focusable value", Integer.toString(recyclerView.));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Save the instance of the progress bar.
        mProgressBar = findViewById(R.id.pb_main_progress_bar);

        mMovieAdapter = new MovieAdapter(this, this.mMovies);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_recycler_view);
        mRecyclerView.addOnScrollListener(new MovieScrollListener());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false);

        mRecyclerView.setAdapter(mMovieAdapter);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        this.fetchData();
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
                this.fetchData();
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

    public void fetchData() {
        Bundle args;

        if (!MainActivity.mLoading) {
            args = new Bundle();
            args.putInt(getString(R.string.bk_page_number), MainActivity.mPageNumber++);
            args.putString(getString(R.string.bk_request_type), getString(R.string.bv_request_type_popular));

            MainActivity.mLoading = true;
            // Loader Manager for async tasks
            LoaderManager loaderManager = getSupportLoaderManager();
            Loader<Cursor> movieLoader = loaderManager.getLoader(MainActivity.FETCH_DATA_ID);

            if (movieLoader == null) {
                loaderManager.initLoader(MainActivity.FETCH_DATA_ID, args, this).forceLoad();
            } else {
                loaderManager.restartLoader(MainActivity.FETCH_DATA_ID, args, this).forceLoad();
            }
        }
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

    @Override
    protected void onResume() {
        super.onResume();
        // mMovieAdapter.registerAdapterDataObserver(this.mObserver);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // mMovieAdapter.unregisterAdapterDataObserver(this.mObserver);
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
                        String response = null;
                        Log.d("LoadInBackground", "in background task");
                        URL url = MovieURLUtils.buildPopularURL(); // NetworkUtils.buildUrl("xxx");
                        try {
                            response = NetworkUtils.getUrlHttpResponse(url);

                            /*
                            List<Movie> movies = JsonUtils.parseJsonResponse(response);

                            for (Movie movie : movies) {
                                Log.d("Movie", movie.toString());
                            }
                            */
                            Log.d("Network Result", response);
                        }
                        catch (java.io.IOException e) {
                            e.printStackTrace();
                        }
                        // work from async tasks for getting data i.e. url, getResponse function.
                        return response;
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
                        // super.onStopLoading();
                        Log.d("Stoped loading", "not loading.");
                    }

                };

            case MainActivity.FETCH_DATA_ID:
                return new MovieAsyncTaskLoader(this, args);
                // break;
            default:
                Log.d("onCreateLoader Error", "Illegal ID of " + id);

                throw new java.lang.IllegalArgumentException("Unsupported ID value.");
        }

        // return null;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String response) {
        // loader is complete but might be some errors w.r.t the data
        // should likely need to set visibility of the views here.
        if (response == null || response.isEmpty()) {

        }
        else {


            for (Movie movie : JsonUtils.parseJsonResponse(response)) {
                this.mMovies.add(movie);
            }

            this.mMovieAdapter.setMovies(this.mMovies);

            Log.d("Movie Count", Integer.toString(this.mMovies.size()));
        }

        this.noLoading();
        MainActivity.mLoading = false;
        Log.d("onLoadFinished", "Completed the task");
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }
    //**********************************************************************************************
    // END LOADER METHODS FOR ASYNC TASKS
    //**********************************************************************************************
}
